package org.mckilliam.lattices.util;

import Jama.Matrix;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.cvp.LatticeAndClosestVectorInterface;
import org.mckilliam.lattices.cvp.ClosestVectorInterface;
import org.mckilliam.lattices.cvp.SphereDecoder;
import pubsim.VectorFunctions;

/**
 * This returns points uniformly distributed in the Voronoi region.
 * This uses a sampling length and samples evenly along basis vectors
 * performing the nearest point algorithm on each point.
 * It may be that using uniformly distributed points using a random
 * number generator will work better than this.
 * @author Robby McKilliam
 */
public class SampledInVoronoi  
        extends AbstractPointEnumerator
        implements PointEnumerator{

    protected ClosestVectorInterface decoder;
    protected PointInParallelepiped ppoints;


    protected SampledInVoronoi() {}

    /**
     * Default to using the sphere decoder to compute nearest points
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public SampledInVoronoi(LatticeInterface L, int samples){
        ppoints = new PointInParallelepiped(L, samples);
        decoder = new SphereDecoder(L);
    }

    /**
     * Using the nearest point algorithm supplied.
     * @param L is the lattice with included nearest point algorithm.
     * @param samples is the number of samples used per dimension
     */
    public SampledInVoronoi(LatticeAndClosestVectorInterface L, int samples){
        ppoints = new PointInParallelepiped(L, samples);
        decoder = L;
    }

    public boolean hasMoreElements() {
        return ppoints.hasMoreElements();
    }

    public Matrix nextElement() {
        return VectorFunctions.columnMatrix(nextElementDouble());
    }

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    public double[] nextElementDouble() {
        double[]  p = ppoints.nextElement().getColumnPackedCopy();
        decoder.closestPoint(p);
        return  VectorFunctions.subtract(p, decoder.getLatticePoint());
    }

    public double percentageComplete() {
        return ppoints.percentageComplete();
    }
}
