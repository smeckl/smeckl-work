import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class CodeAnalyzer
{
	LineList m_lineList;
	ArrayList<BasicBlock> m_blockList = new ArrayList<BasicBlock>();
	BasicBlock m_header = new BasicBlock("", -1);;

	public CodeAnalyzer(LineList lineList)
	{
		setLineList(lineList);
	}

	public void setLineList(LineList lineList)
	{
		m_lineList = lineList;
	}

	public LineList getLineList()
	{
		return m_lineList;
	}

	public void buildCFG()
	{
		markLeaders();
		buildBlocks();
		buildGraph();
	}

	public void buildBlocks()
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

	public void buildGraph()
	{
		// Loop through blocks
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock curBlock = m_blockList.get(i);

			// Check last instruction.
			LineList blockLines = curBlock.getLines();
			Line lastLine = blockLines.getItemAtIndex(blockLines.getNumItems() - 1);

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
				}
			}					

			// If this is not the last block, then get next block
			if(i < (m_blockList.size() - 1))
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
			System.out.println(line);
		}
		
		for(int i = 0; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			System.out.println("# B" + block.getBlockNum());

			LineList blockLines = block.getLines();

			for(int j = 0; j < blockLines.getNumItems(); j++)
			{
				Line line = blockLines.getItemAtIndex(j);
				System.out.println(line);
			}
		}
	}

	public void buildDomSets()
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
				copyDominatorList(joinSet, block.getPredecessors().get(0).getDominators());				

				// Need to start with 
				for(int j = 0; j < block.getPredecessors().size(); j++)
				{		
					BasicBlock pred = block.getPredecessors().get(j);
	
					// Stores the intersection of the two sets in the leftmost list
					joinSet = intersectBlockSets(joinSet, pred.getDominators());
				}

				// Add self to dominator set
				joinSet.add(block);

				// If joinSet is different from original set, then flag bChanges to true
				bChanges = !setsEqual(block.getDominators(), joinSet);

				block.setDominators(joinSet);
			}
		}	
	}

	public void buildIDomTree()
	{
		// First, build dominator sets
		buildDomSets();

		// Initialize IDom(n) to Dom(n) - {n}
		// Skip n0
		for(int i = 1; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			copyDominatorList(block.getIDom(), block.getDominators());
			block.getIDom().remove(block);
		}

		// For each node p in the CFG
		for(int i = 1; i < m_blockList.size(); i++)
		{
			BasicBlock block = m_blockList.get(i);

			ArrayList<BasicBlock> temp = new ArrayList<BasicBlock>();

			copyDominatorList(temp, block.getIDom());

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
	}

	public void calculateDominanceFrontiers()
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
					while(runner != block.getIDom().get(0))
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
					System.out.print("B" + dom.getBlockNum());			
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

	public void copyDominatorList(ArrayList<BasicBlock> dest, ArrayList<BasicBlock> src)
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
