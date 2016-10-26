package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.AdjustAnnotationColorsMessage;
import bus.messages.StatusMessage;
import bus.messages.ThumbnailSizeMessage;
import constants.AnnotationMode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by saracagle on 10/26/16.
 */
public class ThumbnailSizePanel extends JPanel{
    private TitledBorder title;
    private JRadioButton small;
    private JRadioButton med;
    private JRadioButton large;
    private ButtonGroup sizeButtonGroup;

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
            /*Bus.getInstance().setAnnotationMode(AnnotationMode.Text);
            Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());*/
        });

        sizeButtonGroup.add(med);
        med.addActionListener(e -> {
            Bus.getInstance().sendMessage(new ThumbnailSizeMessage(100));
            /*Bus.getInstance().setAnnotationMode(AnnotationMode.Drawing);
            Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());*/
        });

        sizeButtonGroup.add(large);
        large.addActionListener(e -> {
            Bus.getInstance().sendMessage(new ThumbnailSizeMessage(200));
           /* Bus.getInstance().setAnnotationMode(AnnotationMode.Drawing);
            Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());*/
        });

        super.add(small);
        super.add(med);
        super.add(large);



    }
}
