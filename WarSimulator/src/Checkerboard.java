import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Checkerboard {
    private static CheckerboardPanel panel;
    private static Environment environment;
    private static int numberOfTeams=2;
    private static int numberOfSoldiersPerTeam=1;
    private static int stepLimit=80;
    private static int sizeOfEnvironmentX=10;
    private static int sizeOfEnvironmentY=10;
    private static int numberOfGames=5000;
    private static boolean showCheckerBoard=false;
    private static Position referencePosition = new Position(0,0,0);
    private static int gameStyle=2;   //1 dumb v dumb 2 dumb v smart 3 smart v smart

    private static double lambda = 0.0;
    private static double gamma = 0.5;
    private static double learningRate = 0.125;
    private static double epsilon = 0.01;
    private static double inactivityPunishment = 0.05;
    private static ArrayList<ArrayList<Integer>> gameResults = new ArrayList<ArrayList<Integer>>(numberOfGames); //numGames x numSoldiers


    public static void main(String[] args){

        if(showCheckerBoard){
            // show the checkerboard
            Checkerboard checkerboard = new Checkerboard();

        }

        // initialize the environment with soldiers
        setUpEnvironment();

        // initialize game results
        setUpGameResults();

        environment.initializeGame();

        int gameNumber = 0;
        // Game loop
        while (numberOfGames>gameNumber) {
            if (environment.getCurrentStep()>=stepLimit){
                // TODO game analysis and breakdown
                for (Soldier a:environment.getSoldiers())
                    gameResults.get(gameNumber).set(a.getIdentifier(),a.getScore());

                environment.resetGame();
                environment.initializeGame();
                gameNumber = gameNumber + 1;
                // break the loop to finish the current play

            }

            System.out.printf("Game %d Step %d : ",gameNumber,environment.getCurrentStep());
            for (Soldier a:environment.getSoldiers()){
                System.out.printf(" S%d x=%d y=%d a=%d",a.getIdentifier(),a.getPosition().getX(),a.getPosition().getY(),a.getPosition().getAngle());
                //for  (Soldier b:environment.getSoldiers()){
                //        if(a.getIdentifier()!=b.getIdentifier() &&environment.arePositionsEqual(a.getPosition(),b.getPosition())){
                //            System.out.printf("ISSUE S%d S%d \n\n\n\n\n\n\n\n\n\n",a.getIdentifier(), b.getIdentifier());
                //        }

                //}
            }
            System.out.printf("\n");

            // Update the state and position of all the game objects,
            // detect collisions and provide responses.
            environment.stepGame();

            if(showCheckerBoard)    {

                // Refresh the display
                panel.repaint();
            }

            // Delay timer to provide the necessary delay to meet the target rate.
        }
        if(showCheckerBoard)    {

            // Refresh the display
            panel.repaint();
        }

        printResults();

    }// main

    private static void printResults() {


        System.out.printf("Printing results for each game: \n");
        ArrayList<Integer> tally = new ArrayList<Integer>(numberOfSoldiersPerTeam*numberOfTeams);
        for (int soldierNumber=0; soldierNumber<environment.getSoldiers().size(); soldierNumber++){
            tally.add(0);
        }

        for (int gameNumber=0; gameNumber<gameResults.size(); gameNumber++){
            System.out.printf("%d ",gameNumber);

            for (int soldierNumber=0; soldierNumber<environment.getSoldiers().size(); soldierNumber++){
                System.out.printf(" %d ",gameResults.get(gameNumber).get(soldierNumber));
                tally.set(soldierNumber,(tally.get(soldierNumber)+gameResults.get(gameNumber).get(soldierNumber)));
            }
            System.out.printf("\n");
        }
        System.out.printf("-------------------------------\n");
        System.out.printf("Total: \n");
        for (int soldierNumber=0; soldierNumber<environment.getSoldiers().size(); soldierNumber++){
            System.out.printf(" %d ",tally.get(soldierNumber));
        }
    }

    public Checkerboard() {
        final JFrame frame = new JFrame("Checkerboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new CheckerboardPanel(sizeOfEnvironmentX,sizeOfEnvironmentY);
        frame.getContentPane().add(panel);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void setUpEnvironment(){

        List<Soldier> myList = new ArrayList<Soldier>();

        // Form Teams
        for (int i=0; i<(numberOfSoldiersPerTeam); i++){
            for (int j=0; j<(numberOfTeams); j++){

                Position pos = new Position(i,i,0);//set the positions in initialize game
                Soldier soldier;
                if(gameStyle==1){    //dumb soldiers
                    soldier = new DumbSoldier((i+j*(numberOfSoldiersPerTeam)),j,pos);
                }
                else if(gameStyle==2){    // dumb vs smart soldiers
                    if(j==0)
                        soldier = new DumbSoldier((i+j*(numberOfSoldiersPerTeam)),j,pos);
                    else
                        soldier = new SmartSoldier((i+j*(numberOfSoldiersPerTeam)),j, pos, referencePosition, lambda, gamma,learningRate, epsilon);

                }
                else             //smart soldiers
                    soldier = new SmartSoldier((i+j*(numberOfSoldiersPerTeam)),j, pos, referencePosition, lambda, gamma,learningRate, epsilon);


                myList.add(soldier);
            }
        }

        Soldier[] soldiers = myList.toArray(new Soldier[myList.size()]);

        environment = new Environment(numberOfTeams,numberOfSoldiersPerTeam,soldiers,stepLimit,sizeOfEnvironmentX,sizeOfEnvironmentY, inactivityPunishment);

    }

    private static void setUpGameResults() {
        //gameResults = new ArrayList(numberOfGames);

        for (int i=0; i<numberOfGames; i++){
            gameResults.add(i, new ArrayList<Integer>(numberOfSoldiersPerTeam * numberOfTeams));
            for (int j=0; j<(numberOfSoldiersPerTeam*numberOfTeams); j++){
                gameResults.get(i).add(j, 0);
            }
        }
    }

}
