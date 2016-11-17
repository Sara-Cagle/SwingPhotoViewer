package bus.messages;

/**
 * DeleteImageMessage
 *
 * Message that indicates an image has been deleted.
 *
 * @Author Sara Cagle
 * @Date 9/30/2016.
 */
public class DeleteImageMessage extends Message{
    /**
     * type
     *
     * @return the type of the message, "delete_image_message"
     */
    public String type() {
        return "delete_image_message";
    }
}
