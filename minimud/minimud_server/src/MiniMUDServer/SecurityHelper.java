package MiniMUDServer;
import java.security.SecureRandom;
import java.security.MessageDigest;

public class SecurityHelper
{
	public static byte[] getPwdSalt()
	{
		SecureRandom random = new SecureRandom();
		
		byte randBytes[] = new byte[16];
		
		random.nextBytes(randBytes);
	    
		return randBytes;
	}
	
	public static byte[] hashPassword(String strPasswd, byte salt[])
	{
		byte hashRet[] = null;

		 try 
		 {
			 MessageDigest md = MessageDigest.getInstance("SHA-256");
			 
		     md.update(salt);
		     md.update(strPasswd.getBytes());
		     byte hashBytes[] = md.digest();
		     
		     hashRet = hashBytes.clone();
		 } 
		 catch (Exception e) 
		 {
		     hashRet = null;
		 }
		 
		 return hashRet;
	}
	
	public static boolean verifyPassword(String strPassword, byte hash[], byte salt[])
	{
		boolean bRet = false;
		
		byte computedHash[] = hashPassword(strPassword, salt);
		
		bRet = MessageDigest.isEqual(computedHash, hash);
		
		return bRet;
	}
}
