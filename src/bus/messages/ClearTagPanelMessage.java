package bus.messages;

/**
 * Created by saracagle on 11/13/16.
 */
public class ClearTagPanelMessage extends Message {

    /**
     * PanelTagMessage constructor
     *
     */
    public ClearTagPanelMessage(){
    }

    /**
     * type
     *
     * @return the type of the message, "tag_message"
     */
    public String type() {
        return "clear_tag_message";
    }

}
