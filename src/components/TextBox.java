package components;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.ArrayList;

/**
 * Created by saracagle on 9/30/16.
 */
public class TextBox {
    private int width;
    private int height;
    private int startX;
    private int startY;
    private ArrayList<Character> text;

    public TextBox(Point startPoint, Point endPoint){
        setDimensions(startPoint, endPoint);
        text = new ArrayList<>();
    }

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


    public void addChar(char c){
        text.add(c);
    }

    public void removeChar(){
        text.remove(text.size()-1);
    }

    private String getString(){
        StringBuilder builder = new StringBuilder();
        for(char c: text){
            builder.append(c);
        }
        return builder.toString();
    }

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
            if(currWidth >= width) {
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

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        String[] strings= splitStrings(metrics, getString());
        if( metrics.getHeight()*strings.length > height){
            height = metrics.getHeight()*strings.length;
        }
        g.setColor(Color.yellow);
        g.fillRect(startX, startY, width, height);
        g.setColor(Color.black);
        g.drawRect(startX, startY, width, height);
        int currY = startY+metrics.getHeight();
        for(String s: strings){
            g.drawString(s, startX, currY);
            currY += metrics.getHeight();
        }

    }
}
