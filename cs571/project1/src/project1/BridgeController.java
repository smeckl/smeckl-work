/*
 * Created by Steven W. Meckl, G00459475 for CS 571, Fall 2008
 * Octomber 8, 2008
 */


package project1;

// Imported Interfaces and Classes
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Steven W. Meckl, G00459475
 * 
 * This class controls the access vehicles have to the simulated bridge.  The
 * bridge controller is implemented as a Monitor.  Access to the public methods
 * is mutually exclusive to all calling threads using the built-in Java
 * "synchronized" keyword.
 */
public class BridgeController 
{
    // Constants
    private static final int MAX_CARS_PER_LANE = 6;
    private static final int MAX_BRIDGE_WEIGHT = 500;
    
    // Lock object that will be used to manage synchronization in this Monitor
    private static ReentrantLock m_lock = new ReentrantLock();    
    
    // Wait queues for Northbound and Southbound Vehicles
    private Queue<VehicleInformation> m_NorthboundQueue;
    private Queue<VehicleInformation> m_SouthboundQueue;
    
    // Queue of vehicles crossing the bridge
    private Queue<VehicleInformation> m_BridgeQueue;
    
    // Current direction of traffic on the bridge
    private VehicleInformation.Direction m_currentDirection;
    
    // Number of consecutive vehicles that have traveled in the current
    // direction.
    private int m_nDirectionCount;
    
    /**
     * Constructor for the BridgeController class.  Initializes the three
     * queue objects.
     */
    public BridgeController()
    {
        // Create objects for queues
        m_NorthboundQueue = new LinkedList<VehicleInformation>();
        m_SouthboundQueue = new LinkedList<VehicleInformation>();
        m_BridgeQueue = new LinkedList<VehicleInformation>();
        
        // Initialize current direction (arbitrarily chose Northbound)
        m_currentDirection = VehicleInformation.Direction.Northbound;
        
        // Initialize to 1
        m_nDirectionCount = 1;
    }
    
    /**
     * This method is used when a vehicle requests crossing of the bridge.  This
     * method should exit only when the calling vehicle is permitted to cross
     * the bridge.
     * @param vehicle A VehicleInformation object containing information about
     * the Vehicle that is requesting access to cross the bridge.
     * @return true if the request is successful.  false otherwise.
     */
    public void RequestCrossing(VehicleInformation vehicle)
    {
       // Lock the monitor to provide mutual exclusion
        m_lock.lock();
        
        try
        {
            // Print the status message for arrival of the vehicle.
            printArrivalStatus(vehicle);

            // If the vehicle has not been assigned a Condition for synchronization
            // yet, then create and assign it now.
            if(null == vehicle.GetCondition())
            {
                vehicle.SetCondition(m_lock.newCondition());
            }                                   

            // Check to see if we can cross.  If not, wait.
            if(!vehicleCanCrossImmediately(vehicle))
            {                                
                // Add the arriving vehicle to the proper queue
                switch(vehicle.GetDirection())
                {
                    case Northbound:
                        m_NorthboundQueue.add(vehicle);
                        break;

                    case Southbound:
                        m_SouthboundQueue.add(vehicle);
                        break;
                }
                
                printWaitStatus(vehicle);
                
                vehicle.GetCondition().await();                                
            }
            else
            {
                // Add vehicle to the Bridge's queue
                m_BridgeQueue.add(vehicle);
                
                // Print out status message
                printCrossingStatus(vehicle);
            }
        }
        catch (InterruptedException e) // Thrown by the Condition.await() method
        {
            System.out.println("Thread wait() interrupted");
        }
        catch(Exception e) // Catch generic exception
        {
            System.out.println("Encountered Exception: " + e);
        }
        finally
        {
            // Unlock the Monitor to allow other threads access
            m_lock.unlock();
        }        
    }
  
    /**
     * This method is used when a vehicle begins crossing of the bridge.  This 
     * method prints out the status of the queues and a message describing which
     * vehicle has started crossing the bridge.
     * @param vehicle A VehicleInformation object containing information about
     * the Vehicle that is crossing the bridge.
     * @return true if the request is successful.  false otherwise.
     */
    public boolean NotifyCrossingStatus(VehicleInformation vehicle)
    {
        boolean bRetVal = true; // Return value
        
        // Lock the monitor to provide mutual exclusion
        m_lock.lock();
        
        try
        {                        
            // Print out status message
            printCrossingStatus(vehicle);                        
        }        
        finally
        {
            // Unlock the Monitor to allow other threads access
            m_lock.unlock();
        }
        
        return bRetVal; 
    }
    
    /**
     * This method is used when a vehicle finishes crossing of the bridge.  This 
     * method prints out the status of the queues and a message describing which
     * vehicle has finished crossing the bridge.
     * @param vehicle A VehicleInformation object containing information about
     * the Vehicle that is exiting the bridge.
     * @return true if the request is successful.  false otherwise.
     */
    public boolean NotifyExitStatus(VehicleInformation vehicle)
    {
        boolean bRetVal = true;
        
        // Lock the monitor to provide mutual exclusion
        m_lock.lock();
        
        try
        {                       
            // Find exiting vehicle in the Bridge queue and remove it
            m_BridgeQueue.remove(vehicle);
            
            // If no vehicles are waiting in the opposite direction, then decrement
            // the direction counter
            Queue oppDirQueue = (m_currentDirection == VehicleInformation.Direction.Northbound)
                                ? m_SouthboundQueue : m_NorthboundQueue;
            
            if(0 == oppDirQueue.size())
                m_nDirectionCount--;
            
            printExitStatus(vehicle);
            
            // Choose the next vehicle to cross the bridge
            chooseNextVehicleToCross();                        
        }        
        finally
        {
            // Unlock the Monitor to allow other threads access
            m_lock.unlock();
        }
        
        return bRetVal;        
    }
    
    /**
     * Determines if a specified vehicle is allowed to cross the bridge
     * @param vehicle The vehicle that is requesting permission to cross the bridge
     * @return true if the vehicle is allowed to cross.  false otherwise.
     */
    private boolean vehicleCanCrossImmediately(VehicleInformation vehicle)
    {
        boolean bRetVal = false;        
        
        // The current weight of the bridge
        int nBridgeWeight = getBridgeWeight();
        
        Queue curDirQueue = (m_currentDirection == VehicleInformation.Direction.Northbound)
                                ? m_NorthboundQueue : m_SouthboundQueue;
        
        Queue oppDirQueue = (m_currentDirection == VehicleInformation.Direction.Northbound)
                                ? m_SouthboundQueue : m_NorthboundQueue;

        // If both wait queues and the bridge are empty, then we can cross as
        // long as we don't go over the max weight
        if(curDirQueue.isEmpty() && oppDirQueue.isEmpty() && m_BridgeQueue.isEmpty())
        {
            // Set the direction
            m_currentDirection = vehicle.GetDirection();
            
            // Reset the direction counter
            m_nDirectionCount = 1;
            
            bRetVal = true;
        }
        // Is it going in the same direction as current traffic?  Have too many
        // vehicles in that direction crossed already?  Is the wait queue empty()?
        // Will we be under max weight?
        else if((vehicle.GetDirection() == m_currentDirection)
                && (m_nDirectionCount < MAX_CARS_PER_LANE)
                && curDirQueue.isEmpty()
                && (nBridgeWeight + vehicle.GetWeight()) <= MAX_BRIDGE_WEIGHT)
        {            
            m_nDirectionCount++;
            bRetVal = true;            
        }
        // Can it cross in the other direction?
        else if(m_BridgeQueue.isEmpty() && oppDirQueue.isEmpty()
                && (m_nDirectionCount >= MAX_CARS_PER_LANE))
        {
            // Set the direction
            m_currentDirection = vehicle.GetDirection();
            
            // Reset the direction counter
            m_nDirectionCount = 1;
            
            bRetVal = true;
        }
        
        return bRetVal;
    }
    
    /**
     * This method determines the next vehicle that will be allowed to cross 
     * the bridge.  The chosen vehicle's thread will be signaled to continue
     * execution and the Cross() method will be allowe to run.
     * @return The next vehicle allowed to cross the bridge.
     */
    private void chooseNextVehicleToCross()
    {
        // The current weight of the bridge
        int nWeight = getBridgeWeight();
        
        Queue curDirQueue = (m_currentDirection == VehicleInformation.Direction.Northbound)
                                ? m_NorthboundQueue : m_SouthboundQueue;
        
        Queue oppDirQueue = (m_currentDirection == VehicleInformation.Direction.Northbound)
                                ? m_SouthboundQueue : m_NorthboundQueue;
       
        // Try to allow vehicles cross in the current direction first
        if(!curDirQueue.isEmpty() && (m_nDirectionCount < MAX_CARS_PER_LANE))
        {                       
            queueVehiclesToBridge(curDirQueue);

        }
        // Try to allow vehicles in the opposite direction cross
        else if(!oppDirQueue.isEmpty())
        {
            // Can switch directions only if bridge has vehicles on it
            if(m_BridgeQueue.isEmpty())
            {                                                        
                // Reset direction count
                m_nDirectionCount = 1;

                // Switch the direction
                m_currentDirection = (m_currentDirection == VehicleInformation.Direction.Northbound)
                                     ? VehicleInformation.Direction.Southbound 
                                     : VehicleInformation.Direction.Northbound;    
                
                queueVehiclesToBridge(oppDirQueue);
            }            
        } 
        // If both wait queues are empty, then don't do anything
    }
    
    private void queueVehiclesToBridge(Queue waitQueue)
    {                     
        int nNumToAdd = Math.min(waitQueue.size(), MAX_CARS_PER_LANE - m_nDirectionCount);
        
        boolean bContinue = true;
        
        for(int i = 0; (i < nNumToAdd) && bContinue; i++)
        {
            // Check to see if next vehicle in queue will send bridge over
            // its max weight
            VehicleInformation veh = (VehicleInformation)waitQueue.peek();
            
            if((getBridgeWeight() + veh.GetWeight()) <= MAX_BRIDGE_WEIGHT)
            {
                waitQueue.remove();
                
                m_BridgeQueue.add(veh);
                
                // Print the status message that the vehicle is crossing
                printCrossingStatus(veh);

                veh.GetCondition().signal();

                m_nDirectionCount++;
            }
            else
                bContinue = false;
        }
            
    }
    /**
     * Prints a status message saying that the specified vehicle has arrived.
     * @param vehicle The vehicle that has arrived at the bridge.
     */
    private void printArrivalStatus(VehicleInformation vehicle)
    {       
        System.out.println(printVehicleInfo(vehicle) + " arrived.");                          
    }
    
    /**
     * Prints a status message saying that a vehicle has begun crossing the bridge.
     * The contents of the two waiting queues and the bridge queue are also
     * printed.
     * @param vehicle The vehicle that has begun crossing the bridge.
     */
    private void printCrossingStatus(VehicleInformation vehicle)
    {
        // Print delimeter
        System.out.println("****************************************************");
        
        // Print vehicle crossing status information
        System.out.println("Vehicle #" + vehicle.GetID() + " is now crossing the bridge.");
        
        // Print Bridge Queue
        System.out.println("Vehicles on the bridge (" + m_currentDirection + "): "
                          + printQueue(m_BridgeQueue) + ".  Total Weight: " + getBridgeWeight());
        
        // Print Northbound queue
        System.out.println("Waiting " + VehicleInformation.Direction.Northbound 
                          + " vehicles: " + printQueue(m_NorthboundQueue));
        
        // Print Southbound queue
        System.out.println("Waiting " + VehicleInformation.Direction.Southbound 
                          + " vehicles: " + printQueue(m_SouthboundQueue));
        
        // Print delimeter
        System.out.println("****************************************************");
    }
    
    /**
     * Prints a status message saying that a vehicle is exiting the bridge.
     * @param vehicle The vehicle that is exiting the bridge.
     */
    private void printExitStatus(VehicleInformation vehicle)
    {
        // Print the status message
        System.out.println("Vehicle #" + vehicle.GetID() + " exited bridge.  "
                          + "Total weight: " + getBridgeWeight());                
        
        // Print delimeter
        System.out.println("****************************************************");
    }
    
    /**
     * Prints a message saying that a vehicle has entered a wait queue.
     * @param vehicle The vehicle that has entered a wait queue.
     */
    private void printWaitStatus(VehicleInformation vehicle)
    {
        // Print delimeter
        System.out.println("****************************************************");
        
        // Print the status message
        System.out.println("Vehicle #" + vehicle.GetID() + " entering wait state."); 
        
        // Print Bridge Queue
        System.out.println("Vehicles on the bridge (" + m_currentDirection + "): "
                          + printQueue(m_BridgeQueue) + ".  Total Weight: " + getBridgeWeight());
        
        // Print Northbound queue
        System.out.println("Waiting " + VehicleInformation.Direction.Northbound 
                          + " vehicles: " + printQueue(m_NorthboundQueue));
        
        // Print Southbound queue
        System.out.println("Waiting " + VehicleInformation.Direction.Southbound 
                          + " vehicles: " + printQueue(m_SouthboundQueue));
        
        // Print delimeter
        System.out.println("****************************************************");
    }
    
    /**
     * Builds a String containg a text representation of the contents of one of 
     * the Monitor's queues
     * .@param queue The queue to retrieve the contents of.
     * @return String containg a text representation of the queue's contents.
     */
    private String printQueue(Queue queue)
    {
        String out = "[";
        
        // Convert the queue to an array so we can loop through the elements 
        // without removing them from the queue
        Object[] vehicleArray = queue.toArray();
        
        // Looop through the contents of the array
        for(int i = 0; i < vehicleArray.length; i++)
        {
            // Add the vehicle's short string representation to the output string
            out += printVehicleInfoShort((VehicleInformation)vehicleArray[i]);
            
            // Add a comma if appropriate
            if(i < (vehicleArray.length - 1))
                out += ", ";
        }
        
        out += "]";
        
        return out;
    }
    
    /**
     * Calculates the total weight of all vehicles crossing the bridge
     */ 
    private int getBridgeWeight()
    {
        int nWeight = 0;
        
        // Convert the queue to an array so we can loop through the elements 
        // without removing them from the queue
        Object[] vehicleArray = m_BridgeQueue.toArray();
        
        // Looop through the contents of the array
        for(int i = 0; i < vehicleArray.length; i++)
        {
            VehicleInformation veh = (VehicleInformation)vehicleArray[i];
            
            // Add the weight of the vehicle to the sum
            nWeight += veh.GetWeight();
        }
        
        return nWeight;
    }
    
    /**
     * Builds a String containg a text representation of a vehicle
     * @param vehicle The vehicle to create a text version of.
     * @return The String containg the vehicle's information
     */
    private String printVehicleInfo(VehicleInformation vehicle)
    {
        String out = "Vehicle #" + vehicle.GetID() + " (" + vehicle.GetDirection()
                     + ", Type: " + vehicle.GetType() + ")";
        
        return out;
    }
    
    /**
     * Builds a short String containg a text representation of a vehicle
     * @param vehicle The vehicle to create a text version of.
     * @return The String containg the vehicle's information
     */
    private String printVehicleInfoShort(VehicleInformation vehicle)
    {
        String out = "#" + vehicle.GetID() + " (Type: " + vehicle.GetType() + ")";
        
        return out;
    }
}
