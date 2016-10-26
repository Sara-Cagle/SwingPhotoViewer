package components;

import javax.xml.soap.Text;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Photo
 *
 * Photo object that contains the photo image and its annotations.
 *
 * @Author Sara Cagle
 * @Date 10/23/2016
 */
public class Photo {
    private BufferedImage image;
    private ArrayList<TextBox> textBoxList;
    private ArrayList<LineStroke> lineStrokeList;

    /**
     * Photo constructor
     *
     * Contains the image, the collection of textboxes, collections of linestrokes.
     *
     * @param i, the image
     */
    public Photo(BufferedImage i){
        this.image = i;
        this.textBoxList = new ArrayList<>();
        this.lineStrokeList = new ArrayList<>();
    }

    /**
     * getImage
     *
     * Gets the image of the photo object.
     *
     * @return image, the visual photo
     */
    public BufferedImage getImage(){
        return image;
    }

    /**
     * addLine
     *
     * Allows user to add an annotated line to the image.
     *
     * @param line, the line in question
     */
    public void addLine(LineStroke line){
        lineStrokeList.add(line);
    }

    /**
     * addTextBox
     *
     * Allows user to add an annotated textbox to the image.
     *
     * @param box, the textbox in question
     */
    public void addTextBox(TextBox box){
        textBoxList.add(box);
    }

    /**
     * getLines
     *
     * Getter for the collection of annotated lines on an image.
     *
     * @return the lines
     */
    public ArrayList<LineStroke> getLines(){
        return lineStrokeList;
    }

    /**
     * getTextBoxes
     *
     * Getter for the collection of annotated textboxes on an image.
     *
     * @return the textboxes
     */
    public ArrayList<TextBox> getTextBoxes() {
        return textBoxList;
    }

}
