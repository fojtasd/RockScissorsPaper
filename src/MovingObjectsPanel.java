import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingObjectsPanel extends JPanel implements ActionListener {
    int canvasWidth;
    int canvasHeight;
    int imageHeight;
    int imageWidth;

    public MovingObjectsPanel(int numberOfObjects, int canvasWidth, int canvasHeight) throws Exception {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.imageWidth = Entity.getImageSize()[0];
        this.imageHeight = Entity.getImageSize()[1];
        createEntities(numberOfObjects);
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();
    }

    private void createEntities(int numberOfObjects) throws Exception {
        for (int i = 0; i < numberOfObjects; i++) {
            new Entity(Tools.generateRandomNumberInRange(0, canvasWidth - imageWidth),
                    Tools.generateRandomNumberInRange(0, canvasHeight - imageHeight),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Entity.Type.scissors, this);
            new Entity(Tools.generateRandomNumberInRange(0, canvasWidth - imageWidth),
                    Tools.generateRandomNumberInRange(0, canvasHeight - imageHeight),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Entity.Type.rock, this);
            new Entity(Tools.generateRandomNumberInRange(0, canvasWidth - imageWidth),
                    Tools.generateRandomNumberInRange(0, canvasHeight - imageHeight),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Entity.Type.paper, this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear the panel
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        final EntityPool pool = EntityPool.getInstance();
        for (Entity entity : pool.getEntities()) {
            if (entity == null) {
                break;
            }
            g.drawImage(entity.getImage(), entity.getCoordinateX(), entity.getCoordinateY(), this);
        }

        if(pool.checkVictoryCondition()){
            FontMetrics fontMetrics = g.getFontMetrics();

            // Calculate the x-coordinate to center the text
            String textToWrite = pool.getEntities().get(0).getTypeOfObject().toString().toUpperCase() + " has won!";
            int textX = (canvasWidth - fontMetrics.stringWidth(textToWrite)) / 2;
            int textY = (canvasHeight - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 30));

            g.drawString(textToWrite, textX, textY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity.checkCollisionsBetweenObjects();
        for (Entity entity : EntityPool.getInstance().getEntities()) {
            if (entity == null) {
                break;
            }
            entity.updateObjectPosition();
        }
        repaint();
    }
}