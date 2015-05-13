// Victim_DLL.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "Wincrypt.h"
#include "Victim_DLL.h"

VICTIM_DLL_API bool MyEncryptFile(char *szPath, char *szPassword)
{
	bool bRet = false;
	HCRYPTPROV hCryptProv = NULL;
	HCRYPTKEY hKey = NULL;
	
	while(1)
	{
		// Acquire a cryptographic context
		if(!CryptAcquireContext(&hCryptProv,               // handle to the CSP
							   NULL,                  // container name 
							   NULL,                      // use the default provider
							   PROV_RSA_FULL,             // provider type
							   CRYPT_VERIFYCONTEXT))      // flag values
		{
			break;
		}

		// Derive a Key
		if(!DeriveKey(hCryptProv, szPassword, &hKey))
		{
			break;
		}

		// Encrypt the file
		OFSTRUCT ofStr;
		ofStr.cBytes = sizeof(OFSTRUCT);
		HANDLE hFile = (HANDLE)OpenFile(szPath, &ofStr, OF_READWRITE);

		if(NULL == hFile)
			break;

		DWORD dwSizeLow(0), dwSizeHigh(0);
		dwSizeLow = GetFileSize(hFile, &dwSizeHigh);

		BYTE *pbData = new BYTE[dwSizeLow*4];

		if(NULL == pbData)
			break;

		DWORD dwBytesRead(0);
		ReadFile(hFile, pbData, dwSizeLow, &dwBytesRead, NULL);

		CryptEncrypt(hKey, NULL, TRUE, 0, pbData, &dwSizeLow, dwSizeLow*4);

		SetFilePointer(hFile, 0, NULL, FILE_BEGIN);

		DWORD dwBytesWritten(0);
		WriteFile(hFile, pbData, dwSizeLow, &dwBytesWritten, NULL);

		CloseHandle(hFile);

		bRet = true;
		break;
	}	

	// Destroy the session key.
	if(hKey)
	{
		CryptDestroyKey(hKey);
	}

	// Release the provider handle.
	if(hCryptProv)
	{
	   CryptReleaseContext(hCryptProv, 0);
	}

	return bRet;
}

VICTIM_DLL_API bool MyDecryptFile(char *szPath, char *szPassword)
{
	bool bRet = false;
	HCRYPTPROV hCryptProv = NULL;
	HCRYPTKEY hKey = NULL;
	
	while(1)
	{
		// Acquire a cryptographic context
		if(!CryptAcquireContext(&hCryptProv,               // handle to the CSP
							   NULL,                  // container name 
							   NULL,                      // use the default provider
							   PROV_RSA_FULL,             // provider type
							   CRYPT_VERIFYCONTEXT))                        // flag values
		{
			break;
		}

		// Derive a Key
		if(!DeriveKey(hCryptProv, szPassword, &hKey))
		{
			break;
		}

		// Encrypt the file
		OFSTRUCT ofStr;
		ofStr.cBytes = sizeof(OFSTRUCT);
		HANDLE hFile = (HANDLE)OpenFile(szPath, &ofStr, OF_READWRITE);

		if(NULL == hFile)
			break;

		DWORD dwSizeLow(0), dwSizeHigh(0);
		dwSizeLow = GetFileSize(hFile, &dwSizeHigh);

		BYTE *pbData = new BYTE[dwSizeLow*4];

		if(NULL == pbData)
			break;

		DWORD dwBytesRead(0);
		ReadFile(hFile, pbData, dwSizeLow, &dwBytesRead, NULL);

		CryptDecrypt(hKey, NULL, TRUE, 0, pbData, &dwSizeLow);

		SetFilePointer(hFile, 0, NULL, FILE_BEGIN);

		DWORD dwBytesWritten(0);
		WriteFile(hFile, pbData, dwSizeLow, &dwBytesWritten, NULL);

		CloseHandle(hFile);

		bRet = true;
		break;
	}	

	// Destroy the session key.
	if(hKey)
	{
		CryptDestroyKey(hKey);
	}

	// Release the provider handle.
	if(hCryptProv)
	{
	   CryptReleaseContext(hCryptProv, 0);
	}

	return bRet;
}

VICTIM_DLL_API bool DeriveKey(HCRYPTPROV hCryptProv, char *szPassword, HCRYPTKEY *hKey)
{
	bool bRet = false;
	HCRYPTHASH hHash = NULL;

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
						 (BYTE *)szPassword, 
						 strlen(szPassword), 
						 0))
		{
			break;
		}

		if(!CryptDeriveKey(hCryptProv, 
						   CALG_RC4, 
						   hHash, 
						   CRYPT_EXPORTABLE, 
						   hKey)) 
		{	
			break;
		}

		bRet = true;
		break;
	}

	// We're done with the keys.  Release the context
	if(hHash)
	{
		CryptDestroyHash(hHash);
	}

	return true;
}

