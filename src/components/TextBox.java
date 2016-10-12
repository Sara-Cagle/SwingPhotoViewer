package components;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.ArrayList;

/**
 * TextBox
 *
 * Class responsible for TextBoxes drawn on the screen
 * when the app is in Text Annotation Mode.
 * TextBoxes are rectangular boxes drawn by the user
 * that can be filled with text. Word wrapping is implemented,
 * and TextBoxes grow vertically when the text is too long.
 *
 * @Author Sara Cagle
 * @Date 9/30/16.
 */
public class TextBox {
    private int width;
    private int height;
    private int startX;
    private int startY;
    private ArrayList<Character> text;
    private Color color;

    /**
     * TextBox
     *
     * Contructor for the TextBox.
     * Sets the box's dimensions and instantiates
     * the text ArrayList for the box's message.
     *
     * @param startPoint the point the user began drawing from
     * @param endPoint the point the user ended their drawing
     */
    public TextBox(Point startPoint, Point endPoint, Color color){
        setDimensions(startPoint, endPoint);
        this.color = color;
        text = new ArrayList<>();
    }

    /**
     * setDimensions
     *
     * Sets the dimension of the TextBox being drawn.
     *
     * @param startPoint the point the user began drawing from
     * @param endPoint the point the user ended their drawing
     */
    public void setDimensions(Point startPoint, Point endPoint){
        this.startX = (int) startPoint.getX();
        this.startY = (int) startPoint.getY();
        if((int)endPoint.getX()< startX){
            this.width = startX-(int)endPoint.getX();
            startX = (int)endPoint.getX();
        }
        else{
            this.width = (int)endPoint.getX()-startX;
        }
        if((int)endPoint.getY()<startY){
            this.height = startY-(int)endPoint.getY();
            startY = (int)endPoint.getY();
        }
        else{
            this.height = (int)endPoint.getY()-startY;
        }
    }


    /**
     * addChar
     *
     * Adds a character into the text ArrayList.
     * Designed to be used when a user types on the keyboard.
     *
     * @param c the character in question
     */
    public void addChar(char c){
        text.add(c);
    }

    /**
     * removeChar
     *
     * Deletes a character out of the text ArrayList.
     * Designed to be used with hitting the backspace button.
     */
    public void removeChar(){
        text.remove(text.size()-1);
    }

    /**
     * getString
     *
     * Takes the input text one character at a time and builds a String.
     *
     * @return text as a String
     */
    private String getString(){
        StringBuilder builder = new StringBuilder();
        for(char c: text){
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * splitStrings
     *
     * Takes in a string being typed into the textbox.
     * Watches for width of the string. If the string grows
     * too big, it needs to be split and brought onto a newline.
     * If the string has a space, we break on the space.
     * If the string doesn't have a space, we break on the letter
     * that no longer fits on the line.
     *
     * @param metrics the FontMetrics object used to check for character width
     * @param str the string being typed in
     * @return an array of strings, each entry is a substring broken to fit the width
     */
    private String[] splitStrings(FontMetrics metrics, String str) {
        ArrayList<String> listOfStrings = new ArrayList<>();
        listOfStrings.add(str);
        int currWidth = 0;
        int prevSpace = -1;
        int currLine = 0;
        for(int i=0; i<listOfStrings.get(currLine).length(); i++) {
            String currString = listOfStrings.get(currLine);
            if(currString.charAt(i)==' ') {
                prevSpace = i;
            }
            if(currString.charAt(i)=='\n'){
                listOfStrings.set(currLine, currString.substring(0, i));
                listOfStrings.add(currString.substring(i+1));
                prevSpace=-1;
                currWidth=0;
                i=-1;
                currLine++;
                continue;
            }
            currWidth+=metrics.charWidth(currString.charAt(i));
            if(currWidth >= width-5) { //-5 is for right hand side padding
                if( prevSpace > -1) {
                    listOfStrings.set(currLine, currString.substring(0, prevSpace));
                    listOfStrings.add(currString.substring(prevSpace+1));
                }
                else {
                    listOfStrings.set(currLine, currString.substring(0, i));
                    listOfStrings.add(currString.substring(i));
                }
                prevSpace=-1;
                currWidth=0;
                i=-1;
                currLine++;
            }
        }
        return listOfStrings.toArray(new String[listOfStrings.size()]);
    }

    /**
     * draw
     *
     * Draws all of the information to the screen.
     * Takes in the splitStrings array from splitting the text.
     * Calculates height so the box will grow vertically if
     * it's too small for the text.
     * Draws the TextBox and its text inside of it.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        String[] strings= splitStrings(metrics, getString());

        if( (metrics.getAscent()+metrics.getDescent()+metrics.getLeading())*strings.length > height){
            height = (metrics.getAscent()+metrics.getDescent()+metrics.getLeading())*strings.length;
        }
        g.setColor(color);
        g.fillRect(startX, startY, width, height);
        g.setColor(Color.black);
        g.drawRect(startX, startY, width, height);
        int currY = startY+(metrics.getAscent()+metrics.getDescent()+metrics.getLeading());
        for(String s: strings){
            g.drawString(s, startX+5, currY-3); //+5 is for left hand side padding, -3 for bottom edge padding (top is automatic)
            currY += (metrics.getAscent()+metrics.getDescent()+metrics.getLeading());
        }

    }
}
