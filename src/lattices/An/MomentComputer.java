/*
 */
package pubsim.lattices.An;

import bignums.BigInteger;
import bignums.BigRational;
import java.io.*;

/**
 * Runs the recursion to compute moments for An and also the probability of error
 * @author Robby McKilliam
 */
public class MomentComputer {
    
    //stores the recurively computed values
    RecursionStorageAndCompute Gr;
    
    final BigInteger one = new BigInteger(Integer.toString(1));
    
    final String fname;
    
    protected int mUsed = 0;
    
    public MomentComputer(String fname){
        this.fname = fname;
        //try to read in a stored table of values
        FileInputStream fis;
        ObjectInputStream ois;
        try{
            fis = new FileInputStream(fname);
            ois = new ObjectInputStream(fis);
            Gr = (RecursionStorageAndCompute) ois.readObject();
        }
        catch(Exception ex){
            System.out.println("Read failed with exception:");
            System.out.println(ex.getMessage());
            System.out.println("I'm going to generate a new moment table. This will be slow!");
            Gr = new RecursionStorageAndCompute();
        }
        
    }
    
    /**
     * Compute the probability of error when the noise variance is v
     * with error less than tol
     * @param n lattice dimension
     * @param v noise variance
     * @param tol maximum approximation error
     * @return A double representing the probability of error.
     */
    public double ProbError(int n, BigRational v, double tol){
        
        double ce = Double.POSITIVE_INFINITY;
        BigRational sum = new BigRational(1,1);
        int m = 1;
        while(ce > tol){
            BigRational denom = v.pow(m).multiply(one.shiftLeft(m));
            BigRational mscale = new BigRational(n,n+2*m);
            BigRational toadd = mscale.multiply(Gr.moment(n, m).divide(denom));
            ce = toadd.doubleValue();
 //           System.out.println(ce);
            if(m%2 == 0) sum = sum.add(toadd);
            else sum = sum.subtract(toadd);
            m++;
        }
        mUsed = m;
        double S = sum.doubleValue()*Math.sqrt(n+1);
        double probcorrect = S/Math.pow(2*Math.PI*v.doubleValue(),n/2.0);
        return 1.0 - probcorrect;
    }
    
    /** Save the recursion data to a file so that it need not be computed again! */
    public void save(String fname){
        FileOutputStream fos;
        ObjectOutputStream oos;
        try{
            fos = new FileOutputStream(fname);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Gr);
            oos.close();
        }
        catch(Exception ex){
            throw new RuntimeException("Something went wrong when saving the recursion data");
        }
    }
    public void save() { save(fname); }
    
    public int numberMomentsLastUsed() {return mUsed; }
    
    /**
     * Class efficiently computing and storing the recursively generated
     * moments of the lattice An.
     */
    public static class RecursionStorageAndCompute implements Serializable{
        
        //Storage arrays.  This is a bit dodgy as these
        //array cannot grow.  They are intended to be set much larger
        //than will ever be needed (hopefully)!  This code will break
        //if you attempt to generate really large moments, i.e. larger
        //than about 50 and n can't be larger than 26.
        final BigRational[][][] G = new BigRational[26][500][1000];
        final BigInteger[] F = new BigInteger[1000];
        final BigInteger[][] B = new BigInteger[800][1000];
        //final BigInteger[] K = new BigInteger[2000];
        final BigRational[][] M = new BigRational[26][500];
        
        final BigInteger one = new BigInteger(Integer.toString(1));
        
        public RecursionStorageAndCompute(){
            
            //fill up initial values for our recursions
            F[0] = new BigInteger(Integer.toString(1));
            for(int m = 0; m < B.length; m++)
                B[m][0] = new BigInteger(Integer.toString(1));
            for(int k = 1; k < B.length; k++)
                B[0][k] = new BigInteger(Integer.toString(0));
            
            //fill a list of BigIntegers to avoid parsing later on.
            //for(int k = 0; k < K.length; k++)
            //    K[k] = new BigInteger(Integer.toString(k));
            //}
        }
        
        public BigRational G(int n, int k, int m){
            BigRational ret = null;
            if(n < 1 || k < 0 || m < 0) throw new RuntimeException("n,k or m are out of bounds");
            else if(n == 1) return new BigRational(1,2*k+m+1);
            else if(k==0 && m==0) return new BigRational(1, 1);
            else if(G[n][k][m] != null) return G[n][k][m];
            
            BigRational R = new BigRational(0,1);
            for(int kd = 0; kd <= k; kd++){
                for(int md = 0; md <= m; md++){
                    BigRational r = new BigRational(binom(k,kd).multiply(binom(m,md)), 
                                    new BigInteger(Integer.toString(2*kd+md+1,2),2));
                    R = R.add(r.multiply(G(n-1,k-kd,m-md)));
                }
            }
            G[n][k][m] = R;
            return R;
        }
        
        public BigInteger factorial(int n){
            if(F[n]!=null) return F[n];
            else F[n] = new BigInteger(Integer.toString(n,2),2).multiply(factorial(n-1));
            return F[n];
        }
        
        public BigInteger binom(int m, int k){
            if(B[m][k]!=null) return B[m][k];
            if(m-k < k) return binom(m,m-k);
            else B[m][k] = binom(m-1,k-1).multiply(new BigInteger(Integer.toString(m,2),2)).divide(new BigInteger(Integer.toString(k,2),2));
            return B[m][k];
        }
        
        public BigRational moment(int n, int m){
            //if(n==1) M[n][m] = new BigRational(1,2*m+1).divide(factorial(m));
            if(M[n][m]!=null) return M[n][m];

            BigInteger N = new BigInteger(Integer.toString(n,2),2);
            BigInteger Np1 = new BigInteger(Integer.toString(n+1,2),2);
            BigRational R = new BigRational(0,1);
            for(int k = 0; k <= m; k++){
                for(int a = 0; a <= k; a++){
                    for(int b = 0; b <= k-a; b++){
                        BigInteger twopb = one.shiftLeft(b);
                        BigInteger numer = twopb.multiply(N.pow(m-k));
                        
                        
                        BigInteger denom = Np1.pow(m-a).multiply(factorial(a))
                                .multiply(factorial(b))
                                .multiply(factorial(m-k))
                                .multiply(factorial(k-a-b));
                        
                        BigRational H = new BigRational(numer, denom);
                        
                        if((k-a)%2 == 0) 
                            R = R.add(H.multiply(G(n-1,a,2*k-2*a-b)));
                        else 
                            R = R.subtract(H.multiply(G(n-1,a,2*k-2*a-b)));
                    }
                }
            }
            M[n][m] = R;
            return M[n][m];
        }
         
    } 
    
    
    
    
    
}
