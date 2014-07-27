/*
 */

package pubsim.lattices.Vnmstar;

import Jama.Matrix;
import static pubsim.Util.binom;
import static pubsim.Util.factorial;
import static pubsim.VectorFunctions.dot;
import pubsim.lattices.AbstractLattice;

/**
 *
 * @author Robby McKilliam
 */
public class VnmStar extends AbstractLattice {

    /** dimension of this lattice*/
    final protected int n;

    /** polynomial order */
    final protected int m;
    
    final protected int N;
    
    //store all the legendre polynomials so that we can make projection fast.
    double[][] legendre;
    
    public VnmStar(int m, int n){
        this.n = n;
        this.m = m;
        N = n+m+1;
    }

    /** {@inheritDoc} */
    @Override
    public double volume(){
        Matrix M = getGeneratorMatrix();
        return Math.sqrt(M.transpose().times(M).det());
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return getGeneratorMatrix(m, n);
    }

    public static Matrix getGeneratorMatrix(int m, int n) {
        int N = n+m+1;
        Matrix Q = new HilbertMatrix(m+1,N).VnmStarGeneratorDouble();
        return Q.getMatrix(0, n+m, 0, n-1);
    }

    /**
     * This is the matrix M in most of my papers
     * M = [1, n, n^2, ..., n^m]
     */
    public Matrix getMMatrix(){
        return getMMatrix(m, n);
    }

    /**
     * This is the Vandermonde matrix N in my thesis.
     * N = [1, n, n^2, ..., n^m]
     */
    public static Matrix getMMatrix(int m, int n){
        int N = m + n + 1;
        Matrix M = new Matrix(N, m+1);

        for(int i = 0; i < N; i++){
            for(int j = 0; j <= m; j++){
                M.set(i, j, Math.pow(i+1, j));
            }
        }
        return M;
    }

    /**
     * Project x into the space of the lattice VnmStarSampled and return
     * the projection into y.  Requires recursion, runs in O(n m) time
     * PRE: x.length = y.length
     */
    public static void project(double[] x, double[] y, int m){
        int n = x.length;
        System.arraycopy(x, 0, y, 0, n);
        double[] p = new double[n];
        for(int k = 0; k <= m; k++){
            discreteLegendrePolynomialVector(n, k, p);
            double ytp = dot(y,p);
            double ptp = dot(p,p);
            double scale = ytp/ptp;
            for(int s = 0; s < n; s++) y[s] -= p[s]*scale;
        }
    }

    /** Project into the space this lattice lies in. */
    public void project(double[] x, double[] y) {
        if(legendre == null) computeLegendreVectors(m, n);
        System.arraycopy(x, 0, y, 0, N);
        for(int k = 0; k <= m; k++){
            double[] ell = legendre[k];
            double ytp = dot(y,ell);
            double ptp = dot(ell,ell);
            double scale = ytp/ptp;
            for(int s = 0; s < N; s++) y[s] -= ell[s]*scale;
        }
    }

    /**
     * Return the mth monic discrete Legendre polynomial of length n.
     */
    public static double[] discreteLegendrePolynomialVector(int n, int m){
        double[] p = new double[n];
        discreteLegendrePolynomialVector(n, m, p);
        return p;
    }

    /**
     * Return the mth monic discrete Legendre polynomial of length n.
     * Does not allocate memory.
     * PRE: p.length = n;
     */
    public static void discreteLegendrePolynomialVector(int n, int m, double[] p){
        if(m < 0 || m > n)
            throw new ArrayIndexOutOfBoundsException("m is out of range");
        if(p.length < n)
            throw new ArrayIndexOutOfBoundsException("p is too short");
        //zero the elements in p
        for(int x = 0; x < n; x++) p[x] = 0.0;
        double scale = factorial(m)/((double)binom(2*m, m));
        for(int s = 0; s <= m; s++){
            for(int x = 0; x < n; x++)
                p[x] += scale*Math.pow(-1, s+m)*binom(s+m, s)*binom(n-s-1, n-m-1)*binom(x,s);
        }
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return n;
    }
    
    @Override
    public String name() { 
        return "Vn" + n + "m" + m + "star";
    }

    final protected void computeLegendreVectors(int m, int n) {
        //compute all the Legendre polynomials
       legendre = new double[m+1][];
       for(int k = 0; k <= m; k++)
           legendre[k] = discreteLegendrePolynomialVector(n+m+1, k);
    }


}
