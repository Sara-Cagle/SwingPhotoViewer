package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by saracagle on 11/27/16.
 */
public class Magnet extends JComponent {
    private String tag;
    private Point location;
    private final int DIAMETER = 20;


    public Magnet(String tag){
        this.tag = tag;
        location = new Point();
        this.setSize(new Dimension(100,100));
        this.setPreferredSize(new Dimension(100,100));
    }

    public void setPoint(int x, int y){
        location.x = x;
        location.y = y;
        System.out.println("Reset location to: "+location.x+" , "+location.y);
    }

    public Point getPoint(){
        return location;
    }

    public void applyDelta(int x, int y){
        location.x += x;
        location.y += y;
    }

    public String getTag(){
        return tag;
    }

    public void setTag(String s){
        this.tag = s;
    }


    public boolean containsPoint(Point p){
        return (p.x <= location.x+DIAMETER && p.x >= location.x) && (p.y <=location.y+DIAMETER && p.y>= location.y);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.red);
        g2.fillOval(0, 0, DIAMETER, DIAMETER); //draws an oval inside a rectangle, w/ top left corner of 0,0
    }
}
