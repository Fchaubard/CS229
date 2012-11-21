import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 10/31/12
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */

public class DumbSoldier implements Soldier {

    //Attributes
    private  int identifier;
    private  int teamIdentifier;
    private Position position;
    private int score=0;
    private boolean reallyDumb=true;
    private int sizeOfEnvironmentX;
    private int sizeOfEnvironmentY;

    public DumbSoldier(int identifier, int teamIdentifier, Position position, int sizeOfEnvironmentX, int sizeOfEnvironmentY) {
        this.setIdentifier(identifier);
        this.setTeamIdentifier(teamIdentifier);
        this.setPosition(position);
        this.sizeOfEnvironmentX = sizeOfEnvironmentX;
        this.sizeOfEnvironmentY = sizeOfEnvironmentY;
    }

    public void prepareForNewGame(Position startPosition ){
        setPosition(startPosition);
        setScore(0);
    }

    public Position move(Double newReward, ArrayList<Soldier> soldiers){

        //Extra dumb -> stand still
        Position newPosition = new Position(position.getX(),position.getY(),position.getAngle());

        // TODO Decision Process happens here
        Random random = new Random();
        int choice = random.nextInt(4);  // gives either a 0, 1, 2, or 3

        if(reallyDumb)
            choice =0;
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
