package pubsim.lattices.Vnm.codec.generators;

/**
 * Utility classes for storing banded matrices.  Useful for efficient computation with the Vnm
 * codes.
 * @author Robby McKilliam
 */
public interface BandedGenerator {
    
    public double get(int i, int j);
    
}
