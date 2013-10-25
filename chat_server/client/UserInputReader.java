package project2.client;

// Imported objects
import project2.ClientSocketManager;
import project2.Command;
import java.util.Scanner;

/*
 * @author Steven Meckl, G00459475
 * 
 * This class waits for input from the user, and then sends the user's commands
 * to the server.
 */
public class UserInputReader extends Thread
{
    // The ClientSocketManager class that messages are sent to the server on
    private ClientSocketManager m_socket;
    
    // Flag that determines if wait loop should continue
    private boolean m_bContinue = true;
    
    // Scanner object used to read input from the keyboard
    private Scanner m_kbIn = null;
    
    // Constructor for the UserInputReader class
    public UserInputReader(ClientSocketManager socket)
    {
        SetSocket(socket);
    }
    
    // Sets the socket object to be used by this class
    public void SetSocket(ClientSocketManager socket)
    {
        m_socket = socket;        
    }     
    
    @Override
    // The method executed when the Thread's start() method is called
    public void run()
    {
        // Initialize the Scanner object with the System.in object
        m_kbIn = new Scanner(System.in);        
        
        // Initialize the input string to ""
        String input = "";        
        
        // Create a new Command object to parse and marshal user commands
        Command cmd = new Command();
        
        // Initialize m_bContinue
        m_bContinue = true;                
            
        // Loop as long as there is input to be read
        while(m_bContinue && m_kbIn.hasNextLine() && (input = m_kbIn.nextLine()) != null)
        {
            // If this is not a valid command, then print error message
            if(!cmd.ParseCommand(input))
            {
                System.out.println("Invalid Command!");
                System.out.print(">>");
            }  
            // If this is a Bye or Killserver command, then send message and
            // exit loop
            else if((Command.Type.Bye == cmd.GetType())
                    || (Command.Type.KillServer == cmd.GetType()))
            {
                // Send the message only if socket is open
                if(!m_socket.IsClosed())
                    m_socket.SendMessage(cmd.toString());

                // Exit loop.
                m_bContinue = false;
            }
            else
            {
                // Send command to server                 
                m_socket.SendMessage(cmd.toString());
            }
        }

        // Close the socket.  The program is exiting
        m_socket.Close();
    }
}
