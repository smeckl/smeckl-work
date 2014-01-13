/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project1;

public class ScheduleEntry
{
    // Type of Schedule Entry
    public enum Type
    {
        Arrival,
        Delay;
    }
    
    // Type of this entry
    private Type m_type;
    
    // Value deternining number of cars arriving or number of seconds to 
    // delay, depending on the value of Type
    private int m_nValue;
    
    /**
     * Constructor for the ScheduleEntry class
     * @param type Type of ScheduleEntry this is.
     * @param nValue Value deternining number of cars arriving or number of seconds to 
     * delay, depending on the value of Type
     */
    public ScheduleEntry(Type type, int nValue)
    {
        SetType(type);
        SetValue(nValue);
    }
    
    /**
     * Sets the Type of this ScheduleEntry
     * @param type Type of entry: Arrival or Delay
     */
    public void SetType(Type type)
    {
        m_type = type; 
    }
    
    /**
     * Retrieves the Type of this entry.
     * @return Type of entry: Arrival or Delay
     */
    public Type GetType()
    {
        return m_type;
    }
    
    /**
     * Sets the Value for this entry
     * @param nValue Number of cars arriving or number of seconds to delay.
     */
    public void SetValue(int nValue)
    {
        m_nValue = nValue;
    }
    
    /**
     * Retrieves the Value of this entry.
     * @return Number of cars arriving or number of seconds to delay.
     */
    public int GetValue()
    {
        return m_nValue;
    }
}
