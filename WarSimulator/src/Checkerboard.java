import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Checkerboard {
    private static CheckerboardPanel panel;
    private static Environment environment;
    private static int numberOfTeams=2;
    private static int numberOfSoldiersPerTeam=2;
    private static int stepLimit=3000;
    private static int sizeOfEnvironmentX=10;
    private static int sizeOfEnvironmentY=10;
    private static int numberOfGames=10;
    private static boolean gameOver=false;
    private static boolean showCheckerBoard=true;


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
        for (int i=0; i<(numberOfSoldiersPerTeam*numberOfTeams); i++){

            int teamNumber =0;
            if(i>=numberOfSoldiersPerTeam){
                teamNumber = 1;
            }
            Position pos = new Position(i,i,0);//set the positions in initialize game
            Soldier soldier = new Soldier(i,teamNumber,numberOfSoldiersPerTeam,numberOfTeams, pos);
            myList.add(soldier);
        }

        Soldier[] soldiers = myList.toArray(new Soldier[myList.size()]);

        environment = new Environment(numberOfTeams,numberOfSoldiersPerTeam,soldiers,stepLimit,sizeOfEnvironmentX,sizeOfEnvironmentY);

    }

    public static void main(String[] args){

        if(showCheckerBoard)    {

          Checkerboard checkerboard = new Checkerboard();

        }
        int gameNumber = 0;
        setUpEnvironment();
        environment.initializeGame();
        // Game loop
        while (numberOfGames>gameNumber) {
            if (environment.getCurrentStep()>=stepLimit){
                // TODO game analysis and breakdown
                environment.resetGame();
                environment.initializeGame();
                gameNumber = gameNumber + 1;
               // break the loop to finish the current play

            }

            System.out.printf("Game %d Step %d : ",gameNumber,environment.getCurrentStep());
            for (Soldier a:environment.getSoldiers()){
                System.out.printf(" S%d x=%d y=%d a=%d",a.getIdentifier(),a.getPosition().getX(),a.getPosition().getY(),a.getPosition().getAngle());

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

    }

}
