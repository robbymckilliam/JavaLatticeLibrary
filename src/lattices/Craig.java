package pubsim.lattices;

import Jama.Matrix;
import static pubsim.Util.log2;
import pubsim.lattices.An.AnSorted;

/**
 *
 * @author Robby McKilliam
 */
public class Craig extends AbstractLattice{

    protected final int n, r;

    /**
     * Craig's difference lattices.  Craig(n,0) = Z^(n+1).
     * Craig(n,1) = An.
     * @param n dimension
     * @param r difference order.
     */
    public Craig(int n, int r){
        this.n = n;
        this.r = r;
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

    /**
     * This is only true if p = n+1 is prime and if
     * p = 3 mod 4.
     * @return
     */
    @Override
    public long kissingNumber(){
        return n*(n+1);
    }

    /**
     * This is only true if p = n+1 is a prime and p+1 is divisible by 4.
     */
    @Override
    public double norm(){
         return 2*r;
    }

    @Override
    public double volume(){
        if(r == 0) return 1.0;
        return Math.pow(n+1, r - 0.5);
    }

    @Override
    public double logVolume(){
        return (r-0.5)*log2(n+1);
    }

    /**
     * This is not an efficient way of constructing the generator matrix.
     * A better way would be to use the cyclic nature of the matrix.
     * This is simple to code though.
     */
    @Override
    public Matrix getGeneratorMatrix() {
        if(r == 0) return Matrix.identity(n+1, n+1);

        Matrix D = (new AnSorted(n)).getGeneratorMatrixBig();
        Matrix A = (new AnSorted(n)).getGeneratorMatrix();
        for(int i = 1; i < r; i++){
            A = D.times(A);
        }
        return A;

    }
    
    @Override
    public String name() { 
        return "Craign" + n + "r" + r;
    }

}
