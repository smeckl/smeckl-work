package AlgTests;

import java.util.Vector;
import java.util.Collections;

public class ChangeMaker
{
    private Vector<Integer> values = null;
    
    private class LogEntry
    {
        public int last;
        public int num;
        public int valInd;
    }
    
    public ChangeMaker()
    {
        values = new Vector<Integer>();
        
        // Make sure 1 is in the list
        values.add(new Integer(1));
    }
     
    public void addCoinValue(int newVal)
    {
        if(0 > Collections.binarySearch(values, new Integer(newVal)))
            values.add(new Integer(newVal));
    }
    
    public int minNumCoins(int wholeVal)
    {
        sortValues();
        
        int nc[] = new int[wholeVal+1];
        LogEntry log[] = new LogEntry[wholeVal+1];
        
        for(int i = 0; i <= wholeVal; i++)
            log[i] = new LogEntry();
        
        // Recursion for making change
        //
        //           { 0   if i == 0 
        //  nc[i] =  { 
        //           { min(for all v in values) {(i/v) + nc[i - (v*(i/v))]
        //
        
        // Initialize nc[]
        nc[0] = 0;
        for(int i = 1; i <= wholeVal; i++)
            nc[i] = Integer.MAX_VALUE;
        
        log[0].last = -1;
        log[0].num = 0;
        log[0].valInd = 0;
        
        for(int i = 1; i <= wholeVal; i++)
        {
            log[i].last = -1;
            log[i].num = 0;
            log[i].valInd = -1;
        }
        
        for(int i = 1; i <= wholeVal; i++)
        {                        
            for(int j = 0; j < values.size(); j++)
            {
                int v = values.get(j);
                int cost = (i/v) + nc[i - (v*(i/v))];
                
                if(cost < nc[i])
                {
                    nc[i] = cost;
                    log[i].last = i - (v*(i/v));
                    log[i].valInd = j;
                    log[i].num = i/v;
                }
            }
        }
        
        printCoinCombo(log);
                
        return nc[wholeVal];
    }
    
    private void printCoinCombo(LogEntry log[])
    {
        int i = log.length-1;
        
        while(i > 0)
        {
            LogEntry entry = log[i];
            
            System.out.println(entry.num + " x " + values.get(entry.valInd));
            
            i = entry.last;
        }
    }
    
    private void sortValues()
    {
        Collections.sort(values);
    }  
}
