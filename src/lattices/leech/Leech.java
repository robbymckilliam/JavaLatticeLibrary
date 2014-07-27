/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.leech;

import Jama.Matrix;
import pubsim.lattices.AbstractLattice;

/**
 * Standard, unimodular, self-dual representation of the Leech lattice.
 * See page 133 of SPLAG.
 * @author Robby McKilliam
 */
public class Leech extends AbstractLattice{

    @Override
    public double volume() {
        return 1.0;
    }

    @Override
    public double norm() {
        return 4;
    }

    @Override
    public double coveringRadius() {
        return Math.sqrt(2.0);
    }

    @Override
    public final int getDimension() {
        return 24;
    }

    protected static final double[][] dMat
            = { {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {2,2,2,2,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0},
                {2,2,0,0,2,2,0,0,2,2,0,0,2,2,0,0,0,0,0,0,0,0,0,0},
                {2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,0,0,0,0,0,0,0,0},
                {2,0,0,2,2,0,0,2,2,0,0,2,2,0,0,2,0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0},
                {2,0,2,0,2,0,0,2,2,2,0,0,0,0,0,0,2,2,0,0,0,0,0,0},
                {2,0,0,2,2,2,0,0,2,0,2,0,0,0,0,0,2,0,2,0,0,0,0,0},
                {2,2,0,0,2,0,2,0,2,0,0,2,0,0,0,0,2,0,0,2,0,0,0,0},
                {0,2,2,2,2,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0},
                {0,0,0,0,0,0,0,0,2,2,0,0,2,2,0,0,2,2,0,0,2,2,0,0},
                {0,0,0,0,0,0,0,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0},
                {-3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} };

    //Generator matrix for the Leech lattice
    protected static final Matrix mat
                    = new Matrix(dMat).times(1.0/Math.sqrt(8.0)).transpose();

    @Override
    public Matrix getGeneratorMatrix() {
        return mat;
    }

    @Override
    public final long kissingNumber(){
        return 196560;
    }

    /*
     * Second moment taken from SPLAG page 61 (it's an approximation made by Monte-Carlo)
     */
    @Override
    public double secondMoment(){
        return 24 * 0.065771;
    }
    
    @Override
    public String name() {
        return "Leech";
    }
    
}
