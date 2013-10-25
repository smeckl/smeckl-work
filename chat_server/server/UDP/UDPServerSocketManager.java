package project2.server.UDP;

// Imported objects
import project2.server.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.IOException;


/**
 * @author Steven Meckl, G00459475
 * 
 * This class is responsible for managing the server's socket for UDP connections.
 */
public class UDPServerSocketManager 
        implements ServerSocketManager
{
    // The Java DatagramSocket object.
    DatagramSocket m_socket = null;
    
    // Initializes the object with a given port.
    public boolean Initialize(int nPort)
    {
        boolean bRetVal = true;
        
        try
        {
            // Create the DatagramSocket object with the specified port.
            System.out.println("Waiting on socket " + nPort);
            m_socket = new DatagramSocket(nPort);
        }
        catch(SocketException e)
        {
            bRetVal = false;
        }
        
        return bRetVal;
    }

    // Closes the socket
    public boolean Close()
    {
        m_socket.close();
        
        return true;
    }
    
    // Attempts to receive a packet
    public DatagramPacket Receive()
    {
        // Buffer for receiving messages
        byte[] buf = new byte[1024];
            
        // Use the buffer to create a packet object
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        try
        {
            // Attempt to receive a packet.  This method blocks until a packet
            // is received or the socket is closed by another thread.
            m_socket.receive(packet);       
        }
        catch(IOException e)
        {
            packet = null;
        }
        
        return packet;
    }

}
