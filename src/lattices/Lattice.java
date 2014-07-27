package pubsim.lattices;

import Jama.Matrix;
import pubsim.lattices.relevant.RelevantVectors;
import pubsim.lattices.util.PointEnumerator;

/**
 * Class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public class Lattice extends AbstractLattice {

    /** The generator matrix for the lattice */
    final protected Matrix B;
    
    public Lattice(Matrix B){
        this.B = B;     
    }
    
    public Lattice(double[][] B){
        this.B = new Matrix(B);
    }

    @Override
    public Matrix getGeneratorMatrix() {
        return B;
    }

    @Override
    public int getDimension() {
        return B.rank();
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
