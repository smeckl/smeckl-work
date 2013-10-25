class Instruction extends Line
{
	enum BranchType
	{
		JUMP,
		JUMP_EQ,
		JUMP_NEQ
	};

	private String m_strInstruction;
	private Argument m_argument1;
	private Argument m_argument2;
	private Argument m_argument3;

	private boolean m_bIsJump;
	private BranchType m_branchType;

	public Instruction(Line.Type type, String strInstruction, Argument argument1, Argument argument2, Argument argument3)
	{
		super(type, buldInstructionString(strInstruction,
						  argument1, argument2, argument3) );

		setInstruction(strInstruction);
		setArgument1(argument1);
		setArgument2(argument2);
		setArgument3(argument3);
		setIsJump(false);
	}

	public Instruction(Instruction right)
	{
		super(right);		
		setInstruction(right.getInstruction());
		setArgument1(right.getArgument1());
		setArgument2(right.getArgument2());
		setArgument3(right.getArgument3());
		setIsJump(right.getIsJump());
	}


	public void setInstruction(String strInstruction)
	{
		m_strInstruction = strInstruction;
	}

	public String getInstruction()
	{
		return m_strInstruction;
	}

	public void setArgument1(Argument argument1)
	{
		m_argument1 = argument1;
	}

	public Argument getArgument1()
	{
		return m_argument1;
	}

	public void setArgument2(Argument argument2)
	{
		m_argument2 = argument2;
	}

	public Argument getArgument2()
	{
		return m_argument2;
	}

	public void setArgument3(Argument argument3)
	{
		m_argument3 = argument3;
	}

	public Argument getArgument3()
	{
		return m_argument3;
	}

	public void setIsJump(boolean bIsJump)
	{
		m_bIsJump = bIsJump;
	}

	public boolean getIsJump()
	{
		return m_bIsJump;
	}

	public void setBranchType(BranchType branchType)
	{
		m_branchType = branchType;
	}

	public BranchType getBranchType()
	{
		return m_branchType;
	}

	public String getJumpTarget()
	{
		String strTarget = "";

		if(BranchType.JUMP == getBranchType())
			strTarget = getArgument1().getOrigValue();
		else
			strTarget = getArgument2().getOrigValue();

		return strTarget;
	}

	private static String buldInstructionString(String strInstruction, Argument argument1, Argument argument2, Argument argument3)
	{
		String strOut = "\t" + strInstruction;

		if(null != argument1)
			strOut += " " + argument1;

		if(null != argument2)
			strOut += "," + argument2;	

		if(null != argument3)
			strOut += "," + argument3;	

		return strOut;
	}
};
