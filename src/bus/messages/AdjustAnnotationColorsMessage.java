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
    public Color strokeColor;
    public Color boxColor;
    public AnnotationMode annotationMode;

    /**
     * AdjustAnnotationColorsMessage constructor
     */
    public AdjustAnnotationColorsMessage(){
        strokeColor = Color.black;
        boxColor = Color.yellow;
    }

    public void setCurrentColors(Color strokeColor, Color boxColor){
        if(strokeColor == null){
            strokeColor = Color.black;
        }
        if(boxColor == null){
            strokeColor = Color.yellow;
        }
        System.out.println("I just set the stroke color to: "+strokeColor);
        System.out.println("I just set the box color to: "+boxColor);
        this.strokeColor = strokeColor;
        this.boxColor = boxColor;
    }

    public void setCurrentAnnotationMode(AnnotationMode annotationMode){
        System.out.println("I just set the annotation to: "+annotationMode);
        this.annotationMode = annotationMode;
    }

    /**
     * type
     *
     * @return the type of the message, "adjust_annotation_colors_message"
     */
    public String type() {
        return "adjust_annotation_colors_message";
    }
}
