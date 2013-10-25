package project2.server.TCP;

// Imported objects
import project2.server.*;
import project2.ClientSocketManager;
import project2.TCPClientSocketManager;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

/**
 * @author Steven Meckl, G00459475
 * 
 * This class is responsible for implementing the ServerSocketManager interface
 * for TCP.
 */
public class TCPServerSocketManager 
        implements ServerSocketManager
{
    // The Java ServerSocket object (TCP).
    private static ServerSocket m_serverSocket  = null;
    
    // Initializes the object to listen on a specified port.
    public boolean Initialize(int nPort)
    {
        boolean bRetVal = true;
        
        try
        {        
            // Creates a new server socket object and binds it to the specified
            // port.
            System.out.println("Waiting on socket " + nPort);
            m_serverSocket = new ServerSocket(9001);                        
        }
        catch(IOException e)
        {
            System.out.println("Encountered exception in TCPServerSocketManager.Initialize(): " + e);
            bRetVal = false;
        }      
        
        return bRetVal;
    }
    
    // Attempts to accept a new connection to the server.
    public ClientSocketManager Accept()
    {
        Socket sock = null;
        TCPClientSocketManager  sockMgr = null;
        
        try
        {
            // Attempt to accept a connection.  This call blocks until a new
            // connection is received or the socket is closed by another thread.
            sock = m_serverSocket.accept();
            
            if(null != sock)
            {     
                // The connection was made, create a new TCP client connection
                // and initialize it with the new socket.
                sockMgr = new TCPClientSocketManager();
                
                sockMgr.Initialize(sock);
            }
        }
        catch(IOException e)
        {
            System.out.println("Encountered exception in TCPServerSocketManager.Accept(): " + e);
        }
        
        return sockMgr;
    }
    
    // Closes the TCP socket.
    public boolean Close()
    {
        boolean bRetVal = true;
        
        try
        {
            m_serverSocket.close();
        }
        catch(IOException e)
        {
            bRetVal = false;
        }
        
        return bRetVal;
    }
}
