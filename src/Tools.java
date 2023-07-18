import java.util.Random;

public class Tools {
    public static int generateNegativeOrPositiveRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range");
        }
        Random random = new Random();
        int finalRandomValue;

        do {
            finalRandomValue = random.nextInt(max - min + 1) + min;
        } while (finalRandomValue == 0);

        boolean isNegative = random.nextBoolean();

        if (isNegative) {
            finalRandomValue *= -1;
        }

        return finalRandomValue;
    }

    public static int generateRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range");
        }
        Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }
}
