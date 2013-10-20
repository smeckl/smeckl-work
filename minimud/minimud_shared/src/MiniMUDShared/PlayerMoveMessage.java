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
}
