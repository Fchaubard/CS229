import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    private Soldier[] soldiers= new Soldier[numberOfSoldiersPerTeam*numberOfTeams]; // (Matrix #teams x #soldiersperteam)
    private int currentStep;
    private static int stepLimit;
    private static int sizeOfEnvironmentX;
    private static int sizeOfEnvironmentY;
    private static double inactivityPunishment;
    private GameState currentPositions;
    private RoundRewards currentRewards;

    public Environment(int numberOfTeams, int numberOfSoldiersPerTeam, Soldier[] initial_Soldiers, int stepLimit, int sizeOfEnvironmentX, int sizeOfEnvironmentY, double inactivityPunishment) {
        this.setCurrentStep(-1);
        this.setSoldiers(initial_Soldiers);
        Environment.setNumberOfTeams(numberOfTeams);
        Environment.setNumberOfSoldiersPerTeam(numberOfSoldiersPerTeam);
        Environment.setStepLimit(stepLimit);
        Environment.setSizeOfEnvironmentX(sizeOfEnvironmentX);
        Environment.setSizeOfEnvironmentY(sizeOfEnvironmentY);
        Environment.inactivityPunishment = inactivityPunishment;
        Position referencePosition = new Position(0,0,0);
    }

    public void initializeGame(){

        for(int i=0; i<(numberOfTeams*numberOfSoldiersPerTeam); i++){

                //TODO Set the positions for team 0
                if(soldiers[i].getTeamIdentifier()==0){
                   soldiers[i].prepareForNewGame(new Position(2,sizeOfEnvironmentY-i-1,0));
                }else{
                   soldiers[i].prepareForNewGame(new Position(sizeOfEnvironmentX-1,sizeOfEnvironmentY-i-1,180));
                }

        }//for

        this.setCurrentStep(0);
    }
    public void giveTeamReward(Soldier doingTheHurting, Soldier gotHurt){
        gotHurt.setScore(gotHurt.getScore()-1);
        doingTheHurting.setScore(doingTheHurting.getScore()+1);
        System.out.printf("Soldier %d hurt soldier %d\n", doingTheHurting.getIdentifier(), gotHurt.getIdentifier());

        for (Soldier a:soldiers) {
            if(a.getTeamIdentifier()==doingTheHurting.getTeamIdentifier())
                if(a.getTeamIdentifier()==doingTheHurting.getIdentifier())
                    currentRewards.setSoldierReward(a.getIdentifier(),1.0+currentRewards.getSoldierReward(a.getIdentifier())); //Reward team b
                else{
                    currentRewards.setSoldierReward(a.getIdentifier(),0.0+currentRewards.getSoldierReward(a.getIdentifier())); //Reward team b
                }
            else if(a.getTeamIdentifier()==gotHurt.getTeamIdentifier())
                if(a.getTeamIdentifier()==gotHurt.getIdentifier())
                    currentRewards.setSoldierReward(a.getIdentifier(),-1.0+currentRewards.getSoldierReward(a.getIdentifier())); //Reward team b
                else{
                    currentRewards.setSoldierReward(a.getIdentifier(),-0.5+currentRewards.getSoldierReward(a.getIdentifier())); //Reward team b
                }
            }
    }

    public void determineRewards(){
        currentRewards = new RoundRewards(soldiers.length);
        // for soldier a check if soldier b hurt him
        for (Soldier a:soldiers){
            for (Soldier b:soldiers){


                // check if they are on the same team
                if(b.getTeamIdentifier()!=a.getTeamIdentifier()){
                    // check if they have the same x or y which they must to have an attack
                    if(b.getPosition().getX()==a.getPosition().getX() || b.getPosition().getY()==a.getPosition().getY()){
                        switch (a.getPosition().getAngle()){
                            case 0:
                                //check -90 180 90
                                if (((a.getPosition().getY()-1)==b.getPosition().getY())&&(b.getPosition().getAngle()==90)){  //-90
                                    giveTeamReward(b, a);
                                }else if (((a.getPosition().getX()-1)==b.getPosition().getX())&&(b.getPosition().getAngle()==0)){  //180
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getY()+1)==b.getPosition().getY())&&(b.getPosition().getAngle()==270)){  //90
                                   giveTeamReward(b, a);
                                }else{
                                    // System.out.print("close but no cigar\n");

                                }
                                break;
                            case 90:
                                //check -90 180 90
                                if (((a.getPosition().getX()+1)==b.getPosition().getX())&&(b.getPosition().getAngle()==180)){  //-90
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getY()-1)==b.getPosition().getY())&&(b.getPosition().getAngle()==90)){  //180
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getX()-1)==b.getPosition().getX())&&(b.getPosition().getAngle()==0)){  //90
                                   giveTeamReward(b, a);
                                }else{
                                    //System.out.print("close but no cigar\n");
                                }
                                break;
                            case 180:
                                //check -90 180 90
                                if (((a.getPosition().getY()+1)==b.getPosition().getY())&&(b.getPosition().getAngle()==270)){  //-90
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getX()+1)==b.getPosition().getX())&&(b.getPosition().getAngle()==180)){  //180
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getY()-1)==b.getPosition().getY())&&(b.getPosition().getAngle()==90)){  //90
                                   giveTeamReward(b, a);
                                }else{
                                    // System.out.print("close but no cigar\n");
                                }
                                break;
                            case 270:
                                //check -90 180 90
                                if (((a.getPosition().getX()-1)==b.getPosition().getX())&&(b.getPosition().getAngle()==0)){  //-90
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getY()+1)==b.getPosition().getY())&&(b.getPosition().getAngle()==270)){  //180
                                   giveTeamReward(b, a);
                                }else if (((a.getPosition().getX()+1)==b.getPosition().getX())&&(b.getPosition().getAngle()==180)){  //90
                                   giveTeamReward(b, a);
                                }else{
                                    //System.out.print("close but no cigar\n");
                                }
                                break;
                            default:
                                System.out.print("someones got a wrong angle\n");

                        }//switch
                    }// second if
                }// first if


            }// second for
        }// first for
        currentRewards.punishInactivity(inactivityPunishment);



    }

    public void stepGame(){
        // Let each decide where to move
        ArrayList<Position> moveList = new ArrayList<Position>();

        // Step game until step limit
        if (currentStep==stepLimit){
            //error
            System.out.print("game went longer then intended");
            return;
        }

        // Step the game by 1
        this.setCurrentStep(this.getCurrentStep()+1);

        determineRewards();

        // TODO Decide if there were any successful attacks
        // TODO Give rewards or hurt

        for (Soldier a : soldiers){
            double rewardForA = currentRewards.getSoldierReward(a.getIdentifier());

            Position moveForA = a.move(rewardForA, new ArrayList<Soldier>(Arrays.asList(getSoldiers())));

            moveList.add(moveForA);
        }

        // Do move rectification
        moveRectification(moveList);

        // Commit Moves
        for (int i=0; i<soldiers.length; i++){
            soldiers[i].setPosition(moveList.get(i));
        }

    }

    public ArrayList moveRectification(ArrayList moveList){

        ArrayList newMoveList = arePositionsUnique(moveList);
        return newMoveList;
    }

    public boolean arePositionsEqual(Position a,Position b){
        if((a.getX()==b.getX())&&(a.getY()==b.getY())){
            return true;
        }

        return false;
    }

    public ArrayList arePositionsUnique(ArrayList<Position> list){
        boolean positionsArentEqual=false;

        // ensure of the positions are equal by putting them into a set
        for (int i=0; i<list.size(); i++){
            for (int j=0; j<list.size(); j++){
                if(i!=j){
                    Position ai = list.get(i);
                    Position aj = list.get(j);

                    if(arePositionsEqual(ai,aj)){

                        //System.out.printf("conflict with S%d and S%d \n\n",i,j);
                        Random random = new Random();
                        int whogetsit = random.nextInt(1);  // gives either a 0, 1, 2, or 3
                        if(whogetsit==0){
                            // Randomly give it to a
                            list.set(i,ai);
                            list.set(j,getSoldier(j).getPosition());   //keep at current position
                        }else if(whogetsit==1){
                            // Randomly give it to b
                            list.set(j,aj);
                            list.set(i,getSoldier(i).getPosition());  //keep at current position
                        }else{
                            System.out.print("issue with random");
                        }

                    }
                }
            }

        }

        return list;
    }


    public void resetGame(){
        //TODO save off data and outcome for the game
        this.setCurrentStep(-1);
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

    public Soldier[] getSoldiers() {
        return soldiers;
    }
    public ArrayList<Position> getSoldierPositions() {

        ArrayList<Position> positionArrayList = new ArrayList<Position>(soldiers.length);

        for(Soldier a:soldiers){
            positionArrayList.add(a.getPosition());
        }

        return positionArrayList;
    }
    public Soldier getSoldier(int i) {
        return soldiers[i];
    }
    public void setSoldiers(Soldier[] soldiers) {
        this.soldiers = soldiers;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
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
