/**
 * 
 */
package project2.server;

/**
 * @author Steven Meckl, G00459475
 *
 * An interface used by the ServerSocketThread object to have the ChatServerManager
 * process client requests
 */
public interface ChatServerManagerInterface
{    
    public boolean ProcessClientRequest(ClientInfo user, String command);
}
