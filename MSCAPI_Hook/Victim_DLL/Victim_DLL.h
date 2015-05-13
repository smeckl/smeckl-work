// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the VICTIM_DLL_EXPORTS
// symbol defined on the command line. this symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// VICTIM_DLL_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.
#ifdef VICTIM_DLL_EXPORTS
#define VICTIM_DLL_API __declspec(dllexport)
#else
#define VICTIM_DLL_API __declspec(dllimport)
#endif

#include "Wincrypt.h"

extern "C"
{
VICTIM_DLL_API bool MyEncryptFile(char *szPath, char *szPassword);
VICTIM_DLL_API bool MyDecryptFile(char *szPath, char *szPassword);
VICTIM_DLL_API bool DeriveKey(HCRYPTPROV hCryptProv, char *szPassword, HCRYPTKEY *hKey);
}