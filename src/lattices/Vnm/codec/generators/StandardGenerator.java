package pubsim.lattices.Vnm.codec.generators;

import pubsim.lattices.Vnm.Vnm;

/**
 * The standard generator for Vnm as an n+m+1 by n Toeplits matrix stored in a memory efficient
 * format.
 * @author Robby McKilliam
 */
public class StandardGenerator implements BandedGenerator {
    
    protected final double[] gcol;
    protected final int n, m;
    
    public StandardGenerator(int n, int m){
        gcol = Vnm.getGeneratorColumn(n, m);
        this.n = n;
        this.m = m;
    }
    
    @Override
    public double get(int i, int j){
        if( i  - j >= 0 && i - j <= m+1 ) return gcol[i-j];
        else return 0.0;
    }
    
    @Override
    public final String toString() {
        String st = new String();
        for (int i = 0; i < n+m+1; i++) {
            for (int j = 0; j < n; j++) {
                st = st.concat("\t" + get(i,j));
            }
            st = st.concat("\n");
        }
        return st;
    }
    
}
