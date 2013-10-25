
package project2.server;

/**
 * @author Steven Meckl, G00459475
 *
 * This class is a simple data structure for matching a Channel number to the 
 * chat handle a user is using in that channel
 */
public class Channel
{
    // Handle used
    private String m_handle;
    
    // Channel ID
    private int m_nID;
    
    // Constructor for the Channel class
    public Channel(int nChannelNum, String handle)
    {
        SetHandle(handle);
        SetID(nChannelNum);
    }
    
    // Sets the Handle the user is using for this channel
    public void SetHandle(String handle)
    {
        m_handle = handle;
    }
    
    // Retrieves the chat handle
    public String GetHandle()
    {
        return m_handle;
    }
    
    // Sets the Channel ID
    public void SetID(int nChannelNum)
    {
        m_nID = nChannelNum;
    }
    
    // Retrieves the channel ID
    public int GetID()
    {
        return m_nID;
    }
}
