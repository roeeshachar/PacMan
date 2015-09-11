package pacman;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Visitor' interface allows us to define the relations between pacman and the monsters.
 * Each time a monster encounters a pacman, it checks if it can defeat him.
 * Since pacman has different instances, and each affects the monsters differently,
 * each monster (weak or strong) has a specific implementation indicating if the monster can
 * beat pacman in his current state or not.
 */
public interface Visitor {
	/**
	 * A method that states that the a monster beats the regular pacman
	 * @param rp - The regular pacman encountered
	 */
	boolean visit(RegularPacMan rp);
	/**
	 * A method that defines the relations between a type of monster and a mighty pacman 
	 * @param mp - The misghty pacman encountered
	 */
	boolean visit(MightyPacMan mp);
	/**
	 * A method that defines the relations between a type of monster and a super pacman 
	 * @param sp - The super pacman encountered
	 */
	boolean visit(SuperPacMan sp);
}
