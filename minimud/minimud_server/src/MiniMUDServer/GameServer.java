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
	private HashMap<String, Room> m_Rooms = new HashMap<String, Room>();
	
	private Logger m_logger = null;
	
	private DatabaseConnector m_dbConn = null;
	
	public GameServer(DatabaseConnector dbConn, Logger logger)
	{
		m_logger = logger;
		m_dbConn = dbConn;
	}
	
	// Loads game data from MySQL database into memory
	public ErrorCode loadGameData()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Load the Room datatypes
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
					ResultSet moves = m_dbConn.getMovesForRoom(newRoom.getID());
					
					while(null != moves && moves.next())
					{
						Move newMove = new Move();
						
						newMove.setRoomID(moves.getInt("RoomID"));
						newMove.setDirection(moves.getString("direction"));
						newMove.setNextRoomID(moves.getInt("NextRoomID"));
						newMove.setDescription(moves.getString("description"));
						
						if(newMove.isValid())
							newRoom.addMove(newMove);
					}
					
					m_Rooms.put(newRoom.getName(), newRoom);
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
		m_userMap.put(user.getUserInfo().getUserName(), user);
	}
	
	public synchronized ErrorCode processUserMessage(UserInfo userInfo, Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{		
			if(MessageID.USER_CHAT == msg.getMessageId())
			{
				UserChatMessage userChat = (UserChatMessage)msg;
				
				userChat.setFromUser(userInfo.getUserName());
				
				retVal = processChatMessage(userChat);
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
