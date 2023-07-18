import javax.swing.JFrame;
import java.awt.*;


public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("RockScissorsPaper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MovingObjectsPanel movingObjectsPanel = new MovingObjectsPanel(50, 900, 900);
        frame.getContentPane().add(movingObjectsPanel);
        frame.pack();
        frame.setVisible(true);
    }
}


