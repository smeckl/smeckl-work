
package project2.server;

import project2.Command;
import project2.ClientSocketManager;

/**
 * @author Steven Meckl, G00459475
 *
 * This class has two functions.  It implements a thread that can listen for
 * commands from the client.  It is used in this fashion for the TCP chat server
 * program.
 * 
 * It also implements the ClientInfo interface, allowing the ChatServerManager
 * class to use it to manage individual client connections.
 */
public class ServerSocketThread
    extends Thread
    implements ClientInfo
{
    // Maximum valid channel number (they start at 1)
    private static final int MAX_CHANNEL_NUM = 3;
    
    // Socket manager object to be used by the connection.
    private ClientSocketManager m_socket;
    
    // ChatServerManager object that is used to process received requests.
    private ChatServerManagerInterface m_serverMgr = null;   
    
    // Array of channels.  Min # is 1.  Max # is 3
    private Channel[] m_channels = new Channel[MAX_CHANNEL_NUM+1];
    
    // Constructor
    public ServerSocketThread(ClientSocketManager newUser, ChatServerManagerInterface serverMgr)
    {
        setSocket(newUser);
        setServerManager(serverMgr);
        
        // Initialize channel array
        for(int i = 0; i <= MAX_CHANNEL_NUM; i++)
            m_channels[i] = new Channel(i, "");
    }
    
    // Sets the socket object to be used by this class
    private void setSocket(ClientSocketManager socket)
    {
        m_socket = socket;
    }
    
    // Sets the ChatServerManagerInterface to be used to process requests.
    private void setServerManager(ChatServerManagerInterface serverMgr)
    {
        m_serverMgr = serverMgr;
    }
   
    // ---------------------------------------
    // Implementation of ClientInfo interface
    //----------------------------------------
    
    // Adds a client to the specified channel with the specified handle       
    public boolean AddToChannel(int nChannel, String handle)
    {
        boolean bRetVal = true;
        
        // Only add the client if the channel is valid and the client is not
        // already in that channel.
        if(isValidChannel(nChannel) && !IsInChannel(nChannel))
            m_channels[nChannel].SetHandle(handle);
        else 
            bRetVal = false;
        
        return bRetVal;
    }
    
    // Removes the client from the specified channel
    public boolean RemoveFromChannel(int nChannel)
    {
        boolean bRetVal = true;
        
        // Only remove them if the channel is valid and they are a member of that
        // channel.
        if(isValidChannel(nChannel) && IsInChannel(nChannel))
            m_channels[nChannel].SetHandle("");
        
        return bRetVal;
    }
    
    // Determines if the client is in the specified channel.
    public boolean IsInChannel(int nChannel)
    {
        boolean bRetVal = false;
        
        if(isValidChannel(nChannel) && !m_channels[nChannel].GetHandle().equalsIgnoreCase(""))
            bRetVal = true;
        
        return bRetVal;
    }
    
    // Retrieves the handle a client is using for a specifed channel.
    public String GetHandleForChannel(int nChannel)
    {
        String handle = "";
        
        if(isValidChannel(nChannel))
            handle = m_channels[nChannel].GetHandle();
        
        return handle;
    }
    
    // Sends a message to a client using the socket object.
    public boolean SendMessage(String message)
    {
        boolean bRetVal = true;
        
        bRetVal = m_socket.SendMessage(message + "\n");            
        
        return bRetVal;
    }
    
    // Determines if the specified channel is valid.
    public boolean IsValidChannel(int nChannel)
    {
        return isValidChannel(nChannel);
    }
            
    // Determines if the specified channel is valid.
    private boolean isValidChannel(int nChannelNum)
    {
        boolean bRetVal = true;
        
        if((nChannelNum <= 0) || (nChannelNum > MAX_CHANNEL_NUM))
            bRetVal = false;
        
        return bRetVal;
    }
    
    // Logs the user out from the server.
    public boolean LogoutUser()
    {
        // Remove the user from all channels
        for(int i = 1; i <= MAX_CHANNEL_NUM; i++)
        {
            m_channels[i].SetHandle("");
        }

        // Send the Bye message to the client, telling them to close the 
        // connection on their end.
        m_socket.SendMessage(Command.BYE);

        // Close all socket-based objects for the client.          
        m_socket.Close();
        
        return true;
    }
    
    public String GetAddress()
    {
        return m_socket.GetAddress();
    }
    
    public int GetPort()
    {
        return m_socket.GetPort();
    }
    
    @Override
    // This method is executed when the Thread object's start() method is called.
    public void run()
    {                
        // Log client connection
        System.out.println("Client connected from " + m_socket.GetAddress() 
                           + ", source port =" + m_socket.GetPort());        
        
        // Initialize the input string
        String serverInput = "";

        // Keep waiting for messages until the socket is closed
        while(!m_socket.IsClosed())
        {
            // Attempt to read a message from the client.  This call will block
            // for up to 1 second.
            serverInput = m_socket.ReceiveMessage();
            
            // If a message was received, then process it.
            if(null != serverInput)
            {
               System.out.println("Received command: " + serverInput);                                            

                m_serverMgr.ProcessClientRequest((ClientInfo)this, serverInput);   

                System.out.println("Processed command");
            }                        
        }        

        // Close the socket and exit.
        m_socket.Close(); 
    }    
}
