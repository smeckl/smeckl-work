package AlgTests;

public class EditDistance
{
    private String n;
    private String m;
    
    private final int INSERT_COST = 1;
    private final int DELETE_COST = 3;
    private final int REPLACE_COST = 4;
    
    public EditDistance(String n_in, String m_in)
    {
        n = n_in;
        m = m_in;
    }
    
    public int CalculateDistance()
    {       
        if(n.length() == 0)
            return (m.length() * INSERT_COST);
        
        if(m.length() == 0)
            return (n.length() * DELETE_COST);
        
        int T[][] = new int[n.length()+1][m.length()+1];            
        
        // Initialize costs array to max value
        // Except for cells with index of 0, which will be 0
        for(int i = 0; i <= n.length(); i++)
        {
            for(int j = 0; j <= m.length(); j++)
            {
                if(i == 0 && j > 0)
                    T[i][j] = (INSERT_COST*j);
                else if(j == 0 && i > 0)
                    T[i][j] = (DELETE_COST*i);
                else if(i == 0 && j == 0)
                    T[i][j] = 0;
                else
                    T[i][j] = Integer.MAX_VALUE;
            }
        }
        
        print_array(T, n.length(), m.length());
        
        // Now, calculate min edit distance using dynamic programming
        for(int i = 1; i <= n.length(); i++)
        {
            for(int j = 1; j <= m.length(); j++)
            {
                int del_cost = DELETE_COST + T[i-1][j];
                
                int ins_cost = INSERT_COST + T[i][j-1];
                
                // Handle Replace
                int rep_cost = Integer.MAX_VALUE;
                                
                if(n.charAt(i-1) == m.charAt(j-1))  // Need to convert from 1-based loop to 0-based index
                    rep_cost = T[i-1][j-1];
                else
                    rep_cost = REPLACE_COST + T[i-1][j-1];
                
                int min_cost = min(del_cost, ins_cost, rep_cost);
                
                if(min_cost < T[i][j])
                    T[i][j] = min_cost;
                
                print_array(T, n.length(), m.length());
            }
        }
        
        print_array(T, n.length(), m.length());
        
        return T[n.length()][m.length()];
    }
            
    private int min(int a, int b, int c)
    {
        int ret = a;
        
        if(b < ret)
            ret = b;
        
        if(c < ret)
            ret = c;
        
        return ret;        
    }
    
    private void print_array(int T[][], int n, int m)
    {
        System.out.print("    ");
        for(int j = 0; j <= m; j++)
            System.out.print(j + "\t");
        
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        
        for(int i = 0; i <= n; i++)
        {
            System.out.print(i + " | ");
            
            for(int j = 0; j <= m; j++)
            {
                System.out.print(T[i][j] + "\t");
            }
            
            System.out.println();
        }
    }
}
