import java.util.Random;

public class Tools {
    /**
     * @param min
     * @param max
     * @return
     * @throws IllegalArgumentException throws when min >= max or min is zero
     */
    public static int generateNegativeOrPositiveRandomNumberInRange(
        final int min, 
        final int max
    ) throws IllegalArgumentException {
        if (min >= max || min == 0) {
            throw new IllegalArgumentException("Invalid range.");
        }
        final Random random = new Random();
        final int positiveRandomValue = random.nextInt(max - min + 1) + min;
        final boolean isNegative = random.nextBoolean();

        return isNegative ? -positiveRandomValue : positiveRandomValue;
    }

    public static int generateRandomNumberInRange(final int min, final int max) throws IllegalArgumentException {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range.");
        }
        final Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }
}
