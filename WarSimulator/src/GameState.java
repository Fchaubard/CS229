import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 2012-11-15
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class GameState {
    //Attributes
    ArrayList<ArrayList<Position> > positions;

    //Constructors
    public GameState(int numberOfTeams, int numberOfSoldiersPerTeam)
    {
        positions = new ArrayList<ArrayList<Position> >();
        for (int i = 0; i < numberOfTeams; ++i)
        {
            positions.add(i, new ArrayList<Position>());

            for (int j = 0; j < numberOfSoldiersPerTeam; ++j)
            {
               positions.get(i).add(j, null);
            }

        }
    }


    //Methods
    public Position getSoldierPosition(int team, int soldier)
    {
        return positions.get(team).get(soldier);
    }

    public void setSoldierPosition(int team, int soldier, Position position)
    {
        positions.get(team).set(soldier, position);
    }

}
