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
        objectsCreator(numberOfObjects);
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();
    }

    private void objectsCreator(int numberOfObjects) throws Exception {
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

        for (Entity entity : Entity.entities) {
            if (entity == null) {
                break;
            }
            g.drawImage(entity.image, entity.getCoordinateX(), entity.getCoordinateY(), this);
        }
        for (Entity entity : Entity.entities) {
            if (entity == null) {
                break;
            }
            if (entity.placeOfCollision == null){
                break;
            }
            System.out.println(entity.placeOfCollision);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity.checkCollisionsBetweenObjects();
        for (Entity entity : Entity.entities) {
            if (entity == null) {
                break;
            }
            entity.updateObjectPosition();
        }
        repaint();
    }
}