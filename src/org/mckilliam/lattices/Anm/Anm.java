package org.mckilliam.lattices.Anm;

import Jama.Matrix;
import org.mckilliam.lattices.An.AnSorted;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.cvp.ClosestVectorStandardNumenclature;

/**
 * Abstract class for the Coxeter lattice Anm
 * @author Robby McKilliam
 */
public abstract class Anm extends ClosestVectorStandardNumenclature{

    protected final int m;
    
    public Anm(int n, int m){
        super(n);
        this.m = m;
        u = new double[n+1];
        v = new double[n+1];
    }

    @Override
    public Matrix generatorMatrix() {
        LatticeInterface an = new AnSorted(n);
        Matrix Mat = an.generatorMatrix();
        double d = ((double) m)/(n+1);
        for(int i = 0; i < n+1; i++){
            Mat.set(i, n-1, -d);
        }
        Mat.set(0, n-1, m - d);
        return Mat;
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
     /** {@inheritDoc} */
    @Override
    public double volume(){
        return m/Math.sqrt(n+1);
    }

    
    @Override
    public String name() {
        return "An" + n + "m" + m;
    }
    
}
