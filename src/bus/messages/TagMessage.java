package bus.messages;

/**
 * @Author Sara Cagle
 * @Datw 11/13/16
 */
public class TagMessage extends Message{

    public int tagNumber;

    /**
     * TagMessage constructor
     *
     * @param tagNumber, the tagNumber to be turned on/off
     */
    public TagMessage(int tagNumber){
        this.tagNumber = tagNumber;
    }

    /**
     * type
     *
     * @return the type of the message, "tag_message"
     */
    public String type() {
        return "tag_message";
    }
}
