package pubsim.lattices;

import Jama.Matrix;
import static pubsim.Util.*;
import pubsim.VectorFunctions;
import pubsim.lattices.decoder.KissingNumber;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;
import pubsim.lattices.relevant.RelevantVectors;
import pubsim.lattices.util.PointEnumerator;

/**
 * Abstract lattice that contains default operations for many of the
 * methods in interface LatticeInterface.
 * @author Robby McKilliam
 */
public abstract class AbstractLattice implements LatticeInterface {
    
    /** Stores the Gram matrix for this lattice */
    protected Matrix Q;
    
    @Override
    public double secondMoment() {throw new UnsupportedOperationException(); }

    @Override
    public double centerDensity() {
        return pow2(logCenterDensity());
    }

    @Override
    public double logCenterDensity() {
        return getDimension()*log2(inradius()) - logVolume();
    }

    @Override
    public double logVolume(){
        return log2(volume());
    }

    @Override
    public double logPackingDensity() {
        return logCenterDensity() + log2HyperSphereVolume(getDimension());
    }

    @Override
    public double packingDensity() {
        return pow2(logPackingDensity());
    }
    
    /*
     * Hermite parameter, also known as nomial coding gain
     */
    @Override
    public double hermiteParameter(){
        double logv = 2.0*logVolume()/getDimension(); //using logarithm tests to be more stable
        return norm()/Math.pow(2.0,logv);
    }
    @Override
    public double nominalCodingGain() { return hermiteParameter(); }

    /*
     * Effective coding gain.  See page 2396 of 
     * FORNEY AND UNGERBOECK: MODULATION AND CODING FOR LINEAR GAUSSIAN CHANNELS.
     * The argument S is a 'normalised' signal to noise ratio.
     * This assumes no shaping gain, i.e. rectangular shaping region
     */
    @Override
    public double probCodingError(double S) {
        return kissingNumber() * Q(Math.sqrt(nominalCodingGain() * 3.0 * S));
    }

    @Override
    public double volume(){
        Matrix B = getGeneratorMatrix();
        return Math.sqrt((B.transpose().times(B)).det());
    }

    //make consecutive calls to inradius and kissing number run fast.
    private double norm;
    private long kissingnumber;

    /**
     * Half the square root of the norm
     */
    @Override
    public final double inradius(){
        if(norm==0.0) norm = norm();
        return Math.sqrt(norm)/2.0;
    }

    /**
     * Default way to compute the norm is to compute
     * short vector by sphere decoding.  This is going to
     * be very slow for large dimensions.
     */
    @Override
    public double norm(){
        if(norm == 0){
            ShortVectorSphereDecoded sv = new ShortVectorSphereDecoded(this);
            norm = VectorFunctions.sum2(sv.getShortestVector());
        }
        return norm;
    }
    
    /**
     * By default this brute forces the kissing number by sphere decoding.
     * Lattices with known kissing numbers can override this.
     */
    @Override
    public long kissingNumber() {
        if(kissingnumber == 0){
            pubsim.lattices.decoder.KissingNumber k = new KissingNumber(this);
            kissingnumber = k.kissingNumber();
        }
        return kissingnumber;
    }
    
    @Override
    public Matrix gramMatrix() {
        if(Q==null) { //lazy evaluation of Q (silly Java)
            Matrix B = getGeneratorMatrix();
            Q = B.transpose().times(B);
        } 
        return Q;
    }
    
     /** @return an enumeration of the relevant vectors for this lattice */
    @Override
    public PointEnumerator relevantVectors() {
        return new RelevantVectors(new LatticeAndNearestPointAlgorithm(this.getGeneratorMatrix()));
    }
    
}
