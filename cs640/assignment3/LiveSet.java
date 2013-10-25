
public class LiveSet extends Set<String>
{
	private static final long serialVersionUID = 1L;
	
	public LiveSet()
	{
		super();
	}
	
	public LiveSet(LiveSet right)
	{
		super(right);
	}
	
	public boolean add(String str)
	{
		boolean bRet = false;
		
		boolean bFound = false;
		
		for(int i = 0; !bFound && i < size(); i++)
		{
			if(0 == get(i).compareTo(str))
				bFound = true;
		}
		
		if(!bFound)
			bRet = super.add(str);
		
		return bRet;
	}
}
