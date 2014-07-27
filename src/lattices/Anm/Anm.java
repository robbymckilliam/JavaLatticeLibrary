/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Anm;

import Jama.Matrix;
import pubsim.lattices.An.AnSorted;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.NearestPointAlgorithmStandardNumenclature;

/**
 * Abstract class for the Coxeter lattice Anm
 * @author Robby McKilliam
 */
public class Anm extends NearestPointAlgorithmStandardNumenclature{

    protected final int m;
    
    public Anm(int n, int m){
        super(n);
        this.m = m;
        u = new double[n+1];
        v = new double[n+1];
    }

    @Override
    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Matrix getGeneratorMatrix() {
        LatticeInterface an = new AnSorted(n);
        Matrix Mat = an.getGeneratorMatrix();
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
