import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingObjectsPanel extends JPanel implements ActionListener {

    MovingObject scissors;
    MovingObject scissors2;
    MovingObject scissors3;
    MovingObject paper;
    MovingObject paper2;
    MovingObject paper3;
    MovingObject rock;
    MovingObject rock2;
    MovingObject rock3;

    public MovingObjectsPanel() throws Exception {
        objectsCreator();

        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();
    }

    /**
     * Creates objects
     */
    private void objectsCreator() throws Exception {
        scissors = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.scissors, this);
        scissors2 = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.scissors, this);
        scissors3 = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.scissors, this);
        paper = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.paper, this);
        paper2 = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.paper, this);
        paper3 = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.paper, this);
        rock = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.rock, this);
        rock2 = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.rock, this);
        rock3 = new MovingObject(Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(30, 370), Tools.generateRandomNumberInRange(1, 3), MovingObject.Type.rock, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear the panel
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the circles
        g.drawImage(scissors.image, scissors.getCoordinateX(), scissors.getCoordinateY(), this);
        g.drawImage(scissors2.image, scissors2.getCoordinateX(), scissors2.getCoordinateY(), this);
        g.drawImage(scissors3.image, scissors3.getCoordinateX(), scissors3.getCoordinateY(), this);

        g.drawImage(rock.image, rock.getCoordinateX(), rock.getCoordinateY(), this);
        g.drawImage(rock2.image, rock2.getCoordinateX(), rock2.getCoordinateY(), this);
        g.drawImage(rock3.image, rock3.getCoordinateX(), rock3.getCoordinateY(), this);

        g.drawImage(paper.image, paper.getCoordinateX(), paper.getCoordinateY(), this);
        g.drawImage(paper2.image, paper2.getCoordinateX(), paper2.getCoordinateY(), this);
        g.drawImage(paper3.image, paper3.getCoordinateX(), paper3.getCoordinateY(), this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MovingObject.checkCollision();
        scissors.updateObjectPosition();
        rock.updateObjectPosition();
        paper.updateObjectPosition();

        scissors2.updateObjectPosition();
        rock2.updateObjectPosition();
        paper2.updateObjectPosition();

        scissors3.updateObjectPosition();
        rock3.updateObjectPosition();
        paper3.updateObjectPosition();

        repaint();
    }
}