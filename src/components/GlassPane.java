package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.Templates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * GlassPane
 *
 * The glass pane sitting on top of the entire app.
 * Takes in right click digital ink, while passing through left clicks.
 *
 * @Author Sara Cagle
 * @Date 11/9/2016.
 */
public class GlassPane extends JComponent implements IMessageListener{
    private Container contentPane;
    private LineStroke line;
    private Templates templates;
    private JPanel contentPanel;
    private ArrayList<TextBox> selectedBoxes;
    private ArrayList<LineStroke> selectedLines;

    public GlassPane(Container contentPane, JPanel contentPanel) {
        this.contentPane = contentPane;
        this.contentPanel = contentPanel;
        MouseAdapter mouseAdapter = new GlassPaneMouseAdapter();
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        templates = new Templates();
        selectedBoxes = new ArrayList<>();
        selectedLines = new ArrayList<>();
        Bus.getInstance().registerListener(this);
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
        char prevLetter = 'D'; //some arbitrary starting direction
        for(int i=0; i<points.size(); i+=3){
            if(i+1<points.size()){
                char letter = getPointDiff(points.get(i), points.get(i+1));
                if(letter == 'K' || letter =='P'){ //checking for same points or errors
                    builder.append(prevLetter);
                }
                else{
                    prevLetter = letter;
                    builder.append(letter);
                }

            }
        }
        System.out.println(builder.toString());
        String matchedPattern = null;
        for(String pattern: templates.gestures.keySet()){
            if(Pattern.matches(pattern, builder.toString())){
                System.out.println("Found a match w this: "+pattern);
                System.out.println("It is this action: "+templates.gestures.get(pattern));
                matchedPattern = templates.gestures.get(pattern);
                break;
            }
        }
        if(matchedPattern == null){
            JOptionPane.showMessageDialog(null, "Sorry, that gesture was not recognized.");
        }
        else{
            switch(matchedPattern){
                case "left":
                    Bus.getInstance().sendMessage(new MoveLeftMessage());
                    Bus.getInstance().sendMessage(new StatusMessage("Moved to previous photo."));
                    break;
                case "right":
                    Bus.getInstance().sendMessage(new MoveRightMessage());
                    Bus.getInstance().sendMessage(new StatusMessage("Moved to next photo."));
                    break;
                case "pigtail":
                    if(selectedBoxes.size() > 0 || selectedLines.size()>0){ //delete the selection
                        Bus.getInstance().sendMessage(new DeleteSelectedItemsMessage(selectedBoxes, selectedLines));
                        Bus.getInstance().sendMessage(new ClearSelectedItemsMessage());
                        Bus.getInstance().sendMessage(new StatusMessage("Deleted the selection."));
                    }
                    else{ //delete the image
                        Bus.getInstance().sendMessage(new DeleteImageMessage());
                        Bus.getInstance().sendMessage(new StatusMessage("Deleted the image."));
                    }
                    break;
                case "loop":
                    java.util.List<Point> photoComponentLoop = new ArrayList<>();
                    for(Point p: points){
                        photoComponentLoop.add(SwingUtilities.convertPoint(this, p, contentPanel));
                    }
                    Bus.getInstance().sendMessage(new SelectionMessage(photoComponentLoop));
                    Bus.getInstance().sendMessage(new StatusMessage("Ready."));
                    break;
                case "tag1":
                    Bus.getInstance().sendMessage(new PanelTagMessage(1));
                    Bus.getInstance().sendMessage(new PhotoTagMessage(1));
                    Bus.getInstance().sendMessage(new StatusMessage("Toggled a tag."));
                    break;
                case "tag2":
                    Bus.getInstance().sendMessage(new PanelTagMessage(2));
                    Bus.getInstance().sendMessage(new PhotoTagMessage(2));
                    Bus.getInstance().sendMessage(new StatusMessage("Toggled a tag."));
                    break;
                case "tag3":
                    Bus.getInstance().sendMessage(new PanelTagMessage(3));
                    Bus.getInstance().sendMessage(new PhotoTagMessage(3));
                    Bus.getInstance().sendMessage(new StatusMessage("Toggled a tag."));
                    break;
                case "tag4":
                    Bus.getInstance().sendMessage(new PanelTagMessage(4));
                    Bus.getInstance().sendMessage(new PhotoTagMessage(4));
                    Bus.getInstance().sendMessage(new StatusMessage("Toggled a tag."));
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * getPointDiff
     *
     * Takes in points and finds the difference between the two.
     * This difference is converted into a char representing a direction for gestures
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

            if (deltaX > 0 && deltaY > 0) {
                //move southeast
                return 'X';
            }
            if (deltaX > 0 && deltaY == 0) {
                //move east
                return 'D';
            }
            if (deltaX > 0 && deltaY < 0) {
                //move northeast
                return 'E';
            }
            if (deltaX == 0 && deltaY > 0) {
                //move south
                return 'S';
            }
            if (deltaX == 0 && deltaY < 0) {
                //move north
                return 'W';
            }
            if (deltaX == 0 && deltaY == 0) { //edge case
                return 'K';
            }
            if (deltaX < 0 && deltaY > 0) {
                //move southwest
                return 'Z';
            }
            if (deltaX < 0 && deltaY < 0) {
                //move northwest
                return 'Q';
            }
            if (deltaX < 0 && deltaY == 0) {
                //move west
                return 'A';
            }

        return 'P'; //shouldn't hit this
    }

    /**
     * receiveMessage
     *
     * Receives a message of a file that will be passed in from the Bus.
     * Listens for the annotation mode and the colors.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m){
        switch(m.type()){
            case "has_selected_items_message":
                HasSelectedItemsMessage selectedItemsMessage = (HasSelectedItemsMessage) m;
                selectedBoxes = selectedItemsMessage.selectedBoxes;
                selectedLines = selectedItemsMessage.selectedLines;
                break;
            case "clear_selected_items_message":
                selectedBoxes.clear();
                selectedLines.clear();
            default:
                break;
        }
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
                GlassPane.this.line= new LineStroke(Color.pink);
            }
            else{
                dispatchEvent(e);
            }
        }

        public void mouseDragged(MouseEvent e){
            if(isRightClick(e)) {
                GlassPane.this.line.addPoint(e.getPoint());
                repaint();
            }
            else{
                dispatchEvent(e);
            }
        }

        public void mouseReleased(MouseEvent e){
            if(isRightClick(e)){
                GlassPane.this.checkGesture(GlassPane.this.line);
                GlassPane.this.line = null;
                repaint();
            }
            else{
                dispatchEvent(e);
            }
        }

        private void dispatchEvent(MouseEvent e){
            Point currPoint = e.getPoint();
            Point containerPoint = SwingUtilities.convertPoint(GlassPane.this, currPoint, GlassPane.this.contentPane);
            Component component = SwingUtilities.getDeepestComponentAt(GlassPane.this.contentPane, containerPoint.x, containerPoint.y);
            if(component != null){
                Point componentPoint = SwingUtilities.convertPoint(GlassPane.this, currPoint, component);
                component.dispatchEvent(new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiers(), componentPoint.x, componentPoint.y, e.getClickCount(), e.isPopupTrigger()));
            }
        }

        private boolean isRightClick(MouseEvent e) {
            return SwingUtilities.isRightMouseButton(e) || e.isControlDown();
        }
    }

}
