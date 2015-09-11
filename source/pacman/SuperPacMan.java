package pacman;

import javax.swing.ImageIcon;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Mighty PacMan' represents the pacman while in super pacman mode.
 * It contains its specific icons and speed.
 */

public class SuperPacMan extends PacMan {
	private static final ImageIcon ICON_RIGHT = new ImageIcon("blackpmRight.png");
	private static final ImageIcon ICON_LEFT = new ImageIcon("blackpmLeft.png");
	private static final ImageIcon ICON_UP = new ImageIcon("blackpmUp.png");
	private static final ImageIcon ICON_DOWN = new ImageIcon("blackpmDown.png");
	private static final int SPEED = 2;

	/**
	 * A constructor for a new super pacman. receives the old pacman's (regular or mighty) location and direction
	 * @param parent - The game panel
	 * @param pixelLocationX - The X location of the old pacman in the image 
	 * @param pixelLocationY - The Y location of the old pacman in the image
	 * @param matrixLocationX - The X location of the old pacman in the matrix
	 * @param matrixLocationY - The Y location of the old pacman in the matrix
	 * @param direction - The direction of the old pacman
	 */
	public SuperPacMan(GamePanel parent, int pixelLocationX, int pixelLocationY, int matrixLocationX, int matrixLocationY, int direction) {
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
