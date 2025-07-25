package snakeTools;

//import packages
import java.awt.*;
import java.awt.event.MouseEvent;

/** A button that switches between a selected and unselected state when clicked */
public class SelectionButton {
    // declare global vars 
    public Rectangle bounds;;
    public String label;
    private boolean isHovering = false;
    public boolean isSelected = false; 

    //construct class and take parameters
    public SelectionButton(int x, int y, int width, int height, String label) {
        this.bounds = new Rectangle(x, y, width, height);
        this.label = label;
    }

    /** draw the button, must be in paintComponent() method or another with Graphics */
    public void draw(Graphics g, Font font, Color color, int BoxThickness) {
        //set up font
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        
        //check for selection and draw related object
        if (!isSelected) {
            // if its not selected draw box around button (uses multiple Rects to make thick outline)
            g.setColor(color); 
            g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height);
            g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), bounds.width, BoxThickness);
            g.fillRect(bounds.x - (BoxThickness/2), bounds.y + bounds.height - (BoxThickness/2), bounds.width + BoxThickness, BoxThickness); 
            g.fillRect(bounds.x + bounds.width - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height + BoxThickness);
        } else {
            //if it is selected draw a filled box
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            
            // set text color to black
            g.setColor(Color.BLACK);
        }

        //draw label
        int labelWidth = metrics.stringWidth(label);
        int labelHeight = metrics.getHeight(); 
        int labelX = bounds.x + (bounds.width - labelWidth)/2;
        int labelY = bounds.y + (bounds.height - labelHeight)/2 + metrics.getAscent();
        g.drawString(label, labelX, labelY);
        
        //draw hovering animation
        if (isHovering) {
            // this is the animation loop
            while (BoxThickness < 8) {

                BoxThickness += 2; //iterate length

                // redraw box outline with new size (no need to set color bc already set)
                g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height);
                g.fillRect(bounds.x - (BoxThickness/2), bounds.y - (BoxThickness/2), bounds.width + 1, BoxThickness);
                g.fillRect(bounds.x - (BoxThickness/2), bounds.y + bounds.height - (BoxThickness/2), bounds.width + BoxThickness, BoxThickness); 
                g.fillRect(bounds.x + bounds.width - (BoxThickness/2), bounds.y - (BoxThickness/2), BoxThickness, bounds.height + BoxThickness);

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

    public void smartSetHovering(MouseEvent e) {
        setHovering(bounds.contains(e.getPoint()));
    }

    public void setSelected(boolean selected) {
        isSelected = selected; 
    }

    public void invertSelected() {
        isSelected = !isSelected;
    }

    public void smartSetSelected(MouseEvent e, boolean selected) {
        if (bounds.contains(e.getPoint())) {isSelected = !isSelected;}
    }

    public void smartInvertSelected(MouseEvent e) {
        if (bounds.contains(e.getPoint())) {isSelected = !isSelected;}
    }

    public boolean checkSelected() {
        return isSelected; 
    }
}

