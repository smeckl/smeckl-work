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
		INVALID_DIRECTION
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
				// Get <room>
				NodeList rootList = doc.getDocumentElement().getElementsByTagName(XMLNames.ROOMS);
				
				if(null == rootList || rootList.getLength() != 1)
				{
					retVal = ErrorCode.INVALID_ROOMS_TAG;
					System.out.println("Invalid <rooms> tag.");
					break;
				}
			
				Node rooms = rootList.item(0);
				
				NodeList roomList = rooms.getChildNodes();
				
				if(null == roomList || 0 == roomList.getLength())
				{
					retVal = ErrorCode.INVALID_ROOMS_TAG;
					System.out.println("Invalid <rooms> tag.");
					break;
				}
				
				for(int i = 0; i < roomList.getLength(); i++)
				{
					Node room = roomList.item(i);
					
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
		
		RegularExpressions regEx = new RegularExpressions();
		
		try
		{
			while(true)
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
						else if(0 == nodeName.compareTo(XMLNames.MOVES))
						{
							retVal = processMoves(node);
						}
							
						if(!bSavedRoom && bID && bName && bDescription)
						{
							DatabaseConnector.ErrorCode err = getDBconn().addRoom(nID, strName, strDescription);
							bSavedRoom = true;
						}
					}
				}

				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processRoomElement(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode processMoves(Node moves)
	{
		ErrorCode retVal = ErrorCode.Success;
				
		try
		{
			while(true)
			{
				NodeList nodes = moves.getChildNodes();
				
				for(int i = 0; i < nodes.getLength(); i++)
				{
					Node move = nodes.item(i);
					
					// Make sure this is an element
					if (move instanceof Element)
					{						
						String nodeName = move.getNodeName();
						
						if(0 == nodeName.compareTo(XMLNames.MOVE))
						{
							retVal = processMove(move);
						}
					}
				}
				
				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processRoomElement(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode processMove(Node move)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		RegularExpressions regEx = new RegularExpressions();
		
		int nRoomID = 0;
		String strDirection = "";
		int nNextRoomID = 0;
		String strDescription = "";
		
		boolean bRoomID = false;
		boolean bDirection = false;
		boolean bNextRoomID = false;
		boolean bDescription = false;
		
		try
		{
			while(true)
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
						
						if(0 == nodeName.compareTo(XMLNames.ROOM_ID))
						{
							if(regEx.stringMatchesRegEx(content, RegularExpressions.RegExID.ID))
							{
								nRoomID = Integer.parseInt(content);
								bRoomID = true;
							}
							else
							{
								retVal = ErrorCode.INVALID_ID;
								System.out.println("Invalid Room ID specified.");
							}
						}
						else if(0 == nodeName.compareTo(XMLNames.DIRECTION))
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
						else if(0 == nodeName.compareTo(XMLNames.NEXT_ROOM_ID))
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
						
						if(bRoomID && bDirection && bNextRoomID && bDescription)
						{
							getDBconn().addMove(nRoomID, strDirection, nNextRoomID, strDescription);
						}
					}
				}
				
				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in WorldImporter.processRoomElement(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
}
