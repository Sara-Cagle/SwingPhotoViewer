package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.AdjustAnnotationColorsMessage;
import bus.messages.Message;
import bus.messages.StatusMessage;
import constants.AnnotationMode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * AnnotationPanel
 *
 * A JPanel that contains information for the annotations.
 * Allows user to toggle annotation type and updates the program status.
 *
 * @Author Sara Cagle
 * @Date 9/13/2016
 */
public class AnnotationPanel extends JPanel implements IMessageListener{
    private TitledBorder title;
    private JRadioButton drawing;
    private JRadioButton text;
    private ButtonGroup annotationButtonGroup;

    /**
     * AnnotationPanel constructor
     *
     * Defines mutually exclusive Text and Drawing annotations.
     * Changing annotation type will send a new status to the bus.
     */
    public AnnotationPanel() {
        super();
        this.setMaximumSize(new Dimension(800, 75));
        Bus.getInstance().registerListener(this);
        Bus.getInstance().setAnnotationMode(AnnotationMode.Text);

        title = new TitledBorder("Annotation Mode");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);


        text = new JRadioButton("Text", true);
        drawing = new JRadioButton("Drawing");

        annotationButtonGroup = new ButtonGroup();

        annotationButtonGroup.add(text);
        text.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(text.getText() + " annotation activated"));
            Bus.getInstance().setAnnotationMode(AnnotationMode.Text);
            Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());
        });

        annotationButtonGroup.add(drawing);
        drawing.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(drawing.getText() + " annotation activated"));
            Bus.getInstance().setAnnotationMode(AnnotationMode.Drawing);
            Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());
        });

        super.add(text);
        super.add(drawing);
    }

    /**
     * receiveMessage
     *
     * Receives messages, listening for changes in annotation style.
     *
     * @param m the message
     */
    public void receiveMessage(Message m) {
        switch (m.type()) {
            case "adjust_annotation_colors_message":
                AnnotationMode mode = Bus.getInstance().getAnnotationMode();
                if(mode == AnnotationMode.Drawing){
                    drawing.setSelected(true);
                }
                else{
                    text.setSelected(true);
                }
                break;
        }
    }

}

