/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 10/31/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Position {
    // attributes
    private  int x;
    private  int y;
    private  int angle;

    public Position(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    //methods
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        //ensure at or between 0 and 270
        if (angle>270)
            angle = 0;
        if (angle<0)
            angle = 270;

        this.angle = angle;
    }

}
