package pubsim.lattices.Vnmstar;

import bignums.BigInteger;
import bignums.BigRational;
import Jama.Matrix;
import bignums.RoundingMode;

/**
 * A class for efficiently and stably dealing with Hilbert matrices and their inverses.
 * @author Robby McKilliam
 */
public class HilbertMatrix {
    
    final BigRational[][] qmem;
    final BigRational[][] Pmem;
    final BigRational[][] Hinvmem;
    final int m;
    final int N;
    
    /** M by M Hilbert matrix */
    public HilbertMatrix(int M, int N){
        m = M-1;
        this.N = N;
        qmem = new BigRational[m+1][m+1];
        Pmem = new BigRational[m+1][m+1];
        Hinvmem = new BigRational[m+1][m+1];
    }
    
    /** 
     * Coefficients of the integer valued polynomials.  Memoizes numbers we will need 
     * regularly for this Hilbert matrix.
     */
    public BigRational q(int s, int i) {
        if(i < 0 || s < 0) return BigRational.ZERO;
        else if(i == 0 && s==0) return BigRational.ONE;
        else if(s == 0) return BigRational.ZERO; 
        else if(i <= m && s <= m && qmem[s][i] != null) return qmem[s][i];
        BigRational num = (q(s-1,i-1).divide(new BigRational(s,1))).subtract(q(s-1,i));
        if(i <= m && s <= m) qmem[s][i] = num;
        return num;
    }
    
    /** 
     * Coefficients of the discrete orthogonal polynomials.  Memoizes numbers we will need 
     * regularly for this Hilbert matrix.
     */
    public BigRational P(int k, int i) {
        if(i < 0 || k < 0) return BigRational.ZERO;
        if(i <= m && k <= m && Pmem[k][i] != null) return Pmem[k][i];
        BigRational sum = BigRational.ZERO;
        for(int s = 0; s <= k; s++){
            BigInteger v = BigInteger.ONE.negate().pow(s+k).multiply(binom(s+k,s)).multiply(binom(N-s-1,N-k-1));
            sum = sum.add(q(s,i).multiply(new BigRational(v)));
        }
        if(i <= m && k <= m && Pmem[k][i] != null) Pmem[k][i] = sum;
        return sum;
    }
    
    /**
     * Calculate the binomial coefficient
     * using a recursive procedure.
     */
    public static BigInteger binom(int n, int m) {
        if(m > n) return BigInteger.ZERO;
        if(n==m || m==0) return BigInteger.ONE;
        if(n-m < m) return binom(n,n-m);
        return binom(n-1,m-1).multiply(new BigInteger(Integer.toString(n))).divide(new BigInteger(Integer.toString(m)));
    }
    
     /** 
     * Elements of the inverse Matrix.  Memoizes elements
     */
    public BigRational Hinv(int i, int j){
        if(i < 0 || j < 0) return BigRational.ZERO;
        if(i <= m && j <= m && Hinvmem[i][j] != null) return Hinvmem[i][j];
        BigRational sum = BigRational.ZERO;
        for(int k = 0; k <= m; k++){
            BigInteger b = binom(2*k, k).multiply(binom(N+k, 2*k+1));
            sum = sum.add(P(k,i).multiply(P(k,j).multiply(new BigRational(BigInteger.ONE,b))));
        }
        if(i <= m && j <= m) Hinvmem[i][j] = sum;
        return sum;
    }
    
    /** Rounds the values of the inverse to doubles and returns a matrix */
    public Matrix HinverseDouble() {
        Matrix w = new Matrix(m+1,m+1);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j <= m; j++)
                w.set(i,j, Hinv(i,j).doubleValue());
        return w;
    }
    
    /** 
     * This matrix projects onto the coefficients in the space of polynomials.  Useful for
     * polynomial phase estimation.
     */
    public BigRational KProjection(int i, int j){
        BigRational sum = BigRational.ZERO;
        for(int k = 0; k <=m; k++) {
            BigInteger v = new BigInteger(Integer.toString(j+1)).pow(k);
            sum = sum.add(Hinv(i,k).multiply(new BigRational(v)));
        }
        return sum;
    }
    
    public Matrix KDouble() {
        Matrix w = new Matrix(m+1,N);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j < N; j++)
                w.set(i,j, KProjection(i,j).doubleValue());
        return w;
    }
    
    /** Array containing the K projection matrix */
    public BigRational[][] K() {
        BigRational[][] w = new BigRational[m+1][N];
        for(int i = 0; i <= m; i++)
            for(int j = 0; j < N; j++)
                w[i][j] = KProjection(i,j);
        return w;
    }
    
    /** 
     * Elements of the Vnm* generator matrix
     */
    public BigRational[][] VnmStarGenerator() {
        BigRational[][] K = K(); //load an array with the Kprojection matrix
        BigRational[][] Q = new BigRational[N][N]; //memory for output
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                Q[i][j] = VnmStarGenerator(i,j,K);
        return Q;
    }
    
    public Matrix VnmStarGeneratorDouble() {
        Matrix w = new Matrix(N,N);
        BigRational[][] mat = VnmStarGenerator();
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                w.set(i,j, mat[i][j].doubleValue());
        return w;
    }
    
    /** Given the K projection matrix, return elements of the generator */
    protected BigRational VnmStarGenerator(int i, int j, BigRational[][] K){
        BigRational sum = BigRational.ZERO;
        for(int k = 0; k <= m; k++){
            BigInteger v = new BigInteger(Integer.toString(i+1)).pow(k);
            sum = sum.add((new BigRational(v)).multiply(K[k][j]));
        }
        if(i==j) return BigRational.ONE.subtract(sum);
        else return sum.negate();
    }
    
}
