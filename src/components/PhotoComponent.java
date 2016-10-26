package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.AnnotationMode;
import constants.Colors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        Bus.getInstance().registerListener(this);
        mouseAdapter = new PhotoMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        this.addKeyListener(this);
        mode = AnnotationMode.Text;
        this.boxColor = Color.yellow;
        this.lineColor = Color.black;
        this.setPreferredSize(new Dimension(DEFAULTWIDTH,DEFAULTHEIGHT));
        this.setSize(new Dimension(DEFAULTWIDTH,DEFAULTHEIGHT));
        if(photo != null && photo.getImage() != null){
            BufferedImage image = photo.getImage();
            this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            this.setSize(new Dimension(image.getWidth(), image.getHeight()));
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
     * receiveMessage
     *
     * Receives a message of a file that will be passed in from the Bus.
     * Listens for the annotation mode and the colors.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m){
        switch(m.type()){
            case "change_text_box_color_message":
                ChangeTextBoxColorMessage textBoxColorMessage = (ChangeTextBoxColorMessage) m;
                boxColor = textBoxColorMessage.color;
                break;
            case "change_stroke_color_message":
                ChangeStrokeColorMessage strokeColorMessage = (ChangeStrokeColorMessage) m;
                lineColor = strokeColorMessage.color;
                break;
            case "annotation_mode_message":
                AnnotationModeMessage annotationMode = (AnnotationModeMessage) m;
                mode = annotationMode.mode;
                break;
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
        if(e.getKeyChar() != '\b'){
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

        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2 && PhotoComponent.this.isPointInImage(e.getPoint())){
                flipped = !flipped;
                repaint();
            }
        }

        public void mousePressed(MouseEvent e){
            PhotoComponent.this.requestFocusInWindow();
            if(flipped){
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
        }

    }

}
