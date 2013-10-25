import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ListIterator;

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

				if(Variable.Type.BINARY == varList.get(i).getType())
				{
					String strBaseName = CodeAnalyzer.getNewVariableName(varList.get(i).getName(), this);
					m_varCounters.put(varList.get(i).getName(), 0);
				}
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

	class ConstPropContext
	{
		public Hashtable<String, Symbol> mapRegToValue = new Hashtable<String, Symbol>();
		public Hashtable<String, Symbol> mapVarToValue = new Hashtable<String, Symbol>();
		public Hashtable<String, Instruction> mapVarToInstr = new Hashtable<String, Instruction>();
		public Hashtable<String, Instruction> mapRegToInstr = new Hashtable<String, Instruction>();

		public ConstPropContext()
		{
		}

		public ConstPropContext(ConstPropContext right)
		{
			copyHashStringToSymbol(mapRegToValue, right.mapRegToValue);
			copyHashStringToSymbol(mapVarToValue, right.mapVarToValue);
			copyHashStringToInstruction(mapVarToInstr, right.mapVarToInstr);
			copyHashStringToInstruction(mapRegToInstr, right.mapRegToInstr);
		}

		private void copyHashStringToString(Hashtable<String, String> dest, Hashtable<String, String> src)
		{
			for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
			{
				String str = e.nextElement();
				dest.put(str, src.get(str));
			}
		}

		private void copyHashStringToSymbol(Hashtable<String, Symbol> dest, Hashtable<String, Symbol> src)
		{
			for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
			{
				String str = e.nextElement();
				dest.put(str, src.get(str));
			}
		}

		private void copyHashStringToInstruction(Hashtable<String, Instruction> dest, Hashtable<String, Instruction> src)
		{
			for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
			{
				String str = e.nextElement();
				dest.put(str, src.get(str));
			}
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
		//runDiagnostics();

		analyzeCode();
		PrintOptimizedCode();
	}	

	public void runDiagnostics()
	{
		buildCFG();
                buildIDomTree();
                buildDominanceFrontiers();
                calcStaticSingleAssignment();

		PrintCFG();
		printIDom();
		printDominanceFrontiers();
		printPhiFunctions();
	}

	public void analyzeCode()
        {
                boolean bChanges = true;

                int iter = 1;
                while(iter <= 50 && bChanges)
                {
                        __debugPrintln("=================================");
                        __debugPrintln("Iter " + iter);
                        __debugPrintln("=================================");

                        buildCFG();
                        buildIDomTree();
                        buildDominanceFrontiers();
                        calcStaticSingleAssignment();

                        // Alternately, propagate changes or remove blocks
                        if(1 == iter % 2)
                                bChanges = optimizeCode();
                        else
                        {
                                for(int i = 0; i < m_blockList.size(); i++)
                                {
                                        BasicBlock curBlock = m_blockList.get(i);

                                        // Remove unreachable code
                                        if(0 == i || includeBlock(curBlock, true))  // Include block does pruning
                                        {
                                        }
                                }
                        }
                
                        __debugPrintln("bChanges = " + (bChanges ? "true" : "false"));

                        if(bChanges)
                        {
                                __debugPrintln("There were changes!");
                                // Rewrite instructionlist
                                LineList newLines = new LineList();

                                LineList headerLines = m_header.getLines();

                                for(int i = 0; i < headerLines.getNumItems(); i++)
                                {
                                        Line line = headerLines.getItemAtIndex(i);
                                        newLines.addItem(headerLines.getItemAtIndex(i));
                                        __debugPrintln(line);
                                }

                                for(int i = 0; i < m_blockList.size(); i++)
                                {
                                        BasicBlock curBlock = m_blockList.get(i);

                                        // Remove unreachable code
                                        if(0 == i || 0 != curBlock.getPredecessors().size())
                                        {
                                                for(int j = 0; j < curBlock.getLines().getNumItems(); j++)
                                                {
                                                        Line line = curBlock.getLines().getItemAtIndex(j);

                                                        if(Line.Type.INSTRUCTION == line.getType())
                                                        {
                                                                Instruction instr = (Instruction)line;

                                                                instr.resetArguments();
                                                        }

                                                        newLines.addItem(line);
                                                        __debugPrintln(line);
                                                }
                                        }
                                }

                                // Reset block list
                                m_blockList.clear();
                                m_blockList = new ArrayList<BasicBlock>();
                                m_lineList = newLines;
                                m_header = new BasicBlock("", -1);
                        }

                        iter++; 
                }
        }

	public void __analyzeCode()
	{
		boolean bChanges = true;

		int iter = 1;
		while(iter < 8 && bChanges)
		{
			__debugPrintln("=================================");
			__debugPrintln("Iter " + iter);
			__debugPrintln("=================================");

			if(1 == iter % 2)
				bChanges = doConstPropagation();
			else
				doBlockPruning();
			
			iter++;
		}
	}

	public boolean doConstPropagation()
	{
		__debugPrintln("Doing Constant propagation.");
		boolean bChanges = false;

		buildCFG();
		buildIDomTree();
		buildDominanceFrontiers();
		calcStaticSingleAssignment();

		bChanges = optimizeCode();

		if(bChanges)
			rewriteInstructionList();
	
		return bChanges;	
	}

	public boolean doBlockPruning()
	{
		__debugPrintln("Doing block pruning");

		buildCFG();
		buildIDomTree();
		buildDominanceFrontiers();
		calcStaticSingleAssignment();

		boolean bCodeSame = true;

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);

			// Remove unreachable code
			if(0 != i)
				bCodeSame &= includeBlock(curBlock, true);  // Include block does pruning
		}

		if(!bCodeSame)
			rewriteInstructionList();

		return !bCodeSame;
	}

	public void rewriteInstructionList()
	{
		// Rewrite instructionlist
		LineList newLines = new LineList();

		LineList headerLines = m_header.getLines();

		for(int i = 0; i < headerLines.getNumItems(); i++)
		{
			Line line = headerLines.getItemAtIndex(i);
			newLines.addItem(headerLines.getItemAtIndex(i));
			__debugPrintln(line);
		}

		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);

			// Remove unreachable code
			if(0 == i || 0 != curBlock.getPredecessors().size())
			{
				for(int j = 0; j < curBlock.getLines().getNumItems(); j++)
				{
					Line line = curBlock.getLines().getItemAtIndex(j);

					if(Line.Type.INSTRUCTION == line.getType())
					{
						Instruction instr = (Instruction)line;

						instr.resetArguments();
					}

					newLines.addItem(line);
					__debugPrintln(line);
				}
			}
		}

		// Reset block list
		m_blockList.clear();
		m_blockList = new ArrayList<BasicBlock>();
		m_lineList = newLines;
		m_header = new BasicBlock("", -1);
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

	public void PrintCFG()
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

	public void PrintOptimizedCode()
	{
		for(int i = 0; i < m_lineList.getNumItems(); i++)
		{
			Line line = m_lineList.getItemAtIndex(i);
	
			System.out.println(line);
		}
	}

	private boolean includeBlock(BasicBlock block, boolean bPrune)
	{
		boolean bRet = true;

		__debugPrintln("Evaluating B:" + block.getBlockNum() + ":" + block.getLabel() +", which has " + block.getLines().getNumItems() + " lines");

		// Block has no more predecessors, unreachable
		if(0 == block.getPredecessors().size())
		{
			__debugPrintln("Removing B" + block.getBlockNum() + " due to 0 predecessors");

			// Prune the block from the predecessor
			if(bPrune)	
			{
				//removeVarRefsFromSuccessors(block);
				removeBlockFromCFG(block);
			}

			bRet = false;
		}
		// Block consists of just a single LABEL line
		else if(1 == block.getLines().getNumItems())
		{
			Line line = block.getLines().getItemAtIndex(0);
		
			if(Line.Type.LABEL == line.getType())
			{
				if(bPrune)
				{
					// Look at the block's predecessors
					int nEnd = block.getPredecessors().size()-1;
					for(int i = nEnd; i >= 0 ; i--)
					{
						// Prune the block from the predecessor
						pruneBlock(block.getPredecessors().get(i), block);
					}
				}

				bRet = false;
			}
		}
		// Block is just a JUMP instruction with 
		else if(2 == block.getLines().getNumItems())
		{
			Instruction instr = (Instruction)block.getLines().getItemAtIndex(1);
			
			// If this is a block with only a b <label> instruction, then remove it
			if(instr.getIsJump() && Instruction.BranchType.JUMP == instr.getBranchType())
			{
				__debugPrintln("Removing B" + block.getBlockNum() + " due to branch-only structure");

				if(bPrune)
				{
					// Look at the block's predecessors
					int nEnd = block.getPredecessors().size()-1;
					for(int i = nEnd; i >= 0 ; i--)
					{
						// Prune the block from the predecessor
						pruneBlock(block.getPredecessors().get(i), block);
					}
				}

				bRet = false;
			}
		}

		return bRet;
	}

	// Prune nextBlock from CFG
	private void pruneBlock(BasicBlock curBlock, BasicBlock nextBlock)
	{
		// Remove nextBlock from the CFG
		nextBlock.getPredecessors().remove(curBlock);
		curBlock.getSuccessors().remove(nextBlock);

		// If nextBlock has a jump target
		BasicBlock nextNext = getBlockJumpTarget(nextBlock);

		if(null != nextNext)
		{
			// make curBlock jump to it
			patchJumpTarget(curBlock, nextNext);
		}
		else
		{
			// Make cur block jump to the fall-through block
			// Hint:  If there is no jump, then there can be only one successor
			if(nextBlock.getSuccessors().size() != 0)
			{
				patchJumpTarget(curBlock, nextBlock.getSuccessors().get(0));
			}
		}	

		removeBlockFromCFG(nextBlock);
	}

 	public void removeBlockFromCFG(BasicBlock curBlock)
        {
                for(int i = 0; i < curBlock.getSuccessors().size(); i++)
                {
                        BasicBlock nextBlock = curBlock.getSuccessors().get(i);

                        nextBlock.getPredecessors().remove(curBlock);
                        curBlock.getSuccessors().remove(nextBlock);
                
                        if(0 == nextBlock.getPredecessors().size())
                        {
                                removeBlockFromCFG(nextBlock);
                        }
                }
        }

	private BasicBlock getBlockJumpTarget(BasicBlock block)
	{
		BasicBlock blockRet = null;

		Instruction instr = null;

		// Get last line in the block
		Line line = block.getLines().getItemAtIndex(block.getLines().getNumItems()-1);
		Argument jumpTarget = null;

		// If it's an instruction, look closer
		if(Line.Type.INSTRUCTION == line.getType())
		{
			instr = (Instruction)line;

			// Get the argument that contains the jump target label
			if(0 == instr.getInstruction().compareTo("b"))
				jumpTarget = instr.getArgument1();
			else if(0 == instr.getInstruction().compareTo("bnez"))
				jumpTarget = instr.getArgument2();
			else if(0 == instr.getInstruction().compareTo("beqz"))
				jumpTarget = instr.getArgument2();
		}

		// If we found a jump target
		if(null != jumpTarget)
		{
			String strCurLabel = instr.getArgument1().getName();

			// Find the block that matches the label
			for(int i = 0; i < m_blockList.size(); i++)
			{
				if(0 == m_blockList.get(i).getLabel().compareTo(strCurLabel))
					blockRet = m_blockList.get(i);
			}
		}
	
		return blockRet;
	}

	private void patchJumpTarget(BasicBlock base, BasicBlock newTarget)
	{
		Instruction instr = null;

		// Get last line in the block
		Line line = base.getLines().getItemAtIndex(base.getLines().getNumItems()-1);
		Argument jumpTarget = null;

		// If it's an instruction, look closer
		if(Line.Type.INSTRUCTION == line.getType())
		{
			instr = (Instruction)line;

			// Get the argument that contains the jump target label
			if(0 == instr.getInstruction().compareTo("b"))
				jumpTarget = instr.getArgument1();
			else if(0 == instr.getInstruction().compareTo("bnez"))
				jumpTarget = instr.getArgument2();
			else if(0 == instr.getInstruction().compareTo("beqz"))
				jumpTarget = instr.getArgument2();
		}

		// If we found a jump target
		if(null != jumpTarget)
		{
			// Change the instruction to jump to target block's label
			jumpTarget.setName(newTarget.getLabel());
			jumpTarget.setOrigName(newTarget.getLabel());

			newTarget.getPredecessors().add(base);
			base.getSuccessors().add(newTarget);
		}
		// If not, we're getting to next block via fall-through
		else
		{
			// Create b <label> instruction to branch to newTarget's label
			Argument arg = new Argument(newTarget.getLabel(), Argument.Type.IDENTIFIER);
			Instruction newInstr = new Instruction(Line.Type.INSTRUCTION, "b", arg, null, null);
			newInstr.setIsJump(true);
			newInstr.setBranchType(Instruction.BranchType.JUMP);
			
			// Add the instruction to base
			base.getLines().addItem(newInstr);

			newTarget.getPredecessors().add(base);
			base.getSuccessors().add(newTarget);
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
				ArrayList<BasicBlock> joinSet = new ArrayList<BasicBlock>();

				// For intersection to work, we have to start with the first predecessor's set
				if(!block.getPredecessors().isEmpty())
				{
					copyBlockList(joinSet, block.getPredecessors().get(0).getDominators());				

					// Need to start with 
					for(int j = 0; j < block.getPredecessors().size(); j++)
					{		
						BasicBlock pred = block.getPredecessors().get(j);
	
						// Stores the intersection of the two sets in the leftmost list
						joinSet = intersectBlockSets(joinSet, pred.getDominators());
					}
				}

				// Add self to dominator set
				joinSet.add(block);

				// If joinSet is different from original set, then flag bChanges to true
				bChanges = !setsEqual(block.getDominators(), joinSet);

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

			ArrayList<BasicBlock> temp = new ArrayList<BasicBlock>();

			copyBlockList(temp, block.getIDom());

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
			String strNewName = getNewVariableName(phiFunc.getVariable().getName(), context);
			phiFunc.setVersion(context.getCounterVal(phiFunc.getVariable().getName()) - 1);
		}

		// For each instruction x <- y op z
		LineList lineList = curBlock.getLines();

		ListIterator<Line> listIter = lineList.getListIterator();

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

	public boolean optimizeCode()
	{
		__debugPrintln("optimizing code");
		boolean bRet = false;

		// Reset visited flag for all blocks
		for(int i = 0; i < m_blockList.size(); i++)
		{
			m_blockList.get(i).setVisited(false);
		}

		ConstPropContext context = new ConstPropContext();
		bRet = propagateConstants(m_blockList.get(0), context);

		return bRet;
	}

	public void updatePhiFunctions(BasicBlock curBlock, ConstPropContext context)
	{
		__debugPrintln("Looping through const variables for B" + curBlock.getBlockNum());
		// Loop through all constant variables, and update Phi Functions accordingly
		for(Enumeration<String> e = context.mapVarToValue.keys(); e.hasMoreElements();)
		{
			String var = e.nextElement();
			Symbol sym = context.mapVarToValue.get(var);

			__debugPrintln("Const Variable " + var + " = " + sym.getIntegerValue());

			// Update curBlock's Phi function for this variable
			curBlock.updatePhiFunctionCurName(var, sym);

			// If this variable is now a constant, then add it to the
			// mapVarToValue map
			PhiFunction phi = curBlock.getPhiFunctionMatchingCurName(var);
	
			if(null != phi && phi.isConst())
			{
				__debugPrintln("Adding Const Variable " + phi.getName() + " = " + sym.getIntegerValue());
				context.mapVarToValue.put(phi.getName(), sym);
			}
		
			// Not related to an instruction, so remove any reference
			context.mapVarToInstr.remove(var);
		}

		__debugPrint("Phi Functions for B" + curBlock.getBlockNum() + ":  ");
		for(int i = 0; i < curBlock.getPhiFunctions().size(); i++)
		{
			PhiFunction func = curBlock.getPhiFunctions().get(i);

			__debugPrint(func.getName() + "= {");
			for(Enumeration<String> e =  func.getVariableList().keys(); e.hasMoreElements();)
			{
				String var = e.nextElement();
				Symbol sym = func.getVariableList().get(var);

				__debugPrint("(" + var + "," + sym.getIntegerValue() + "), " );
			}
			
			__debugPrint("}");
		}
		__debugPrintln("");
	}

	public boolean propagateConstants(BasicBlock curBlock, ConstPropContext context)
	{
		boolean bChanges = false;

		// Set myself to visited
		curBlock.setVisited(true);

		//curBlock.setVisited(true);
		ArrayList<Instruction> removeList = new ArrayList<Instruction>();

		// Update block's Phi functions with relevant constant info
		updatePhiFunctions(curBlock, context);

		// Examine lines in current block
		LineList lineList = curBlock.getLines();
		ListIterator<Line> listIter = lineList.getListIterator();

		Line line = listIter.next();

		__debugPrintln("Propogating constants.");

		while(null != line)
		{
			if(Line.Type.INSTRUCTION == line.getType())
			{
				Instruction instr = (Instruction)line;

				// Get Argument1
				Argument arg1 = instr.getArgument1();

				// Get Argument2
				Argument arg2 = instr.getArgument2();

				// Get Argument3
				Argument arg3 = instr.getArgument3();

				// Perform instruction-level optimization
				// If this is a LI instruction
				if(0 == instr.getInstruction().compareTo("li") 
					&& !(0 == arg1.getName().compareTo("$v0") && 0 == arg2.getName().compareTo("5")))
				{
					Symbol sym = new Symbol(arg1.getName(), Symbol.SymbolType.CONSTANT, Symbol.DataType.INTEGER);
					sym.setIntegerValue(arg2.getValue());

					// Map the constant to a register
					context.mapRegToValue.put(arg1.getName(), sym);

					// Map the register to the instruction
					context.mapRegToInstr.put(arg1.getName(), instr);
				}
				// If this is a SW instruction
				else if(0 == instr.getInstruction().compareTo("sw"))
				{
					// If the register is associated with a constant
					if(context.mapRegToValue.containsKey(arg1.getName()))
					{
						// Map this variable to its integer value
						context.mapVarToValue.put(arg2.getName(), context.mapRegToValue.get(arg1.getName()));

						// Map the variable to this instruction
						context.mapVarToInstr.put(arg2.getName(), instr);

						// Can only delete of no unresolved Phi functions
						boolean bDeleteSW = !unresolvedPhiFunctionsExist(curBlock, arg2.getOrigName());

						// Remove this instruction
						if(bDeleteSW)
						{
							removeList.add(instr);

							// Get the instruction mapped to the register and remove it
							Instruction liInstr = context.mapRegToInstr.get(arg1.getName());
							removeList.add(liInstr);
						}
						__debugPrintln("setting SW changes to true");
						bChanges = true;
					}
					else
					{
						context.mapVarToInstr.remove(arg2.getName());
						context.mapVarToValue.remove(arg2.getName());
					}
				}
				// If this is a LW instruction
				else if(0 == instr.getInstruction().compareTo("lw"))
				{
					// If the variable is a constant
					if(context.mapVarToValue.containsKey(arg2.getName()))
					{		
						__debugPrintln("Found const variable: " + arg2.getName());

						// Propagate const-ness to the register
						context.mapRegToValue.put(arg1.getName(), context.mapVarToValue.get(arg2.getName()));		
						
						// Map the register to the instruction
						context.mapRegToInstr.put(arg1.getName(), instr);

						// Rewrite instruction to be a LI
						Symbol symConst = context.mapVarToValue.get(arg2.getName());
						instr.setInstruction("li");
						instr.setArgument2(new Argument(symConst.toString(), Argument.Type.LITERAL));
						instr.setArgument3(null);	

						bChanges = true;	
					}
					else if(curBlock.isConstPhiFunc(arg2.getName()))
					{
						__debugPrintln("Found const Phi Func: " + arg2.getName());

						// Propagate const-ness to the register
						int nVal = curBlock.getPhiFunctionConstValue(arg2.getOrigName());
						Symbol sym = new Symbol("" + nVal, Symbol.SymbolType.CONSTANT, Symbol.DataType.INTEGER);
						sym.setIntegerValue(nVal);
						context.mapRegToValue.put(arg1.getName(), sym);		
						
						// Map the register to the instruction
						context.mapRegToInstr.put(arg1.getName(), instr);

						// Rewrite instruction to be a LI
						Symbol symConst = curBlock.getPhiFunctionSymbol(arg2.getOrigName());

						instr.setInstruction("li");
						instr.setArgument2(new Argument(symConst.toString(), Argument.Type.LITERAL));
						instr.setArgument3(null);

						// Identified a const Phi variable, add it to map
						context.mapVarToValue.put(arg2.getName(), symConst);
						context.mapVarToInstr.put(arg2.getName(), instr);

						bChanges = true;
					}
					else
					{
						// Remove mapping of register to constant
						context.mapRegToValue.remove(arg1.getName());
						
						// Remove mapping of register to instruction
						context.mapRegToInstr.remove(arg1.getName());
					}
				}
				else if(0 == instr.getInstruction().compareTo("neg"))
				{
					if(context.mapRegToValue.containsKey(arg2.getName()))
					{
						// Negating the register, multiply by -1 and set destination
						// register as constant
						Symbol symArg2 = context.mapRegToValue.get(arg2.getName());

						Symbol symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
						symRet.setIntegerValue(symArg2.getIntegerValue() * -1);

						// Remove related register loads
						Instruction arg2Line = context.mapRegToInstr.get(arg2.getName());

						if(null != arg2Line)
							removeList.add(arg2Line);

						// Mark the assigned register as constant
						context.mapRegToValue.put(arg1.getName(), symRet);
						context.mapRegToInstr.put(arg1.getName(), instr);
				
						// Rewrite instruction to be a LI instruction
						instr.setInstruction("li");
						instr.setArgument1(new Argument(symRet.toString(), Argument.Type.LITERAL));
						instr.setArgument2(null);
						instr.setArgument3(null);

						bChanges = true;
					}
					else
					{
						// Remove mapping of register to constant
						context.mapRegToValue.remove(arg1.getName());
						
						// Remove mapping of register to instruction
						context.mapRegToInstr.remove(arg1.getName());
					}
					
				}
				// If this is another non-jump instruction
				else if(!instr.getIsJump() && 0 != instr.getInstruction().compareTo("syscall")
					&& 0 != instr.getInstruction().compareTo("move"))
				{
					// See if both operands are constants
					if(context.mapRegToValue.containsKey(arg2.getName()) && context.mapRegToValue.containsKey(arg3.getName()))
					{
						Symbol symArg2 = context.mapRegToValue.get(arg2.getName());
						Symbol symArg3 = context.mapRegToValue.get(arg3.getName());

						Symbol symRet = calculateResult(instr.getInstruction(), symArg2, symArg3);

						// Remove related register loads
						Instruction arg2Line = context.mapRegToInstr.get(arg2.getName());

						if(null != arg2Line)
							removeList.add(arg2Line);

						Instruction arg3Line = context.mapRegToInstr.get(arg3.getName());

						if(null != arg3Line)
							removeList.add(arg3Line);

						// Mark the assigned register as constant
						context.mapRegToValue.put(arg1.getName(), symRet);
						context.mapRegToInstr.put(arg1.getName(), instr);
					
						// Rewrite instruction to be a LI instruction
						instr.setInstruction("li");
						instr.setArgument2(new Argument(symRet.toString(), Argument.Type.LITERAL));
						instr.setArgument3(null);

						bChanges = true;
					}
					else
					{
						// Remove mapping of register to constant
						context.mapRegToValue.remove(arg1.getName());
						
						// Remove mapping of register to instruction
						context.mapRegToInstr.remove(arg1.getName());
					}
				}
				// If this is a jump instruction
				else if(instr.getIsJump())
				{
					// See if the operand is a constant
					if(null != arg1.getName() && context.mapRegToValue.containsKey(arg1.getName()))
					{
						// Determine to branch or not
						boolean bBranch = false;

						Symbol sym = context.mapRegToValue.get(arg1.getName());						

						if(0 == instr.getInstruction().compareTo("bnez"))
						{
							bBranch = (sym.getIntegerValue() != 0);
						}
						else if(0 == instr.getInstruction().compareTo("beqz"))
						{
							bBranch = (sym.getIntegerValue() == 0);
						}

						// If branching, then rewrite instruction
						if(bBranch)
						{
							instr.setInstruction("b");
							instr.setArgument1(new Argument(arg2.getName(), Argument.Type.IDENTIFIER));
							instr.setArgument2(null);

							// Remove this block from fall-through block's predecessors list
                                                        BasicBlock nextBlock = m_blockList.get(curBlock.getBlockNum() + 1);

                                                        if(null != nextBlock)
                                                        {
                                                                nextBlock.getPredecessors().remove(curBlock);
                                                                curBlock.getSuccessors().remove(nextBlock);

                                                                if(0 == nextBlock.getPredecessors().size())
                                                                        removeBlockFromCFG(nextBlock);
                                                        } 							
						}
						// else, remove instruction
						else
						{
							removeList.add(instr);

							// Remove jump target block
                                                        BasicBlock nextBlock = getTargetBlock(instr);

                                                        if(null != nextBlock)
                                                        {
                                                                nextBlock.getPredecessors().remove(curBlock);
                                                                curBlock.getSuccessors().remove(nextBlock);

                                                                if(0 == nextBlock.getPredecessors().size())
                                                                        removeBlockFromCFG(nextBlock);
                                                        }
						}

						instr.setBranchType(Instruction.BranchType.JUMP);

						// Remove related instruction from list
						Instruction arg1Line = context.mapRegToInstr.get(arg1.getName());

						if(null != arg1Line)
							removeList.add(arg1Line);

						 bChanges = true;
					}
				}
			}
			
			if(listIter.hasNext())
				line = listIter.next();
			else
				line = null;
		}

		// Remove lines
		for(int i = 0; i < removeList.size(); i++)
		{
			lineList.removeLine(removeList.get(i));
		}

		// Recursively Enumerate Successors of curBlock and propagate constants to them
		// (depth-first search)
		for(int i = 0; i < curBlock.getSuccessors().size(); i++)
		{
			if(!curBlock.getSuccessors().get(i).getVisited())
			{
				ConstPropContext newCtxt = new ConstPropContext(context);
				bChanges |= propagateConstants(curBlock.getSuccessors().get(i), newCtxt);
			}
		}

		// Remove vistied
		curBlock.setVisited(false);

		return bChanges;
	}

	private boolean unresolvedPhiFunctionsExist(BasicBlock curBlock, String strName) // Call with getOrigName()
	{	
		boolean bRet = false;

		curBlock.setPhiVisited(true);

		for(int i = 0; !bRet && i < curBlock.getSuccessors().size(); i++)
		{	
			BasicBlock successor = curBlock.getSuccessors().get(i);
			if(successor.hasPhiFunc(strName))
			{
				// Can only delete if Const across all successors and their sub-trees	
				
				bRet = !successor.isConstPhiFunc(strName);
				break;
			}

			// Now recursively check successor's tree
			if(!successor.getPhiVisited())
			{
				bRet = unresolvedPhiFunctionsExist(successor, strName);
				break;
			}
		}

		curBlock.setPhiVisited(false);

		return bRet;
	}

	private static Instruction getInstructionFromRegister(String strRegName, ConstPropContext context)
	{
		Instruction instrRet = context.mapRegToInstr.get(strRegName);

		return instrRet;
	}

	private Symbol calculateResult(String strOp, Symbol symLeft, Symbol symRight)
	{
		Symbol symRet = null;

		// Calculate the resulting value
		if(0 == strOp.compareTo("sgt"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() > symRight.getIntegerValue() ? 1 : 0);
		}
		else if(0 == strOp.compareTo("sge"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() >= symRight.getIntegerValue() ? 1 : 0);
		}
		else if(0 == strOp.compareTo("slt"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() < symRight.getIntegerValue() ? 1 : 0);
		}
		else if(0 == strOp.compareTo("sle"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() <= symRight.getIntegerValue() ? 1 : 0);
		}
		else if(0 == strOp.compareTo("seq"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() == symRight.getIntegerValue() ? 1 : 0);
		}
		else if(0 == strOp.compareTo("sne"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() != symRight.getIntegerValue() ? 1 : 0);
		}
		else if(0 == strOp.compareTo("add"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() + symRight.getIntegerValue());
		}
		else if(0 == strOp.compareTo("addi"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() + symRight.getIntegerValue());
		}
		else if(0 == strOp.compareTo("sub"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() - symRight.getIntegerValue());
		}
		else if(0 == strOp.compareTo("mul"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() * symRight.getIntegerValue());
		}
		else if(0 == strOp.compareTo("div"))
		{
			symRet = new Symbol("__constant", Symbol.SymbolType.CONSTANT,Symbol.DataType.INTEGER);
			symRet.setIntegerValue(symLeft.getIntegerValue() / symRight.getIntegerValue());
		}

		return symRet;
	}

	public void printIDom()
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

	public void printDominanceFrontiers()
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

	public void printPhiFunctions()
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

	public void printDomSets()
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

	public void copyBlockList(ArrayList<BasicBlock> dest, ArrayList<BasicBlock> src)
	{
		dest.clear();

		for(int i = 0; i < src.size(); i++)
		{
			dest.add(src.get(i));
		}
	}

	public ArrayList<BasicBlock> intersectBlockSets(ArrayList<BasicBlock> left, ArrayList<BasicBlock> right)
	{
		ArrayList<BasicBlock> joinSet = new ArrayList<BasicBlock>();

		for(int i = 0; i < left.size(); i++)
		{
			BasicBlock leftBlock = left.get(i);
			boolean bMatch = false;

			for(int j = 0; j < right.size()  && !bMatch; j++)
			{
				bMatch = right.contains(left.get(i));
			}

			if(bMatch)
				joinSet.add(leftBlock);
		}

		return joinSet;
	}

	public boolean setsEqual(ArrayList<BasicBlock> left, ArrayList<BasicBlock> right)
	{
		boolean bRet = true;

		if(left.size() != right.size())
			bRet = false;
		else
		{
			for(int i = 0; i < left.size() && bRet; i++)
			{
				bRet = right.contains(left.get(i));
			}
		}

		return bRet;
	}

	private boolean allBlocksVisited()
	{
		boolean bRet = true;

		for(int i = 0; i < m_blockList.size() && bRet; i++)
		{
			BasicBlock block = m_blockList.get(i);

			bRet = block.getVisited();
		}

		return bRet;
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
