import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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

    public Environment(int numberOfTeams, int numberOfSoldiersPerTeam, Soldier[] initial_Soldiers, int stepLimit, int sizeOfEnvironmentX, int sizeOfEnvironmentY) {
        this.setCurrentStep(-1);
        this.setSoldiers(initial_Soldiers);
        Environment.setNumberOfTeams(numberOfTeams);
        Environment.setNumberOfSoldiersPerTeam(numberOfSoldiersPerTeam);
        Environment.setStepLimit(stepLimit);
        Environment.setSizeOfEnvironmentX(sizeOfEnvironmentX);
        Environment.setSizeOfEnvironmentY(sizeOfEnvironmentY);
    }

    public void initializeGame(){

        Environment.getNumberOfSoldiersPerTeam();

        for(int j=0; j<numberOfTeams; j++){
            for(int i=0; i<numberOfSoldiersPerTeam; i++){

                //TODO Set the positions for team 0
                if(soldiers[i].getTeamIdentifier()==0){
                   soldiers[i].setPosition(new Position(0,sizeOfEnvironmentY-i-1,0));
                }else{
                   soldiers[i].setPosition(new Position(sizeOfEnvironmentX-1,sizeOfEnvironmentY-i-1,180));
                }

            }
        }
        this.setCurrentStep(0);
    }

    public void stepGame(){

        // Step game until step limit
        if (currentStep==stepLimit){
            //error
            System.out.print("game went longer then intended");
            return;
        }

        // Step the game by 1
        this.setCurrentStep(this.getCurrentStep()+1);

        // TODO Decide if there were any successful attacks
        // TODO Give rewards or hurt
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
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getX()-1)==b.getPosition().getX())&&(b.getPosition().getAngle()==0)){  //180
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getY()+1)==b.getPosition().getY())&&(b.getPosition().getAngle()==270)){  //90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else{
                                   // System.out.print("close but no cigar\n");
                                }
                                break;
                            case 90:
                                //check -90 180 90
                                if (((a.getPosition().getX()+1)==b.getPosition().getX())&&(b.getPosition().getAngle()==180)){  //-90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getY()-1)==b.getPosition().getY())&&(b.getPosition().getAngle()==90)){  //180
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getX()-1)==b.getPosition().getX())&&(b.getPosition().getAngle()==0)){  //90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else{
                                    //System.out.print("close but no cigar\n");
                                }
                                break;
                            case 180:
                                //check -90 180 90
                                if (((a.getPosition().getY()+1)==b.getPosition().getY())&&(b.getPosition().getAngle()==270)){  //-90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getX()+1)==b.getPosition().getX())&&(b.getPosition().getAngle()==180)){  //180
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getY()-1)==b.getPosition().getY())&&(b.getPosition().getAngle()==90)){  //90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else{
                                   // System.out.print("close but no cigar\n");
                                }
                                break;
                            case 270:
                                //check -90 180 90
                                if (((a.getPosition().getX()-1)==b.getPosition().getX())&&(b.getPosition().getAngle()==0)){  //-90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getY()+1)==b.getPosition().getY())&&(b.getPosition().getAngle()==270)){  //180
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else if (((a.getPosition().getX()+1)==b.getPosition().getX())&&(b.getPosition().getAngle()==180)){  //90
                                    System.out.printf("Soldier %d hurt soldier %d\n",b.getIdentifier(),a.getIdentifier());
                                }else{
                                    //System.out.print("close but no cigar\n");
                                }
                                break;
                            default:
                                System.out.print("someones got a wrong angle\n");

                        }
                    }
                }


            }

        }

        // Let each decide where to move
        ArrayList<Position> moveList = new ArrayList<Position>();

        for (Soldier a : soldiers){
            moveList.add(a.move(sizeOfEnvironmentX,sizeOfEnvironmentY));
        }

        // Do move rectification
        moveRectification(moveList);

        // Commit Moves
        for (int i=0; i<soldiers.length; i++){

            soldiers[i].setPosition(moveList.get(i));
        }



    }

    public ArrayList moveRectification(ArrayList moveList){
        ArrayList newMoveList = moveList;

        if(arePositionsUnique(moveList)){
            newMoveList = moveList;
        }
        else{
            //TODO a bunch of move rectification crap

        }

        return newMoveList;
    }

    public boolean arePositionsUnique(ArrayList<Position> list){
        boolean positionsArentEqual=false;
        Set<Integer> xPos = new TreeSet<Integer>();
        Set<Integer> yPos = new TreeSet<Integer>();

        // ensure of the positions are equal by putting them into a set
        for (int i=0; i<list.size(); i++){
            Position position = list.get(i);

            try{
                xPos.add(position.getX());
                yPos.add(position.getY());
                positionsArentEqual = true;
            }
            catch(Exception e){
                positionsArentEqual = false;
                break;
            }

        }

        return positionsArentEqual;
    }


    public void resetGame(){
        //TODO save off data and outcome for the game
        this.initializeGame();
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
