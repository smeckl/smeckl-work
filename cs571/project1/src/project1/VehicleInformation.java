/*
 * Created by Steven W. Meckl, G00459475 for CS 571, Fall 2008
 * Octomber 8, 2008
 */

package project1;

import java.util.concurrent.locks.Condition;

/**
 *
 * @author Steven W. Meckl, G00459475
 * 
 * This Interface is used to access and change the Vehicle information without
 * providing access to the Thread functions of a Vehicle object.
 * 
 * Implemented by: Vehicle
 */
public interface VehicleInformation 
{
    /**
     * Enum describing type of vehicle
     */ 
    public enum Type
    {
        Car,
        Van,
        Truck;
    }
    
    /**
     * Enum for Direction the vehicle is traveling
     */
    public enum Direction
    {
        Northbound,
        Southbound;
    }
    
    /**
     * Sets the Type attribute of the Vehicle
     * @param type The Type of vehicle
     */
    public void SetType(Type type);
    
    /**
     * Retrieves the Type attirbute of the Vehicle
     * @return
     */
    public Type GetType();
    
    /**
     * Sets the Direction attribute of the Vehicle
     * @param direction The Direction the vehicle is traveling
     */ 
    public void SetDirection(Direction direction);
    
    /**
     * Retrieves the Direction attribute of the Vehicle.
     * @return The Direction the vehicle is traveling
     */
    public Direction GetDirection();
    
    /** 
     * Sets the ID attribute of the Vehicle
     * @param nID Unique ID for the vehicle
     */
    public void SetID(int nID);
    
    /**
     * Retrieves the ID attribue for the Vehicle
     * @return The ID of the vehicle.
     */
    public int GetID();
    
    /**
     * Retrieves the weight of the vehicle
     * @return The weight of the vehicle.
     */
    public int GetWeight();
    
    /**
     * Sets the Condition variable associated with the VehicleInformation 
     * interface for the object.  The Condition variable will be used 
     * exclusively by one thread for waiting and signaling.
     * @param condition A Condition variable that will be used to synchronize
     * the Vehicle thread.
     */
    public void SetCondition(Condition condition);
    
    /**
     * Retrieves the Condition variable associated with the VehicleInformation 
     * interface for the object.  The Condition variable will be used 
     * exclusively by one thread for waiting and signaling.
     * @param condition A Condition variable that will be used to synchronize
     * the Vehicle thread.  Return value can be null.
     */
    public Condition GetCondition();
}
