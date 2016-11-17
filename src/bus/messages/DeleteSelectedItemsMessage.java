package bus.messages;

import components.LineStroke;
import components.TextBox;

import java.util.ArrayList;

/**
 * DeleteSelectedItemsMessage
 *
 * To let the PhotoComponent know Glass Pane is deleting things.
 *
 * @Author Sara Cagle
 * @Date 11/16/2016
 */
public class DeleteSelectedItemsMessage extends Message{
    public ArrayList<TextBox> selectedBoxes;
    public ArrayList<LineStroke> selectedLines;

        /**
         * DeleteSelectedItemsMessage constructor
         *
         * @param selectedBoxes, collection of selected text boxes
         * @param selectedLines, collection of selected lines
         */
        public DeleteSelectedItemsMessage(ArrayList<TextBox> selectedBoxes, ArrayList<LineStroke> selectedLines){
            this.selectedBoxes = selectedBoxes;
            this.selectedLines = selectedLines;
        }

    /**
     * type
     *
     * @return the type of the message, "delete_selected_items_message"
     */
    public String type() {
            return "delete_selected_items_message";
        }
}
