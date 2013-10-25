import java.util.ArrayList;
import java.util.ListIterator;

class LineList
{
	private ArrayList<Line> m_lineList;
	
	private int m_nMaxLineNum;

	public LineList()
	{
		m_lineList = new ArrayList<Line>();

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

	public void setItemAtIndex(int index, Line line)
	{
		m_lineList.set(index, line);
	}

	public void removeLine(Line line)
	{
		m_lineList.remove(line);
	}

	public void clear()
	{
		m_lineList.clear();
	}

	public ListIterator<Line> getListIterator()
	{
		return m_lineList.listIterator();
	}
}

