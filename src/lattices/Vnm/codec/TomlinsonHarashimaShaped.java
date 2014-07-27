package pubsim.lattices.Vnm.codec;

import pubsim.lattices.Vnm.Vnm;
import pubsim.lattices.Vnm.codec.generators.UpperTriangularGenerator;

/**
 * Basic Tomlinson Harashima shaped encoder.  This has very poor shaping if the dimension is not
 * large enough.  Decoder is a standard trellis that incorporates the shaping.
 * @author Robby McKilliam
 */
public class TomlinsonHarashimaShaped implements VnmCodec {
    
    final double[] x;
    final int[] u, k;
    final int n,m,M;
    final UpperTriangularGenerator R;
    
    /** 
     * @param n code length/lattice dimension
     * @param m Vnm lattice parameter/filter order
     * @param M size of symbol alphabet, i.e. M=2 would mean a rate 1 code.
     */
    public TomlinsonHarashimaShaped(int n, int m, int M){
        this.n = n;
        this.m = m;
        this.M = M;
        x = new double[n];
        u = new int[n];
        k = new int[n];
        R = new UpperTriangularGenerator(n, m); //get a generator for our lattice
    }

    /**
     * Encodes and shapes the transmitted symbols u.  Using standard Tomlinson Harashima
     * encoding.
     */
    @Override
    public double[] encode(int[] u) {
        for(int i = 0; i < n; i++) k[i] = 0; //clear working memory
        for( int i = n-1; i >= 0; i-- ){
            double sum = 0.0;
            for( int j = Math.min(n-1,i+m+1); j>=i; j-- ){
                sum += R.get(i,j) * (u[j] + k[j]);
            }
            k[i] = (int)Math.round(-sum/M/R.get(i,i));
            x[i] = sum + k[i]*M*R.get(i,i);
        }
        return x;
    }

    @Override
    public int[] decode(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
        //return u;
    }
    
     /** 
     * Return the shaping loss with respect to the hypercube for this code.  Value returned in dB.
     */
    @Override
    public double shapingLoss(){
        if(n==1) return 0.0;

        double scale = Math.pow(2.0, new Vnm(n, m).logVolume()/n);
        double secmom = 0.0;
        for(int i = 0; i < n; i++){
            double d = Math.abs(R.get(i,i))/scale;
            secmom +=d*d*d/12.0;
        }
        secmom /= n;
        //System.out.println(secmom + ", " + scale);
        double hypercubesecmom = 1.0/12.0;
        return 10.0 * Math.log10(secmom/hypercubesecmom);
    }
    
    
    
}
