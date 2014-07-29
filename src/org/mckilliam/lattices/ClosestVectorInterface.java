/*
 * NearestPointAlgorithmInterface.java
 *
 * Created on 12 August 2007, 20:23
 */

package org.mckilliam.lattices;

import java.io.Serializable;

/**
 *
 * @author Robby McKilliam
 */
public interface ClosestVectorInterface extends Serializable {
    
    double[] closestPoint(double[] y);
    
    /**
     * Returns the nearest lattice point computed the last time closestPoint was run.
     */
    double[] getLatticePoint();
    
    /**
     * Returns the integer vector associated with the nearest lattice point, i.e so that x = Bu. 
     * The actually returned index with depend on the choice of generator.
     */
    double[] getIndex();
    
    /**
     * Return the distance between the nearest point computed 
 the last time closestPoint was run.
     */
    double distance();
    
}
