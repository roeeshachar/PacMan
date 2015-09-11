package pacman;

/**
 * @author Tom Leibovich and Roee Shachar
 * 'Visited' interface allows us to define the relations between pacman and the monsters in run-time.
 * Each time a monster encounters a pacman, it checks if it can defeat him, by calling to this method.
 * Thus, in run-time using the polymorphism it will go to the specific method in the specific pacman,
 * Allowing us to get the pacman's type in run-time.
 */
public interface Visited {
	/**
	 * 'accept' method is used to determine the relations between the various pacmans and monsters using
	 * the polymorphism in run-time. 
	 * @param v - A visitor, monster
	 */
	boolean accept(Visitor v);
}
