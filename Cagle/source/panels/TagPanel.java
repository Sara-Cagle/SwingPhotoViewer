package panels;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * TagPanel
 *
 * TagPanel is a JPanel that contains information about tags for photos.
 * @Author Sara Cagle
 * @Date 9/13/2016
 */
public class TagPanel extends JPanel{
    private TitledBorder title;
    private JCheckBox option1;
    private JCheckBox option2;
    private JCheckBox option3;
    private JCheckBox option4;

    /**
     * TagPanel constructor
     *
     * Instantiates the tag panel with toggle-able tags to tag photos.
     */
    public TagPanel(){
        super();
        this.setLayout(new GridLayout(0,2));
        //need to set max size or else the objects stretch out
        //the height value (100) will eventually be dynamic based on number of tags
        this.setMaximumSize(new Dimension(800, 100));

        title = new TitledBorder("Tags");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);

        /* These will likely be changed to some ArrayList instantiation in the future */
        option1 = new JCheckBox("Spring");
        option2 = new JCheckBox("Summer");
        option3 = new JCheckBox("Fall");
        option4 = new JCheckBox("Winter");

        this.add(option1);
        this.add(option2);
        this.add(option3);
        this.add(option4);
    }
}
