package project2;

import java.util.Scanner;
/**
 * @author Steven Meckl, G00459475
 *
 * 
 * This class is used to parse commands issued by both the client and server
 * programs.  It performs, parsing, validation, and marshaling of commands.
 */
public class Command
{
    // The Type of the command
    private Type m_type;
    
    // The chat channel a command is associated with.
    private int m_nChannel;
    
    // The message for Msg commands
    private String m_message;
    
    // The chat handle used for Join commands
    private String m_handle;
    
    // Constant Strings used for command marshaling
    public static final String JOIN = "join";
    public static String EXIT = "exit";
    public static String LIST = "list";
    public static String MSG = "msg";
    public static String BYE = "bye";
    public static String KILLSERVER = "killserver";
    
    // Enum defining types of commands
    public enum Type 
    {
        Join,
        Exit,
        List,
        Msg,
        Bye,
        KillServer,
        Invalid;
    }
    
    // Basic constructor for the Command object
    public Command()
    {
        SetType(Type.Invalid);
        SetChannel(0);
        SetMessage("");
    }
    
    // Constructs a Command by parsing the given string
    public Command(String command)
    {
        ParseCommand(command);
    }
    
    // Sets the type of the command
    public void SetType(Type type)
    {
        m_type = type;
    }
    
    // Retrieves the command type
    public Type GetType()
    {
        return m_type;
    }
    
    // Sets the channel to be used for the command
    public void SetChannel(int nChannel)
    {
        m_nChannel = nChannel;
    }
    
    // Returns the command's channel
    public int GetChannel()
    {
        return m_nChannel;
    }
    
    // Sets the chat handle for this command
    public void SetHandle(String handle)
    {
        m_handle = handle;
    }
    
    // Retrieves the command's chat handle
    public String GetHandle()
    {
        return m_handle;
    }
    
    // Sets the command's message field
    public void SetMessage(String msg)
    {
        m_message = msg;
    }
    
    // Retrieves the command's message
    public String GetMessage()
    {
        return m_message;
    }
    
    // This methos parses a command in string format.  Validation is performed
    // and the string command is parsed out into the Command's individual
    // fields.
    public boolean ParseCommand(String command)
    {
        boolean bRetVal = true;
        
        // Create a Scanner object to read the string command's fields
        Scanner strScan = new Scanner(command);
        
        // Define a String to hold the command's type
        String type = null;       
        
        // check to see if the command string has any tokens.
        if(strScan.hasNext())
        {
            type = strScan.next();        
        }
        else
            bRetVal = false;
        
        // Only continue if the command string has at least one token
        if(bRetVal)
        {                
            // If this is a Join command, then process it
            if(JOIN.equalsIgnoreCase(type))
            {
                SetType(Type.Join);
                
                // Parse the Join command's channel field
                if(strScan.hasNextInt())
                    SetChannel(strScan.nextInt());
                else
                    bRetVal = false;

                // Parse the Join command's Handle field
                if(strScan.hasNext() && bRetVal)
                    SetHandle(strScan.next());
                else
                    bRetVal = false;
            }
            // If this is an Exit command, then process it
            else if(EXIT.equalsIgnoreCase(type))
            {
                SetType(Type.Exit);

                // Parse the Exit command's Channel field
                if(strScan.hasNextInt())
                     SetChannel(strScan.nextInt());
                 else
                     bRetVal = false;
            }
            // If it is a Msg command, then process it
            else if(MSG.equalsIgnoreCase(type))
            {
                SetType(Type.Msg);

                // Process the Channel field
                if(strScan.hasNextInt())
                     SetChannel(strScan.nextInt());
                 else
                     bRetVal = false;

                // Process the Message field
                if(strScan.hasNextLine() && bRetVal)
                    SetMessage(strScan.nextLine());
                else
                    bRetVal = false;
            }
            // If it is a List command, then process it
            else if(LIST.equalsIgnoreCase(type))
            {
                SetType(Type.List);

                // Process the Channel field
                if(strScan.hasNextInt())
                     SetChannel(strScan.nextInt());
                 else
                     bRetVal = false;
            }
            // If this is a Bye command, then process it
            else if(BYE.equalsIgnoreCase(type))
            {
                SetType(Type.Bye);
            }
            // If it is a Killserver command, then process it
            else if(KILLSERVER.equalsIgnoreCase(type))
            {
                SetType(Type.KillServer);
            }
            // Otherwise it is an Invalid command
            else
            {
                SetType(Type.Invalid);
                bRetVal = false;
            }
        }
        
        return bRetVal;
    }

    
    @Override
    // This method converts the command into a string for printing to the 
    // screen or marshaling over a socket
    public String toString()
    {
        String out = "";
        
        switch(m_type)
        {
            case Join:
                out += JOIN + " " + GetChannel() + " " + GetHandle();
                break;
                
            case Exit:
                out += EXIT + " " + GetChannel();
                break;
                
            case List:
                out += LIST + " " + GetChannel();
                break;
                
            case Msg:
                out += MSG + " " + GetChannel() + " " + GetMessage(); 
                break;
                
            case Bye:
                out += BYE;
                break;
                
            case KillServer:
                out += KILLSERVER;
                break;
                
            case Invalid:
                out += "Invalid command";
                break;
        }
        
        out += "\n";
        
        return out;
    }
}
