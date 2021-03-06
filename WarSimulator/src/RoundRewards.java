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

    // Constructor
    public RoundRewards(int numberOfSoldiers)
    {
        this.rewards = new ArrayList<Double>();
        for (int i = 0; i < numberOfSoldiers; ++i)
        {
            this.rewards.add(i, 0.0);

        }
    }

    //Methods
    public Double getSoldierReward(int soldierNumber)
    {
        return this.rewards.get(soldierNumber);
    }

    public void setSoldierReward(int soldierNumber, Double reward)
    {
        this.rewards.set(soldierNumber, reward);
    }



    public void punishInactivity(Double uniformPunishment)
    {
        for(int i=0; i<rewards.size(); i++)
        {
            rewards.set(i, rewards.get(i)-uniformPunishment);

        }
    }
}
