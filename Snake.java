//import packages for graphics
import java.awt.*;
import javax.swing.*;
import snakeTools.*; /* my package */

//import event package
import java.awt.event.*;

//import logical packages
import java.lang.Math;
import java.util.ArrayList;

public class Snake extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener 
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
	int tickspeed = 100; //controls tickspeed
	int tickspeedIncreaseCounter = 0;

	ArrayList<Integer> x = new ArrayList<Integer>();
	ArrayList<Integer> y = new ArrayList<Integer>();
	
	int length = 1;
	
	int applex = (int)((35) * Math.random());
	int appley = (int)((30) * Math.random());

	boolean gameover = false;
	boolean gameNotStarted = true; 
	
	String direction = "";
	int score = 0;
	String difficulty = "";

	//declare start and reset button
	ActionButton resetButton = new ActionButton(300, 430, 140, 60, "Reset", () -> {reset();});
	ActionButton startButton = new ActionButton(300, 520, 140, 60, "Start", () -> {start();});

	//declare difficulty selectors
	SelectionButton easyButton = new SelectionButton(140, 400, 140, 60, "Easy"); 
	SelectionButton normalButton = new SelectionButton(300, 400, 140, 60, "Normal"); 
	SelectionButton hardButton = new SelectionButton(460, 400, 140, 60, "Hard"); 
	ButtonSet diffSet = new ButtonSet(null);

	//create fonts
	Font titleFont = new Font("Monospaced", Font.BOLD, 100);
	Font scoreFont = new Font("Monospaced", Font.PLAIN, 25);
	Font selectFont = new Font("Monospaced", Font.ITALIC, 20);
	Font creditFont = new Font("Times New Roman", Font.PLAIN, 20);
	
	//create timer
	Timer time = new Timer(tickspeed, this);
	
	//__init__ Keyboard Listener
	public Snake() {
		//this allows for keyboard inputs to work
		addKeyListener(this);
		setFocusable(true);
	    requestFocusInWindow();

		//mouse inputs
		addMouseListener(this);
		addMouseMotionListener(this);
	   
	    //give initial values to x and y
	    x.add((int)((35) * Math.random()));
	    y.add((int)((30) * Math.random()));

		// add difficulty buttons to set
		diffSet.add(easyButton);
		diffSet.add(normalButton);
		diffSet.add(hardButton);
		normalButton.isSelected = true;
	}
	
	public void start() {
		//allow standard gamboard to be drawn
		gameNotStarted = false; 
		
		//assign correct tickspeed
		if (easyButton.checkSelected()) {
			tickspeed = 160;
			difficulty = "easy";
		} else if (normalButton.checkSelected()) {
			difficulty = "normal";
		} else {
			difficulty = "hard";
		}

		//set time speed to new difficulty based speed
		time.setDelay(tickspeed);

		//redraw game board
		repaint(); 
	}

	/** draws the start screen */
	private void drawStartScreen(Graphics g) {
		//draw Name:
		g.setColor(Color.GREEN);
		g.setFont(titleFont);
		g.drawString("Snake", 220, 350);

		//draw buttons;
		startButton.draw(g, selectFont, Color.GREEN, 3);
		easyButton.draw(g, selectFont, Color.GREEN, 3);
		normalButton.draw(g, selectFont, Color.GREEN, 3);
		hardButton.draw(g, selectFont, Color.GREEN, 3);
	
		//draw copyright
		g.setColor(Color.GREEN);
		g.setFont(creditFont);
		g.drawString("Roshan Kareer © 2025, All rights reserved", 390, 785);

		//start timer
		time.start(); 
	}

	/** Draws the gameboard while the game is still running**/
	private void drawGameScreen(Graphics g) {
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
		g.drawString("Roshan Kareer © 2025, All rights reserved", 390, 785);
	
		//draw borders
		g.setColor(Color.GREEN);
		g.fillRect(0, 120, 740, 20);
		g.fillRect(0, 740, 740, 20);
		g.fillRect(0, 140, 20, 600);
		g.fillRect(720, 140, 20, 600);
	
		//draw apple
		g.setColor(Color.RED);
		g.fillRect((applex*20) + 20, (appley*20) + 140, 20, 20);

		//draw snake
		g.setColor(Color.WHITE);

		for (int i = x.size() - 1; i >= x.size() - length; i--) {
			g.fillRect((x.get(i)*20) + 20, (y.get(i)*20) + 140, 20, 20);
		}
	
		//run time event to wait until next step
		time.start();
	}

	/** Draws the loss screen */
	private void drawGameOverScreen(Graphics g) {
		//draw GameOver:
		g.setColor(Color.GREEN);
		g.setFont(titleFont);
		g.drawString("Game Over", 100, 350);
	
		//draw score
		g.setFont(scoreFont);
		g.drawString("Score: " + score, 310, 400);

		//draw reset button;
		resetButton.draw(g, selectFont, Color.GREEN, 3); 
	
		//draw copyright
		g.setFont(creditFont);
		g.drawString("Roshan Kareer © 2025, All rights reserved", 390, 785);
	}
	
	//draw graphics
	public void paintComponent(Graphics g)
    {
   	 	//set up new gameboard 
        super.paintComponent(g); //clear
        setBackground(Color.BLACK); //set bg to black
		
        // runs while the game is not over (like a loop)
		if (!gameover && gameNotStarted) {
			drawStartScreen(g); 
		} else if (!gameover) {
			drawGameScreen(g);
		} else {
			drawGameOverScreen(g);
		}
    }

	/** creates a new list of all the squares of the snake each tick to minimize memory usage  */
	private void newSnakeList() {
		// add new position node to old list
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

		//create temporary lists 
		ArrayList<Integer> tempx = new ArrayList<Integer>();
		ArrayList<Integer> tempy = new ArrayList<Integer>();

		//we then add the old positions to the new list via iteration. 
		for (int i = x.size() - length; i <= x.size() - 1; i++) { // We count up as we want the front of our list to be the front of the snake
			tempx.add(x.get(i));
			tempy.add(y.get(i));
        }

		//finally, we just make the old lists the temp lists
		x = tempx; 
		y = tempy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// check if there is a collision with the apple
		if (applex == x.get(x.size() - 1) && appley == y.get(y.size() - 1)) {
			score++; //add to the score
			setAppleRandomCoords(); //respawn apple in a different location
			length++; //add to snake length
		}

		//create new snake
		newSnakeList();

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

		// decrement tickspeed 
		tickspeedIncreaseCounter++;
		if (difficulty.equals("hard") && tickspeed >= 75) {
			tickspeed--; 
			time.setDelay(tickspeed);
			tickspeedIncreaseCounter = 0;
		} else if (difficulty.equals("hard") && tickspeed >= 60 && tickspeedIncreaseCounter == 4) {
			tickspeed--; 
			time.setDelay(tickspeed);
			tickspeedIncreaseCounter = 0;
		} else if (difficulty.equals("hard") && tickspeed >= 40 && tickspeedIncreaseCounter == 9) {
			tickspeed--; 
			time.setDelay(tickspeed);
			tickspeedIncreaseCounter = 0;
		}

		if (!gameover) {
			repaint();
		} else {
			time.stop();
			repaint();
		}
	 }
	
	 /** spawns the apple at a random point NOT within the snake **/
	private void setAppleRandomCoords() {
		boolean insideSnake; //this variable will be used to end the loop if the apple is not in the snake (no issue) and continue it if it is.
		do {
			insideSnake = false; //resets the insideSnake variable. 

			// sets the apple's spawning point to a random coordinate
			applex = (int)((35) * Math.random());
			appley = (int)((30) * Math.random());

			//check if its inside the snake by iterating through all of it's coordinates
			for (int i = x.size() - 1; i >= x.size() - length; i--) {
				if(applex == x.get(i) && appley == y.get(i)) {
					insideSnake = true;
				}
			}

		} while(insideSnake); // if the spawning point is in the snake then repeat the loop until it isn't
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!gameover) {
			// if the game isn't over, then whenever a key is pressed assign the correct direction based on the key pressed
			// the && direction part makes it so you don't instantly kill yourself if you press the button in the opposite direction of movement. 
			// () are needed around the different key options as &&nds execute before ||rs.
			if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && direction != "down") { 
				direction = "up";
		 	} else if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT ) && direction != "right") {
			 	direction = "left"; // A or left is left
		 	} else if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && direction != "up") {
			 	direction = "down"; // S or down is down
		 	} else if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && direction != "left") {
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
	 
	/** this method resets the game without having to restart the app */
    private void reset() {
		gameover = false; //reset gameover variable
		
		//clear and redefine empty arrayLists
		x = new ArrayList<Integer>();
		y = new ArrayList<Integer>();

		//set the initial location of the snake to a random place
		x.add((int)((35) * Math.random()));
	    y.add((int)((30) * Math.random()));

		//reset length, score, and direction to starting variables
		length = 1;
		score = 0; 
		direction = "";
		tickspeed = 100; 

		//set gameNotStarted to true
		gameNotStarted = true; 

		//redraw the game board
		repaint();
	}

	public static void main(String[] args) {
        Snake p = new Snake();
        JFrame f = new JFrame("Snake");
        f.setSize(755, 840);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.setVisible(true);
    }
   
	@Override 
	public void mousePressed(MouseEvent e) {
		if (gameNotStarted) {
			diffSet.smartSelect(e); 
			startButton.smartClick(e);
		}
		
		if (gameover) {
			//check if the reset button was pressed
			resetButton.smartClick(e);
		}

		//redraw the game board (needed for button to update)
		repaint(); 
	}

	@Override 
	public void mouseMoved(MouseEvent e) {
		//automatically set button hovering states
		if (gameNotStarted) {
			for (SelectionButton b : diffSet.buttons) {
				b.smartSetHovering(e);
			}

			startButton.smartSetHovering(e);
		}

		if (gameover) {
			resetButton.smartSetHovering(e);
		}

		// redraw the gameboard so hovering animation can commence
		repaint();
	}

    //unused keyEvent Methods
	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}

	//unused MouseEvent Methods
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

	 //unused MouseMotionEvent Methods
	@Override public void mouseDragged(MouseEvent e) {}
}
