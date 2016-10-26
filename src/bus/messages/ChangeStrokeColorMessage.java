package bus.messages;

import java.awt.*;

/**
 * ChangeStrokeColorMessage
 *
 * Message alerting the color of the LineStroke has been changed.
 *
 * @Author Sara Cagle
 * @Date 10/25/16
 */
public class ChangeStrokeColorMessage extends Message{

        public Color color;

        /**
         * ChangeStrokeColorMessage constructor
         *
         * @param color, the current color
         */
        public ChangeStrokeColorMessage(Color color){
            this.color = color;
        }

        /**
         * type
         *
         * @return the type of the message, "change_stroke_color_message"
         */
        public String type() {
            return "change_stroke_color_message";
        }


}
