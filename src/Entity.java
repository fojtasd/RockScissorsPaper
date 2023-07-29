import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Incremental adding of velocity????

public class Entity {
    private int coordinateX;
    private int coordinateY;
    public final int IMAGE_HEIGHT;
    public final int IMAGE_WIDTH;
    public int velocityX;
    public int velocityY;
    public MovingObjectsPanel movingObjectsPanel;

    /**
     * Contains every created object of this class. It is necessary for controlling collisions.
     */
    static Entity[] entities = new Entity[1000];
    Image image;
    private Type typeOfObject;
    Boundaries boundaries;
    static int counterOfObjectsInArray = 0;

    public Entity(int x, int y, int velocityX, int velocityY, Type type, MovingObjectsPanel movingObjectsPanel) throws Exception {
        this.coordinateX = x;
        this.coordinateY = y;
        this.velocityX = velocityX;
        this.velocityY = -velocityY;
        this.typeOfObject = type;
        this.image = Toolkit.getDefaultToolkit().getImage(type.getValue());
        this.boundaries = new Boundaries(this);
        this.movingObjectsPanel = movingObjectsPanel;
        entities[counterOfObjectsInArray] = this;
        counterOfObjectsInArray++;

        File imageFile = new File(type.getValue());
        try {
            BufferedImage image = ImageIO.read(imageFile);
            this.IMAGE_WIDTH = image.getWidth();
            this.IMAGE_HEIGHT = image.getHeight();
        } catch (IOException e) {
            throw new Exception("Image has not been read correctly. Incorrect path.");
        }
    }

    /**
     * Images have to be the same size, so rock object is used.
     *
     * @return size of the image in order width, height.
     * @throws Exception in case image was not properly loaded or is missing.
     */
    public static int[] getImageSize() throws Exception {
        int[] imageSize = new int[2];
        File imageFile = new File(Type.rock.getValue());
        try {
            BufferedImage image = ImageIO.read(imageFile);
            imageSize[0] = image.getWidth();
            imageSize[1] = image.getHeight();
        } catch (IOException e) {
            throw new Exception("Image has not been read correctly. Incorrect path.");
        }
        return imageSize;
    }

    /**
     * Updates object's x and y coordination in accordance to its velocity.
     */
    void updateObjectPosition() {
        move();
        for (int i = 0; i < this.boundaries.allCoordinatesOfCorners.length; i++) {
            this.boundaries.allCoordinatesOfCorners[i] += this.velocityX;
        }

        if ((coordinateX + velocityX + IMAGE_WIDTH) >= movingObjectsPanel.getWidth() || (coordinateX - velocityX) <= 0) {
            velocityX = -velocityX; // Reverse x-direction velocity if the square hits the horizontal edges
            move();
        }

        if (coordinateY + velocityY + IMAGE_HEIGHT >= movingObjectsPanel.getHeight() || coordinateY - velocityY <= 0) {
            velocityY = -velocityY; // Reverse y-direction velocity if the square hits the vertical edges
            move();
        }

        //This block prevents crossing edges of canvas.
        if (coordinateX < 0) {
            coordinateX = 1;
            velocityX *= -1;
        }
        if (coordinateX > movingObjectsPanel.getWidth()) {
            coordinateX = movingObjectsPanel.canvasWidth + 1;
            velocityX *= -1;
        }
        if (coordinateY < 0) {
            coordinateY = 1;
            velocityY *= -1;
        }
        if (coordinateY > movingObjectsPanel.getHeight()) {
            coordinateY = movingObjectsPanel.canvasHeight + 1;
            velocityY *= -1;
        }
    }

    private void move(){
        coordinateX += velocityX;
        coordinateY += velocityY;
    }

    static void checkCollisionsBetweenObjects() {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] == null) {
                break;
            }

            for (int j = 0; j < entities.length; j++) {
                if (i == j) {
                    break;
                }
                if (entities[i].coordinateX + entities[i].IMAGE_WIDTH >= entities[j].coordinateX
                        && entities[i].coordinateX <= entities[j].coordinateX + entities[j].IMAGE_WIDTH
                        && entities[i].coordinateY + entities[i].IMAGE_HEIGHT >= entities[j].coordinateY
                        && entities[i].coordinateY <= entities[j].coordinateY + entities[j].IMAGE_HEIGHT) {
                    // same signs of vectors
                    if ((entities[i].velocityX > 0 && entities[j].velocityX > 0 && entities[i].velocityY > 0 && entities[j].velocityY > 0)
                            || (entities[i].velocityX < 0 && entities[j].velocityX < 0 && entities[i].velocityY < 0 && entities[j].velocityY < 0)) {
                        int temporaryVelocity = entities[i].velocityX;
                        entities[i].velocityX = entities[j].velocityX;
                        entities[j].velocityX = temporaryVelocity;

                        temporaryVelocity = entities[i].velocityY;
                        entities[i].velocityY = entities[j].velocityY;
                        entities[j].velocityY = temporaryVelocity;
                    }
                    // different signs of vector
                    else if ((entities[i].velocityX < 0 && entities[j].velocityX < 0 && entities[i].velocityY > 0 && entities[j].velocityY > 0)
                            || (entities[i].velocityX > 0 && entities[j].velocityX > 0 && entities[i].velocityY < 0 && entities[j].velocityY < 0)) {
                        int temporaryVelocity = entities[i].velocityX;
                        entities[i].velocityX = entities[j].velocityX;
                        entities[j].velocityX = temporaryVelocity;

                        temporaryVelocity = entities[i].velocityY;
                        entities[i].velocityY = entities[j].velocityY;
                        entities[j].velocityY = temporaryVelocity;
                    } else {
                        entities[i].velocityX = -entities[i].velocityX;
                        entities[i].velocityY = -entities[i].velocityY;
                        entities[i].coordinateX += entities[i].velocityX;
                        entities[i].coordinateY += entities[i].velocityY;
                        entities[j].velocityX = -entities[j].velocityX;
                        entities[j].velocityY = -entities[j].velocityY;
                        entities[j].coordinateX += entities[j].velocityX;
                        entities[j].coordinateY += entities[j].velocityY;
                    }
                    entities[i].changeTypeOfObject(entities[j]);
                }
            }
        }
    }

    private void changeTypeOfObject(Entity secondObject) {
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
     * Contains coordinates of each corner in order x,y.
     */
    private static class Boundaries {
        int[] bottomLeft = new int[2];
        int[] topLeft = new int[2];
        int[] bottomRight = new int[2];
        int[] topRight = new int[2];
        int[] allCoordinatesOfCorners = new int[8];

        private Boundaries(Entity entity) {
            this.bottomLeft[0] = entity.getCoordinateX();
            this.bottomLeft[1] = entity.getCoordinateY();

            this.topLeft[0] = entity.coordinateX;
            this.topLeft[1] = entity.coordinateY + entity.IMAGE_HEIGHT;

            this.topRight[0] = entity.coordinateX + entity.IMAGE_WIDTH;
            this.topRight[1] = entity.coordinateY + entity.IMAGE_HEIGHT;

            this.bottomRight[0] = entity.coordinateX + entity.IMAGE_WIDTH;
            this.bottomRight[1] = entity.coordinateY;

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
