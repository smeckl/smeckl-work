/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minimud_server;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author steve
 */
public class Monster extends Mob
{
    public enum ErrorCode
    {
        Success,
        Error
    }
    
    public enum State
    {
        Open,
        InCombat,
        Dead
    }
    
    private State m_state = Monster.State.Open;
    private Calendar m_respawnTime = new GregorianCalendar();
    
    public Monster()
    {
        super();
    }
    
    public synchronized void setState(State state)
    {
        m_state = state;
    }
    
    public synchronized State getState()
    {
        return m_state;
    }
    
    public synchronized ErrorCode simulateFight(UserConnectionThread user)
    {
        ErrorCode retVal = ErrorCode.Success;
        
        // Can only attack if the monster is alive and nobody else is attacking it
        if(Monster.State.Open == getState())
        {
            // Mark the monster as in combat
            setState(Monster.State.InCombat);
            
            GameServer.sendUserText(user, "You attack the " + getName());

            // Mark the monster as dead
            setState(Monster.State.Dead);

            // Calculate the next respawn time
            m_respawnTime.setTime(new Date());
            m_respawnTime.add(Calendar.MINUTE, 2);
        }
        
        return retVal;
    }
    
    public synchronized boolean refreshState()
    {
        boolean bRet = false;
        
        // If the monster is dead, respawn if it is time
        if(Monster.State.Dead == getState())
        {
            // Get current time
            Calendar now = new GregorianCalendar();
            now.setTime(new Date());
            
            //Check to see if current time is after the next respawn time
            if(now.after(m_respawnTime))
            {
                setState(Monster.State.Open);
                bRet = true;
            }
        }
        
        return bRet;
    }
}
