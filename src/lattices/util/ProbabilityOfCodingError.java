package pubsim.lattices.util;

import pubsim.distributions.NoiseGenerator;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 * 
 * @author Robby McKilliam
 */
public class ProbabilityOfCodingError {
    
    final double Pe;
    final double[] nvec;
    
    /**
     * Estimates the probability of the random variable ngen being outside the Voronoi
     * cell of L.
     */
    public ProbabilityOfCodingError(LatticeAndNearestPointAlgorithmInterface L, NoiseGenerator<Double> ngen, int numerrors){
        
        int errorcount = 0;
        long itercount = 0;
        
        int n = L.getGeneratorMatrix().getRowDimension();
        nvec = new double[n];
        
        while(errorcount < numerrors){
            for(int i = 0; i < n; i++) nvec[i] = ngen.noise();
            L.nearestPoint(nvec);
            double[] latticep = L.getLatticePoint();
            if( !isOrigin(latticep) ) errorcount++;
            itercount++;
        }
        
        Pe = (1.0*numerrors)/itercount;
    }
    
    protected boolean isOrigin(double[] v){
        boolean isorg = true;
        int n = 0;
        while(isorg && n < v.length){
            isorg = Math.abs(v[n]) < 1e-9;
            n++;
        }
        return isorg;
    }
    
    
    public double probError(){
        return Pe;
    }
    
    public double probCorrect(){
        return 1 - Pe;
    }
    
}
