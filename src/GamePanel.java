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

public class GamePanel extends jPanel implements KeyListener, ActionListener {

	private Rectangle[] blocks = new Rectangle[1000];
	private Color[] Colors = new Color[1000];
	private Rectangle[] food = new Rectangle[6];
	private int numblocks = 3,numticks = 0, numfood = 0,points = 0,times = 0;
	private boolean tick = true, start = false;
	private int direction = 0;
	private javax.swing.Timer time = new javax.swing.Timer(10, this);
	private javax.swing.Timer timer = new javax.swing.Timer(400, this);
	private Top10 top = new Top10();
	private double starttime = System.currentTimeMillis();
	public JButton Restart = new JButton("Restart");

	public GamePanel() {
		setSize(1000,1000);
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.green));
		Restart.setBounds(900,900,100,30);
		add(Restart);
		Restart.addActionListener(this);
		Restart.setVisible(false);
		Random ran = new Random();
		for (int x = 0; x < 1000; x++) {
			blocks[x] = new Rectangle(-50,-50,50,50);
			Colors[x] = new Color(ran.nextInt(155) + 100,ran.nextInt(155) + 100,ran.nextInt(155) + 100);
		}
		for (int x = 0; x < 6; x++) {
			food[x] = new Rectangle(-50,-50,50,50);
		}
		blocks[0] = new Rectangle(100,100,50,50);
		blocks[1] = new Rectangle(50,100,50,50);
		blocks[2] = new Rectangle(0,100,50,50);
		
	}
	public void restart(){
		Random ran = new Random();
		for (int x = 0; x < 1000; x++) {
			blocks[x] = new Rectangle(-50,-50,50,50);
			Colors[x] = new Color(ran.nextInt(155) + 100,ran.nextInt(155) + 100,ran.nextInt(155) + 100);
		}
		for (int x = 0; x < 6; x++) {
			food[x] = new Rectangle(-50,-50,50,50);
		}
		blocks[0] = new Rectangle(100,100,50,50);
		blocks[1] = new Rectangle(50,100,50,50);
		blocks[2] = new Rectangle(0,100,50,50);
		direction = 0;
		start = false;
		tick = true;
		numblocks = 3;
		numticks = 0;
		numfood = 0;
		points = 0;
		times = 0;
		Restart.setVisible(false);
		repaint();
		eventFired();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		super.setBackground(Color.black);
		for (int x = 0; x < 1000; x++) {
			g.setColor(Colors[x]);
			g.fillRect((int)blocks[x].getX(),(int)blocks[x].getY(),(int)blocks[x].getWidth(),(int)blocks[x].getHeight());
			
			g.setColor(Color.black);
			g.drawRect((int)blocks[x].getX(),(int)blocks[x].getY(),(int)blocks[x].getWidth(),(int)blocks[x].getHeight());
		}
		g.setColor(Color.blue);
		g.fillOval((int)blocks[0].getX() + 15,(int)blocks[0].getY() + 15,20,20);
		g.setColor(Color.pink);
		for (int x = 0;x <6;x++){
			g.fillOval((int)food[x].getX(),(int)food[x].getY(),(int)food[x].getWidth(),(int)food[x].getHeight());
		}
		g.setColor(Color.cyan);
		g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 2.5F));
		g.drawString("Points: " + points, 800, 30);
		double time = System.currentTimeMillis();
		g.drawString("Time: " + (int)((time - starttime)/1000/60) +":"+(int)((time - starttime) % 60000 / 1000) + "." + ((time - starttime) % 1000),50,30);
		if (start == false){
			g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 1.5F));
			g.setColor(Color.RED);
			g.drawString("Press Right Arrow Key To Start! Good Luck",100,300);
			g.drawString("Press ESC Key To Exit",100,400);
		}
	}
	public void drawfood(){
		if (numticks >= 10 && numfood < 5){
			if (timer.getDelay() > 5){
				timer.setDelay(timer.getDelay() - 5);
			}
			Random ran = new Random();
			boolean flag = true;
			while(flag == true){
			flag = false;
			Rectangle temp = new Rectangle(ran.nextInt(900),ran.nextInt(900),50,50);
			temp = new Rectangle((int)(temp.getX() - temp.getX() % 50),(int)(temp.getY() - temp.getY() % 50),50,50);
			
			for(int x = 0; x < 1000;x++){
				if (temp.equals(blocks[x])){
					flag = true;
				}
			}
			for(int x = 0; x < 6;x++){
				if (temp.equals(food[x])){
					flag = true;
				}
			}
			if (flag == false){
				food[numfood] = temp;
				numfood++;
			}
			numticks = 0;
			}
		}
	}
	public void eatfood(){
		Random ran = new Random();
		for (int x = 0; x < 6;x ++){
			if (blocks[0].equals((food[x]))){
				numblocks++;
				for (int y = x; y < 5 ;y++){
					food[y] = food[y + 1];
				}
				numfood--;
				blocks[numblocks - 1].setBounds(blocks[numblocks-2]);
				points += ran.nextInt(1000);
			}
		}
		
	}
	public boolean collision(Rectangle r) {
		for (int x = 1; x < 1000 ; x++){
			if (r.contains(blocks[x])) {
				return true;
			}
		}
		if (blocks[0].getX() > 950 || blocks[0].getX() < 0 || blocks[0].getY() > 950 || blocks[0].getY() < 0 ) {
			return true;
		}
		return false;
	}
	public void move() {
		Rectangle[] temp = new Rectangle[1000];
		temp = (Rectangle[])blocks.clone();
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
		eatfood();
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
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			top.save();
			System.exit(0);
		}
		if (tick == true) {
		if (start != false) {
			if (e.getKeyCode() == 38 && direction != 2) {
					direction = 1;
					tick = false;
			} else if (e.getKeyCode() == 40 && direction != 1) {
				direction = 2;
				tick = false;
			} else if (e.getKeyCode() == 37 && direction != 4) {
				direction = 3;
				tick = false;
			} else if (e.getKeyCode() == 39 && direction != 3) {
				direction = 4;
				tick = false;
			}
		}
		}
		if (e.getKeyCode() == 39 && direction != 3 && start == false) {
					direction = 4;
					tick = false;
					start = true;
					starttime = System.currentTimeMillis();
					timer.start();
					time.start();
			}
		}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			move();
			drawfood();
			tick = true;
			numticks++;
		}
		if (e.getSource() == time) {
			times += 10;
			repaint();	
		}
		if (e.getSource() == Restart) {
			restart();
		}
	}
}
