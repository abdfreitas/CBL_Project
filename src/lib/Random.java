package src.lib;

/**
 * Class for generating random numbers in different ways.
 * 
 * Includes helper methods to get random ints and doubles,
 * and a custom method for creating "weighted" random values that are 
 * skewed toward lower or middle numbers.
 */
public class Random {

    /**
     * Returns a random integer between min and max.
     * 
     * @param min the smallest number that can be returned
     * @param max the largest number that can be returned
     * @return a random integer between min and max
     */
    public static int randomInt(int min, int max) {
        int n = (int) (Math.random() * (double) ((max 
                - min) + min));
        return n;
    }

    /**
     * Returns a random double between min and max.
     * 
     * @param min the smallest number that can be returned
     * @param max the largest number that can be returned
     * @return a random double between min and max
     */
    public static double randomDouble(double min, double  max) {
        double n = Math.random() * (max 
                - min) + min;
        return n;
    }

    /**
    * Returns a random double between min and max, but with a "weighted" curve.
    * 
    * We want to bias results, with smaller numbers are more likely than big ones.
    * Controlled by:
    *   exp  → how steep the bias curve is
    *   pol2 → how much we bend the middle section
    * Desmos to visualize this (see link below).
    */
    public static double randomDoubleWeighedDown(double min, double max, double exp, double pol2) {
        // Made the following desmos graph to visualize the randomizer based on parameters
        // b = max, a = exp, c = pol2, d = min
        // https://www.desmos.com/calculator/5vm5tltysh

        double x = Math.random();
        double n =  (Math.pow(x, exp) * (max - min)) + ((max - min) * pol2 * x * (1 - x)) + min;
        return n;
    }
}
