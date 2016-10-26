package bus.messages;

import java.io.File;

/**
 * Created by saracagle on 10/26/16.
 */
public class ThumbnailSizeMessage extends Message{
    private int size;
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
