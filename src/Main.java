import javax.swing.JFrame;

public class Main {
    /**
     * Enter desire number of rock, scissors, paper. Entities are in the same ratio.
     */
    public static int numberOfObjects = 50;

    /**
     * Enter desire size of canvas.
     */
    public static int widthOfCanvas = 900;
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


