package pubsim.lattices.Anstar;

/**
 * Vaughan's original An* O(nlogn) nearest point algorithm.
 * Modified by Robby McKilliam 5/3/2012 
 * @author Vaughan Clarkson, 15-Jun-05.
 */
public class AnstarVaughan extends Anstar {

    //protected int m;
    final protected int[] s, tmps;
    final protected Double[] delta;
    final private double[] x;

    public AnstarVaughan(int n) {
        super(n);
        x = new double[n + 1];
        s = new int[n + 1];
        tmps = new int[n + 1];
        delta = new Double[n + 1];
    }

    @Override
    public final void nearestPoint(double[] y) {
        if (n != y.length - 1) {
            throw new ArrayIndexOutOfBoundsException("y is the wrong length");
        }

        project(y, x);

        // Find the closest point

        // Algorithm 1

        for (int i = 0; i <= n; i++) {
            u[i] = Math.round(x[i]);
        }
        project(u, v);
        for (int i = 0; i <= n; i++) {
            delta[i] = new Double(x[i] - v[i]);
        }
        Sorter.sortIndices(delta, s);
        /*
         * // DEBUG CHECK for (int i = 0; i < n; i++) if
         * (Math.abs(delta[s[i]].doubleValue() - delta[s[i+1]].doubleValue()) >
         * 1) { System.err.println("*** ERROR: not alpha close"); break; } //
         * END DEBUG CHECK
         */

        // Algorithm 2

        int m = 0;
        while (delta[s[m]].doubleValue() < (double) m / (n + 1) - 0.5) {
            u[s[m]]--;
            m++;
        }
        m = n;
        while (delta[s[m]].doubleValue() > (double) (m + 1) / (n + 1) - 0.5) {
            u[s[m]]++;
            m--;
        }
        project(u, v);
        for (int i = 0; i <= n; i++) {
            delta[i] = new Double(x[i] - v[i]);
        }
        Sorter.sortIndices(delta, s);
        /*
         * // DEBUG CHECK for (int i = 0; i <= n; i++) if
         * (Math.abs(delta[i].doubleValue()) > 0.5) { System.err.println("***
         * ERROR: not beta close"); break; } // END DEBUG CHECK
         */

        // Algorithm 3

        m = n;
        double t = 0, tau = 0;
        for (int i = 0; i < n; i++) {
            t += delta[s[i]].doubleValue()
                    - (double) (2 * i - n) / (2 * n + 2);
            if (t < tau) {
                tau = t;
                m = i;
            }
        }
        rotate(m);

    }

    protected void rotate(int r) {
        // Update u and v

        for (int i = r + 1; i <= n; i++) {
            u[s[i]]++;
        }
        project(u, v);

        // Rotate s circularly to the left by r+1 positions

        for (int i = 0; i <= n; i++) {
            tmps[i] = s[i];
        }
        for (int i = 0; i <= n; i++) {
            s[i] = tmps[(i + r + 1) % (n + 1)];
        }
    }

    /**
     * Project a vector into the zero-mean plane y is output, x is input (x & y
     * can be the same array) <p> Pre: y.length >= x.length
     */
    public static void project(double[] x, double[] y) {
        double xbar = 0.0;
        for (int i = 0; i < x.length; i++) {
            xbar += x[i];
        }
        xbar /= x.length;
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] - xbar;
        }
    }

    /**
     * Getter for the nearest point.
     */
    @Override
    public double[] getLatticePoint() {
        return v;
    }

    /**
     * Getter for the integer vector.
     */
    @Override
    public double[] getIndex() {
        return u;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double volume() {
        return Math.sqrt(1 / (n + 1));
    }
}
