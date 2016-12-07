package bus.messages;

/**
 * MagnetOffMessage
 *
 * Message indicating the magnet has been turned off.
 *
 * @Author Sara Cagle
 * @Date 11/27/2016
 */
public class MagnetOffMessage extends Message{

    /**
     * type
     *
     * The type of the message passed.
     *
     * @return the type of message, magnet_off_message
     */
    public String type() {
        return "magnet_off_message";
    }
}
