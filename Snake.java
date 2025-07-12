//import packages for graphics
import java.awt.*;
import javax.swing.*;
//import event package
import java.awt.event.*;
//import logical packages
import java.lang.Math;
import java.util.ArrayList;
public class Snake extends JPanel implements KeyListener, ActionListener
{
	
	/* NOTE ON COORDINATE SYSTEM
	 *
	 * The coordinate system of this app will be in 20x20 chunks starting
	 * at y = 140 and x = 20. This is because each square of the snake or
	 * the apple is 20x20 pixels. The padding on each side is for borders,
	 * the title, or any other text and or not directly game related
	 * functions
	 *
	 */
	
   //create global variables
	ArrayList<Integer> x = new ArrayList<Integer>();
	ArrayList<Integer> y = new ArrayList<Integer>();
	int length = 1;
	
	int applex = (int)((35) * Math.random());
	int appley = (int)((30) * Math.random());
	
	boolean gameover = false;
	
	String direction = "";
	int score = 0;
	
	//create fonts
	Font titleFont = new Font("Monospaced", Font.BOLD, 100);
	Font scoreFont = new Font("Monospaced", Font.PLAIN, 25);
	Font selectFont = new Font("Monospaced", Font.ITALIC, 20);
	Font creditFont = new Font("Times New Roman", Font.PLAIN, 20);
	
	//create timer
	Timer time = new Timer(100, this);
	
	//__init__ Keyboard Listener
	public Snake() {
		//this allows for keyboard inputs to work
		addKeyListener(this);
		setFocusable(true);
	    requestFocusInWindow();
	   
	    //give intial values to x and y
	    x.add((int)((35) * Math.random()));
	    y.add((int)((30) * Math.random()));
	}
	
	//draw graphics
	public void paintComponent(Graphics g)
    {
   	 //set up new game
        super.paintComponent(g);
        setBackground(Color.BLACK);
       
        //while the game is not over (like a loop)
        if(!gameover) {
	         //draw title
	         g.setColor(Color.GREEN);
	         g.setFont(titleFont);
	         g.drawString("Snake", 210, 90);
	        
	         //draw score
	         g.setColor(Color.WHITE);
	         g.setFont(scoreFont);
	        
	         if (score < 10) {
	        	 g.drawString("Score: " + score, 600, 100);
	         } else {
	        	 g.drawString("Score: " + score, 550, 100);
	         }
	        
	         //draw copyright
	         g.setFont(creditFont);
	         g.drawString("Roshan Kareer © 2025", 550, 785);
	        
	         //draw borders
	         g.setColor(Color.GREEN);
	         g.fillRect(0, 120, 740, 20);
	         g.fillRect(0, 740, 740, 20);
	         g.fillRect(0, 140, 20, 600);
	         g.fillRect(720, 140, 20, 600);
	        
	         //draw snake
	         g.setColor(Color.WHITE);
	        
	         for (int i = x.size() - 1; i >= x.size() - length; i--) {
	        	 g.fillRect((x.get(i)*20) + 20, (y.get(i)*20) + 140, 20, 20);
	         }
	        
	         //draw apple
	         g.setColor(Color.RED);
	         g.fillRect((applex*20) + 20, (appley*20) + 140, 20, 20);
	        
	         //run time event to wait until next step
	         time.start();
	        
        }
       
        else // if game is over
        {
       	 //draw GameOver:
       	 g.setColor(Color.GREEN);
       	 g.setFont(titleFont);
       	 g.drawString("Game Over", 100, 350);
       	
       	 //draw score
       	 g.setFont(scoreFont);
       	 g.drawString("Score: " + score, 310, 400);

		 //draw reset button;
		 g.setFont(selectFont);
		 g.drawString("Press R to restart", 260, 430);
       	
       	 //draw copyright
		 g.setFont(creditFont);
       	 g.drawString("Roshan Kareer © 2025", 550, 785);
        }
    }
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 //check what the current direction is and move in that direction by adding a new square to that list
		 int currx = x.get(x.size() - 1);
		 int curry = y.get(y.size() - 1); //these two are the current heads
		
		 if (direction.equals("right")) {
			 x.add(currx + 1);
			 y.add(curry);
		 } else if (direction.equals("up")) {
			 y.add(curry - 1);
			 x.add(currx);
		 } else if (direction.equals("left")) {
			 x.add(currx - 1);
			 y.add(curry);
		 } else if (direction.equals("down")) {
			 y.add(curry + 1);
			 x.add(currx);
		 }
		
		 // check if there is a collision with the apple
		 if (applex == x.get(x.size() - 1) && appley == y.get(y.size() - 1)) {
			 score++; //add to the score
			
			 //respawn apple in a different location
			 applex = (int)((35) * Math.random());
			 appley = (int)((30) * Math.random());
			
			 //add to snake length
			 length++;
		 }
		
		 //collision detection with walls
		 if (x.get(x.size() - 1) < 0 || x.get(x.size() - 1) > 34 || y.get(y.size() - 1) < 0 || y.get(y.size() - 1) > 29) {
			 gameover = true;
		 }
		
		 //check for self collision
		for (int i = x.size() - 3; i >= x.size() - length; i--) {
       	 if(x.get(x.size() - 1) == x.get(i) && y.get(y.size() - 1) == y.get(i)) {
       		 gameover = true;
       	 }
        }
		 //reset the game board
		repaint();
	 }
	
	 @Override
	 public void keyPressed(KeyEvent e) {
		 if (!gameover) {
			// if the game isn't over, then whenever a key is pressed assign the correct direction based on the key pressed
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			 	direction = "up";// W or Up is up
		 	} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			 	direction = "left"; // A or left is left
		 	} else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			 	direction = "down"; // S or down is down
		 	} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			 	direction = "right"; // D or right is right
		 	}
		 } 
		 
		 else // if the game is over
		 {
			//add an option to reset
			if(e.getKeyCode() == KeyEvent.VK_R) {
				reset();
			}
		 }
	 }
	 
	//this method resets the game without having to restart the app
    private void reset() {
		gameover = false; //reset gameover varible
		
		//clear and redefine empty arrayLists
		x = new ArrayList<Integer>();
		y = new ArrayList<Integer>();

		//set the intial location of the snake to a random place
		x.add((int)((35) * Math.random()));
	    y.add((int)((30) * Math.random()));

		//reset length, score, and direction to starting varibles
		length = 1;
		score = 0; 
		direction = "";

		//redraw the game board
		repaint();
	}

	public static void main(String[] args) {
        Snake p = new Snake();
        JFrame f = new JFrame();
        f.setSize(755, 840);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.setVisible(true);
    }
   
    //unused keyEvent Methods
	 @Override public void keyTyped(KeyEvent e) {}
	 @Override public void keyReleased(KeyEvent e) {}
}
