package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.Message;
import bus.messages.StatusMessage;

import javax.swing.*;
import java.awt.*;

/**
 * StatusPanel
 *
 * JPanel responsible for putting status messages on the screen.
 *
 * @Author Sara Cagle
 * @Date 9/13/2016
 */
public class StatusPanel extends JPanel implements IMessageListener {

    private JLabel statusLabel;

    public StatusPanel(){
        super();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusLabel = new JLabel("Ready");
        this.add(statusLabel);
        Bus.getInstance().registerListener(this);
    }

    /**
     * receiveMessage
     *
     * Receives a message that will be passed in from the Bus.
     * If the message is a StatusMessage, then the message will be displayed on the status bar.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m){
        switch(m.type()){
            case "status_message":
                StatusMessage statusMessage = (StatusMessage) m;
                this.statusLabel.setText(statusMessage.message);
                break;
            default:
                break;
        }
    }


}
