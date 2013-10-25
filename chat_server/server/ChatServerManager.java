package project2.server;

// Imported objects
import project2.Command;
import project2.ClientSocketManager;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.Semaphore;
import java.net.DatagramPacket;

/**
 *
 * @author Steven Meckl, G00459475
 * 
 * This class is responsible for managing the client's connected to the server.
 * It is responsible for adding new connections, processingn client commands,
 * and dispatching UDP datagrams
 */
public class ChatServerManager
        implements ChatServerManagerInterface
{
    // The list of connected clients
    private LinkedList<ClientInfo> m_connectedUsers = null;
    
    // Semaphore used to stop the server when the Killserver command is issued
    private Semaphore m_stopServer = null;
    
    // A pointer to the ServerSocketmanager interface, which represents the
    // socket that listens for new client connections.
    private ServerSocketManager m_serverSock = null;

    // The ChatServerManager constructor
    public ChatServerManager(Semaphore stopServerSem, ServerSocketManager servSock)
    {
        m_stopServer = stopServerSem;
        m_serverSock = servSock;
        m_connectedUsers = new LinkedList<ClientInfo>();
    }

    // This method adds a new client connection to the server.  If bStartThread
    // is called, then the ServerSocketThread's start() method is called to start
    // the thread.
    public synchronized boolean AddConnection(ClientSocketManager sock, boolean bStartThread)
    {
        boolean bRetVal = true;

        // Create new ServerSocketThread object
        ServerSocketThread serverSock = new ServerSocketThread(sock, this);

        // Add the newly-created object to the list of client connections
        m_connectedUsers.add(serverSock);

        // Start the thread if necessary
        if (bStartThread)
        {
            serverSock.start();
        }

        return bRetVal;
    }

    // Determines if the request is from a new client or not, based on the
    // specified IP address and remote port.
    public synchronized boolean IsNewConnection(String address, int nRemotePort)
    {
        boolean bRetVal = true;

        // Get the ListIterator to traverse the list with.
        ListIterator<ClientInfo> iterator = m_connectedUsers.listIterator();
 
        // Loop through all existing client connections
        while (bRetVal && iterator.hasNext())
        {
            // Get next client connection
            ClientInfo info = iterator.next();

            /// If the remote address and port match the specified remote
            // address and port, then set return value to false.  It is not a new
            // connection.
            if (address.equalsIgnoreCase(info.GetAddress()) && info.GetPort() == nRemotePort)
            {
                bRetVal = false;
            }
        }

        return bRetVal;
    }

    // Dispatches a UDP datagram to the proper client.  This method assumes that
    // the caller has already determined that the client is already connected by
    // using the IsNewConnection() method.
    public boolean DispatchDatagram(DatagramPacket packet)
    {
        boolean bRetVal = true;
        boolean bContinue = true;

        // Address and port associated with the packet
        String packetAddr = packet.getAddress().getHostAddress();
        int nPacketPort = packet.getPort();

        // Iterator used to traverse the list
        ListIterator<ClientInfo> iterator = m_connectedUsers.listIterator();

        // Loop through existing client connections
        while (bContinue && iterator.hasNext())
        {
            ClientInfo info = iterator.next();

            // If the client's address and port match the packet's address and
            // port, then process the message for that client.
            if (packetAddr.equalsIgnoreCase(info.GetAddress()) && nPacketPort == info.GetPort())
            {
                // Convert the packet to a String command.
                String message = new String(packet.getData(), 0, packet.getLength());

                // Process the request.
                bRetVal = ProcessClientRequest(info, message);

                // Stop the loop since we have found the matching client
                bContinue = false;
            }

        }

        return bRetVal;
    }

    // Processes the command issued by the client
    public synchronized boolean ProcessClientRequest(ClientInfo user, String command)
    {
        boolean bRetVal = true;
        
        // Object used to parse the client command
        Command cmd = new Command(command);

        switch (cmd.GetType())
        {
            case Join:
                bRetVal = processJoinRequest(user, cmd);
                break;

            case Exit:
                bRetVal = processExitRequest(user, cmd);
                break;

            case List:
                bRetVal = processListRequest(user, cmd);
                break;

            case Msg:
                bRetVal = processMsgRequest(user, cmd);
                break;

            case Bye:
                bRetVal = processByeRequest(user, cmd);
                break;

            case KillServer:
                bRetVal = processKillServerRequest(user, cmd);
                break;

            case Invalid:
                user.SendMessage("Invalid request.  Please try again.");
                break;
        }

        return bRetVal;
    }

    // Processes a request to join a chat room
    private boolean processJoinRequest(ClientInfo user, Command cmd)
    {
        boolean bRetVal = true;

        // Check to see if the user is already in the channel
        if (!user.IsInChannel(cmd.GetChannel()))
        {
            // The are not.  Add them to the channel and send welcome message
            bRetVal = user.AddToChannel(cmd.GetChannel(), cmd.GetHandle());
            user.SendMessage("Welcome to Channel " + cmd.GetChannel() + ", " + cmd.GetHandle() + ".");
        }
        else
        {
            // User is already in the channel.  Print error message
            user.SendMessage("You are already a member of Channel " + 
                             cmd.GetChannel() + 
                             ".  Please exit channel before re-joining it.");
        }

        if (!bRetVal)
        {
            // If an error occurred, then send an error message to the user
            user.SendMessage("Failed to join Channel " + cmd.GetChannel());
        }

        return bRetVal;
    }

    // Processes a request to exit a specific chat channel
    private boolean processExitRequest(ClientInfo user, Command cmd)
    {
        boolean bRetVal = true;

        // If the user is in the channel, then remove them from it.
        if (user.IsInChannel(cmd.GetChannel()))
        {
            user.RemoveFromChannel(cmd.GetChannel());

            user.SendMessage("Successfully removed from Channel " + cmd.GetChannel());
        }
        // User not in the specified channel.  Send error message.
        else
        {
            user.SendMessage("You are not a member of that channel.");
        }

        return bRetVal;
    }
    
    // Processes a request to send a message to a channel
    private boolean processMsgRequest(ClientInfo user, Command cmd)
    {
        boolean bRetVal = true;

        // Check to see if the channel is valid.  If not, then send error msg.
        if (!user.IsValidChannel(cmd.GetChannel()))
        {
            user.SendMessage("Invalid Channel.");
        }
        // It is a valid channel.  Make sure the user has joined the channel
        else if (user.IsInChannel(cmd.GetChannel()))
        {
            // Create the message to be sent to the channel
            String out = "Channel (" + cmd.GetChannel() + ") " + 
                        user.GetHandleForChannel(cmd.GetChannel()) + ": " + 
                        cmd.GetMessage();

            // Get iterator for list of connected users
            ListIterator<ClientInfo> iterator = m_connectedUsers.listIterator();

            // Loop through connected users
            while (iterator.hasNext())
            {
                ClientInfo info = iterator.next();

                // If the user is in the channel and is not the sender, then
                // send the message to that user.
                if (info.IsInChannel(cmd.GetChannel()) && info != user)
                {
                    info.SendMessage(out);
                }                
            }
             
            // Send success message to the sender.
            user.SendMessage("Successfully sent Message");
        }
        else
        {
            user.SendMessage("You are not a member of Channel " + cmd.GetChannel());
        }

        return bRetVal;
    }

    // Processes request to list the users in a channel
    private boolean processListRequest(ClientInfo user, Command cmd)
    {
        boolean bRetVal = true;

        // If the specified channel is valid, then proceed.
        if (user.IsValidChannel(cmd.GetChannel()))
        {
            // Start of list message
            String out = "The following users are in Channel " + cmd.GetChannel() + ":\n";

            ListIterator<ClientInfo> iterator = m_connectedUsers.listIterator();

            // Iterate through connected users
            while (iterator.hasNext())
            {
                ClientInfo info = iterator.next();

                // If the user is in the specified channel, then add them to
                // the list message
                if (!info.GetHandleForChannel(cmd.GetChannel()).equals(""))
                {
                    out += "-" + info.GetHandleForChannel(cmd.GetChannel()) + "\n";
                }
            }

            // Send the user the list of people in the specified channel.
            user.SendMessage(out);
        }
        else
        {
            user.SendMessage("Invalid Channel.");
        }

        return bRetVal;
    }

    // Processes a request from a client to logout of the server.  This command
    // was not specified in the assignment description but was created to allow the
    // client to gracefully exit.
    private boolean processByeRequest(ClientInfo user, Command cmd)
    {
        boolean bRetVal = true;

        // Remove the client from the list of connected users
        m_connectedUsers.remove(user);

        // Log the user out of the server
        user.LogoutUser();

        return bRetVal;
    }

    // Processes a request to kill the server.  This command was not specified
    // in the assignment, but is used to allow the server and all connected
    // clients to exit gracefully.
    private boolean processKillServerRequest(ClientInfo user, Command cmd)
    {
        boolean bRetVal = true;

        ListIterator<ClientInfo> iterator = m_connectedUsers.listIterator();

        // Loop through all connected clients and log them out.
        while (iterator.hasNext())
        {
            ClientInfo info = iterator.next();

            info.LogoutUser();
        }

        try
        {
            // Signal the semaphore that stops the main listener loop.
            m_stopServer.acquire();
            
            // Close the server socket
            bRetVal = m_serverSock.Close();
        }
        catch (InterruptedException e)
        {
            System.out.println("Shutdown semaphore acquisition interrupted.");
        }

        return bRetVal;
    }
}
