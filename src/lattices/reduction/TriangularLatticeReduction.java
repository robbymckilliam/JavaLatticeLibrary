package pubsim.lattices.reduction;

import Jama.Matrix;

/**
 * Interface for lattice reduction algorithms which extensively use
 * an equivalent upper triangular basis.
 * @author Vaughan Clarkson
 */
public interface TriangularLatticeReduction extends LatticeReduction {

    /**
     * Returns the upper triangular matrix R
     */
    Matrix getR();
}
