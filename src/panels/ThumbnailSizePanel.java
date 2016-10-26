package panels;

import bus.Bus;
import bus.messages.ThumbnailSizeMessage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * ThumbnailSizePanel
 *
 * Contains info for changing the thumbnail size
 *
 * @Author Sara Cagle
 * @Date 10/26/2016.
 */
public class ThumbnailSizePanel extends JPanel{
    private TitledBorder title;
    private JRadioButton small;
    private JRadioButton med;
    private JRadioButton large;
    private ButtonGroup sizeButtonGroup;

    /**
     * ThumbnailSizePanel constructor
     *
     * Sets up the radio buttons and their messages.
     */
    public ThumbnailSizePanel(){
        super();
        this.setMaximumSize(new Dimension(800, 75));
        title = new TitledBorder("Thumbnail Size");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);


        small = new JRadioButton("Small");
        med = new JRadioButton("Medium", true);
        large = new JRadioButton("Large");
        sizeButtonGroup = new ButtonGroup();

        sizeButtonGroup.add(small);
        small.addActionListener(e -> {
            Bus.getInstance().sendMessage(new ThumbnailSizeMessage(50));
        });

        sizeButtonGroup.add(med);
        med.addActionListener(e -> {
            Bus.getInstance().sendMessage(new ThumbnailSizeMessage(100));
        });

        sizeButtonGroup.add(large);
        large.addActionListener(e -> {
            Bus.getInstance().sendMessage(new ThumbnailSizeMessage(200));
        });

        super.add(small);
        super.add(med);
        super.add(large);
    }
}
