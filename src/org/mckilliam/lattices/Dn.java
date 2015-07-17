package org.mckilliam.lattices;

import org.mckilliam.lattices.cvp.LatticeAndClosestVectorInterface;
import Jama.Matrix;
import pubsim.Util;
import pubsim.VectorFunctions;
import org.mckilliam.lattices.relevant.RelevantVectors;
import org.mckilliam.lattices.util.PointEnumerator;

/**
 * Nearest point algorithm for the lattice Dn.
 * @author Robby McKilliam
 */
public class Dn extends AbstractLattice implements LatticeAndClosestVectorInterface {

    double[] u,v;
    int n;
    double dist;

    protected Matrix Binv;

    public Dn(int n){
        this.n = n;
        u = new double[n];
        v = new double[n];
        Binv = null;
    }

    
    @Override
    public int dimension() {
        return n;
    }

    @Override
    public double[] closestPoint(double[] y) {
        if (n != y.length) throw new RuntimeException("y is the wrong length");
        
        VectorFunctions.round(y, u);
        int m = (int)Math.rint(VectorFunctions.sum(u));
        if( Util.mod(m, 2) == 1){
            int k = 0;
            double D = Double.NEGATIVE_INFINITY;
            for(int i = 0; i < n; i++){
                double d = Math.abs(y[i] - u[i]);
                if( d > D){
                    k = i;
                    D = d;
                }
            }
            u[k] += Math.signum(y[k] - u[k]);
        }
        
        dist = VectorFunctions.distance_between(u, y);
        return u;
    }
    
    @Override
    public double distance(){
        return dist;
    }

    @Override
    public double[] getLatticePoint() {
        return u;
    }

    @Override
    public double[] getIndex() {
        if(Binv == null) Binv = generatorMatrix().inverse();
        VectorFunctions.matrixMultVector(Binv, u, v);
        return v;
    }

    @Override
    public double volume() {
        return 2.0;
    }

    @Override
    public Matrix generatorMatrix() {
        Matrix B = new Matrix(n, n);
        B.set(0, 0, -1); B.set(1, 0, -1);
        for(int j = 1; j < n; j++){
            B.set(j-1, j, 1); B.set(j, j, -1);
        }
        return B;
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String name() { 
        return "D" + n;
    }
    
     /** @return an enumeration of the relevant vectors for this lattice */
    @Override
    public PointEnumerator relevantVectors() {
        return new RelevantVectors(this);
    }

}
