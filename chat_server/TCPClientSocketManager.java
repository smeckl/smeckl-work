package project2;

// List of imported objects
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.net.SocketTimeoutException;

/**
 * @author Steven Meckl, G00459475
 * 
 * This command is responsible for implementing the ClientSocketManager 
 * Interface for TCP client sockets.
 */
public class TCPClientSocketManager 
        implements ClientSocketManager
{
    // Timeout used in the receive() call, so that the call does not block
    // forever.
    private static final int RECEIVE_TIMEOUT = 1000;
    
    // The TCP socket used by the class
    private Socket m_socket = null;
    
    // The Buffered Reader object is used to read from the socket
    private BufferedReader m_input = null;
    
    // The DataOutputStream object is used to write to the socket
    private DataOutputStream m_output = null;
    
    // Because this class implements the ClientSocketManager interface and different
    // implementations of that interface may require different information during
    // construction, this class uses the default constructor and the object is
    // initialized using this method.
    // 
    // This initializer uses an address and port, creating a new socket
    public boolean Initialize(String address, int nPort)
    {
        boolean bRetVal = true;
        
        try
        {
            // Create a new TCP socket
            Socket socket = new Socket(address, nPort);                        
            Initialize(socket);
        }
        catch (IOException e)
        {            
            System.out.println("Encountered exception in TCPClientSocketManager::Initialize(): " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // Because this class implements the ClientSocketManager interface and different
    // implementations of that interface may require different information during
    // construction, this class uses the default constructor and the object is
    // initialized using this method.
    // 
    // This alternate initialzer uses an existing socket
    public boolean Initialize(Socket socket)
    {
         boolean bRetVal = true;
        
        try
        {
            // Init the socket
            m_socket = socket;            
            
            m_socket.setSoTimeout(RECEIVE_TIMEOUT);
            
            // Create the BufferedReader and attach it to the socket
            m_input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
            
            // Create the DataOutputStream and attach it to the socket
            m_output = new DataOutputStream(m_socket.getOutputStream());
        }
        catch (IOException e)
        {            
            System.out.println("Encountered exception in TCPClientSocketManager::Initialize(): " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // This method closes the socket and associated read/write objects
    public synchronized boolean Close()
    {
        boolean bRetVal = true;
        
        try
        {
            m_output.close();
            m_input.close();
            m_socket.close();
        }
        catch (IOException e)
        {            
            System.out.println("Encountered exception in TCPClientSocketManager::Close(): " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // This method determines if the socket is closed or not
    public synchronized boolean IsClosed()
    {
        return m_socket.isClosed();
    }
    
    // Sends a message to a host over the TCP socket
    public boolean SendMessage(String message)
    {
        boolean bRetVal = true;
        
        try
        {
            // writeBytes() is used so that the String object is marshalled 
            // correctly.  Because Strings are Unicode, they need to be written
            // as Unicode bytes instead of an ASCII string.
            m_output.writeBytes(message);
        }
        catch (IOException e)
        {            
            System.out.println("Encountered exception in TCPClientSocketManager::SendMessage(): " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
  
    // Determines if there is a message ready to be read
    public boolean MessageReady()
    {
        boolean bRetVal = true;
        
        try
        {
            bRetVal = m_input.ready();
        }
        catch(IOException e)
        {
            System.out.println("Encountered exception in TCPClientSocketManager::MessageBody(): " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // Reads one line of text from the TCP socket
    public String ReceiveMessage()
    {
        String out = null;
        
        try
        {                        
            out = m_input.readLine();
        }
        // If receive() call timed out, then return null
        catch(SocketTimeoutException e)
        {
            out = null;
        }
        // An IO exception was caught.  Return null
        catch(IOException e)
        {
            out = null;
        }
        
        return out;
    }
    
    // Returns the address of the remote host in String format
    public String GetAddress()
    {
        return m_socket.getInetAddress().getHostAddress();
    }
    
    // Returns the port of the remote host
    public int GetPort()
    {
        return m_socket.getLocalPort();
    }
}
