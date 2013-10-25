package MiniMUDServer;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

import MiniMUDShared.ClientRequestInputMessage;
import MiniMUDShared.ClientShowTextMessage;
import MiniMUDShared.Message;

public class DisplayHelper
{
	public enum ErrorCode
	{
		Success,
		Exception
	}
	
	private PrintWriter m_textOut;
	private BufferedReader m_textIn;
	
	private static Logger m_logger = null;
	
	public DisplayHelper(PrintWriter textOut, BufferedReader textIn, Logger logger)
	{
		m_textOut = textOut;
		m_textIn = textIn;
		m_logger = logger;
	}
	
	public ErrorCode sendText(String strMessage)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			ClientShowTextMessage msg = new ClientShowTextMessage("server", strMessage);
			
			m_textOut.println(msg.serialize());
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::sendText() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode sendCommand(Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			m_textOut.println(msg.serialize());
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::sendCommand() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public String requestClientInput(ClientRequestInputMessage.Type type, String strMessage)
	{
		String strRet = "";
		
		try
		{
			ClientRequestInputMessage reqInput = new ClientRequestInputMessage(type, strMessage);

			m_textOut.println(reqInput.serialize());
			
			strRet = m_textIn.readLine();
			
			if(null == strRet)
				strRet = "";
		}
		catch(SocketTimeoutException e)
		{
			strRet = null;
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::requestClientInput() - " + e);
			strRet = "";
		}
		
		return strRet;
	}
}
