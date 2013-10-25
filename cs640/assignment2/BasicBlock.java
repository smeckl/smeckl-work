import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

class BasicBlock
{
	private static boolean bDebug = false;

	public static void __debugPrint(Object str)
	{
		if(bDebug)
			System.out.print(str);
	}

	public static void __debugPrintln(Object str)
	{
		if(bDebug)
			System.out.println(str);
	}

	private String m_strLabel;
	private int m_nBlockNum;

	private LineList m_lines = new LineList();
	private ArrayList<BasicBlock> m_predecessors = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_successors = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_dominators = new ArrayList<BasicBlock>();

	private ArrayList<BasicBlock> m_iDom = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_iDomChildren = new ArrayList<BasicBlock>();
	private ArrayList<BasicBlock> m_dominanceFrontier = new ArrayList<BasicBlock>();

	private ArrayList<PhiFunction> m_phiFunctions = new ArrayList<PhiFunction>();

	private Hashtable<String, Integer> m_varRefs = new Hashtable<String, Integer>();
	private Hashtable<String, String> m_varMap = new Hashtable<String,String>();

	private boolean m_bVisited = false;
	private boolean m_bPhiVisited = false;

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

	public void setPhiFunctions(ArrayList<PhiFunction> phiFunctions)
	{
		m_phiFunctions = phiFunctions;
	}

	public ArrayList<PhiFunction> getPhiFunctions()
	{
		return m_phiFunctions;
	}

	// Updates this block's Phi Function
	public void updatePhiFunction(Argument arg, Symbol sym)
	{
		for(int i = 0; i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(0 == phiFunc.getVariable().getName().compareTo(arg.getOrigName()))
			{
				__debugPrintln("Updating Phi Func: " + arg.getOrigName() + " to " + sym.getIntegerValue());

				phiFunc.updateVariable(arg.getName(), new Symbol(sym));

				break;
			}
		}
	}

	public void updatePhiFunctionCurName(String strCurName, Symbol sym)
	{
		for(int i = 0; i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(phiFunc.varInMergeList(strCurName))
			{
				__debugPrintln("Updating Phi Func: " + phiFunc.getVariable().getName() + " to " + sym.getIntegerValue());

				phiFunc.updateVariable(strCurName, new Symbol(sym));

				break;
			}
		}
	}

	public PhiFunction getPhiFunctionMatchingCurName(String strCurName)
	{
		PhiFunction ret = null;

		for(int i = 0; i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(phiFunc.varInMergeList(strCurName))
			{
				__debugPrintln("Found Phi Func : " + phiFunc.getVariable().getName() + " matching " + strCurName);

				ret =  phiFunc;

				break;
			}
		}

		return ret;
	}

	public void removePhiFuncVariable(String origName, String varName)
	{
		for(int i = 0; i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(0 == phiFunc.getVariable().getName().compareTo(origName))
			{
				__debugPrintln("Removing Phi Func ref: " + origName + " to " + varName);

				phiFunc.deleteVariable(varName);
			}
		}
	}
	
	public boolean hasPhiFunc(String strName)
	{
		boolean bRet = false;

		for(int i = 0; !bRet && i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(0 == phiFunc.getVariable().getName().compareTo(strName))
			{
				bRet = true;
			}
		}

		return bRet;
	}

	public boolean isConstPhiFunc(String strName)
	{
		boolean bRet = false;

		for(int i = 0; !bRet && i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(0 == phiFunc.getName().compareTo(strName))
			{
				if(phiFunc.isConst())
					bRet = true;
			}
		}

		return bRet;
	}

	public int getPhiFunctionConstValue(String strName)
	{
		int nRet = 0;

		for(int i = 0; i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(0 == phiFunc.getVariable().getName().compareTo(strName))
			{
				if(phiFunc.isConst())
				{
					nRet = phiFunc.getConstValue();
					break;
				}
			}
		}

		return nRet;
	}

	public Symbol getPhiFunctionSymbol(String strName)
	{
		Symbol symRet = null;

		for(int i = 0; i < getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = getPhiFunctions().get(i);

			if(0 == phiFunc.getVariable().getName().compareTo(strName))
			{
				__debugPrintln("Found Symbol for " + strName);
				symRet = new Symbol("" + phiFunc.getConstValue(), Symbol.SymbolType.CONSTANT, Symbol.DataType.INTEGER);
				symRet.setIntegerValue(phiFunc.getConstValue());
				break;
			}
		}

		return symRet;
	}

	public void setIDomChildren(ArrayList<BasicBlock> iDomChildren)
	{
		m_iDomChildren = iDomChildren;
	}

	public ArrayList<BasicBlock> getIDomChildren()
	{
		return m_iDomChildren;
	}

	public int getVariableRefCount(String strVar)
	{
		int nRet = 0;

		Integer i = m_varRefs.get(strVar);

		if(null != i)
			nRet = i.intValue();

		return nRet;
	}

	public void addVariableRef(String strVar)
	{
		Integer oldVal = m_varRefs.get(strVar);
		Integer newVal = null;

		if(null != oldVal)
			newVal = new Integer(oldVal.intValue() + 1);
		else
			newVal = new Integer(1);
	
		m_varRefs.put(strVar, newVal);		
	}

	public void subVariableRef(String strVar)
	{
		Integer oldVal = m_varRefs.get(strVar);
		Integer newVal = null;

		if(null != oldVal)
			if(oldVal.intValue() > 0)
				newVal = new Integer(oldVal.intValue() - 1);
			else
				newVal = new Integer(0);
		else
			newVal = new Integer(1);
	
		m_varRefs.put(strVar, newVal);
	}

	public Hashtable<String,Integer> getVariableRefList()
	{
		return m_varRefs;
	}

	public Hashtable<String,String> getVariableMap()
	{
		return m_varMap;
	}

	public void setVisited(boolean bVisited)
	{
		m_bVisited = bVisited;
	}

	public boolean getVisited()
	{
		return m_bVisited;
	}

	public void setPhiVisited(boolean bVisited)
	{
		m_bPhiVisited = bVisited;
	}

	public boolean getPhiVisited()
	{
		return m_bPhiVisited;
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
