import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
* Created with IntelliJ IDEA.
* User: francoischaubard
* Date: 11/9/12
* Time: 4:40 PM
* To change this template use File | Settings | File Templates.
*/
public class EnvironmentTest {

    public static Environment testEnvironment;

    @Before
    public void setUpEnvironment() throws Exception {
        int numberOfTeams=2;
        int numberOfSoldiersPerTeam=2;
        int stepLimit=3;
        int sizeOfEnvironmentX=10;
        int sizeOfEnvironmentY=10;
        List<Soldier> myList = new ArrayList<Soldier>();


        // Form Teams
        for (int i=0; i<(numberOfSoldiersPerTeam*numberOfTeams); i++){

            int teamNumber =0;
            if(i>=numberOfSoldiersPerTeam){
                teamNumber = 1;
            }
            Position pos = new Position(i,i,i);//set the positions in initialize game
            Soldier soldier = new Soldier(i,teamNumber,numberOfSoldiersPerTeam,numberOfTeams, pos);
            myList.add(soldier);
        }

        Soldier[] soldiers = myList.toArray(new Soldier[myList.size()]);

        testEnvironment = new Environment(numberOfTeams,numberOfSoldiersPerTeam,soldiers,stepLimit,sizeOfEnvironmentX,sizeOfEnvironmentY);

    }

    @Test
    public void testNormal() throws Exception {

        // Start an Environment, step it once, reset the game, step again, stop the game, and make sure you have what you need
        testInitializeGame();
        testStepGame();
        testResetGame();
        testStepGame();
    }




    @After
    public void tearDown() throws Exception {

    }


    public static void testInitializeGame() throws Exception {

        //Ensure things are setup correctly
        assert(testEnvironment.getCurrentStep()==-1);

        //Initialize the Game
        testEnvironment.initializeGame();

        testPositions();

        //Ensure things were initialized correctly
        assert(testEnvironment.getCurrentStep()==0);

    }

    public static void testPositions() throws Exception{

        int positionsArentEqual = 0;
        Set<Integer> xPos = new TreeSet<Integer>();
        Set<Integer> yPos = new TreeSet<Integer>();

        // ensure of the positions are equal by putting them into a set
        for (int i=0; i<testEnvironment.getSoldiers().length; i++){
            Position position = testEnvironment.getSoldier(i).getPosition();

            try{
                xPos.add(position.getX());
                yPos.add(position.getY());
            }
            catch(Exception e){
                positionsArentEqual = 0;
                break;
            }

        }
        if(yPos.size()==testEnvironment.getSoldiers().length){
            positionsArentEqual = 1;
        }

        assert(positionsArentEqual==1);
    }

    public static void testStepGame() throws Exception {

        //Ensure things are setup correctly
        assert(testEnvironment.getCurrentStep()==0);

        //Initialize the Game
        testEnvironment.stepGame();

        //Ensure things were initialized correctly
        assert(testEnvironment.getCurrentStep()==1);
    }

    public static void testResetGame() throws Exception {

        //Reset and initialize the Game
        testEnvironment.resetGame();
        //Ensure things were initialized correctly
        assert(testEnvironment.getCurrentStep()==0);
    }

}