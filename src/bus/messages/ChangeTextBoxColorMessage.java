package bus.messages;

import java.awt.*;

/**
 * ChangeTextBoxColorMessage
 *
 * Message alerting the color of the LineStroke has been changed.
 *
 * @Author Sara Cagle 
 * @Date 10/25/16
 */
public class ChangeTextBoxColorMessage extends Message{

    public Color color;
    //public constants.Colors objectType;

    /**
     * ChangeStrokeColorMessage constructor
     *
     * @param color, the current color
     * @param , enum of the object for the colors
     */
    public ChangeTextBoxColorMessage(Color color/*, constants.Colors objectType*/){
        //this.objectType = objectType;
        this.color = color;
    }

    /**
     * type
     *
     * @return the type of the message, "change_color_message"
     */
    public String type() {
        return "change_text_box_color_message";
    }


}
