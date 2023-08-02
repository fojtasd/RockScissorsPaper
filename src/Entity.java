import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity {
    private int coordinateX;
    private int coordinateY;
    private final int IMAGE_HEIGHT;
    private final int IMAGE_WIDTH;
    private int velocityX;
    private int velocityY;
    private final MovingObjectsPanel movingObjectsPanel;
    private Image image;
    private Type typeOfObject;

    public Entity(int x, int y, int velocityX, int velocityY, Type type, MovingObjectsPanel movingObjectsPanel) throws Exception {
        this.coordinateX = x;
        this.coordinateY = y;
        this.velocityX = velocityX;
        this.velocityY = -velocityY;
        this.typeOfObject = type;
        this.image = Toolkit.getDefaultToolkit().getImage(type.getValue());
        this.movingObjectsPanel = movingObjectsPanel;
        EntityPool.getInstance().getEntities().add(this);

        File imageFile = new File(type.getValue());
        try {
            BufferedImage image = ImageIO.read(imageFile);
            this.IMAGE_WIDTH = image.getWidth();
            this.IMAGE_HEIGHT = image.getHeight();
        } catch (IOException e) {
            throw new Exception("Image has not been read correctly. Incorrect path.");
        }
    }

    public Type getTypeOfObject() {
        return typeOfObject;
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
     * Reverse velocity if edge is reached.
     */
    void updateObjectPosition() {
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

        move();
    }

    private void move(){
        coordinateX += velocityX;
        coordinateY += velocityY;
    }

    static void checkCollisionsBetweenObjects() {
        final EntityPool pool = EntityPool.getInstance();
        final int entitiesSize = pool.getEntities().size();

        for (int i = 0; i < entitiesSize; i++) {
            final Entity left = pool.getEntities().get(i);
            if (left == null) {
                break;
            }

            for (int j = 0; j < entitiesSize; j++) {
                if (i == j) {
                    continue;
                }
                final Entity right = pool.getEntities().get(j);

                if (left.coordinateX + left.IMAGE_WIDTH >= right.coordinateX
                        && left.coordinateX < right.coordinateX + right.IMAGE_WIDTH
                        && left.coordinateY + left.IMAGE_HEIGHT > right.coordinateY
                        && left.coordinateY < right.coordinateY + right.IMAGE_HEIGHT) {

                    // same signs of vectors
                    if ((left.velocityX > 0 && right.velocityX > 0 && left.velocityY > 0 && right.velocityY > 0)
                            || (left.velocityX < 0 && right.velocityX < 0 && left.velocityY < 0 && right.velocityY < 0)) {
                        int temporaryVelocity = left.velocityX;
                        left.velocityX = right.velocityX;
                        right.velocityX = temporaryVelocity;

                        temporaryVelocity = left.velocityY;
                        left.velocityY = right.velocityY;
                        right.velocityY = temporaryVelocity;
                    }
                    // different signs of vector
                    else if ((left.velocityX < 0 && right.velocityX < 0 && left.velocityY > 0 && right.velocityY > 0)
                            || (left.velocityX > 0 && right.velocityX > 0 && left.velocityY < 0 && right.velocityY < 0)) {
                        int temporaryVelocity = left.velocityX;
                        left.velocityX = right.velocityX;
                        right.velocityX = temporaryVelocity;

                        temporaryVelocity = left.velocityY;
                        left.velocityY = right.velocityY;
                        right.velocityY = temporaryVelocity;
                    } else {
                        left.velocityX = -left.velocityX;
                        left.velocityY = -left.velocityY;
                        left.coordinateX += left.velocityX;
                        left.coordinateY += left.velocityY;
                        right.velocityX = -right.velocityX;
                        right.velocityY = -right.velocityY;
                        right.coordinateX += right.velocityX;
                        right.coordinateY += right.velocityY;
                    }
                    changeTypeOfObject(right, left);
                }
            }
        }
    }

    private static void changeTypeOfObject(Entity A, Entity B) {
        if (A.typeOfObject == B.typeOfObject) {
            return;
        }

        if (A.typeOfObject == Type.paper && B.typeOfObject == Type.scissors) {
            A.typeOfObject = Type.scissors;
            A.image = Toolkit.getDefaultToolkit().getImage(A.typeOfObject.getValue());
        }
        if (A.typeOfObject == Type.paper && B.typeOfObject == Type.rock) {
            B.typeOfObject = Type.paper;
            B.image = Toolkit.getDefaultToolkit().getImage(A.typeOfObject.getValue());
        }

        if (A.typeOfObject == Type.rock && B.typeOfObject == Type.scissors) {
            B.typeOfObject = Type.rock;
            B.image = Toolkit.getDefaultToolkit().getImage(A.typeOfObject.getValue());
        }
        if (A.typeOfObject == Type.rock && B.typeOfObject == Type.paper) {
            A.typeOfObject = Type.paper;
            A.image = Toolkit.getDefaultToolkit().getImage(A.typeOfObject.getValue());
        }

        if (A.typeOfObject == Type.scissors && B.typeOfObject == Type.rock) {
            A.typeOfObject = Type.rock;
            A.image = Toolkit.getDefaultToolkit().getImage(A.typeOfObject.getValue());
        }
        if (A.typeOfObject == Type.scissors && B.typeOfObject == Type.paper) {
            B.typeOfObject = Type.scissors;
            B.image = Toolkit.getDefaultToolkit().getImage(A.typeOfObject.getValue());
        }
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public Image getImage() {
        return image;
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
