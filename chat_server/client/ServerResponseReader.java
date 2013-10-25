package project2.client;

// Imported objects
import project2.ClientSocketManager;
import project2.Command;

/*
 * @author Steven Meckl, G00459475
 * 
 * This class responds to messages from the server and displays them to the
 * console.
 */
public class ServerResponseReader extends Thread
{
    // The socket object that messages will be received on.
    ClientSocketManager m_socket = null;
    
    // Constructor for the ServerResponseReader class
    public ServerResponseReader(ClientSocketManager socket)
    {
        SetSocket(socket);
    }
    
    // Sets the ClientSocketManager object to receive message on
    public void SetSocket(ClientSocketManager socket)
    {
        m_socket = socket;        
    }
    
    @Override
    // The method that is called when the Thread's start() method is called.
    public void run()
    {     
        // Flag that determines if the receive loop should continue
        boolean bContinue = true;        
                
        // Command object used to parse server messages
        Command cmd = new Command();

        // String object used to receive server messages
        String serverInput = "";

        // Continue until the server socket is closed
        while(bContinue && !m_socket.IsClosed())
        {
            // Attempt to receive a message from the server.
            // This call blocks for up to 1 second
            serverInput = m_socket.ReceiveMessage();
            
            // If a message was received, then process it
            if(null != serverInput)
            {
                do
                {
                    // If this is a Bye command, then stop listening because
                    // the server has shut down.
                    if(cmd.ParseCommand(serverInput) && Command.Type.Bye == cmd.GetType())
                    {
                        System.out.println("Server shutting down.  Type \"Bye\" to exit.");
                        bContinue = false;                            
                    }
                    else
                        // Print the message from the server
                        System.out.println(serverInput);
                
                } while(bContinue && (serverInput = m_socket.ReceiveMessage()) != null);
                
                // Print out a new command prompt to the console
                System.out.print(">>");
            }                        
        }        

        // Close the socket.
        m_socket.Close();        
    }
}