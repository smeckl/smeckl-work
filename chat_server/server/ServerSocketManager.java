package project2.server;

/**
 * @author Steven Meckl, G00459475
 * 
 * Interface used to manage a server socket.  This interface allows both TCP 
 * and UDP sockets to be managed using a consistent interface.
 */
public interface ServerSocketManager 
{
    public boolean Initialize(int nPort);
    public boolean Close();
}
