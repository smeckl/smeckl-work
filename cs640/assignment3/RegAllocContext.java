import java.util.Enumeration;
import java.util.Hashtable;

public class RegAllocContext
	{
		public Hashtable<String,String> regMap = null;
		public Hashtable<String,String> variableLocs = null;
		public Hashtable<String,Instruction> mapRegToInstruction = new Hashtable<String,Instruction>();
		public Hashtable<String, Set<String>> varAssignedRegisters = null;
		public String strLastTempReg = "";
		public Set<String> m_variables = new Set<String>();
		
		public RegAllocContext(LineList lineList, int nMaxRegisters)
		{
			regMap = initializeRegMap(nMaxRegisters);
			variableLocs = initializeVariableLocations(lineList);
			varAssignedRegisters = initializeVariableAssignedRegisters();
		}
/*		
		public RegAllocContext(RegAllocContext right)
		{
			regMap = new Hashtable<String,String>();
			copyHashStringToString(regMap, right.regMap);
			
			variableLocs = new Hashtable<String,String>();
			copyHashStringToString(variableLocs, right.variableLocs);
			
			copyHashStringToInstruction(mapRegToInstruction, right.mapRegToInstruction);
			
			varAssignedRegisters = initializeVariableAssignedRegisters();
			copyHashStringToStringSet(varAssignedRegisters, right.varAssignedRegisters);

			strLastTempReg = right.strLastTempReg;
			m_variables.copy(right.m_variables);
		}
*/		
		private void copyHashStringToString(Hashtable<String, String> dest, 
											Hashtable<String, String> src)
        {
                for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
                {
                        String str = e.nextElement();
                        dest.put(str, src.get(str));
                }
        }

        private void copyHashStringToInstruction(Hashtable<String, Instruction> dest, 
        										 Hashtable<String, Instruction> src)
        {
                for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
                {
                        String str = e.nextElement();
                        dest.put(str, src.get(str));
                }
        }
        
        private void copyHashStringToStringSet(Hashtable<String, Set<String>> dest, 
        									 Hashtable<String, Set<String>> src)
        {
                for (Enumeration<String> e = src.keys(); e.hasMoreElements();)
                {
                        String str = e.nextElement();
                        
                        Set<String> srcSet = src.get(str);
                        Set<String> destSet = new Set<String>(srcSet);
                        dest.put(str, destSet);
                }
        }
		
		public Set<String> getVariables()
		{
			return m_variables;
		}

		public void setVariables(Set<String> variables)
		{
			m_variables = variables;
		}
		
		private Hashtable<String,String> initializeRegMap(int nMaxRegisters)
		{
			Hashtable<String,String> newRegMap = new Hashtable<String,String>();
			
			for(int i = 0; i < nMaxRegisters; i++)
			{
				String strReg = "$t" + i;
				
				newRegMap.put(strReg, "");
			}
			
			return newRegMap;
		}
		
		public Hashtable<String,String> initializeVariableLocations(LineList lineList)
		{
			buildVariableList(lineList);
			
			Hashtable<String,String> newVariableMap = new Hashtable<String,String>();
			
			for(int i = 0; i < getVariables().size(); i++)
			{	
				newVariableMap.put(getVariables().get(i), "m");
			}
			
			return newVariableMap;
		}
		
		private Hashtable<String,Set<String>> initializeVariableAssignedRegisters()
		{
			Hashtable<String,Set<String>> newRegRefCount = new Hashtable<String,Set<String>>();
			
			for(int i = 0; i < getVariables().size(); i++)
			{		
				String strVar = getVariables().get(i);
				newRegRefCount.put(strVar, new Set<String>());
			}
			
			return newRegRefCount;
		}
		
		private void buildVariableList(LineList lineList)
		{
			for(int i = lineList.getNumItems() - 1; i >= 0; i--)
			{
				Line line = lineList.getItemAtIndex(i);			
				
				if(Line.Type.INSTRUCTION == line.getType())
				{
					Instruction instr = (Instruction)line;
					
					// If this is a SW instruction
					if(0 == instr.getInstruction().compareTo("sw"))
					{
						getVariables().add(instr.getArgument2().getName());
					}
					// If this is a LW instruction
					else if(0 == instr.getInstruction().compareTo("lw")
							|| 0 == instr.getInstruction().compareTo("li"))		
					{
						getVariables().add(instr.getArgument2().getName());
					}
				}
			}
		}
	};
