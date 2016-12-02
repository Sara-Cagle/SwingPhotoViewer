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
    private String tag;
    private Point location;
    private final int DIAMETER = 20;
    private MagnetMouseAdapter mouseAdapter;


    public Magnet(String tag){
        this.tag = tag;
        location = new Point();
        this.setSize(new Dimension(100,100));
        this.setPreferredSize(new Dimension(100,100));
        mouseAdapter = new MagnetMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
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


    /**
     * Internal class of MouseAdapter created for the MagnetBoard.
     * Handles mouse information for dragging around magnets
     */
    private class MagnetMouseAdapter extends MouseAdapter {
        private Point anchorPoint;

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e){
            System.out.println("Press" + e.getPoint());
            anchorPoint = e.getPoint();
        }

        //has some issues for if you pull your mouse off of the magnet
        public void mouseDragged(MouseEvent e){
            Magnet.this.setLocation(e.getX() - anchorPoint.x + Magnet.this.getLocation().x, e.getY() - anchorPoint.y + Magnet.this.getLocation().y);
        }

        public void mouseReleased(MouseEvent e){
            System.out.println("Release " + e.getPoint());
        }

    }
}
