package pubsim.lattices.Anstar;

/**
 * This is Vaughan's practical interpretation of the Anstar bucket
 * algorithm.  This uses 2 arrays rather than a linked list.  It is
 * simpler and a little faster in practice.
 * @author Robby McKilliam
 */
public class AnstarLinear extends Anstar {
    
    final private int[] bucket;
    final private int[] link;
    final private double[] z;

    public AnstarLinear(int n){
        super(n);
        z = new double[n + 1];
        link = new int[n + 1];
        bucket = new int[n+1];
        for(int i = 0; i < n + 1; i++){
            bucket[i] = -1;
            link[i] = 0;
        }
    }

    @Override
    public final void nearestPoint(double[] y) {
        if (n != y.length-1) throw new ArrayIndexOutOfBoundsException("y is the wrong length");
        
        //make sure that the buckets are empty!
        for(int i = 0; i < n + 1; i++)
            bucket[i] = -1;
        
        double a = 0, b = 0;
        for(int t = 0; t < n + 1; t++){
            z[t] = y[t] - Math.round(y[t]);
            int i = n - (int)(Math.floor((n+1)*(z[t]+0.5)));
            link[t] = bucket[i];
            bucket[i] = t;
            a += z[t];
            b += z[t] * z[t];
        }
        
        double D = b - a*a/(n+1);
        int m = 0;
        for(int i = 0; i < n; i++){
            int t = bucket[i];
            while(t != -1){
                a -= 1;
                b += -2*z[t] + 1;
                t = link[t];
            }
            double dist = b - a*a/(n+1);
            if(dist < D){
                D = dist;
                m = i+1;
            }
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++){
            int t = bucket[i];
            while(t != -1){
                u[t] += 1;
                t = link[t];
            }
        }
        
        project(u, v);
        
    }

}