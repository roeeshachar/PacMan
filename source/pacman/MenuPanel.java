package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * MenuPanel is the upper graphical object of the game.
 * It paints the static graphics (which don't change)  and the pacman lives which changes whenever a pacman is beaten. 
 *
 */
public class MenuPanel extends JPanel {	 
	/** serial ID */
	
	private static final long serialVersionUID = 2162176699244449855L;
	/** Fields containing all the ImageIcon objects that will show up on the panel. */
	private static final ImageIcon NEON_IMAGE = new ImageIcon("neon2.gif");
	private static final ImageIcon NEW_GAME_IMAGE = new ImageIcon("button.png");
	private static final ImageIcon HEART_IMAGE = new ImageIcon("heart.gif");
	private static final ImageIcon SPIDY_IMAGE = new ImageIcon("spidyRight.png");
	private static final ImageIcon HOMER_IMAGE = new ImageIcon("homerRight.png");
	private static final ImageIcon ALIEN_IMAGE = new ImageIcon("alienLeft.png");
	private static final ImageIcon VENOM_IMAGE = new ImageIcon("venomLeft.png");
	
	/** The Main object containing the panel. */
	private Main parent;
	
	/** Number of lives remaining */
	private int lives;
	
	/**
	 * The Constructor sets the lives to 3 initially and adds a listener for the new game button.
	 * @param parent
	 */
	public MenuPanel(Main parent) {
		this.parent = parent;
		lives = 3;
		/**	adding a mouse listener to simulate a button but not using JButton to keep our design of the panel */
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getX() > 175 && e.getX() < 263 && e.getY() > 70 && e.getY() < 104) {
					lives = 3;
					MenuPanel.this.parent.newGame();
				}
					
			}
		});
		
	}
	
	
	/**
	 *  The paint function is called automatically and paints the panel using DOUBLE BUFFERING.
	 *  It paints all the static images and paints the hearts by the number of lives.
	 */
	public void paint(Graphics g){
		
		// initializes an empty image to buffer on.
		Image offIm = createImage(getSize().width, getSize().height);
		Graphics offGr = offIm.getGraphics();
		offGr.setColor(Color.black);
		offGr.fillRect(0, 0, getWidth(), getHeight());
		
		// painting the headline gif.
		NEON_IMAGE.paintIcon(this, offGr, 125, 10);
		
		// painting the new game button.
		NEW_GAME_IMAGE.paintIcon(this, offGr, 175, 70);
		
		// painting the hearts by number of lives.
		for (int i=0; i<lives; i++) {
			HEART_IMAGE.paintIcon(this, offGr, 100*(i+1), 110);
		}
		
		// painting the monsters on the left
		SPIDY_IMAGE.paintIcon(this, offGr, 30 , 70);
		HOMER_IMAGE.paintIcon(this, offGr, 60 , 70);
		
		// painting the monsters on the right
		VENOM_IMAGE.paintIcon(this, offGr, 360 , 70);
		ALIEN_IMAGE.paintIcon(this, offGr, 390 , 70);
		
		// draws the image on the panel.
		g.drawImage(offIm, 0,0, this);

		
	}

	/**
	 * This method is called when pacman is beaten by a monster and deduces one life each time.
	 */
	public void deduceLife () {
		lives--;
		repaint();
	}
}
