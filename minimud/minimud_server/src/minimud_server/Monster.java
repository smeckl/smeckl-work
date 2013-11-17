/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minimud_server;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import minimud_shared.RangeChecker.RangeID;
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
    
    public enum Winner
    {
        Player,
        Monster
    }
    
    private State m_state = Monster.State.Open;
    private Calendar m_respawnTime = new GregorianCalendar();
    
    private int m_nLootTableID = 0;
    private int m_nKillXP = 0;
    private int m_nKillGold = 0;
    private int m_nUpdateQuestID = 0;
    private int m_nUpdateQuestStep = 0;
    
    public Monster()
    {
        super();
    }
    
    public synchronized void setState(State state)
    {
        m_state = state;
        
        // If the monster is dead, then calculate the respawn time
        if(State.Dead == state || State.Open == state)
        {
            // Calculate the next respawn time
            m_respawnTime.setTime(new Date());
            m_respawnTime.add(Calendar.MINUTE, 2);
        }
    }
    
    public synchronized State getState()
    {
        return m_state;
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
                // Reset health to max health and set state to Open
                setHealth(getMaxHealth());
                setState(Monster.State.Open);
                bRet = true;
            }
        }
        
        return bRet;
    }
    
    public void setLootTableID(int nID)
    {
        m_nLootTableID = nID;
    }
    
    public int getLootTableID()
    {
        return m_nLootTableID;
    }
    
    public boolean isValidLootTableID(int nID)
    {
        return rangeCheck.checkRange(RangeID.ID, nID);
    }
    
    public void setKillXP(int nXP)
    {
        m_nKillXP = nXP;
    }
    
    public int getKillXP()
    {
        return m_nKillXP;
    }
    
    public boolean isValidKillXP(int nKillXP)
    {
        return rangeCheck.checkRange(RangeID.REWARD_XP, nKillXP);
    }
    
    public void setKillGold(int nGold)
    {
        m_nKillGold = nGold;
    }
    
    public int getKillGold()
    {
        return m_nKillGold;
    }
    
    public boolean isValidKillGold(int nGold)
    {
        return rangeCheck.checkRange(RangeID.REWARD_GOLD, nGold);
    }
    
    public void setUpdateQuestID(int nID)
    {
        m_nUpdateQuestID = nID;
    }
     
    public int getUpdateQuestID()
    {
        return m_nUpdateQuestID;
    }
    
    public boolean isValidUpdateQuestID(int nID)
    {
        return rangeCheck.checkRange(RangeID.ID, nID);
    }
    
    public void setUpdateQuestStep(int nStep)
    {
        m_nUpdateQuestStep = nStep;
    }
    
    public int getUpdateQuestStep()
    {
        return m_nUpdateQuestStep;
    }
    
    public boolean isValidUpdateQuestStep(int nStep)
    {
        return rangeCheck.checkRange(RangeID.QUEST_STEP_NUM, nStep);
    }
    
    public boolean isValid()
    {
        return (super.isValid()
                && isValidLootTableID(getLootTableID())
                && isValidKillXP(getKillXP())
                && isValidKillGold(getKillGold()));
    }
    
    private Winner runSimulation(UserConnectionThread user)
    {
        Winner winner = Winner.Player;
        
        
        
        return winner;
    }
}
