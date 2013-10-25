
package project2.client.UDP;

import project2.client.*;
import project2.UDPClientSocketManager;

/**
* @author Steven Meckl, G00459475
*/

// This is the main class for the UDP version of the chat client
public class UDPChatClient 
{
    // Thread object that listens for messages from the server
    private static ServerResponseReader m_serverReader = null;
    
    // TGhread object that waits for input from the user
    private static UserInputReader m_kbReader = null;
    
    // The main method of the UDP chat client program
    public static void main(String[] args)
    {
        // Make sure there are enough arguments.  Print usage if not.
        if(args.length < 2)
        {
            System.out.println("Invalid arguments.");
            System.out.println("Use java project2.client.ChatClient <server_addr> <port>");
            
            return;
        }
        
        // args[0] = address of server
        // args[1] = port to connect  to
        System.out.println("Connecting to " + args[0] + " on port " + args[1] + "\n");
        System.out.print(">>");
        
        // Open socket to server
        UDPClientSocketManager clientSock = new UDPClientSocketManager();
        clientSock.Initialize(args[0], Integer.parseInt(args[1]), false);

        // Start thread that listens for responses from the server
        m_serverReader = new ServerResponseReader(clientSock);            
        m_serverReader.start();

        // Start the thread that reads commands from the keyboard
        m_kbReader = new UserInputReader(clientSock);
        m_kbReader.start();
    }
}
