/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Anstar;

import Jama.Matrix;
import pubsim.lattices.NearestPointAlgorithmStandardNumenclature;
import pubsim.lattices.decoder.KissingNumber;
import pubsim.lattices.relevant.RelevantVectors;
import pubsim.lattices.util.PointEnumerator;

/**
 * Abstract class for any Anstar algorithm
 * @author robertm
 */
public abstract class Anstar extends NearestPointAlgorithmStandardNumenclature {
    
    public Anstar(int n){
        super(n);
        this.v = new double[n+1];
        this.u = new double[n+1];
    }

    @Override
    public double volume() {
        return Math.sqrt(1.0 / (n + 1));
    }

    @Override
    public double norm() {
        return n / (n + 1.0);
    }

    @Override
    public double coveringRadius(){
        return Math.sqrt(n*(n+2)/12.0/(n+1));
    }

    /**
     * Project a vector into the zero-mean plane
     * y is output, x is input (x & y can be the same array)
     * <p>
     * Pre: y.length >= x.length
     */
    public static void project(double[] x, double[] y) {
        double xbar = 0.0;
        for (int i = 0; i < x.length; i++) {
            xbar += x[i];
        }
        xbar /= x.length;
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] - xbar;
        }
    }
    
    /**
     * Project a vector into the zero-mean plane
     * y is output, x is input (x & y can be the same array)
     * <p>
     * Pre: y.length >= x.length
     */
    public static void project(Double[] x, double[] y) {
        double xbar = 0.0;
        for (int i = 0; i < x.length; i++) {
            xbar += x[i];
        }
        xbar /= x.length;
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] - xbar;
        }
    }

    @Override
    public Matrix getGeneratorMatrix() {
        Matrix on = new Matrix(n + 1, n);
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    on.set(i, j, 1.0 - 1.0 / (n + 1));
                } else {
                    on.set(i, j, -1.0 / (n + 1));
                }
            }
        }
        return on;
    }

    /** Return the n+1 by n+1 projection matrix into the space orthogonal to (1,1,...,1) */
    public Matrix getGeneratorMatrixBig() {
        Matrix on = new Matrix(n + 1, n+1);
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n+1; j++) {
                if (i == j) {
                    on.set(i, j, 1.0 - 1.0 / (n + 1));
                } else {
                    on.set(i, j, -1.0 / (n + 1));
                }
            }
        }
        return on;
    }
    
    /**
     * Return the ith glue vector between An* and An.
     * Memory for g must be preallocated.
     *
     * This is not Conway and Sloane's glue vectors, but
     * an equivalent set that lie in a straight line.
     */
    public static void glue(int i, double[] glue) {
        if (glue == null || glue.length == 0) {
            throw new RuntimeException("You must allocate memory for glue " +
                    "before constructing glue vectors");
        }

        int n = glue.length;
        double dn = 1.0 / n;
        glue[0] = i * (1.0 - dn);
        for (int j = 1; j < n; j++) {
            glue[j] = -i * dn;
        }

    }
    
    @Override
    public long kissingNumber() {
        return 2*(n+1);
    }
    
    @Override
    public double distance() { throw new UnsupportedOperationException(); }
    
    @Override
    public String name() {
        return "An" + n + "star";
    }
    
    /** @return an enumeration of the relevant vectors for this lattice */
    @Override
    public PointEnumerator relevantVectors() {
        return new RelevantVectors(this);
    }
    
}
