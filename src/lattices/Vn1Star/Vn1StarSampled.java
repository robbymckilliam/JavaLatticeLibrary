/*
 * Vn1StarSampled.java
 *
 * Created on 18 August 2007, 12:53
 */

package pubsim.lattices.Vn1Star;

import pubsim.lattices.Anstar.AnstarSorted;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.NearestPointAlgorithmInterface;
import pubsim.*;
import pubsim.lattices.Anstar.AnstarLinear;

/**
 * Suboptimal (maybe?) algorithm for finding the nearest lattice point in Phin2StarZnLLS.
 * Based on the pes.SamplingLLS period estimator.  Runs in 0(n^2 logn) assuming
 * that 0(n) samples are used.
 * <p>
 * This currently only works for odd n.  Why?
 * 
 * @author Robby McKilliam
 */
public class Vn1StarSampled extends Vn1Star {
    
    final protected int num_samples;
    final Anstar anstar;
    
    protected double[] g, vt, ut, y;
    
    /** Sets dimension and number of samples */
    public Vn1StarSampled(int n, int samples) {
        super(n);
        num_samples = samples;
        anstar = new AnstarLinear(n+1);
        vt = new double[n+2];
        y = new double[n+2];
        g = new double[n+2];
    }
    
    @Override
    public void nearestPoint(double[] y){
        if (n != y.length-2) throw new ArrayIndexOutOfBoundsException("y is the wrong length");
        
        project(y, this.y);
        
        double bestdist = Double.POSITIVE_INFINITY;
        double step = 1.0/num_samples;
        for( double f = -0.5; f < 0.5; f+=step ){
            
            //calculate the next point on line to test
            for(int i=0; i<n+2; i++)
                g[i] = this.y[i] + (i+1.0-(n+3.0)/2.0)*f;
            
            anstar.nearestPoint(g);         
            project(anstar.getLatticePoint(), vt);
            
            double dist = VectorFunctions.distance_between(this.y, vt);
            
            if(dist < bestdist){
                bestdist = dist;
                System.arraycopy(vt, 0, v, 0, vt.length);
                System.arraycopy(anstar.getIndex(), 0, u, 0, u.length);
            } 
           
        }

    }
    
}
