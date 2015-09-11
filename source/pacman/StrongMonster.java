package pacman;

import java.util.Arrays;

import javax.swing.ImageIcon;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Strong Monster' represents the strong monster in the game panel. It beats both regular
 * and mighty pacman. In addition the strong monsters chases the pacman once it is in regular/mighty mode
 * but run from it when it's in super mode.
 */
public class StrongMonster extends Monster {
	/** States that the monster is chasing pacman */
	public static final int CHASE = 1;
	/** States that the monster is running from pacman */
	public static final int RUN = -1;
	private int state; 

	/**
	 * A constructor for a new monster, as default chases pacman
	 * @param parent - The game panel
	 * @param icons - The mosnter's set of icons for each movement
	 */
	public StrongMonster(GamePanel parent, ImageIcon[] icons) {
		super(parent, icons[1]);
		iconUp = icons[0];
		iconRight = icons[1];
		iconDown = icons[2];
		iconLeft = icons[3];
		state = CHASE;
	}


	/**
	 * Overrides the move method in Game Character, in order to calculate the priority of the
	 * next possible moves. In each step the monster calculates its location relative to pacman,
	 * and chooses the the one with the greatest distance to pacman.
	 * If the chosen move is not a legal move, than it goes to the step with the next priority.
	 */
	public boolean move() {
		int nextDirection;
		int pacmanX = parent.getPacManX();
		int pacmanY = parent.getPacManY();
		
		int[] directionPriority = new int[4];
		/** The direction is added to the priority in order to achieve it once the move is chosen */
		directionPriority[0]=(pacmanX-pixelLocationX)*(10)*state+GameCharacter.RIGHT;
		directionPriority[1]=((pacmanX-pixelLocationX)*(-10)*state+GameCharacter.LEFT);
		directionPriority[2]=(pacmanY-pixelLocationY)*(10)*state+GameCharacter.DOWN;
		directionPriority[3]=((pacmanY-pixelLocationY)*(-10)*state+GameCharacter.UP);
		Arrays.sort(directionPriority);
		
		int i=3;
		
		do {
			nextDirection = directionPriority[i]%10;
			if (nextDirection < 0) {
				nextDirection += 10;
			}
			if(isLegalMove(nextDirection) && (Math.abs(nextDirection-currentDirection) != 2) || i==0) {
				setDirection(nextDirection);

				break;
			}
			i--;
		}
		while (i>=0);
		return super.move();
	}
	
	/**
	 * Sets the state from chase to run, based on pacman's mode
	 * @param state - Chase or Run
	 */
	public void setState(int state) {
		if (state == CHASE)
			this.state = CHASE;
		else if (state == RUN) 
			this.state = RUN;
	}
		
	/**
	 * A method that states that the strong monster beats the regular pacman
	 * @param rp - The regular pacman encountered
	 */
	public boolean visit(RegularPacMan rp) {
		return true;
	}

	/**
	 * A method that states that the strong monster beats the mighty pacman
	 * @param mp - The mighty pacman encountered
	 */
	public boolean visit(MightyPacMan mp) {
		return true;
	}

	/**
	 * A method that states that the strong monster doesn't beats the super pacman
	 * @param sp - The super pacman encountered
	 */
	public boolean visit(SuperPacMan sp) {
		return false;
	}
}
