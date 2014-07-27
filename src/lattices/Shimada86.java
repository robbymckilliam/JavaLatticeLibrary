package pubsim.lattices;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public class Shimada86 extends AbstractLattice{

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDimension(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final int getDimension() {
        return 86;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double volume(){
        return 256 * Math.sqrt(3);
    }

    @Override
    public double norm(){
        return 8;
    }

    @Override
    public final long kissingNumber(){
        return 109421928;
    }
    
    @Override
    public String name() { 
        return "Shimada86";
    }

}
