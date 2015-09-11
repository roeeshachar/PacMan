package pacman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * @author Tom Leibovich and Roee Shachar
 * Main is the frame the whole program runs in.
 * it contains two panels organized in box layout.
 * the upper panel is the menu panel. it contains the pacman lives left and the new game button.
 * the game panel is where the game is played.
 */
public class Main extends JFrame {

	/** serial ID*/
	private static final long serialVersionUID = -5947410653348215542L;
	/** the pane which sorts the game and menu panels */
	private JPanel contentPane;
	/** the game panel */
	private GamePanel gamePanel;
	/** the menu panel */
	private MenuPanel menuPanel;
	
	
	/**
	 * The main function launches the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * The constructor creates the frame.
	 * It adds and initialize the two panels.
	 */
	public Main() {
		super("PacMan");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 455, 675);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		menuPanel = new MenuPanel(this);
		menuPanel.setBounds(0, 0, 549, 150);
		contentPane.add(menuPanel);
		gamePanel = new GamePanel(this);
		gamePanel.setBounds(0, 150, 549, 700);
		contentPane.add(gamePanel);
		setContentPane(contentPane);
		
	}
	
	
	/**
	 * newGame is the method called when the new game button is clicked
	 * it calls the newGame method of the gamePanel object.
	 */
	public void newGame() {
		gamePanel.newGame();
		
	}
	
	/**
	 * This method is called whenever pacman is beaten by a monster.
	 * It calls the deduceLife method in the menu panel which removes one heart from the panel.
	 */
	public void loseLife() {
		menuPanel.deduceLife();
	}

}
