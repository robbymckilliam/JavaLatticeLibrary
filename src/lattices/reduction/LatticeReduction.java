package pubsim.lattices.reduction;

import Jama.Matrix;

/**
 * Interface for lattice reduction algorithms
 * @author Robby McKilliam
 */
public interface LatticeReduction {
    
    /** 
     * Reduce the matrix B.  Assumes that the dimension of the lattice
     * (ie the rank of the matrix) is given by the number of columns in
     * the matrix B.
     */
    Matrix reduce(Matrix B);
    
    /** 
     * Returns the matrix M such that reduce(B) = BM
     * @return The unimodular matrix that performs the reduction
     */
    Matrix getUnimodularMatrix();

}
