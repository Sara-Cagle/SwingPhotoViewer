package panels;

import javax.swing.*;
import java.awt.*;

/**
 * LeftToolPanel
 *
 * A JPanel used on the left hand side of the screen.
 * Contains all the other panels that exist on the left hand side of the screen.
 *
 * @Author Sara Cagle
 * @Date 9/13/2016
 */
public class LeftToolPanel extends JPanel{
    private AnnotationPanel annotationPanel;
    private PaginationPanel paginationPanel;
    private ColorPanel colorPanel;
    private TagPanel tagPanel;
    private JPanel centerPanel;
    private ThumbnailSizePanel thumbnailSizePanel;


    /**
     * LeftToolPanel constructor
     *
     * Adds and arranges any smaller panels to itself.
     */
    public LeftToolPanel(){
        super();
        this.setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        tagPanel = new TagPanel();
        annotationPanel = new AnnotationPanel();
        paginationPanel = new PaginationPanel();
        colorPanel = new ColorPanel();
        thumbnailSizePanel = new ThumbnailSizePanel();
        centerPanel.add(tagPanel);
        centerPanel.add(annotationPanel);
        centerPanel.add(colorPanel);
        centerPanel.add(thumbnailSizePanel);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(paginationPanel, BorderLayout.PAGE_END);
    }

}
