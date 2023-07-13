import java.awt.*;

public class MovingObject {
    private int coordinateX;
    private int coordinateY;
    public final int IMAGE_HEIGHT = 30;
    public final int IMAGE_WIDTH = 30;
    public int velocity;
    Image image;
    Boundaries boundaries;

    public MovingObject(int x1, int y1, int velocity, Image image) {
        this.coordinateX = x1;
        this.coordinateY = y1;
        this.velocity = velocity;
        this.image = image;
        this.boundaries = new Boundaries(this);
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
        this.coordinateX += this.velocity;
        this.coordinateY += this.velocity;
        for (int i = 0; i < this.boundaries.allCoordinatesOfCorners.length; i++){
            this.boundaries.allCoordinatesOfCorners[i] = this.boundaries.allCoordinatesOfCorners[i] + this.velocity;
        }
    }

    public void checkBorders(MovingObjectsPanel movingObjectsPanel){
        if (this.getCoordinateX() <= 0 || this.getCoordinateX() + this.IMAGE_WIDTH >= movingObjectsPanel.getWidth()
                || this.getCoordinateY() <= 0 || this.getCoordinateY() + this.IMAGE_HEIGHT >= movingObjectsPanel.getHeight()) {
            this.reverseDirection();
            // Checking if not crossing edges.
            if (this.getCoordinateX() <= 0 || this.getCoordinateX() + IMAGE_WIDTH >= movingObjectsPanel.getWidth()
                    || this.getCoordinateY() <= 0 || this.getCoordinateY() + IMAGE_HEIGHT >= movingObjectsPanel.getHeight()){
                this.move();
            }
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
