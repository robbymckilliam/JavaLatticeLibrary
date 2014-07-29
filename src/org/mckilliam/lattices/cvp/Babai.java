package org.mckilliam.lattices.cvp;

import Jama.Matrix;
import pubsim.VectorFunctions;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.ClosestVectorInterface;
import org.mckilliam.lattices.reduction.LLL;
import org.mckilliam.lattices.reduction.LatticeReduction;

/**
 * Implements the Babai nearest plane algorithm.
 * This gives a rough approximation to the nearest point.
 * This does not perform the LLL reduction first.
 * Use BabaiLLL for that.
 * @author Robby McKilliam
 * Modified to use any sort of LatticeReduction (with LLL as the default)
 */
public class Babai implements ClosestVectorInterface {

    /** Generator matrix of the lattice */
    protected Matrix G;
    
    /** Index of the Babai point. x = Gu */
    protected double[] u; 
    
    /** The Babai point */
    protected double[] x;
    
    /** 
     * Index of Babai point in LLL reduced basis.
     * x = GUuh
     */
    protected double[] uh; 
    
    /** 
     * Point y in triangular reference frame.
     * yr = Q'y
     */
    protected double[] yr;
    
    /** LLL reduced basis matrix. G = BU */
    final protected Matrix B; 
    
    /** 
     * Unimodular transform between G and it's LLL
     * reduction B. G = BU
     */
    protected Matrix U; 
    
    /** R component of B = QR */
    protected Matrix R;
    
    /** Q component of B = QR */       
    protected Matrix Q, Qtrans;
    
    protected int n, m;
    protected LatticeReduction reducer;
    
    public Babai(LatticeInterface L){
	this(L, new LLL());
    }

    public Babai(LatticeInterface L, LatticeReduction lr) {
        reducer = lr;
        G = L.generatorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        
        B = reducer.reduce(G);
        U = reducer.getUnimodularMatrix();
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();
        Qtrans = Q.transpose();
    }
    
    /** Returns an Babai algorithm that does not perform any reduction first */
    public static Babai BabaiNoLLL(LatticeInterface L, int M) {
        return new Babai(L,new org.mckilliam.lattices.reduction.None());
    }

    @Override
    public double[] closestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y of length " + y.length + 
                    " and Generator matrix of column length " + m +
                    " are of different dimension!");
        computeBabaiPoint(y);        
        return getLatticePoint();
    }

    @Override
    public double[] getLatticePoint() {
        return x;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    //compute the babai
    protected void computeBabaiPoint(double[] y) {
        
         VectorFunctions.matrixMultVector(Qtrans, y, yr);

        for (int i = n - 1; i >= 0; i--) {
            double rsum = 0.0;
            for (int j = n - 1; j > i; j--) {
                rsum += R.get(i, j) * uh[j];
            }
            uh[i] = Math.round((yr[i] - rsum) / R.get(i, i));
        }
    
        //compute index u = Uuh so that Gu is Babai point
        VectorFunctions.matrixMultVector(U, uh, u);
        
        //compute Babai point
        VectorFunctions.matrixMultVector(G, u, x);

    }
    
    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
