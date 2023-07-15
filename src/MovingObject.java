import java.awt.*;
import java.sql.SQLOutput;

public class MovingObject {
    private int coordinateX;
    private int coordinateY;
    public final int IMAGE_HEIGHT = 30;
    public final int IMAGE_WIDTH = 40;
    public double velocityX;
    public double velocityY;
    public MovingObjectsPanel movingObjectsPanel;
    Image image;
    Type typeOfObject;
    Boundaries boundaries;

    static MovingObject[] movingObjects = new MovingObject[10];
    static int counterOfObjectsInArray = 0;

    enum Type{
        scissors,
        rock,
        paper
    }

    public MovingObject(int x1, int y1, double velocity, String imageAddress, MovingObjectsPanel movingObjectsPanel) {
        this.coordinateX = x1;
        this.coordinateY = y1;
        this.velocityX = velocity;
        this.velocityY = -velocity;
        this.image = Toolkit.getDefaultToolkit().getImage(imageAddress);
        this.boundaries = new Boundaries(this);
        this.movingObjectsPanel = movingObjectsPanel;
        movingObjects[counterOfObjectsInArray] = this;
        counterOfObjectsInArray++;

        String substring = imageAddress.substring(7);
        switch (substring) {
            case "scissors" -> typeOfObject = Type.scissors;
            case "rock" -> typeOfObject = Type.rock;
            case "paper" -> typeOfObject = Type.paper;
        }

    }

    void updateObjectPosition(){
        coordinateX += velocityX;
        coordinateY += velocityY;
        for (int i = 0; i < this.boundaries.allCoordinatesOfCorners.length; i++){
            this.boundaries.allCoordinatesOfCorners[i] += (int)this.velocityX;
        }

        if (coordinateX + IMAGE_WIDTH >= movingObjectsPanel.getWidth() || coordinateX <= 0) {
            velocityX = -velocityX; // Reverse x-direction velocity if the square hits the horizontal edges
        }

        if (coordinateY + IMAGE_HEIGHT >= movingObjectsPanel.getHeight() || coordinateY <= 0) {
            velocityY = -velocityY; // Reverse y-direction velocity if the square hits the vertical edges
        }
    }

    public static void checkCollision() {
        for(int i = 0; i < movingObjects.length; i++){
            if(movingObjects[i] == null){
                break;
            }
            for(int j = 0; j < movingObjects.length; j++){
                if(i == j){
                    break;
                }
                if (movingObjects[i].coordinateX + movingObjects[i].IMAGE_WIDTH >= movingObjects[j].coordinateX
                        && movingObjects[i].coordinateX <= movingObjects[j].coordinateX + movingObjects[j].IMAGE_WIDTH
                        && movingObjects[i].coordinateY + movingObjects[i].IMAGE_HEIGHT >= movingObjects[j].coordinateY
                        && movingObjects[i].coordinateY <= movingObjects[j].coordinateY + movingObjects[j].IMAGE_HEIGHT) {
                    // same signs of vectors
                    if ((movingObjects[i].velocityX > 0 && movingObjects[j].velocityX > 0 && movingObjects[i].velocityY > 0 && movingObjects[j].velocityY > 0)
                            || (movingObjects[i].velocityX < 0 && movingObjects[j].velocityX < 0 && movingObjects[i].velocityY < 0 && movingObjects[j].velocityY < 0)){
                        double temp = movingObjects[i].velocityX;
                        movingObjects[i].velocityX = movingObjects[j].velocityX;
                        movingObjects[j].velocityX = temp;

                        temp = movingObjects[i].velocityY;
                        movingObjects[i].velocityY = movingObjects[j].velocityY;
                        movingObjects[j].velocityY = temp;
                    }
                    // different signs of vector
                    else if ((movingObjects[i].velocityX < 0 && movingObjects[j].velocityX < 0 && movingObjects[i].velocityY > 0 && movingObjects[j].velocityY > 0)
                            || (movingObjects[i].velocityX > 0 && movingObjects[j].velocityX > 0 && movingObjects[i].velocityY < 0 && movingObjects[j].velocityY < 0)){
                        double temp = movingObjects[i].velocityX;
                        movingObjects[i].velocityX = movingObjects[j].velocityX;
                        movingObjects[j].velocityX = temp;

                        temp = movingObjects[i].velocityY;
                        movingObjects[i].velocityY = movingObjects[j].velocityY;
                        movingObjects[j].velocityY = temp;
                    }
                    else {
                        movingObjects[i].velocityX = -movingObjects[i].velocityX;
                        movingObjects[i].velocityY = -movingObjects[i].velocityY;
                        movingObjects[i].coordinateX += movingObjects[i].velocityX;
                        movingObjects[i].coordinateY += movingObjects[i].velocityY;
                        movingObjects[j].velocityX = -movingObjects[j].velocityX;
                        movingObjects[j].velocityY = -movingObjects[j].velocityY;
                        movingObjects[j].coordinateX += movingObjects[j].velocityX;
                        movingObjects[j].coordinateY += movingObjects[j].velocityY;
                    }

                    //change image and type
                    if(movingObjects[i].typeOfObject != movingObjects[j].typeOfObject){
                        if (movingObjects[i].typeOfObject == Type.scissors && movingObjects[j].typeOfObject == Type.paper){

                        }
                    }
                }
            }

        }
    }

    public int getCoordinateX(){
        return coordinateX;
    }

    public int getCoordinateY(){
        return coordinateY;
    }

    public void move(){
        this.coordinateX += this.velocityX;
        this.coordinateY += this.velocityY;
        for (int i = 0; i < this.boundaries.allCoordinatesOfCorners.length; i++){
            this.boundaries.allCoordinatesOfCorners[i] += (int)this.velocityX;
        }
    }

    public boolean isColliding(MovingObject movingObject) {
        int square1MinX = findMinX(this.boundaries.allCoordinatesOfCorners);
        int square1MaxX = findMaxX(this.boundaries.allCoordinatesOfCorners);
        int square1MinY = findMinY(this.boundaries.allCoordinatesOfCorners);
        int square1MaxY = findMaxY(this.boundaries.allCoordinatesOfCorners);

        int square2MinX = findMinX(movingObject.boundaries.allCoordinatesOfCorners);
        int square2MaxX = findMaxX(movingObject.boundaries.allCoordinatesOfCorners);
        int square2MinY = findMinY(movingObject.boundaries.allCoordinatesOfCorners);
        int square2MaxY = findMaxY(movingObject.boundaries.allCoordinatesOfCorners);

        return !(square1MaxX < square2MinX || square1MinX > square2MaxX
                || square1MaxY < square2MinY || square1MinY > square2MaxY);
    }

    private int findMinX(int[] allCoordinatesOfCorners) {
        int minX = Integer.MAX_VALUE;
        for (int i = 0; i < allCoordinatesOfCorners.length; i += 2) {
            minX = Math.min(minX, allCoordinatesOfCorners[i]);
        }
        return minX;
    }

    private int findMaxX(int[] allCoordinatesOfCorners) {
        int maxX = Integer.MIN_VALUE;
        for (int i = 0; i < allCoordinatesOfCorners.length; i += 2) {
            maxX = Math.max(maxX, allCoordinatesOfCorners[i]);
        }
        return maxX;
    }

    private int findMinY(int[] allCoordinatesOfCorners) {
        int minY = Integer.MAX_VALUE;
        for (int i = 1; i < allCoordinatesOfCorners.length; i += 2) {
            minY = Math.min(minY, allCoordinatesOfCorners[i]);
        }
        return minY;
    }

    private int findMaxY(int[] allCoordinatesOfCorners) {
        int maxY = Integer.MIN_VALUE;
        for (int i = 1; i < allCoordinatesOfCorners.length; i += 2) {
            maxY = Math.max(maxY, allCoordinatesOfCorners[i]);
        }
        return maxY;
    }

    /**
     * Contains coordinates of each corner in order x,y
     */
    private static class Boundaries{
        int[] bottomLeft = new int[2];
        int[] topLeft = new int[2];
        int[] bottomRight = new int[2];
        int[] topRight = new int[2];
        int[] allCoordinatesOfCorners = new int[8];

        public Boundaries(MovingObject movingObject){
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
}
