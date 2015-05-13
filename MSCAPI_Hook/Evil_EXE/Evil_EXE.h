// Evil_EXE.h : main header file for the PROJECT_NAME application
//

#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"		// main symbols


// CEvil_EXEApp:
// See Evil_EXE.cpp for the implementation of this class
//

class CEvil_EXEApp : public CWinApp
{
public:
	CEvil_EXEApp();

// Overrides
	public:
	virtual BOOL InitInstance();

// Implementation

	DECLARE_MESSAGE_MAP()
};

extern CEvil_EXEApp theApp;