package bus.messages;

import components.LineStroke;
import components.TextBox;

import java.util.ArrayList;

/**
 * Created by saracagle on 11/16/16.
 */
public class HasSelectedItemsMessage extends Message{

    public ArrayList<TextBox> selectedBoxes;
    public ArrayList<LineStroke> selectedLines;

    /**
     * HasSelectedItemsMessage constructor
     *
     * @param selectedBoxes, collection of selected text boxes
     * @param selectedLines, collection of selected lines
     */
    public HasSelectedItemsMessage(ArrayList<TextBox> selectedBoxes, ArrayList<LineStroke> selectedLines){
        this.selectedBoxes = selectedBoxes;
        this.selectedLines = selectedLines;
    }

    /**
     * type
     *
     * @return the type of the message, "has_selected_items_message"
     */
    public String type() {
        return "has_selected_items_message";
    }
}
