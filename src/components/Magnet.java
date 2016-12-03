package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by saracagle on 11/27/16.
 */
public class Magnet extends JComponent {
    private int tag;
    private Point location;
    private final int DIAMETER = 20;
    private MagnetMouseAdapter mouseAdapter;
    private Color color;


    public Magnet(int tag, Color color){
        this.tag = tag;
        location = new Point();
        this.color = color;
        this.setSize(new Dimension(DIAMETER,DIAMETER));
        this.setPreferredSize(new Dimension(DIAMETER,DIAMETER));
        mouseAdapter = new MagnetMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setPoint(int x, int y){
        location.x = x;
        location.y = y;
    }

    public Point getPoint(){
        return location;
    }

    public void applyDelta(int x, int y){
        location.x += x;
        location.y += y;
    }

    public int getTag(){
        return tag;
    }

    public void setTag(int s){
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
        g2.setColor(color);
        g2.fillOval(0, 0, DIAMETER, DIAMETER); //draws an oval inside a rectangle, w/ top left corner of 0,0
    }


    /**
     * Internal class of MouseAdapter created for the MagnetBoard.
     * Handles mouse information for dragging around magnets
     */
    private class MagnetMouseAdapter extends MouseAdapter {
        private Point anchorPoint;

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e){
            anchorPoint = e.getPoint();
        }

        //has some issues for if you pull your mouse off of the magnet
        public void mouseDragged(MouseEvent e){
            int x = e.getX() - anchorPoint.x + Magnet.this.getLocation().x;
            int y = e.getY() - anchorPoint.y + Magnet.this.getLocation().y;
            Magnet.this.setPoint(x,y);
            Magnet.this.setLocation(x,y);
        }

        public void mouseReleased(MouseEvent e){
            anchorPoint = null;
        }

    }
}
