// Victim_EXEDlg.cpp : implementation file
//

#include "stdafx.h"
#include "Victim_DLL.h"
#include "Victim_EXE.h"
#include "Victim_EXEDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


CVictim_EXEDlg::CVictim_EXEDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CVictim_EXEDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CVictim_EXEDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CVictim_EXEDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDC_ENCRYPT, &CVictim_EXEDlg::OnBnClickedEncrypt)
	ON_BN_CLICKED(IDC_DECRYPT, &CVictim_EXEDlg::OnBnClickedDecrypt)
	ON_BN_CLICKED(IDC_BROWSEFILE, &CVictim_EXEDlg::OnBnClickedBrowsefile)
END_MESSAGE_MAP()


// CVictim_EXEDlg message handlers

BOOL CVictim_EXEDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	CString csTitle;

	csTitle.Format(_T("ProcessID = %ld"), GetCurrentProcessId());

	SetDlgItemText(IDC_PROCID, csTitle);

	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CVictim_EXEDlg::OnPaint()
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
HCURSOR CVictim_EXEDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CVictim_EXEDlg::OnBnClickedEncrypt()
{
	GetDlgItemText(IDC_FILENAME, m_csFile);
	GetDlgItemText(IDC_PASSWORD, m_csPasswd);

	MyEncryptFile(ATL::CT2A(m_csFile.GetBuffer()), ATL::CT2A(m_csPasswd.GetBuffer()));
}

void CVictim_EXEDlg::OnBnClickedDecrypt()
{
	GetDlgItemText(IDC_FILENAME, m_csFile);
	GetDlgItemText(IDC_PASSWORD, m_csPasswd);

	MyDecryptFile(ATL::CT2A(m_csFile.GetBuffer()), ATL::CT2A(m_csPasswd.GetBuffer()));
}

void CVictim_EXEDlg::OnBnClickedBrowsefile()
{
	CFileDialog dlgFile(TRUE);
	
	dlgFile.DoModal();
	m_csFile = dlgFile.GetPathName();
	
	SetDlgItemText( IDC_FILENAME, m_csFile.GetBuffer());
}
