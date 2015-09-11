package pacman;

import javax.swing.ImageIcon;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Regular PacMan' represents the regular pacman, meaning in no mighty or super mode.
 * It contains its specific icons, and the accept method to determine the relations between him and the pacmans
 */
public class RegularPacMan extends PacMan {
	private static final ImageIcon ICON_RIGHT = new ImageIcon("pmRight.png");
	private static final ImageIcon ICON_LEFT = new ImageIcon("pmLeft.png");
	private static final ImageIcon ICON_UP = new ImageIcon("pmUp.png");
	private static final ImageIcon ICON_DOWN = new ImageIcon("pmDown.png");
	private static final int SPEED = 1;

	/**
	 * A constructor for a new regular pacman at the beginning of the game. Uses the constructor of 'PacMan' and only determines the icons
	 * @param parent - Game Panel
	 */
	public RegularPacMan(GamePanel parent) {
		super(parent,ICON_RIGHT, SPEED);
		iconRight = ICON_RIGHT;
		iconLeft = ICON_LEFT;
		iconUp = ICON_UP;
		iconDown = ICON_DOWN;
	}
	
	/**
	 * A constructor for a new regular pacman once returning from a special mode, receives its position and direction from the old pacman
	 * @param parent - Game Panel
	 * @param pixelLocationX - The X location of the old pacman in the image
	 * @param pixelLocationY - The Y location of the old pacman in the image
	 * @param matrixLocationX - The X location of the old pacman in the matrix
	 * @param matrixLocationY - The Y location of the old pacman in the matrix
	 * @param direction - The direction of the old pacman
	 */
	public RegularPacMan(GamePanel parent, int pixelLocationX, int pixelLocationY, int matrixLocationX, int matrixLocationY, int direction) {
		super(parent,pixelLocationX, pixelLocationY,matrixLocationX, matrixLocationY,  ICON_RIGHT, direction, SPEED);
		iconRight = ICON_RIGHT;
		iconLeft = ICON_LEFT;
		iconUp = ICON_UP;
		iconDown = ICON_DOWN;
	}

	/**
	 * 'accept' method is used to determine the relations between the various pacmans and monsters using
	 * the polymorphism in run-time. 
	 * @param v - A visitor, monster
	 */
	public boolean accept(Visitor v) {
	return v.visit(this);
	}
}
