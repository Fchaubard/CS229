/**
 * Created with IntelliJ IDEA.
 * User: francoischaubard
 * Date: 10/31/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Position {
    // attributes
    private static int x;
    private static int y;
    private static int angle;

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
        this.angle = angle;
    }

}
