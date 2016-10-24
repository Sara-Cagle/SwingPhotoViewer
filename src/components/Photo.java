package components;

import javax.xml.soap.Text;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saracagle on 10/23/16.
 */
public class Photo {
    private BufferedImage image;
    private ArrayList<TextBox> textBoxList;
    private ArrayList<LineStroke> lineStrokeList;

    public Photo(BufferedImage i){
        this.image = i;
        this.textBoxList = new ArrayList<>();
        this.lineStrokeList = new ArrayList<>();
    }

    public BufferedImage getImage(){
        return image;
    }

    public void addLine(LineStroke line){
        lineStrokeList.add(line);
    }

    public void addTextBox(TextBox box){
        textBoxList.add(box);
    }

    public ArrayList<LineStroke> getLines(){
        return lineStrokeList;
    }

    public ArrayList<TextBox> getTextBoxes() {
        return textBoxList;
    }

}
