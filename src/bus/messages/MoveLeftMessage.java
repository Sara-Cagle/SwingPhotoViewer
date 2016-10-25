package bus.messages;

/**
 * MoveLeftMessage
 *
 * A message to indicate to the system we're moving "left" or backward in the images.
 *
 * @Author Sara Cagle
 * @Date 10/24/2016.
 */
public class MoveLeftMessage extends Message {

    /**
     * type
     *
     * @return the type of the message, "move_left_message"
     */
    public String type() {
        return "move_left_message";
    }
}
