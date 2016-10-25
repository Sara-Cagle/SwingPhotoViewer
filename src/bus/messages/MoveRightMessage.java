package bus.messages;

/**
 * MoveRightMessage
 *
 * A message to indicate to the system we're moving "right" or forward in the images.
 *
 * @Author Sara Cagle
 * @Date 10/24/2016.
 */
public class MoveRightMessage extends Message {

    /**
     * type
     *
     * @return the type of the message, "move_right_message"
     */
    public String type() {
        return "move_right_message";
    }
}
