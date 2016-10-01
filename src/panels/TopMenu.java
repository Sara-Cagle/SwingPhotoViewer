package panels;

import bus.Bus;
import bus.messages.DeleteImageMessage;
import bus.messages.StatusMessage;
import bus.messages.ImageMessage;

import javax.swing.*;
import java.io.File;


/**
 * TopMenu
 *
 * The menu bar that runs along the top of the application.
 * Allows users to import images, delete images, exit the app, and change views.
 *
 * @Author Sara Cagle
 * @Data 9/13/2016
 */
public class TopMenu extends JMenuBar{

    private JMenu file;
    private JMenuItem importItem;
    private JMenuItem deleteItem;
    private JMenuItem exitItem;
    private JMenu view;
    private ButtonGroup viewRadioButtonGroup;
    private JRadioButtonMenuItem photoViewRadioItem;
    private JRadioButtonMenuItem gridViewRadioItem;
    private JRadioButtonMenuItem splitViewRadioItem;

    /**
     * TopMenu constructor
     *
     * Creates the items within the menu.
     * Each item sends a new status to the bus for the status bar.
     */
    public TopMenu(){
        super();

        file = new JMenu("File");

        importItem = new JMenuItem("Import");
        importItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage("Importing photo..."));
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(fileChooser);
            File file = fileChooser.getSelectedFile();
            System.out.println(file);
            //pick only images here
            /*
             public boolean accept(File f) {
       if (f.isDirectory()) {
           return true;
       } else {
           String filename = f.getName().toLowerCase();
           return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
       }
   }
             */
            Bus.getInstance().sendMessage(new ImageMessage(file));
        });

        deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage("Deleting photo..."));
            Bus.getInstance().sendMessage(new DeleteImageMessage());
        });

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage("Exiting program..."));
            System.exit(0);
        });

        file.add(importItem);
        file.add(deleteItem);
        file.add(exitItem);
        this.add(file);

        view = new JMenu("View");

        viewRadioButtonGroup = new ButtonGroup();
        photoViewRadioItem = new JRadioButtonMenuItem("Photo View", true);
        photoViewRadioItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(photoViewRadioItem.getText()+" activated"));
        });

        gridViewRadioItem = new JRadioButtonMenuItem("Grid View");
        gridViewRadioItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(gridViewRadioItem.getText()+" activated"));
        });

        splitViewRadioItem = new JRadioButtonMenuItem("Split View");
        splitViewRadioItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(splitViewRadioItem.getText()+" activated."));
        });

        viewRadioButtonGroup.add(photoViewRadioItem);
        viewRadioButtonGroup.add(gridViewRadioItem);
        viewRadioButtonGroup.add(splitViewRadioItem);
        view.add(photoViewRadioItem);
        view.add(gridViewRadioItem);
        view.add(splitViewRadioItem);
        this.add(view);
    }
}
