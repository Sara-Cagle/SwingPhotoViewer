package bus.messages;

/**
 * Created by saracagle on 11/13/16.
 */
public class PhotoTagMessage extends Message {
    public int tag;

    public PhotoTagMessage(int tag) {
        this.tag = tag;
    }

    public String type() {
        return "photo_tag_message";
    }
}
