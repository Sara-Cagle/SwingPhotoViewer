package bus.messages;

import java.io.File;

/**
 * ImageMessage
 *
 * Message object that shares uploaded files with the bus.
 *
 * Created by saracagle on 9/28/16.
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
