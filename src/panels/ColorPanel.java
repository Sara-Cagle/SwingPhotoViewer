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
 * Created by saracagle on 10/4/16.
 */
public class ColorPanel extends JPanel  implements IMessageListener{
    JColorChooser colorChooser;
    TitledBorder title;
    JButton lineColorButton;
    JButton boxColorButton;
    JPanel lineColorPanel;
    JPanel boxColorPanel;
    Color newColor;


    public ColorPanel(Color lineColor, Color boxColor){
        super();
        this.setMaximumSize(new Dimension(800, 75));
        //this.setLayout(new BorderLayout());
        title = new TitledBorder("Color Selection");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);
        newColor = null;
        //cleanUpColorSwatches();


        // set up the color button
        lineColorButton = new JButton("Select Pen Color");
        lineColorButton.addActionListener((e -> {
            colorChooser = new JColorChooser();
            //cleanUpColorSwatches();
            newColor = JColorChooser.showDialog(this, "Choose a color", Color.black);
            if (newColor != null) {
                Bus.getInstance().sendMessage(new ChangeColorMessage(newColor, Colors.Line));
            }
        }));
        boxColorButton = new JButton("Select Textbox Color");
        boxColorButton.addActionListener((e -> {
            newColor = JColorChooser.showDialog(this, "Choose a color", Color.yellow);
            if (newColor != null) {
                System.out.println("Got the new color "+newColor);
                Bus.getInstance().sendMessage(new ChangeColorMessage(newColor, Colors.Box));
            }
        }));

        // set up the preview pane
        lineColorPanel = new JPanel();
        lineColorPanel.setBackground(lineColor);
        boxColorPanel = new JPanel();
        boxColorPanel.setBackground(boxColor);
        this.add(boxColorButton);
        this.add(boxColorPanel);
        this.add(lineColorButton);
        this.add(lineColorPanel);

        Bus.getInstance().registerListener(this);

    }

    public void receiveMessage(Message m) {
        switch(m.type()) {
            case "change_color_message":
                ChangeColorMessage colorMessage = (ChangeColorMessage) m;
                if(colorMessage.objectType == Colors.Line){
                    this.lineColorPanel.setBackground(colorMessage.color);
                }
                else {
                    this.boxColorPanel.setBackground(colorMessage.color);
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
                /*JPanel swatches = (JPanel) colorTabs[i].getComponent(0); //these will remove "recent:" an the recent swatches
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
