package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * The game panel is where the game is played.
 * it draws the background, the points and game-characters, and reacts to the users key clicks.
 * This class also calls the methods which move the monsters by a timer that calls the actionPerformed method and 
 * deals with encounters between pacman and the monsters.  
 */

public class GamePanel extends JPanel implements ActionListener{
	
	/**  The abstract pacman object containing one type of non-abstract pacman class - regular super or mighty */
	private PacMan pacman;
	
	/** A key listener*/
	private KeyListener keyListener;
	
	/** An array of monsters. which will be initialized in the constructor to contain two strong and two weak monsters. */
	private Monster[] monsters = new Monster[4];
	
	/** A single timer for creating all timed-events */
	private static Timer timer;
	
	/** The current number of dots on the game panel. (pacman's food) */
	private int numberOfDots;
	
	/** Counting down 10 seconds when pacman turns into super\mighty */
	private int specialPacManTime;
	
	/** Number of lives remaining*/
	private int pacmanLives;
	
	/** The Main frame containing this panel */
	private Main parent;

	/** serial ID*/
	private static final long serialVersionUID = 4306858853915564842L;
	
	/** The ratio between game cells and pixels */
	public static final int RESOLUTION_PARAMETER = 16; 
	
	/** The game speed - the actionPerformed method is called every *value* milliseconds  */
	public static final int	GAME_SPEED = 12;
	
	/** The initial lives of pacman */
	public static final int PACMAN_LIVES=3;
	
	/** The ImageIcon object containing the background image */
	private static final ImageIcon BACKGROUND_IMAGE = new ImageIcon("bg.png");
	
	/** An array of ImageIcons containing all spiderman's photos */
	private static final ImageIcon[] MONSTER_ICONS_1= {new ImageIcon("spidyUp.png"),new ImageIcon("spidyRight.png"),new ImageIcon("spidyDown.png"),new ImageIcon("spidyLeft.png")};
	
	/** An array of ImageIcons containing all homer's photos */
	private static final ImageIcon[] MONSTER_ICONS_2= {new ImageIcon("homerUp.png"),new ImageIcon("homerRight.png"),new ImageIcon("homerDown.png"),new ImageIcon("homerLeft.png")};
	
	/** An array of ImageIcons containing all alien's photos */
	private static final ImageIcon[] MONSTER_ICONS_3= {new ImageIcon("alienUp.png"),new ImageIcon("alienRight.png"),new ImageIcon("alienDown.png"),new ImageIcon("alienLeft.png")};
	
	/** An array of ImageIcons containing all venom's photos */
	private static final ImageIcon[] MONSTER_ICONS_4= {new ImageIcon("venomUp.png"),new ImageIcon("venomRight.png"),new ImageIcon("venomDown.png"),new ImageIcon("venomLeft.png")};
	
	/** The ImageIcon object containing the spiral gif for the portal*/
	private static final ImageIcon SPIRAL = new ImageIcon("spiralsmall.gif");
	
	/** A boolean indicating if a click is the first click.  */
	private boolean firstClick = true;
	
	/** The initial number of dots */
	private static final int INITIAL_NUMBER_OF_POINTS = 246;
	
	/** Matrix indicating the current state of each cell. 0 - obstacle, 1- regular point, 2 - super point, 3 - mighty point, 9 - empty cell, 8 - portal */
	private int[][] pointMatrix;
	
	/** The initial matrix  */
	private static final int[][] INITIAL_MATRIX = {
		
			{1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
			{2,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,3},
			{1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
			{1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
			{1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1},
			{0,0,0,0,0,1,0,0,0,0,0,9,0,0,9,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,9,0,0,9,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,9,9,9,9,9,9,9,9,9,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,0,0,0,0,0,0,0,0,9,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,0,0,0,9,9,0,0,0,9,0,0,1,0,0,0,0,0},
			{8,9,9,9,9,1,9,9,9,0,9,9,9,9,9,9,0,9,9,9,1,9,9,9,9,8},
			{0,0,0,0,0,1,0,0,9,0,0,0,0,0,0,0,0,9,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,0,0,0,0,0,0,0,0,9,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,9,9,9,9,9,9,9,9,9,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,0,0,0,0,0,0,0,0,9,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,9,0,0,0,0,0,0,0,0,9,0,0,1,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
			{1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
			{3,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,2},
			{0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0},
			{0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0},
			{1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};

	
	/**
	 * The constructor initializes the game.
	 * It creates monsters and pacman objects and assigns the start value to the point matrix.
	 * It creates a keyListener which sends events only for the 4 direction keys.
	 * @param parent the Main object which contains this panel.
	 */
	public GamePanel(Main parent) {
		
		// assigning initial value to the fields.
		pointMatrix = cloneMatrix(INITIAL_MATRIX);
		this.parent = parent;
		monsters[0]=new WeakMonster(this, MONSTER_ICONS_1);
		monsters[1]=new WeakMonster(this, MONSTER_ICONS_2);
		monsters[2]=new StrongMonster(this, MONSTER_ICONS_3);
		monsters[3]=new StrongMonster(this, MONSTER_ICONS_4);
		pacmanLives = PACMAN_LIVES;
		pacman = new RegularPacMan(this);
		timer = new Timer(GAME_SPEED,this);
		specialPacManTime=1;
		numberOfDots=INITIAL_NUMBER_OF_POINTS;
		setFocusable(true);
		// creating the listener
		keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				// the timer is paused until the first click happens
				if (firstClick) {
					timer.start();
				}
				firstClick = false;
				// each key is setting a direction
				switch(key.getKeyCode()){
					case KeyEvent.VK_LEFT:{
						pacman.setNextDirection(GameCharacter.LEFT);
						break;
					}
					case KeyEvent.VK_UP:{
						pacman.setNextDirection(GameCharacter.UP);
						break;
					}
					case KeyEvent.VK_RIGHT:{
						pacman.setNextDirection(GameCharacter.RIGHT);
						break;
					}
					case KeyEvent.VK_DOWN:{
						pacman.setNextDirection(GameCharacter.DOWN);
						break;
					}
				}
			}
		};
		//the listener is added to this panel.
		addKeyListener(keyListener);

	}
	
	/**
	 * The method paint draws the graphics on this panel using double buffering technique.
	 * @param graphics is a graphics object, which the panel's graphics are edited by it.
	 */
	public void paint(Graphics graphics){
		// an empty image is set.
		Image bufferedImage = createImage(getSize().width, getSize().height);
		Graphics bufferedImageGraphics = bufferedImage.getGraphics();

		// first the background is painted
		paintBackground(bufferedImageGraphics);
		// then, the points and portal.
		paintPoints(bufferedImageGraphics);
		// lastly, the game characters are drawn.
		paintGameCharacters(bufferedImageGraphics);
		// the buffered image is copied on screen
		graphics.drawImage(bufferedImage, 0,0, this);

	}

	/**
	 * this method paints the background
	 * @param g is the graphics object of this panel
	 */
	private void paintBackground(Graphics g){
		BACKGROUND_IMAGE.paintIcon(this, g, 0, 0);
	}

	/**
	 * this method paints the points by the current state of each cell.
	 * @param g is the graphics object of this panel
	 */
	private void paintPoints(Graphics g) {
		g.setColor(Color.white);
		for (int i = 0; i < pointMatrix.length; i++) { 
			for (int j =0; j < pointMatrix[i].length; j++) {
				if (pointMatrix[i][j] != 0) {
					// normal dot
					if (pointMatrix[i][j] == 1)
						g.fillOval(19+RESOLUTION_PARAMETER*j, 21+RESOLUTION_PARAMETER*i, 5, 5);
					// mighty or super dot
					else if (pointMatrix[i][j] == 2 || pointMatrix[i][j] == 3){
						g.setColor(new Color(255,255*((pointMatrix[i][j])%2),255*(pointMatrix[i][j]+1)%2));
						g.fillOval(17+RESOLUTION_PARAMETER*j, 18+RESOLUTION_PARAMETER*i, 10, 10);
						g.setColor(Color.white);
					}		
					// portal cell
					else if(pointMatrix[i][j] == 8)
						SPIRAL.paintIcon(this, g, 12+RESOLUTION_PARAMETER*j, 12+RESOLUTION_PARAMETER*i);
				}
			}
		}
	}

	/**
	 * this method paints the game characters.
	 * it draws each character in its current location.
	 * @param g is the graphics object of this panel
	 */
	private void paintGameCharacters(Graphics g) {
		// monsters are drawn.
		for (Monster monster : monsters) {
			monster.getIcon().paintIcon(this, g, monster.getPixelLocationX(), monster.getPixelLocationY());
		}
		// pacman is drawn.
		pacman.getIcon().paintIcon(this, g, pacman.getPixelLocationX(), pacman.getPixelLocationY());
	}

	/**
	 * This method is called by the timer each GAME_SPPED milliseconds.
	 * it manages the movement of monsters and pacman on the board and deals with encounters and win/lose scenarios. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			// movements and encounters.
			moveMonsters();
			movePacManAndCheckForDots();
			checkForCharacterEncounter();
			
			// win \ lose
			if(numberOfDots ==0)
				win();
			if(pacmanLives == 0)
				lose();
			
			// returns pacman into normal if necessary
			specialPacManTime--;
			if(specialPacManTime ==0 && pacman.isSpecial())
				returnToRegularPacMan();
			
		}
		repaint();
	}

	/**
	 * This method moves the monsters. it calls the move method of each monster.
	 */
	private void moveMonsters(){
		for (Monster monster : monsters)
			monster.move();
	}

	/**
	 *  This method moves pacman and checks the cell it is currently on, if it's a special dot it turns pacman into mighty\super.
	 */
	private void movePacManAndCheckForDots(){

		// checks if the direction clicked by the user is legal.
		if(pacman.isLegalMove(pacman.getNextDirection())){
			pacman.setDirection(pacman.getNextDirection());
		}
		
		// checks if pacman actually moved and if it did it checks if the dot is special. 
		if(pacman.move()){
			if(pointMatrix[pacman.getMatrixLocationY()][pacman.getMatrixLocationX()] <8) {
				//superDot
				if(pointMatrix[pacman.getMatrixLocationY()][pacman.getMatrixLocationX()]==3) {
					pacman=new SuperPacMan(this, pacman.getPixelLocationX(), pacman.getPixelLocationY(), pacman.getMatrixLocationX(), pacman.getMatrixLocationY(), pacman.getDirection());
					pacman.setNextDirection(pacman.getDirection());
					pacman.setAsSpecial(true);
					((StrongMonster)monsters[2]).setState(StrongMonster.RUN);
					((StrongMonster)monsters[3]).setState(StrongMonster.RUN);
					specialPacManTime=10000/GAME_SPEED;
				}
				//mightyDot
				if(pointMatrix[pacman.getMatrixLocationY()][pacman.getMatrixLocationX()]==2) {
					pacman=new MightyPacMan(this, pacman.getPixelLocationX(), pacman.getPixelLocationY(), pacman.getMatrixLocationX(), pacman.getMatrixLocationY(), pacman.getDirection());
					pacman.setNextDirection(pacman.getDirection());
					pacman.setAsSpecial(true);
					
					((StrongMonster)monsters[2]).setState(StrongMonster.CHASE);
					((StrongMonster)monsters[3]).setState(StrongMonster.CHASE);
					specialPacManTime=10000/GAME_SPEED;
				}
				if (pointMatrix[pacman.getMatrixLocationY()][pacman.getMatrixLocationX()]!=8) {
					pointMatrix[pacman.getMatrixLocationY()][pacman.getMatrixLocationX()]=9;
				}
				numberOfDots--;
			}
		}
	}

	/**
	 * win scenario. stops the timer and shows the victory picture.
	 */
	public void win(){
		timer.stop();
		JOptionPane.showMessageDialog(this, "", "YOU WIN!", JOptionPane.OK_OPTION, new ImageIcon("win.png"));
	}

	/**
	 * lose scenario. stops the timer and shows the "you lose" picture.
	 */
	public void lose(){
		timer.stop();
		JOptionPane.showMessageDialog(this, "", "YOU LOSE!", JOptionPane.OK_OPTION, new ImageIcon("lose.png"));

	}

	/**
	 * this method checks if pacman is encountered by a monster and calls the beats method which works by the visitor pattern.
	 * it calls the relevant methods if pacman is eaten or eats.
	 */
	public void checkForCharacterEncounter(){
		for (Monster monster : monsters){
			if(Math.abs(monster.getPixelLocationX() - pacman.getPixelLocationX()) <= 10 && Math.abs(monster.getPixelLocationY() - pacman.getPixelLocationY()) <= 10)
				if(monster.beats(pacman)) {
					pacman.goToStart();
					pacmanLives--;
					parent.loseLife();
					for (Monster monsterToGoToStart : monsters)
						monsterToGoToStart.goToStart();
				}
				else
					monster.goToStart();
		}
		

	}

	/**
	 * This method is called when the special pacman time turns to 0 - meaning the super/mighty pacman turns back to regular.
	 */
	public void returnToRegularPacMan(){
		pacman=new RegularPacMan(this, pacman.getPixelLocationX(), pacman.getPixelLocationY(), pacman.getMatrixLocationX(), pacman.getMatrixLocationY(), pacman.getDirection());
		pacman.setNextDirection(pacman.getDirection());
		pacman.setAsSpecial(false);

		((StrongMonster)monsters[2]).setState(StrongMonster.CHASE);
		((StrongMonster)monsters[3]).setState(StrongMonster.CHASE);
	}

	/**
	 * the x coordination getter
	 * @return the x in the matrix
	 */
	public int getPacManX(){
		return pacman.getPixelLocationX();
	}

	/**
	 * the y coordination getter
	 * @return the y in the matrix
	 */
	public int getPacManY(){
		return pacman.getPixelLocationY();
	}

	/**
	 * a getter for the point matrix.
	 * @return the current point matrix
	 */
	public int[][] getMatrix () {
		return pointMatrix;
	}	

	/**
	 * this method is called when the user clicks the new game button.
	 * it starts a new game by returning all characters to their starting point, sets lives back to 3 and reverts the matrix.
	 */
	public void newGame() {
		for (Monster m : monsters) {
			m.goToStart();
		}
		pacman.setDirection(0);
		pacman.goToStart();
		returnToRegularPacMan();
		numberOfDots = INITIAL_NUMBER_OF_POINTS ;
		pacmanLives = PACMAN_LIVES;
		pointMatrix = cloneMatrix(INITIAL_MATRIX);
		timer.start();
		
	}
	
	/**
	 * A method for cloning a matrix.
	 * @param toClone the matrix to be cloned
	 * @return the clone.
	 */
	private int[][] cloneMatrix(int[][] toClone) {
		int[][] ans = new int[toClone.length][toClone[0].length];
		for (int i=0; i<toClone.length; i++) {
			for (int j=0; j<toClone[i].length; j++) {
				ans[i][j] = toClone[i][j];
			}
		}
		return ans;
	}
}
