package bus.messages;

/**
 * ClearTagPanelMessage
 *
 * Message to indicate clear all tags from the tag panel.
 * Used when bringing in a new photo.
 *
 * @Author Sara Cagle
 * @Date 11/13/16
 */
public class ClearTagPanelMessage extends Message {

    /**
     * type
     *
     * @return the type of the message, "clear_tag_message"
     */
    public String type() {
        return "clear_tag_message";
    }

}
