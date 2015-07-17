package org.mckilliam.lattices;

import org.mckilliam.lattices.cvp.LatticeAndClosestVectorInterface;
import Jama.Matrix;
import static pubsim.VectorFunctions.times;

/**
 * Class for storing scaled versions of lattices
 * @author Robby McKilliam
 */
public class ScaledLattice extends AbstractLattice implements LatticeAndClosestVectorInterface {
    
    protected final LatticeAndClosestVectorInterface L;
    protected final double d;
    protected final Matrix B;
    protected final int n,m;

    protected final double[] yd, v;
    
    /**
     * @param L the lattice
     * @param d the scale factor
     */
    public ScaledLattice(LatticeAndClosestVectorInterface L, double d){
        this.L = L;
        this.d = d;
        this.B = L.generatorMatrix().times(d);
        this.n = L.dimension();
        this.m = B.getRowDimension();
        yd= new double[m];
        v = new double[m];
    }
    

    @Override
    public double coveringRadius() {
        return d*L.coveringRadius();
    }
    
    @Override
    public double norm() {
        return d*d*L.norm();
    }

    @Override
    public double volume() {
        return Math.pow(d,n)*L.volume();
    }

    @Override
    public int dimension(){
        return n;
    }

    @Override
    public Matrix generatorMatrix(){
        return B;
    }

    @Override
    public double[] closestPoint(double[] y) {
        if(y.length != m) throw new RuntimeException("vector to compute the nearest point to is the wrong size");
        times(y,1/d,yd);
        L.closestPoint(yd);
        times(L.getLatticePoint(),d,v);
        return v;
    }

    @Override
    public double[] getLatticePoint() {
       return v;
    }

    @Override
    public double[] getIndex() {
        return L.getIndex();
    }

    @Override
    public double distance() {
        return d*L.distance();
    }
    
    @Override
    public String name() { 
        return L.name();
    }
    
    
    
}
