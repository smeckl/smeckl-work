// Victim_EXEDlg.h : header file
//

#pragma once


// CVictim_EXEDlg dialog
class CVictim_EXEDlg : public CDialog
{
// Construction
public:
	CVictim_EXEDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	enum { IDD = IDD_VICTIM_EXE_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	CString m_csFile;
	CString m_csPasswd;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnBnClickedCheckstringlen();
	afx_msg void OnStnClickedProcid();
	afx_msg void OnBnClickedEncrypt();
	afx_msg void OnBnClickedDecrypt();
	afx_msg void OnBnClickedBrowsefile();
};
