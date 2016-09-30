package components;

import java.awt.*;

/**
 * Created by saracagle on 9/30/16.
 */
public class TextBox {
    private int width;
    private int height;
    private int startX;
    private int startY;
    private String text;

    public TextBox(Point startPoint, Point endPoint){
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

    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(startX, startY, width, height);
        g.setColor(Color.black);
        g.drawRect(startX, startY, width, height);
    }
}
