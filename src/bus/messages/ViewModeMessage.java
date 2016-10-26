package bus.messages;

import constants.ViewMode;

/**
 * ViewModeMessage
 *
 * Message alerting that the view mode has changed (Photo, Grid, Split).
 *
 * @Author Sara Cagle
 * @Date 10/23/2016.
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
     * @return the type of the message, "view_mode_message"
     */
    public String type() {
        return "view_mode_message";
    }

}
