package panels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.AdjustAnnotationColorsMessage;
import bus.messages.ImageMessage;
import bus.messages.Message;
import bus.messages.StatusMessage;
import components.LightTable;
import components.MagnetBoard;
import components.Photo;
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
    private LightTable lightTable;
    private MagnetBoard magnetBoard;
    private java.util.List<Photo> photos;
    //private JScrollPane scrollPane;

    /**
     * ContentPanel constructor
     */
    public ContentPanel(){
        super();
        this.photos = new ArrayList<>();
        this.setLayout(new BorderLayout()); //need to set border layout in order to have full scrollability
        lightTable = new LightTable(photos);
        this.add(lightTable, BorderLayout.CENTER);
        magnetBoard = new MagnetBoard(photos);
        Bus.getInstance().registerListener(this);
    }

    public void receiveMessage(Message m) {
        switch (m.type()) {
            case "image_message":
                ImageMessage imageMessage = (ImageMessage) m;
                try {
                    BufferedImage image = ImageIO.read(imageMessage.file);
                    Photo photo = new Photo(image);
                    lightTable.updateCurrentPhoto(photo);
                    this.photos.add(photo);
                    Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());
                    Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                    lightTable.updateView();
                    magnetBoard.updateView();
                }
                catch (IOException e) {
                    //handle it
                }
                break;
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