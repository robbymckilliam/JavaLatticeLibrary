package pubsim.lattices.An;

import java.util.Arrays;
import pubsim.IndexedDouble;
import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 * Nearest point algorithm for the Lattice An.  This uses the sorting
 * method described by Conway and Sloane.
 * @author Robby McKilliam
 */
public class AnSorted extends An implements LatticeAndNearestPointAlgorithmInterface{

    protected final IndexedDouble[] z;

    public AnSorted(int n){
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
        double m = VectorFunctions.sum(u);
        for(int i = 0; i < n+1; i++){
            z[i].value = Math.signum(m)*(y[i] - u[i]);
            z[i].index = i;
        }

        Arrays.sort(z);
        for(int i = 0; i < Math.abs(m); i++)
            u[z[i].index] -= Math.signum(m);
        
    }
    

}
