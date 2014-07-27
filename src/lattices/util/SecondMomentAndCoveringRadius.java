package pubsim.lattices.util;

import pubsim.VectorFunctions;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 *
 * @author Robby McKilliam
 */
public class SecondMomentAndCoveringRadius extends PropertyCalculator{

    protected final double vol;
    protected final int N;
    protected double sm = 0.0, outradius = 0.0;

    public SecondMomentAndCoveringRadius(LatticeAndNearestPointAlgorithmInterface L){
        super(L);
        vol = L.volume();
        N = L.getDimension();
    }

    protected void calculateProperty(double[] p){
        //System.out.println(VectorFunctions.print(p));
        double mag2 = VectorFunctions.sum2(p);
        if(mag2 > outradius) outradius = mag2;
        sm += mag2;
    }

    public double outRadius() {return Math.sqrt(outradius);}

    public double coveringRadius() {return outRadius();}

    /**
     * Carefull here, the normalised second moment does not need to
     * be divided by the volume due to way that the points are generated
     * (i.e. using the fundamental parralelipided).  If you write the integral
     * down it becomes clear that the change of variables occurs with a
     * Jacobian that is equal to the volume, hence cancellation.
     *
     * Really, I think that Conway and Sloane got this a little wrong.  The
     * normalised second moment should be I = U/vol^(2/n + 1) then it is scale
     * independent.  The dimensionless second moment is then I/n
     */
     public double normalisedSecondMoment() {
        return sm/numsamples/Math.pow(vol, 2.0/N);
    }

    public double secondMoment() {return sm/numsamples;}

    public double dimensionalessSecondMoment() {
        return normalisedSecondMoment()/N;
    }


    /**
     * Return the computed error, this is not normalised by the number
     * of iterations actually run.
     */
    public double rawError() { return sm; }

}
