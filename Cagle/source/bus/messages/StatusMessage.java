package bus.messages;

/**
 * StatusMessage
 *
 * StatusMessage is a type of Message that contains a status
 * for the status bar to display.
 *
 * @Author Sara Cagle
 * @Date 9/14/2016
 */
public class StatusMessage extends Message{

    public String message;

    /**
     * StatusMessage constructor
     *
     * @param message, the current status message
     */
    public StatusMessage(String message){
        this.message = message;
    }

    /**
     * type
     *
     * @return the type of the message, "status_message"
     */
    public String type() {
        return "status_message";
    }
}
