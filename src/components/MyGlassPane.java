package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @Author Sara Cagle
 * @date 11/9/2016.
 */
public class MyGlassPane extends JComponent {
    private Container contentPane;

    public MyGlassPane(Container contentPane) {
        this.contentPane = contentPane;
        System.out.println("instantiated glass pane");
        MouseAdapter mouseAdapter = new GlassPaneMouseAdapter();
        this.addMouseListener(mouseAdapter);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        /*g.drawRect(10,10,100,500);*/
        System.out.println("drawing glass pane");
        /*Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawBackground(g);
        if(!flipped){
            drawImage(g);
        }
        else{
            drawFlipped(g);
        }*/
        revalidate();
    }

    private class GlassPaneMouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                repaint();
            }


        }

        public void mousePressed(MouseEvent e){
            if(SwingUtilities.isRightMouseButton(e)){
                //start the gesture
            }
        }

        public void mouseDragged(MouseEvent e){
            if(SwingUtilities.isRightMouseButton(e)){

            }
            //compile e.getPoint() to the gesture list
            //repaint
        }

        public void mouseReleased(MouseEvent e){
            //run the gesture check
            //do the gesture
            //reset the gesture list
            //repaint?
        }
    }

}
