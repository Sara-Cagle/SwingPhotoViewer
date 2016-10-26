package bus.messages;


/**
 * ThumbnailSizeMessage
 *
 * Shares the size of the thumbnails with the LightTable.
 *
 * @Author Sara Cagle
 * @Date 10/26/2016.
 */
public class ThumbnailSizeMessage extends Message{
    public int size;

    /**
     * ThumbnailSizeMessage constructor
     *
     * @param size, the size of the thumbnail
     */
    public ThumbnailSizeMessage(int size){ this.size = size; }

    /**
     * type
     *
     * @return the type of the message, "thumbnail_size_message"
     */
    public String type() {
        return "thumbnail_size_message";
    }
}
