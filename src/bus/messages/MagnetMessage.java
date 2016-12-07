package bus.messages;

/**
 * MagnetMessage
 *
 * Message to turn the magnet for a tag off or on.
 *
 * @Autor Sara Cagle
 * @Date 12/01/2016
 */
public class MagnetMessage extends Message {
    public int tag;

    /**
     * MagnetMessage constructor
     *
     * @param tag, the tagMagnetNumber to be turned on/off
     */
    public MagnetMessage(int tag) {
        this.tag = tag;
    }

    /**
     * type
     *
     * @return the type of the message, "magnet_message"
     */
    public String type() {
        return "magnet_message";
    }
}
