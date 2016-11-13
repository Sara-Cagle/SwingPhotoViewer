package panels;
import bus.Bus;
import bus.IMessageListener;
import bus.messages.Message;
import bus.messages.TagMessage;
import bus.messages.ThumbnailSizeMessage;

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
public class TagPanel extends JPanel implements IMessageListener{
    private TitledBorder title;
    private JCheckBox tag1;
    private JCheckBox tag2;
    private JCheckBox tag3;
    private JCheckBox tag4;

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
        Bus.getInstance().registerListener(this);

        title = new TitledBorder("Tags");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);

        tag1 = new JCheckBox("Spring");
        tag1.addActionListener(e -> {
            Bus.getInstance().sendMessage(new TagMessage(1));
        });

        tag2 = new JCheckBox("Summer");
        tag2.addActionListener(e -> {
            Bus.getInstance().sendMessage(new TagMessage(2));
        });

        tag3 = new JCheckBox("Fall");
        tag3.addActionListener(e -> {
            Bus.getInstance().sendMessage(new TagMessage(3));
        });

        tag4 = new JCheckBox("Winter");
        tag4.addActionListener(e -> {
            Bus.getInstance().sendMessage(new TagMessage(4));
        });

        this.add(tag1);
        this.add(tag2);
        this.add(tag3);
        this.add(tag4);

    }

    /**
     * receiveMessage
     *
     * Receives a message of a file that will be passed in from the Bus.
     * Listens for the annotation mode and the colors.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m){
        switch(m.type()){
            case "tag_message":
                TagMessage tagMessage = (TagMessage) m;
                int tagNumber = tagMessage.tagNumber;
                switch(tagNumber){ //toggle checked/unchecked
                    case 1:
                        tag1.setSelected(!tag1.isSelected());
                        break;
                    case 2:
                        tag2.setSelected(!tag2.isSelected());
                        break;
                    case 3:
                        tag3.setSelected(!tag3.isSelected());
                        break;
                    case 4:
                        tag4.setSelected(!tag4.isSelected());
                        break;
                }
            default:
                break;
        }
    }
}
