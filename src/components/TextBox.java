package components;

import java.awt.*;

/**
 * Created by saracagle on 9/30/16.
 */
public class TextBox {
    private int width;
    private int height;
    private Point startPoint;
    private String text;

    public TextBox(Point startPoint, Point endPoint){
        this.startPoint = startPoint;
        this.width = (int)(endPoint.getX()-startPoint.getX());
        this.height = (int)(endPoint.getY()-startPoint.getY());
    }


    public void addChar(char c){

    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.drawRect((int)startPoint.getX(), (int)startPoint.getY(), width, height);
    }
}
