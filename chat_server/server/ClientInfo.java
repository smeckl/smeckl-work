
package project2.server;

/**
 * @author Steven Meckl, G00459475
 *
 * An interface used to manage connected clients.  Implemented by ServerSocketThread.
 */
public interface ClientInfo
{
    // Adds a client to a specified channel with a specified handle
    public boolean AddToChannel(int nChannel, String handle);
    
    // Removes a client from the specified channel
    public boolean RemoveFromChannel(int nChannel);
    
    // Checks to see if the client is in the specified channel.
    public boolean IsInChannel(int nChannel);
    
    // Retrieves that handle that a client is using for the specified channel.
    public String GetHandleForChannel(int Channel);
    
    // Sends a message to the client.
    public boolean SendMessage(String message);
    
    // Logs the user out from the server.
    public boolean LogoutUser();
    
    // Checks to see if the specified channel is valid.
    public boolean IsValidChannel(int nChannel);
    
    // Returns the IP address the client is connected from.
    public String GetAddress();
    
    // Returns the remote port of the client connection.
    public int GetPort();
}
