package bus.messages;

import constants.AnnotationMode;

import java.awt.*;

/**
 * AdjustAnnotationColorsMessage
 *
 * Sends a message alerting that the program must pull global colors and annotation mode off the bus.
 *
 * @Author Sara Cagle
 * @Date 10/25/2016.
 */
public class AdjustAnnotationColorsMessage extends Message{

    /**
     * type
     *
     * @return the type of the message, "adjust_annotation_colors_message"
     */
    public String type() {
        return "adjust_annotation_colors_message";
    }
}
