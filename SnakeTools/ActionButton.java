package SnakeTools;

//import packages
import java.awt.*;

public class ActionButton {
    private Rectangle bounds;;
    private String label;
    private Runnable onClick;
    private boolean isHovering = false;
    private Color color;

    public ActionButton(int x, int y, int width, int height, String label, Color color, Runnable Onclick) {
        this.bounds = new Rectangle(x, y, width, height);
        this.label = label;
        this.onClick = onClick;
        this.color = color;
    }

    public void draw(Graphics g, Font f) {
        g.setColor(this.color); 
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
