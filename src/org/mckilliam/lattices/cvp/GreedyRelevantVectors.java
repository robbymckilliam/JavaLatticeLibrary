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
    public void nearestPoint(double[] y) {
        babai.computeBabaiPoint(y);
        babai.nearestPoint(y);
        double[] xk = babai.getLatticePoint();
        double D1 = Double.POSITIVE_INFINITY;
        double D2 = pubsim.VectorFunctions.distance_between(xk, y);
        //while( D2 < D1 ){
        //    
        //}
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
    public void nearestPoint(Double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
