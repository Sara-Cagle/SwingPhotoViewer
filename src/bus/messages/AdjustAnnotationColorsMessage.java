package bus.messages;

import constants.AnnotationMode;

import java.awt.*;

/**
 * AdjustAnnotationColorsMessage
 *
 * Sends a message containing info about the last picked colors.
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
