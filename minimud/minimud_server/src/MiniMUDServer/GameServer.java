package MiniMUDServer;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import MiniMUDShared.*;

public class GameServer
{
	public enum ErrorCode
	{
		Success,
		Exception, 
		ObjectCreationFailed,
		MsgPostFailed
	}
	
	private HashMap<String, UserConnectionThread> m_userMap = new HashMap<String, UserConnectionThread>();
	private HashMap<Integer, Room> m_Rooms = new HashMap<Integer, Room>();
	
	private MMLogger m_logger = null;
	
	private DatabaseConnector m_dbConn = null;
	
	private Room m_startingRoom = null;
	
	public GameServer(DatabaseConnector dbConn, MMLogger logger)
	{
		m_logger = logger;
		m_dbConn = dbConn;
	}
	
	private void setStartingRoom(Room startingRoom)
	{
		m_startingRoom = startingRoom;
	}
	
	private Room getStartingRoom()
	{
		return m_startingRoom;
	}
	
	// Loads game data from MySQL database into memory
	public ErrorCode loadGameData()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Load the Room data types
			ResultSet rooms = m_dbConn.getRooms();

			while(ErrorCode.Success == retVal && null != rooms && rooms.next())
			{
				Room newRoom = new Room();
				
				newRoom.setID(rooms.getInt("ID"));
				newRoom.setName(rooms.getString("name"));
				newRoom.setDescription(rooms.getString("description"));
				
				// Make sure that the data fields are valid
				if(newRoom.isValid())
				{		
					retVal = loadMovesIntoRoom(newRoom);
					
					if(ErrorCode.Success != retVal)
					{
						m_logger.severe("Failed to load moves into room object.");
						break;
					}
					
					retVal = loadNPCsIntoRoom(newRoom);
					
					if(ErrorCode.Success != retVal)
					{
						m_logger.severe("Failed to load NPCs into room object.");
						break;
					}
					
					retVal = loadObjectsIntoRoom(newRoom);
					
					if(ErrorCode.Success != retVal)
					{
						m_logger.severe("Failed to load Objects into room object.");
						break;
					}
					
					// Add the room to the 
					m_Rooms.put(new Integer(newRoom.getID()), newRoom);
					
					// If this is the starting room (always 1) then save it as
					// the starting room
					if(1 == newRoom.getID())
					{
						setStartingRoom(newRoom);
					}
				}
				else
				{
					m_logger.severe("Failed to load room object.  Invalid field value.");
					break;
				}
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception caught in GameServer.postUserMessage(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode loadMovesIntoRoom(Room room)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Now, load all of the moves for the room
			ResultSet moves = m_dbConn.getMovesForRoom(room.getID());
			
			while(null != moves && moves.next())
			{
				Move newMove = new Move();
				
				newMove.setRoomID(moves.getInt("RoomID"));
				newMove.setDirection(moves.getString("direction"));
				newMove.setNextRoomID(moves.getInt("NextRoomID"));
				newMove.setDescription(moves.getString("description"));
				
				// Only add the move if it is valid
				if(newMove.isValid())
				{
					room.addMove(newMove);													
				}
				else
					retVal = ErrorCode.ObjectCreationFailed;
			}
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;		
			m_logger.severe("Exception caught in GameServer.loadMovesIntoRoom()");
		}
		
		return retVal;
	}
	
	private ErrorCode loadNPCsIntoRoom(Room room)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Now, load all of the NPCs for the room
			ResultSet npcs = m_dbConn.getNPCsForRoom(room.getID());
			
			while(null != npcs && npcs.next())
			{
				NPC newNPC = new NPC();
				
				newNPC.setID(npcs.getInt("id"));
				newNPC.setRoomID(npcs.getInt("room"));
				newNPC.setName(npcs.getString("name"));
				newNPC.setDescription(npcs.getString("description"));
				newNPC.setIntro(npcs.getString("intro"));
				
				retVal = loadActionsIntoObject(newNPC);
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("Failed to load Actions into NPC object.");
					break;
				}
				
				// Only add the move if it is valid
				if(newNPC.isValid())
				{
					room.addNPC(newNPC);													
				}
			}
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;		
			m_logger.severe("Exception caught in GameServer.loadNPCsIntoRoom()");
		}
		
		return retVal;
	}
	
	private ErrorCode loadObjectsIntoRoom(Room room)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Now, load all of the NPCs for the room
			ResultSet objects = m_dbConn.getObjectsForRoom(room.getID());
			
			while(null != objects && objects.next())
			{
				GameObject obj = new GameObject();
				
				obj.setID(objects.getInt("id"));
				obj.setRoomID(objects.getInt("room"));
				obj.setName(objects.getString("name"));
				obj.setDescription(objects.getString("description"));
				
				retVal = loadActionsIntoObject(obj);
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("Failed to load Actions into Game object.");
					break;
				}
				
				// Only add the move if it is valid
				if(obj.isValid())
				{
					room.addObject(obj);													
				}
			}
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;		
			m_logger.severe("Exception caught in GameServer.loadObjectsIntoRoom()");
		}
		
		return retVal;
	}
	
	private ErrorCode loadActionsIntoObject(GameObject obj)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Now, load all of the NPCs for the room
			ResultSet actions = m_dbConn.getActionsForObject(obj);
			
			while(null != actions && actions.next())
			{
				Action action = new Action();
				
				action.setID(actions.getInt("ID"));
				action.setName(actions.getString("name"));
				action.setResult(actions.getInt("result"));
				
				// Only add the move if it is valid
				if(action.isValid())
				{
					obj.addAction(action);													
				}
				else
				{
					retVal = ErrorCode.ObjectCreationFailed;
					m_logger.severe("FAILED to load Action into object.");
				}
			}
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;		
			m_logger.severe("Exception caught in GameServer.loadActionsIntoObject()");
		}
		
		return retVal;
	}
	
	public void addUser(UserConnectionThread user)
	{
		// Add the user to the game
		m_logger.info("Adding user " + user.getUserInfo().getUserName() + " to the game.");
		m_userMap.put(user.getUserInfo().getUserName(), user);
				
		m_logger.info("Adding user " + user.getUserInfo().getUserName() + " to starting room " + 
					  getStartingRoom().getName() + ".");
		
		// Set the starting room
		user.setCurrentRoom(getStartingRoom());
		
		// Add the suer to the starting Room
		getStartingRoom().addUser(user);
	}
	
	public void removeUser(UserConnectionThread user)
	{
		m_logger.info("Removing user " + user.getUserInfo().getUserName() + " from the game.");
		
		// Remove the user from their current room
		user.getCurrentRoom().removeUser(user);
		
		// Remove the user from the game
		m_userMap.remove(user.getUserInfo().getUserName());
	}
	
	public synchronized ErrorCode processUserMessage(UserConnectionThread user, Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{		
			m_logger.info("Received message from user (" + user.getUserInfo().getUserName() 
						  + "): " + msg.serialize());
			
			// Process user chat message
			if(MessageID.USER_CHAT == msg.getMessageId())
			{
				UserChatMessage userChat = (UserChatMessage)msg;
				
				userChat.setFromUser(user.getUserInfo().getUserName());
				
				// Method to handle different types of chat commands
				retVal = processChatMessage(userChat);
			}
			else if(MessageID.MOVE == msg.getMessageId())
			{
				retVal = processMoveMessage(user, (PlayerMoveMessage)msg);
			}
			else if(MessageID.ACTION == msg.getMessageId())
			{
				retVal = processActionMessage(user, (PlayerActionMessage)msg);
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception caught in GameServer.postUserMessage(): " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode processChatMessage(UserChatMessage msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		if(UserChatMessage.MsgType.Tell == msg.getMsgType())
		{
			UserConnectionThread user = m_userMap.get(msg.getToUser());
			
			if(null != user)
				user.processGameServerCommand(new ClientShowTextMessage(msg.getFromUser(), msg.getMessage()));
		}
		else if(UserChatMessage.MsgType.Say == msg.getMsgType())
		{
			Iterator<UserConnectionThread> userIter = m_userMap.values().iterator();
			
			while(userIter.hasNext())
			{
				UserConnectionThread user = userIter.next();
				
				user.processGameServerCommand(new ClientShowTextMessage("server", "Invalid chat message"));
			}
		}
		
		return retVal;
	}
	
	public ErrorCode processMoveMessage(UserConnectionThread user, PlayerMoveMessage msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		// Get the user's current room
		Room curRoom = user.getCurrentRoom();
		
		// Find the room the move goes to
		int nNextRoom = curRoom.getNextRoomID(msg);
		
		// If there is no room in that direction, then tell user
		if(-1 == nNextRoom)
		{
			ClientShowTextMessage clMsg = new ClientShowTextMessage("server", "There is nothing in that direction.");
			
			user.processGameServerCommand(clMsg);
		}
		// else handle move
		else
		{
			// Get next room
			Room nextRoom = m_Rooms.get(nNextRoom);
			
			if(null != nextRoom)
			{
				// Remove the user from the current room
				curRoom.removeUser(user);
				
				// Add the user to the new room
				nextRoom.addUser(user);
				
				// Set the new room as the user's current room
				user.setCurrentRoom(nextRoom);
				
				// Have the user display the new room
				user.displayCurrentRoom();
			}
		}	
		
		return retVal;
	}
	
	public ErrorCode processActionMessage(UserConnectionThread user, PlayerActionMessage msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		switch(msg.getAction())
		{
		case Look:
			if(msg.getObject().isEmpty())
			{
				// Display the user's current room
				user.displayCurrentRoom();
				break;
			}

		// If the Look action is aimed at an object or NPC, then
		// We want to fall through to the next case.
			
		case Talk:	
		case Punch:	
		case Kick:	
		case Stab:	
		case Slash:	
		case Push:
		case Shoot:
			Room room = user.getCurrentRoom();
			
			// See if there is a valid NPC in the room
			NPC npc = room.getNPC(msg.getObject());
			GameObject obj = null;
			
			if(null == npc)
			{
				obj = room.getObject(msg.getObject());
			}
			else
				obj = npc;
			
			if(null != obj)
			{
				// If this is a Look message, then show the objects description
				if(PlayerActionMessage.Action.Look == msg.getAction())
				{
					sendUserText(user, "You see " + obj.getDescription());
				}
				else
				{
					// Check to see if this NPC has an action matching the name
					Action action = obj.getAction(msg.getActionString());
					
					if(null != action)
					{
						// There is a valid action, do it and get the result
						
						// Look up the ActionResult from the database
						ActionResult result = m_dbConn.getActionResult(action.getID(), action.getResultID());
						
						if(null != result)
						{
							sendUserText(user, result.getDescription());
							
							// TODO:  Need to handle action types other than text_only
						}
						else
						{
							// There is no valid result.  Tell the user.
							sendUserText(user, "Your action yields no result.");
						}
					}
					else
					{
						// There is no valid action, do it and get the result
						sendUserText(user, "You can't " + msg.getActionString() + " " + msg.getObject());
					}
				}
			}	
			else
			{
				// Tell user that the object doesn't exist
				// There is a valid action, do it and get the result
				sendUserText(user, "You can't do that.  " + msg.getObject() + " is not in the room.");
			}
			break;
			
		default:
			
		}
		
		return retVal;
	}
	
	private static void sendUserText(UserConnectionThread user, String strMsg)
	{
		ClientShowTextMessage clMsg = new ClientShowTextMessage("server", strMsg);
		user.processGameServerCommand(clMsg);
	}
}
