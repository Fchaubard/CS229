import javax.swing.*;
import java.awt.*;
public class CheckerboardPanel extends JPanel{
    int x_env;
    int y_env;
    int x_square_size = 25;
    int y_square_size = 25;


    public CheckerboardPanel(int x_env,int y_env){

        this.x_env = x_env;
        this.y_env = y_env;

        setPreferredSize(new Dimension(x_env*x_square_size, y_env*y_square_size));


    }



    public void paintComponent(Graphics g){


        int x = 0, y = 0, color = 0;
        for(int i = 0;i<x_env; i++){
            x=0;
            for(int j = 0;j<y_env; j++){
                if(color % 2 == 0)
                    g.setColor(Color.RED);
                else if(color % 2 != 0)
                    g.setColor(Color.BLACK);
                g.fillRect(x, y, x_square_size, y_square_size);
                x=x+x_square_size;
                color++;
            }
            y=y+y_square_size;
            color--;
        }


    }
}