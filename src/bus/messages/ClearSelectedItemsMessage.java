package bus.messages;

/**
 * ClearSelectedItemsMessage
 *
 * To let the glass pane know it should clear its selections.
 *
 * @Author Sara Cagle
 * @Date 11/16/2016
 */
public class ClearSelectedItemsMessage extends Message{

    /**
     * type
     *
     * @return the type of the message, "clear_selected_items_message"
     */
    public String type() {
        return "clear_selected_items_message";
    }
}
