package MiniMUDServer;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
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

			while(null != rooms && rooms.next())
			{
				Room newRoom = new Room();
				
				newRoom.setID(rooms.getInt("ID"));
				newRoom.setName(rooms.getString("name"));
				newRoom.setDescription(rooms.getString("description"));
				
				// Make sure that the data fields are valid
				if(newRoom.isValid())
				{		
					// Now, load all of the moves for the room
					ResultSet moves = m_dbConn.getMovesForRoom(newRoom.getID());
					
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
							newRoom.addMove(newMove);													
						}
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
				PlayerMoveMessage moveMsg = (PlayerMoveMessage)msg;
				
				// Get the user's current room
				Room curRoom = user.getCurrentRoom();
				
				// Find the room the move goes to
				int nNextRoom = curRoom.getNextRoomID(moveMsg);
				
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
}
