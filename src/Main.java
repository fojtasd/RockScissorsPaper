import javax.swing.JFrame;

public class Main {
    public static int numberOfObjects = 1;

    /**
     * Enter desired size of canvas.
     */
    public static int widthOfCanvas = 1200;
    public static int heightOfCanvas = 900;

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("RockScissorsPaper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MovingObjectsPanel movingObjectsPanel = new MovingObjectsPanel(numberOfObjects, widthOfCanvas, heightOfCanvas);
        frame.getContentPane().add(movingObjectsPanel);
        frame.pack();
        frame.setVisible(true);
    }
}


