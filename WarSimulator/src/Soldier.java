import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 11/19/12
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Soldier {

    // implement all methods you want to have polymorphism
    public void prepareForNewGame(Position startPosition);
    public Position move(Double newReward, ArrayList<Soldier> soldier, int sizeOfEnvironmentX, int sizeOfEnvironmentY);
    public Position getPosition();
    public void setPosition(Position p);
    public int getIdentifier();
    public void setIdentifier(int i);
    public int getTeamIdentifier();
    public void setTeamIdentifier(int i);
    public int getScore();
    public void setScore(int i);
    public Position convertChoiceToPosition(int choice, int sizeOfEnvironmentX, int sizeOfEnvironmentY);



}
