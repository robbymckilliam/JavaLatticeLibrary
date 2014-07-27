/*
 * Zn.java
 *
 * Created on 2 November 2007, 13:14
 */

package pubsim.lattices;

import Jama.Matrix;
import java.util.Iterator;
import pubsim.lattices.util.AbstractPointEnumerator;
import pubsim.lattices.util.PointEnumerator;

/**
 * Nearest point algorithm for the square lattice Zn.
 * You probably never want to use this, it's just here
 * for completeness.
 * @author Robby McKilliam
 */
public class Zn extends AbstractLattice implements LatticeAndNearestPointAlgorithmInterface {
    
    protected double[] x;
    protected int n;

    public Zn(int n){
        this.n =  n;
        x = new double[n];
    }
    
    @Override
    public final void nearestPoint(double[] y){
        if (n != y.length) throw new RuntimeException("y is the wrong length");
        
        for(int i = 0; i < n; i++)
            x[i] = Math.round(y[i]);
    }
    
    @Override
    public double[] getLatticePoint() { return x; }
    
    @Override
    public double[] getIndex() { return x; }

    /** {@inheritDoc} */
    @Override
    public double volume(){ return 1.0;}

    @Override
    public double norm() {
        return 1.0;
    }

    @Override
    public int getDimension() {
        return n;
    }

    @Override
    public final long kissingNumber() {
        return 2*n;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return Matrix.identity(n, n);
    }

    @Override
    public final double coveringRadius() {
        return Math.sqrt(n)/2;
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            yDoubletoy = new double[y.length];
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }
    
    @Override
    public String name() {
        return "Zn" + n;
    }
    
    /** @return An enumeration of the integer lattice */
    @Override
    public PointEnumerator relevantVectors() {
        return new ZnRelevantVectorsEnumerator(n);
    }
    
    public static class ZnRelevantVectorsEnumerator 
        extends AbstractPointEnumerator implements PointEnumerator {
        
        protected int count = 0;
        public final int finishedcount;
        public final int n;
        
        public ZnRelevantVectorsEnumerator(int n){
            finishedcount = 2*n;
            this.n = n;
        }
        
        @Override
        public double percentageComplete() {
            return (100.0*count) / finishedcount;
        }

        @Override
        public boolean hasMoreElements() {
            return count < finishedcount;
        }

        @Override
        public Matrix nextElement() {
            if( !hasMoreElements() ) throw new ArrayIndexOutOfBoundsException("No more relevant vectors to get!");
            int sign = count%2==0 ? 1 : -1;
            int i = count/2;
            Matrix ei = new Matrix(n,1,0.0);
            ei.set(i,0,sign);
            count++;
            return ei;
        }
        
    }
    
}
