import java.awt.*;
import java.util.Random;

public class MovingObject {
    private int coordinateX;
    private int coordinateY;
    public final int IMAGE_HEIGHT = 30;
    public final int IMAGE_WIDTH = 30;
    public int velocity;
    Image image;

    public MovingObject(int x1, int y1, int velocity, Image image) {
        this.coordinateX = x1;
        this.coordinateY = y1;
        this.velocity = velocity;
        this.image = image;
    }

    void reverseDirection(){
        this.velocity = this.velocity * (-1);
    }

    public int getCoordinateX(){
        return coordinateX;
    }

    public int getCoordinateY(){
        return coordinateY;
    }

    public void move(){

        Random random = new Random();

        this.coordinateX += this.velocity;
        this.coordinateY += this.velocity;
        this.coordinateY += random.nextInt(2);
        this.coordinateX += random.nextInt(2);
    }
}
