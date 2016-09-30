package components;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


/**
 * DrawingLine
 *
 * The lines that users can draw on the back of photos.
 * Consists of a collection of points and a color.
 *
 * Created by saracagle on 9/29/16.
 */
public class DrawingLine {
    private List<Point> pointList;
    private Color color;

    public DrawingLine(List<Point> pointList, Color color){
        this.pointList = pointList;
        this.color = color;
    }

    /**
     * DrawingLine constructor
     *
     * @param color the color of the line
     */
    public DrawingLine(Color color){
        this.pointList = new ArrayList<>();
        this.color = color;
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
        g.setColor(this.color);
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
