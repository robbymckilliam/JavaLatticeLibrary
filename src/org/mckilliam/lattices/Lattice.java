package org.mckilliam.lattices;

import Jama.Matrix;
import org.mckilliam.lattices.relevant.RelevantVectors;
import org.mckilliam.lattices.util.PointEnumerator;

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
    public Matrix generatorMatrix() {
        return B;
    }

    @Override
    public int dimension() {
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
