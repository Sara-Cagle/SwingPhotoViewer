package panels;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

/**
 * Created by saracagle on 10/4/16.
 */
public class ColorPanel extends JPanel {
    JColorChooser colorChooser;
    TitledBorder title;


    public ColorPanel(){
        super();
        this.setMaximumSize(new Dimension(400, 800));
        this.setLayout(new BorderLayout());
        title = new TitledBorder("Color Selection");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);
        colorChooser = new JColorChooser(Color.black);
        cleanUpColorSwatches();
        this.add(BorderLayout.CENTER, colorChooser);
        //repaint();
    }
   /* public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawColors(g);
        revalidate();
    }

    public void drawColors(Graphics g){
        System.out.println("i've been called");
        g.setColor(Color.red);
        g.fillRect(this.getX(),this.getY(),this.getWidth(),20);
        g.setColor(Color.orange);
        g.fillRect(this.getX(),this.getY()+20,this.getWidth(),20);
        g.setColor(Color.yellow);
        g.fillRect(this.getX(),this.getY()+30,this.getWidth(),20);
    }*/

    private void cleanUpColorSwatches(){
        AbstractColorChooserPanel[] colorTabs = colorChooser.getChooserPanels();
        colorChooser.setPreviewPanel(new JPanel());
        for(int i=0; i<colorTabs.length; i++){
            System.out.println("Index number: "+i+" Name of thing: "+colorTabs[i].getClass().getName());
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
