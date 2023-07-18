import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingObjectsPanel extends JPanel implements ActionListener {
    public MovingObjectsPanel(int numberOfObjects, int width, int height) throws Exception {
        setPreferredSize(new Dimension(width, height));
        objectsCreator(numberOfObjects);


        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();
    }

    private void objectsCreator(int numberOfObjects) throws Exception {
        for (int i = 0; i < numberOfObjects + 1; i++) {
            new Entity(Tools.generateRandomNumberInRange(30, 770),
                    Tools.generateRandomNumberInRange(30, 770),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 3),
                    Entity.Type.scissors, this);
            new Entity(Tools.generateRandomNumberInRange(30, 770),
                    Tools.generateRandomNumberInRange(30, 770),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 3),
                    Entity.Type.rock, this);
            new Entity(Tools.generateRandomNumberInRange(30, 770),
                    Tools.generateRandomNumberInRange(30, 770),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 2),
                    Tools.generateNegativeOrPositiveRandomNumberInRange(1, 3),
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Entity.checkCollision();
        for (Entity entity : Entity.entities) {
            if (entity == null) {
                break;
            }
            entity.updateObjectPosition();
        }
        repaint();
    }
}