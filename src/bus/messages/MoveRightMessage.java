package bus.messages;

/**
 * Created by saracagle on 10/24/16.
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
