// filler code for pong provided by Mr. David
package loops;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class extra {
	 // variables for freeze extra feature icon
	    public boolean freezeAppear = false;
	    public int freezeX = 300;
	    public int freezeY = 300;
	    public int radiusF = 50;

	  // speed for freeze button's position and radius change
	    public int freezeXV = 0;
	    public int freezeYV = 0;
	    public int radiusFV = 0;

	   // boolean for freeze icon to stop
	    public boolean freezeStopped = false;
	    
	   // boolean for freeze icon being used
	    public boolean use = false;
	    public boolean finish = false;
}

public class Pong extends JPanel implements KeyListener {
	
	// freeze extra feature variable
	extra f1 = new extra();
	extra f2 = new extra();
	
	// pretty color
	private static final Color LIGHT_PURPLE = new Color(204,153,255);
	private static final Color LIGHT_BLUE = new Color(137,207,240);
	
	// constants that are predefined and won't change as the program runs
	private final int WIDTH = 600, HEIGHT = 600, WINDOW_HEIGHT = 650;
	private final int PADDLE_WIDTH = 20, DIAM = 8, PADDLE_HEIGHT = 100;
	private final int PADDLE_SPEED = 4;

	
	// your instance variables here, I've given you a few 
	private boolean up1, down1, up2, down2; 		// booleans to keep track of paddle movement
	private boolean solo = false;
	
	// ball x, y position and velocity
	private int ballx = WIDTH/2;
	private int bally = HEIGHT/20;
	private int ballvx = 4;
	private int ballvy = 3;
	
	// slider left and right y position and velocity
	private int sliderLy = HEIGHT/2;
	private int sliderRy = HEIGHT/2;
	private int sliderLvy = 0;
	private int sliderRvy = 0;
	
	// left, right score
	private int scoreL = 0;
	private int scoreR = 0;
	
	// these stores the speed of the ball so it can move in its old speed after FREEZE ends
	private int ballIntermediateX = 0;
	private int ballIntermediateY = 0;

	// this method moves the ball at each timestep
	public void move_ball() {
		// ball movement
		ballx += ballvx;
		bally += ballvy;
		// your code here //
	}
	
	//this method moves the extra feature FREEZE's icon
	public void move_freeze() {
		// freeze symbol movement
	    f1.freezeX = f1.freezeX + f1.freezeXV;
	    f1.freezeY = f1.freezeY + f1.freezeYV;
	    f1.radiusF = f1.radiusF + f1.radiusFV;

	    f2.freezeX = f2.freezeX + f2.freezeXV;
	    f2.freezeY = f2.freezeY + f2.freezeYV;
	    f2.radiusF = f2.radiusF + f2.radiusFV;
	    if (f1.freezeX <= 60 && f1.freezeY >= 540 && f1.radiusF <= 10 && !f1.freezeStopped) {
	        f1.freezeXV = 0;
	        f1.freezeYV = 0;
	        f1.radiusFV = 0;
	        ballvx = 4;
	        ballvy = 3;
	        f1.freezeStopped = true;
	    }
	    // freeze shape stop
	    if (f2.freezeX <= 540 && f2.freezeY >= 540 && f2.radiusF <= 10 && !f2.freezeStopped) {
	        f2.freezeXV = 0;
	        f2.freezeYV = 0;
	        f2.radiusFV = 0;
	        ballvx = 4;
	        ballvy = 3;
	        f2.freezeStopped = true;
	    }
	}
	
	// this method happens when the freeze function is used
	public void use_freeze() {
		if (f1.use == true || f2.use == true) {
			if (ballvx!=0&&ballvy!=0) {
				ballIntermediateX = ballvx;
				ballIntermediateY = ballvy;
			}
			ballvx = 0;
			ballvy = 0;
		}
		if (f1.finish == true || f2.finish == true) {
			ballvx = ballIntermediateX;
			ballvy = ballIntermediateY;
			if (f1.finish == true) f1.finish = false;
			if (f2.finish == true) f2.finish = false;
		}
	}
	
	// this method moves the paddles at each timestep
	public void move_paddles() {
		sliderRy += sliderRvy;
		sliderLy += sliderLvy;
		
		if (down2 == true) sliderRvy = 3;
		else if (up2 == true) sliderRvy = -3;
		else sliderRvy = 0;
		
		if (down1 == true) sliderLvy = 3;
		else if (up1 == true) sliderLvy = -3;
		else sliderLvy = 0;
		
		// your code here // 
	}
	
	// this method checks if there are any bounces to take care of,
	// and if the ball has reached a left/right wall it adds to 
	// the corresponding player's score
	public void check_collisions() {
		// ball bounce off walls;
		if ((ballx >= WIDTH-15)&&(bally >= sliderRy)&&(bally <= sliderRy+85)) {
			ballvx = -ballvx;
		}
		if ((ballx <= 15)&&(bally >= sliderLy)&&(bally <= sliderLy+85)) {
			ballvx = -ballvx;
		}
		if ((bally >= HEIGHT)||(bally <= 0)) {
			ballvy = -ballvy;
		}
		if ((ballx >= WIDTH)||(ballx <= 0)) {
			if (ballx >= WIDTH) scoreL++;
			if (ballx <= 0) scoreR++;
	        if (scoreL - scoreR == 4) {
	            // freeze
	            ballvx = 0;
	            ballvy = 0;
	            // change color (appear)
	            f1.freezeAppear = true;
	            // shape starts moving
	            f1.freezeXV = -6;
	            f1.freezeYV = 6;
	            f1.radiusFV = -1;
	            f1.freezeStopped = false;
	        }
	        // when point difference is 5, get freeze feature
	        else if (scoreR - scoreL == 4) {
	            // freeze
	            ballvx = 0;
	            ballvy = 0;
	            // change color (appear)
	            f2.freezeAppear = true;
	            // shape starts moving
	            f2.freezeXV = 6;
	            f2.freezeYV = 6;
	            f2.radiusFV = -1;
	            f2.freezeStopped = false;
	        }
			ballx = WIDTH/2;
			bally = HEIGHT/2;
		}

		// your code here
	}
	

	// defines what we want to happen anytime we draw the game
	// you simply need to fill in a few parameters here
	public void paint(Graphics g) {
		
		// background color is gray
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// draw your rectangles and circles here
		// .......
		g.setColor(Color.white);
		g.fillOval(ballx, bally, 20, 20);
		g.setColor(LIGHT_PURPLE);
		g.fillRect(0, sliderLy, 15, 85);
		g.fillRect(WIDTH-15, sliderRy, 15, 85);
		
		// draw extra feature FREEZE's icon
		if (f1.freezeAppear == true) {
			g.setColor(LIGHT_BLUE);
			g.fillOval(f1.freezeX, f1.freezeY, f1.radiusF, f1.radiusF);
		}
		if (f2.freezeAppear == true) {
			g.setColor(LIGHT_BLUE);
			g.fillOval(f2.freezeX, f2.freezeY, f2.radiusF, f2.radiusF);
		}
		
		// change score int into String
		String Lscore = String.valueOf(scoreL);
		String Rscore = String.valueOf(scoreR);
		
		// writes the score of the game - you just need to fill the scores in
		Font f = new Font("Arial", Font.BOLD, 14);
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("P1 Score: "+Lscore, WIDTH/5, 20);
		g.drawString("P2 Score: "+Rscore, WIDTH*3/5, 20);
	}

	// defines what we want to happen if a keyboard button has 
	// been pressed - you need to complete this
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// changes paddle direction based on what button is pressed
		if (keyCode == KeyEvent.VK_DOWN) {
			down2 = true;
		}
			// fill this in
		
		if (keyCode == KeyEvent.VK_UP) {
			up2 = true;
		}
			// fill this in

		if (e.getKeyChar() == 'w') {
			up1 = true;
		}
			// move paddle down
		
		if (e.getKeyChar() == 's'){
			down1 = true;
		}
		if (f1.freezeAppear == true && e.getKeyChar() == 'f') {
			f1.use = true;
			System.out.println("hehe");
		}
		if (f2.freezeAppear == true && e.getKeyChar() == 'l') {
			f2.use = true;
		}
	}

	// defines what we want to happen if a keyboard button
	// has been released - you need to complete this
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// stops paddle motion based on which button is released
		if (keyCode == KeyEvent.VK_UP) up2 = false;
			// fill this in

		if (keyCode == KeyEvent.VK_DOWN) down2 = false;
			// fill this in
  
		if(e.getKeyChar() == 'w') up1 = false;
			// fill this in
		
		if (e.getKeyChar() == 's') down1 = false;
			// fill this in
		if (f1.freezeAppear == true && e.getKeyChar() == 'f') {
			f1.use = false;
			f1.freezeAppear = false;
			f1.finish = true;
		}
		if (f2.freezeAppear == true && e.getKeyChar() == 'f') {
			f2.use = false;
			f2.freezeAppear = false;
			f2.finish = true;
		}
	}
	
	// restarts the game, including scores
	public void restart() {
		up1 = false;
		down1 = false;
		up2 = false;
		down2 = false;		// booleans to keep track of paddle movement
		solo = false;
		
		// ball x, y position and velocity
		ballx = WIDTH/2;
		bally = HEIGHT/20;
		ballvx = 4;
		ballvy = 3;
		
		// slider left and right y position and velocity
		sliderLy = HEIGHT/2;
		sliderRy = HEIGHT/2;
		sliderLvy = 0;
		sliderRvy = 0;
		
		// left, right score
		scoreL = 0;
		scoreR = 0;
		// your code here
	}

	//////////////////////////////////////
	//////////////////////////////////////
	
	// DON'T TOUCH THE BELOW CODE
	
	
	// this method runs the actual game.
	public void run() {

		// while(true) should rarely be used to avoid infinite loops, but here it is ok because
		// closing the graphics window will end the program
		while (true) {
	
			// draws the game
			repaint();
			
			// we move the ball, paddle, and check for collisions
			// every hundredth of a second
			check_collisions();
			move_ball();
			move_paddles();
			move_freeze();
			use_freeze();
			
			//rests for a hundredth of a second
			try {
				Thread.sleep(10);
			} catch (Exception ex) {}
		}
	}
	
	// very simple main method to get the game going
	public static void main(String[] args) {
		new Pong();
	}
 
	// does complicated stuff to initialize the graphics and key listeners
	// DO NOT CHANGE THIS CODE UNLESS YOU ARE EXPERIENCED WITH JAVA
	// GRAPHICS!
	public Pong() {
		JFrame frame = new JFrame();
		JButton button = new JButton("restart");
		frame.setSize(WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.add(button, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart();
				Pong.this.requestFocus();
			}
		});
		this.addKeyListener(this);
		this.setFocusable(true);
		
		run();
	}
	
	// method does nothing
	public void keyTyped(KeyEvent e) {}
}