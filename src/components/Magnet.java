package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Magnet
 *
 * Magnet object, an oval with the tag name in it.
 *
 * @Author Sara Cagle
 * @Date 11/27/16
 */
public class Magnet extends JComponent {
    private int tag;
    private Point location;
    private MagnetMouseAdapter mouseAdapter;
    private Color color;
    private IMagnetListener magnetListener;
    private int diameter;
    private int round;

    /**
     * Magnet constructor
     *
     * Magnet constructor, sets the shape of the magnet, color, and what tag it's for.
     *
     * @param tag the integer value of the tag
     * @param color the color to make the magnet
     * @param magnetListener ensures passing that the magnet is being clicked
     * @param diameter the diameter of the magnet
     * @param round the curvature of the magnet
     */
    public Magnet(int tag, Color color, IMagnetListener magnetListener, int diameter, int round){
        this.tag = tag;
        location = new Point();
        this.color = color;
        this.setSize(new Dimension(diameter,diameter));
        this.setPreferredSize(new Dimension(diameter,diameter));
        mouseAdapter = new MagnetMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        this.magnetListener = magnetListener;
        this.diameter = diameter;
        this.round = round;
    }

    /**
     * setPoint
     *
     * Sets the point of the magnet.
     *
     * @param x the new x
     * @param y the new y
     */
    public void setPoint(int x, int y){
        location.x = x;
        location.y = y;
    }

    /**
     * getPoint
     *
     * Gets the point the magnet is located at.
     *
     * @return location, the top left corner of magnet's bounding box
     */
    public Point getPoint(){
        return location;
    }


    /**
     * getTagString
     *
     * Using the tag's integer id, determines the actual tag label.
     * Constrained by only 4 options.
     *
     * @return the label
     */
    public String getTagString(){
        switch(tag){
            case 1:
                return "Spring";
            case 2:
                return "Summer";
            case 3:
                return "Fall";
            case 4:
                return "Winter";
        }
        return "error";
    }

    /**
     * getTag
     *
     * Gets the magnet tag's integer id.
     *
     * @return tag, the magnet tag's id
     */
    public int getTag(){
        return tag;
    }

    /**
     * setTag
     *
     * Sets the magnet's tag integer id.
     *
     * @param s the magnet's tag id
     */
    public void setTag(int s){
        this.tag = s;
    }

    /**
     * paintComponent
     *
     * Paints the magnet and its label on the screen.
     * Uses colors that are passed in, and passed in diameter and curvature.
     *
     * @param g the graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(0, 0, diameter+round, diameter); //draws an oval inside a rectangle, w/ top left corner of 0,0
        g2.setColor(Color.black);
        g.drawString(getTagString(), diameter/2-10, (diameter)/2+4);
    }

    /**
     * Internal class of MouseAdapter created for the MagnetBoard.
     * Handles mouse information for dragging around magnets
     */
    private class MagnetMouseAdapter extends MouseAdapter {
        private Point anchorPoint;

        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e){
            anchorPoint = e.getPoint();
        }

        /**
         * Updates the location of the magnet when it's being dragged.
         * @param e the mouse event
         */
        public void mouseDragged(MouseEvent e){
            if(anchorPoint!= null){
                int x = e.getX() - anchorPoint.x + Magnet.this.getLocation().x;
                int y = e.getY() - anchorPoint.y + Magnet.this.getLocation().y;
                Magnet.this.setPoint(x,y);
                Magnet.this.setLocation(x,y);
                Magnet.this.magnetListener.onMagnetLocationUpdated();
            }
        }

        public void mouseReleased(MouseEvent e){
            anchorPoint = null;
        }

    }
}
