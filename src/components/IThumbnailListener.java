package components;

/**
 * IThumbnailListener
 *
 * Interface for listening for clicks on thumbnails.
 *
 * @Author Sara Cagle
 * @Date 10/23/2016
 */
public interface IThumbnailListener {
    void onThumbnailClick(Thumbnail thumbnail);
    void onThumbnailDoubleClick(Thumbnail thumbnail);
}
