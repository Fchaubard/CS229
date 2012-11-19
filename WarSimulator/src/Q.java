import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 2012-11-14
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public class Q {

    //Attributes
    private HashMap<String,Double[]>  qValues;


    //Constructor
    public Q()
    {
          qValues = new HashMap<String, Double[]>();
    }

    //Methods
    public Double getQValue(String stateKey, int action)
    {
        //Look up saved value
        if (qValues.containsKey(stateKey))
        {
            return (qValues.get(stateKey))[action];
        }
        else  //Value doesn't exist yet -> random initialization
        {
            Double[] initValues = {1.0,1.0,1.0,1.0};
            qValues.put(stateKey, initValues);
            return  initValues[action];
        }
    }

    public Double[] getStateActionValues(String stateKey)
    {
        //Look up saved value
        if (qValues.containsKey(stateKey))
        {
            return (qValues.get(stateKey));
        }
        else  //Value doesn't exist yet -> random initialization
        {
            Double[] initValues = {1.0,1.0,1.0,1.0};
            qValues.put(stateKey, initValues);
            return  initValues;
        }
    }

    public void setQValue(String stateKey, int action, Double qValue)
    {
        //Look up correct place
        if (qValues.containsKey(stateKey))
        {
            qValues.get(stateKey)[action] = qValue;
        }
        else  //Value doesn't exist.. shit's fucked up!?
        {
            //error message of some sort
        }
    }
}
