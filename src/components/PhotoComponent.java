package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.AnnotationModeMessage;
import bus.messages.Message;
import bus.messages.ImageMessage;
import bus.messages.StatusMessage;
import constants.AnnotationMode;

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
 * you see in the canvas area upon image uploads.
 * Handles drawing the image and interacting with it via mouse events
 * such as "flipping", drawing, and creating text.
 * Listens for messages on the bus to hear states from other parts of the app.
 *
 * Created by saracagle on 9/28/16.
 */
public class PhotoComponent extends JComponent implements IMessageListener, KeyListener {

    private boolean flipped;
    private boolean photoUploaded;
    private BufferedImage image;
    private AnnotationMode mode;
    private ArrayList<LineStroke> lines;
    private ArrayList<TextBox> textBoxes;
    private PhotoMouseAdapter mouseAdapter;
    private TextBox inFocusTextBox;

    /**
     * PhotoComponent constructor
     *
     * Registers the component to listen to the bus.
     * Sets up special mouseAdapters to prepare for drawing and text.
     * Prepares collections of lines to be drawn.
     * Waits for images to be uploaded.
     */
    public PhotoComponent(){
        super();
        this.setFocusable(true);
        flipped = false;
        photoUploaded = false;
        Bus.getInstance().registerListener(this);
        mouseAdapter = new PhotoMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        this.addKeyListener(this);
        mode = AnnotationMode.Text;
        lines = new ArrayList<>();
        textBoxes = new ArrayList<>();
        //this.setPreferredSize(new Dimension(640,480));
        //this.setSize(new Dimension(640,480));
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
        g.drawImage(image, 0, 0, null); //center this
    }

    /**
     * drawFlipped
     *
     * Draws the flipped back of the image on the screen via a rectangle.
     *
     * @param g the Graphics object
     */
    public void drawFlipped(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,image.getWidth(),image.getHeight());
        for(LineStroke line: lines){
            line.draw(g);
        }
        for(TextBox textBox: textBoxes){ //textboxes will always be on top
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

    public void typeInBox(){
        if(inFocusTextBox != null && mode == AnnotationMode.Text){

        }
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
        return p.x>0 && p.y>0 && p.x<image.getWidth() && p.y<image.getHeight();
    }

    /**
     * clearState
     *
     * Resets the state of an image that's been uploaded.
     * Should be called whenever an image is uploaded.
     * Will clear the back of the image and reset the flipped bool.
     */
    public void clearState(){
        lines = new ArrayList<>();
        textBoxes = new ArrayList<>();
        flipped = false;
    }


    /**
     * receiveMessage
     *
     * Receives a message of a file that will be passed in from the Bus.
     * If the message is an ImageMessage, then the file will be checked if it's an image.
     * If it's an image, then it'll be given to the component to display.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m){
        switch(m.type()){
            case "image_message":
                ImageMessage imageMessage = (ImageMessage) m;
                try{
                    image = ImageIO.read(imageMessage.file);
                    this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                    //this.setSize(new Dimension(image.getWidth(), image.getHeight()));
                    Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                    photoUploaded = true;
                    clearState();
                    repaint();
                }
                catch(IOException e){
                    //handle it
                }
                break;
            case "delete_image_message":
                photoUploaded = false;
                image = null;
                //need some default size to reset to, isn't reset until new image uploaded
                Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                clearState();
                repaint();
                break;
            case "annotation_mode_message":
                AnnotationModeMessage annotationMode = (AnnotationModeMessage) m;
                mode = annotationMode.mode;
                break;
            default:
                break;
        }
    }

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() != '\b'){
            inFocusTextBox.addChar(e.getKeyChar());
        }
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){ //can't be done in keyTyped because that's only for input keys
            inFocusTextBox.removeChar();
        }
        repaint();
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {

    }

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
        private TextBox inFocusTextBox;

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
                    currentLine = new LineStroke(Color.black);
                    PhotoComponent.this.lines.add(currentLine);
                }
                else if(mode == AnnotationMode.Text && PhotoComponent.this.isPointInImage(e.getPoint())){
                    startCorner = e.getPoint();
                    currentTextBox = new TextBox(startCorner, startCorner);
                    PhotoComponent.this.textBoxes.add(currentTextBox);
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
                    endCorner = e.getPoint();
                    currentTextBox.setDimensions(startCorner, endCorner);
                    repaint();



                    //text
                }
            }
        }

        public void mouseReleased(MouseEvent e){
            currentLine = null;
            PhotoComponent.this.inFocusTextBox = currentTextBox;
            currentTextBox = null; //for now
        }

    }

}
