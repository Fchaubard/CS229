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

        this.worldRefPos = theWorldRefPos;
        this.learningRate = theLearningRate;
        this.lambda = theLambda;
        this.gamma = theGamma;
        this.epsilon = theEpsilon;
        this.qMatrix = new Q();
        this.traces = new EligibilityTraces(lambda, gamma);
        this.currentStateKey = "";
        this.previousStateKey = "";

    }

    public void prepareForNewGame(Position startPosition )
    {
        setScore(0);
        this.position = startPosition;
        this.currentStateKey = "";
        this.previousStateKey = "";
        this.previousChoice = 0;
        this.traces = new EligibilityTraces(lambda, gamma);
    }


    private void learn(Double newReward)
        {
            //Calculate new delta (temporal difference?)
            int provisionalChoice = this.makeEGreedyChoice(this.currentStateKey);
            Double tempDiff = newReward + gamma*this.qMatrix.getQValue(this.currentStateKey,provisionalChoice)
                                        - this.qMatrix.getQValue(this.previousStateKey,this.previousChoice);

            //Update
            this.traces.updateQMatrix(this.qMatrix,tempDiff,learningRate);
            this.traces.updateTraces();
        }

    public Position move(Double newReward, ArrayList<Soldier> soldiers){


        //Update state
        currentStateKey = this.calculateLocalStateKey(soldiers);


        //Learn from experience if not first play of the game
        if (!previousStateKey.equals(""))
        {
            this.learn(newReward);
        }

        //Make choice and leave trace
        int newChoice =  this.makeEGreedyChoice(currentStateKey);
        this.previousChoice = newChoice;
        this.previousStateKey = this.currentStateKey;
        this.traces.addTrace(this.previousStateKey,this.previousChoice);  //This was missing before


        return this.convertChoiceToPosition(newChoice);

    }


    private Position convertChoiceToPosition(int choice)
    {
        int x=this.position.getX();
        int y=this.position.getY();
        int a=this.position.getAngle();

        Position newPosition = new Position(this.position);
        boolean jumpingOverEdge = false;

        switch(choice) {
            case 0:    // Attack
                // Dont change position

                break;
            case 1:   // Go left

                newPosition.setAngle(a+90);
                break;
            case 2:   // Go right

                newPosition.setAngle(a-90);
                break;
            case 3:  // Move Forward

                switch(newPosition.getAngle()) {
                    case 0:
                        if(newPosition.getX()<sizeOfEnvironmentX-1){
                            newPosition.setX(x+1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                            jumpingOverEdge = true;
                        }
                        break;
                    case 90:
                        if(newPosition.getY()<sizeOfEnvironmentY-1){
                            newPosition.setY(y+1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                            jumpingOverEdge = true;
                        }
                        break;
                    case 180:
                        if(newPosition.getX()>0){
                            newPosition.setX(x-1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                            jumpingOverEdge = true;
                        }
                        break;
                    case 270:
                        if(newPosition.getY()>0){
                            newPosition.setY(y-1);
                        }
                        else{
                            //System.out.print("trying to jump over the edge!\n");
                            jumpingOverEdge = true;
                        }
                        break;
                    default:
                         System.out.printf("not a valid angle %d", newPosition.getAngle());
                }
                break;
            default: System.out.print("Invalid move");
                break;


        }

        if(!jumpingOverEdge&&(newPosition.getAngle()==position.getAngle())&&(newPosition.getX()==position.getX())&&(newPosition.getY()==position.getY())&&choice!=0) {
            System.out.print("big issue");
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
            Double[] actionValues = this.qMatrix.getStateActionValues(stateKey);

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
            if (max>1.0){
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

        Position myWorldPosition = this.position;//positions.get(identifier);

        //Calculate world coordinates reference key
        int refX = -worldRefPos.getX() + myWorldPosition.getX();
        int refY = -worldRefPos.getY() + myWorldPosition.getY();
        int refAngle = -worldRefPos.getAngle() + myWorldPosition.getAngle();

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
            if (soldiers.get(i).getTeamIdentifier() != this.teamIdentifier)
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
            else if (soldiers.get(i).getIdentifier() != this.identifier)
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
    public Q getQMatrix() {
        return qMatrix;
    }

    public void setQMatrix(Q qMatrix) {
        this.qMatrix = qMatrix;
    }

}
