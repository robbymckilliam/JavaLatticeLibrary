package pubsim.lattices;

import Jama.Matrix;

/**
 * The root lattice E6
 * @author Robby McKilliam
 */
public class E6 extends AbstractLattice implements LatticeAndNearestPointAlgorithmInterface {
    
        protected static final double[][] dMat
            = { {0,-1,1,0,0,0,0,0},
                {0,0,-1,1,0,0,0,0},
                {0,0,0,-1,1,0,0,0},
                {0,0,0,0,-1,1,0,0},
                {0,0,0,0,0,-1,1,0},
                {1.0/2,1.0/2,1.0/2,1.0/2,-1.0/2,-1.0/2,-1.0/2,-1.0/2} };
        //Generator matrix for the E8 lattice
    protected static final Matrix B = new Matrix(dMat).transpose();

    @Override
    public double coveringRadius() {
        return Math.sqrt(norm()*8.0/3.0)/2;
    }

    @Override
    public double volume(){
        return Math.sqrt(3.0);
    }
    
    @Override
    public final long kissingNumber(){
        return 72;
    }

    @Override
    public double norm(){
        return 2.0;
    }
    
    @Override
    public int getDimension() {
        return 6;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return B;
    }

    @Override
    public String name() {
        return "E6";
    }

    @Override
    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nearestPoint(Double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
