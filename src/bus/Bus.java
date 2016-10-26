package bus;
import bus.messages.*;
import constants.AnnotationMode;

import java.awt.*;
import java.util.ArrayList;

/**
 * Bus
 *
 * A Bus class following singleton design.
 * There exists only one bus in the entire system.
 * The bus keeps track of registered listeners and shares information
 * with all the listeners.
 *
 * @Author Sara Cagle
 * @Date 9/14/2016.
 */
public class Bus {

    private static Bus bus;
    private ArrayList<IMessageListener> registeredListeners;
    private Color strokeColor;
    private Color boxColor;
    private AnnotationMode annotationMode;

    /**
     * Bus constructor
     *
     * Is never instantiated outside of this class (singleton)
     * Keeps track of registered listeners.
     */
    private Bus(){
        registeredListeners = new ArrayList<>();
    }

    /**
     * getInstance
     *
     * Creates a new instance of the bus if it doesn't exist yet
     * Otherwise will return the single instance that exists.
     *
     * @return bus, the only instance of the bus
     */
    public static Bus getInstance(){
        if(bus == null){
            bus = new Bus();
        }
        return bus;
    }

    /**
     * sendMessage
     *
     * For each registered listener, this method sends them
     * all a message.
     *
     * @param m the Message to send to the listeners
     */
    public void sendMessage(Message m){
        for(int i=0; i<registeredListeners.size(); i++){
            registeredListeners.get(i).receiveMessage(m);
        }
    }

    /**
     * registerListener
     *
     * Registers a message listener object with the bus,
     * each message listener can only be registered once.
     *
     * @param listener, the message listener object
     */
    public void registerListener(IMessageListener listener){
        if(!registeredListeners.contains(listener)) {
            registeredListeners.add(listener);
        }
    }

    /**
     * setStrokeColor
     *
     * Sets the linestroke color globally.
     *
     * @param c, the color
     */
    public void setStrokeColor(Color c){
        strokeColor = c;
    }

    /**
     * setBoxColor
     *
     * Sets the textbox color globally.
     *
     * @param c, the color
     */
    public void setBoxColor(Color c){
        boxColor = c;
    }

    /**
     * getStrokeColor
     *
     * Gets the linestroke color to traverse images.
     *
     * @return the color
     */
    public Color getStrokeColor(){
        return strokeColor;
    }

    /**
     * getBoxColor
     *
     * Gets the textbox color to traverse images.
     *
     * @return the color
     */
    public Color getBoxColor(){
        return boxColor;
    }

    /**
     * setAnnotationMode
     *
     * Sets the annotation mode globally.
     *
     * @param m the mode (drawing or text)
     */
    public void setAnnotationMode(AnnotationMode m){
        annotationMode = m;
    }

    /**
     * getAnnotationMode
     *
     * Gets the annotation mode to traverse images.
     *
     * @return the mode (drawing or text)
     */
    public AnnotationMode getAnnotationMode(){
        return annotationMode;
    }
}
