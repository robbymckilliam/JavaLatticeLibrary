package pubsim.lattices;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public class P48 extends AbstractLattice{

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final int getDimension() {
        return 48;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double volume(){
        return 1.0;
    }
    
    @Override
    public double norm(){
        return 6;
    }

    @Override
    public final long kissingNumber(){
        return 52416000;
    }
    
    @Override
    public String name() { return "P48"; }


}
