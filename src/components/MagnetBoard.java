package components;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;

import javax.swing.*;
import java.awt.*;
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
    private ConcurrentHashMap<Photo, Point> photoToPoint;
    private Timer timer;
    private ConcurrentHashMap<Photo, Point> animationDelta;
    private ConcurrentHashMap<Photo, Point> startingPoints;
    private final int DIAMETER = 50;
    private final int ROUND = 20;

    /**
     * MagnetBoard
     *
     * MagnetBoard constructor, instantiates all of the hashmaps of photos to points.
     * Creates the timer, starting with the "fast" speed.
     *
     * @param photos
     */
    public MagnetBoard(List<Photo> photos){
        this.setLayout(new BorderLayout());
        this.photos = photos;
        startingPoints = new ConcurrentHashMap<>();
        activeMagnets = new ArrayList<>();
        this.thumbnailSize = 100;
        Bus.getInstance().registerListener(this);
        photoToPoint = new ConcurrentHashMap<>();
        animationDelta = new ConcurrentHashMap<>();
        timer = new Timer(5, e -> doAnimate());
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
        Dimension size;

        thumbnailPanel.setLayout(null);
        for(Magnet mag: activeMagnets){
            mag.setBounds(mag.getPoint().x, mag.getPoint().y, DIAMETER+ROUND, DIAMETER);
            thumbnailPanel.add(mag);
        }
        for (Photo photo : photos) {
            Thumbnail thumbnail = new Thumbnail(photo, animationDelta.containsKey(photo), this, thumbnailSize);
            size = thumbnail.getPreferredSize();
            thumbnailPanel.add(thumbnail);
            if(photoToPoint.containsKey(photo)){
                locationX = photoToPoint.get(photo).x;
                locationY = photoToPoint.get(photo).y;
            }
            photoToPoint.put(photo, new Point(locationX, locationY));
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
     * Required for IThumbnailListener, but not being used in this class.
     *
     * @param thumbnail the thumbnail in question
     */
    public void onThumbnailClick(Thumbnail thumbnail) {}

    /**
     * onThumbnailDoubleClick
     *
     * Required for IThumbnailListener, but not being used in this class.
     *
     * @param thumbnail the thumbnail in question
     */
    public void onThumbnailDoubleClick(Thumbnail thumbnail) {}

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
                if(!startingPoints.containsKey(photo)){
                    startingPoints.put(photo, photoToPoint.get(photo));
                }
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
            Point startPoint = startingPoints.get(p);
            if(!currPoint.equals(finalPoint)){
                if((Math.abs(currPoint.x - finalPoint.x) > 30 || Math.abs(currPoint.y - finalPoint.y) > 30) && //far from final point
                        (Math.abs(startPoint.x - currPoint.x) > 15 || Math.abs(startPoint.y - currPoint.y) > 15)){ //far from start
                    timer.setDelay(5); //far away, moves fast
                }
                else{
                    timer.setDelay(15); //close to either end, moves slow
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
                startingPoints.remove(p);
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
                            mag.setPoint(150, 250);
                            break;
                        case 2:
                            mag = new Magnet(tag, new Color(255, 251, 48), this, DIAMETER, ROUND);
                            mag.setPoint(250, 250);
                            break;
                        case 3:
                            mag = new Magnet(tag, new Color(239, 99, 47), this, DIAMETER, ROUND);
                            mag.setPoint(150, 350);
                            break;
                        case 4:
                            mag = new Magnet(tag, new Color(119, 225, 255), this,DIAMETER, ROUND);
                            mag.setPoint(250, 350);
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
