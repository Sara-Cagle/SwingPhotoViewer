package panels;
import bus.Bus;
import bus.messages.StatusMessage;

import javax.swing.*;

/**
 * PaginationPanel
 *
 * JPanel responsible for holding information on pagination
 *
 * @Author Sara Cagle
 * @Date 9/13/2016
 */
public class PaginationPanel extends JPanel{
    private JButton left;
    private JButton right;

    /**
     * PaginationPanel constructor
     *
     * Buttons for moving forward and backward,
     * sends status messages to the bus when clicked.
     */
    public PaginationPanel(){
        super();

        left = new JButton("<- Backward");
        left.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage("Moved backward"));
        });

        right = new JButton("Forward ->");
        right.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage("Moved forward"));
        });

        this.add(left);
        this.add(right);
    }
}
