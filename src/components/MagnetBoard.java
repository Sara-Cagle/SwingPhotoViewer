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
import java.util.HashMap;
import java.util.List;

/**
 * Created by saracagle on 11/27/16.
 */
public class MagnetBoard extends JPanel implements IMessageListener, IThumbnailListener, IMagnetListener {
    private List<Magnet> activeMagnets;
    private List<Photo> photos;
    private int thumbnailSize;
    private Photo currentPhoto;
    private HashMap<Photo, Point> photoToPoint;

    public MagnetBoard(List<Photo> photos){
        this.setLayout(new BorderLayout());
        this.photos = photos;
        activeMagnets = new ArrayList<>();
        this.thumbnailSize = 100;
        Bus.getInstance().registerListener(this);
        currentPhoto = null;
        photoToPoint = new HashMap<>();
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
            Thumbnail thumbnail = new Thumbnail(photo, photo == currentPhoto, this, thumbnailSize);
            size = thumbnail.getPreferredSize();
            thumbnailPanel.add(thumbnail);
            if(photoToPoint.containsKey(photo)){
                thumbnail.setBounds(photoToPoint.get(photo).x, photoToPoint.get(photo).y, size.width, size.height);
            }
            else {
                thumbnail.setBounds(locationX, locationY, size.width, size.height);
                rowCounter++;
                if (rowCounter > 3) {
                    rowCounter = 1;
                    locationX = 0;
                    locationY += thumbnailSize + 10;
                } else {
                    locationX += thumbnailSize + 10;
                }
                photoToPoint.put(photo, new Point(locationX, locationY));
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

    public void animateThumbnails(){
        for(Photo photo: photos){
            Point finalLocation = photoToPoint.get(photo); //upper left corner
            ArrayList<Point> magnetAttractionPoints = new ArrayList<>();
            for(Magnet mag: activeMagnets){
                if (photo.hasTag(mag.getTag())){
                    Point magPoint = mag.getPoint(); //upper left corner
                    magnetAttractionPoints.add(magPoint);
                    currentPhoto = photo;
                    //light up photo draw rect around it
                    //move photo to magnet location
                    //get magnet location
                    //update photo location
                    //start slow, speed up, slow down again
                }
            }
            if(!magnetAttractionPoints.isEmpty()){
                int x = 0;
                int y = 0;
                for(Point p: magnetAttractionPoints){
                    x+=p.x;
                    y+=p.y;
                }
                finalLocation = new Point(x/magnetAttractionPoints.size(), y/magnetAttractionPoints.size());
            }
            photoToPoint.put(photo, finalLocation);

            //calculate midpoint of magnetattractionpoints
            //move photo to there
        }
        updateView();
    }

    public void onMagnetLocationUpdated(){
        animateThumbnails();
    }




    public void receiveMessage(Message m) {
        switch (m.type()) {
            case "thumbnail_size_message":
                ThumbnailSizeMessage thumbnailSizeMessage = (ThumbnailSizeMessage) m;
                thumbnailSize = thumbnailSizeMessage.size;
                updateView();
                break;
            case "magnet_message":
                MagnetMessage magnetMessage = (MagnetMessage) m;
                int tag = magnetMessage.tag;
                boolean contains = false;
                for(Magnet mag : activeMagnets){
                    if(mag.getTag() == tag){
                        activeMagnets.remove(mag);
                        contains = true;
                        break;
                    }
                }
                if(!contains){
                    Magnet mag = null;
                    switch(tag){
                        case 1:
                            mag = new Magnet(tag, Color.red, this);
                            mag.setPoint(50, 50);
                            break;
                        case 2:
                            mag = new Magnet(tag, Color.blue, this);
                            mag.setPoint(50, 50);
                            break;
                        case 3:
                            mag = new Magnet(tag, Color.green, this);
                            mag.setPoint(50, 50);
                            break;
                        case 4:
                            mag = new Magnet(tag, Color.yellow, this);
                            mag.setPoint(50, 50);
                            break;
                    }
                    activeMagnets.add(mag);
                }
                animateThumbnails();
                updateView();
                break;
        }
    }
}
