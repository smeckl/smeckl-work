import java.util.ArrayList;
import java.util.Stack;
import java.util.Hashtable;
import java.util.Enumeration;

class CodeAnalyzer
{
	static boolean bDebug = false;

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

	class SSAContext
	{
		private Hashtable<String, Stack<String>> m_varStacks = new Hashtable<String, Stack<String>>();

		private Hashtable<String, Integer> m_varCounters = new Hashtable<String, Integer>();

		public SSAContext(ArrayList<Variable> varList)
		{
			for(int i = 0; i < varList.size(); i++)
			{
				m_varStacks.put(varList.get(i).getName(), new Stack<String>());
				m_varCounters.put(varList.get(i).getName(), 0);
			}
		}

		public Stack<String> stack(String varName)
		{
			return m_varStacks.get(varName);
		}

		public int getCounterVal(String varName)
		{
			return m_varCounters.get(varName);
		}

		public void setCounterVal(String varName, int nVal)
		{
			m_varCounters.put(varName, nVal);
		}
	};
	
	

	private LineList m_lineList;
	private ArrayList<BasicBlock> m_blockList = new ArrayList<BasicBlock>();
	private BasicBlock m_header = new BasicBlock("", -1);

	private ArrayList<Variable> m_variableList;

	public CodeAnalyzer(LineList lineList, ArrayList<Variable> varList)
	{
		setLineList(lineList);
		setVariableList(varList);
	}

	public void setLineList(LineList lineList)
	{
		m_lineList = lineList;
	}

	public LineList getLineList()
	{
		return m_lineList;
	}

	public void setVariableList(ArrayList<Variable> variableList)
	{
		m_variableList = variableList;
	}

	public ArrayList<Variable> getVariableList()
	{
		return m_variableList;
	}

	public void doWork()
	{
		analyzeCode();
		PrintOptimizedCode();
	}	

	public void runDiagnostics()
	{
		buildCFG();
        buildIDomTree();
        buildDominanceFrontiers();
        calcStaticSingleAssignment();
        calculateLiveSets();

		PrintCFG();
		printIDom();
		printDominanceFrontiers();
		printPhiFunctions();
		printLiveSets();
	}

	private void analyzeCode()
    {  
		boolean bChanges = true;
		
		// The following metadata need to be calculated prior to optimization
		buildCFG();
        buildIDomTree();
        buildDominanceFrontiers();
        calcStaticSingleAssignment();
        calculateLiveSets();
		
		while(bChanges)
		{
			bChanges = optimizeCode();
		}
    }

	private void buildCFG()
	{
		markLeaders();
		buildBlocks();
		buildGraph();
	}

	private void buildBlocks()
	{
		// Build header block with instructions up to first leader
		int i = 0;

		boolean bFoundFirstLeader = false;

		while(!bFoundFirstLeader && i < m_lineList.getNumItems())
		{
			Line line = m_lineList.getItemAtIndex(i);

			if(!line.getIsLeader())
			{
				m_header.addLine(line);
				i++;
			}	
			else
				bFoundFirstLeader = true;
		}

		BasicBlock curBlock = null;
		int nBlockNum = 0;

		// Build basic blocks and add them to block array
		for(; i < m_lineList.getNumItems(); i++)
		{
			Line line = m_lineList.getItemAtIndex(i);			

			// If this is the start of a block, then print block label
			if(line.getIsLeader())
			{
				curBlock = new BasicBlock(line.getLabel(), nBlockNum++);
				m_blockList.add(curBlock);
			}
			
			if(null != curBlock)
				curBlock.addLine(line);
		}
	}

	private void buildGraph()
	{
		// Loop through blocks
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);

			// Check last instruction.
			LineList blockLines = curBlock.getLines();
			Line lastLine = blockLines.getItemAtIndex(blockLines.getNumItems() - 1);

			boolean lastLineUnconditionalBranch = false;

			// If the last instruction is a jump, then find block current block
			// jumps to
			if(Line.Type.INSTRUCTION == lastLine.getType())
			{
				Instruction instr = (Instruction)lastLine;

				if(instr.getIsJump())
				{
					BasicBlock targetBlock = getTargetBlock((Instruction)lastLine);
		
					if(null != targetBlock)
					{
						// Add current block to target block's predecessors list
						targetBlock.getPredecessors().add(curBlock);

						// Add target block to current block's successors
						curBlock.getSuccessors().add(targetBlock);
					}

					if(Instruction.BranchType.JUMP == instr.getBranchType())
						lastLineUnconditionalBranch = true;
				}
			}					

			// If this is not the last block, then get next block
			if(i < (m_blockList.size() - 1) && !lastLineUnconditionalBranch)
			{
				BasicBlock nextBlock = m_blockList.get(i+1);

				// Add current block to next block's predecessor's list
				nextBlock.getPredecessors().add(curBlock);

				// Add next block to current block's successor's list
				curBlock.getSuccessors().add(nextBlock);
			}
		}
	}

	private void PrintCFG()
	{
		LineList headerLines = m_header.getLines();

		for(int i = 0; i < headerLines.getNumItems(); i++)
		{
			Line line = headerLines.getItemAtIndex(i);
			System.out.println(line.printDebug());
		}
		
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			System.out.println("# B" + block.getBlockNum());

			LineList blockLines = block.getLines();

			for(int j = 0; j < blockLines.getNumItems(); j++)
			{
				Line line = blockLines.getItemAtIndex(j);
				System.out.println(line.printDebug());
			}
		}
	}

	private void PrintOptimizedCode()
	{
		for(int i = 0; i < m_header.getLines().getNumItems(); i ++)
		{
			Line line = m_header.getLines().getItemAtIndex(i);
			
			System.out.println(line);
		}
		
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);
			
			for(int j = 0; j < curBlock.getLines().getNumItems(); j++)
			{
				Line line = curBlock.getLines().getItemAtIndex(j);
		
				System.out.println(line);
			}
		}
	}

	private void buildDomSets()
	{
		boolean bChanges = true;

		BasicBlock n0 = m_blockList.get(0);
		
		n0.getDominators().add(n0);

		// Initialize all nodes' dominator set to be N
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curNode = m_blockList.get(i);

			// If n0, then continue
			if(curNode.getBlockNum() == n0.getBlockNum())
				continue;

			for(int j = 0; j < m_blockList.size(); j++)
			{
				BasicBlock dom = m_blockList.get(j);

				curNode.getDominators().add(dom);
			}
		}
		
		// Iterate over graph until no changes occur
		while(bChanges)
		{
			bChanges = false;

			// For all nodes that aren't n0
			for(int i = 0; i < m_blockList.size(); i++)
			{
				BasicBlock block = m_blockList.get(i);

				// If n0, then continue
				if(block.getBlockNum() == n0.getBlockNum())
					continue;

				// Dom(block) = {block} + Intesect(preds of block)
				Set<BasicBlock> joinSet = new Set<BasicBlock>();

				// For intersection to work, we have to start with the first predecessor's set
				if(!block.getPredecessors().isEmpty())
				{
					joinSet.copy(block.getPredecessors().get(0).getDominators());			

					// Need to start with 
					for(int j = 0; j < block.getPredecessors().size(); j++)
					{		
						BasicBlock pred = block.getPredecessors().get(j);
	
						// Stores the intersection of the two sets in the leftmost list
						joinSet.intersect(pred.getDominators());
					}
				}

				// Add self to dominator set
				joinSet.add(block);

				// If joinSet is different from original set, then flag bChanges to true
				bChanges = !block.getDominators().equals(joinSet);

				block.setDominators(joinSet);
			}
		}	
	}

	// This method assumes that the function buildCFG() has already been run on the 
	// block list
	private void buildIDomTree()
	{
		// First, build dominator sets
		buildDomSets();

		// Initialize IDom(n) to Dom(n) - {n}
		// Skip n0
		for(int i = 1; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			copyBlockList(block.getIDom(), block.getDominators());
			block.getIDom().remove(block);
		}

		// For each node p in the CFG
		for(int i = 1; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			Set<BasicBlock> temp = new Set<BasicBlock>(block.getIDom());

			// For each element in IDom(block)
			for(int j = 0; j < block.getIDom().size(); j++)
			{
				BasicBlock block_dom = block.getIDom().get(j);

				// For each element in IDom(dom)
				for(int k = 0; k < block_dom.getIDom().size(); k++)
				{
					BasicBlock dom_dom = block_dom.getIDom().get(k);
			
					// If dom_dom is not equal to block_dom, then 
					// remove from IDom(block)
					if(dom_dom.getBlockNum() != block_dom.getBlockNum())
						temp.remove(dom_dom);
				}
			}

			block.setIDom(temp);
		}

		// Now set up the IDOM children for each node
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);

			ArrayList<BasicBlock> iDomList = curBlock.getIDom();

			if(0 != iDomList.size())
			{
				BasicBlock iDom = iDomList.get(0);

				iDom.getIDomChildren().add(curBlock);
			}
		}
	}

	// This method assumes that buildIDomTree() has already been run on the 
	// block list
	private void buildDominanceFrontiers()
	{
		// Initialize DF for all basic blocks to {} 
			// Shoud have been set that way on initialization

		// Iterate through basic blocks
		for(int i = 1; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);
			
			// If a BB has multiple predecessors (join node) then
			if(block.getPredecessors().size() > 1)
			{
				ArrayList<BasicBlock> preds = block.getPredecessors();

				// For each predecessor p of BB n
				for(int j = 0; j < preds.size(); j++)
				{
					// runner = p
					BasicBlock runner = preds.get(j);

					// While runner != IDom(n)
					while(!block.getIDom().isEmpty() && runner != block.getIDom().get(0))
					{
						// If n is not in DF(runner)
						if(!runner.getDominanceFrontier().contains(block))
						{
							// add n to DF(runner)
							runner.getDominanceFrontier().add(block);
						}
						// runner = IDom(runner)
						runner = runner.getIDom().get(0);
					}
				}
			}
		}
	}

	private void calcStaticSingleAssignment()
	{
		SSAContext context = new SSAContext(m_variableList);

		insertPhiFunctions();
		ssaAnnotateCFG(m_blockList.get(0), context);
	}

	private void insertPhiFunctions()
	{
		// For all variables v in the program
		for(int i = 0; i < m_variableList.size(); i++)
		{
			Variable curVar = m_variableList.get(i);

			// worklist = list of blocks with an assignment to v
			ArrayList<BasicBlock> worklist = buildWorkList(curVar);

			// everWorked = worklist
			ArrayList<BasicBlock> everWorked = new ArrayList<BasicBlock>();
			copyBlockList(everWorked, worklist);

			// hasPhiFunc = {}
			ArrayList<BasicBlock> hasPhiFunc = new ArrayList<BasicBlock>();

			// while worklist != {}
			while(!worklist.isEmpty())
			{
				// Remove a block d from worklist to work on
				BasicBlock workBlock = worklist.get(0);
				worklist.remove(workBlock);

				// For each block d in DF(workBlock)
				for(int j = 0; j < workBlock.getDominanceFrontier().size(); j++)
				{
					BasicBlock d = workBlock.getDominanceFrontier().get(j);
	
					// if d is not in hasPhiFunc(v)
					if(!hasPhiFunc.contains(d))
					{
						// Insert a Phi function for v in d
						PhiFunction phiFunc = new PhiFunction(curVar);
						d.getPhiFunctions().add(phiFunc);

						// hasPhiFunc(v) += {d}
						hasPhiFunc.add(d);					
				
						// if d is not in everWorked(v)
						if(!everWorked.contains(d))
						{
							// everWorked += d
							everWorked.add(d);

							// worklist += d	
							worklist.add(d);
						}
					}
				}
			}
		}
	}

	private void ssaAnnotateCFG(BasicBlock curBlock, SSAContext context)
	{	
		ArrayList<String> popList = new ArrayList<String>();
		Hashtable<String, String> mapRegToVar = new Hashtable<String, String>();
		Hashtable<String, Instruction> mapVarToInstr = new Hashtable<String, Instruction>();

		// For each Phi funciton in the block x <- Phi(...)
		for(int i = 0; i < curBlock.getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = curBlock.getPhiFunctions().get(i);

			// NewName(x)
			phiFunc.setVersion(context.getCounterVal(phiFunc.getVariable().getName()) - 1);
		}

		// For each instruction x <- y op z
		LineList lineList = curBlock.getLines();

		for(int i = 0; i < lineList.getNumItems(); i++)
		{
			Line line = lineList.getItemAtIndex(i);

			if(Line.Type.INSTRUCTION == line.getType())
			{
				Instruction instr = (Instruction)line;

				// If this is a LA instruction
				if(0 == instr.getInstruction().compareTo("la"))
				{
					// Get Register
					Argument arg1 = instr.getArgument1();

					// Get variable
					Argument arg2 = instr.getArgument2();

					// Use variable at the top of the stack(arg2) if it exists
					Stack<String> stack = context.stack(arg2.getOrigName());
					
					if(null != stack && !stack.isEmpty())
					{
						String strName = stack.peek();

						if(null != strName)
						{
							arg2.setName(strName);
						}
					}

					// Add pair to map
					mapRegToVar.put(arg1.getOrigName(), arg2.getOrigName());
					mapVarToInstr.put(arg2.getOrigName(), instr);
				}
				// If this is a LW instruction
				else if(0 == instr.getInstruction().compareTo("lw"))
				{
					// y = top(stack(y))
					// z = top(stack(z))
					Argument arg = instr.getArgument2();
					String strOldVal = arg.getOrigName();

					Stack<String> stack = context.stack(strOldVal);

					if(null != stack && !stack.isEmpty())
					{
						String strName = stack.peek();
		
						if(null != strName)
						{
							arg.setName(strName);
						}
					}				
				}
				// If this is a SW instruction
				else if(0 == instr.getInstruction().compareTo("sw"))
				{
					// x = NewName(x)
					Argument arg2 = instr.getArgument2();

					// If this is a normal SW instruction
					if(Argument.Type.IDENTIFIER == arg2.getType())
					{
						popList.add(arg2.getOrigName());

						String strNewName = getNewVariableName(arg2.getOrigName(), context);
						arg2.setName(strNewName);
					}
					// If this is of the form sw $t0, ($t1)
					else if(Argument.Type.REGISTER_CONTENTS == arg2.getType())
					{
						// Parse out the Register name from the argument text
						String strVar = mapRegToVar.get(arg2.getOrigName());

						if(null != strVar)
						{
							Instruction laInstr = mapVarToInstr.get(strVar);

							if(null != laInstr)
							{
								popList.add(strVar);
								String strNewName = getNewVariableName(strVar, context);
							
								Argument arg = laInstr.getArgument2();
								arg.setName(strNewName);
							}
						}
					}
				}
			}
		}

		// For each successor of block B
		for(int i = 0; i < curBlock.getSuccessors().size(); i++)
		{
			BasicBlock succBlock = curBlock.getSuccessors().get(i);

			// Rewrite the appropriate Phi functions
			rewritePhiFunctions(succBlock, context);
		}

		// For each child s of B in the IDom tree
		for(int i = 0; i < curBlock.getIDomChildren().size(); i++)
		{
			BasicBlock iDomChild = curBlock.getIDomChildren().get(i);
			ssaAnnotateCFG(iDomChild, context);
		}

		// For each x <- y op z and x <- Phi(...)
		for(int i = 0; i < popList.size(); i++)
		{
			// pop(stack(x))
			context.stack(popList.get(i)).pop();
		}
			
	}

	private static String getNewVariableName(String oldName, SSAContext context)
	{
		String newName;
		int counter = context.getCounterVal(oldName);

		newName = oldName + "_" + counter;

		context.setCounterVal(oldName, counter + 1);
		context.stack(oldName).push(newName);
	
		return newName;
	}

	private void rewritePhiFunctions(BasicBlock curBlock, SSAContext context)
	{
		for(int i = 0; i < curBlock.getPhiFunctions().size(); i++)
		{
			PhiFunction phiFunc = curBlock.getPhiFunctions().get(i);
			
			String strVarName = phiFunc.getVariable().getName();

			if(null == strVarName)
			{
				System.out.println("Error:  Variale name is NULL");	
			}
			else
			{
				if(0 != context.stack(strVarName).size())
				{
					String strVar = context.stack(strVarName).peek();

					phiFunc.addVariable(strVar, new Symbol(strVar, Symbol.SymbolType.VARIABLE, Symbol.DataType.INTEGER));
				}
			}
		}
	}

	private ArrayList<BasicBlock> buildWorkList(Variable curVar)
	{
		ArrayList<BasicBlock> workList = new ArrayList<BasicBlock>();

		// Iterate through blocks
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);

			// Iterate through instructions
			LineList lines = curBlock.getLines();
	
			for(int j = 0; j < lines.getNumItems(); j++)
			{
				Line curLine = lines.getItemAtIndex(j);

				// If there is an assignment to v (sw instruction)
				if(Line.Type.INSTRUCTION == curLine.getType())
				{
					Instruction instr = (Instruction)curLine;

					if(0 == instr.getInstruction().compareTo("sw"))
					{
						Argument instrArg = instr.getArgument2();

						if(0 == instrArg.getOrigName().compareTo(curVar.getName()))
						{
							// add block to worklist	
							workList.add(curBlock);
						}
					}	
				}
			}
		}

		return workList;
	}
	
	private void calculateLiveSets()
	{
		// For each basic block...starting with the last
		for(int i = m_blockList.size()-1; i >= 0; i--)
		{
			BasicBlock curBlock = m_blockList.get(i);
			
			// Calculate LiveOUT for the block
			curBlock.calculateLiveOUT();

			// Calculate LiveIN for the block
			// (live set preceding first instruction)
			// This method also calculates 
			curBlock.calculateLiveIN();
		}
	}
	
	private boolean optimizeCode()
	{
		__debugPrintln("optimizing code");
		boolean bChanges = false;

		// Reset visited flag for all blocks
		for(int i = 0; i < m_blockList.size(); i++)
		{
			m_blockList.get(i).setVisited(false);
		}

		// For each block in the program, optimize register allocation
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);
			
			RegAllocContext context = new RegAllocContext(curBlock.getLines(), 8);
			bChanges = curBlock.optimizeRegisterAllocation(context,8);
		}
		
		return bChanges;
	}

	private void printIDom()
	{
		System.out.println("\n\n");

		System.out.println("# IDom tree edges:");

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			System.out.print("#B" + block.getBlockNum() + ": ");

			if(0 == block.getIDom().size())
				System.out.print("-");
			else
			{
				for(int j = 0; j < block.getIDom().size(); j++)
				{
					BasicBlock dom = block.getIDom().get(j);
					System.out.print("B" + dom.getBlockNum());			
				}
			}

			System.out.println("");
		}
	}

	private void printDominanceFrontiers()
	{
		System.out.println("\n\n");

		System.out.println("# Dominance Frontiers:");

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			System.out.print("#B" + block.getBlockNum() + ": ");

			if(0 == block.getDominanceFrontier().size())
				System.out.print("-");
			else
			{
				for(int j = 0; j < block.getDominanceFrontier().size(); j++)
				{
					BasicBlock dom = block.getDominanceFrontier().get(j);
					System.out.print("B" + dom.getBlockNum() + ",");			
				}
			}

			System.out.println("");
		}
	}

	private void printPhiFunctions()
	{
		System.out.println("\n\n");

		System.out.println("# Phi Functions:");

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			System.out.print("#B" + block.getBlockNum() + ": ");

			if(0 == block.getPhiFunctions().size())
				System.out.print("-");
			else
			{
				for(int j = 0; j < block.getPhiFunctions().size(); j++)
				{
					PhiFunction phiFunc = block.getPhiFunctions().get(j);
					System.out.print(phiFunc.getVariable().getName() + "_" + phiFunc.getVersion() + "=(");

					for (Enumeration<String> e = phiFunc.getVariableList().keys(); e.hasMoreElements();)
					{
						String strVar = e.nextElement();
						Symbol sym = phiFunc.getVariableList().get(strVar);
						System.out.print(strVar + "=" + sym.getIntegerValue() + ",");
					}

					System.out.print("),");		
				}
			}

			System.out.println("");
		}
	}

	private void printDomSets()
	{
		System.out.println("-----------------------------------------------");
		System.out.println("Dominators:");
		System.out.println("-----------------------------------------------");

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			System.out.println("## Block " + block.getBlockNum());
			System.out.print("Dom: ");
			for(int j = 0; j < block.getDominators().size(); j++)
			{
				BasicBlock dom = block.getDominators().get(j);
				System.out.print(dom.getBlockNum() + " ");				
			}

			System.out.println("");
		}
	}
	
	private void printLiveSets()
	{
		System.out.println("-----------------------------------------------");
		System.out.println("Live Sets:");
		System.out.println("-----------------------------------------------");
		
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);
			
			System.out.println("######Block " + block.getBlockNum());
			
			System.out.println("LiveIN = " + block.getLiveIn());
			
			for(int j = 0; j < block.getLines().getNumItems(); j++)
			{
				Line line = block.getLines().getItemAtIndex(j);
				System.out.print(line.printDebug());
				System.out.println("\tLive = " + line.getLiveSet());
			}
			
			System.out.println("LiveOUT = " + block.getLiveOut());
			
			System.out.println("\n");
		}
	}

	private void printGraph()
	{
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);
			
			System.out.println("######Block " + block.getBlockNum());
			
			System.out.println("Predecessors: ");

			ArrayList<BasicBlock> preds = block.getPredecessors();

			for(int j = 0; j < preds.size(); j++)
			{
				BasicBlock pred = preds.get(j);

				System.out.print(pred.getBlockNum() + " ");
			}

			System.out.println("");
			System.out.println("Successors:");
			ArrayList<BasicBlock> succs = block.getSuccessors();

			for(int j = 0; j < succs.size(); j++)
			{
				BasicBlock succ = succs.get(j);
				System.out.print(succ.getBlockNum() + " ");
			}

			System.out.println("");
		}
	}

	private void copyBlockList(ArrayList<BasicBlock> dest, ArrayList<BasicBlock> src)
	{
		dest.clear();

		for(int i = 0; i < src.size(); i++)
		{
			dest.add(src.get(i));
		}
	}

	private void markLeaders()
	{
		boolean bFoundText = false;
		boolean bLeader = true;

		for(int i = 0; i < m_lineList.getNumItems(); i++)
		{
			Line line = m_lineList.getItemAtIndex(i);

			// find start of .text section
			if(!bFoundText)
			{
				if(line.getOrigText().contains(".text"))
				{
					bFoundText = true;
				}

				continue;
			}
			
			// Get pointer to current instruction
			Line curLine = m_lineList.getItemAtIndex(i);

			// If examination of the last instruction indicated that this one is a leader, then
			// add it to the list of leaders
			if(bLeader)
			{
				curLine.setIsLeader(true);

				bLeader = false;
			}

			// If the current line is an instruction...
			if(Line.Type.INSTRUCTION == curLine.getType())
			{
				Instruction instr = (Instruction)line;

				// If the current instruction is a jump, then
				// mark the line it jumps to as a leader
				if(instr.getIsJump())
				{
					Argument jumpTarget = null;

					switch(instr.getBranchType())
					{
						case JUMP:
							jumpTarget = instr.getArgument1();
							break;

						case JUMP_EQ:
						case JUMP_NEQ:
							jumpTarget = instr.getArgument2();
							break;
					}

					// Find target instruction in instruction list
					Line target = findJumpTarget(jumpTarget);

					if(null != target)
						target.setIsLeader(true);

					// Set leader flag so next instruction becomes a leader
					bLeader = true;	
				}
			}
		}
	}

	private Line findJumpTarget(Argument arg)
	{
		Line jumpTarget = null;

		for(int i = 0; i < m_lineList.getNumItems(); i++)
		{
			Line line = m_lineList.getItemAtIndex(i);

			if(Line.Type.LABEL == line.getType())
			{
				if(0 == line.getLabel().compareTo(arg.toString()))
				{
					jumpTarget = line;
					break;
				}
			}
		}

		return jumpTarget;
	}

	private BasicBlock getTargetBlock(Instruction instr)
	{
		BasicBlock retBlock = null;

		// Get jump target of line
		String strTarget = instr.getJumpTarget();

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			// If jump target is label in block, then return block
			if(0 == strTarget.compareTo(block.getLabel()))
				retBlock = block;
		}

		return retBlock;
	}
};
