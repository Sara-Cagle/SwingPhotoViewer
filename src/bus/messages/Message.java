package bus.messages;

/**
 * Message
 *
 * An abstract class that specifies that all Messages must have a type
 * Types may be things such as status_message, etc.
 *
 * @Author Sara Cagle
 * @Date 9/14/2016
 */
public abstract class Message {

    public abstract String type();
    
}
