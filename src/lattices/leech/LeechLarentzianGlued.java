/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.leech;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.Zn;

/**
 * Decoder for the Leech lattice based on the Lorezentian construction.
 * This doesn't work.
 * @author Robby McKilliam
 */
public class LeechLarentzianGlued extends Leech implements LatticeAndNearestPointAlgorithmInterface {

    protected final double[] u = new double[25];
    protected final double[] v = new double[25];
    protected final double[] g = new double[25];
    protected final double[] glue = new double[25];
    protected final double gmag2;

    public LeechLarentzianGlued(){
        for(int i = 0; i < 23; i++) g[i] = 2*i + 3;
        g[23] = 51;
        g[24] = -145;
        gmag2 = VectorFunctions.sum2(g);
        for(int i = 0; i < 24; i++) glue[i] = -3*g[i]/gmag2;
        glue[0] += 1.0;

    }

    //working memory
    private final double[] yd = new double[25];
    private final Zn zn = new Zn(25);

    @Override
    public void nearestPoint(double[] y) {
        if (24 != y.length-1)
	    throw new ArrayIndexOutOfBoundsException("Input vector needs to " +
                    "be length 25 for Lorentzian version of Leech lattice " +
                    "decoder.");

        double D = Double.POSITIVE_INFINITY;
        int besti = 0;

        for(int i = 0; i < gmag2-1; i++){

            //System.out.println(VectorFunctions.print(g));

            for(int j = 0; j < 25; j++)
                yd[j] = y[j] - i*glue[j];

            zn.nearestPoint(yd);

            double d = VectorFunctions.distance_between2(yd, zn.getLatticePoint());

            if( d < D ){
                besti = i;
                D = d;
            }

        }

        for(int j = 0; j < 25; j++)
            yd[j] = y[j] - besti*glue[j];

        zn.nearestPoint(yd);

        for(int j = 0; j < 25; j++)
            v[j] = zn.getLatticePoint()[j] + besti*glue[j];

    }

    @Override
    public double[] getLatticePoint() {
        return v;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        Matrix I = Matrix.identity(25, 25);
        Matrix p = VectorFunctions.columnMatrix(g);
        Matrix P = p.times(p.transpose()).times(1.0/gmag2);
        return I.minus(P).getMatrix(0,24,0,23);
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private double[] yDoubletoy = new double[24];
    @Override
    public void nearestPoint(Double[] y) {
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }

}
