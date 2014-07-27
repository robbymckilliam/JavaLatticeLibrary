package pubsim.lattices.Vnm.codec;

import pubsim.lattices.Vnm.Vnm;
import pubsim.lattices.Vnm.codec.generators.UpperTriangularGenerator;

/**
 *
 * @author Robby McKilliam
 */
public class ModifiedTomlinsonHarashima implements VnmCodec{
    
    protected final int n, m, M, K;
    protected final UpperTriangularGenerator R;
    
    public ModifiedTomlinsonHarashima(int n, int m, int M, int K){
        if( K >= n ) throw new RuntimeException("n must be greater than k, otherwise this code has rate zero!");
        this.n= n;
        this.m = m;
        this.M= M;
        this.K= K;
        R = new UpperTriangularGenerator(n, m); 
    }
    
     /** 
     * Return the shaping loss with respect to the hypercube for this code.  Value returned in dB.
     * Modified to model the situation where rate can be sacrificed for shaping gain.  This works under the 
     * assumption that the baseband rate is 1.
     */
    @Override
    public double shapingLoss(){
        if(n==1) return 0.0;

        double scale = Math.pow(2.0, new Vnm(n, m).logVolume()/n);
        double secmom = 0.0;
        for(int i = 0; i < K; i++){
            double d = Math.abs(R.get(i,i))/scale/M;
            secmom +=d*d*d/12.0;
        }
        for(int i = K; i < n; i++){
            double d = Math.abs(R.get(i,i))/scale;
            secmom +=d*d*d/12.0;
        }
        secmom /= n;
        //System.out.println(secmom + ", " + scale);
        double hypercubesecmom = 1.0/12.0;
        return 10.0 * Math.log10(secmom/hypercubesecmom);
    }

    @Override
    public double[] encode(int[] u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int[] decode(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
