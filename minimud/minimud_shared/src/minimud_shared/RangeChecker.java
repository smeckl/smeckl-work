/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minimud_shared;

import java.util.HashMap;

/**
 *
 * @author steve
 */
public class RangeChecker
{
    public class Range
    {
        private int m_nMin = 0;
        private int m_nMax = 0;
        
        public Range(int nMin, int nMax)
        {
            m_nMin = nMin;
            m_nMax = nMax;
        }
        
        public boolean inRange(int nNum)
        {
            return (nNum >= m_nMin && nNum <= m_nMax);
        }
    }
    
    public enum RangeID
	{
        ID,
        HEALTH,
        GOLD,
        ATTACK_POWER,
        MAGIC_POWER,
        DEFENSE,
        MAGIC_DEFENSE,
        REWARD_XP,
        REWARD_GOLD,
        FIRST_BONUS,
        QUEST_STEP_NUM,
        VALUE,
        DAMAGE,
        PERCENT
	}
    
    private HashMap<RangeID, Range> m_ranges = new HashMap<RangeID, Range>();
    
    public RangeChecker()
    {
        m_ranges.put(RangeChecker.RangeID.ID, new Range(0, 100));
        m_ranges.put(RangeChecker.RangeID.HEALTH, new Range(0, 1000));
        m_ranges.put(RangeChecker.RangeID.GOLD, new Range(0, 10000));
        m_ranges.put(RangeChecker.RangeID.ATTACK_POWER, new Range(0, 1000));
        m_ranges.put(RangeChecker.RangeID.MAGIC_POWER, new Range(0, 1000));
        m_ranges.put(RangeChecker.RangeID.DEFENSE, new Range(0, 100));
        m_ranges.put(RangeChecker.RangeID.MAGIC_DEFENSE, new Range(0, 100));
        m_ranges.put(RangeChecker.RangeID.REWARD_XP, new Range(0, 1000));
        m_ranges.put(RangeChecker.RangeID.REWARD_GOLD, new Range(0, 1000));
        m_ranges.put(RangeChecker.RangeID.FIRST_BONUS, new Range(0, 100));
        m_ranges.put(RangeChecker.RangeID.QUEST_STEP_NUM, new Range(0, 15));
        m_ranges.put(RangeChecker.RangeID.VALUE, new Range(0, 1000));
        m_ranges.put(RangeChecker.RangeID.DAMAGE, new Range(0, 100));
        m_ranges.put(RangeChecker.RangeID.PERCENT, new Range(0, 100));
    }
    
    public boolean checkRange(RangeID id, int nNum)
    {
        return m_ranges.get(id).inRange(nNum);
    }
}
