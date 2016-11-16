package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.AnnotationMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;

/**
 * PhotoComponent
 *
 * PhotoComponent is a JComponent responsible for the image
 * you see in the canvas.
 * Handles drawing the image and interacting with it via mouse events
 * such as "flipping", drawing, and creating text.
 * Listens for messages on the bus to hear states from other parts of the app.
 *
 * @Author Sara Cagle
 * @Date 09/28/16
 */
public class PhotoComponent extends JComponent implements IMessageListener, KeyListener {

    private final int DEFAULTWIDTH = 300;
    private final int DEFAULTHEIGHT = 300;
    private Photo photo;
    private int imageX;
    private int imageY;
    private boolean flipped;
    private AnnotationMode mode;
    private PhotoMouseAdapter mouseAdapter;
    private TextBox inFocusTextBox;
    private Color boxColor;
    private Color lineColor;
    private ArrayList<TextBox> selectedBoxes;
    private ArrayList<LineStroke> selectedLines;
    private boolean hasCurrSelection;


    /**
     * PhotoComponent constructor
     *
     * Registers the component to listen to the bus.
     * Sets up special mouseAdapters to prepare for drawing and text.
     * Prepares collections of lines to be drawn.
     * Waits for images to be uploaded.
     *
     * @param photo, the Photo object it's drawing on
     */
    public PhotoComponent(Photo photo){
        super();
        this.setFocusable(true);
        this.photo = photo;
        flipped = false;
        mouseAdapter = new PhotoMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        hasCurrSelection = false;
        selectedBoxes = new ArrayList<>();
        selectedLines = new ArrayList<>();
        this.addKeyListener(this);
        this.mode = Bus.getInstance().getAnnotationMode();
        this.boxColor = Bus.getInstance().getBoxColor();
        this.lineColor = Bus.getInstance().getStrokeColor();
        this.setPreferredSize(new Dimension(DEFAULTWIDTH,DEFAULTHEIGHT));
        this.setSize(new Dimension(DEFAULTWIDTH,DEFAULTHEIGHT));
        if(photo != null && photo.getImage() != null){
            BufferedImage image = photo.getImage();
            this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            this.setSize(new Dimension(image.getWidth(), image.getHeight()));
            Bus.getInstance().sendMessage(new ClearTagPanelMessage());
            for(Integer tag: photo.getTags()) {
                Bus.getInstance().sendMessage(new PanelTagMessage(tag));
            }
        }

        if(photo != null) {
            Bus.getInstance().registerListener(this);
        }
    }

    /**
     * drawBackground
     *
     * Draws the background of the canvas before anything else on top.
     * Considered the "empty space" of app if the image isn't a large size.
     * You shouldn't be able to interact with this space.
     *
     * @param g the Graphics object
     */
    public void drawBackground(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }

    /**
     * drawImage
     *
     * Draws the uploaded image with the Graphics object.
     *
     * @param g the Graphics object
     */
    public void drawImage(Graphics g){
        int currScreenHeight = this.getHeight();
        int currScreenWidth = this.getWidth();
        imageY = 0;
        imageX = 0;
        if(photo != null && photo.getImage() != null) { //centers the image
            BufferedImage image = photo.getImage();
            if (image.getHeight() < currScreenHeight) {
                int diff = currScreenHeight - image.getHeight();
                diff = diff / 2;
                imageY += diff;
            }
            if (image.getWidth() < currScreenWidth) {
                int diff = currScreenWidth - image.getWidth();
                diff = diff / 2;
                imageX += diff;
            }
            g.drawImage(image, imageX, imageY, null);
        }

    }

    /**
     * drawFlipped
     *
     * Draws the flipped back of the image on the screen via a rectangle.
     *
     * @param g the Graphics object
     */
    public void drawFlipped(Graphics g){
        BufferedImage image = photo.getImage();
        g.setColor(Color.white);
        g.fillRect(imageX,imageY,image.getWidth(),image.getHeight());
        for(LineStroke line: photo.getLines()){
            line.draw(g);
        }
        for(TextBox textBox: photo.getTextBoxes()){ //textboxes will always be on top
            textBox.draw(g);
        }

    }

    /**
     * paintComponent
     *
     * Overrides the initial paintComponent function that's called on repaint()
     * Draws the background, and then either an image or the back of the image
     * depending on if the image is flipped or not.
     *
     * @param g the Graphics item
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawBackground(g);
        if(!flipped){
            drawImage(g);
        }
        else{
            drawFlipped(g);
        }
        revalidate();
    }

    /**
     * isPointInImage
     *
     * Used when drawing and interacting with image to know if you're touching
     * the image, or if you're in the blank area around the image.
     *
     * @param p the Point in question
     * @return boolean that says if the point is inside the image or not
     */
    public boolean isPointInImage(Point p){
        BufferedImage image = photo.getImage();
        return p.x>imageX && p.y>imageY && p.x<imageX+image.getWidth() && p.y<imageY+image.getHeight();
    }

    /**
     * selection
     *
     * Using the points of a loop, determines which text boxes have been selected.
     *
     * @param loop the collection of points in the drawn loop
     */
    public void selection(java.util.List<Point> loop){
        Bus.getInstance().sendMessage(new ClearSelectedItemsMessage());
        if(!flipped){
            JOptionPane.showMessageDialog(null, "Are you trying to select something? Try flipping the photo, first.");
            return;
        }

        for(TextBox box: photo.getTextBoxes()){
            for(Point p: loop){
                if(box.isPointInside(p)){
                    selectedBoxes.add(box);
                    box.setSelected(true);
                    hasCurrSelection = true;
                    break;
                }
            }
        }
        int maxY = 0;
        int maxX = 0;
        int minY = Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        for(Point p: loop){
            if(p.x > maxX) {
                maxX = p.x;
            }
            if(p.y > maxY){
                maxY = p.y;
            }
            if(p.x< minX){
                minX = p.x;
            }
            if(p.y< minY){
                minY = p.y;
            }
        }

        for(LineStroke stroke: photo.getLines()){
            for(Point p: stroke.getPoints()){
                if((p.x <= maxX && p.x >= minX) && (p.y <= maxY && p.y >= minY)){ //uses a square bounding box
                    stroke.setSelected(true);
                    selectedLines.add(stroke);
                    hasCurrSelection = true;
                    break;
                }
            }
        }
        Bus.getInstance().sendMessage(new HasSelectedItemsMessage(selectedBoxes, selectedLines));
        repaint();
    }

    /**
     * clearSelected
     *
     * Unselects all of the selected items.
     */
    public void clearSelected(){
        hasCurrSelection = false;
        for(TextBox b: selectedBoxes){
            b.setSelected(false);
        }
        for(LineStroke s: selectedLines){
            s.setSelected(false);
        }
        selectedBoxes.clear();
        selectedLines.clear();
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
            case "adjust_annotation_colors_message":
                mode = Bus.getInstance().getAnnotationMode();
                lineColor = Bus.getInstance().getStrokeColor();
                boxColor = Bus.getInstance().getBoxColor();
                break;
            case "photo_tag_message":
                PhotoTagMessage panelTagMessage = (PhotoTagMessage) m;
                int tagNumber = panelTagMessage.tag;
                if(!photo.hasTag(tagNumber)){ //add the tag if it doesn't exist yet
                    photo.addTag(tagNumber);
                }
                else{
                    photo.removeTag(tagNumber); //otherwise, remove the tag
                }
                break;
            case "selection_message":
                SelectionMessage selectionMessage = (SelectionMessage) m;
                this.selection(selectionMessage.loop);
                break;
            case "delete_selected_items_message":
                DeleteSelectedItemsMessage deleteSelectedItemsMessage = (DeleteSelectedItemsMessage) m;
                for(TextBox box: deleteSelectedItemsMessage.selectedBoxes){
                    photo.getTextBoxes().remove(box);
                }
                for(LineStroke stroke: deleteSelectedItemsMessage.selectedLines){
                    photo.getLines().remove(stroke);
                }
                break;
            case "clear_selected_items_message":
                clearSelected();
            default:
                break;
        }
    }

    /*
     * Key events -------------------------------------------------------------------------------
     * ------------------------------------------------------------------------------------------
     */

    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){ //can't be done in keyTyped because that's only for input keys
            inFocusTextBox.removeChar();
        }
        if(e.getKeyChar() != '\b' && e.getKeyCode() != KeyEvent.VK_CONTROL && inFocusTextBox != null){
            inFocusTextBox.addChar(e.getKeyChar());
        }
        repaint();
    }

    public void keyReleased(KeyEvent e){}

    /*
     * ------------------------------------------------------------------------------------------
     * ------------------------------------------------------------------------------------------
     */


    /**
     * Internal class of MouseAdapter created for the PhotoComponent.
     * Handles mouse information for drawing on the backs of photos.
     * Makes use of mouseDragged for drawing, as well as mouseClicked for double click, and mouseRelease to finish lines.
     */
    private class PhotoMouseAdapter extends MouseAdapter{
        private LineStroke currentLine;
        private TextBox currentTextBox;
        private Point startCorner;
        private Point endCorner;
        private Point prevPoint;

        public void mouseClicked(MouseEvent e) {
            Bus.getInstance().sendMessage(new ClearSelectedItemsMessage());
            if(e.getClickCount() == 2 && PhotoComponent.this.isPointInImage(e.getPoint())){
                flipped = !flipped;
                repaint();
            }
        }

        public void mousePressed(MouseEvent e){
            PhotoComponent.this.requestFocusInWindow();
            if(flipped){
                if(PhotoComponent.this.hasCurrSelection){
                    prevPoint = e.getPoint();
                    //do nothing, don't let user annotate
                    return;
                }
                if(mode == AnnotationMode.Drawing){
                    currentLine = new LineStroke(PhotoComponent.this.lineColor);
                    PhotoComponent.this.photo.addLine(currentLine);
                }
                else if(mode == AnnotationMode.Text && PhotoComponent.this.isPointInImage(e.getPoint())){
                    startCorner = e.getPoint();
                    currentTextBox = new TextBox(startCorner, startCorner, PhotoComponent.this.boxColor);
                    PhotoComponent.this.photo.addTextBox(currentTextBox);
                }
            }
        }

        public void mouseDragged(MouseEvent e){
            if(flipped){
                if(PhotoComponent.this.hasCurrSelection){

                    int deltaX = e.getPoint().x - prevPoint.x;
                    int deltaY = e.getPoint().y - prevPoint.y;
                    for(TextBox b: PhotoComponent.this.selectedBoxes){
                        b.applyDelta(deltaX, deltaY);
                    }
                    for(LineStroke s: PhotoComponent.this.selectedLines){
                        s.applyDelta(deltaX, deltaY);
                    }
                    repaint();
                    prevPoint = e.getPoint();
                    return;
                }
                if(mode == AnnotationMode.Drawing && PhotoComponent.this.isPointInImage(e.getPoint())){
                    this.currentLine.addPoint(e.getPoint());
                    repaint();
                }
                else if(mode == AnnotationMode.Text && PhotoComponent.this.isPointInImage(e.getPoint())){
                    if(startCorner != null){ //catches dragging the mouse from out of image INTO image
                        endCorner = e.getPoint();
                        currentTextBox.setDimensions(startCorner, endCorner);
                        repaint();
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e){
            currentLine = null;
            PhotoComponent.this.inFocusTextBox = currentTextBox;
            currentTextBox = null;
            startCorner = null;
            endCorner = null;
            prevPoint = null;
        }

    }

}
