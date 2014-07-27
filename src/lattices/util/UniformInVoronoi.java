package pubsim.lattices.util;

import Jama.Matrix;
import pubsim.distributions.Uniform;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.NearestPointAlgorithmInterface;
import pubsim.lattices.decoder.SphereDecoder;
import pubsim.distributions.processes.NoiseVector;
import pubsim.VectorFunctions;

/**
 * Generate points uniformly in the Voronoi region using
 * uniformly generate noise.
 * @author Robby McKilliam
 */
public class UniformInVoronoi 
        extends AbstractPointEnumerator
        implements PointEnumerator{
    
    private int numsamples = 1000, count = 0;
    protected NearestPointAlgorithmInterface decoder;
    private NoiseVector nv;
    Matrix B;


    protected UniformInVoronoi() {}

    /**
     * Default to using the sphere decoder to compute nearest points
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public UniformInVoronoi(LatticeInterface L, int samples){
        B = L.getGeneratorMatrix();
        decoder = new SphereDecoder(L);
        numsamples = samples;
        initNoiseVector(L.getDimension());
    }

    /**
     * Using the nearest point algorithm supplied.
     * @param L is the lattice with included nearest point algorithm.
     * @param samples is the number of samples used per dimension
     */
    public UniformInVoronoi(LatticeAndNearestPointAlgorithmInterface L, int samples){
        B = L.getGeneratorMatrix();
        decoder = L;
        numsamples = samples;
        initNoiseVector(L.getDimension());
    }

    public boolean hasMoreElements() {
        return count < numsamples;
    }

    public Matrix nextElement() {
        return VectorFunctions.columnMatrix(nextElementDouble());
    }

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    public double[] nextElementDouble() {
        count++;
        double[] p = VectorFunctions.matrixMultVector(B, nv.generateReceivedSignal());
        decoder.nearestPoint(p);
        return  VectorFunctions.subtract(p, decoder.getLatticePoint());
    }

    protected void initNoiseVector(int N) {
        //System.out.println("N = " + N);
        Uniform noise =Uniform.constructFromMeanAndRange(0,1.0);
        noise.randomSeed();
        nv = new NoiseVector(noise, N);
    }

    public double percentageComplete() {
        return (100.0 * count) / numsamples;
    }



}
