package snakeTools;

//import packages
import java.awt.*;
import java.awt.event.MouseEvent;

/** A button that switches between a selected and unselected state when clicked */
public class SelectionButton {
    // declare global vars 
    public Rectangle bounds;
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
        
        if (!isSelected) {
            // compute outline thickness based on hover state (single pass; no loops)
            int boxThickness = isHovering ? Math.min(BoxThickness + 2, 8) : BoxThickness;

            // if its not selected draw box around button (uses multiple Rects to make thick outline)
            g.setColor(color); 
            g.fillRect(bounds.x - (boxThickness/2), bounds.y - (boxThickness/2), boxThickness, bounds.height);
            g.fillRect(bounds.x - (boxThickness/2), bounds.y - (boxThickness/2), bounds.width, boxThickness);
            g.fillRect(bounds.x - (boxThickness/2), bounds.y + bounds.height - (boxThickness/2), bounds.width + boxThickness, boxThickness); 
            g.fillRect(bounds.x + bounds.width - (boxThickness/2), bounds.y - (boxThickness/2), boxThickness, bounds.height + boxThickness);
        } else {
            //if it is selected draw a filled box
            g.setColor(color);
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
        if (bounds.contains(e.getPoint())) {isSelected = selected;}
    }

    public void smartInvertSelected(MouseEvent e) {
        if (bounds.contains(e.getPoint())) {isSelected = !isSelected;}
    }

    public boolean checkSelected() {
        return isSelected; 
    }
}

