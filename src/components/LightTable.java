package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.Colors;
import constants.ViewMode;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saracagle on 10/23/16.
 */
public class LightTable extends JPanel implements IMessageListener, IThumbnailListener{
    private ArrayList<Photo> photos;
    private ArrayList<ThumbnailComponent> thumbnails;
    private ViewMode mode;
    private Photo currentPhoto;

    public LightTable(){
        this.setLayout(new BorderLayout());
        this.photos = new ArrayList<Photo>();
        this.currentPhoto = null;
        this.thumbnails = new ArrayList<ThumbnailComponent>();
        Bus.getInstance().registerListener(this);
        mode = ViewMode.Photo;
        updateView();
    }

    public void updateView() {
        this.removeAll(); //clear the component
        switch(mode){
            case Photo:
                drawPhotoMode();
                break;
            case Grid:
                drawGridMode();
                break;
            case Split:
                drawSplitMode();
                break;
        }
        validate();
        repaint();
    }

    public void drawPhotoMode(){

    }

    public void drawGridMode(){
        JPanel thumbnailPanel = new JPanel();
        thumbnailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (ThumbnailComponent thumbnailComponent : thumbnails) {
            thumbnailPanel.add(thumbnailComponent);
        }
        this.add(thumbnailPanel, BorderLayout.CENTER);
    }


    public void drawSplitMode(){
        PhotoComponent photoComponent = new PhotoComponent(currentPhoto);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(photoComponent);

        JPanel thumbnailPanel = new JPanel();
        thumbnailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (ThumbnailComponent thumbnailComponent : thumbnails) {
            thumbnailPanel.add(thumbnailComponent);
        }
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(thumbnailPanel, BorderLayout.SOUTH);
    }

    @Override
    public void onThumbnailClick(ThumbnailComponent thumbnail) {
        System.out.println("Single click");
    }

    @Override
    public void onThumbnailDoubleClick(ThumbnailComponent thumbnail) {
        System.out.println("Double click");
        currentPhoto = thumbnail.getPhoto();
        updateView();
    }

    @Override
    public void receiveMessage(Message m) {
        switch(m.type()) {
            case "image_message":
                ImageMessage imageMessage = (ImageMessage) m;
                try {
                    BufferedImage image = ImageIO.read(imageMessage.file);
                    Photo photo = new Photo(image);
                    currentPhoto = photo;
                    this.photos.add(photo);
                    this.thumbnails.add(new ThumbnailComponent(photo, this));
                    Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                    updateView();
                }
                catch (IOException e) {
                    //handle it
                }
                break;
            case "view_mode_message":
                ViewModeMessage modeMessage = (ViewModeMessage) m;
                mode = modeMessage.mode;
                System.out.println("Mode set to: "+mode);
                updateView();
            case "delete_image_message":
                Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                repaint();
                break;
        }
    }


}
