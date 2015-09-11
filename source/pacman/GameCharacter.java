package pacman;

import javax.swing.ImageIcon;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Game Character' represents a moving object in the game panel. It contains its location in the matrix,
 * and in the game panel image, its speed, different icons for each movement and methods that determine
 * how will it move in the panel. 
 *
 */
public abstract class GameCharacter {
	/**  how many pixels the object will move in each step. */
	protected int speed;
	
	/**  The direction the object is in now (determined by the constants: UP, DOWN, LEFT, RIGHT) */
	protected int currentDirection;
	
	/**  The next desired direction, will turn to current direction once possible */
	protected int nextDirection;
	
	/**  The x,y coordinates of the object in the matrix and in the game panel's image. */
	protected  int pixelStartPositionX, pixelStartPositionY, matrixStartPositionX, matrixStartPositionY, pixelLocationX, pixelLocationY, matrixLocationX, matrixLocationY;
	
	/**  The object's current icon */
	protected ImageIcon icon;
	
	/**  The game panel */
	protected GamePanel parent;
	
	/**  The object's icons according to each movement */
	protected ImageIcon iconUp, iconDown, iconLeft, iconRight;
	
	/**  Direction constants */
	public static final int IDLE = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	
	/**  The start position of the game panel's image, and accordingly the offset to be added in each calculation */
	public static final int OFFSET_PARAMETER_X = 15;
	public static final int OFFSET_PARAMETER_Y = 14;
	
	/**
	 * A constructor for a new Game Character.
	 * @param parent - The game panel
	 * @param startPixelX - The X position (in the image) the character starts a new game from, and the position it goes back to once beaten by a another character.
	 * @param startPixelY - The Y position (in the image) the character starts a new game from, and the position it goes back to once beaten by a another character.
	 * @param startMatrixX - The X position (in the matrix) the character starts a new game from, and the position it goes back to once beaten by a another character.
	 * @param startMatrixY - The Y position (in the matrix) the character starts a new game from, and the position it goes back to once beaten by a another character.
	 * @param icon - The icon of the character at the beginning of the game.
	 * @param direction - The initial direction of the character
	 * @param speed - How many pixels the character moves in each step.
	 */
	public GameCharacter(GamePanel parent, int startPixelX, int startPixelY, int startMatrixX, int startMatrixY, ImageIcon icon, int direction, int speed) {
		this.parent = parent;
		this.speed = speed;
		this.icon = icon;
		currentDirection = direction;
		nextDirection = direction;
		pixelStartPositionY = startPixelY;
		pixelStartPositionX = startPixelX;
		pixelLocationX = startPixelX;
		pixelLocationY = startPixelY;
		matrixStartPositionX = startMatrixX;
		matrixStartPositionY = startMatrixY;
		matrixLocationX = startMatrixX;
		matrixLocationY = startMatrixY;
	}
	
	/**
	 * 'Move' determines how the character will move based on the current direction chosen.
	 * In each call to this method, it calls the method 'isLegalMove' to check whether the step is legal,
	 * and calculates the character's next location based on its speed.
	 * @return True - if the move was accomplished successfully, False - otherwise.
	 */
	public boolean move() {
		// if the next desired location is not an obstacle nor the end of the image.
		if (!isLegalMove(currentDirection))
			return false;
		switch(currentDirection) {
		case UP:
			pixelLocationY -= speed;
			pixelLocationY %= parent.getHeight();
			break;
		case RIGHT:
			pixelLocationX += speed;
			pixelLocationX %= parent.getWidth();
			break;
		case DOWN:
			pixelLocationY += speed;
			pixelLocationY %= parent.getHeight();
			break;
		case LEFT:
			pixelLocationX -= speed;
			pixelLocationX %= parent.getWidth();
			break;
		}
		
		//applying the scale between the matrix and the pixels
		if ((pixelLocationX-OFFSET_PARAMETER_X)%GamePanel.RESOLUTION_PARAMETER==0 && (pixelLocationY-OFFSET_PARAMETER_Y)%GamePanel.RESOLUTION_PARAMETER==0) {
			matrixLocationX=(pixelLocationX-OFFSET_PARAMETER_X)/GamePanel.RESOLUTION_PARAMETER;
			matrixLocationY=(pixelLocationY-OFFSET_PARAMETER_Y)/GamePanel.RESOLUTION_PARAMETER;
		}

		return true;
	}
	
	/**
	 * 'isLegalMove' checks whether the next direction is an obstale or the end of the image.
	 * @param direction - The desired direction
	 * @return - True - If the move is legal, False - otherwise
	 */
	public boolean isLegalMove(int direction) {
		int x=0 , y=0;
		switch(direction) {
		//checking if an obstacle considering the scale
		case UP:
			if((pixelLocationX-OFFSET_PARAMETER_X)%GamePanel.RESOLUTION_PARAMETER !=0)
				return false;
				y = -1;
			break;
		case RIGHT:
			if((pixelLocationY-OFFSET_PARAMETER_Y)%GamePanel.RESOLUTION_PARAMETER !=0)
				return false;
			x = 1;
			break;
		case DOWN:
			if((pixelLocationX-OFFSET_PARAMETER_X)%GamePanel.RESOLUTION_PARAMETER !=0)
				return false;
			y = 1;
			break;
		case LEFT:
			if((pixelLocationY-OFFSET_PARAMETER_Y)%GamePanel.RESOLUTION_PARAMETER !=0)
				return false;
			x = -1;
			break;
		}
		try {
			if (parent.getMatrix()[matrixLocationY+y][matrixLocationX+x] == 0) {		
				return false;
			}
			else {
				return true;
			}
		}
		catch (RuntimeException e) {	
			// if the next step brings us to the end of the image
			if (e instanceof ArrayIndexOutOfBoundsException && (parent.getMatrix()[matrixLocationY][matrixLocationX] == 8)) {
				//left portal
				if(matrixLocationX == 0){
					pixelLocationX=399;
					matrixLocationX=parent.getMatrix()[matrixLocationY].length-1;
				}
				else{
					// right portal
					matrixLocationX=0;
					pixelLocationX=OFFSET_PARAMETER_X-GamePanel.RESOLUTION_PARAMETER/2;
				}
			return true;
			}
			return false;
		}
	}
	
	/**
	 * Sets the next direction, and changes the icon of the character accordingly
	 * @param direction
	 */
	public void setDirection(int direction){
		currentDirection = direction;
		switch(currentDirection) {
		case UP:
			icon = iconUp;
			break;
		case RIGHT:
			icon = iconRight;
			break;
		case DOWN:
			icon = iconDown;
			break;
		case LEFT:
			icon = iconLeft;
			break;
		}
	}

	/**
	 * Sends the character to its start position, in case beaten by a another character.
	 */
	public void goToStart(){
		pixelLocationX = pixelStartPositionX;
		pixelLocationY = pixelStartPositionY;
		matrixLocationX = matrixStartPositionX;
		matrixLocationY = matrixStartPositionY;
	}
	
	/**
	 * A getter for the character's X coordinate in the matrix. 
	 * @return The character's X coordinate in the matrix
	 */
	public int getMatrixLocationX() {
		return matrixLocationX;
	}
	
	/**
	 * A getter for the character's X coordinate in the matrix.
	 * @return The character's Y coordinate in the matrix.
	 */
	public int getMatrixLocationY() {
		return matrixLocationY;
	}

	/**
	 * A setter for the character's X coordinate in the matrix.
	 * @param matrixLocationX - The desired X coordinate of the character in the matrix.
	 */
	public void setMatrixLocationX(int matrixLocationX) {
		this.matrixLocationX = matrixLocationX;
	}

	/**
	 * A setter for the character's Y coordinate in the matrix.
	 * @param matrixLocationY - The desired Y coordinate of the character in the matrix.
	 */
	public void setMatrixLocationY(int matrixLocationY) {
		this.matrixLocationY = matrixLocationY;
	}

	/**
	 * A getter for the character's X coordinate in the game panel image.
	 * @return - The character's X coordinate in the game panel image.
	 */
	public int getPixelLocationX() {
		return pixelLocationX;
	}

	/**
	 * A getter for the character's X coordinate in the game panel image.
	 * @return - The character's X coordinate in the game panel image.
	 */
	public int getPixelLocationY() {
		return pixelLocationY;
	}
	
	/**
	 * A setter for the next desired direction.
	 * @param nextDirection - The next desired direction
	 */
	public void setNextDirection(int nextDirection) {
		this.nextDirection = nextDirection;
	}

	/**
	 * A getter for the character's current direction
	 * @return - The character's current direction
	 */
	public int getDirection() {
		return currentDirection;
	}

	/**
	 * A getter for the character's current icon
	 * @return - The character's current icon
	 */
	public ImageIcon getIcon() {
		return icon;
	}

}
