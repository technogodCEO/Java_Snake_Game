package snakeTools;

//import packages
import java.awt.*;

public class ActionButton {
    // declare global vars 
    private Rectangle bounds;;
    private String label;
    private Runnable onClick;
    private boolean isHovering = false;
    private Color color;

    //construct class and take parameters
    public ActionButton(int x, int y, int width, int height, String label, Runnable onClick) {
        this.bounds = new Rectangle(x, y, width, height);
        this.label = label;
        this.onClick = onClick;
        this.color = color;
    }

    /** draw the button, must be in paintComponent() method or another with Graphics */
    public void draw(Graphics g, Font font, Color color, int BoxThickness) {
        //set up font
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        
        //draw box around button (uses multiple Rects to make thick outline)
        g.setColor(color); 
        g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height);
        g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), bounds.width, BoxThickness);
        g.fillRect(bounds.x - (BoxThickness/2), bounds.y + bounds.height - (BoxThickness/2), bounds.width, BoxThickness); 
        g.fillRect(bounds.x + bounds.width - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height);

        //draw label
        int labelWidth = metrics.stringWidth(label);
        int labelHeight = metrics.getHeight(); 
        int labelX = bounds.x + (bounds.width - labelWidth)/2;
        int labelY = (bounds.height - labelHeight)/2;
        g.drawString(label, labelX, labelY);
        
        //draw hovering animation
        if (isHovering) {
            // this is the animation loop
            while (BoxThickness < 15) {

                BoxThickness += 2; //iterate length

                // redraw box outline with new size (no need to set color bc already set)
                g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height);
                g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), bounds.width, BoxThickness);
                g.fillRect(bounds.x - (BoxThickness/2), bounds.y + bounds.height - (BoxThickness/2), bounds.width, BoxThickness); 
                g.fillRect(bounds.x + bounds.width - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height);

                try {
                Thread.sleep(15); //this pauses for 15 milliseconds -> the try catch is just red tape mostly 
                // This function will pause all execution so if this button is used within game with a tick system ticks will not occur during the animation
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }            
        }
    }

    public boolean contains(Point p) {
        return bounds.contains(p);
    }

    public void setHovering(boolean hovering) {
        isHovering = hovering; 
    }

    public void click() {
        if (onClick != null) {onClick.run();}
    }
}

