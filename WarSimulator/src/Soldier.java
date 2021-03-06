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
    public Position move(Double newReward, ArrayList<Soldier> soldiers);
    public Position getPosition();
    public void setPosition(Position p);
    public int getIdentifier();
    public void setIdentifier(int i);
    public int getTeamIdentifier();
    public void setTeamIdentifier(int i);
    public int getScore();
    public void setScore(int i);
    public void setQMatrix(Q qMatrix);
    public Q getQMatrix();



}
