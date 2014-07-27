package pubsim.lattices;

import Jama.Matrix;
import pubsim.VectorFunctions;

/**
 * The E8 lattice with nearest point algorithm based on gluing two translates of D8.
 * @author Robby McKilliam
 */
public class E8 extends AbstractLattice implements LatticeAndNearestPointAlgorithmInterface {
    
    final protected int n = 8;
    final protected double[] yt= new double[8],u = new double[8], v = new double[8];
    final protected Dn dn1 = new Dn(8),dn2 = new Dn(8);
    protected double dist;

    @Override
    public double coveringRadius() {
        return 1.0;
    }

    @Override
    public final int getDimension() {
        return 8;
    }
    
    protected static final double[][] dMat
            = { {2,0,0,0,0,0,0,0},
                {-1,1,0,0,0,0,0,0},
                {0,-1,1,0,0,0,0,0},
                {0,0,-1,1,0,0,0,0},
                {0,0,0,-1,1,0,0,0},
                {0,0,0,0,-1,1,0,0},
                {0,0,0,0,0,-1,1,0},
                {1.0/2,1.0/2,1.0/2,1.0/2,1.0/2,1.0/2,1.0/2,1.0/2} };
    
    //Generator matrix for the E8 lattice
    protected static final Matrix B
                    = new Matrix(dMat).transpose();
    protected static final Matrix Binv = B.inverse();

    @Override
    public Matrix getGeneratorMatrix() {
        return B;
    }

    @Override
    public double volume(){
        return 1.0;
    }

    @Override
    public double norm(){
        return 2.0;
    }

    @Override
    public final long kissingNumber(){
        return 240;
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length) throw new RuntimeException("vector must have length 8");
        
        dn1.nearestPoint(y);
        for(int i = 0; i < n; i++) yt[i] = y[i] - 1.0/2.0;
        dn2.nearestPoint(yt);
        
        if(dn1.distance() < dn2.distance()){
            dist = dn1.distance();
            System.arraycopy(dn1.getLatticePoint(), 0, v, 0, n);
        }
        else{
            dist = dn2.distance();
            for(int i = 0; i < n; i++) v[i] = dn2.getLatticePoint()[i] + 1.0/2.0;
        }

    }

    @Override
    public double secondMoment() { return 929.0/1620; }
    
    @Override
    public double[] getLatticePoint() {
        return v;
    }

    @Override
    public double[] getIndex() {
        VectorFunctions.matrixMultVector(Binv, v, u);
        return u;
    }

    @Override
    public double distance() {
        return dist;
    }
    
    private double[] yDoubletoy = new double[8];
    @Override
    public void nearestPoint(Double[] y) {
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i]; 
        this.nearestPoint(yDoubletoy);
    }
    
    @Override
    public String name() {
        return "E8";
    }


}
