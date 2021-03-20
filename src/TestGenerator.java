public class TestGenerator {

    /*
    Target proba for each sum of 2 dice
    * sum = 2 or 3 proba = 1/12 = 0,083
    * sum = 4 proba = 1/12 = 0,083
    * sum = 5 proba = 1/9 = 0,111
    * sum = 6 proba = 5/36 = 0,139
    * sum = 7 proba = 1/6 = 0,167
    * sum = 8 proba = 5/36 = 0,139
    * sum = 9 proba = 1/9 = 0,111
    * sum = 10 proba = 1/12 = 0,083
    * sum = 11 or 12 proba = 1/12 = 0,083
    * */


    public static void main(String[] args) {
        Generator rand = new Generator(164635156);
        int[] counterSum = new int[9];
        int numberOfTry = 1000;
        int[] expectedNumberSum = {Math.round((float)numberOfTry/12), Math.round((float)numberOfTry/12), Math.round((float)numberOfTry/9), Math.round((5* (float)numberOfTry)/36),
                Math.round((float)numberOfTry/6),Math.round((5* (float)numberOfTry)/36) , Math.round((float)numberOfTry/9), Math.round((float)numberOfTry/12), Math.round((float)numberOfTry/12)};
        for (int i = 0; i < numberOfTry; i++) {
            int dice1 = rand.nextInt(1,6);
            int dice2 = rand.nextInt(1,6);
            int sumDice = dice1 + dice2;
            if (sumDice == 2 || sumDice == 3) {
                counterSum[0] ++;
            }
            else if(sumDice == 11 || sumDice == 12) {
                counterSum[8] ++;
            }
            else {
                counterSum[sumDice - 3] ++;
            }

        }

        float C = 0f;

        for (int i = 0; i < counterSum.length; i++) {
            C += Math.pow(counterSum[i] - expectedNumberSum[i],2)/expectedNumberSum[i];
        }
        System.out.println("C = " + C);
        System.out.println("sum = 2 or 3 " + " proba = " +(float) counterSum[0] + " Target proba = " + expectedNumberSum[0]);
        for (int i = 1; i < counterSum.length - 1; i++) {
            System.out.println("sum = " + (i + 3) + " proba = " +(float) counterSum[i] + " Target proba = " + expectedNumberSum[i]);
        }
        System.out.println("sum = 11 or 12 " + " proba = " +(float) counterSum[8] + " Target proba = " + expectedNumberSum[8]);
    }

}
