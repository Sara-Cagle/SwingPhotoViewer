package bus.messages;

import constants.ViewMode;

/**
 * Created by saracagle on 10/23/16.
 */
public class ViewModeMessage extends Message{

    public ViewMode mode;

    /**
     * ViewModeMessage constructor
     *
     * @param mode, the current mode
     */
    public ViewModeMessage(ViewMode mode){
        this.mode = mode;
    }

    /**
     * type
     *
     * @return the type of the message, "status_message"
     */
    public String type() {
        return "view_mode_message";
    }

}
