package pubsim.lattices.decoder;

import static pubsim.VectorFunctions.matrixMultVector;
import pubsim.lattices.LatticeInterface;

/**
 * This is a sphere decoder that searches the 'planes' in a greedy
 * order.  This tends to go a little faster.  See Agrell et. al.
 * 'CLOSEST POINT SEARCH IN LATTICES'
 * @author Robby McKilliam
 */
public class SphereDecoderSchnorrEuchner extends SphereDecoder{

    public SphereDecoderSchnorrEuchner(LatticeInterface L){
        super(L);
    }

    /** 
     * Computes the nearest point using Schnorr and Euchner's sphere decoder.
     * Default is to set starting distance to positive infinity.  This implicitly compute the Babai
     * point to start with.
     * @param y 
     */     
    @Override
    public void nearestPoint(double[] y) {
        nearestPoint(y, Double.POSITIVE_INFINITY);
    }
    
    //Allows you to set the initial starting distance for the sphere decoder
    public void nearestPoint(double[] y, double D) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");

        //don't need to compute the Babai point.  This strategy automattically computes
        //the Babai point.
        matrixMultVector(Qtrans, y, yr);
        this.D = D*D; //the decode function uses the square of the distance!
        
        //current element being decoded
        int k = n-1;

        decode(k, 0);

        //compute index u = Uuh so that Gu is nearest point
        matrixMultVector(U, ubest, u);

        //compute nearest point
        matrixMultVector(G, u, x);

    }

    @Override
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

        //this is the first point to test
        ut[k] = Math.round((yr[k] - rsum)/R.get(k,k));

        //this updates the ut[k] in the order of Schnorr and Euchner.
        double del = Math.signum( pubsim.Util.fracpart( (yr[k] - rsum)/R.get(k,k) )  );
        if(Math.abs(del) < 0.5) del = 1.0;
        
        while( Math.abs(ut[k]*R.get(k,k) + rsum - yr[k]) <= Math.sqrt(D - d) ){
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
            ut[k] += del;
            del = -Math.signum(del)*(Math.abs(del) + 1);
        }

    }

}
