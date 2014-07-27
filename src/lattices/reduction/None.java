package pubsim.lattices.reduction;

import Jama.Matrix;

/**
 * Identity (or null) reduction, doesn't actually do any reduction
 * @author Robby McKilliam
 */
public class None implements LatticeReduction {
    
    Matrix M;
    
    @Override
    public Matrix reduce(Matrix B) {
        int n = B.getColumnDimension();
        M = Matrix.identity(n,n);
        return B;
    }
    
    @Override
    public Matrix getUnimodularMatrix() {
        return M;
    }
    
}
