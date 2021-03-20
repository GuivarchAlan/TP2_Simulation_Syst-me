public class RandomWalk {

    // https://stackoverflow.com/questions/26511402/drawing-a-random-walk-in-java-with-arrays
    // https://docs.google.com/document/d/1UJT_BOBdGhGDUe2HkNBb-AOsaTjc7gv9AVg6McYg2WE/edit

    int step;
    String type;

    int height;
    int width;

    int[] stepX;
    int[] stepY;

    RandomWalk(int n, String type,int h, int w, int seed) {
        step = n;
        this.type = type;
        stepX = new int[step];
        stepY = new int[step];

    }

    void startWalk() {

    }

    void classicWalk() {

    }

    void noReturnWalk() {

    }

    void uniquePassageWalk() {

    }

    public static void main(String[] args) {

    }
}
