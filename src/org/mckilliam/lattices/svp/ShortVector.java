package org.mckilliam.lattices.svp;

/**
 * Interface for computing short vectors in lattices
 * @author Robby McKilliam
 */
public interface ShortVector {
    
    public double[] getShortestVector();

    public double[] getShortestIndex();
    
}
