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
        scissors = new MovingObject(0, 70, 2,  imageScissors);
        paper = new MovingObject(350, 10, 2,  imagePaper);
        rock = new MovingObject(50, 120, 2, imageRock);

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
            if (scissors.isColliding(rock)) {
                scissors.reverseDirection();
                rock.reverseDirection();
            }

            if (scissors.isColliding(paper)) {
                scissors.reverseDirection();
                paper.reverseDirection();
            }

            if (rock.isColliding(paper)) {
                rock.reverseDirection();
                paper.reverseDirection();
            }
            System.out.println(scissors.isColliding(rock));
            System.out.println(scissors.isColliding(paper));
            System.out.println(paper.isColliding(rock));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // Reverse direction if circles reach the edges
        scissors.checkBorders(this);
        rock.checkBorders(this);
        paper.checkBorders(this);

        // Repaint the panel to update the animation
        repaint();
    }



}