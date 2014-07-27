package pubsim.lattices;

import Jama.Matrix;

/**
 * The Kschischang-Pasupathy 36-d lattice, the densest packing 
 * known in 36 dimensions.
 * @author Robby McKilliam
 */
public class KP36 extends AbstractLattice {

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return 36;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public double volume() {
        return 3486784401.0;
    }
    
    @Override
    public double norm() {
        return 8.0;
    } 
    
    @Override
    public long kissingNumber() {
        return 239598;
    }
    
    @Override
    public String name() { 
        return "KP36";
    }
    
}
