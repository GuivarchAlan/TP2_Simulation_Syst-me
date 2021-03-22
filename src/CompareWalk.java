import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompareWalk {

    public static void main(String[] args) {

        int seed = Integer.parseInt(args[0]);

        try {
            FileWriter wClassic = new FileWriter("outClassic.txt");
            FileWriter wNoReturn = new FileWriter("outNoReturn.txt");
            FileWriter wUnique = new FileWriter("outUnique.txt");
            float[] distanceClassic = new float[101];
            float[] distanceNoReturn = new float[101];
            float[] distanceUnique = new float[101];
            int numberOfTry = Integer.parseInt(args[1]);
        for (int k = 0; k < numberOfTry; k++) {
            for (int i = 2; i < 101; i++) {
                RandomWalk classicWalk = new RandomWalk(i, "C", seed);
                RandomWalk noReturnWalk = new RandomWalk(i, "S", seed);
                RandomWalk uniqueWalk = new RandomWalk(i, "U", seed);

                distanceClassic[i] += classicWalk.startWalk();
                distanceNoReturn[i] += noReturnWalk.startWalk();
                distanceUnique[i] += uniqueWalk.startWalk();
            }
            seed += k;
        }
        for(int i = 2; i < 100; i++) {
            wClassic.write(i + " " + distanceClassic[i]/numberOfTry + "\n");
            wNoReturn.write(i + " " + distanceNoReturn[i]/numberOfTry + "\n");
            wUnique.write(i + " " + distanceUnique[i]/numberOfTry + "\n");
        }
            wClassic.close();
            wNoReturn.close();
            wUnique.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
