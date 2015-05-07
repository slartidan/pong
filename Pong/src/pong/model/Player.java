package pong.model;

public class Player extends Item {

	public ItemDirection direction;

	public boolean directionChange(ItemDirection newDirection) {
		if (direction == newDirection)
			return false;
		direction = newDirection;
		return true;
	}

}
