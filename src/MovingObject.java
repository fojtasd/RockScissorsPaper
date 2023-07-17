import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MovingObject {
    private int coordinateX;
    private int coordinateY;
    private final int IMAGE_HEIGHT;
    private final int IMAGE_WIDTH;
    public double velocityX;
    public double velocityY;
    public MovingObjectsPanel movingObjectsPanel;

    /**
     * Contains every created object of this class. It is necessary for controlling collisions.
     */
    static MovingObject[] movingObjects = new MovingObject[10];
    Image image;
    Type typeOfObject;
    Boundaries boundaries;

    static int counterOfObjectsInArray = 0;

    public MovingObject(int x1, int y1, double velocity, Type type, MovingObjectsPanel movingObjectsPanel) throws Exception {
        this.coordinateX = x1;
        this.coordinateY = y1;
        this.velocityX = velocity;
        this.velocityY = -velocity;
        this.image = Toolkit.getDefaultToolkit().getImage(type.getValue());
        this.boundaries = new Boundaries(this);
        this.movingObjectsPanel = movingObjectsPanel;
        movingObjects[counterOfObjectsInArray] = this;
        counterOfObjectsInArray++;
        this.typeOfObject = type;

        File imageFile = new File(type.getValue());
        try {
            BufferedImage image = ImageIO.read(imageFile);
            this.IMAGE_WIDTH = image.getWidth();
            this.IMAGE_HEIGHT = image.getHeight();
        } catch (IOException e) {
            throw new Exception("Image has not been read correctly. Incorrect path.");
        }
    }

    void updateObjectPosition() {
        coordinateX += velocityX;
        coordinateY += velocityY;
        for (int i = 0; i < this.boundaries.allCoordinatesOfCorners.length; i++) {
            this.boundaries.allCoordinatesOfCorners[i] += (int) this.velocityX;
        }

        if (coordinateX + IMAGE_WIDTH >= movingObjectsPanel.getWidth() || coordinateX <= 0) {
            velocityX = -velocityX; // Reverse x-direction velocity if the square hits the horizontal edges
        }

        if (coordinateY + IMAGE_HEIGHT >= movingObjectsPanel.getHeight() || coordinateY <= 0) {
            velocityY = -velocityY; // Reverse y-direction velocity if the square hits the vertical edges
        }
    }

    static void checkCollision() {
        for (int i = 0; i < movingObjects.length; i++) {
            if (movingObjects[i] == null) {
                break;
            }

            for (int j = 0; j < movingObjects.length; j++) {
                if (i == j) {
                    break;
                }

                if (movingObjects[i].coordinateX + movingObjects[i].IMAGE_WIDTH >= movingObjects[j].coordinateX
                        && movingObjects[i].coordinateX <= movingObjects[j].coordinateX + movingObjects[j].IMAGE_WIDTH
                        && movingObjects[i].coordinateY + movingObjects[i].IMAGE_HEIGHT >= movingObjects[j].coordinateY
                        && movingObjects[i].coordinateY <= movingObjects[j].coordinateY + movingObjects[j].IMAGE_HEIGHT) {
                    // same signs of vectors
                    if ((movingObjects[i].velocityX > 0 && movingObjects[j].velocityX > 0 && movingObjects[i].velocityY > 0 && movingObjects[j].velocityY > 0)
                            || (movingObjects[i].velocityX < 0 && movingObjects[j].velocityX < 0 && movingObjects[i].velocityY < 0 && movingObjects[j].velocityY < 0)) {
                        double temp = movingObjects[i].velocityX;
                        movingObjects[i].velocityX = movingObjects[j].velocityX;
                        movingObjects[j].velocityX = temp;

                        temp = movingObjects[i].velocityY;
                        movingObjects[i].velocityY = movingObjects[j].velocityY;
                        movingObjects[j].velocityY = temp;
                    }
                    // different signs of vector
                    else if ((movingObjects[i].velocityX < 0 && movingObjects[j].velocityX < 0 && movingObjects[i].velocityY > 0 && movingObjects[j].velocityY > 0)
                            || (movingObjects[i].velocityX > 0 && movingObjects[j].velocityX > 0 && movingObjects[i].velocityY < 0 && movingObjects[j].velocityY < 0)) {
                        double temp = movingObjects[i].velocityX;
                        movingObjects[i].velocityX = movingObjects[j].velocityX;
                        movingObjects[j].velocityX = temp;

                        temp = movingObjects[i].velocityY;
                        movingObjects[i].velocityY = movingObjects[j].velocityY;
                        movingObjects[j].velocityY = temp;
                    } else {
                        movingObjects[i].velocityX = -movingObjects[i].velocityX;
                        movingObjects[i].velocityY = -movingObjects[i].velocityY;
                        movingObjects[i].coordinateX += movingObjects[i].velocityX;
                        movingObjects[i].coordinateY += movingObjects[i].velocityY;
                        movingObjects[j].velocityX = -movingObjects[j].velocityX;
                        movingObjects[j].velocityY = -movingObjects[j].velocityY;
                        movingObjects[j].coordinateX += movingObjects[j].velocityX;
                        movingObjects[j].coordinateY += movingObjects[j].velocityY;
                    }

                    movingObjects[i].changeTypeOfObject(movingObjects[j]);
                }
            }
        }
    }

    private void changeTypeOfObject(MovingObject secondObject) {
        if (this.typeOfObject == secondObject.typeOfObject) {
            return;
        }

        if (this.typeOfObject == Type.paper && secondObject.typeOfObject == Type.scissors) {
            this.typeOfObject = Type.scissors;
            this.image = Toolkit.getDefaultToolkit().getImage(this.typeOfObject.getValue());
        }
        if (this.typeOfObject == Type.paper && secondObject.typeOfObject == Type.rock) {
            secondObject.typeOfObject = Type.paper;
            secondObject.image = Toolkit.getDefaultToolkit().getImage(this.typeOfObject.getValue());
        }

        if (this.typeOfObject == Type.rock && secondObject.typeOfObject == Type.scissors) {
            secondObject.typeOfObject = Type.rock;
            secondObject.image = Toolkit.getDefaultToolkit().getImage(this.typeOfObject.getValue());
        }
        if (this.typeOfObject == Type.rock && secondObject.typeOfObject == Type.paper) {
            this.typeOfObject = Type.paper;
            this.image = Toolkit.getDefaultToolkit().getImage(this.typeOfObject.getValue());
        }

        if (this.typeOfObject == Type.scissors && secondObject.typeOfObject == Type.rock) {
            this.typeOfObject = Type.rock;
            this.image = Toolkit.getDefaultToolkit().getImage(this.typeOfObject.getValue());
        }
        if (this.typeOfObject == Type.scissors && secondObject.typeOfObject == Type.paper) {
            secondObject.typeOfObject = Type.scissors;
            secondObject.image = Toolkit.getDefaultToolkit().getImage(this.typeOfObject.getValue());
        }
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Contains coordinates of each corner in order x,y
     */
    private static class Boundaries {
        int[] bottomLeft = new int[2];
        int[] topLeft = new int[2];
        int[] bottomRight = new int[2];
        int[] topRight = new int[2];
        int[] allCoordinatesOfCorners = new int[8];

        private Boundaries(MovingObject movingObject) {
            this.bottomLeft[0] = movingObject.getCoordinateX();
            this.bottomLeft[1] = movingObject.getCoordinateY();

            this.topLeft[0] = movingObject.coordinateX;
            this.topLeft[1] = movingObject.coordinateY + movingObject.IMAGE_HEIGHT;

            this.topRight[0] = movingObject.coordinateX + movingObject.IMAGE_WIDTH;
            this.topRight[1] = movingObject.coordinateY + movingObject.IMAGE_HEIGHT;

            this.bottomRight[0] = movingObject.coordinateX + movingObject.IMAGE_WIDTH;
            this.bottomRight[1] = movingObject.coordinateY;

            this.allCoordinatesOfCorners[0] = this.topLeft[0];
            this.allCoordinatesOfCorners[1] = this.topLeft[1];
            this.allCoordinatesOfCorners[2] = this.topRight[0];
            this.allCoordinatesOfCorners[3] = this.topRight[1];
            this.allCoordinatesOfCorners[4] = this.bottomRight[0];
            this.allCoordinatesOfCorners[5] = this.bottomRight[1];
            this.allCoordinatesOfCorners[6] = this.bottomLeft[0];
            this.allCoordinatesOfCorners[7] = this.bottomLeft[1];
        }
    }

    public enum Type {
        scissors("assets/scissors.png"),
        rock("assets/rock.png"),
        paper("assets/paper.png");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
