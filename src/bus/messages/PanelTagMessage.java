package bus.messages;

/**
 * PanelTagMessage
 *
 * Message to send info to the tag panel that there is an associated tag with a photo.
 *
 * @Author Sara Cagle
 * @Date 11/13/2016
 */
public class PanelTagMessage extends Message{

    public int tagNumber;

    /**
     * PanelTagMessage constructor
     *
     * @param tagNumber, the tagNumber to be turned on/off
     */
    public PanelTagMessage(int tagNumber){
        this.tagNumber = tagNumber;
    }

    /**
     * type
     *
     * @return the type of the message, "panel_tag_message"
     */
    public String type() {
        return "panel_tag_message";
    }
}
