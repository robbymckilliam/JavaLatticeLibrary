package pubsim.lattices.reduction;

import Jama.Matrix;

/**
 * The Hermite lattice reduction algorithm.
 * @author Robby McKilliam
 */
public class Hermite implements TriangularLatticeReduction{
    
    /** Unimodular matrix such that reduce(B) = BM */
    protected Matrix M;
    protected Matrix R;

    /** {@inheritDoc} */
    @Override
    public Matrix reduce(Matrix B) {
        if(B == null){
            M = null;
            return null;
        }
        
        Matrix Bcopy = B.copy();
        
        //this is assumed to be the dimension of the lattice.
        int n = B.getColumnDimension();
        
        //set the unimodular matrix
        M = Matrix.identity(n, n);
        
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(Bcopy);
        R = QR.getR();
              
        for(int j = 0; j < n; j++){
            for(int i = j-1; i >= 0; i--){
                double k = Math.round(R.get(i, j)/R.get(i, i));
                
                for(int t = 0; t < n; t++){
                    double rval = R.get(t,j) - k*R.get(t,i);
                    R.set(t, j, rval);
                    double mval = M.get(t,j) - k*M.get(t,i);
                    M.set(t, j, mval);
                }
                for(int t = 0; t < B.getRowDimension(); t++){
                    double bval = Bcopy.get(t,j) - k*Bcopy.get(t,i);
                    Bcopy.set(t, j, bval);
                }
              
            }
        }
        
        return Bcopy;
    }

    /** {@inheritDoc} */
    @Override
    public Matrix getUnimodularMatrix() {
        return M;
    }
    
    @Override
    public Matrix getR() {
	return R;
    }
}
