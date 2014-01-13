/*
 * Created by Steven W. Meckl, G00459475 for CS 571, Fall 2008
 * Octomber 8, 2008
 */
package project1;

/**
 * @author Steven W. Meckl, G00459475
 * 
 * This class represents the schedule of traffic arrival to the bridge and delays
 * between bursts of traffic.  It is designed to be used by the Main class to
 * quickly generate and test different traffic schedules.
 */
public class Schedule
{
    // Index of last ScheduleEntry in the array.
    private int m_nNextEntryIndex;
    
    // Array of ScheduleEntries
    private ScheduleEntry[] m_schedule;
   
    // Probability distributions for vehicle type
    private double m_fCarProbability;
    private double m_fVanProbability;
    private double m_fTruckProbability;

    /**
     * Constructor for the Schedule class
     * @param nEntries Number of entries in the schedule
     * @param fCarProbability Probability that a vehicle arriving at the bridge is a Car
     * @param fVanProbability Probability that a vehicle arriving at the bridge is a Van
     * @param fTruckProbability Probability that a vehicle arriving at the bridge is a Truck
     */
    public Schedule(int nEntries, double fCarProbability, double fVanProbability,
                    double fTruckProbability)
    {
        SetNumberOfEntries(nEntries);
        SetCarProbability(fCarProbability);
        SetVanProbability(fVanProbability);
        SetTruckProbability(fTruckProbability);
    }

    /**
     * Sets the number of entries in this Schedule
     * @param nEntries Number of schedule entries.
     */
    public void SetNumberOfEntries(int nEntries)
    {
        // Initialize array of ScheduleEntry objects
        m_schedule = new ScheduleEntry[nEntries];
    }

    /**
     * Retrieves the number of entries in the Schedule
     * @return Number of schedule entries.
     */
    public int GetNumberOfEntries()
    {
        return m_schedule.length;
    }

    /**
     * Sets the probability that the vehicle will be of type Car
     * @param fProbability The probability (between 0 and 1.0) that a given
     * vehicle will be a Car.
     * @return True if fProbability is between 0.0 and 1.0.  False otherwise.
     */
    public boolean SetCarProbability(double fProbability)
    {
        boolean bRetVal = true;
        
        // Make sure that the value is in a valid range
        if((0 > fProbability) || (1 < fProbability))
            bRetVal = false;
        else
            m_fCarProbability = fProbability;
        
        return bRetVal;
    }
    
    /** 
     * Retrieves the probability that a given vehicle will be a Car.
     * @return The probability value.
     */
    public double GetCarProbability()
    {
        return m_fCarProbability;
    }
    
    /**
     * Sets the probability that the vehicle will be of type Van
     * @param fProbability The probability (between 0 and 1.0) that a given
     * vehicle will be a Van.
     * @return True if fProbability is between 0.0 and 1.0.  False otherwise.
     */
    public boolean SetVanProbability(double fProbability)
    {
        boolean bRetVal = true;
        
        // Make sure that the value is in a valid range
        if((0 > fProbability) || (1 < fProbability))
            bRetVal = false;
        else
            m_fVanProbability = fProbability;
        
        return bRetVal;
    }
    
    /** 
     * Retrieves the probability that a given vehicle will be a Van.
     * @return The probability value.
     */
    public double GetVanProbability()
    {
        return m_fVanProbability;
    }
    
    /**
     * Sets the probability that the vehicle will be of type Truck
     * @param fProbability The probability (between 0 and 1.0) that a given
     * vehicle will be a Truck.
     * @return True if fProbability is between 0.0 and 1.0.  False otherwise.
     */
    public boolean SetTruckProbability(double fProbability)
    {
        boolean bRetVal = true;
        
        // Make sure that the value is in a valid range
        if((0 > fProbability) || (1 < fProbability))
            bRetVal = false;
        else
            m_fTruckProbability = fProbability;
        
        return bRetVal;
    }
    
    /** 
     * Retrieves the probability that a given vehicle will be a Truck.
     * @return The probability value.
     */
    public double GetTruckProbability()
    {
        return m_fCarProbability;
    }
    
    /**
     * Adds a ScheduleEntry to the Schedule.  
     * @param entry New ScheduleEntry to be added to the Schedule.
     * @return Returns true if successful.  Returns false if the Schedule is full.
     */
    public boolean AddEntry(ScheduleEntry entry)
    {
        boolean bRetVal = true;
        
        // Make sure that we don't overrun the array boundary
        if(m_nNextEntryIndex > (m_schedule.length - 1))
        {
            bRetVal = false;
        }
        else
        {
            // Add the ScheduleEntry to the array
            m_schedule[m_nNextEntryIndex] = entry;
            
            // Increment the index of the next entry
            m_nNextEntryIndex++;
        }
        
        return bRetVal;
    }
    
    public ScheduleEntry GetEntry(int nIndex) throws Exception
    {
        // Make sure that the requested index is valid
        if((0 > nIndex) || (nIndex >= m_schedule.length))
        {
            Exception exception = new Exception("Index out of bounds.");
            throw exception;
        }
        
        return m_schedule[nIndex];
    }
    
    public String toString()
    {
        String out = "";
        
        for(int i = 0; i < m_schedule.length; i++)
        {
            if(m_schedule[i].GetType() == ScheduleEntry.Type.Arrival)
                out += m_schedule[i].GetValue();
            else
                out += "DELAY (" + m_schedule[i].GetValue() + ")";
            
            if(i != (m_schedule.length - 1))
                out += " : ";                                              
        }
        
        out += "\n";
        
        out += "Vehicle probability distribution: Car=" + m_fCarProbability 
                + "  Van=" + m_fVanProbability + "  Truck=" + m_fTruckProbability
                + "\n";
        
        return out;
    }
            
}
