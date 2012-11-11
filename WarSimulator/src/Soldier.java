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
        int moveNumber = random.nextInt(4);

        switch(moveNumber) {
            case 0:    // Attack
                // TODO
                break;
            case 1:   // Go left
                // TODO
                break;
            case 2:   // Go right
                // TODO
                break;
            case 3:  // Move Forward
                // TODO
                break;
            default: System.out.print("Invalid move");
                break;

        }

        // Submit the newPosition
        Position newPosition = this.position;
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
