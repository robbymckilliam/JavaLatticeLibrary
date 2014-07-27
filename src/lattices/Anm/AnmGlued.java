

package pubsim.lattices.Anm;

import pubsim.VectorFunctions;
import pubsim.lattices.An.An;
import pubsim.lattices.An.AnFastSelect;

/**
 * Simple Anm algorithm based on glue vector (cosets).
 * This algorithm was known by Conway and Sloane.
 * @author Robby McKilliam
 */
public class AnmGlued extends Anm {
    
    private final double[] g, yd; 
    private final An an;
    
     /** 
     * Sets protected variable g to the glue
     * vector [i].  See SPLAG pp109.
     */
    protected final void glueVector(int i){
        
        /*
        int j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(double)(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(double)(n+1);
        */ 
        
        //this is another set of glue vectors that can be
        //used.  These are in a line and are sometimes
        //more convenient than Conway and Sloane's.
        g[0] = i*(1.0 - 1.0/(n+1));
        for(int j = 1; j < n+1; j++)
            g[j] = -i*1.0/(n+1);
         
        
    }
    
    /** Constructor can set the m part of A_{n/m}. */
    public AnmGlued(int n, int m){
        super(n,m);
        g = new double[n+1];
        yd = new double[n+1];
        an = new AnFastSelect(n);
    }

    @Override
    public final void nearestPoint(double[] y) {
        if (n != y.length-1) throw new RuntimeException("y is the wrong length");
        
        double D = Double.POSITIVE_INFINITY;
        int besti = 0;
        int q = (n+1)/m;
        
        for(int i = 0; i < q; i++){
            glueVector(i*m);
            
            //System.out.println(VectorFunctions.print(g));
            
            for(int j = 0; j < n+1; j++)
                yd[j] = y[j] - g[j];
            
            an.nearestPoint(yd);
            
            double d = VectorFunctions.distance_between2(yd, an.getLatticePoint());

            if( d < D ){
                besti = i;
                D = d;
            }
            
        }
        
        glueVector(besti*m);
        for(int j = 0; j < n+1; j++)
            yd[j] = y[j] - g[j];
            
        an.nearestPoint(yd);
        
        for(int j = 0; j < n+1; j++)
            v[j] = an.getLatticePoint()[j] + g[j];
    }
    
    
}
