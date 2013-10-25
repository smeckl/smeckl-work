package project2.server.TCP;

import project2.server.*;
import project2.ClientSocketManager;
import java.util.concurrent.Semaphore;

 /**
 * @author Steven Meckl, G00459475
  * 
  * This is the main class for the TCP chat server proram.
 */

public class TCPChatServer
{
    // Class that manages client connections
    private static ChatServerManager m_serverManager = null;

    // Socket management object used by the server
    private static TCPServerSocketManager m_serverSocket  = null;
    
    // Main method of the TCP chat server program
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
        m_serverSocket = new TCPServerSocketManager();
        
        // Initialize the socket to use the user-specified port.
        m_serverSocket.Initialize(Integer.parseInt(args[0]));
        
        // Create the chat server manager object.
        m_serverManager = new ChatServerManager(stayAlive, m_serverSocket);           
               
        // Continue until the semaphore is signaled
        while(1 == stayAlive.availablePermits())
        {           
            // Attempt to accept a new connection.  This call blocks until a
            // connection is accepted or the socket is closed by another thread.
            ClientSocketManager sock = m_serverSocket.Accept();
            
            // Make sure it is a valid socket.
            if(null != sock)
            {
                System.out.println("Accepted connection from client");                            
                
                // Add new connection to the ChatServerManager
                m_serverManager.AddConnection(sock, true);             
            }
            else
            {
                System.out.println("Failed to accept() client connection.");
            }
        }                           
    }
}
