import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MovingObjectsPanel extends JPanel implements ActionListener {
    String addressScissors = "assets/scissors.png";
    String addressRock = "assets/rock.png";
    String addressPaper = "assets/paper.png";

    MovingObject scissors;
    MovingObject scissors2;
    MovingObject scissors3;
    MovingObject paper;
    MovingObject paper2;
    MovingObject paper3;
    MovingObject rock;
    MovingObject rock2;
    MovingObject rock3;

    public MovingObjectsPanel() {
        // Set initial positions, deltas, and radii for the circles
        scissors = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 2,  addressScissors, this);
        scissors2 = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 1, addressScissors, this);
        scissors3 = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 3, addressScissors, this);
        paper = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 1,  addressPaper, this);
        paper2 = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 2,  addressPaper, this);
        paper3 = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 3,  addressPaper, this);
        rock = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 3, addressRock, this);
        rock2 = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 2, addressRock, this);
        rock3 = new MovingObject(generateRandomNumberInRange(30, 370), generateRandomNumberInRange(30, 370), 1, addressRock, this);


        // Start a timer to update the animation
        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();
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

        g.drawImage(paper.image, paper.getCoordinateX(), paper.getCoordinateY(),this);
        g.drawImage(paper2.image, paper2.getCoordinateX(), paper2.getCoordinateY(),this);
        g.drawImage(paper3.image, paper3.getCoordinateX(), paper3.getCoordinateY(),this);

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

    public int generateRandomNumberInRange(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range");
        }
        Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }
}