package panels;

import java.awt.*;
import javax.swing.*;
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
public class ContentPanel extends JPanel{
    private JComponent photoComponent;
    private JScrollPane scrollPane;

    /**
     * ContentPanel constructor
     */
    public ContentPanel(){
        super();
        this.setLayout(new BorderLayout()); //need to set border layout in order to have full scrollability
        photoComponent = new PhotoComponent(); //this will determine the size (greater than minimum)
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(photoComponent);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}