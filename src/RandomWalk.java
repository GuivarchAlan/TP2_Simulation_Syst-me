import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RandomWalk extends JPanel{

    // nombre de pas a realiser
    int step;
    //type de marche choisit “C” pour classique, “S” pour sans retour et “U” pour passage unique
    String type;
    // stock le generateur aléatoire
    Generator rand;

    // dimension de la fenetre d'affichage
    int height;
    int width;

    // stock les cordonnées x et y de la marche aleatoire choisit
    int[] stepX;
    int[] stepY;

    // stock le déplacement précédent utile pour la marche sans retour
    int previousStep;
    // longueur d'un pas lors de l'affichage
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
        // pour chaque pas
            for (int i = 1; i < step; i++) {
                // tant que le déplacement n'est pas valide
                boolean isNotChoose = true;
                while (isNotChoose) {
                    //  recupere un entier entre 1 et 4
                    int direction = rand.nextInt(1, 4);
                    int nextX;
                    int nextY;
                    switch (direction) {
                        case 1: // deplacement vers le haut
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] + range;
                            // si le deplacement ne sort pas l'ecran
                            if (nextY <= height) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                        case 2: // deplacement vers la bas
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] - range;
                            // si le deplacement ne sort pas l'ecran
                            if (nextY >= 0) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                        case 3: // deplacement vers le droite
                            nextX = stepX[i - 1] + range;
                            nextY = stepY[i - 1];
                            // si le deplacement ne sort pas l'ecran
                            if (nextX <= width) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            break;
                        case 4: // deplacement vers la gauche
                            nextX = stepX[i - 1] - range;
                            nextY = stepY[i - 1];
                            // si le deplacement ne sort pas l'ecran
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
            // tant que le déplacement n'est pas valide
            boolean isNotChoose = true;
            while (isNotChoose) {
                int nextX;
                int nextY;
                //  recupere un entier entre 1 et 4
                int direction  = rand.nextInt(1,4);
                switch(direction) {
                    case 1: // deplacement vers le haut
                        nextX = stepX[i-1];
                        nextY = stepY[i-1] + range;
                        // si le deplacement ne sort pas l'ecran et de revient pas en arriere
                        if(nextY <= height && previousStep != 2) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                    case 2: // deplacement vers le bas
                        nextX = stepX[i-1];
                        nextY = stepY[i-1] - range;
                        // si le deplacement ne sort pas l'ecran et de revient pas en arriere
                        if(nextY >= 0 && previousStep != 1) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                    case 3: // deplacement vers la droite
                        nextX = stepX[i-1] + range;
                        nextY = stepY[i-1];
                        // si le deplacement ne sort pas l'ecran et de revient pas en arriere
                        if(nextX <= width && previousStep != 4) {
                            stepX[i] = nextX;
                            stepY[i] = nextY;
                            isNotChoose = false;
                            previousStep = direction;
                        }
                        break;
                    case 4 : // deplacement vers la gauche
                        nextX = stepX[i-1] - range;
                        nextY = stepY[i-1];
                        // si le deplacement ne sort pas l'ecran et de revient pas en arriere
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
        // tant que l'on rencontre un cul-de-sac et donc que nous n'avons pas fait
        // tout nos pas
        while(deadEnd) {
            deadEnd = false;

            // permet de reinitialiser le déplacement en ca de cul-de-sac
            stepX = new int[step];
            stepY = new int[step];
            stepX[0] = rand.nextInt(0,width);
            stepY[0] =rand.nextInt(0,height);

            for (int i = 1; i < step; i++) {
                boolean isNotChoose = true;

                if (deadEnd) {
                    break;
                }
                boolean alreadyPathUp = false;
                boolean alreadyPathDown = false;
                boolean alreadyPathRight = false;
                boolean alreadyPathLeft = false;

                while (isNotChoose && !deadEnd) {

                    // si on est deja passe par les quatres directions direction alors
                    // c'est cul-de-sac et coupe
                    if (alreadyPathUp && alreadyPathDown && alreadyPathRight && alreadyPathLeft) {
                        deadEnd = true;
                    }

                    //  recupere un entier entre 1 et 4
                    int direction = rand.nextInt(1, 4);


                    int nextX;
                    int nextY;
                    switch (direction) {
                        case 1: // deplacement vers le haut
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] + range;
                            // verifie si on est deja passe par cette case
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == nextY) {
                                    alreadyPathUp = true;
                                    break;
                                }
                            }
                            // verifie si on est deja passe par cette case
                            if (nextY <= height && !alreadyPathUp) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            else if (nextY > height){
                                alreadyPathUp = true;
                            }
                            break;
                        case 2: // deplacement vers le bas
                            nextX = stepX[i - 1];
                            nextY = stepY[i - 1] - range;
                            // verifie si on est deja passe par cette case
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == nextY) {
                                    alreadyPathDown = true;
                                    break;
                                }
                            }
                            // si on est deja passe on ne choisi pas se cette direction
                            if (nextY >= 0 && !alreadyPathDown) {
                                stepX[i] = nextX;
                                stepY[i] = nextY;
                                isNotChoose = false;
                            }
                            else if (nextY < 0){
                                alreadyPathDown = true;
                            }
                            break;
                        case 3: // deplacement vers la droite
                            nextX = stepX[i - 1] + range;
                            nextY = stepY[i - 1];
                            // verifie si on est deja passe par cette case
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == stepY[i - 1]) {
                                    alreadyPathRight = true;
                                    break;
                                }
                            }
                            // si on est deja passe on ne choisi pas se cette direction
                            if (nextX <= width && !alreadyPathRight) {
                                stepX[i] = stepX[i - 1] + range;
                                stepY[i] = stepY[i - 1];
                                isNotChoose = false;
                            }
                            else if (nextX > width){
                                alreadyPathRight = true;
                            }
                            break;
                        case 4: // deplacement vers la gauche
                            nextX = stepX[i - 1] - range;
                            nextY = stepY[i - 1];
                            // verifie si on est deja passe par cette case
                            for (int j = 0; j < i - 1; j++) {
                                if (stepX[j] == nextX && stepY[j] == nextY) {
                                    alreadyPathLeft = true;
                                    break;
                                }
                            }
                            // si on est deja passe on ne choisi pas se cette direction
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
        //affiche le chemin parcouru stocké dans stepX et stepY
        g.setColor(Color.black);
        for (int i = 1; i < step; i++) {
            g.drawLine(stepX[i - 1], stepY[i - 1], stepX[i], stepY[i]);
        }
        // affiche un point vert au départ et un point bleu à la fin
        g.setColor(Color.GREEN);
        g.fillOval(stepX[0] - 5,stepY[0] - 5, 10,10);
        g.setColor(Color.BLUE);
        g.fillOval(stepX[step-1] - 5,stepY[step-1] - 5, 10,10);
    }

    // args[0] = n, args[1] = type, args[2] = seed
    public static void main(String[] args) {

        int seed = Integer.parseInt(args[2]);
        //Initialise la JFrame pour afficher le chemin
        JFrame frame = new JFrame();

        RandomWalk panel = new RandomWalk(Integer.parseInt(args[0]), args[1], seed);
        //set les parametre de la JFrame
        frame.setSize(panel.width,panel.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);

        panel.startWalk();
    }


}
