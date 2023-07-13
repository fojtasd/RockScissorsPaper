public class CollisionChecker {

    // Check if two squares intersect
    public static boolean isColliding(MovingObject movingObject1,
                                      MovingObject movingObject2) {
        // Calculate the coordinates of the sides of the squares
/*        if (movingObject1.pointB.getX() < movingObject2.pointD.getX()
                || movingObject1.pointA.getY() < movingObject2.pointC.getY()){
            return false;
        }

        if (movingObject1.pointB.getY() < movingObject2.pointD.getY()
                || movingObject1.pointA.getX() < movingObject2.pointC.getX()){
            return false;
        }

        if (movingObject1.pointB.getY() < movingObject2.pointD.getY()
                || movingObject1.pointA.getX() < movingObject2.pointC.getX()){
            return false;
        }*/

        return true;
    }
}
