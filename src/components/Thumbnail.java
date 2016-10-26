package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Thumbnail
 *
 * Thumbnail extends JComponent, is a smaller, scaled down version of a Photo.
 *
 *
 * @Author Sara Cagle
 * @Date 10/23/2016
 */
public class Thumbnail extends JComponent {
    private Photo photo;
    private IThumbnailListener listener;
    private boolean selected;
    private double scaleX;
    private double scaleY;

    public Thumbnail(Photo photo, boolean selected, IThumbnailListener listener) {
        this.photo = photo;
        this.selected = selected;
        this.listener = listener;
        scaleImage();
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){ //change to photo mode
                    Thumbnail.this.listener.onThumbnailDoubleClick(Thumbnail.this);
                }
                else if(e.getClickCount()==1){ //unsure if need this //highlight photo
                    Thumbnail.this.listener.onThumbnailClick(Thumbnail.this);
                }
                super.mouseClicked(e);
            }
        });
    }

    /**
     * photoExists
     *
     * Does a sanity check to ensure the photo hosted in Thumbnail exists.
     *
     * @return boolean, the photo exists or does not
     */
    private boolean photoExists(){
        return (photo != null) && (photo.getImage() != null);
    }

    /**
     * getPhoto
     *
     * Gets the photo held by Thumbnail.
     *
     * @return photo
     */
    public Photo getPhoto(){
        return photo;
    }

    /**
     * scaleImage
     *
     * Sets the scaling factor for the image.
     * Default is to try and get the image to be 100x100 or less.
     * The image ratio will stay intact.
     */
    private void scaleImage(){
        if(photoExists()) {
            BufferedImage image = photo.getImage();
            int w = image.getWidth();
            int h = image.getHeight();
            if(image.getWidth()<100 && image.getHeight() < 100){ //small enough, don't scale.
                scaleX = 1.0;
                scaleY = 1.0;
            }
            else{ //aiming for 100x100 while remaining in ratio
                double x = w;
                double y = h;
                if(w>h){
                    System.out.println("The image was longer than it was wide");
                    while(x > 100){
                        x--;
                        y = x*(y/(x+1));
                    }
                    scaleX = x/w;
                    scaleY = y/h;
                }
                else{
                    System.out.println("The image was taller than it was wide");
                    while(y > 100){
                        y--;
                        x = y*(x/(y+1));
                    }
                    scaleX = x/w;
                    scaleY = y/h;
                }
            }

            //this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight())); //this will mess up the split view grid
            this.setPreferredSize(new Dimension(200, 200));
        }
    }

    /**
     * paintComponent
     *
     * Paint method for the thumbnail. Draws the thumbnail and highlights it if selected.
     *
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if(photoExists()) {
            BufferedImage image = photo.getImage();
            //g2.scale(scaleX, scaleY); //scales the existing preferred size
            g2.scale(0.5,0.5);
            g2.setStroke(new BasicStroke(10));
            g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            if(selected){
                g2.setColor(Color.red);
                g2.drawRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
}
