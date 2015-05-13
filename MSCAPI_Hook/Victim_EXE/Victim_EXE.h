// Victim_EXE.h : main header file for the PROJECT_NAME application
//

#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"		// main symbols


// CVictim_EXEApp:
// See Victim_EXE.cpp for the implementation of this class
//

class CVictim_EXEApp : public CWinApp
{
public:
	CVictim_EXEApp();

// Overrides
	public:
	virtual BOOL InitInstance();

// Implementation

	DECLARE_MESSAGE_MAP()
};

extern CVictim_EXEApp theApp;