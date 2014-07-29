package org.mckilliam.lattices.cvp;

import org.mckilliam.lattices.ClosestVectorInterface;
import org.mckilliam.lattices.LatticeInterface;
import Jama.Matrix;
import org.mckilliam.lattices.util.PointEnumerator;

/**
 * A closest point algorithm that works by greedy iterating over relevant vectors.  This
 * requires enumerating the set of relevant vectors first, and so, in general is very slow. However,
 * version of this algorithm are fast for specific lattices (such as those of Voronoi's first kind).
 * 
 * @author Robby McKilliam
 */
public class GreedyRelevantVectors implements ClosestVectorInterface {

    protected final LatticeInterface L;
    protected final Babai babai; 
    
    /** Compute closest point for lattice L */
    public GreedyRelevantVectors(LatticeInterface L) {
        this.L = L;
        babai = new Babai(L);
    }

    @Override
    public double[] nearestPoint(double[] y){
         babai.nearestPoint(y);
        double[] x0 = babai.getLatticePoint();
        return nearestPoint(y,x0);
    }
    
    public double[] nearestPoint(double[] y, double[] x0) {
        if(y.length != x0.length) throw new RuntimeException("y and x0 must have the same length.");
        int m = x0.length; //the dimension of the space this lattice lies in
        double[] ymx = new double[m]; //some memory
        double[] xk = new double[m]; //stores current iterate
        System.arraycopy(x0, 0, xk, 0, m); //starting iterate is x0
        double D1 = Double.POSITIVE_INFINITY;
        double D2 = pubsim.VectorFunctions.distance_between(xk, y);
        while( D2 < D1 ){
            D1 = D2;
            for(int i = 0; i < m; i++) ymx[i] = y[i] - xk[i];
            Matrix v = closestRelevantVector(ymx);
            for(int i = 0; i < m; i++) xk[i] += v.get(i,0);
            D2 = pubsim.VectorFunctions.distance_between(xk, y);
        }
        return xk;
    }
    
    /// Returns the closest relevant vector (including the origin) to ya.
    protected Matrix closestRelevantVector(double[] ya){
        Matrix y = pubsim.VectorFunctions.columnMatrix(ya);
        PointEnumerator rv = L.relevantVectors();
        Matrix vmin = new Matrix(ya.length,1,0.0); //origin tests out of loop first
        double D = y.normF();
        while(rv.hasMoreElements()){
            Matrix v = rv.nextElement();
            double Dd = (y.minus(v)).normF();
            if(Dd < D){
                D = Dd;
                vmin = v;
            }
        }
        return vmin;
    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
