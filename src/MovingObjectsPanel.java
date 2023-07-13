import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingObjectsPanel extends JPanel implements ActionListener {
    String addressScissors = "E:/DEV/rockScissorsPaper/assets/scissors.png";
    String addressRock = "E:/DEV/rockScissorsPaper/assets/rock.png";
    String addressPaper = "E:/DEV/rockScissorsPaper/assets/paper.png";
    Image imageScissors = Toolkit.getDefaultToolkit().getImage(addressScissors);
    Image imageRock = Toolkit.getDefaultToolkit().getImage(addressRock);
    Image imagePaper = Toolkit.getDefaultToolkit().getImage(addressPaper);

    MovingObject scissors;
    MovingObject paper;
    MovingObject rock;

    public MovingObjectsPanel() {
        // Set initial positions, deltas, and radii for the circles
        scissors = new MovingObject(120, 50, 5,  imageScissors);
        paper = new MovingObject(350, 10, 5,  imagePaper);
        rock = new MovingObject(240, 240, 5, imageRock);

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

        g.drawImage(rock.image, rock.getCoordinateX(), rock.getCoordinateY(), this);

        g.drawImage(paper.image, paper.getCoordinateX(), paper.getCoordinateY(),this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the positions of the circles
        scissors.move();
        rock.move();
        paper.move();

        // Check for collisions between circles
        try {
            if (CollisionChecker.isColliding(scissors, rock)) {
                scissors.reverseDirection();
                rock.reverseDirection();
            }

            if (CollisionChecker.isColliding(scissors, paper)) {
                scissors.reverseDirection();
                paper.reverseDirection();
            }

            if (CollisionChecker.isColliding(rock, paper)) {
                rock.reverseDirection();
                paper.reverseDirection();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // Reverse direction if circles reach the edges
        if (scissors.getCoordinateX() <= 0 || scissors.getCoordinateX() >= getWidth()
        || scissors.getCoordinateY() <= 0 || scissors.getCoordinateY() >= getHeight()) {
            scissors.reverseDirection();
            // Checking not crossing edges.
            if (scissors.getCoordinateX() <= 0 || scissors.getCoordinateX() >= getWidth()
                    || scissors.getCoordinateY() <= 0 || scissors.getCoordinateY() >= getHeight()){
                scissors.move();
            }
        }
        if (rock.getCoordinateX() <= 0 || rock.getCoordinateX() >= getWidth()
        || rock.getCoordinateY() <= 0 || rock.getCoordinateY() >= getHeight()) {
            rock.reverseDirection();
            if (rock.getCoordinateX() <= 0 || rock.getCoordinateX() >= getWidth()
                    || rock.getCoordinateY() <= 0 || rock.getCoordinateY() >= getHeight()) {
                rock.move();
            }
        }
        if (paper.getCoordinateX() <= 0 || paper.getCoordinateX() >= getWidth()
        || paper.getCoordinateY() <= 0 || paper.getCoordinateY() >= getHeight()) {
            paper.reverseDirection();
            if (paper.getCoordinateX() <= 0 || paper.getCoordinateX() >= getWidth()
                    || paper.getCoordinateY() <= 0 || paper.getCoordinateY() >= getHeight()) {
                paper.move();
            }
        }

        System.out.println("Kamen coord: x:" + rock.getCoordinateX() + ", y: " + rock.getCoordinateY());
        System.out.println("Nuzky coord: x:" + scissors.getCoordinateX() + ", y: " + scissors.getCoordinateY());
        System.out.println("Papir coord: x:" + paper.getCoordinateX() + ", y: " + paper.getCoordinateY());
        // Repaint the panel to update the animation
        repaint();
    }



}