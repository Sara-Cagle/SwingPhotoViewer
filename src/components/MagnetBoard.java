package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MagnetBoard
 *
 * The board that sits in place of LightTable.
 * Can apply magnets and maneuver photos.
 *
 * @Author Sara Cagle
 * @Date 11/27/2016
 */
public class MagnetBoard extends JPanel implements IMessageListener, IThumbnailListener, IMagnetListener {
    private List<Magnet> activeMagnets;
    private List<Photo> photos;
    private int thumbnailSize;
   //private Photo currentPhoto;
    private ConcurrentHashMap<Photo, Point> photoToPoint;
    private Timer timer;
    private ConcurrentHashMap<Photo, Point> animationDelta;
    private ArrayList<Photo> currentPhotos;
    private ConcurrentHashMap<Photo, Point> startingPoints;
    private final int DIAMETER = 50;
    private final int ROUND = 20;

    public MagnetBoard(List<Photo> photos){
        this.setLayout(new BorderLayout());
        this.photos = photos;
        activeMagnets = new ArrayList<>();
        this.thumbnailSize = 100;
        Bus.getInstance().registerListener(this);
        //currentPhoto = null;
        photoToPoint = new ConcurrentHashMap<>();
        animationDelta = new ConcurrentHashMap<>();
        timer = new Timer(5, e -> doAnimate());
        currentPhotos = new ArrayList<>();
    }

    /**
     * updateView
     *
     * Redraws all of the photos and magnets on the screen whenever they change.
     */
    public void updateView(){
        this.removeAll();
        JPanel thumbnailPanel = new JPanel();
        int locationX = 0;
        int locationY = 0;
        int rowCounter = 1;
        Dimension size;

        thumbnailPanel.setLayout(null);
        for(Magnet mag: activeMagnets){
            mag.setBounds(mag.getPoint().x, mag.getPoint().y, DIAMETER+ROUND, DIAMETER);
            thumbnailPanel.add(mag);
        }
        for (Photo photo : photos) {
            Thumbnail thumbnail = new Thumbnail(photo, currentPhotos.contains(photo), this, thumbnailSize);
            size = thumbnail.getPreferredSize();
            thumbnailPanel.add(thumbnail);
            if(photoToPoint.containsKey(photo)){
                //System.out.println("found a photo but its already in the photoToPoint");
                locationX = photoToPoint.get(photo).x;
                locationY = photoToPoint.get(photo).y;
            }
            else {
                System.out.println("found a photo putting it in normal");
                System.out.println("Location: "+locationX+" , "+locationY);
                System.out.println("Rowcounter before the stuff: "+rowCounter);
                rowCounter++;

                if (rowCounter > 3) {
                    rowCounter = 1;
                    locationX = 0;
                    locationY += thumbnailSize + 10;
                } else {
                    locationX += thumbnailSize + 10;
                }
                rowCounter++;
                System.out.println("Rowcounter after the stuff: "+rowCounter);
                photoToPoint.put(photo, new Point(locationX, locationY));
            }
            thumbnail.setBounds(locationX, locationY, size.width, size.height);
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

    /**
     * animateThumbnails
     *
     * For each photo, collect the magnets that it will interact with.
     * Compile the new locations these photos should move to in relation to the magnets.
     * Start the animation, call doAnimate.
     */
    public void animateThumbnails(){
        for(Photo photo: photos){
            Point finalLocation = photoToPoint.get(photo); //upper left corner
            ArrayList<Point> magnetAttractionPoints = new ArrayList<>();
            for(Magnet mag: activeMagnets){
                if (photo.hasTag(mag.getTag())){
                    Point magPoint = mag.getPoint(); //upper left corner
                    magnetAttractionPoints.add(magPoint);

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
            if(finalLocation != photoToPoint.get(photo)){ //if the photo isn't at the new location
                animationDelta.put(photo, finalLocation);
                currentPhotos.add(photo);
//                if(!startingPoints.containsKey(photo)){
//                    startingPoints.put(photo, photoToPoint.get(photo));
//                }
            }
        }
        timer.start();
    }

    /**
     * onMagnetLocationUpdated
     *
     * Allows us to communicate with the magnet as it moves.
     * Animates the thumbnails as we move magnets.
     */
    public void onMagnetLocationUpdated(){
        animateThumbnails();
    }

    /**
     * doAnimate
     *
     * Moves the photos toward newly found final location posts.
     * Increments x and y to shift the photo to its new place.
     * Shifting stops when the top left hand corners match
     */
    public void doAnimate(){
        List<Photo> toRemove = new ArrayList<>();
        if(animationDelta.isEmpty()){
            timer.stop();
            return;
        }
        for(Photo p: animationDelta.keySet()){
            Point currPoint = photoToPoint.get(p);
            Point finalPoint = animationDelta.get(p);
            Point startPoint = currPoint;
            /*if(startingPoints.contains(p)){
                startPoint = startingPoints.get(p);
            }*/
            if(!currPoint.equals(finalPoint)){

                if((Math.abs(currPoint.x - finalPoint.x) > 30 || Math.abs(currPoint.y - finalPoint.y) > 30) || //far from magnet
                        (Math.abs(startPoint.x - currPoint.x) > 30 || Math.abs(startPoint.y - currPoint.y) > 30)){ //far from start
                    timer.setDelay(5); //far away, moves fast
                }
                else{
                    timer.setDelay(15); //close, moves slow
                }
                if(currPoint.x < finalPoint.x){
                    currPoint.x++;
                }
                else if(currPoint.x > finalPoint.x){
                    currPoint.x--;
                }
                if(currPoint.y < finalPoint.y){
                    currPoint.y++;
                }
                else if(currPoint.y > finalPoint.y){
                    currPoint.y--;
                }
            }
            else{
                toRemove.add(p);
                currentPhotos.remove(p);
                //startingPoints.remove(p);
            }
        }
        for(Photo p: toRemove){
            animationDelta.remove(p);
        }
        updateView();
    }


    /**
     * receiveMessage
     *
     * Listens for messages coming in regarding thumbnails and magnets.
     *
     * @param m the message in question
     */
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
                            mag = new Magnet(tag, new Color(142, 255, 189), this, DIAMETER, ROUND);
                            mag.setPoint(50, 50);
                            break;
                        case 2:
                            mag = new Magnet(tag, new Color(255, 251, 48), this, DIAMETER, ROUND);
                            mag.setPoint(150, 50);
                            break;
                        case 3:
                            mag = new Magnet(tag, new Color(239, 99, 47), this, DIAMETER, ROUND);
                            mag.setPoint(50, 150);
                            break;
                        case 4:
                            mag = new Magnet(tag, new Color(119, 225, 255), this,DIAMETER, ROUND);
                            mag.setPoint(150, 150);
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
