package bus.messages;

/**
 * Created by saracagle on 9/30/16.
 */
public class DeleteImageMessage extends Message{
    /*public boolean message;

    /**
     * DeleteImageMessage constructor
     *
     * @param message, signals to PhotoComponent to delete the image
     */
   /* public DeleteImageMessage(boolean message){
        this.message = message;
    }*/

    /**
     * type
     *
     * @return the type of the message, "delete_image_message"
     */
    public String type() {
        return "delete_image_message";
    }
}
