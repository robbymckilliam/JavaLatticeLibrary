/*
 * Pnx.java
 *
 * Created on 2 November 2007, 13:24
 */

package org.mckilliam.lattices.Vnmstar;

import Jama.Matrix;
import pubsim.VectorFunctions;
import org.mckilliam.lattices.Anstar.Anstar;
import org.mckilliam.lattices.Anstar.AnstarLinear;
import org.mckilliam.lattices.cvp.LatticeAndClosestVectorInterface;
import org.mckilliam.lattices.util.PointInParallelepiped;

/**
 * Approximate nearest point algorithm for Vnm* that samples
 * in the hyperplane H.  Uses the nearest lattice point algorithm
 * for An* to help speed things up.
 * @author Robby McKilliam
 */
public class VnmStarSampled extends VnmStar implements LatticeAndClosestVectorInterface {

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
    public double[] closestPoint(double[] y) {

        PointInParallelepiped points = new PointInParallelepiped(M, samples);
        double D = Double.POSITIVE_INFINITY;
        while(points.hasMoreElements()){
            double[] p = points.nextElement().getColumnPackedCopy();
            for(int i = 0; i < N; i++){
                yt[i] = y[i] - p[i];
            }
            anstar.closestPoint(yt);
            project(anstar.getIndex(), yt);
            double d = VectorFunctions.distance_between(yt, y);
            if( d < D ){
                System.arraycopy(anstar.getIndex(), 0, u, 0, N);
                D = d;
            }
        }

        project(u, x);
        return x;
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

    
}
