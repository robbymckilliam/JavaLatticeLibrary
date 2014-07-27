/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Vn1Star;

import Jama.Matrix;
import pubsim.lattices.Anstar.AnstarVaughan;
import pubsim.lattices.NearestPointAlgorithmStandardNumenclature;

/**
 *
 * @author Robby McKilliam
 */
public abstract class Vn1Star extends NearestPointAlgorithmStandardNumenclature{
    
    public Vn1Star(int n){
        super(n);
        u = new double[n+2];
        v = new double[n+2];
    }
    
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            yDoubletoy = new double[y.length];
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(y);
    }
    
    /**Getter for the nearest point. */
    @Override
    public double[] getLatticePoint(){ return v; }
    
    /**Getter for the integer vector. */
    @Override
    public double[] getIndex(){ return u; }
    
    /**
     * Project a n+2 length vector into Phin2StarZnLLS space.
     * y is output, x is input (x & y can be the same array)
     * <p>
     * PRE: x.length <= y.length
     */
    public static void project(double[] x, double[] y){
        AnstarVaughan.project(x,y);
        double sumn2 = sumg2(x.length);
        double nbar = (x.length + 1)/2.0;
        double dot = 0.0;
        for(int i = 0; i < x.length; i++)
            dot += y[i]*(i+1-nbar);
        for(int i = 0; i < x.length; i++)
            y[i] -= dot/sumn2 * (i+1-nbar);
    }
    
    @Override
    public Matrix getGeneratorMatrix() {      
        return getGeneratorMatrix(n);      
    }

    public static Matrix getGeneratorMatrix(int n) {
        Matrix M = getMMatrix(n);
        Matrix Mt = M.transpose();
        Matrix K = (Mt.times(M)).inverse().times(Mt);
        Matrix G = Matrix.identity(n+2, n+2).minus(M.times(K));

        return G.getMatrix(0, n+2-1, 0, n-1);
    }
    
    /** 
     * This is the Vandermonde matrix M in most of my papers
     * M = [1, n, n^2, ..., n^a].
     */
    public Matrix getMMatrix(){    
        return getMMatrix(n);
    }

    /**
     * This is the Vandermonde matrix M in most of my papers
     * M = [1, n, n^2, ..., n^a].
     */
    public static Matrix getMMatrix(int n){
        Matrix M = new Matrix(n+2, 2);

        for(int i = 0; i < n+2; i++){
            for(int j = 0; j < 2; j++){
                M.set(i, j, Math.pow(i+1, j));
            }
        }
        return M;
    }

    /** 
     * Return the vector g = Qn which is used
     * regularly for this lattice
     * @param n is g.length
     * @return g
     */
    public static double[] getgVector(int n){
        double[] g = new double[n];
        for(int i = 0; i < n; i++)
            g[i] = (i+1.0-(n+1.0)/2.0);
        return g;
    }

    /**
     * Return the magnitude squared of the vector g
     * See Chapter 6 of Robby's confirmation report.
     */
    public static double sumg2(int n){
        double f = Math.floor(n/2.0);
        double sum = f*(f + 1)*(2*f + 1)/3.0;
        if(n%2 == 0)
            sum += n/4.0 - f*(f+1);
        return sum;
    }
    
    @Override
    public double distance() { throw new UnsupportedOperationException(); }
    
    @Override
    public String name() { 
        return "Vn" + n + "m1star";
    }


}
