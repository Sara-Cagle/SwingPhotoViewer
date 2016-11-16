package bus.messages;

import java.awt.Point;
import java.util.List;

/**
 * Created by saracagle on 11/15/16.
 */
public class SelectionMessage extends Message{

    public List<Point> loop;

    /**
     * StatusMessage constructor
     *
     * @param loop, the collection of points on the loop line.
     */
    public SelectionMessage(List<Point> loop){
        this.loop = loop;
    }

    /**
     * type
     *
     * @return the type of the message, "selection_message"
     */
    public String type() {
        return "selection_message";
    }

}
