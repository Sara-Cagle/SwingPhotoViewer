package bus.messages;

/**
 * Created by saracagle on 11/16/16.
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
