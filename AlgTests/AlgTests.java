package AlgTests;

public class AlgTests 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
	    String string1 = "epic";
	    String string2 = "epidemic";
	    
	    EditDistance dist = new EditDistance(string1, string2);
	    
	    System.out.println("The edit distance for \"" + string1 + "\" and \"" + string2 + "\" is: " + dist.CalculateDistance());
	}

}
