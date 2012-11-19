import java.util.ArrayList;
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

    // Smart Variables
    private Q qMatrix;
    private EligibilityTraces traces;

    //Static variables
    // State variables
    private String currentStateKey;
    private String previousStateKey;
    private int previousChoice;
    // Game descriptors
    private static int sizeOfEnvironmentX;
    private static int sizeOfEnvironmentY;
    private static Position worldRefPos; //for reference.

    //Learning parameters
    private static Double lambda;
    private static Double gamma;
    private static Double epsilon;
    private static Double learningRate;




    //Constructors
    public SmartSoldier(int identifier, int teamIdentifier,  Position position,
                   Position theWorldRefPos,
                   Double theLambda, Double theGamma, Double theLearningRate, Double theEpsilon) {

        this.setIdentifier(identifier);
        this.setTeamIdentifier(teamIdentifier);
        this.setPosition(position);
        worldRefPos = theWorldRefPos;
        learningRate = theLearningRate;
        lambda = theLambda;
        gamma = theGamma;
        epsilon = theEpsilon;
        qMatrix = new Q();
        traces = new EligibilityTraces(lambda, gamma);
        //sizeOfEnvironmentX = theSizeOfEnvironmentX;
        //sizeOfEnvironmentY = theSizeOfEnvironmentY;

    }

    public void prepareForNewGame(Position startPosition )
    {
        setScore(0);
        position = startPosition;
        currentStateKey = "";
        previousChoice = 0;
        currentStateKey = "";
        traces = new EligibilityTraces(lambda, gamma);
    }


    private void learn(Double newReward)
        {
            //Calculate new delta (temporal difference?)
            int provisionalChoice = makeEGreedyChoice(currentStateKey);
            Double tempDiff = newReward + gamma*qMatrix.getQValue(currentStateKey,provisionalChoice)
                                        + qMatrix.getQValue(previousStateKey,previousChoice);

            //Update
            traces.updateQMatrix(qMatrix,tempDiff,learningRate);
            traces.updateTraces();
        }

    public Position move(Double newReward, ArrayList<Position> newGamePositions, int sizeOfEnvironmentX, int sizeOfEnvironmentY){

        //Update state
        currentStateKey = calculateLocalStateKey(newGamePositions);


        //Learn from experience if not first play of the game
        if (!previousStateKey.equals(""))
        {
          learn(newReward);
        }


        int newChoice =  makeEGreedyChoice(currentStateKey);
        previousChoice = newChoice;
        previousStateKey = currentStateKey;


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
            Double max = 0.0;
            for (int i = 0; i < 4; ++i)
            {
                if (actionValues[i] > max)
                {
                    max = actionValues[i];
                    move = i;
                }
            }
            return move;
        }
    }


    private String calculateLocalStateKey(ArrayList<Position> positions)
    {
        //TODO improve this key generator

        //Initialize sub keys
        String teamKey = "";
        String refKey = "";
        String enemiesKey = "";

        Position myWorldPosition = positions.get(identifier);

        //Calculate world coordinates reference key
        int refX = worldRefPos.getX() - myWorldPosition.getX();
        int refY = worldRefPos.getY() - myWorldPosition.getY();
        int refAngle = worldRefPos.getY() - myWorldPosition.getAngle();

        //Make sure angle is one of 0, 90, 180, 270
        if (refAngle < 0)
        {
            refAngle += 360;
        }
        //TODO make all the angles give constant length strings i.e. 000, 090...
        refKey += Integer.toString(refX) + Integer.toString(refY) + Integer.toString(refAngle);

        //Calculate team and enemies keys
        for(int team = 0; team < numberOfTeams; ++team)
        {
            for (int member = 0; member < numberOfSoldersPerTeam; ++team)
            {
                //Enemies
                if (team != teamIdentifier)
                {
                    Position enemyPosition = positions.get(member + team*(numberOfSoldersPerTeam+1));
                    int enemyX = enemyPosition.getX() - myWorldPosition.getX();
                    int enemyY = enemyPosition.getY() - myWorldPosition.getY();
                    int enemyAngle = enemyPosition.getAngle() - myWorldPosition.getAngle();

                    //Make sure angle is one of 0, 90, 180, 270
                    if (enemyAngle < 0)
                    {
                        enemyAngle += 360;
                    }

                    enemiesKey += Integer.toString(enemyX) + Integer.toString(enemyY) + Integer.toString(enemyAngle);
                }
                //Team mates
                else if (member != identifier)
                {
                    Position memberPosition = positions.get(member + team*(numberOfSoldersPerTeam+1));
                    int memberX = memberPosition.getX() - myWorldPosition.getX();
                    int memberY = memberPosition.getY() - myWorldPosition.getY();
                    int memberAngle = memberPosition.getAngle() - myWorldPosition.getAngle();

                    //Make sure angle is one of 0, 90, 180, 270
                    if (memberAngle < 0)
                    {
                        memberAngle += 360;
                    }

                    teamKey += Integer.toString(memberX) + Integer.toString(memberY) + Integer.toString(memberAngle);
                }
            }
        }


        //Generate and return the state key
        return refKey + teamKey + enemiesKey;
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