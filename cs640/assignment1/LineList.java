import java.util.Vector;

class LineList
{
	private Vector<Line> m_lineList;
	
	private int m_nMaxLineNum;

	public LineList()
	{
		m_lineList = new Vector<Line>();

		m_nMaxLineNum = 1;
	}

	public void addItem(Line instr)
	{
		instr.setLineNum(m_nMaxLineNum++);
		m_lineList.add(instr);
	}

	public int getNumItems()
	{
		return m_lineList.size();
	}

	public Line getItemAtIndex(int index)
	{
		return m_lineList.get(index);
	}
}

