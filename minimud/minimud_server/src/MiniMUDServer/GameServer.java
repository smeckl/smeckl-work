package MiniMUDServer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
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
	
	private Logger m_logger = null;
	
	public GameServer(Logger logger)
	{
		m_logger = logger;
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
			System.out.println("Exception caught in GameServer.postUserMessage(): " + e);
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
