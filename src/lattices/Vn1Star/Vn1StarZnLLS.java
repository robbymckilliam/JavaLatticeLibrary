package pubsim.lattices.Vn1Star;

import pubsim.lattices.Anstar.Anstar;
import java.util.Map.Entry;
import java.util.TreeMap;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.NearestPointAlgorithmInterface;

/**
 * O(N^3log(N)) version of the nearest point algorithm for Vnm*.
 * @author Robby McKilliam
 */
public class Vn1StarZnLLS extends
        Vn1Star implements NearestPointAlgorithmInterface {

    final double[] g, glue, z, x;
    final Anstar anstar;

    public Vn1StarZnLLS(int n){
        super(n);
        glue = new double[n+2];
        g = Vn1Star.getgVector(n+2);
        z = new double[n+2];
        x = new double[n+2];

        anstar = new AnstarLinear(n+1);
    }

    public void nearestPoint(double[] y) {
        if (n != y.length-2) throw new ArrayIndexOutOfBoundsException("y is the wrong length");

        Anstar.project(y, z);
        double D = Double.POSITIVE_INFINITY;
        double fhat = 0.0;

        //for each of the glue vectors
        for(int i = 0; i < n+2; i++){
            Anstar.glue(i, glue);

            //tree map acts as the sorted list
            TreeMap<Double, Integer> K = new TreeMap();

            //these are the values alpha, beta, f and gamma
            double a = 0.0, b = 0.0, f = 0.0, gam = 0.0;
            for(int t = 0; t < n+2; t++){
                x[t] = Math.round(z[t] - glue[t]) + glue[t];
                if(g[t] != 0){
                    double fs = (z[t] - x[t] + 0.5*Math.signum(g[t]))/g[t];
                    K.put(new Double(fs), new Integer(t));
                }
                double zmx = z[t] - x[t];
                a += -g[t]*zmx;
                b += zmx*zmx;
                gam += g[t]*g[t];
                f += g[t]*zmx;
            }
            f/=gam;

            //while loop traverses the bresenham set
            while( f < 1 ){
                double h = b + 2*f*a + f*f*gam;
                if(h < D){
                    D = h;
                    fhat = f;
                }
                Entry<Double, Integer> e = K.firstEntry();
                K.remove(e.getKey());
                int t = e.getValue().intValue();

                a -= Math.abs(g[t]);
                b += 2*Math.signum(g[t])*(z[t] - x[t]) + 1;
                f += Math.abs(g[t])/gam;

                x[t] -= Math.signum(g[t]);
                double fs = (z[t] - x[t] + 0.5*Math.signum(g[t]))/g[t];
                K.put(new Double(fs), new Integer(t));
            }
        }

        //compute the nearest point in An* to get the index
        //of the nearest lattice point.
        for(int t = 0; t < n+2; t++)
            x[t]  = z[t] - fhat*g[t];
        anstar.nearestPoint(x);

        project(anstar.getIndex(), v);
        u = anstar.getIndex();
        
    }


}
