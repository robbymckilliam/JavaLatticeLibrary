package org.mckilliam.lattices.An;

import Jama.Matrix;
import pubsim.Util;
import org.mckilliam.lattices.AbstractLattice;
import org.mckilliam.lattices.cvp.LatticeAndClosestVectorInterface;
import org.mckilliam.lattices.cvp.ClosestVectorInterface;
import org.mckilliam.lattices.relevant.RelevantVectors;
import org.mckilliam.lattices.util.PointEnumerator;

/**
 * Abstract for the lattice An.  Protected variable u and n
 * must be set by the extending class.  u is the nearest point
 * and n is the dimension.
 * @author Robby McKilliam
 */
public abstract class An extends AbstractLattice implements LatticeAndClosestVectorInterface {

    protected double[] u;
    protected int n;


    @Override
    public double[] getLatticePoint() {
        return u;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    @Override
    public double volume() {
        return Math.sqrt(n+1);
    }

    @Override
   public double norm() {
        return 2;
    }

    @Override
    public int dimension() {
        return n;
    }

    /**
     * Return the n+1 by n generator matrix for An.
     */
    @Override
    public Matrix generatorMatrix() {
        return getGeneratorMatrixBig().getMatrix(0, n, 0, n-1);
    }

    /**
     * Return the n+1 by n+1 matrix that contains the generator
     * matrix for An but is not linear independent.
     */
    public Matrix getGeneratorMatrixBig() {
        Matrix B = Matrix.identity(n+1, n+1);
        for(int i = 0; i < n+1; i++)
            B.set(Util.mod(i+1, n+1), Util.mod(i, n+1), -1.0);
        return B;
    }

    /**
     * Returns glue vector [i] for An.
     * See SPLAG pp109.  This is not an
     * efficient implementation.  It
     * allocates memory.  This is
     * here for testing purposes.
     */
    public double[] getGlueVector(double i){
        double[] g = new double[n+1];
        double j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(n+1);
        return g;
    }

    /**
     * @return  the covering radius for this lattice
     */
    @Override
    public double coveringRadius(){
        throw new UnsupportedOperationException("Covering radius not supported");
    }

    @Override
    public final long kissingNumber() {
        return n*(n+1);
    }
    
    @Override
    public double secondMoment(){
        return Math.sqrt(n+1.0)*n*(n+3)/12.0/(n+1);
    }
    
    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String name() {
        return "A" + n;
    }
    
    /** @return an enumeration of the relevant vectors for this lattice */
    @Override
    public PointEnumerator relevantVectors() {
        return new RelevantVectors(this);
    }

}
