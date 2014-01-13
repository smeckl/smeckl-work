/*
 * Created by Steven W. Meckl, G00459475 for CS 571, Fall 2008
 * Octomber 8, 2008 */

package project1;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Steven W. Meckl, G00459475
 */
public class Main 
{
    private static int m_nCurrentID = 1;
    private static final int WAIT_MULTIPLIER = 1000; // Multiplier for specified wait unit
    
    // Object to control access to bridge.  Is intended to be passed to 
    // Vehicle objects.  
    private static BridgeController m_BridgeController;
    
    /**
     * @param No command-line arguments
     */
    public static void main(String[] args) 
    {               
        m_BridgeController = new BridgeController();
        
        Scanner kbIn = new Scanner(System.in);
        
        System.out.println("Enter the number of the traffic schedule defined in the");
        System.out.println("homework assignment (1-6) and press <Enter>:");
        
        int nSchedule = 0;
        
        try
        {
            nSchedule = kbIn.nextInt();
        }
        catch(InputMismatchException e)
        {
            nSchedule = 0;
        }
        
        if((nSchedule >= 1) && (nSchedule <= 6))
        {
            switch(nSchedule)
            {
                case 1:
                    RunSchedule1();
                    break;
                    
                case 2:
                    RunSchedule2();
                    break;
                
                case 3:
                    RunSchedule3();
                    break;
                    
                case 4:
                    RunSchedule4();
                    break;
                    
                case 5:
                    RunSchedule5();
                    break;
                    
                case 6:
                    RunSchedule6();
                    break;
            }
        }
        else
            System.out.println("Invalid input.  Valid values are 1-6.");
    }
    
    public static void RunSchedule1()
    {          
        Schedule schedule = new Schedule(3, 1.0, 0.0, 0.0);
        
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 20));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 10));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 10));
        
        System.out.println("Running Schedule 1: " + schedule);
        
        runSchedule(schedule);
    }

    public static void RunSchedule2()
    {                
        Schedule schedule = new Schedule(3, 0.0, 0.0, 1.0);
        
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 10));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 10));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 5));
        
        System.out.println("Running Schedule 2: " + schedule);
        
        runSchedule(schedule);
    }
    
    public static void RunSchedule3()
    {
        Schedule schedule = new Schedule(1, 0.5, 0.3, 0.2);
        
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 30)); 
        
        System.out.println("Running Schedule 3: " + schedule);
        
        runSchedule(schedule);
    }
    
    public static void RunSchedule4()
    {
        Schedule schedule = new Schedule(5, 0.5, 0.25, 0.25);
        
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 20));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 45));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 10));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 45));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 5));
        
        System.out.println("Running Schedule 4: " + schedule);
        
        runSchedule(schedule);
    }
    
    public static void RunSchedule5()
    {
        Schedule schedule = new Schedule(5, 0.5, 0.25, 0.25);
        
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 20));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 5));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 10));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 5));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 5));
        
        System.out.println("Running Schedule 5: " + schedule);
        
        runSchedule(schedule);
    }
    
    public static void RunSchedule6()
    {
        Schedule schedule = new Schedule(3, 0.3, 0.3, 0.4);
        
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 20));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Delay, 15));
        schedule.AddEntry(new ScheduleEntry(ScheduleEntry.Type.Arrival, 10));
        
        System.out.println("Running Schedule 6: " + schedule);
        
        runSchedule(schedule);
    }    
    
    private static void runSchedule(Schedule schedule)
    {
        try
        {
            for(int i = 0; i < schedule.GetNumberOfEntries(); i++)
            {
                ScheduleEntry entry = schedule.GetEntry(i);                


                switch(entry.GetType())
                {
                    case Arrival:
                        simulateVehicleArrival(schedule, entry);
                        break;

                    case Delay:
                        Thread.sleep(entry.GetValue() * WAIT_MULTIPLIER);
                        break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Encountered Exception: " + e);
        }
    }
    
    private static void simulateVehicleArrival(Schedule schedule, ScheduleEntry currentEntry)
    {
        Random rand = new Random();
        
        // Create currentEntry Vehicle thread objects
        for(int i = 0; i < currentEntry.GetValue(); i++)
        {
            // Random number to determine the type of Vehicle
            double fRandType = rand.nextDouble();
            
            //  Type of Vehicle to create
            Vehicle.Type type;
            
            //  Direction Vehicle will be traveling
            Vehicle.Direction direction;
            
            // Determine Type of Vehicle
            if((fRandType < schedule.GetCarProbability()))
            {                
                type = Vehicle.Type.Car;
            }
            else if( (fRandType >= schedule.GetCarProbability())
                     && (fRandType < (schedule.GetCarProbability() + schedule.GetVanProbability())) )
            {
                type = Vehicle.Type.Van;
            }
            else
                type = Vehicle.Type.Truck;
            
            // Determine Direction Vehicle is heading
            double fRandDirection = rand.nextDouble();
            
            if(fRandDirection < 0.5)
                direction = Vehicle.Direction.Northbound;
            else
                direction = Vehicle.Direction.Southbound;
            
            // Create new Vehicle and start its thread
            Vehicle vehicle = new Vehicle(getNextVehicleID(), type, direction,
                                          m_BridgeController);
            vehicle.start();
        }
    }
    
    private static int getNextVehicleID()
    {
        int nNextID = m_nCurrentID;
        
        m_nCurrentID++;
        
        return nNextID;
    }
}
