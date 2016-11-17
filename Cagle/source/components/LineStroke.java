package components;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * LineStroke
 *
 * The lines that users can draw on the back of photos.
 * Consists of a collection of points and a color.
 *
 * @author Sara Cagle
 * @date 9/29/16.
 */
public class LineStroke {
    private List<Point> pointList;
    private Color color;
    private boolean selected;

    /**
     * LineStroke constructor
     *
     * @param color the color of the line
     */
    public LineStroke(Color color){
        this.pointList = new ArrayList<>();
        this.color = color;
        selected = false;
    }

    /**
     * getPoints
     *
     * @return pointList, the collection of points for the line
     */
    public List<Point> getPoints(){
        return pointList;
    }

    /**
     * setColor
     *
     * Changes the color of the stroke, used for selecting.
     *
     * @param color, the color in question
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * setSelected
     *
     * Sets the selected bool.
     *
     * @param b, selected or not boolean
     */
    public void setSelected(boolean b){
        selected = b;
    }

    /**
     * applyDelta
     *
     * Takes in the delta of new coordinates from old coordinates.
     * Resets the coordinates of the box to apply the delta.
     *
     * @param x the change in x value
     * @param y the change in y value
     */
    public void applyDelta(int x, int y){
        for(Point p: pointList){
            p.x+=x;
            p.y+=y;
        }
    }

    /**
     * draw
     *
     * Draws the line with the Graphics object.
     * Uses the drawPolyline function to draw the collection of points
     * with the given color.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g){
        int[] x = new int[pointList.size()];
        int[] y = new int[pointList.size()];
        for(int i=0; i<pointList.size(); i++){
            x[i] = pointList.get(i).x;
            y[i] = pointList.get(i).y;
        }
        if(selected){
            g.setColor(Color.pink);
        }
        else {
            g.setColor(this.color);
        }
        g.drawPolyline(x, y, pointList.size());
    }

    /**
     * addPoint
     *
     * Adds a new point to the point list to draw.
     *
     * @param p the point in question
     */
    public void addPoint(Point p){
        this.pointList.add(p);
    }
}
