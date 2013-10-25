package MiniMUDShared;

public class PlayerMoveMessage extends Message
{
	public enum Direction
	{
		North,
		South,
		East,
		West,
		Northeast,
		Northwest,
		Southeast,
		Southwest,
		Up,
		Down
	}
	
	private Direction m_dir;
	
	public PlayerMoveMessage(Direction dir)
	{
		super(MessageID.MOVE);
		
		setDirection(dir);
	}
	
	public void setDirection(Direction dir)
	{
		m_dir = dir;
	}
	
	public Direction getDirection()
	{
		return m_dir;
	}
	
	public String getDirectionString()
	{
		String strOut = "";
		
		switch (m_dir)
		{
		case North:
			strOut = "north";
			break;
			
		case South:
			strOut = "south";
			break;
			
		case East:
			strOut = "east";
			break;
			
		case West:
			strOut = "west";
			break;
			
		case Northeast:
			strOut = "northeast";
			break;
			
		case Northwest:
			strOut = "northwest";
			break;
			
		case Southeast:
			strOut = "southeast";
			break;
			
		case Southwest:
			strOut = "southwest";
			break;
			
		case Up:
			strOut = "up";
			break;
			
		case Down:
			strOut = "down";
			break;
		}
		
		return strOut;
	}
}
