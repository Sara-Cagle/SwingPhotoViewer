package bus.messages;

/**
 * MagnetOnMessage
 *
 * Message indicating the magnet has been turned on.
 *
 * @Author Sara Cagle
 * @Date 11/27/2016
 */
public class MagnetOnMessage extends Message{

    /**
     * type
     *
     * The type of the message passed.
     *
     * @return the type of message, magnet_on_message
     */
    public String type() {
        return "magnet_on_message";
    }

}
