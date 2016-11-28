package panels;

import java.awt.*;
import javax.swing.*;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.Message;
import components.LightTable;
import components.MagnetBoard;
import components.PhotoComponent;

/**
 * ContentPanel
 *
 * A JScrollPane that holds uploaded images. Expands based on size of images.
 * Changed from a JPanel.
 *
 * @Author Sara Cagle
 * @Date 9/29/2016
 */
public class ContentPanel extends JPanel implements IMessageListener{
    //private JComponent photoComponent;
    private JComponent lightTable;
    private JComponent magnetBoard;
    //private JScrollPane scrollPane;

    /**
     * ContentPanel constructor
     */
    public ContentPanel(){
        super();
        this.setLayout(new BorderLayout()); //need to set border layout in order to have full scrollability
        lightTable = new LightTable();
        this.add(lightTable, BorderLayout.CENTER);
        magnetBoard = new MagnetBoard();
        Bus.getInstance().registerListener(this);
    }

    public void receiveMessage(Message m) {
        switch (m.type()) {
            case "magnet_off_message":
                this.removeAll();
                this.add(lightTable, BorderLayout.CENTER);
                repaint();
                revalidate();
                break;
            case "magnet_on_message":
                this.removeAll();
                this.add(magnetBoard, BorderLayout.CENTER);
                repaint();
                revalidate();
                break;
                //make sure its magnetboard in grid view
        }
    }
}