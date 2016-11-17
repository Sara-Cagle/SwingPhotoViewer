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
                System.out.println("Just picked a new pen color, its "+newColor);
                Bus.getInstance().setStrokeColor(newColor);
                Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());
            }
        }));

        boxColorButton = new JButton("Select Textbox Color");
        boxColorButton.addActionListener((e -> {
            newColor = JColorChooser.showDialog(this, "Choose a color", this.getBoxColor());
            if (newColor != null) {
                Bus.getInstance().setBoxColor(newColor);
                Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());
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
            case "adjust_annotation_colors_message":
                boxColor = Bus.getInstance().getBoxColor();
                this.boxColorPanel.setBackground(boxColor);
                lineColor = Bus.getInstance().getStrokeColor();
                this.lineColorPanel.setBackground(lineColor);
                break;
            default:
                break;
        }
    }


}
