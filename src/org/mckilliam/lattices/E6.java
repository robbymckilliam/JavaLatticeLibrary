package org.mckilliam.lattices;

import org.mckilliam.lattices.cvp.LatticeAndClosestVectorInterface;
import Jama.Matrix;
import org.mckilliam.lattices.cvp.SphereDecoder;
import org.mckilliam.lattices.cvp.SphereDecoderSchnorrEuchner;

/**
 * The root lattice E6
 * @author Robby McKilliam
 */
public class E6 extends AbstractLattice implements LatticeAndClosestVectorInterface {
    
        protected static final double[][] dMat
            = { {0,-1,1,0,0,0,0,0},
                {0,0,-1,1,0,0,0,0},
                {0,0,0,-1,1,0,0,0},
                {0,0,0,0,-1,1,0,0},
                {0,0,0,0,0,-1,1,0},
                {1.0/2,1.0/2,1.0/2,1.0/2,-1.0/2,-1.0/2,-1.0/2,-1.0/2} };
        //Generator matrix for the E8 lattice
    protected static final Matrix B = new Matrix(dMat).transpose();

    @Override
    public double coveringRadius() {
        return Math.sqrt(norm()*8.0/3.0)/2;
    }

    @Override
    public double volume(){
        return Math.sqrt(3.0);
    }
    
    @Override
    public final long kissingNumber(){
        return 72;
    }

    @Override
    public double norm(){
        return 2.0;
    }
    
    @Override
    public int dimension() {
        return 6;
    }

    @Override
    public Matrix generatorMatrix() {
        return B;
    }

    @Override
    public String name() {
        return "E6";
    }

    @Override
    public double[] closestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
