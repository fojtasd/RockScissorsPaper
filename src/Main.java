import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Circles Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom JPanel subclass to handle the animation
        MovingObjectsPanel movingObjectsPanel = new MovingObjectsPanel();

        // Set the preferred size for the panel
        movingObjectsPanel.setPreferredSize(new Dimension(400, 400));

        // Add the panel to the frame
        frame.getContentPane().add(movingObjectsPanel);

        // Set the size of the frame and make it visible
        frame.pack();
        frame.setVisible(true);
    }
}


