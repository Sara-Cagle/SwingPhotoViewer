package bus.messages;

import java.awt.*;

/**
 * Created by saracagle on 10/4/16.
 */
public class ChangeColorMessage extends Message {
    public Color color;
    public constants.Colors objectType;

    /**
     * StatusMessage constructor
     *
     * @param color, the current color
     */
    public ChangeColorMessage(Color color, constants.Colors objectType){
        this.objectType = objectType;
        this.color = color;
    }

    /**
     * type
     *
     * @return the type of the message, "status_message"
     */
    public String type() {
        return "change_color_message";
    }
}
