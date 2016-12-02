package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.AnnotationMode;
import constants.ViewMode;
import panels.MagnetPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saracagle on 11/27/16.
 */
public class MagnetBoard extends JPanel implements IMessageListener, IThumbnailListener {
    private List<Magnet> activeMagnets;
    private List<Photo> photos;
    private int thumbnailSize;

    public MagnetBoard(){
        this.setLayout(new BorderLayout());
        photos = new ArrayList<>();
        activeMagnets = new ArrayList<>();
        this.thumbnailSize = 100;
        Bus.getInstance().registerListener(this);
        Magnet m = new Magnet("Winter");
        m.setPoint(50,50);
        activeMagnets.add(m);
    }

    public void updateView(){
        this.removeAll();
        JPanel thumbnailPanel = new JPanel();
        int locationX = 0;
        int locationY = 0;
        int rowCounter = 1;
        Dimension size;

        thumbnailPanel.setLayout(null);
        for(Magnet moo: activeMagnets){
            moo.setBounds(moo.getPoint().x, moo.getPoint().y, 100, 100);
            thumbnailPanel.add(moo);
        }
        for (Photo photo : photos) {
            Thumbnail thumbnail = new Thumbnail(photo, false, this, thumbnailSize);
            size = thumbnail.getPreferredSize();
            thumbnailPanel.add(thumbnail);
            thumbnail.setBounds(locationX, locationY, size.width, size.height);
            rowCounter++;
            if(rowCounter > 3){
                rowCounter = 1;
                locationX = 0;
                locationY+= thumbnailSize+10;
            }
            else{
                locationX += thumbnailSize+10;
            }
        }
        thumbnailPanel.setPreferredSize(new Dimension((thumbnailSize+10)*3, (thumbnailSize+10)*photos.size()/3));
        JScrollPane parentScrollPane = new JScrollPane(thumbnailPanel);
        this.add(parentScrollPane, BorderLayout.CENTER);
        repaint();
        revalidate();
    }



    /**
     * onThumbnailClick
     *
     * This method is called when the thumbnail is single clicked.
     * Will highlight the thumbnail in question.
     *
     * @param thumbnail the thumbnail in question
     */
    public void onThumbnailClick(Thumbnail thumbnail) {
        /*currentPhoto = thumbnail.getPhoto();
        updateView();*/
    }

    /**
     * onThumbnailDoubleClick
     *
     * This method is called when the thumbnail is double clicked.
     * Should swap the view to photo mode.
     *
     * @param thumbnail the thumbnail in question
     */
    public void onThumbnailDoubleClick(Thumbnail thumbnail) {
        /*currentPhoto = thumbnail.getPhoto();
        Bus.getInstance().sendMessage(new ViewModeMessage(ViewMode.Photo));*/
    }

    public void receiveMessage(Message m) {
        switch (m.type()) {
            case "image_message":
                ImageMessage imageMessage = (ImageMessage) m;
                try {
                    BufferedImage image = ImageIO.read(imageMessage.file);
                    Photo photo = new Photo(image);
                    this.photos.add(photo);
                    updateView();
                }
                catch (IOException e) {
                    //handle it
                }
                break;
            case "thumbnail_size_message":
                ThumbnailSizeMessage thumbnailSizeMessage = (ThumbnailSizeMessage) m;
                thumbnailSize = thumbnailSizeMessage.size;
                updateView();
                break;
        }
    }
}
