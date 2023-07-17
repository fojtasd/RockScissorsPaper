import java.util.Random;

public class Tools {
    public static int generateRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range");
        }
        Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }
}
