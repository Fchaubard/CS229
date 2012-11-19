import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 2012-11-15
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class RoundRewards {
    //Attributes
    ArrayList<Double> rewards;

    //Constructors
    /*public RoundRewards(int numberOfTeams, int numberOfSoldiers)
    {
        rewards = new ArrayList<ArrayList<Double> >();
        for (int i = 0; i < numberOfTeams; ++i)
        {
            rewards.add(i, new ArrayList<Double>());

            for (int j = 0; j < numberOfSoldiers; ++j)
            {
                rewards.get(i).add(j, 0.0);
            }

        }
    }   */

    public RoundRewards(int numberOfSoldiers)
    {
        rewards = new ArrayList<Double>();
        for (int i = 0; i < numberOfSoldiers; ++i)
        {
            rewards.add(i, 0.0);

        }
    }

    //Methods
    public Double getSoldierReward(int soldierNumber)
    {
        return rewards.get(soldierNumber);
    }

    public void setSoldierReward(int soldierNumber, Double reward)
    {
        rewards.set(soldierNumber, rewards.get(soldierNumber)+reward);
    }

    public void rewardTeam(int soldierNumber, Double reward)
    {
         rewards.set(soldierNumber, rewards.get(soldierNumber)+reward);

    }

    public void punishInactivity(Double uniformPunishment)
    {
        for(Double a: rewards)
        {
              a-=uniformPunishment;
        }
    }
}
