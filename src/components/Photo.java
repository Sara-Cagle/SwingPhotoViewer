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
    private ArrayList<Integer> tagList;

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
        this.tagList = new ArrayList<>();
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

    /**
     * hasTag
     *
     * Checks to see if the photo has already been tagged with this.
     *
     * @param tagNumber, the tag
     * @return boolean, the photo has the tag or not
     */
    public boolean hasTag(int tagNumber){
        return tagList.contains(tagNumber);
    }

    /**
     * addTag
     *
     * Adds given tag to the photo.
     *
     * @param tagNumber, the tag
     */
    public void addTag(int tagNumber){
        tagList.add(tagNumber);
    }

    /**
     * removeTag
     *
     * Removes the given tag from the photo.
     *
     * @param tagNumber, the tag
     */
    public void removeTag(int tagNumber){
        tagList.remove(tagList.indexOf(tagNumber));
    }

    /**
     * getTags
     *
     * Getter for the list of tags.
     *
     * @return tagList, the list of tags
     */
    public ArrayList<Integer> getTags(){
        return tagList;
    }

}
