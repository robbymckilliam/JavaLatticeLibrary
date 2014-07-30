package org.mckilliam.lattices;

import Jama.Matrix;

/**
 * Class that represents a lattice with arbitrary
 * generator matrix.
 * @author Robby McKilliam
 */
public class Lattice extends AbstractLattice {

    /** The generator matrix for the lattice */
    final protected Matrix B;
    
    final protected String name;
        
    public Lattice(Matrix B, String name){
        this.B = B;     
        this.name = name;
    }
    
    public Lattice(Matrix B){
        this(B, "SomeLattice");
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
        return name;
    }

}
