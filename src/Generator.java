public class Generator {

    double a;
    double c;
    double m;
    double seed;
    double value;

    Generator(int seed) {
        this.seed = seed;
        m = (int) Math.pow(2,32);
        a = 1664525;
        c = 1013904223;
        value = seed;
    }

    double nextRand() {
        value = (a*value + c) % m;
        return value / m;
    }

   int nextInt(int lowerBorder, int upperBorder) {
        double rand = nextRand();
        return (int) (rand * upperBorder + lowerBorder);
   }
}
