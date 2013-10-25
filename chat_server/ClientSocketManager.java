
package project2;

/**
 * @author Steven Meckl, G00459475
 * 
 * This interface is used to abstract the functionality of socket management so 
 * that clients and servers can use UDP and TCP sockets in the same way.
 */
public interface ClientSocketManager 
{
    // Close the socket and all read/write objects attached to it, if necessary
    public boolean Close();
    
    // Checks to see if the socket is closed.  Returns true if it is.
    public boolean IsClosed();
    
    // Sends a message to a host using the socket
    public boolean SendMessage(String message);
    
    // Returns true if a message is ready to be read.
    public boolean MessageReady();
    
    // Receives a message from the socket and returns it as a String
    public String ReceiveMessage();
    
    // Returns the remote IP address (in String form) of the socket.
    public String GetAddress();
    
    // Returns the remote port for the socket
    public int GetPort();
}
