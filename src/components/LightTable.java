package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.ViewMode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * LightTable
 *
 * The main component of the app that displays photos based on their modes.
 * Users can select thumbnails and annotate images, navigating between modes.
 *
 * @Author Sara Cagle
 * @Date 10/23/2016
 */
public class LightTable extends JPanel implements IMessageListener, IThumbnailListener{
    private ArrayList<Photo> photos;
    private ViewMode mode;
    private Photo currentPhoto;
    private int thumbnailSize;

    /**
     * LightTable constructor
     *
     * Sits in the ContentPanel, displays the photos based on the current view mode.
     * Has a current photo.
     */
    public LightTable(){
        this.setLayout(new BorderLayout());
        this.photos = new ArrayList<>();
        this.currentPhoto = null;
        this.thumbnailSize = 100;
        Bus.getInstance().registerListener(this);
        mode = ViewMode.Photo;
        updateView();

    }

    /**
     * updateView
     *
     * The updateView method draws the appropriate view based on the mode.
     */
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

    /**
     * drawGridMode
     *
     * Creates the Grid Mode View of the application.
     * Paints all of the existing thumbnails as a dynamic grid in the app.
     */
    public void drawGridMode(){
        JPanel thumbnailPanel = new JPanel();
        thumbnailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Photo photo : photos) {
            thumbnailPanel.add(new Thumbnail(photo, photo==currentPhoto, this, thumbnailSize));
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
        JPanel innerThumbnailPanel = new JPanel();
        innerThumbnailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Photo photo : photos) {
            innerThumbnailPanel.add(new Thumbnail(photo, photo==currentPhoto, this, thumbnailSize));
        }

        JScrollPane thumbnailScrollPane = new JScrollPane(innerThumbnailPanel);
        thumbnailScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        thumbnailScrollPane.getViewport().add(innerThumbnailPanel);

        this.add(photoComponentScrollPane, BorderLayout.CENTER);
        this.add(thumbnailScrollPane, BorderLayout.SOUTH);
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
        currentPhoto = thumbnail.getPhoto();
        updateView();
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
        currentPhoto = thumbnail.getPhoto();
        Bus.getInstance().sendMessage(new ViewModeMessage(ViewMode.Photo));
    }

    /**
     * receiveMessage
     *
     * Receives a message of a file that will be passed in from the Bus.
     * If the message is an ImageMessage, then the file will turned into a Photo object.
     * Other messages indicate deletion, view mode, and pagination.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m) {
        switch(m.type()) {
            case "image_message":
                ImageMessage imageMessage = (ImageMessage) m;
                try {
                    BufferedImage image = ImageIO.read(imageMessage.file);
                    Photo photo = new Photo(image);
                    currentPhoto = photo;
                    this.photos.add(photo);
                    Bus.getInstance().sendMessage(new AdjustAnnotationColorsMessage());
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
                break;
            case "thumbnail_size_message":
                ThumbnailSizeMessage thumbnailSizeMessage = (ThumbnailSizeMessage) m;
                thumbnailSize = thumbnailSizeMessage.size;
                updateView();
                break;
            case "delete_image_message":
                int currIndex = photos.indexOf(currentPhoto);
                if(currIndex+1 < photos.size() ){ //currentPhoto is not the end
                    photos.remove(currentPhoto);
                    currentPhoto = photos.get(currIndex);
                    Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                    updateView();
                    break;
                }
                if(currIndex-1 >= 0){ //currentPhoto is not the beginning
                    photos.remove(currentPhoto);
                    currentPhoto = photos.get(currIndex-1);
                    Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                    updateView();
                    break;
                }
                photos.remove(currentPhoto); //currentPhoto is the only photo
                currentPhoto = null;
                Bus.getInstance().sendMessage(new StatusMessage("Ready"));
                updateView();
                break;
            case "move_right_message":
                if(photos.indexOf(currentPhoto)+1 < photos.size()){
                    currentPhoto = photos.get(photos.indexOf(currentPhoto)+1);
                    updateView();
                }
                else{
                    if(!photos.get(0).equals(currentPhoto)){ //circular rotation
                        currentPhoto = photos.get(0);
                    }
                }
                break;
            case "move_left_message":
                if(photos.indexOf(currentPhoto)-1 > -1){
                    currentPhoto = photos.get(photos.indexOf(currentPhoto)-1);
                    updateView();
                }
                else{
                    if(!photos.get(photos.size()-1).equals(currentPhoto)){ //circular rotation
                        currentPhoto = photos.get(photos.size()-1);
                        updateView();
                    }
                }
                break;
        }
    }


}
