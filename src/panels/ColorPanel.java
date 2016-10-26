package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.Colors;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

/**
 * ColorPanel
 *
 * Contains information about the selected colors
 * for the annotation portion of the program.
 * Shares this with the ContentPanel via the Bus.
 *
 * @Author Sara Cagle
 * @Date 10/4/16
 */
public class ColorPanel extends JPanel  implements IMessageListener{
    private JColorChooser colorChooser;
    private TitledBorder title;
    private JButton lineColorButton;
    private JButton boxColorButton;
    private JPanel lineColorPanel;
    private JPanel boxColorPanel;
    private Color newColor;
    private Color lineColor;
    private Color boxColor;
    private AdjustAnnotationColorsMessage adjust;

    /**
     * ColorPanel
     *
     * Constructor for the color panel. Includes buttons
     * for opening color pickers and JPanels for previewing the
     * selected colors.
     */
    public ColorPanel(){
        super();
        this.setMaximumSize(new Dimension(800, 75));
        Bus.getInstance().registerListener(this);
        title = new TitledBorder("Color Selection");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);
        adjust = new AdjustAnnotationColorsMessage();


        newColor = null;
        lineColor = Color.black;
        boxColor = Color.yellow;
        Bus.getInstance().setBoxColor(boxColor);
        Bus.getInstance().setStrokeColor(lineColor);


        lineColorButton = new JButton("Select Pen Stroke Color");
        lineColorButton.addActionListener((e -> {
            colorChooser = new JColorChooser();
            newColor = JColorChooser.showDialog(this, "Choose a color", this.getLineColor());
            if (newColor != null) {
                Bus.getInstance().sendMessage(new ChangeStrokeColorMessage(newColor));
                //adjust.setCurrentColors(lineColor, boxColor);
                Bus.getInstance().setStrokeColor(lineColor);
            }
        }));

        boxColorButton = new JButton("Select Textbox Color");
        boxColorButton.addActionListener((e -> {
            newColor = JColorChooser.showDialog(this, "Choose a color", this.getBoxColor());
            if (newColor != null) {
                Bus.getInstance().sendMessage(new ChangeTextBoxColorMessage(newColor));
                //adjust.setCurrentColors(lineColor, boxColor);
                Bus.getInstance().setBoxColor(boxColor);
            }
        }));

        //previews of the colors
        boxColorPanel = new JPanel();
        boxColorPanel.setBackground(boxColor);
        lineColorPanel = new JPanel();
        lineColorPanel.setBackground(lineColor);
        this.add(boxColorButton);
        this.add(boxColorPanel);
        this.add(lineColorButton);
        this.add(lineColorPanel);



    }

    /**
     * getLineColor
     *
     * Used to get around final lambda problems.
     * Used to share the current color with the color picker.
     *
     * @return lineColor the question in color
     */
    public Color getLineColor(){
        return lineColor;
    }

    /**
     * getBoxColor
     *
     * Used to get around final lambda problems.
     * Used to share the current color with the color picker.
     *
     * @return boxColor the question in color
     */
    public Color getBoxColor(){
        return boxColor;
    }

    /**
     * receiveMessage
     *
     * Receives messages off of the bus relating to the changing colors.
     *
     * @param m the message off the bus
     */
    public void receiveMessage(Message m) {
        switch(m.type()) {
            case "change_text_box_color_message":
                ChangeTextBoxColorMessage textBoxColorMessage = (ChangeTextBoxColorMessage) m;
                this.boxColorPanel.setBackground(textBoxColorMessage.color);
                boxColor = textBoxColorMessage.color;
                break;
            case "change_stroke_color_message":
                ChangeStrokeColorMessage strokeColorMessage = (ChangeStrokeColorMessage) m;
                this.lineColorPanel.setBackground(strokeColorMessage.color);
                lineColor = strokeColorMessage.color;
                break;
            case "adjust_annotation_colors_message":
                this.boxColorPanel.setBackground(Bus.getInstance().getBoxColor());
                boxColor = Bus.getInstance().getBoxColor();
                this.lineColorPanel.setBackground(Bus.getInstance().getStrokeColor());
                lineColor = Bus.getInstance().getStrokeColor();
                break;
            default:
                break;
        }
    }


}
