package pacman;

import javax.swing.ImageIcon;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Monster' class represents a monster in the game. It contains each monsters start positions, and their speed.
 */
public abstract class Monster extends GameCharacter implements Visitor {
	/** The monsters' start positions, each time a monster is created it's given a differnet coordinate */
	private static final int[] START_PIXEL_X = {191,239,159,271};
	private static final int START_PIXEL_Y = 174;
	private static final int[] START_MATRIX_X = {11,14,9,16};
	private static final int START_MATRIX_y = 10;
	/** The index that determines which start position the monster will get, initialized to zero */
	private static int MONSTER_INDEX=0;
	/** The monsters' speed */
	private static final int SPEED = 1;
	
	/**
	 * A constructor for a new monster, receives the initial icon from the weak/strong monster.
	 * @param parent - The game panel
	 * @param icon - The initial icon
	 */
	public Monster(GamePanel parent, ImageIcon icon) {
		super(parent, START_PIXEL_X[MONSTER_INDEX], START_PIXEL_Y, START_MATRIX_X[MONSTER_INDEX], START_MATRIX_y, icon, 2, SPEED);
		/** The next monster will get the next position index */
		MONSTER_INDEX = (MONSTER_INDEX+1)%4;
	}

	
	/**
	 * 'beats' checks if the monster can defeat the pacman in his current state (regular, mighty or super)
	 * using the polymorphism
	 * @param pacman - The pacman the monster has encountered
	 * @return - True - if the monster can beat the pacman, False - otherwise
	 */
	public boolean beats(PacMan pacman){
		return pacman.accept(this);
	}
}
