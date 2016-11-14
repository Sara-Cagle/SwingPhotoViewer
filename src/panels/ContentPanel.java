package panels;

import java.awt.*;
import javax.swing.*;

import components.LightTable;
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
    private JComponent lightTable;
    private JScrollPane scrollPane;

    /**
     * ContentPanel constructor
     */
    public ContentPanel(){
        super();
        this.setLayout(new BorderLayout()); //need to set border layout in order to have full scrollability
        lightTable = new LightTable();
        this.add(lightTable, BorderLayout.CENTER);
    }
}