package components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by saracagle on 11/15/16.
 */
public class StatusModal {
    private String status;
    private int startX;

    public StatusModal(String status, int startX){
        super();
        System.out.println("I've created the modal");
        this.status = status;
        this.startX = startX;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setX(int startX){
        this.startX = startX;
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.fillRect(15+startX, 25, 300, 40);
        g2.drawRect(15+startX, 25, 300, 40);
        g2.setColor(Color.black);
        g2.drawString(status, 15+startX+10, 40);

    }
}
