package panels;

import bus.Bus;
import bus.messages.AdjustAnnotationColorsMessage;
import bus.messages.MagnetOffMessage;
import bus.messages.MagnetOnMessage;
import bus.messages.StatusMessage;
import constants.AnnotationMode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by saracagle on 11/27/16.
 */
public class MagnetPanel extends JPanel{
    private TitledBorder title;
    private JRadioButton off;
    private JRadioButton on;
    private ButtonGroup magnetButtonGroup;

    public MagnetPanel() {
        super();
        this.setMaximumSize(new Dimension(800, 75));

        title = new TitledBorder("Magnet Mode");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);


        off = new JRadioButton("Off", true);
        on = new JRadioButton("On");

        magnetButtonGroup = new ButtonGroup();

        magnetButtonGroup.add(off);
        off.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetOffMessage());
        });

        magnetButtonGroup.add(on);
        on.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetOnMessage());
        });

        super.add(off);
        super.add(on);
    }

}
