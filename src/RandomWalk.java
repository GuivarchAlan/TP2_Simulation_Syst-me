import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RandomWalk extends JPanel{

    // https://stackoverflow.com/questions/26511402/drawing-a-random-walk-in-java-with-arrays
    // https://docs.google.com/document/d/1UJT_BOBdGhGDUe2HkNBb-AOsaTjc7gv9AVg6McYg2WE/edit

    int step;
    String type;
    Generator rand;

    int height;
    int width;
    int previousStep;

    int[] stepX;
    int[] stepY;

    int range = 10;

    RandomWalk(int n, String type, int seed) {

        step = n;
        previousStep = 0;
        this.type = type;
        stepX = new int[step];
        stepY = new int[step];
        height = 1000;
        width = 1000;

        rand = new Generator(seed);
        stepX[0] = rand.nextInt(0,width);
        stepY[0] =rand.nextInt(0,height);
    }

    float startWalk() {
        switch (this.type) {
            case "C":
                this.classicWalk();
                break;
            case "S":
                this.noReturnWalk();
                break;
            case "U":
                this.uniquePassageWalk();
                break;
            default:
                System.out.println("wrong type of Walk");
                System.exit(-1);
        }
        return (float) (Math.pow((stepX[step-1] - stepX[0]),2) + Math.pow((stepY[step-1] - stepY[0]),2));
    }

    private void classicWalk() {
            for (int i = 1; i < step; i++) {
                boolean isNotChoose = true;
                while (isNotChoose) {
                    int direction = rand.nextInt(1, 4);
                    int nextX;
                    int nextY;
                    switch (direction) {
                        case 1:
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] + range;
                            if (nextY <= height) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                        case 2:
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] - range;
                            if (nextY >= 0) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                        case 3:
                            nextX = stepX[i - 1] + range;
                            nextY = stepY[i - 1];
                            if (nextX <= width) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                        case 4:
                            nextX = stepX[i - 1] - range;
                            nextY = stepY[i - 1];
                            if (nextX >= 0) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                    }
                }
            }
    }

    private void noReturnWalk() {
        for (int i = 1; i < step; i++)
        {
            boolean isNotChoose = true;
            while (isNotChoose) {
                int nextX;
                int nextY;
                int direction  = rand.nextInt(1,4);
                switch(direction) {
                    case 1:
                        nextX = stepX[i-1];
                        nextY = stepY[i-1] + range;
                        if(nextY <= height && previousStep != 2) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                    case 2:
                        nextX = stepX[i-1];
                        nextY = stepY[i-1] - range;
                        if(nextY >= 0 && previousStep != 1) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                    case 3:
                        nextX = stepX[i-1] + range;
                        nextY = stepY[i-1];
                        if(nextX <= width && previousStep != 4) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                    case 4 :
                        nextX = stepX[i-1] - range;
                        nextY = stepY[i-1];
                        if(nextX >= 0 && previousStep != 3) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                }
            }
        }
    }

    private void uniquePassageWalk() {
        boolean deadEnd = true;
        int deadEnds = 0;
        while(deadEnd) {
            deadEnd = false;

            stepX = new int[step];
            stepY = new int[step];
            stepX[0] = rand.nextInt(0,width);
            stepY[0] =rand.nextInt(0,height);

            for (int i = 1; i < step; i++) {
                boolean isNotChoose = true;

                if (deadEnd) {
                    deadEnds ++;
                    break;
                }
                boolean alreadyPathUp = false;
                boolean alreadyPathDown = false;
                boolean alreadyPathRight = false;
                boolean alreadyPathLeft = false;

                while (isNotChoose && !deadEnd) {

                    if (alreadyPathUp && alreadyPathDown && alreadyPathRight && alreadyPathLeft) {
                        deadEnd = true;
                    }

                    int direction = rand.nextInt(1, 4);


                    int nextX;
                    int nextY;
                    switch (direction) {
                        case 1:
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] + range;
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == nextY) {
                                    alreadyPathUp = true;
                                    break;
                                }
                            }
                            if (nextY <= height && !alreadyPathUp) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            else if (nextY > height){
                                alreadyPathUp = true;
                            }
                            break;
                        case 2:
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] - range;
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == nextY) {
                                    alreadyPathDown = true;
                                    break;
                                }
                            }
                            if (nextY >= 0 && !alreadyPathDown) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            else if (nextY < 0){
                                alreadyPathDown = true;
                            }
                            break;
                        case 3:
                            nextX = stepX[i - 1] + range;
                            nextY = stepY[i - 1];
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == stepY[i - 1]) {
                                    alreadyPathRight = true;
                                    break;
                                }
                            }
                            if (nextX <= width && !alreadyPathRight) {
                                stepX[i] = stepX[i - 1] + range;
                                stepY[i] = stepY[i - 1];
                                isNotChoose = false;
                            }
                            else if (nextX > width){
                                alreadyPathRight = true;
                            }
                            break;
                        case 4:
                            nextX = stepX[i - 1] - range;
                            nextY = stepY[i - 1];
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == nextY) {
                                    alreadyPathLeft = true;
                                    break;
                                }
                            }
                            if (nextX >= 0 && !alreadyPathLeft) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            else if (nextX < 0){
                                alreadyPathLeft = true;
                            }
                            break;
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(Color.black);
        for (int i = 1; i < step; i++) {
            g.drawLine(stepX[i - 1], stepY[i - 1], stepX[i], stepY[i]);
        }
        g.setColor(Color.GREEN);
        g.fillOval(stepX[0] - 5,stepY[0] - 5, 10,10);
        g.setColor(Color.BLUE);
        g.fillOval(stepX[step-1] - 5,stepY[step-1] - 5, 10,10);
    }

    // args[0] = n, args[1] = type, args[2] = seed
    public static void main(String[] args) {

        int seed = Integer.parseInt(args[2]);
        //Generates frame.
        JFrame frame = new JFrame();

        RandomWalk panel = new RandomWalk(Integer.parseInt(args[0]), args[1], seed);
        //Sets frame resolution and other parameters.
        frame.setSize(panel.width,panel.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);

        panel.startWalk();
    }


}
