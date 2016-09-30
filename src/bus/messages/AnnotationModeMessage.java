package bus.messages;

import constants.AnnotationMode;

/**
 * AnnotationModeMessage
 *
 * The Message object used to pass the annotation mode via enum.
 *
 * Created by saracagle on 9/29/16.
 */
public class AnnotationModeMessage extends Message {
    public AnnotationMode mode;

    /**
     * AnnotationModeMessage constructor
     *
     * @param mode, the current mode for annotations, either drawing or text
     */
    public AnnotationModeMessage(AnnotationMode mode){ this.mode = mode; }

    /**
     * type
     *
     * @return the type of the message, "annotation_mode_message"
     */
    public String type() {
        return "annotation_mode_message";
    }
}
