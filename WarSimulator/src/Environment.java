/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 11/1/12
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Environment {
    private static int numberOfTeams;
    private static int numberOfSoldiersPerTeam;
    private static Position[] positions= new Position[numberOfSoldiersPerTeam*numberOfTeams]; // (Matrix #teams x #soldiersperteam)
    private static int currentStep;
    private static int stepLimit;
    private static int sizeOfEnvironmentX;
    private static int sizeOfEnvironmentY;

    public Environment(int numberOfTeams, int numberOfSoldiersPerTeam, Position[] initial_Positions, int stepLimit, int sizeOfEnvironmentX, int sizeOfEnvironmentY) {
        Environment.setPositions(initial_Positions);
        Environment.setNumberOfTeams(numberOfTeams);
        Environment.setNumberOfSoldiersPerTeam(numberOfSoldiersPerTeam);
        Environment.setStepLimit(stepLimit);
        Environment.setSizeOfEnvironmentX(sizeOfEnvironmentX);
        Environment.setSizeOfEnvironmentY(sizeOfEnvironmentY);

    }

    public static void initializeGame(){

    }

    public static void stepGame(){

    }

    public static void stopGame(){

    }

    public static void resetGame(){

    }

    public static int getNumberOfTeams() {
        return numberOfTeams;
    }

    public static void setNumberOfTeams(int numberOfTeams) {
        Environment.numberOfTeams = numberOfTeams;
    }

    public static int getNumberOfSoldiersPerTeam() {
        return numberOfSoldiersPerTeam;
    }

    public static void setNumberOfSoldiersPerTeam(int numberOfSoldiersPerTeam) {
        Environment.numberOfSoldiersPerTeam = numberOfSoldiersPerTeam;
    }

    public static Position[] getPositions() {
        return positions;
    }

    public static void setPositions(Position[] positions) {
        Environment.positions = positions;
    }

    public static int getCurrentStep() {
        return currentStep;
    }

    public static void setCurrentStep(int currentStep) {
        Environment.currentStep = currentStep;
    }

    public static int getStepLimit() {
        return stepLimit;
    }

    public static void setStepLimit(int stepLimit) {
        Environment.stepLimit = stepLimit;
    }

    public static int getSizeOfEnvironmentX() {
        return sizeOfEnvironmentX;
    }

    public static void setSizeOfEnvironmentX(int sizeOfEnvironmentX) {
        Environment.sizeOfEnvironmentX = sizeOfEnvironmentX;
    }

    public static int getSizeOfEnvironmentY() {
        return sizeOfEnvironmentY;
    }

    public static void setSizeOfEnvironmentY(int sizeOfEnvironmentY) {
        Environment.sizeOfEnvironmentY = sizeOfEnvironmentY;
    }
}
