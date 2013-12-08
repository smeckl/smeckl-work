package minimud_server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;

public class MMLogger
{
	private Logger m_logger = null;
	Handler m_fh = null;
	
	public MMLogger(String strLogFile) throws SecurityException, IOException
	{  
		try
		{
			m_logger = Logger.getLogger(MiniMUDServer.class.getName());
			
			// Set up the logger object
			if(!strLogFile.isEmpty())
            {
				m_fh = new FileHandler(strLogFile, true);
                m_fh.setFormatter(new SimpleFormatter());
			
                // Remove existing handlers
                Handler handlers [] = m_logger.getHandlers();

                for(int i = 0; i < handlers.length; i++)
                    m_logger.removeHandler(handlers[i]);

                // Add the new file handler
                m_logger.addHandler(m_fh);
            }
            
			m_logger.setLevel(Level.FINE);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void info(String strLog)
	{
		String strSafeLog = escapeString(strLog);
		
		m_logger.info(strSafeLog);
	}
	
	public void severe(String strLog)
	{
		String strSafeLog = escapeString(strLog);
		
		m_logger.severe(strSafeLog);
	}
	
	private String escapeString(String strLog)
	{
		return StringEscapeUtils.escapeJava(strLog);
	}
}
