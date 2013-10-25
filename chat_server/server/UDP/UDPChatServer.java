package project2.server.UDP;

// Imported objects
import project2.server.*;
import java.util.concurrent.Semaphore;
import project2.UDPClientSocketManager;
import java.net.DatagramPacket;

 /**
 * @author Steven Meckl, G00459475
  * 
  * The main class for the UCP chat server program
 */
public class UDPChatServer 
{
    // Class that manages client connections
    private static ChatServerManager m_serverManager = null;
    
    // Socket management object used by the server
    private static UDPServerSocketManager m_serverSocket  = null;
    
    // Main method of the UDP chat server program
    public static void main(String[] args)
    {              
        // Check the number of command-line arguments and print usage if
        // needed.
        if(1 != args.length)
        {
            System.out.println("Missing Parameters.  Use: java project2.server.UDPChatServer <port_number>");
            return;
        }
        
        // Even semaphore used to stop the server, when neccessary.
        Semaphore stayAlive = new Semaphore(1);                
        
        // Create the socket manager object
        m_serverSocket = new UDPServerSocketManager();
        
        // Initialize the socket to use the user-specified port.
        m_serverSocket.Initialize(Integer.parseInt(args[0]));
        
        // Create the chat server manager object.
        m_serverManager = new ChatServerManager(stayAlive, m_serverSocket);           
         
        // Continue until the semaphore is signaled
        while(1 == stayAlive.availablePermits())
        {         
            // Attempt to receive a packet.  This call blocks for up to 1 second.
            DatagramPacket packet = m_serverSocket.Receive();
            
            // If a packet is received, then process it.
            if(null != packet)
            {      
                // If this is a new client connection...
                if(m_serverManager.IsNewConnection(packet.getAddress().getHostAddress(),
                                               packet.getPort()))
                {
                    // Log client connection
                    System.out.println("Client connected from " + packet.getAddress() 
                                       + ", source port =" + packet.getPort());
                    
                    // Create a new object to manage the client connection
                    UDPClientSocketManager sock = new UDPClientSocketManager();
                    sock.Initialize(packet.getAddress().getHostAddress(), 
                                    packet.getPort(), false);
                    
                    // Add the new connection to the ChatServerManager object
                    m_serverManager.AddConnection(sock, false);
                    
                    // Dispatch the datagram for processing.
                    m_serverManager.DispatchDatagram(packet);
                }
                else
                {
                    // Dispatch the datagram for processing.
                    m_serverManager.DispatchDatagram(packet);             
                }
            }
            else
            {
                System.out.println("Failed to accept() client connection.");
            }
        }                           
    }
}
