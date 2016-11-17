package bus.messages;

import java.io.File;

/**
 * ImageMessage
 *
 * Message object that shares uploaded files with the bus.
 *
 * @Author Sara Cagle
 * @Date 9/28/2016.
 */
public class ImageMessage extends Message{
    public File file;

    /**
     * ImageMessage constructor
     *
     * @param file, the current file, ideally an image, being uploaded
     */
    public ImageMessage(File file){ this.file = file; }

    /**
     * type
     *
     * @return the type of the message, "image_message"
     */
    public String type() {
        return "image_message";
    }

}
