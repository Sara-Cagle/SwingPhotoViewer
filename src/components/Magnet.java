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

    public Magnet(String tag){
        this.tag = tag;
        location = new Point();
    }

    public void setPoint(int x, int y){
        location.x = x;
        location.y = y;
    }

    public Point getPoint(){
        return location;
    }

    public String getTag(){
        return tag;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.red);
        g2.fillOval(location.x, location.y, 10, 10);
    }
}
