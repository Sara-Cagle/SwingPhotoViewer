package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.ChangeColorMessage;
import bus.messages.Message;
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
    JColorChooser colorChooser;
    TitledBorder title;
    JButton lineColorButton;
    JButton boxColorButton;
    JPanel lineColorPanel;
    JPanel boxColorPanel;
    Color newColor;
    Color lineColor;
    Color boxColor;

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
        title = new TitledBorder("Color Selection");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);

        newColor = null;
        lineColor = Color.black;
        boxColor = Color.yellow;


        lineColorButton = new JButton("Select Pen Color");
        lineColorButton.addActionListener((e -> {
            colorChooser = new JColorChooser();
            newColor = JColorChooser.showDialog(this, "Choose a color", this.getLineColor());
            if (newColor != null) {
                Bus.getInstance().sendMessage(new ChangeColorMessage(newColor, Colors.Line));
            }
        }));

        boxColorButton = new JButton("Select Textbox Color");
        boxColorButton.addActionListener((e -> {
            newColor = JColorChooser.showDialog(this, "Choose a color", this.getBoxColor());
            if (newColor != null) {
                Bus.getInstance().sendMessage(new ChangeColorMessage(newColor, Colors.Box));
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

        Bus.getInstance().registerListener(this);

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
            case "change_color_message":
                ChangeColorMessage colorMessage = (ChangeColorMessage) m;
                if(colorMessage.objectType == Colors.Line){
                    this.lineColorPanel.setBackground(colorMessage.color);
                    lineColor = colorMessage.color;
                }
                else {
                    this.boxColorPanel.setBackground(colorMessage.color);
                    boxColor = colorMessage.color;
                }
                break;
            default:
                break;
        }
    }

    private void cleanUpColorSwatches(){
        AbstractColorChooserPanel[] colorTabs = colorChooser.getChooserPanels();
        colorChooser.setPreviewPanel(new JPanel());
        for(int i=0; i<colorTabs.length; i++){
            if(colorTabs[i].getClass().getName() == "javax.swing.colorchooser.DefaultSwatchChooserPanel"){
                /*JPanel swatches = (JPanel) colorTabs[i].getComponent(0); //these will remove "recent:" and the recent swatches
                System.out.println("this many components in swatches: "+swatches.getComponentCount());
                swatches.remove(2);
                System.out.println("this many components in swatches: "+swatches.getComponentCount());
                swatches.remove(1);
                System.out.println("this many components in swatches: "+swatches.getComponentCount());*/
            }
            else{
                colorChooser.removeChooserPanel(colorTabs[i]); //this removes all but the default color swatch tab
            }
        }
    }




}
