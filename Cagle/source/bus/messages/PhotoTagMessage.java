package bus.messages;

/**
 * PhotoTagMessage
 *
 * Message to send info to the photo that it has been tagged.
 *
 * @Autor Sara Cagle
 * @Date 11/13/2016
 */
public class PhotoTagMessage extends Message {
    public int tag;

    /**
     * PanelTagMessage constructor
     *
     * @param tag, the tagNumber to be turned on/off
     */
    public PhotoTagMessage(int tag) {
        this.tag = tag;
    }

    /**
     * type
     *
     * @return the type of the message, "photo_tag_message"
     */
    public String type() {
        return "photo_tag_message";
    }
}
