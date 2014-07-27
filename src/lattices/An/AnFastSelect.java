package pubsim.lattices.An;

import pubsim.lattices.Anstar.Anstar;
import pubsim.FastSelection;
import pubsim.IndexedDouble;
import pubsim.VectorFunctions;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 * Simple linear time An algorithm using the Rivest Tarjan
 * selection algorithm.  This was described in the most
 * recent version of SPLAG.
 * @author Robby McKilliam
 */
public class AnFastSelect extends An implements LatticeAndNearestPointAlgorithmInterface {

    protected final IndexedDouble[] z;
    
    public AnFastSelect(int n){
        this.n = n;
        u = new double[n+1];
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n+1; i++)
            z[i] = new IndexedDouble();
    }

    @Override
    public final void nearestPoint(double[] y) {
        if (n != y.length-1) throw new RuntimeException("y is the wrong length");
        
        Anstar.project(y, y);
        
        VectorFunctions.round(y, u);
        int m = (int)VectorFunctions.sum(u);
        for(int i = 0; i < n+1; i++){
            z[i].value = Math.signum(m)*(y[i] - u[i]);
            z[i].index = i;
        }
        
        if(m != 0)
            FastSelection.FloydRivestSelect(0, n, (int)Math.abs(m), z);
    
        for(int i = 0; i < Math.abs(m); i++)
            u[z[i].index] -= Math.signum(m);
        
    }

}
