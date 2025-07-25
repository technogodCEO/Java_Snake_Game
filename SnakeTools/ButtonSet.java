package snakeTools;

import java.awt.event.MouseEvent;
//import packages
import java.util.ArrayList;

public class ButtonSet {
    private ArrayList<SelectionButton> buttons = new ArrayList<SelectionButton>();
    
    public ButtonSet(ArrayList<SelectionButton> buttons) {
        this.buttons = (buttons != null) ? buttons : new ArrayList<>();
    }

    public void add(SelectionButton button) {
        buttons.add(button);
    }

    public void smartSelect(MouseEvent e) {
        //set all buttons to unclicked
        for (int i = 0; i <= buttons.size() - 1; i++) {
            buttons.get(i).setSelected(false);
        }

        // for each button in the set, check if it was pressed and set only it to selected (the checking is done in smartSetSelected)
        for (int i = 0; i <= buttons.size() - 1; i++){ 
            buttons.get(i).smartSetSelected(e, true);
        }

    }
}
