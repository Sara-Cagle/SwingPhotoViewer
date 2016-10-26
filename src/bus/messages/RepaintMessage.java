package bus.messages;

/**
 * RepaintMessage
 *
 * Message that is sent when we should trigger a repaint.
 *
 * @Author Sara Cagle
 * @Date 10/26/2016
 */
public class RepaintMessage extends Message{

    /**
     * type
     *
     * @return the type of the message, "repaint_message"
     */
    public String type() {
        return "repaint_message";
    }
}
