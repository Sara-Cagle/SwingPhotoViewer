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
 * @Author Sara Cagle
 * @Date 10/23/2016
 */
public class Thumbnail extends JComponent{
    private Photo photo;
    private IThumbnailListener listener;
    private boolean selected;
    private double scaleX;
    private double scaleY;
    private int thumbnailSize;

    public Thumbnail(Photo photo, boolean selected, IThumbnailListener listener, int thumbnailSize) {
        this.photo = photo;
        this.selected = selected;
        this.listener = listener;
        this.thumbnailSize = thumbnailSize;
        scaleImage();
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){ //change to photo mode
                    Thumbnail.this.listener.onThumbnailDoubleClick(Thumbnail.this);
                }
                else if(e.getClickCount()==1){ //highlight photo
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
            double x = w;
            double y = h;
            if(image.getWidth()<thumbnailSize && image.getHeight() < thumbnailSize){ //small enough, don't scale.
                scaleX = w;
                scaleY = h;
            }
            else{ //aiming for 100x100 while remaining in ratio
                if(w>h){
                    while(x > thumbnailSize){
                        x--;
                        y = x*(y/(x+1));
                    }
                    scaleX = x;
                    scaleY = y;
                }
                else{
                    while(y > thumbnailSize){
                        y--;
                        x = y*(x/(y+1));
                    }
                    scaleX = x;
                    scaleY = y;
                }
            }

            this.setPreferredSize(new Dimension(thumbnailSize, thumbnailSize+20)); //20 is for vertical padding
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
            int diffY = (thumbnailSize+20-(int)scaleY)/2;
            int diffX = (thumbnailSize-(int)scaleX)/2;
            int strokeSize = 2;
            if(thumbnailSize==100){
                strokeSize+=3;
            }
            if(thumbnailSize==200){
                strokeSize+=8;
            }
            g2.setStroke(new BasicStroke(strokeSize));
            g2.drawImage(image, diffX, diffY, (int)scaleX, (int)scaleY, null);
            if(selected){
                g2.setColor(Color.red);
                g2.drawRect(diffX, diffY, (int)scaleX, (int)scaleY);
            }
        }
    }
}
