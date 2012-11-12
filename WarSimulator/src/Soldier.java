import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 10/31/12
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */

public class Soldier {

    //Attributes
    private  int identifier;
    private  int teamIdentifier;
    private  int numberOfFriends;
    private  int numberOfFoes;


    private Position position;

    public Soldier(int identifier, int teamIdentifier, int numberOfFriends, int numberOfFoes, Position position) {
        this.setIdentifier(identifier);
        this.setTeamIdentifier(teamIdentifier);
        this.setPosition(position);
        this.setNumberOfFriends(numberOfFriends);
        this.setNumberOfFoes(numberOfFoes);
    }

    public Position move(){

        // TODO Decision Process happens here
        Random random = new Random(123);
        int moveNumber = random.nextInt(4);  // gives either a 0, 1, 2, or 3

        Position newPosition = this.position;

        switch(moveNumber) {
            case 0:    // Attack
                // Dont change position

                break;
            case 1:   // Go left

                newPosition.setAngle(newPosition.getAngle()+90);
                break;
            case 2:   // Go right

                newPosition.setAngle(newPosition.getAngle()-90);
                break;
            case 3:  // Move Forward

                switch(newPosition.getAngle()) {
                    case 0:
                        newPosition.setX(newPosition.getX()+1);
                        break;
                    case 90:
                        newPosition.setY(newPosition.getY()+1);
                        break;
                    case 180:
                        newPosition.setX(newPosition.getX()-1);
                        break;
                    case 270:
                        newPosition.setY(newPosition.getY()-1);
                        break;
                    default:
                        System.out.print("not a valid angle");
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

    public  int getNumberOfFriends() {
        return numberOfFriends;
    }

    public  void setNumberOfFriends(int numberOfFriends) {
        this.numberOfFriends = numberOfFriends;
    }

    public  int getNumberOfFoes() {
        return numberOfFoes;
    }

    public  void setNumberOfFoes(int numberOfFoes) {
        this.numberOfFoes = numberOfFoes;
    }


}
