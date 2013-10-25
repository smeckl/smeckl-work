import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ListIterator;

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
	private Set<BasicBlock> m_predecessors = new Set<BasicBlock>();
	private Set<BasicBlock> m_successors = new Set<BasicBlock>();
	private Set<BasicBlock> m_dominators = new Set<BasicBlock>();

	private Set<BasicBlock> m_iDom = new Set<BasicBlock>();
	private Set<BasicBlock> m_iDomChildren = new Set<BasicBlock>();
	private Set<BasicBlock> m_dominanceFrontier = new Set<BasicBlock>();

	private Set<PhiFunction> m_phiFunctions = new Set<PhiFunction>();

	private Hashtable<String, Integer> m_varRefs = new Hashtable<String, Integer>();
	private Hashtable<String, String> m_varMap = new Hashtable<String,String>();
	
	private LiveSet m_liveIn = new LiveSet();
	private LiveSet m_liveOut = new LiveSet();

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

	public Set<BasicBlock> getPredecessors()
	{
		return m_predecessors;
	}

	public void addSuccessor(BasicBlock block)
	{
		m_successors.add(block);
	}

	public Set<BasicBlock> getSuccessors()
	{
		return m_successors;
	}

	public void setDominators(Set<BasicBlock> dominators)
	{
		m_dominators = dominators;
	}
	
	public Set<BasicBlock> getDominators()
	{
		return m_dominators;
	}

	public void setIDom(Set<BasicBlock> iDom)
	{
		m_iDom = iDom;
	}

	public Set<BasicBlock> getIDom()
	{
		return m_iDom;
	}

	public void setDominanceFrontier(Set<BasicBlock> dominanceFrontier)
	{
		m_dominanceFrontier = dominanceFrontier;
	}

	public Set<BasicBlock> getDominanceFrontier()
	{
		return m_dominanceFrontier;
	}

	public void setPhiFunctions(Set<PhiFunction> phiFunctions)
	{
		m_phiFunctions = phiFunctions;
	}

	public Set<PhiFunction> getPhiFunctions()
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

	public void setIDomChildren(Set<BasicBlock> iDomChildren)
	{
		m_iDomChildren = iDomChildren;
	}

	public Set<BasicBlock> getIDomChildren()
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

	public LiveSet getLiveIn() 
	{
		return m_liveIn;
	}	
	
	private void mapRegistersToVariables()
	{
		Hashtable<String,String> prevMapRegToVar = null;
		
		for(int i = 0; i < getLines().getNumItems(); i++)
		{
			Line line = getLines().getItemAtIndex(i);			
			
			if(Line.Type.INSTRUCTION == line.getType())
			{
				Instruction instr = (Instruction)line;
				
				// Copy previous map, if available
				if(null != prevMapRegToVar)
					copyHashStringToString(instr.getMapRegToValue(), prevMapRegToVar);
				
				// On a load word, map the register to the variable
				if(0 == instr.getInstruction().compareTo("lw")
				   || 0 == instr.getInstruction().compareTo("li"))
				{
					instr.getMapRegToValue().put(instr.getArgument1().getName(), instr.getArgument2().getName());
				}
				// On a store word, remove the register mapping
				else if(0 == instr.getInstruction().compareTo("sw"))
				{
					//instr.getMapRegToValue().remove(instr.getArgument1().getName());
					//instr.getMapRegToValue().put(instr.getArgument1().getName(), instr.getArgument2().getName());
				}
				
				prevMapRegToVar = instr.getMapRegToValue();				
			}
		}
	}
	
	private void copyHashStringToString(Hashtable<String, String> dest, Hashtable<String, String> src)
    {
            for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
            {
                    String str = e.nextElement();
                    dest.put(str, src.get(str));
            }
    }
	
	public boolean calculateLiveIN()
	{
		boolean bChanges = false;
		
		mapRegistersToVariables();
		
		// The last lastLive set is a copy of liveOUT
        LiveSet lastLive = new LiveSet(getLiveOut());
		
		for(int i = getLines().getNumItems() - 1; i >= 0; i--)
		{
			Line line = getLines().getItemAtIndex(i);
            
            // Copy the last live set
            line.getLiveSet().copy(lastLive);
			
			if(Line.Type.INSTRUCTION == line.getType())
			{
				Instruction instr = (Instruction)line;
				
				Argument arg2 = instr.getArgument2();
				
				// If this is a SW instruction, then remove the 
				// target variable from the live set
				if(0 == instr.getInstruction().compareTo("sw"))
				{
					// Remove the mapping to the variable
					line.getLiveSet().remove(arg2.getName());
					
					// See if Argument1 is mapped to a variable
					if(instr.getMapRegToValue().containsKey(instr.getArgument1().getName()))
					{
						String strVal = instr.getMapRegToValue().get(instr.getArgument1().getName());
						
						// Mark the variable as live
						line.getLiveSet().add(strVal);
					}
				}
				// If this is a LW instruction, then add the loaded
				// variable to the live set
				else if(0 == instr.getInstruction().compareTo("lw")
						|| 0 == instr.getInstruction().compareTo("li"))		
				{
					line.getLiveSet().add(arg2.getName());
				}
				else if(!instr.getIsJump() && 0 != instr.getInstruction().compareTo("syscall")
                        && 0 != instr.getInstruction().compareTo("move")
                        && 0 != instr.getInstruction().compareTo("li"))
				{
					// See if Argument2 is mapped to a variable
					if(instr.getMapRegToValue().containsKey(instr.getArgument2().getName()))
					{
						String strVal = instr.getMapRegToValue().get(instr.getArgument2().getName());
						
						// Mark the variable as live
						line.getLiveSet().add(strVal);
					}
					
					// See if Argument3 is mapped to a variable
					if(instr.getMapRegToValue().containsKey(instr.getArgument3().getName()))
					{
						String strVal = instr.getMapRegToValue().get(instr.getArgument3().getName());
						
						// Mark the variable as live
						line.getLiveSet().add(strVal);
					}
				}
				else if(0 == instr.getInstruction().compareTo("move"))
				{
					// See if Argument2 is mapped to a variable
					if(instr.getMapRegToValue().containsKey(instr.getArgument2().getName()))
					{
						String strVal = instr.getMapRegToValue().get(instr.getArgument2().getName());
						
						// Mark the variable as live
						line.getLiveSet().add(strVal);
					}
				}
			}
            
            lastLive = line.getLiveSet();
		}
		
		getLiveIn().copy(lastLive);
		
		return bChanges;
	}

	public void setLiveIn(LiveSet liveIn) 
	{
		m_liveIn = liveIn;
	}

	public LiveSet getLiveOut() 
	{
		return m_liveOut;
	}

	public void setLiveOut(LiveSet liveOut) 
	{
		m_liveOut = liveOut;
	}
	
	// This method has to handle two use cases:
	//    1) This is the final block: liveOUT = {}
	//    2) This is a normal block:  liveOUT = union(successors' liveIN)
	public boolean calculateLiveOUT()
	{
		boolean bChanges = false;
		
		// Initialize liveOUT to {}
		Set<String> liveOUT = getLiveOut();
		liveOUT.clear();

		return bChanges;
	}
	
	public boolean optimizeRegisterAllocation(RegAllocContext context, int nMaxRegisters)
	{
		boolean bChanges = false;
		
        ArrayList<Instruction> removeList = new ArrayList<Instruction>();
		
		// Iterate through instructions
        for(int i = 0; i < getLines().getNumItems(); i++)
        {			
        	Line line = getLines().getItemAtIndex(i);
      
        	Instruction nextInstr = getNextInstr(getLines(), i);
        	
			if(Line.Type.INSTRUCTION == line.getType())
			{
				Instruction instr = (Instruction)line;
				
				// If this is a LI instruction
				if(0 == instr.getInstruction().compareTo("li"))		
				{
					// If the register is $v0, do nothing
					if(0 != instr.getArgument1().getName().compareTo("$v0"))
					{
						// Allocate Free register for dest
						String strFreeReg = findFirstFreeReg(context, instr.getLiveSet(), nMaxRegisters);
	                	
	                	// Map old register to this instruction
	                	context.mapRegToInstruction.put(instr.getArgument1().getName(), instr);
	                	
	                	context.regMap.put(strFreeReg, instr.getArgument2().getName());
						context.variableLocs.put(instr.getArgument2().getName(), strFreeReg);
						
	                	// Rewrite Argument1 with new register
	                	instr.getArgument1().setOrigName(strFreeReg);
					}
				}
				// If this is a LW instruction
				else if(0 == instr.getInstruction().compareTo("lw"))		
				{
					// If the variable is in a register, then remove this instruction
					if(0 != context.variableLocs.get(instr.getArgument2().getName()).compareTo("m"))
					{	
						Line nextLine = getLines().getItemAtIndex(i+1);
						Instruction next = null;
						
						if(Line.Type.INSTRUCTION == nextLine.getType())
							next = (Instruction)nextLine;
						
						if(null == next || 0 != next.getInstruction().compareTo("sw"))
							removeList.add(instr);
					}
					// else if the variable is live, 
					else //if(instr.getLiveSet().contains(instr.getArgument2().getName()))
					{
						// 1) allocate free register
						String strFreeReg = findFirstFreeReg(context, instr.getLiveSet(), nMaxRegisters);
						
						// 2) patch instruction
						if(null != strFreeReg)
						{
							context.regMap.put(strFreeReg, instr.getArgument2().getName());
							context.variableLocs.put(instr.getArgument2().getName(), strFreeReg);
							
							instr.getArgument1().setOrigName(strFreeReg);
						}
					}
				}
				// If this is a SW instruction
				else if(0 == instr.getInstruction().compareTo("sw"))
				{
					// If the register is $v0, do nothing
					if(0 != instr.getArgument1().getName().compareTo("$v0"))
					{
						// If the instruction that is mapped to the register is an LW, then use the register
						// from that instruction.
						Line prevLine = getLines().getItemAtIndex(i-1);
						Instruction prevInstr = null;
						
						if(Line.Type.INSTRUCTION == prevLine.getType())
							prevInstr = (Instruction)prevLine;
					
								//context.mapRegToInstruction.get(instr.getArgument1().getOrigName());
						if(null != prevInstr && 0 == prevInstr.getInstruction().compareTo("lw"))
						{
							String strReg = prevInstr.getArgument1().getOrigName();
							instr.getArgument1().setOrigName(strReg);
							
							context.regMap.put(strReg, instr.getArgument2().getName());
							context.variableLocs.put(instr.getArgument2().getName(), strReg);
						}
						// If the variable is live
						else if(null != nextInstr && nextInstr.getLiveSet().contains(instr.getArgument2().getName()))
						{
							// Add the variable to the live set
							instr.getLiveSet().add(instr.getArgument2().getName());
							
							String strOldReg = instr.getArgument1().getOrigName();
							String strNewReg = "";
							
							// If the variable is already in a register
							if(0 != context.variableLocs.get(instr.getArgument2().getName()).compareTo("m"))
							{
								// Patch current instruction to use that register
								strNewReg = context.variableLocs.get(instr.getArgument2().getName());
								
								instr.getArgument1().setOrigName(strNewReg);
							}
							// Else
							else
							{
								// Allocate free register
								strNewReg = findFirstFreeReg(context, instr.getLiveSet(), nMaxRegisters);
								
								// patch instruction
								if(null != strNewReg)
								{
									context.regMap.put(strNewReg, instr.getArgument2().getName());
									context.variableLocs.put(instr.getArgument2().getName(), strNewReg);
									
									instr.getArgument1().setOrigName(strNewReg);
								}
							}
							
							// Patch register's last-used instruction
							Instruction oldInstr = context.mapRegToInstruction.get(strOldReg);
							
							if(null != oldInstr)
								oldInstr.getArgument1().setOrigName(strNewReg);
						}
						// If the previous instruction is an operation...
						else if(null != prevInstr && prevInstr.isOperation())
						{
							String strReg = prevInstr.getArgument1().getOrigName();
							instr.getArgument1().setOrigName(strReg);
							
							// Remove the mapping from the register to the instruction
							context.mapRegToInstruction.remove(prevInstr.getArgument1().getName());
							
//							context.regMap.put(strReg, instr.getArgument2().getName());
//							context.variableLocs.put(instr.getArgument2().getName(), strReg);
						}
						// If the register is mapped to a variable held in a register
						else if(instr.getMapRegToValue().containsKey(instr.getArgument1().getName()))
						{
							String strOldReg = instr.getArgument1().getName();
							
							String strVar = instr.getMapRegToValue().get(instr.getArgument1().getName());
							
							String strNewReg = context.variableLocs.get(strVar);
							
							if(null != strNewReg && 0 != strNewReg.compareTo("m"))
							{
								// Update the register
								instr.getArgument1().setOrigName(strNewReg);
								
								// Find last instruction that referenced old reg
								Instruction oldInstr = context.mapRegToInstruction.get(strOldReg);
								
								if(null != oldInstr)
								{
									oldInstr.getArgument1().setOrigName(strNewReg);
								}
							}
						}
						else
						// Use temp
						{
							String strOldReg = instr.getArgument1().getName();
							
							// Allocate temp register for dest
		                	String strTempReg = getTempRegister(nMaxRegisters);
		                	
		                	// Update the register
							instr.getArgument1().setOrigName(strTempReg);
							
							// Find last instruction that referenced old reg
							Instruction oldInstr = context.mapRegToInstruction.get(strOldReg);
							
							if(null != oldInstr)
							{
								oldInstr.getArgument1().setOrigName(strTempReg);
							}
						}
					}
				}
				// If it is the NEG function
				else if(0 == instr.getInstruction().compareTo("neg"))
                {
					remapRegister(context, instr, instr.getArgument1());
                }
				// If this is another non-jump instruction
                else if(!instr.getIsJump() && 0 != instr.getInstruction().compareTo("syscall")
                        && 0 != instr.getInstruction().compareTo("move"))
                {
                	// Allocate temp register for dest
                	String strTempReg = getTempRegister(nMaxRegisters);
                	
                	// Map old register to this instruction
                	context.mapRegToInstruction.put(instr.getArgument1().getName(), instr);
                	
                	// Rewrite Argument1 with temp register
                	instr.getArgument1().setOrigName(strTempReg);                                	
                	
                	remapRegister(context, instr, instr.getArgument2());
                	remapRegister(context, instr, instr.getArgument3());
                }
                else if(0 == instr.getInstruction().compareTo("move"))
                {
                	remapRegister(context, instr, instr.getArgument2());
                }
				// If this is a jump instruction
                else if(instr.getIsJump() && Instruction.BranchType.JUMP != instr.getBranchType())
                {
                	String strTempReg = getTempRegister(nMaxRegisters);
	                	
                	// Rewrite Argument1 with temp register
                	instr.getArgument1().setOrigName(strTempReg);
                }
			}
                
            deallocateNonLiveVars(line, context, nMaxRegisters);
		}
        
        // Remove lines
        for(int i = 0; i < removeList.size(); i++)
        {
        	getLines().removeLine(removeList.get(i));
        }
        
		return bChanges;
	}
	
	private String trimVar(String strVar)
	{
		String strOut = "";
		
		int nInd = strVar.lastIndexOf('_');
		
		strOut = strVar.substring(0, nInd);
		
		return strOut;
	}
	
	private Instruction getNextInstr(LineList list, int i)
	{
		Line line = null;
		Instruction instr = null;
		
		if(i < list.getNumItems()-1)
			line = list.getItemAtIndex(i+1);
		
		if(null != line && Line.Type.INSTRUCTION == line.getType())
    	{
    		instr = (Instruction)line;
    	}
		
		return instr;
	}
	
	private String findFirstFreeReg(RegAllocContext context, LiveSet liveVars, int nMaxRegisters)
	{
		String strFree = null;
		
		// Look through register set for unassigned register
		for(int i = 0; i < nMaxRegisters - 1; i++) // Reserve last register for temp usage
		{
			String strReg = "$t" + i;
			
			// If the register is not in the in-use map, then we're done
			if(context.regMap.get(strReg).isEmpty())
			{
				strFree = strReg;
				
				// The register will be marked on use
				break;
			}
		}
		
		return strFree;
	}
	
	private void remapRegister(RegAllocContext context, Instruction instr, Argument arg)
	{
		// If Argument2() is mapped to a variable
		if(instr.getMapRegToValue().containsKey(arg.getName()))
		{
			String strVar = instr.getMapRegToValue().get(arg.getName());
			
			// Re-write argument 2 with re-mapped register
			if(!strVar.isEmpty())
			{
				String strRemapReg = context.variableLocs.get(strVar);
				
				if(null != strRemapReg && 0 != strRemapReg.compareTo("m"))
				{
	    			arg.setName(strRemapReg);
	            	arg.setOrigName(strRemapReg);
				}
			}
		}
	}
	
	private void deallocateNonLiveVars(Line line, RegAllocContext context, int nMaxRegisters)
	{
		// De-allocate non-live variables
        for(int i = 0; i < nMaxRegisters-1; i++)
        {
        	String strReg = "$t" + i;
        	
        	// See if it is live in the next instruction.
        	
        	// Get the variable the register is mapped to
			String strVar = context.regMap.get(strReg);
			
			// If it is not live...
			if(null != strVar && !line.getLiveSet().contains(strVar))
			{  					
				// Remove mapping from old variable to register
				context.regMap.put(strReg, "");
				context.variableLocs.put(strVar, "m");
			}
        }
	}
	
	private String getTempRegister(int nMaxRegisters)
	{
		String strRet = "$t" + (nMaxRegisters - 1);
		
		return strRet;
	}
	
	private void updateInstruction(Instruction instr, String strFreeReg)
	{
		if((0 == instr.getInstruction().compareTo("lw"))
				|| (0 == instr.getInstruction().compareTo("li")))
		{
			instr.getArgument1().setName(strFreeReg);
			instr.getArgument1().setOrigName(strFreeReg);
			
		}
		// If this is another non-jump instruction
        else if(!instr.getIsJump() && 0 != instr.getInstruction().compareTo("syscall")
                && 0 != instr.getInstruction().compareTo("move"))
        {
        	instr.getArgument1().setName(strFreeReg);
			instr.getArgument1().setOrigName(strFreeReg);
        }
	}
};
