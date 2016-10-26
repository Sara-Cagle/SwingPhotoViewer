package panels;

import bus.Bus;
import bus.IMessageListener;
import bus.messages.*;
import constants.ViewMode;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;


/**
 * TopMenu
 *
 * The menu bar that runs along the top of the application.
 * Allows users to import images, delete images, exit the app, and change views.
 *
 * @Author Sara Cagle
 * @Data 9/13/2016
 */
public class TopMenu extends JMenuBar implements IMessageListener{

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

        Bus.getInstance().registerListener(this);

        file = new JMenu("File");

        importItem = new JMenuItem("Import");
        importItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage("Importing photo..."));

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            fileChooser.showOpenDialog(fileChooser);
            File file = fileChooser.getSelectedFile();
            if(file.isDirectory()){ //allows uploading of an entire folder
                for(File f: file.listFiles(pathname -> !pathname.isDirectory() && isImage(pathname))){
                    Bus.getInstance().sendMessage(new ImageMessage(f));
                }
            }
            else{
                if(isImage(file)){
                    Bus.getInstance().sendMessage(new ImageMessage(file));
                }
                else{
                    System.out.println("The file selected was not an image.");
                    Bus.getInstance().sendMessage(new StatusMessage("File was not an image | Ready"));
                }
            }


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
            Bus.getInstance().sendMessage(new ViewModeMessage(ViewMode.Photo));
        });

        gridViewRadioItem = new JRadioButtonMenuItem("Grid View");
        gridViewRadioItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(gridViewRadioItem.getText()+" activated"));
            Bus.getInstance().sendMessage(new ViewModeMessage(ViewMode.Grid));
        });

        splitViewRadioItem = new JRadioButtonMenuItem("Split View");
        splitViewRadioItem.addActionListener(e -> {
            Bus.getInstance().sendMessage(new StatusMessage(splitViewRadioItem.getText()+" activated."));
            Bus.getInstance().sendMessage(new ViewModeMessage(ViewMode.Split));
        });

        viewRadioButtonGroup.add(photoViewRadioItem);
        viewRadioButtonGroup.add(gridViewRadioItem);
        viewRadioButtonGroup.add(splitViewRadioItem);
        view.add(photoViewRadioItem);
        view.add(gridViewRadioItem);
        view.add(splitViewRadioItem);
        this.add(view);
    }

    /**
     * isImage
     *
     * Checks if the imported document is an image.
     * Only accept jpg, jpeg, gif, png
     *
     * @param file the uploaded file
     * @return boolean, it's am image or not
     */
    private boolean isImage(File file){
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
            || fileName.endsWith(".gif") || fileName.endsWith(".png");
    }

    /**
     * receiveMessage
     *
     * Receives a message passed in from the Bus.
     * Listening for the mode change. Only can be changed to Photo mode.
     *
     * @param m, a Message received from the bus.
     */
    public void receiveMessage(Message m) {
        switch(m.type()) {
            case "view_mode_message":
                ViewModeMessage modeMessage = (ViewModeMessage) m;
                if(modeMessage.mode == ViewMode.Photo){
                    photoViewRadioItem.setSelected(true);
                }
                break;
        }
    }
}
