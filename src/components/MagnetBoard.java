package components;

import bus.IMessageListener;
import bus.messages.Message;

import javax.swing.*;

/**
 * Created by saracagle on 11/27/16.
 */
public class MagnetBoard extends JPanel implements IMessageListener {

    public void receiveMessage(Message m) {
        switch (m.type()) {
            case "":
                return;
        }
    }
}
