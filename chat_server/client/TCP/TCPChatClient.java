package project2.client.TCP;

import project2.client.*;
import project2.TCPClientSocketManager;

/**
* @author Steven Meckl, G00459475
*/

// This is the main class for the TCP version of the chat client program
public class TCPChatClient
{      
    // Thread object that listens for messages from teh server
    private static ServerResponseReader m_serverReader = null;
    
    // Thread object that waits for input from the user
    private static UserInputReader m_kbReader = null;
    
    // The main method of the TCP chat client
    public static void main(String[] args)
    {
        // Make sure we have enough command-line arguments.  Print usage if not.
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
        TCPClientSocketManager clientSock = new TCPClientSocketManager();
        clientSock.Initialize(args[0], Integer.parseInt(args[1]));

        // Start thread that listens for responses from the server
        m_serverReader = new ServerResponseReader(clientSock);            
        m_serverReader.start();

        // Start the thread that reads commands from the keyboard
        m_kbReader = new UserInputReader(clientSock);
        m_kbReader.start();
    }
}
