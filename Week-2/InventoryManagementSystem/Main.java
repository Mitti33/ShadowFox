import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        
        // setting os's native theme
        // by native look and feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            System.err.println("Could not apply system look-and-feel: " + e.getMessage());
        }

        // scheduling everything to run on the same thread
        // Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            Mainframe frame = new Mainframe();
            frame.setVisible(true);
        });
    }
}