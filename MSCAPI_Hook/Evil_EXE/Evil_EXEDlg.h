// Evil_EXEDlg.h : header file
//

#pragma once


// CEvil_EXEDlg dialog
class CEvil_EXEDlg : public CDialog
{
// Construction
public:
	CEvil_EXEDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	enum { IDD = IDD_EVIL_EXE_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();

	void EnableSeDebugPrivilege();

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnBnClickedInstallhooks();
};
