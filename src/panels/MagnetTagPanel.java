package panels;

import bus.Bus;
import bus.messages.MagnetMessage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * MagnetTagPanel
 *
 * Panel for when magnet mode is on, allows adding/removing the magnets.
 *
 * @Author Sara Cagle
 * @Date 12/2/2016
 */
public class MagnetTagPanel extends JPanel {
    private TitledBorder title;
    private JCheckBox tag1;
    private JCheckBox tag2;
    private JCheckBox tag3;
    private JCheckBox tag4;

    /**
     * MagnetTagPanel
     *
     * MagnetTagPanel constructor, creates checkboxes for each magnet.
     */
    public MagnetTagPanel(){
        super();
        this.setLayout(new GridLayout(0,2));
        this.setMaximumSize(new Dimension(500, 100));

        title = new TitledBorder("Magnets");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);

        tag1 = new JCheckBox("Spring Magnet");
        tag1.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetMessage(1));
        });

        tag2 = new JCheckBox("Summer Magnet");
        tag2.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetMessage(2));
        });

        tag3 = new JCheckBox("Fall Magnet");
        tag3.addActionListener(e ->  {
            Bus.getInstance().sendMessage(new MagnetMessage(3));
        });

        tag4 = new JCheckBox("Winter Magnet");
        tag4.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetMessage(4));
        });

        this.add(tag1);
        this.add(tag2);
        this.add(tag3);
        this.add(tag4);
    }
}
