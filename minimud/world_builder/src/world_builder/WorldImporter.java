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

import MiniMUDShared.*;

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
		INVALID_ACTION
	}
	
	private String m_strDataFile;
	private DatabaseConnector m_dbConn;
	
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
					Node room = rootList.item(i);
					
					String strNodeName = room.getNodeName();
					
					// Make sure this is a <room> node
					if(0 == strNodeName.compareTo(XMLNames.ROOM))
					{
						// Process Room node
						retVal = processRoomElement(room);
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
					if(!bSavedRoom && 0 == nodeName.compareTo(XMLNames.ID))
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
					else if(!bSavedRoom && 0 == nodeName.compareTo(XMLNames.NAME))
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
					else if(!bSavedRoom && 0 == nodeName.compareTo(XMLNames.DESCRIPTION))
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
							bMoves = true;
						else
							System.out.println("Invalid NPC specified.");
							
					}
					// If this is a <npc> element, then validate and add to Room object
					else if(0 == nodeName.compareTo(XMLNames.OBJECT))
					{
						retVal = processObject(nID, node);
						
						if(ErrorCode.Success == retVal)
							bMoves = true;
						else
							System.out.println("Invalid object specified.");
							
					}
					
						
					if(!bSavedRoom && bID && bName && bDescription && bMoves)
					{
						getDBconn().addRoom(nID, strName, strDescription);
						bSavedRoom = true;
					}
				}
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
		
		int nNpcID = 0;
		String strName = "";
		String strDescription = "";
		String strIntro = "";
		
		boolean bSavedNPC = false;
		boolean bNpcID = false;
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
					
					// If this is an <id> element, then validate and add to Room object
					if(!bSavedNPC && 0 == nodeName.compareTo(XMLNames.ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nNpcID = Integer.parseInt(content);
							bNpcID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
					else if(!bSavedNPC && 0 == nodeName.compareTo(XMLNames.NAME))
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
					else if(!bSavedNPC && 0 == nodeName.compareTo(XMLNames.DESCRIPTION))
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
					else if(!bSavedNPC && 0 == nodeName.compareTo(XMLNames.INTRO))
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
					else if(!bSavedNPC && 0 == nodeName.compareTo(XMLNames.ACTION))
					{
						retVal = processAction(nNpcID, node);
						
						if(ErrorCode.Success == retVal)
							bActions = true;
						else
							System.out.println("Invalid action specified.");
					}
				}
				if(!bSavedNPC && bNpcID && bName && bDescription && bIntro && bActions)
				{
					getDBconn().addNPC(nRoomID, nNpcID, strName, strDescription, strIntro);
					bSavedNPC = true;
				}
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
		
		int nObjectID = 0;
		String strName = "";
		String strDescription = "";
		
		boolean bSavedObject= false;
		boolean bObjectID = false;
		boolean bName = false;
		boolean bDescription = false;
		
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
					
					// If this is an <id> element, then validate and add to Room object
					if(!bSavedObject && 0 == nodeName.compareTo(XMLNames.ID))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
						{
							nObjectID = Integer.parseInt(content);
							bObjectID = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
					else if(!bSavedObject && 0 == nodeName.compareTo(XMLNames.NAME))
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
				}
				if(!bSavedObject && bObjectID && bName && bDescription)
				{
					getDBconn().addObject(nRoomID, nObjectID, strName, strDescription);
					bSavedObject = true;
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
		
		int nID = 0;
		String strName = "";
		int nResult = 0;
		
		boolean bSavedAction = false;
		boolean bID = false;
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
					
					// If this is an <id> element, then validate and add to Room object
					if(!bSavedAction && 0 == nodeName.compareTo(XMLNames.ID))
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
					else if(!bSavedAction && 0 == nodeName.compareTo(XMLNames.NAME))
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
					else if(!bSavedAction && 0 == nodeName.compareTo(XMLNames.RESULT))
					{
						retVal = processActionResult(nID, node);
						
						if(ErrorCode.Success == retVal)
							bResult = true;
						else
							System.out.println("Invalid action specified.");
					}
				}
				if(!bSavedAction && bID && bName && bResult)
				{
					getDBconn().addAction(nParentID, nID, strName, nResult);
					bSavedAction = true;
				}
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
	
	private ErrorCode processActionResult(int nParentID, Node object)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nID = 0;
		String strType = "";
		String strDescription = "";
		int nItemID = 0;
		int nValue = 0;
		
		boolean bSavedActionResult = false;
		boolean bID = false;
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
					
					// If this is an <id> element, then validate and add to Room object
					if(!bSavedActionResult && 0 == nodeName.compareTo(XMLNames.ID))
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
					else if(!bSavedActionResult && 0 == nodeName.compareTo(XMLNames.TYPE))
					{
						if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.RESULT_TYPE))
						{
							strType = content;
							bType = true;
						}
						else
						{
							retVal = ErrorCode.INVALID_TYPE;
							System.out.println("Invalid type specified.");
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
							retVal = ErrorCode.INVALID_DESCRIPTION;
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
							retVal = ErrorCode.INVALID_ID;
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
							retVal = ErrorCode.INVALID_ID;
							System.out.println("Invalid ID specified.");
						}
					}
				}
			}
			
			// ItemID and Value are optional elements
			if(!bSavedActionResult && bID && bType && bDescription)
			{
				getDBconn().addActionResult(nParentID, nID, strType, strDescription, nItemID, nValue);
				bSavedActionResult = true;
			}
			
			if(!bSavedActionResult)
			{
				retVal = ErrorCode.INVALID_RESULT;
				System.out.println("FAILED to import action_result element");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processNPC(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;	
	}
}
