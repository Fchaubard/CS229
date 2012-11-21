import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 2012-11-14
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
public class EligibilityTraces {

    //Attributes
    HashMap<String,Double[]> traces;
    Double lambda;
    Double gamma;

    //Constructors
    public EligibilityTraces(Double theLambda, Double theGamma)
    {
        traces = new HashMap<String, Double[]>();
        lambda = theLambda;
        gamma = theGamma;
    }

    //Methods
    public void addTrace(String stateKey, int action)
    {
        //Look up potential existing trace
        if (traces.containsKey(stateKey))
        {
            //TODO think through this update thoroughly
            //Update trace using truncated update
            Double[] trace = traces.get(stateKey);
            trace[action] = 1.0;
            traces.put(stateKey,trace);
        }
        else
        {
            Double[] trace = {0.0, 0.0, 0.0, 0.0};
            trace[action] = 1.0;
            traces.put(stateKey, trace);
        }
    }

    public void updateTraces()
    {
        //TODO remove the trace when it goes below a threshold
        for(Double[] e:traces.values())
        {
            for(int i = 0; i < 4; ++i)
            {
                e[i] *= gamma*lambda;
            }

        }
    }

    public Double getTrace(String stateKey, int action)
    {

        //Retrieve trace
        if (traces.containsKey(stateKey))
        {
            return traces.get(stateKey)[action];
        }
        else
        {
            return 0.0;
        }

    }

    public void updateQMatrix(Q qMatrix, Double delta ,Double learningRate)
    {
        for(String key:traces.keySet())
        {
            Double[] actionTrace = traces.get(key);
            Double[] actionQValues = qMatrix.getStateActionValues(key);
            for(int i = 0; i < 4; ++i)
            {
                qMatrix.setQValue(key,i, actionQValues[i] + delta*learningRate*actionTrace[i]);
            }
        }
    }



}
