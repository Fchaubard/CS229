/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 10/31/12
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */

public class Soldier {

    //Attributes
    private static int identifier;
    private static int teamIdentifier;
    private static int numberOfFriends;
    private static int numberOfFoes;


    public Soldier(int identifier, int teamIdentifier, int numberOfFriends, int numberOfFoes) {
        Soldier.setIdentifier(identifier);
        Soldier.setTeamIdentifier(teamIdentifier);
        Soldier.setNumberOfFriends(numberOfFriends);
        Soldier.setNumberOfFoes(numberOfFoes);
    }

    public static int getIdentifier() {
        return identifier;
    }

    public static void setIdentifier(int identifier) {
        Soldier.identifier = identifier;
    }

    public static int getTeamIdentifier() {
        return teamIdentifier;
    }

    public static void setTeamIdentifier(int teamIdentifier) {
        Soldier.teamIdentifier = teamIdentifier;
    }

    public static int getNumberOfFriends() {
        return numberOfFriends;
    }

    public static void setNumberOfFriends(int numberOfFriends) {
        Soldier.numberOfFriends = numberOfFriends;
    }

    public static int getNumberOfFoes() {
        return numberOfFoes;
    }

    public static void setNumberOfFoes(int numberOfFoes) {
        Soldier.numberOfFoes = numberOfFoes;
    }


}
