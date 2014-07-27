package pubsim.lattices.reduction;

import Jama.Matrix;
import pubsim.VectorFunctions;

/**
 * The Lenstra, Lenstra and Lovas lattice reduction algorithm.
 * @author Robby McKilliam
 * Modified by Vaughan Clarkson to make it extensible for the purpose
 * of lattice basis completion.
 */
public class LLL implements TriangularLatticeReduction{

    protected Matrix B; // the lattice basis
    /** Unimodular matrix such that reduce(B) = BM */
    protected Matrix M;
    protected Matrix R; // triangularised basis
    protected int j; // loop variable
    protected int m; // embedded dimension
    protected int n; // lattice rank

    protected boolean notDone() {
	return j < n-1;
    }

    protected void finishUp() {
        Hermite hermite = new Hermite();
        B = hermite.reduce(B);
        R = hermite.getR();
        M = M.times(hermite.getUnimodularMatrix());
    }

    @Override
    public Matrix reduce(Matrix Bin){
        if(Bin == null){
            M = null;
            return null;
        }
        
        B = Bin.copy();
        
        //this is the dimension of the lattice.  Need to check this!
        n = B.getColumnDimension();
        m = B.getRowDimension();
        
        //set the unimodular matrix
        M = Matrix.identity(n, n);
        
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        R = QR.getR();
	j = 0;
	int iter = 0;
        while(notDone()){

            double rjj = R.get(j,j);
            double rj1j1 = R.get(j+1, j+1);
            
            if( rjj*rjj > 2 * rj1j1*rj1j1 ){
                
                double k = Math.round( R.get(j, j+1) / rjj );
                
                for(int t = 0; t < n; t++){
                    double rval = R.get(t,j+1) - k*R.get(t,j);
                    R.set(t, j+1, rval);
                    double mval = M.get(t,j+1) - k*M.get(t,j);
                    M.set(t, j+1, mval);
                }
                for(int t = 0; t < m; t++){
                    double bval = B.get(t,j+1) - k*B.get(t,j);
                    B.set(t, j+1, bval);
                }
                
                VectorFunctions.swapColumns(B, j, j+1);
                VectorFunctions.swapColumns(M, j, j+1);
                               
                //QR decomposition version.  Superceeded by Given's rotation
//                Jama.QRDecomposition QRt = new Jama.QRDecomposition(B);
//                R = QRt.getR();
                            
                //Swap columns and Given's rotate R to make it triangular.
                VectorFunctions.swapColumns(R, j, j+1);
                VectorFunctions.givensRotate(R, j, j+1, j);
                
                        
                if(j > 0) j--;
                
            }else{
                j++;
            }
        }
        
	finishUp();

        //M = hermite.getUnimodularMatrix().times(M);
        return B;
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
