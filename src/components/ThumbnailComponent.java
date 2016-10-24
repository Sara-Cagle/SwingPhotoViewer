package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by saracagle on 10/23/16.
 */
public class ThumbnailComponent extends JComponent {
    private Photo photo;
    private IThumbnailListener listener;

    public ThumbnailComponent(Photo photo, IThumbnailListener listener) {
        this.photo = photo;
        this.listener = listener;
        this.setPreferredSize(new Dimension(200, 200));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    ThumbnailComponent.this.listener.onThumbnailDoubleClick(ThumbnailComponent.this);
                }
                else if(e.getClickCount()==1){ //unsure if need this
                    ThumbnailComponent.this.listener.onThumbnailClick(ThumbnailComponent.this);
                }
                super.mouseClicked(e);
            }
        });
    }

    public Photo getPhoto(){
        return photo;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if(photo != null && photo.getImage() != null) { //centers the image
            BufferedImage image = photo.getImage();
            g2.scale(0.5, 0.5);
            g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}
