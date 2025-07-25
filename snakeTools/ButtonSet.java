package snakeTools;

import java.awt.event.MouseEvent;
//import packages
import java.util.ArrayList;

public class ButtonSet {
    public ArrayList<SelectionButton> buttons = new ArrayList<SelectionButton>();
    
    public ButtonSet(ArrayList<SelectionButton> buttons) {
        this.buttons = (buttons != null) ? buttons : new ArrayList<>();
    }

    public void add(SelectionButton button) {
        buttons.add(button);
    }

    public void smartSelect(MouseEvent e) {
        boolean buttonPressed = false; //checks if one of the buttons in the set was pressed
        SelectionButton pressedButton = null;

        for (SelectionButton b: buttons) {
            if (b.contains(e.getPoint())) {
                buttonPressed = true;
                pressedButton = b; 
            }
        }

        if (buttonPressed) { //only changes things if one of the buttons in the set was pressed
            //set all buttons to unclicked if one is 
            for (SelectionButton b: buttons) {
                b.setSelected(false);
            }

            pressedButton.setSelected(true);
        }

    }
}
