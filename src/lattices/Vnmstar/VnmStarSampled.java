/*
 * Pnx.java
 *
 * Created on 2 November 2007, 13:24
 */

package pubsim.lattices.Vnmstar;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.util.PointInParallelepiped;

/**
 * Approximate nearest point algorithm for Vnm* that samples
 * in the hyperplane H.  Uses the nearest lattice point algorithm
 * for An* to help speed things up.
 * @author Robby McKilliam
 */
public class VnmStarSampled extends VnmStar implements LatticeAndNearestPointAlgorithmInterface {

    final private Anstar anstar;
    final private double[] x, u;
    final private int[] samples;
    final private Matrix M;
    final private double[] yt;

    public VnmStarSampled(int m, int n, int[] samples){
        super(m,n);

        anstar = new AnstarLinear(N-1);

        M = new Matrix(legendre).transpose().getMatrix(0, N-1, 1, m);

        //working memory
        yt = new double[N];
        u = new double[N];
        x = new double[N];
        this.samples = samples;
    }

    
    @Override
    public void nearestPoint(double[] y) {

        PointInParallelepiped points = new PointInParallelepiped(M, samples);
        double D = Double.POSITIVE_INFINITY;
        while(points.hasMoreElements()){
            double[] p = points.nextElement().getColumnPackedCopy();
            for(int i = 0; i < N; i++){
                yt[i] = y[i] - p[i];
            }
            anstar.nearestPoint(yt);
            project(anstar.getIndex(), yt);
            double d = VectorFunctions.distance_between(yt, y);
            if( d < D ){
                System.arraycopy(anstar.getIndex(), 0, u, 0, N);
                D = d;
            }
        }

        project(u, x);

    }

    @Override
    public double[] getLatticePoint() {
        return x;
    }

    @Override
    public double[] getIndex() {
        return u;
    }
    
    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(y);
    }

    
}
