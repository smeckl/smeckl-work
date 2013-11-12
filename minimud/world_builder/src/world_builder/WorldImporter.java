package world_builder;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;

import minimud_shared.*;

public class WorldImporter
{
	public enum ErrorCode
	{
		Success,
		Exception,
		InsertFailed,
		INVALID_ROOMS_TAG,
		INVALID_ID,
		INVALID_NAME,
		INVALID_DESCRIPTION,
		INVALID_DIRECTION,
		INVALID_INTRO_TEXT,
		INVALID_TYPE,
		INVALID_RESULT,
		INVALID_MOVE,
		INVALID_NPC,
		INVALID_OBJECT,
		INVALID_ACTION,
		INVALID_QUEST,
        INVALID_NUMBER
	}
	
	private String m_strDataFile;
	private DatabaseConnector m_dbConn;
	
	private int m_nNextObjectId = 1; // Includes NPCs and Items
	private int m_nNextActionId = 1;
	private int m_nNextResultId = 1;
	
	public WorldImporter(String strDataFile, DatabaseConnector dbConn)
	{
		setDataFile(strDataFile);
		setDBConn(dbConn);
	}
	
	private void setDataFile(String strDataFile)
	{
		m_strDataFile = strDataFile;
	}
	
	private void setDBConn(DatabaseConnector dbConn)
	{
		m_dbConn = dbConn;
	}
	
	private DatabaseConnector getDBconn()
	{
		return m_dbConn;
	}
	
	public ErrorCode importData()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document doc = db.parse(new File(m_strDataFile));
			
			System.out.println("Parse successful");
			
			retVal = parseAndImportXML(doc);
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.importData(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode parseAndImportXML(Document doc)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			while(true)
			{
				// Get <room> elemsnts
				NodeList rootList = doc.getDocumentElement().getChildNodes();
				
				if(null == rootList)
				{
					retVal = ErrorCode.INVALID_ROOMS_TAG;
					System.out.println("Invalid <rooms> tag.");
					break;
				}
			
				for(int i = 0; i < rootList.getLength(); i++)
				{
					Node node = rootList.item(i);
					
					String strNodeName = node.getNodeName();
					
					// Make sure this is a <room> node
					if(0 == strNodeName.compareTo(XMLNames.ROOM))
					{
						// Process Room node
						retVal = processRoomElement(node);
					}
					else if(0 == strNodeName.compareTo(XMLNames.ITEM))
					{
						// Process item element
						retVal = processItemElement(node);
					}
					else if(0 == strNodeName.compareTo(XMLNames.QUEST))
					{
						retVal = processQuestElement(node);
					}
                    else if(0 == strNodeName.compareTo(XMLNames.MONSTER))
                    {
                        retVal = processMonsterElement(node);
                    }
				}
				
				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.parseAndImportXML(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode processRoomElement(Node room)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		int nID = 0;
		String strName = "";
		String strDescription = "";
		boolean bID = false;
		boolean bName = false;
		boolean bDescription = false;
		boolean bSavedRoom = false;
		boolean bMoves = false;
		boolean bNPCs = false;
		boolean bObjects = false;
		
		RegularExpressions regEx = new RegularExpressions();
		
		try
		{
			NodeList nodes = room.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					// If this is an <id> element, then validate and add to Room object
					if(0 == nodeName.compareTo(XMLNames.ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nID = Integer.parseInt(content);
							bID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
					// If this is a <name> element, then validate and add to Room object
					else if(0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NAME))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NAME;
							System.out.println("Invalid name specified.");
						}
					}
					// If this is a <description> element, then validate and add to Room object
					else if(0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
					// If this is a <move> element, then validate and add to Room object
					else if(0 == nodeName.compareTo(XMLNames.MOVE))
					{
						retVal = processMove(nID, node);
						
						if(ErrorCode.Success == retVal)
							bMoves = true;
						else
							System.out.println("Invalid move specified.");
							
					}
					// If this is a <npc> element, then validate and add to Room object
					else if(0 == nodeName.compareTo(XMLNames.NPC))
					{
						retVal = processNPC(nID, node);
						
						if(ErrorCode.Success == retVal)
							bNPCs = true;
						else
							System.out.println("Invalid NPC specified.");
							
					}
					// If this is a <object> element, then validate and add to Room object
					else if(0 == nodeName.compareTo(XMLNames.OBJECT))
					{
						retVal = processObject(nID, node);
						
						if(ErrorCode.Success == retVal)
							bObjects = true;
						else
							System.out.println("Invalid object specified.");
							
					}
				}
			}
			
			if(!bSavedRoom && bID && bName && bDescription && bMoves)
			{
				getDBconn().addRoom(nID, strName, strDescription);
				bSavedRoom = true;
			}

			if(!bSavedRoom)
			{
				retVal = ErrorCode.INVALID_ROOMS_TAG;
				System.out.println("FAILED to import room element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processRoomElement(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode processItemElement(Node item)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = 0;
		String strName = "";
		String strDescription = "";
		
		boolean bSavedItem = false;
		boolean bID = false;
		boolean bName = false;
		boolean bDescription = false;
		
		try
		{
			NodeList nodes = item.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					// If this is an <id> element, then validate and add to item object
					if(!bSavedItem && 0 == nodeName.compareTo(XMLNames.ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nID = Integer.parseInt(content);
							bID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
					else if(!bSavedItem && 0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NAME))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NAME;
							System.out.println("Invalid name specified.");
						}
					}
					else if(!bSavedItem && 0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
				}
				if(!bSavedItem && bName && bDescription && bID)
				{
					getDBconn().addItem(nID, strName, strDescription);
					bSavedItem = true;
				}
			}
			
			if(!bSavedItem)
			{
				retVal = ErrorCode.INVALID_OBJECT;
				System.out.println("FAILED to import object element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processNPC(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
	
	private ErrorCode processQuestElement(Node quest)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = 0;
		String strName = "";
		int nRewardXP = 0;
		int nRewardGold = 0;
		int nRewardItem = 0;
		int nFirstBonus = 0;
		
		boolean bSavedQuest = false;
		boolean bID = false;
		boolean bName = false;
		boolean bRewardXP = false;
		boolean bRewardGold = false;
		boolean bRewardItem = false;
		boolean bFirstBonus = false;
		boolean bSteps = false;
		
		try
		{
			NodeList nodes = quest.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					// If this is an <id> element, then validate and add to item object
					if(0 == nodeName.compareTo(XMLNames.ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nID = Integer.parseInt(content);
							bID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NAME))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NAME;
							System.out.println("Invalid name specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.REWARD_XP))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nRewardXP = Integer.parseInt(content);
							bRewardXP = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid REWARD_XP specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.FIRST_BONUS))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nFirstBonus = Integer.parseInt(content);
							bFirstBonus = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid FIRST_BONUS specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.REWARD_GOLD))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nRewardGold = Integer.parseInt(content);
							bRewardGold = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid REWARD_GOLD specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.REWARD_ITEM))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nRewardItem = Integer.parseInt(content);
							bRewardItem = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid REWARD_ITEM specified.");
						}
					}
					// If this is a <quest_step> element, then validate and add to questobject
					else if(0 == nodeName.compareTo(XMLNames.QUEST_STEP))
					{
						retVal = processQuestStep(nID, node);
						
						if(ErrorCode.Success == retVal)
							bSteps = true;
						else
							System.out.println("Invalid quest_step specified.");
							
					}
				}
			}
			
			if(!bSavedQuest && bName && bID && bRewardXP && bFirstBonus)
			{
				getDBconn().addQuest(nID, strName, nRewardXP, nFirstBonus, nRewardGold, nRewardItem);
				bSavedQuest = true;
			}
			
			if(!bSavedQuest)
			{
				retVal = ErrorCode.INVALID_OBJECT;
				System.out.println("FAILED to import quest element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processQuestElement(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
    
    private ErrorCode processMonsterElement(Node monster)
    {
        ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = 0;
		String strName = "";
        String strDescription = "";
        int nHealth = 0;
        int nAttackPower = 0;
        int nMagicPower = 0;
        int nDefense = 0;
        int nMagicDefense = 0;
        int nLootTableID = 0;
        
        boolean bSavedMonster = false;
        boolean bID = false;
        boolean bName = false;
        boolean bDescription = false;
        boolean bHealth = false;
        boolean bAttackPower = false;
        boolean bMagicPower = false;
        boolean bDefense = false;
        boolean bMagicDefense = false;
        boolean bLootTableID = false;
        
        try
        {
            NodeList nodes = monster.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
                    
                    if(0 == nodeName.compareTo(XMLNames.ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nID = Integer.parseInt(content);
							bID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NAME))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NAME;
							System.out.println("Invalid name specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.HEALTH))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.POSITIVE_INT))
						{
							nHealth = Integer.parseInt(content);
							bHealth = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NUMBER;
							System.out.println("Invalid HEALTH specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.ATTACK_POWER))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.POSITIVE_INT))
						{
							nAttackPower = Integer.parseInt(content);
							bAttackPower = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NUMBER;
							System.out.println("Invalid ATTACK POWER specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.MAGIC_POWER))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.POSITIVE_INT))
						{
							nMagicPower = Integer.parseInt(content);
							bMagicPower = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NUMBER;
							System.out.println("Invalid ATTACK POWER specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.DEFENSE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.POSITIVE_INT))
						{
							nDefense = Integer.parseInt(content);
							bDefense = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NUMBER;
							System.out.println("Invalid DEFENSE specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.MAGIC_DEFENSE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.POSITIVE_INT))
						{
							nMagicDefense = Integer.parseInt(content);
							bMagicDefense = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NUMBER;
							System.out.println("Invalid MAGIC DEFENSE specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.LOOT_TABLE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.POSITIVE_INT))
						{
							nLootTableID = Integer.parseInt(content);
							bLootTableID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NUMBER;
							System.out.println("Invalid LOOT TABLE ID specified.");
						}
					}
                }                                
            }
            
            if(!bSavedMonster && bID && bName && bDescription && bHealth && bAttackPower && bMagicPower
                        && bDefense && bMagicDefense && bLootTableID)
            {
                getDBconn().addMonster(nID, strName, strDescription, nHealth,
                        nAttackPower, nMagicPower, nDefense, nMagicDefense, nLootTableID);

                bSavedMonster = true;
            }

            if(!bSavedMonster)
            {
                retVal = ErrorCode.INVALID_OBJECT;
                System.out.println("FAILED to import MONSTER element.");
            }
        }
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processQuestElement(): " + e);
			retVal = ErrorCode.Exception;
		}
        
        return retVal;
    }
	
	private ErrorCode processQuestStep(int nQuestID, Node quest)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = 0;
		int nStepNum = 0;
		String strDescription = "";
		String strHint = "";
		int nRewardXP = 0;
		int nRewardGold = 0;
		int nRewardItem = 0;
		int nFirstBonus = 0;
		
		boolean bSavedQuestStep = false;
		boolean bID = false;
		boolean bStepNum = false;
		boolean bDescription = false;
		boolean bHint = false;
		boolean bRewardXP = false;
		boolean bRewardGold = false;
		boolean bRewardItem = false;
		
		try
		{
			NodeList nodes = quest.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					if(0 == nodeName.compareTo(XMLNames.STEP_NUMBER))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nStepNum = Integer.parseInt(content);
							bStepNum = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid step_num specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.HINT))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.HINT))
						{
							strHint = content;
							bHint = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.REWARD_XP))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nRewardXP = Integer.parseInt(content);
							bRewardXP = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid REWARD_XP specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.REWARD_GOLD))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nRewardGold = Integer.parseInt(content);
							bRewardGold = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid REWARD_GOLD specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.REWARD_ITEM))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nRewardItem = Integer.parseInt(content);
							bRewardItem = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_QUEST;
							System.out.println("Invalid REWARD_ITEM specified.");
						}
					}
				}
			}
			
			if(!bSavedQuestStep && bStepNum && bDescription && bHint && bRewardXP)
			{
				getDBconn().addQuestStep(nID, nStepNum, strDescription, strHint, nRewardXP, nRewardGold, nRewardItem);
				bSavedQuestStep = true;
			}
			
			if(!bSavedQuestStep)
			{
				retVal = ErrorCode.INVALID_OBJECT;
				System.out.println("FAILED to import quest_step element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processQuestStep(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
	
	private ErrorCode processMove(int nID, Node move)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		String strDirection = "";
		int nNextRoomID = 0;
		String strDescription = "";
		
		boolean bSavedMove = false;
		boolean bDirection = false;
		boolean bNextRoomID = false;
		boolean bDescription = false;
		
		try
		{
			NodeList nodes = move.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					if(!bSavedMove && 0 == nodeName.compareTo(XMLNames.DIRECTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DIRECTION))
						{
							strDirection = content;
							bDirection = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DIRECTION;
							System.out.println("Invalid direction specified.");
						}
					}
					else if(!bSavedMove && 0 == nodeName.compareTo(XMLNames.NEXT_ROOM_ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nNextRoomID = Integer.parseInt(content);
							bNextRoomID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid Next Room ID specified.");
						}
					}
					else if(!bSavedMove && 0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
					
					if(bDirection && bNextRoomID && bDescription)
					{
						getDBconn().addMove(nID, strDirection, nNextRoomID, strDescription);
						bSavedMove = true;
					}
				}
			}
			
			if(!bSavedMove)
			{
				retVal = ErrorCode.INVALID_MOVE;
				System.out.println("FAILED to import move element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processRoomElement(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode processNPC(int nRoomID, Node move)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nNpcID = m_nNextObjectId;
		String strName = "";
		String strDescription = "";
		String strIntro = "";
		
		boolean bSavedNPC = false;
		boolean bName = false;
		boolean bDescription = false;
		boolean bIntro = false;
		boolean bActions = false;
		
		try
		{
			NodeList nodes = move.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					if(0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NAME))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NAME;
							System.out.println("Invalid name specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.INTRO))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NPCTEXT))
						{
							strIntro = content;
							bIntro = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_INTRO_TEXT;
							System.out.println("Invalid intro specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.ACTION))
					{
						retVal = processAction(nNpcID, node);
						
						if(ErrorCode.Success == retVal)
							bActions = true;
						else
							System.out.println("Invalid action specified.");
					}
				}
			}
			
			if(!bSavedNPC && bName && bDescription && bIntro && bActions)
			{
				getDBconn().addNPC(nRoomID, nNpcID, strName, strDescription, strIntro);
				bSavedNPC = true;
				
				//Increment NPC ID
				m_nNextObjectId++;
			}
			
			if(!bSavedNPC)
			{
				retVal = ErrorCode.INVALID_NPC;
				System.out.println("FAILED to import NPC element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processNPC(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
	
	private ErrorCode processObject(int nRoomID, Node object)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nObjectID = m_nNextObjectId;
		String strName = "";
		String strDescription = "";
		
		boolean bSavedObject= false;
		boolean bName = false;
		boolean bDescription = false;
		boolean bActions = false;
		
		try
		{
			NodeList nodes = object.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					if(!bSavedObject && 0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.NAME))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_NAME;
							System.out.println("Invalid name specified.");
						}
					}
					else if(!bSavedObject && 0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_DESCRIPTION;
							System.out.println("Invalid description specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.ACTION))
					{
						retVal = processAction(nObjectID, node);
						
						if(ErrorCode.Success == retVal)
							bActions = true;
						else
							System.out.println("Invalid action specified.");
					}
				}
				if(!bSavedObject && bName && bDescription && bActions)
				{
					getDBconn().addObject(nRoomID, nObjectID, strName, strDescription);
					bSavedObject = true;
					
					//Increment Object ID
					m_nNextObjectId++;
				}
			}
			
			if(!bSavedObject)
			{
				retVal = ErrorCode.INVALID_OBJECT;
				System.out.println("FAILED to import object element.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processNPC(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
	
	private ErrorCode processAction(int nParentID, Node object)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = m_nNextActionId;
		String strName = "";
		int nDependencyID = 0;
		int nDepndencyStep = 0;
        int nDepdencyComplete = 0;
		int nResult = 0;
		
		boolean bSavedAction = false;
		boolean bName = false;
		boolean bResult = false;
		
		try
		{
			NodeList nodes = object.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					if(0 == nodeName.compareTo(XMLNames.NAME))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ACTION_TYPE))
						{
							strName = content;
							bName = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_TYPE;
							System.out.println("Invalid type specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.QUEST_DEP_ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nDependencyID = Integer.parseInt(content);
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid Quest Dependency ID specified.");
						}
					}
					else if(0 == nodeName.compareTo(XMLNames.QUEST_DEP_STEP))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nDepndencyStep = Integer.parseInt(content);
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid Quest Step Dependency specified.");
						}
					}
                    else if(0 == nodeName.compareTo(XMLNames.QUEST_DEP_COMPLETE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.QUEST_DEP_COMPLETE))
						{
							nDepdencyComplete = Integer.parseInt(content);
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid Quest Step Completion specified.");
						}
					}
					else if(!bSavedAction && 0 == nodeName.compareTo(XMLNames.RESULT))
					{
						nResult = processActionResult(nID, node);
						
						if(0 != nResult)
							bResult = true;
						else
							System.out.println("Invalid result specified.");
					}
				}
			}
			
			if(!bSavedAction && bName && bResult)
			{
				getDBconn().addAction(nParentID, nID, strName, nDependencyID, nDepndencyStep, nDepdencyComplete, nResult);
				bSavedAction = true;
				
				//Increment Action ID
				m_nNextActionId++;
			}
			
			if(!bSavedAction)
			{
				retVal = ErrorCode.INVALID_ACTION;
				System.out.println("FAILED to import action element");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processNPC(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
	
	private int processActionResult(int nParentID, Node object)
	{
		int retVal = 0;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = m_nNextResultId;
		String strType = "";
		String strDescription = "";
		int nItemID = 0;
		int nValue = 0;
		
		boolean bSavedActionResult = false;
		boolean bType = false;
		boolean bDescription = false;
		boolean bItemID = false;
		boolean bValue = false;
		
		try
		{
			NodeList nodes = object.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				
				// Make sure this is an element
				if (node instanceof Element)
				{
					String content = node.getLastChild().getTextContent().trim();
					
					String nodeName = node.getNodeName();
					
					if(!bSavedActionResult && 0 == nodeName.compareTo(XMLNames.TYPE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.RESULT_TYPE))
						{
							strType = content;
							bType = true;
						}
						else
						{
							System.out.println("Invalid type specified: " + content);
						}
					}
					else if(!bSavedActionResult && 0 == nodeName.compareTo(XMLNames.DESCRIPTION))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.DESCRIPTION))
						{
							strDescription = content;
							bDescription = true;
						}
						else
						{
							System.out.println("Invalid description specified.");
						}
					}
					else if(!bSavedActionResult && 0 == nodeName.compareTo(XMLNames.ITEM_ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nItemID = Integer.parseInt(content);
							bItemID = true;
						}
						else
						{
							System.out.println("Invalid ID specified.");
						}
					}
					else if(!bSavedActionResult && 0 == nodeName.compareTo(XMLNames.VALUE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nValue = Integer.parseInt(content);
							bValue = true;
						}
						else
						{
							System.out.println("Invalid ID specified.");
						}
					}
				}
			}
			
			// ItemID and Value are optional elements
			if(!bSavedActionResult && bType && bDescription)
			{
				getDBconn().addActionResult(nID, nParentID, strType, strDescription, nItemID, nValue);
				bSavedActionResult = true;
				
				retVal = nID;
				
				// Increment Result ID
				m_nNextResultId++;
			}
			
			if(!bSavedActionResult)
			{
				System.out.println("FAILED to import action_result element.  Desc = " + strDescription);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processNPC(): " + e);
			retVal = 0;
		}
		
		return retVal;	
	}
}
