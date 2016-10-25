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

    /**
     * drawPhotoMode
     *
     * Creates the Photo Mode View of the application.
     * The currently selected photo comes into the focus of the entire page in a scrollable pane.
     */
    public void drawPhotoMode(){
        PhotoComponent photoComponent = new PhotoComponent(currentPhoto);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(photoComponent);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void drawGridMode(){
        JPanel thumbnailPanel = new JPanel();
        thumbnailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (ThumbnailComponent thumbnailComponent : thumbnails) {
            thumbnailPanel.add(thumbnailComponent);
        }
        this.add(thumbnailPanel, BorderLayout.CENTER);
    }

    /**
     * drawSplitMode
     *
     * Draws the Split Mode View where the top half of the content area is the photo
     * and the bottom half is a horizontally scrollable strip of photos.
     * The top half consists of a PhotoComponent, displayed at BorderLayout Center.
     * The bottom thumbnail collection is a dynamically sized scrollpane based on an inner thumbnail panel.
     *
     */
    public void drawSplitMode(){
        PhotoComponent photoComponent = new PhotoComponent(currentPhoto);
        JScrollPane photoComponentScrollPane = new JScrollPane();
        photoComponentScrollPane.getViewport().add(photoComponent);

        JScrollPane thumbnailScrollPane = new JScrollPane(); //parent of the inner table

        JPanel innerThumbnailPanel = new JPanel(); //thumbnails actually go in here, this size determines the scroller
        innerThumbnailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        int thumbnailPanelWidth = 0;
        int thumbnailPanelHeight = 0;
        for (ThumbnailComponent thumbnailComponent : thumbnails) {
            thumbnailPanelWidth += thumbnailComponent.getThumbnailImageWidth();//thumbnailComponent.getPhoto().getImage().getWidth();//
            thumbnailPanelHeight = Math.max(thumbnailPanelHeight, thumbnailComponent.getThumbnailImageHeight());
            System.out.println("I'm adding the thumbnail to the split mode");
            innerThumbnailPanel.add(thumbnailComponent);
        }

        innerThumbnailPanel.setMinimumSize(new Dimension(thumbnailPanelWidth, thumbnailPanelHeight)); //currently have big huge gaps between images?
        innerThumbnailPanel.setPreferredSize(new Dimension(thumbnailPanelWidth, thumbnailPanelHeight));
        innerThumbnailPanel.setSize(new Dimension(thumbnailPanelWidth, thumbnailPanelHeight));
        thumbnailScrollPane.getViewport().add(innerThumbnailPanel);
        this.add(photoComponentScrollPane, BorderLayout.CENTER);
        this.add(thumbnailScrollPane, BorderLayout.SOUTH);
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
