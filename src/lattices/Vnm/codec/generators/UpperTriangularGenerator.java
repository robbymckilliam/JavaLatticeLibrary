package pubsim.lattices.Vnm.codec.generators;

/**
 * Efficient representation of the upper triangular generator for the lattice Vnm.  This is the R part 
 * of the QR decomposition of the standard generator.
 * @author Robby McKilliam
 */
public class UpperTriangularGenerator implements BandedGenerator {
    
    /** store the whole matrix in one vector */
    protected final double[] r;
    
    protected final int n, m, N, band;
    
    public UpperTriangularGenerator(int n, int m){
        this.n = n;
        this.m = m;
        band = 2*(m+2)-1;
        N = n+m+1;
        r = new double[band*N]; //slightly more memory than needed, I'm being lazy
        
        //copy the standard generator to this memory.
        StandardGenerator g = new StandardGenerator(n, m);
        for(int j = 0; j < n; j++) for(int i = j; i < j+m+2; i++) set(i, j, g.get(i,j));
        
        //now apply Given's rotations to bring the matrix into upper triangular form.
        for(int k = m; k >= 0; k--) for( int i = 0; i < n; i++) givensRotate(k+i, k+i+1, i);
        
    }
    
    final protected void set(int i, int j, double v){
        if(i >= N || i < 0 || j >= n || j < 0) throw new ArrayIndexOutOfBoundsException();
        int jj = j+m+1;
        if(jj - i >= 0 && jj - i < band) r[band*i + jj-i] = v;
    }
    
    /** 
     * Protected get function.
     * Has access to a bit more memory in r that is useful  for internal setup,
     * but should not be accessible externally by get.
     */
    final protected double pget(int i, int j){
        if(i >= N || i < 0 || j >= n || j < 0) throw new ArrayIndexOutOfBoundsException();
        int jj = j+m+1;
        if(jj - i >= 0 && jj - i < band) return r[band*i + jj-i];
        else return 0.0;
    }

    @Override
    public double get(int i, int j) {
        if(i >= n || i < 0 || j >= n || j < 0) throw new ArrayIndexOutOfBoundsException();
        int jj = j+m+1;
        if(jj - i >= 0 && jj - i < band) return r[band*i + jj-i];
        else return 0.0;
    }
    
     /**
     * Apply a Given's rotation to internal matrix.   
     * The rotation will make M[i2,j] = 0.  It affects the rows i1 and i2.
     */
    final protected void givensRotate(int i1, int i2, int j){
        if(j >= n || i2 > N)
            throw new RuntimeException("Given's rotation parameters outside matrix size.");
        if(i1 >= i2)
            throw new RuntimeException("This Given's implementation requires i1 < i2");
        
        double a = pget(i1, j);
        double b = pget(i2, j);
        double d = 1.0 / Math.sqrt(a*a + b*b);
        double c = a*d;
        double s = b*d;
        
        for(int k = Math.max(0,i1 - band); k < Math.min(n, i2 + band); k++){
            double v1 = pget(i1, k);
            double v2 = pget(i2, k);
            set(i1, k, c*v1 + s*v2);
            set(i2, k, -s*v1 + c*v2);
        }
    }
    
    @Override
    public final String toString() {
        String st = new String();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                st = st.concat("\t" + get(i,j));
            }
            st = st.concat("\n");
        }
        return st;
    }
    
}
