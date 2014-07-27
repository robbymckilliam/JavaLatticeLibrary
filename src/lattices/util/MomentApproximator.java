package pubsim.lattices.util;

import static pubsim.VectorFunctions.norm;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 * Approximate moments of the Voronoi cell of a lattice by brute force
 * enumeration within the Voronoi cell.
 * @author Robby McKilliam
 */
public class MomentApproximator extends PropertyCalculator {

    protected final int m;
    protected double rawmoment = 0.0;

    /**
     * @param L lattice to compute a moment from
     * @param m order of the moment, i.e. compute the mth moment
     */
    public MomentApproximator(LatticeAndNearestPointAlgorithmInterface L, int m){
        super(L);
        this.m = m;
    }

    @Override
    protected void calculateProperty(double[] p){
        //System.out.println(VectorFunctions.print(p));
        double magm = norm(m,p);
        rawmoment += magm;
    }

    public double moment() { 
        //you have to normalise by the volume, this is the Jacobian
        return L.volume()*rawmoment/numsamples; 
    }

}
