import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

	private ActionClass actionHolder = new ActionClass(); // To fire events later

	private Rectangle[] blocks = new Rectangle[1000]; // Rectangles to hold the snake
	private Color[] Colors = new Color[1000]; // Colors for the rectangles above
	private Rectangle[] food = new Rectangle[6]; // Rectangle for 6 possible food spawns
	private int numblocks = 3, numticks = 0, numfood = 0, points = 0, times = 0;
	private boolean tick = true, start = false;
	private int direction = 0; // Holds direction for snake's head
	private javax.swing.Timer time = new javax.swing.Timer(10, this); // Repaint and timekeeping timer
	private javax.swing.Timer timer = new javax.swing.Timer(400, this); // Main timer for game - delay decreases
	private Top10 top = new Top10(); // Top10 to keep track of scores
	private double starttime = System.currentTimeMillis();
	public JButton Restart = new JButton("Restart");

	// Game panel creation and gets the game in ready state to play
	public GamePanel() {
		// Initializing JPanel (self)
		setSize(1000,1000);
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.green));

		// Initialize Restart button
		Restart.setBounds(900,900,100,30);
		add(Restart); // Add button to JPanel (self
		Restart.addActionListener(this); // Add self as action listen (later function for this)
		Restart.setVisible(false); // Invisible on start; only shows when restart is an option

		// Blocks of snake generation
		Random ran = new Random(); // Random for choosing color
		for (int x = 0; x < 1000; x++) {
			blocks[x] = new Rectangle(-50,-50,50,50); // Off-screen location
			Colors[x] = new Color(ran.nextInt(155) + 100,ran.nextInt(155) + 100,ran.nextInt(155) + 100);
		}

		// Food Generation
		for (int x = 0; x < 6; x++) {
			food[x] = new Rectangle(-50,-50,50,50);
		}

		// Changing the first 3 block positions to always start in the same position
		blocks[0] = new Rectangle(100,100,50,50);
		blocks[1] = new Rectangle(50,100,50,50);
		blocks[2] = new Rectangle(0,100,50,50);
	}

	// Sends the ActionListener through to the ActionClass
	public void addListener(ActionClassListener toAdd) {
		actionHolder.addListener(toAdd);
	}

	// Resets the game to original state (resets color)
	public void restart() {
		// Blocks of snake generation
		Random ran = new Random(); // Random for choosing color
		for (int x = 0; x < 1000; x++) {
			blocks[x] = new Rectangle(-50,-50,50,50);
			Colors[x] = new Color(ran.nextInt(155) + 100,ran.nextInt(155) + 100,ran.nextInt(155) + 100);
		}

		//Food generation
		for (int x = 0; x < 6; x++) {
			food[x] = new Rectangle(-50,-50,50,50); // Set off-screen
		}

		// Changing the first 3 blocks positions to always start in the same position
		blocks[0] = new Rectangle(100,100,50,50);
		blocks[1] = new Rectangle(50,100,50,50);
		blocks[2] = new Rectangle(0,100,50,50);

		// Resetting all values to their initial ones
		direction = 0;
		start = false;
		tick = true;
		numblocks = 3;
		numticks = 0;
		numfood = 0;
		points = 0;
		times = 0;

		// Finish restart
		Restart.setVisible(false); // Hide restart button
		repaint(); // Refresh the GUI
		actionHolder.eventFired(); // Fire event to give GameFrame focus
	}

	// Used to update the game layer
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call previous paint to update necessary graphics
		super.setBackground(Color.black); // Change background to start with a blank canvas

		// Color all the blocks
		for (int x = 0; x < 1000; x++) {
			g.setColor(Colors[x]); // Set the color of the block and then paint (next line)
			g.fillRect((int)blocks[x].getX(),(int)blocks[x].getY(),(int)blocks[x].getWidth(),(int)blocks[x].getHeight());
			
			g.setColor(Color.black); // Set the color to black to draw border and then paint (next line)
			g.drawRect((int)blocks[x].getX(),(int)blocks[x].getY(),(int)blocks[x].getWidth(),(int)blocks[x].getHeight());
		}

		// Draw a circle for the head of the snake
		g.setColor(Color.blue);
		g.fillOval((int)blocks[0].getX() + 15,(int)blocks[0].getY() + 15,20,20);

		// Draw all the food
		g.setColor(Color.pink);
		for (int x = 0;x <6;x++){
			g.fillOval((int)food[x].getX(),(int)food[x].getY(),(int)food[x].getWidth(),(int)food[x].getHeight());
		}

		// Draw the time and score
		g.setColor(Color.cyan);
		g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 2.5F));
		g.drawString("Points: " + points, 800, 30);
		double time = System.currentTimeMillis(); // Get the current time to calculate difference from start time
		g.drawString("Time: " + (int)((time - starttime)/1000/60) +":"+(int)((time - starttime) % 60000 / 1000) + "." + ((time - starttime) % 1000),50,30);

		// If the game hasn't started then also draw the instructions
		if (start == false){
			g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 1.5F));
			g.setColor(Color.RED);
			g.drawString("Press Right Arrow Key To Start! Good Luck",100,300);
			g.drawString("Press ESC Key To Exit",100,400);
		}
	}

	// Updates food position (and checks to spawn one)
	public void drawfood() {
		// If the numticks is greater than 9 (~4s at start) and there is not maximum amount of food (6)
		if (numticks >= 10 && numfood < 5){
			// Reduce the timer; this is to increase the speed of the game every 10 "ticks" (and food being spawnable)
			if (timer.getDelay() > 5){
				timer.setDelay(timer.getDelay() - 5);
			}

			// While loop to create a valid food item
			Random ran = new Random();
			boolean flag = true;
			while(flag == true) {
				flag = false;

				// Create the random position
				Rectangle temp = new Rectangle(ran.nextInt(900),ran.nextInt(900),50,50);
				// Remake the position to line up to increments of 50 (one square size)
				temp = new Rectangle((int)(temp.getX() - temp.getX() % 50),(int)(temp.getY() - temp.getY() % 50),50,50);

				// Check for collisions; don't want to spawn an object on-top of another
				// Snake block collision
				for(int x = 0; x < 1000;x++){
					if (temp.equals(blocks[x])){
						flag = true;
					}
				}
				// Food collision
				for(int x = 0; x < 6;x++){
					if (temp.equals(food[x])){
						flag = true;
					}
				}

				// Validate that it did not collide
				if (flag == false){
					// Add new food item rectangle (to be drawn later)
					food[numfood] = temp;
					numfood++;
				}
				numticks = 0; // Reset numticks
			}
		}
	}

	// Eats food, adds points, and rearranges array
	public void eatfood() {
		Random ran = new Random();
		// Check which food block was hit (by snake head)
		for (int x = 0; x < 6;x ++) {
			// If it was hit then add to numblocks and shift array
			if (blocks[0].equals(food[x])) {
				numblocks++;
				// Shift every position past collision 1 forward (empty slot in the back)
				for (int y = x; y < 5; y++) {
					food[y] = food[y + 1];
				}
				numfood--; // Reduce food number to spawn more later
				blocks[numblocks - 1].setBounds(blocks[numblocks - 2]); // Set the spawned block's position equal to the previous
				points += ran.nextInt(1000); // Add to points by random amount
			}
		}
	}

	// Test for boundary of window collision
	public boolean collision(Rectangle r) {
		// Check r collision against all blocks (except first)
		for (int x = 1; x < 1000 ; x++) {
			if (r.contains(blocks[x])) {
				return true;
			}
		}

		// Check if first block (snake head) is out of bounds
		if (blocks[0].getX() > 950 || blocks[0].getX() < 0 || blocks[0].getY() > 950 || blocks[0].getY() < 0 ) {
			return true;
		}

		return false; // If no collisions detected return false
	}

	// Moves all active blocks
	public void move() {
		// Temp array declaration
		Rectangle[] temp = new Rectangle[1000];
		temp = (Rectangle[])blocks.clone();

		// Moving the head of the Snake
		if (direction == 1) {//up
			for (int x = 1; x < numblocks; x++) {
				blocks[x] = (Rectangle) temp[x-1].clone();
			}
			blocks[0].setLocation((int)blocks[0].getX(), (int)blocks[0].getY() - 50);
		} else if (direction == 2) {//down
			for (int x = 1; x < numblocks; x++) {
				blocks[x] = (Rectangle) temp[x-1].clone();
			}
			blocks[0].setLocation((int)blocks[0].getX(), (int)blocks[0].getY() + 50);
		} else if (direction == 3) {//left
			for (int x = 1; x < numblocks; x++) {
				blocks[x] = (Rectangle) temp[x-1].clone();
			}
			blocks[0].setLocation((int)blocks[0].getX() - 50, (int)blocks[0].getY());
		} else if (direction == 4) {//right
			for (int x = 1; x < numblocks; x++) {
				blocks[x] = (Rectangle) temp[x-1].clone();
			}
			blocks[0].setLocation((int)blocks[0].getX() + 50, (int)blocks[0].getY());
		}

		// Call eatfood to check for head collision with food
		eatfood();

		// Check for if the head illegally collided using collision function
		if (collision(blocks[0])){
			timer.stop();
			time.stop();
			JOptionPane.showMessageDialog(null,"Game Over\nYour Points Were: " + points);
			String name = JOptionPane.showInputDialog("Enter Name: ");
			top.add(name, points);
			top.sort();
			JOptionPane.showMessageDialog(null,top.show());
			Restart.setVisible(true);
		}
	}

	// Responds to KeyPress events
	@Override
	public void keyPressed(KeyEvent e) {
		// Escape key was pressed
		if (e.getKeyCode() == 27) {
			top.save();
			System.exit(0);
		}
		// Tick is to only keep one input per tick (reset on a timer tick)
		if (tick == true) {
			if (start != false) {
				// Up key
				if (e.getKeyCode() == 38 && direction != 2) {
					direction = 1;
					tick = false;
				// Down key
				} else if (e.getKeyCode() == 40 && direction != 1) {
					direction = 2;
					tick = false;
				// Left key
				} else if (e.getKeyCode() == 37 && direction != 4) {
					direction = 3;
					tick = false;
				// Right key
				} else if (e.getKeyCode() == 39 && direction != 3) {
					direction = 4;
					tick = false;
				}
			}
		}
		// Right key, used to start game
		if (e.getKeyCode() == 39 && direction != 3 && start == false) {
			direction = 4;
			tick = false;
			start = true;
			starttime = System.currentTimeMillis();
			timer.start();
			time.start();
		}
	}

	// Obligatory for KeyListener
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	// Obligatory for KeyListener
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	// Responds to ActionEvents (timer and button clicking)
	@Override
	public void actionPerformed(ActionEvent e) {
		// If source of action was timer then move forward and then draw food
		if (e.getSource() == timer) {
			move();
			drawfood();
			tick = true;
			numticks++;
		}
		// If source of action was the GUI timer then repaint GUI and add to times
		if (e.getSource() == time) {
			times += 10;
			repaint();	
		}
		// If source was restart button then call restart
		if (e.getSource() == Restart) {
			restart();
		}
	}
}
