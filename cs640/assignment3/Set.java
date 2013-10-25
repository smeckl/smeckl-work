import java.util.ArrayList;

public class Set<T> extends ArrayList<T>
{	
	private static final long serialVersionUID = 1L;	
	
	public Set()
	{
		super();
	}
	
	// Assumes right contains same type as current object
	// Performs a shallow copy of right.
	public Set(Set<T> right)
	{		
		// Call default constructor for ArrayList<T>
		super();
		
		// Add contents of right to this object
		for(int i = 0; i < right.size(); i++)
		{
			add(right.get(i));
		}
	}
	
	public void copy(Set<T> right)
	{
		// Add contents of right to this object
		for(int i = 0; i < right.size(); i++)
		{
			add(right.get(i));
		}
	}
	
	public boolean add(T t)
	{
		boolean bRet = true;
		
		if(!contains(t))
			bRet = super.add(t);
		
		return bRet;
	}
	
	public void union(Set<T> right)
	{
		// Add everything from the right
		for(int i = 0; i < right.size(); i++)
		{
			if(!contains(right.get(i)))
				add(right.get(i));
		}
	}
	
	public void intersect(Set<T> right)
	{
		// Loop through all of my objects
		for(int i = 0; i < size(); i++)
		{
			T element = get(i);
			
			// If the element is not in right, remove it
			if(!right.contains(element))
				remove(element);
		}
	}
	
	public boolean equals(Set<T> right)
	{
		boolean bRet = true;

		if(size() != right.size())
			bRet = false;
		else // Sets are the same size
		{
			// If the same size and every element in this is in right, then equal
			for(int i = 0; i < size() && bRet; i++)
			{
				bRet = right.contains(get(i));
			}
		}

		return bRet;
	}
	
	public String toString()
	{
		String strRet = ("{");
		
		for(int i = 0; i < size(); i++)
		{
			T t = get(i);
			strRet += t.toString();
			
			if(i != size() - 1)
				strRet += ",";
		}
		
		strRet += "}";
		
		return strRet;
	}
}