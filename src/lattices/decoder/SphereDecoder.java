package pubsim.lattices.decoder;

import pubsim.VectorFunctions;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.NearestPointAlgorithmInterface;

/**
 * Sphere decoder that uses the Babai point
 * as the initial point
 * @author Robby McKilliam
 */
public class SphereDecoder extends Babai
        implements NearestPointAlgorithmInterface {

    /** Current sphere radius squared */
    protected double D = Double.POSITIVE_INFINITY;
    
    //temporary variable for ut
    protected double[] ut, ubest;
    
    protected double[] xr;
    
    //small number to avoid numerical errors in branches.
    protected double DELTA = 1e-8;

    public SphereDecoder(LatticeInterface L){
        super(L);
        ut = new double[n];
        ubest = new double[n];
        xr = new double[n];

        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();
        Qtrans = Q.transpose();
    }
       
    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");

        //this will store the nearest point in the variable x
        computeBabaiPoint(y);
        
        //compute the radius squared of the sphere we are decoding in.
        //Add DELTA to avoid numerical error causing the
        //Babai point to be rejected.
        D = VectorFunctions.distance_between2(y, x) + DELTA;

        //System.out.println(" sd D = " + D);
        
        //current element being decoded
        int k = n-1;
        
        decode(k, 0);
        
        //compute index u = Uuh so that Gu is nearest point
        VectorFunctions.matrixMultVector(U, ubest, u);
        
        //compute nearest point
        VectorFunctions.matrixMultVector(G, u, x);
        
    }
    
    /** 
     * Recursive decode function to test nearest plane
     * for a particular dimension/element
     */
    protected void decode(int k, double d){
        //return if this is already not the closest point 
        if(d > D){
            return;
        }
        
        //compute the sum of R[k][k+i]*uh[k+i]'s
        //and the distance so far
        double rsum = 0.0;
        for(int i = k+1; i < n; i++ ){
            rsum += ut[i]*R.get(k, i);
        }
        
        //set least possible ut[k]
        ut[k] = Math.ceil((-Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k));
        
        while(ut[k] <= (Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k) ){
            double kd = R.get(k, k)*ut[k] + rsum - yr[k];
            double sumd = d + kd*kd;
            
            //if this is not the first element then recurse
            if( k > 0)
                decode(k-1, sumd);
            //otherwise check if this is the best point so far encounted
            //and update if required
            else{
                if(sumd <= D){
                    System.arraycopy(ut, 0, ubest, 0, n);
                    D = sumd;
                }
            }
            ut[k]++;
        }
        
    }


    @Override
    public double distance() {
        return D;
    }
    
}
