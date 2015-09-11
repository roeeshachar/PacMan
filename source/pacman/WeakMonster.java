package pacman;

import javax.swing.ImageIcon;
/**
 * @author Tom Leibovich and Roee Shachar
 * 'Weak Monster' represents the weak monster in the game. The weak monsters move randomly in the game panel
 * and can only defeat the regular pacman.
 */
public class WeakMonster extends Monster {
	
	/**
	 * A constructor for a new weak monster
	 * @param parent - The game panel
	 * @param icons - The icons for the specific weak monster, for each movement (given by the game panel)
	 */
	public WeakMonster(GamePanel parent, ImageIcon[] icons) {
		super(parent, icons[1]); 
		iconUp = icons[0];
		iconRight = icons[1];
		iconDown = icons[2];
		iconLeft = icons[3];
	}

	
	/**
	 * overrides the move method in Game Character but still uses it, meaning if the last move
	 * was not accomplished successfully due to an obstacle, than a new random direction is given.
	 */
	public boolean move(){
		if(!super.move()) {
			int r = (int)(Math.random()*4+1);
			setDirection(r);
		}
		return true;
	}

	/**
	 * A method that states that the weak monster beats the regular pacman
	 * @param rp - The regular pacman encountered
	 */
	public boolean visit(RegularPacMan rp) {
		return true;
	}

	/**
	 * A method that states that the weak monster doesn't beat the mighty pacman
	 * @param mp - The mighty pacman encountered
	 */
	public boolean visit(MightyPacMan mp) {
		return false;
	}

	/**
	 * A method that states that the weak monster doesn't beat the super pacman
	 * @param sp - The super pacman encountered
	 */

	public boolean visit(SuperPacMan sp) {
		return false;
	}
	
}