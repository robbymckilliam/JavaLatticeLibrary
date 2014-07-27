package pubsim.lattices.reduction;

import Jama.Matrix;
import Jama.QRDecomposition;
import pubsim.VectorFunctions;
import pubsim.lattices.Lattice;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;

/*
 * Simple-minded implementation of Hermite-Korkin-Zolotarev reduction.
 * Uses Siegel-LLL with Schnorr-Euchner sphere decoding.
 * @author Vaughan Clarkson
 */
public class HKZ implements TriangularLatticeReduction {
    protected BasisCompletion bc = new BasisCompletion();
    protected ShortVectorSphereDecoded svsd;
    protected Matrix R, M;

    @Override
    public Matrix reduce(Matrix B) {
	int m = B.getRowDimension();
	int n = B.getColumnDimension();
	QRDecomposition qrd = new QRDecomposition(B);
	R = qrd.getR();
	LLL lll = new LLL();
	lll.reduce(R);
	R = lll.getR();
	M = lll.getUnimodularMatrix();
	for (int j = 0; j < n-1; j++) {
	    Matrix Rsub = R.getMatrix(j, n-1, j, n-1);
	    svsd = new ShortVectorSphereDecoded(new Lattice(Rsub));
	    Matrix sv = VectorFunctions.columnMatrix(svsd.getShortestVector());
	    bc.completeBasis(sv, Rsub);
	    Rsub = bc.getR();
	    R.setMatrix(j, n-1, j, n-1, Rsub);
	    Matrix Mdash = bc.getUnimodularMatrix();
	    M.setMatrix(0, n-1, j, n-1, 
			M.getMatrix(0, n-1, j, n-1).times(Mdash));
	}
	Hermite hermite = new Hermite();
	R = hermite.reduce(R);
	M = M.times(hermite.getUnimodularMatrix());
	return B.times(M);
    }

    @Override
    public Matrix getUnimodularMatrix() {
	return M;
    }

    @Override
    public Matrix getR() {
	return R;
    }

}