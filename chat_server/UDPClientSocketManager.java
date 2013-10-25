package project2;

// Imported objects
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/*
 * @author Steven Meckl, G00459475
 * 
 * This command is responsible for implementing the ClientSocketManager 
 * Interface for UDP client sockets.
 */
public class UDPClientSocketManager 
        implements ClientSocketManager
{
    // Timeout used in the receive() call, so that the call does not block
    // forever.
    private static final int RECEIVE_TIMEOUT = 1000;
    
    // The DatagramSocket used by this class for communication
    private DatagramSocket m_sock = null;
    
    // Address of the remote host
    private InetAddress m_addr = null;    
    
    // Port of the remote host
    private int m_nPort = 0;
    
    // Because this class implements the ClientSocketManager interface and different
    // implementations of that interface may require different information during
    // construction, this class uses the default constructor and the object is
    // initialized using this method.
    public boolean Initialize(String address, int nPort, boolean bConnect)
    {
        boolean bRetVal = true;
        
        // Set the port
        m_nPort = nPort;
        
        try
        {
            // Convert the String address into an InetAddress object
            m_addr = InetAddress.getByName(address);
            
            // Create a new DatagramSocket object
            m_sock = new DatagramSocket();
            
            // Set the timeout to be used on DatagramSocket.receive() calls.
            m_sock.setSoTimeout(RECEIVE_TIMEOUT);
        
            // If asked to connect, then do so
            if(bConnect)
                m_sock.connect(m_addr, nPort);                
        }
        catch(UnknownHostException e)
        {
            System.out.println("Failed to connnect: " + e);
            bRetVal = false;
        }
        catch(SocketException e)
        {
            System.out.println("Failed to connnect: " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // Closes the socket
    public boolean Close()
    {
        boolean bRetVal = true;
        
        m_sock.close();
        
        return bRetVal;
    }
    
    // Determines if the socket is closed
    public boolean IsClosed()
    {        
        return m_sock.isClosed();        
    }
    
    // Sends a text message using the socket
    public boolean SendMessage(String message)
    {
        boolean bRetVal = true;
        
        try
        {
            // Convert the String into a byte array
            byte[] byteArray = message.getBytes();

            // Use the byte array to create a DatagramPacket
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length,
                                                       m_addr, m_nPort);
            // Send the packet
            m_sock.send(packet);
        }
        catch(IOException e)
        {
            System.out.println("Failed to send data: " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // Determines if a message is ready to be read.
    public boolean MessageReady()
    {
        // Have not determined a way to do this with UDP sockets.  Always return
        // false.  This method should not be used.
        
        return false;
    }
    
    // Receives a message from the client, if one exists.  This method will
    // wait 1 second per call for a message from the client.  Then it will return
    // null.
    public String ReceiveMessage()
    {
        String out = null;
        
        // Create a receive buffer
        byte[] buf = new byte[1024];
            
        // Use the buffer to create a new DatagramPacket
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
        try
        {
            // Attempt to receive a message
            m_sock.receive(packet);
            
            // One was there.  Convert to String
            out = new String(packet.getData(), 0, packet.getLength());

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
    
    // Returns the address of the remote host
    public String GetAddress()
    {           
        return m_addr.getHostAddress().toString();
    }
    
    // Returns the remote port for the socket
    public int GetPort()
    {
        return m_nPort;
    }
}
