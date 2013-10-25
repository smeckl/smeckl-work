import java.util.ArrayList;

class BasicBlock
{
	private String m_strLabel;
	private int m_nBlockNum;

	private LineList m_lines = new LineList();
	private ArrayList<BasicBlock> m_predecessors = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_successors = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_dominators = new ArrayList<BasicBlock>();

	private ArrayList<BasicBlock> m_iDom = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_dominanceFrontier = new ArrayList<BasicBlock>();

	private boolean m_bVisited = false;

	public BasicBlock(String strLabel, int nBlockNum)
	{
		setLabel(strLabel);
		setBlockNum(nBlockNum);	
	}

	public void setLabel(String strLabel)
	{
		m_strLabel = strLabel;
	}

	public String getLabel()
	{
		return m_strLabel;
	}

	public void setBlockNum(int nBlockNum)
	{
		m_nBlockNum = nBlockNum;
	}

	public int getBlockNum()
	{
		return m_nBlockNum;
	}

	public void addLine(Line line)
	{
		m_lines.addItem(line);
	}

	public LineList getLines()
	{
		return m_lines;
	}		

	public void addPredecessor(BasicBlock block)
	{
		m_predecessors.add(block);
	}

	public ArrayList<BasicBlock> getPredecessors()
	{
		return m_predecessors;
	}

	public void addSuccessor(BasicBlock block)
	{
		m_successors.add(block);
	}

	public ArrayList<BasicBlock> getSuccessors()
	{
		return m_successors;
	}

	public void setDominators(ArrayList<BasicBlock> dominators)
	{
		m_dominators = dominators;
	}
	
	public ArrayList<BasicBlock> getDominators()
	{
		return m_dominators;
	}

	public void setIDom(ArrayList<BasicBlock> iDom)
	{
		m_iDom = iDom;
	}

	public ArrayList<BasicBlock> getIDom()
	{
		return m_iDom;
	}

	public void setDominanceFrontier(ArrayList<BasicBlock> dominanceFrontier)
	{
		m_dominanceFrontier = dominanceFrontier;
	}

	public ArrayList<BasicBlock> getDominanceFrontier()
	{
		return m_dominanceFrontier;
	}

	public void setVisited(boolean bVisited)
	{
		m_bVisited = bVisited;
	}

	public boolean getVisited()
	{
		return m_bVisited;
	}

	public boolean isLineInBlock(Line line)
	{
		boolean bFound = false;

		for(int i = 0; i < m_lines.getNumItems(); i++)
		{
			Line target = m_lines.getItemAtIndex(i);
	
			if(line.getLineNum() == target.getLineNum())
			{
				bFound = true;
				break;
			}		
		}

		return bFound;
	}
};
