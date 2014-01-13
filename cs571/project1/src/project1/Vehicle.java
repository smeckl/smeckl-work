/*
 * Created by Steven W. Meckl, G00459475 for CS 571, Fall 2008
 * Octomber 8, 2008
 */

package project1;

import java.util.concurrent.locks.Condition;


/**
 * @author Steven W. Meckl, G00459475
 * 
 * This class implements a Thread that simulates a Vehicle approaching a bridge.
 * The vehicle must be given permission by the controller of the bridge
 * (the BridgeController object) before being allowed to cross the bridge.  
 * This class also implements the VehicleInformation interface, which provides
 * access only to the Vehicle's attributes, but not the Vehicle's Thread 
 * functionality.
 */
public class Vehicle 
        extends Thread
        implements VehicleInformation
{
    private static int WAIT_MULTIPLIER = 1000; // Multiplier for specified wait unit
    
    // Vehicle Type
    private Type m_type;
    
    // Direction the vehicle is traveling
    private Direction m_direction;
    
    // Vehicle ID
    private int m_nID;
    
    // Object that controls access to the bridge
    private BridgeController m_BridgeController;
    
    // Condition variable to wait on
    private Condition m_condition;
    
    /**
     * Sets the Type of the Vehicle
     * @param type The type of the Vehicle
     */
    public void SetType(Type type)
    {
        m_type = type;
    }
    
    /**
     * Retrieves the Type of the Vehicle
     * @return Type of the Vehicle
     */
    public Type GetType()
    {
        return m_type;
    }
    
    /**
     * Sets the Vehicle Direction
     * @param direction The Direction the Vehicle should travel.
     */
    public void SetDirection(Direction direction)
    {
        m_direction = direction;
    }
    
    /**
     * Retrieves the Direction the Vehicle is traveling
     * @return The Vehicle's Direction.
     */
    public Direction GetDirection()
    {
        return m_direction;
    }
    
    /**
     * Sets the Vehicle's ID
     * @param nID The new Vehicle ID
     */
    public void SetID(int nID)
    {
        m_nID = nID;
    }
    
    /**
     * Retrieves the ID of the Vehicle
     * @return The Vehicle ID
     */
    public int GetID()
    {
        return m_nID;
    }
    
    /**
     * Retrieves the weight of the vehicle
     * @return The weight of the vehicle.
     */
    public int GetWeight()
    {
        int nWeight = 0;
        
        switch(m_type)
        {
            case Car:
                nWeight = 100;
                break;
                
            case Van:
                nWeight = 150;
                break;
                
            case Truck:
                nWeight = 300;
                break;
        }
        
        return nWeight;
    }
    
    /**
     * Sets the Condition variable associated with the VehicleInformation 
     * interface for the object.  The Condition variable will be used 
     * exclusively by one thread for waiting and signaling.
     * @param condition A Condition variable that will be used to synchronize
     * the Vehicle thread.
     */
    public void SetCondition(Condition condition)
    {
        m_condition = condition;
    }
    
    /**
     * Retrieves the Condition variable associated with the VehicleInformation 
     * interface for the object.  The Condition variable will be used 
     * exclusively by one thread for waiting and signaling.
     * @param condition A Condition variable that will be used to synchronize
     * the Vehicle thread.  Return value can be null.
     */
    public Condition GetCondition()
    {
        return m_condition;
    }
    
    /** 
     * Sets the bridge controller object to use.
     * @param controller A BridgeController object
     */
    protected void setBridgeController(BridgeController controller)
    {
        m_BridgeController = controller;
    }
    
    /**
     * Constructor for Vehicle Class
     * @param nID The unique ID of the vehicle
     * @param type Tye type of vehicle (Car, Van, Truck)
     * @param direction Direction the vehicle is traveling (Northbound or Southbound)
     */
    public Vehicle(int nID, Type type, Direction direction, 
                    BridgeController controller)
    {
        SetID(nID);
        SetType(type);
        SetDirection(direction);
        setBridgeController(controller);
    }
    
    @Override
    public void run()
    {
        Arrive();
        Cross();
        Leave();
    }
    
    /**
     * This method simulates the Vehicle's arrival at a bridge.
     */
    private void Arrive()
    {
        // Check Restrictions before attempting to cross bridge.
        m_BridgeController.RequestCrossing((VehicleInformation)this);                
    }
    
    /**
     * This method simulates the Vehicle crossing a bridge.  For simplicity
     * this method simply sleeps for 2 seconds.
     */
    private void Cross()
    {
        try
        {            
            // Sleep for 2 seconds
            Thread.sleep(2 * WAIT_MULTIPLIER);            
        }
        catch(java.lang.InterruptedException e) // Thrown by sleep() method 
        {
            System.out.println("Thread.sleep() call interrupted:" + e);
        }
    }
    
    /**
     * This method simulates teh Vehicle leaving a bridge.  It simply notifies
     * the bridge that it is exiting the bridge.
     */
    private void Leave()
    {
        m_BridgeController.NotifyExitStatus((VehicleInformation)this);
    }
}
