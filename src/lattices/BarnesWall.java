package pubsim.lattices;

import Jama.Matrix;
import pubsim.ComplexMatrix;
import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class BarnesWall extends AbstractLattice{

    protected int n;
    protected int m;

    public BarnesWall(int m){
        this.m = m;
        n = (int)Math.round(Math.pow(2, m+1));
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return n;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        Complex[][] C = new Complex[2][2];
        C[0][0] = new Complex(1,0); C[0][1] = new Complex(0,0);
        C[1][0] = new Complex(1,0); C[1][1] = new Complex(1,1);
        ComplexMatrix G = new ComplexMatrix(C);
        ComplexMatrix B = G;
        for(int i = 1; i < m; i++) B = ComplexMatrix.kroneckerProduct(B, G);
        return B.getJamaMatrix();
    }

    @Override
    public double logVolume(){
        return n*m/4;
    }

    @Override
    public double volume(){
        return Math.pow(2.0, logVolume());
    }

    @Override
    public double norm(){
        return Math.pow(2.0, m);
    }

    @Override
    public long kissingNumber(){
        long prod = 1;
        long pow = 1;
        for(int i = 1; i <= m+1; i++){
            pow *= 2;
            prod *= pow + 2;
        }
        return prod;
    }
    
    @Override
    public String name() {
        return "BWn" + n;
    }

}
