/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Vnm;

import Jama.Matrix;
import pubsim.Util;
import pubsim.VectorFunctions;
import pubsim.lattices.AbstractLattice;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.decoder.KissingNumber;

/**
 * Class for the lattice Vnm, ie the integer lattice that is the
 * dual of the polynomial phase estimation lattices Vnm*.  There is
 * no nearest point algorithms for these lattices (yet?).  
 * @author Robby McKilliam
 */
public class Vnm extends AbstractLattice{
    
    protected int m;
    protected int n;
    
    public Vnm(int n, int m){
        this.m = m;
        this.n = n;
    }

    /**
     * Uses nifty binomial formula to compute the volume.
     * @return
     */
    @Override
    public double volume() {
        return volume(n, m);
    }

    public static double volume(int n, int m){
        return Math.pow(2, logVolume(n, m));
    }

    /**
     * Uses nifty binomial formula to compute the log of the volume.
     */
    @Override
    public double logVolume() {
        return logVolume(n, m);
    }

    /**
     * Uses nifty binomial formula to compute the log of the volume.
     */
    public static double logVolume(int n, int m) {
        double vol = 0.0;
        int N = n+m+1;
        for(int k = 0; k <= m; k++){
            vol += ( Util.log2Binom(N+k, 2*k+1) - Util.log2Binom(2*k, k) );
        }
        return vol/2.0;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        
        if( m == -1 ) return Matrix.identity(n, n);
        
        double[] bv = getGeneratorColumn(n, m); 
        
        int N = n+m+1;
        Matrix gen = new Matrix(N, n);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++)
                gen.set(j,i,0.0);
            for(int j = i; j < i + bv.length; j++)
                gen.set(j,i,bv[j-i]);
            for(int j = i + bv.length; j < N; j++)
                gen.set(j,i,0.0);
        }
        
        return gen;
    }

    public static double[] getGeneratorColumn(int n, int m){
        double[] cv  = {1, -1};
        double[] bv = {1, -1};
        for(int i = 0; i < m; i++){
            bv = VectorFunctions.conv(cv, bv);
        }
        return bv;
    }
    
    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return n;
    }
    
    @Override
    public String name() {
        return "Vn" + n + "m" + m;
    }
    
    /** 
     * Returns the kissing number under the assumption that n is large enough
     * for the norm to be 2(m+1), otherwise zero will be returned.
     */
    public long seededKissingNumber(){
        return new KissingNumber(this, 2*(m+1)).kissingNumber();
    }
    
//    /**
//     * When m = 0, this is the integer lattice
//     */
//    public static class Vn0 extends Zn {
//        public Vn0(int n){ super(n); }
//    }
    
     /**
     * When m = 0, this is the root An
     */
    public static class Vn0 extends AnFastSelect {
        public Vn0(int n){ super(n); }
    }
    
    /** 
     * When m = 1, we know the kissing number
     */
    public static class Vn1 extends Vnm {
        public Vn1(int n) { super(n, 1); }
        
        @Override
        public long kissingNumber() {
            if(n==1) return 2;
            else if(n%2 == 0) return n*(2+n)*(2*n-1)/12;
            else return (n-1)*(n+1)*(2*n+3)/12;
        }
        
        public static long kissingNumber(int n, int m){
            if(n==1) return 2;
            else if(n%2 == 0) return n*(2+n)*(2*n-1)/12;
            else return (n-1)*(n+1)*(2*n+3)/12;
        }
        
        @Override
        public double norm() {
            if(n == 1) return 6;
            else return 4;
        }
    }
    
    /** 
     * When m = 1, we know the kissing number
     */
    public static class Vn2 extends Vnm {
        public Vn2(int n) { super(n, 2); }
        
        private long kissingnumber;
        @Override
        public long kissingNumber() {
            if(kissingnumber == 0){
                pubsim.lattices.decoder.KissingNumber k = new KissingNumber(this, norm());
                kissingnumber = k.kissingNumber();
            }
            return kissingnumber;
        }
        
        @Override
        public double norm() {
            if(n > 4) return 6;
            else return super.norm();
        }
    }
    
        /** 
     * When m = 1, we know the kissing number
     */
    public static class Vn3 extends Vnm {
        public Vn3(int n) { super(n, 3); }
        
        private long kissingnumber;
        @Override
        public long kissingNumber() {
            if(kissingnumber == 0){
                pubsim.lattices.decoder.KissingNumber k = new KissingNumber(this, norm());
                kissingnumber = k.kissingNumber();
            }
            return kissingnumber;
        }
        
        @Override
        public double norm() {
            if(n > 8) return 8;
            else return super.norm();
        }
    }
    
    /**
     * Returns the approximated norm and kissing numbers.  The norm returned is always a
     * lower bound.  The kissing number returned is a known to be an upper bound when the
     * dimension n is not too large.  I conjecture it to be a lower bound provided that n is not larger
     * that 1000.
     */
    public static class Approximator extends Vnm {
        public Approximator(int n, int m){
            super(n, m);
        }
        
        @Override
        public double norm() { return 2*(m+1); }
        
        @Override
        public long kissingNumber() {
            return Vn1.kissingNumber(n, m);
        }
    }
    
    
}
