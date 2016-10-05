package bus.messages;

import java.awt.*;

/**
 * ChangeColorMessage
 *
 * A message for the bus about the changing colors
 * for the annotation modes.
 *
 * @Author Sara Cagle
 * @Date 10/4/16.
 */
public class ChangeColorMessage extends Message {
    public Color color;
    public constants.Colors objectType;

    /**
     * ChangeColorMessage constructor
     *
     * @param color, the current color
     * @param , enum of the object for the colors
     */
    public ChangeColorMessage(Color color, constants.Colors objectType){
        this.objectType = objectType;
        this.color = color;
    }

    /**
     * type
     *
     * @return the type of the message, "change_color_message"
     */
    public String type() {
        return "change_color_message";
    }
}
