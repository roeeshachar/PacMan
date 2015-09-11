package pacman;

import javax.swing.ImageIcon;

/** 
 * @author Tom Leibovich and Roee Shachar
 * 'PacMan' represents the pacman. It contains its unique start position, and the next direction desired by the user.
 * In order to apply the relationships between the various pacman's and monsters, the class implements
 * the Visited interface, which allows a monster to see if it can beat pacman.
 */
public abstract class PacMan extends GameCharacter implements Visited {
	/** The pacman's start positions: in the start of the game and once beaten by a monster */
	private static final int START_MATRIX_X = 12;
	private static final int START_MATRIX_y = 22;
	private static final int START_PIXEL_X = 207;
	private static final int START_PIXEL_Y = 366;
	/** The next diretion desired by the user */
	private int nextDirection;
	/** States if the pacman is in Mighty or Super mode */
	private boolean special;

	/**
	 * A constructor for a new pacman, uses its initial start positions.
	 * @param parent - The game panel
	 * @param icon - The initial icon
	 * @param speed - The speed of the pacman, how many pixels it moves in each step
	 */
	public PacMan(GamePanel parent, ImageIcon icon, int speed) {
		super(parent, START_PIXEL_X, START_PIXEL_Y, START_MATRIX_X, START_MATRIX_y, icon, 0, speed);
		
	}
	
	/**
	 * A constructor for a new pacman, used when regular pacman turns into mighty or super,
	 * which in this case receives the position from the regular pacman, and the icon from the specific pacman.
	 * @param parent - The game panel
	 * @param pixelLocationX - The X location of the old pacman in the image
	 * @param pixelLocationY - The Y location of the old pacman in the image
	 * @param matrixLocationX - The X location of the old pacman in the matrix
	 * @param matrixLocationY - The Y location of the old pacman in the matrix
	 * @param icon - The initial icon
	 * @param direction - The direction of the old pacman
	 * @param speed - The speed of the pacman, each special pacman contains its determined speed
	 */
	public PacMan(GamePanel parent, int pixelLocationX, int pixelLocationY,int matrixLocationX,int matrixLocationY, ImageIcon icon, int direction, int speed){
		super(parent, pixelLocationX, pixelLocationY, matrixLocationX, matrixLocationY, icon, direction, speed);
		pixelStartPositionX = START_PIXEL_X;
		pixelStartPositionY = START_PIXEL_Y;
		matrixStartPositionX = START_MATRIX_X;
		matrixStartPositionY = START_MATRIX_y;
	}
	
	/**
	 * 'accept' method is used to determine the relations between the various pacmans and monsters using
	 * the polymorphism in run-time. 
	 * @param v - A visitor, monster
	 */
	public abstract boolean accept(Visitor v);
	

	/**
	 * Sets the given direction as the next direction
	 * @param nextDirection - The direction to be set as the next direction
	 */
	public void setNextDirection(int nextDirection) {
		this.nextDirection = nextDirection;
	}

	/**
	 * A getter for the next direction
	 * @return The next direction
	 */
	public int getNextDirection() {
		return nextDirection;
	}
	
	/**
	 * States if the pacman is in special mode: mighty or super
	 * @return True - if in mighty or super mode, False - otherwise
	 */
	public boolean isSpecial(){
		return special;
	}
	
	/**
	 * Determines the isSpecial field.
	 * @param special - True, if pacman is in mighty or super mode, False - otherwise
	 */
	public void setAsSpecial(boolean special){
		this.special=special;
	}
}
