import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartFrame extends JFrame implements ActionListener{

	private JPanel BackgroundPanel = new JPanel(); // Used to display items in frame
	private JButton Start = new JButton("Start"); // Start button to start game

	public StartFrame(){
		// Initializes JFrame (self)
		setLocation(0,0);
		setSize(1000,1000);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBackground(Color.black);

		// Initialize title label
		JLabel title = new JLabel("SNAKE");
		title.setFont(new Font("AR DESTINE", Font.PLAIN,120));
		title.setBorder(BorderFactory.createBevelBorder(0, Color.blue, Color.orange));
		title.setForeground(Color.magenta);
		title.setBackground(Color.cyan);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setAlignmentY(CENTER_ALIGNMENT);
		title.setOpaque(true);

		// Add Action Listener to Start button
		Start.addActionListener(this);

		// Initialize JPanel (used to display the button/title)
		BackgroundPanel.setSize(1000,1000);
		BackgroundPanel.setLayout(null);
		BackgroundPanel.setBackground(Color.darkGray);

		// Add title and start button
		BackgroundPanel.add(title);
		BackgroundPanel.add(Start);

		// Manually set the bounds of the items after adding them; this is because layout is set to null
		title.setBounds(310, 50, 450, 120);
		Start.setBounds(450,480,100,40);
		
		// Add BackgroundPanel to the JFrame (self)
		add(BackgroundPanel);

		// Manually set the bound of BackgroundPanel; this is because layout is set to null
		BackgroundPanel.setBounds(0,0, 1000, 1000);

		// Show the window (JFrame)
		setVisible(true);
	}

	// Used to show the Game GUI
	public void start(){
		setVisible(false);
		GameFrame Game = new GameFrame();
	}

	// Action listener for Start button to start the game
	@Override
	public void actionPerformed(ActionEvent e) {
		// Check to make sure the event was the start button
		if (e.getSource() == Start) {
			start();
		}
	}
}
