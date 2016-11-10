import components.MyGlassPane;
import panels.ContentPanel;
import panels.LeftToolPanel;
import panels.StatusPanel;
import panels.TopMenu;
import java.awt.*;
import javax.swing.*;


/**
 * Main
 *
 * The main method, driver class for the application.
 * Creates the JFrame that powers the GUI.
 *
 * @Author Sara Cagle
 * @Date 8/25/2016
 */
public class Main {

    private JFrame mainFrame;

    /**
     * makeWindow
     *
     * Creates the JFrame for the GUI and adds its panels.
     */
    private void makeWindow(){
        mainFrame = new JFrame("Sara Cagle's Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,580);
        mainFrame.setMinimumSize(new Dimension(740, 480));
        mainFrame.setLayout(new BorderLayout());
        ContentPanel contentPanel = new ContentPanel();

        mainFrame.add(new TopMenu(), BorderLayout.PAGE_START);
        mainFrame.add(new LeftToolPanel(), BorderLayout.LINE_START);
        mainFrame.add(new ContentPanel(), BorderLayout.CENTER);
        mainFrame.add(new StatusPanel(), BorderLayout.PAGE_END);
        MyGlassPane myGlassPane = new MyGlassPane(mainFrame.getContentPane());
        mainFrame.setGlassPane(myGlassPane);
        mainFrame.getGlassPane().setVisible(true);

        mainFrame.setVisible(true);
    }

    /**
     * main driver method
     */
    public static void main(String[] args){
        System.out.println("Building window...");
        Main window = new Main();
        window.makeWindow();
    }


}

