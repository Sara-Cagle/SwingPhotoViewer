package bus.messages;

/**
 * Created by saracagle on 10/24/16.
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
