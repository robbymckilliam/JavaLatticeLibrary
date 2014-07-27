package pubsim.lattices;

import Jama.Matrix;
import java.io.Serializable;
import pubsim.lattices.util.PointEnumerator;

/**
 * Interface for a LatticeInterface
 * @author Robby McKilliam
 */
public interface LatticeInterface extends Serializable {
    
    /** 
     * Return the volume of the fundamental region
     * of the lattice.  This is the square root of 
     * the determinant of the gram matrix
     */
    double volume();

    /**
     * Return the in radius for this lattice
     */
    double inradius();
    
    /**
     * The squared Euclidean length of the shortest non zero vector
     */
    double norm();

    /**
     * Return the covering radius for this lattice
     */
    double coveringRadius();
    
    /*
     * Return the center density:
     * inradius^n / volume;
     */
    double centerDensity();

    /*
     * Return the log2 of the center density:
     * inradius^n / volume;
     */
    double logCenterDensity();

    /*
     * log2 logarithm of the sphere packing density
     */
    double logPackingDensity();

    /*
     * log2 of the volume of the lattice.
     */
    double logVolume();

    /*
     * Sphere packing density.
     */
    double packingDensity();
    
    /*
     * Hermite parameter, also known as nomial coding gain
     */
    double hermiteParameter();
    double nominalCodingGain();

    /*
     * Effective coding gain.  See page 2396 of 
     * FORNEY AND UNGERBOECK: MODULATION AND CODING FOR LINEAR GAUSSIAN CHANNELS.
     * The argument S is a 'normalised' signal to noise ratio.
     */
    double probCodingError(double S);
    
    /** 
     * @return second moment of the Voronoi cell of this lattice.
     * This is NOT normalised for volume. 
     */
    double secondMoment();

     /*
     * The number of short vectors in the lattice.
     */
    long kissingNumber();
    
    int getDimension();
    
    /**
     * Return the generator matrix for this lattice
     * @return double[][] containing the generator matrix
     */
    Matrix getGeneratorMatrix();
    
    /** 
     * @return the Gram matrix of this lattice, i.e., if the generator it B then return B'B. 
     */
    Matrix gramMatrix();
    
    /**
     * @return the name for this lattice, if it has one
     */
    String name();

    /** @return an enumeration of the relevant vectors for this lattice */
    PointEnumerator relevantVectors();
    
}
