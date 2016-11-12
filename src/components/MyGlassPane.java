package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @Author Sara Cagle
 * @date 11/9/2016.
 */
public class MyGlassPane extends JComponent {
    private Container contentPane;
    private LineStroke line;

    public MyGlassPane(Container contentPane) {
        this.contentPane = contentPane;
        System.out.println("instantiated glass pane");
        MouseAdapter mouseAdapter = new GlassPaneMouseAdapter();
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    /**
     * checkGesture
     *
     * Finds and executes applicable gestures from the given line.
     *
     * @param l, the line for the gesture
     */
    private void checkGesture(LineStroke l){
        StringBuilder builder = new StringBuilder();
        java.util.List<Point> points = l.getPoints();
        for(int i=0; i<points.size(); i++){
            if(i+1<points.size()){
                builder.append(getPointDiff(points.get(i), points.get(i+1)));
            }
        }
        System.out.println(builder.toString());

        //if string matches any of the regex, do the action
    }

    /**
     * getPointDiff
     *
     * Takes in points and finds the difference between the two.
     * This differene is converted into a char representing a direction for gestures
     *
     * @param p1, the first point
     * @param p2, the second point you move to
     * @return the char representing the direction moved
     */
    private char getPointDiff(Point p1, Point p2){
        //North = W
        //South = S
        //West = A
        //East = D
        //Northwest = Q
        //Northeast = E
        //Southwest = Z
        //Southeast = X
        int deltaX = p2.x-p1.x;
        int deltaY = p2.y-p1.y;
        if(deltaX > 0 && deltaY >0){
            //move southeast
            return 'X';
        }
        if(deltaX >0 && deltaY == 0){
            //move east
            return 'D';
        }
        if(deltaX > 0 && deltaY <0){
            //move northeast
            return 'E';
        }
        if(deltaX == 0 && deltaY >0){
            //move south
            return 'S';
        }
        if(deltaX == 0 && deltaY<0){
            //move north
            return 'W';
        }
        if(deltaX<0 && deltaY>0){
            //move southwest
            return 'Z';
        }
        if(deltaX<0 && deltaY<0){
            //move northwest
            return 'Q';
        }
        if(deltaX < 0 && deltaY == 0){
            //move west
            return 'A';
        }
        return 'P';
    }


    /**
     * paintComponent
     *
     * Paints anything drawn on the glass pane. Should only be gesture lines.
     *
     * @param g, the Graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //System.out.println("drawing glass pane");
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(line != null){
            g2.setStroke(new BasicStroke(5));
            line.draw(g2);
        }
        revalidate();
    }

    private class GlassPaneMouseAdapter extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if(isRightClick(e)){

            }
            else{
               dispatchEvent(e);
            }
        }

        public void mousePressed(MouseEvent e){
            if(isRightClick(e)){
                MyGlassPane.this.line= new LineStroke(Color.pink);
            }
            else{
                dispatchEvent(e);
            }
        }

        public void mouseDragged(MouseEvent e){
            if(isRightClick(e)){
                MyGlassPane.this.line.addPoint(e.getPoint());
                repaint();
            }
            else{
                dispatchEvent(e);
            }
        }

        public void mouseReleased(MouseEvent e){
            if(isRightClick(e)){
                MyGlassPane.this.checkGesture(MyGlassPane.this.line);
                MyGlassPane.this.line = null;
                repaint();
            }
            else{
                dispatchEvent(e);
            }
        }

        private void dispatchEvent(MouseEvent e){
            Point currPoint = e.getPoint();
            Point containerPoint = SwingUtilities.convertPoint(MyGlassPane.this, currPoint, MyGlassPane.this.contentPane);
            Component component = SwingUtilities.getDeepestComponentAt(MyGlassPane.this.contentPane, containerPoint.x, containerPoint.y);
            if(component != null){
                Point componentPoint = SwingUtilities.convertPoint(MyGlassPane.this, currPoint, component);
                component.dispatchEvent(new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiers(), componentPoint.x, componentPoint.y, e.getClickCount(), e.isPopupTrigger()));
            }
        }

        private boolean isRightClick(MouseEvent e) {
            return SwingUtilities.isRightMouseButton(e) || e.isControlDown();
        }
    }

}
