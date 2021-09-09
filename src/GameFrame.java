import java.awt.Color;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements ActionClassListener {

	private GamePanel Game = new GamePanel(); // GamePanel holds all functions to run the game

	public GameFrame() {
		// Initialize JFrame
		setLocation(0,0);
		setSize(1000,1000);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBackground(Color.black);

		// Add GamePanel to JFrame (self)
		add(Game);

		// Add Event Listeners
		Game.addListener(this);
		addKeyListener((KeyListener)Game);

		// Show window (JFrame)
		setVisible(true);
	}

	// Implements ActionClassListener to return focus to the GameFrame after a popup/restart
	@Override
	public void EventFired() {
		requestFocus();
	}
	
}
