package src.lib;

public class Random {

    /**
     * 
     * @param min
     * @param max
     * @return
     */
    static public int randomInt(int min, int max) {
        int n = (int) (Math.random() * (double) ((max 
                - min) + min));

        return n;
    }

    static public double randomDouble(double min, double  max) {
        double n = Math.random() * (max 
                - min) + min;

        return n;
    }

    static public double randomDoubleWeighedDown(double min, double max, double exp, double pol2) {

        // I made the following desmos graph to visualize the randomizer based on parameters
        // b = max, a = exp, c = pol2, d = min

        // https://www.desmos.com/calculator/5vm5tltysh

        double x = Math.random();

        double n =  (Math.pow(x, exp) * (max - min)) + ((max - min) * pol2 * x * (1 - x)) + min;

        return n;
    }
    
}
