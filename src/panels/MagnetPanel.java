package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.AnnotationMode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by saracagle on 11/27/16.
 */
public class MagnetPanel extends JPanel implements IMessageListener{
    private TitledBorder title;
    private JRadioButton off;
    private JRadioButton on;
    private ButtonGroup magnetButtonGroup;
    private MagnetTagPanel magnetTagPanel;

    public MagnetPanel() {
        super();
        this.setMaximumSize(new Dimension(800, 130));
        this.setLayout(new BorderLayout());
        Bus.getInstance().registerListener(this);

        title = new TitledBorder("Magnet Mode");
        title.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(title);


        off = new JRadioButton("Off", true);
        on = new JRadioButton("On");

        magnetButtonGroup = new ButtonGroup();

        magnetButtonGroup.add(off);
        off.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetOffMessage());
        });

        magnetButtonGroup.add(on);
        on.addActionListener(e -> {
            Bus.getInstance().sendMessage(new MagnetOnMessage());
        });

        JPanel holderPanel = new JPanel();
        holderPanel.add(off);
        holderPanel.add(on);
        this.add(holderPanel, BorderLayout.CENTER);

        /*this.add(off, BorderLayout.LINE_START);
        this.add(on, BorderLayout.LINE_END);*/
    }

    public void receiveMessage(Message m){
        switch(m.type()) {
            case "magnet_off_message":
                if(magnetTagPanel != null){
                    this.remove(magnetTagPanel);
                    repaint();
                }
                break;
            case "magnet_on_message":
                magnetTagPanel = new MagnetTagPanel();
                this.add(magnetTagPanel, BorderLayout.SOUTH);
                break;
        }
    }

}
