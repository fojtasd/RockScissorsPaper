import javax.swing.JFrame;
import java.awt.*;


public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("RockScissorsPaper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MovingObjectsPanel movingObjectsPanel = new MovingObjectsPanel();
        movingObjectsPanel.setPreferredSize(new Dimension(800, 600));
        frame.getContentPane().add(movingObjectsPanel);
        frame.pack();
        frame.setVisible(true);
    }
}


