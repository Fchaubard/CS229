import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 10/31/12
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */

public class SmartSoldier implements Soldier{

    //Attributes

    // Generics
    private  int identifier;
    private  int teamIdentifier;
    private  int numberOfTeams;
    private  int numberOfSoldersPerTeam;
    private Position position;
    private int score=0;
    private int sizeOfEnvironmentX;
    private int sizeOfEnvironmentY;

    // Smart Variables
    private Q qMatrix;
    private EligibilityTraces traces;

    //Static variables
    // State variables
    private String currentStateKey;
    private String previousStateKey;
    private int previousChoice;
    // Game descriptors

    private static Position worldRefPos; //for reference.

    //Learning parameters
    private static Double lambda;
    private static Double gamma;
    private static Double epsilon;
    private static Double learningRate;




    //Constructors
    public SmartSoldier(int identifier, int teamIdentifier,  Position position, int sizeOfEnvironmentX, int sizeOfEnvironmentY,
                   Position theWorldRefPos,
                   Double theLambda, Double theGamma, Double theLearningRate, Double theEpsilon) {

        this.setIdentifier(identifier);
        this.setTeamIdentifier(teamIdentifier);
        this.setPosition(position);
        this.sizeOfEnvironmentX = sizeOfEnvironmentX;
        this.sizeOfEnvironmentY = sizeOfEnvironmentY;

        worldRefPos = theWorldRefPos;
        learningRate = theLearningRate;
        lambda = theLambda;
        gamma = theGamma;
        epsilon = theEpsilon;
        qMatrix = new Q();
        traces = new EligibilityTraces(lambda, gamma);
        currentStateKey = "";
        previousStateKey = "";
        //sizeOfEnvironmentX = theSizeOfEnvironmentX;
        //sizeOfEnvironmentY = theSizeOfEnvironmentY;

    }

    public void prepareForNewGame(Position startPosition )
    {
        setScore(0);
        position = startPosition;
        currentStateKey = "";
        previousStateKey = "";
        previousChoice = 0;
        traces = new EligibilityTraces(lambda, gamma);
    }


    private void learn(Double newReward)
        {
            //Calculate new delta (temporal difference?)
            int provisionalChoice = makeEGreedyChoice(currentStateKey);
            Double tempDiff = newReward + gamma*qMatrix.getQValue(currentStateKey,provisionalChoice)
                                        - qMatrix.getQValue(previousStateKey,previousChoice);

            //Update
            traces.updateQMatrix(qMatrix,tempDiff,learningRate);
            traces.updateTraces();
        }

    public Position move(Double newReward, ArrayList<Soldier> soldiers){


        //Update state
        currentStateKey = calculateLocalStateKey(soldiers);


        //Learn from experience if not first play of the game
        if (!previousStateKey.equals(""))
        {
          learn(newReward);
        }

        //Make choice and leave trace
        int newChoice =  makeEGreedyChoice(currentStateKey);
        previousChoice = newChoice;
        previousStateKey = currentStateKey;
        traces.addTrace(previousStateKey,previousChoice);  //This was missing before


        return convertChoiceToPosition(newChoice);

    }


    private Position convertChoiceToPosition(int choice)
    {
        Position newPosition = position;


        switch(choice) {
            case 0:    // Attack
                // Dont change position

                break;
            case 1:   // Go left

                newPosition.setAngle(position.getAngle()+90);
                break;
            case 2:   // Go right

                newPosition.setAngle(position.getAngle()-90);
                break;
            case 3:  // Move Forward

                switch(newPosition.getAngle()) {
                    case 0:
                        if(newPosition.getX()<sizeOfEnvironmentX-1){
                            newPosition.setX(position.getX()+1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                        }
                        break;
                    case 90:
                        if(newPosition.getY()<sizeOfEnvironmentY-1){
                            newPosition.setY(position.getY()+1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                        }
                        break;
                    case 180:
                        if(newPosition.getX()>0){
                            newPosition.setX(position.getX()-1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                        }
                        break;
                    case 270:
                        if(newPosition.getY()>0){
                            newPosition.setY(position.getY()-1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                        }
                        break;
                    default:
                        // System.out.printf("not a valid angle %d", newPosition.getAngle());
                }
                break;
            default: System.out.print("Invalid move");
                break;


        }

        // Submit the newPosition
        return newPosition;
    }


    private int makeEGreedyChoice(String stateKey)
    {
        Random random = new Random();
        Double eps = random.nextDouble();
        if (eps < epsilon)
        {
            //Explore by make random move
            return random.nextInt(4);
        } else
        {
            Double[] actionValues = qMatrix.getStateActionValues(stateKey);

            //Find integer with maximum Q-value
            int move = 0;
            Double max = Collections.max((Arrays.asList(actionValues)));
            int a;  //for debugging purposes


            ArrayList<Integer> potentialMoves = new ArrayList<Integer>();

            for (int i = 0; i < actionValues.length; i++)
            {
                if (actionValues[i] == max.doubleValue())
                {
                    max = actionValues[i];
                    potentialMoves.add(i);
                }
            }

            if (potentialMoves.size()!=4)
                a=1;     //for debugging purposes
            if (max.doubleValue()>1.0){
                a=1;   //for debugging purposes
            }
            //System.out.printf("actionValue 0=%f, 1=%f, 2=%f, 3=%f \n\n", actionValues[0],actionValues[1],actionValues[2],actionValues[3]);

            int choice = random.nextInt(potentialMoves.size());  // gives either a 0, 1, 2, or 3

            move = potentialMoves.get(choice);

            return move;
        }
    }


    private String calculateLocalStateKey(ArrayList<Soldier> soldiers)
    {
        //TODO improve this key generator

        //Initialize sub keys
        String teamKey = "";
        String refKey = "";
        String enemiesKey = "";

        Position myWorldPosition = position;//positions.get(identifier);

        //Calculate world coordinates reference key
        int refX = -worldRefPos.getX() + myWorldPosition.getX();
        int refY = -worldRefPos.getY() + myWorldPosition.getY();
        int refAngle = -worldRefPos.getY() + myWorldPosition.getAngle();

        //Make sure angle is one of 0, 90, 180, 270
        if (refAngle < 0)
        {
            refAngle += 360; //should never hit this
        }
        //TODO make all the angles give constant length strings i.e. 000, 090...
        if (refAngle==0)
            refKey += Integer.toString(refX) + Integer.toString(refY) + "000";
        else if(refAngle==90)
            refKey += Integer.toString(refX) + Integer.toString(refY) + "090";
        else
            refKey += Integer.toString(refX) + Integer.toString(refY) + Integer.toString(refAngle);

        //Calculate team and enemies keys
        for(int i = 0; i < soldiers.size(); i++)
        {

            //Enemies
            if (soldiers.get(i).getTeamIdentifier() != teamIdentifier)
            {
                Position enemyPosition = soldiers.get(i).getPosition();
                int enemyX = enemyPosition.getX() - myWorldPosition.getX();
                int enemyY = enemyPosition.getY() - myWorldPosition.getY();
                int enemyAngle = enemyPosition.getAngle() - myWorldPosition.getAngle();

                //Make sure angle is one of 0, 90, 180, 270
                if (enemyAngle < 0)
                {
                    enemyAngle += 360;             //should never hit this
                }
                if (enemyAngle==0)
                    enemiesKey += Integer.toString(enemyX) + Integer.toString(enemyY) + "000";
                else if(enemyAngle==90)
                    enemiesKey += Integer.toString(enemyX) + Integer.toString(enemyY) + "090";
                else
                    enemiesKey += Integer.toString(enemyX) + Integer.toString(enemyY) + Integer.toString(enemyAngle);


            }
            //Team mates
            else if (soldiers.get(i).getIdentifier() != identifier)
            {
                Position memberPosition = soldiers.get(i).getPosition();
                int memberX = memberPosition.getX() - myWorldPosition.getX();
                int memberY = memberPosition.getY() - myWorldPosition.getY();
                int memberAngle = memberPosition.getAngle() - myWorldPosition.getAngle();

                //Make sure angle is one of 0, 90, 180, 270
                if (memberAngle < 0)
                {
                    memberAngle += 360;     //should never hit this
                }
                if (memberAngle==0)
                    teamKey += Integer.toString(memberX) + Integer.toString(memberY) + "000";
                else if(memberAngle==90)
                    teamKey += Integer.toString(memberX) + Integer.toString(memberY) + "090";
                else
                    teamKey += Integer.toString(memberX) + Integer.toString(memberY) + Integer.toString(memberAngle);


            }
        }


        //String key =  teamKey + enemiesKey + Integer.toString(sizeOfEnvironmentX-position.getX()) + Integer.toString(sizeOfEnvironmentY-position.getY()) + Integer.toString(position.getAngle());  //
         String key =refKey + teamKey + enemiesKey;
        //System.out.print(key+"\n");
        //Generate and return the state key
        return key;


    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public  int getIdentifier() {
        return identifier;
    }

    public  void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public  int getTeamIdentifier() {
        return teamIdentifier;
    }

    public  void setTeamIdentifier(int teamIdentifier) {
        this.teamIdentifier = teamIdentifier;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
