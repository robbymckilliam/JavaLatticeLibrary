package pubsim.lattices.util;

import static pubsim.VectorFunctions.sum2;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 *
 * @author Robby McKilliam
 */
public class PowerOfEuclideanNorm extends PropertyCalculator {
  
    protected final int m;
    protected double rawmoment = 0.0;

    /**
     * @param L lattice to compute a moment from
     * @param m order of the moment, i.e. compute the mth moment
     */
    public PowerOfEuclideanNorm(LatticeAndNearestPointAlgorithmInterface L, int m){
        super(L);
        this.m = m;
    }

    @Override
    protected void calculateProperty(double[] p){
        //System.out.println(VectorFunctions.print(p));
        double magm = Math.pow(sum2(p),m);
        rawmoment += magm;
    }

    public double moment() { 
        //you have to normalise by the volume, this is the Jacobian
        return L.volume()*rawmoment/numsamples; 
    }
    
}
