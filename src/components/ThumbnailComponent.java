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
    private final double SCALEX = 0.5;
    private final double SCALEY = 0.5;

    public ThumbnailComponent(Photo photo, IThumbnailListener listener) {
        System.out.println("I'm making a new thumbnail!!");
        this.photo = photo;
        this.listener = listener;
        if(photoExists()) {
            BufferedImage image = photo.getImage();
            //this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            this.setPreferredSize(new Dimension(200, 200));
        }
        else { //shouldn't ever hit this
            this.setPreferredSize(new Dimension(100, 100)); //some default value
        }
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){ //change to photo mode
                    ThumbnailComponent.this.listener.onThumbnailDoubleClick(ThumbnailComponent.this);
                }
                else if(e.getClickCount()==1){ //unsure if need this //highlight photo
                    ThumbnailComponent.this.listener.onThumbnailClick(ThumbnailComponent.this);
                }
                super.mouseClicked(e);
            }
        });
    }

    /**
     * photoExists
     *
     * Does a sanity check to ensure the photo hosted in ThumbnailComponent exists.
     *
     * @return boolean, the photo exists or does not
     */
    private boolean photoExists(){
        return (photo != null) && (photo.getImage() != null);
    }

    /**
     * getPhoto
     *
     * Gets the photo held by ThumbnailComponent.
     *
     * @return photo
     */
    public Photo getPhoto(){
        return photo;
    }

    /**
     * getThumbnailImageWidth
     *
     * Calculates the width of the thumbnail based on scaling factor.
     *
     * @return width of thumbnail, -1 if error
     */
    public int getThumbnailImageWidth(){
        if(photoExists()){
            BufferedImage image = photo.getImage();
            return (int)(image.getWidth() * SCALEX);
        }
        return -1;
    }

    /**
     * getThumbnailImageHeight
     *
     * Calculates the height of the thumbnail based on scaling factor.
     *
     * @return height of thumbnail, -1 if error
     */
    public int getThumbnailImageHeight(){
        if(photoExists()){
            BufferedImage image = photo.getImage();
            return (int)(image.getHeight() * SCALEY);
        }
        return -1;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if(photoExists()) {
            BufferedImage image = photo.getImage();
            g2.scale(SCALEX, SCALEY); //scales the existing preferred size
            g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}
