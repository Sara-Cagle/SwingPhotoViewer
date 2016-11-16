package components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by saracagle on 11/15/16.
 */
public class StatusModal {
    private String status;

    public StatusModal(String status){
        super();
        System.out.println("I've created the modal");
        this.status = status;
        //this.setPreferredSize(new Dimension(300, 100));
        //this.setSize(new Dimension(300, 100));
        //repaint();

    }

    public void draw(Graphics g){
        System.out.println("I've redrawn the modal");
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.fillRect(0, 0, 300, 100);
        g2.setColor(Color.red);
        g2.drawRect(0, 0, 300, 100);
        g2.drawString(status, 10, 10);

    }
}
