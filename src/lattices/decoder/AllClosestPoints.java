package pubsim.lattices.decoder;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Set;
import pubsim.VectorFunctions;
import static pubsim.VectorFunctions.matrixMultVector;
import static pubsim.VectorFunctions.columnMatrix;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.LatticeInterface;

/**
 * Finds all lattices points closest to a given point.  If the solution is unique, this returns
 * a single point, the same as the sphere decoder.
 * @author Robby McKilliam
 */
public class AllClosestPoints {
    
    protected final ModSphereDecoder sd;
    
    protected final LatticeAndNearestPointAlgorithmInterface L;
    
    public AllClosestPoints(LatticeInterface L) {
        this(new LatticeAndNearestPointAlgorithm(L.getGeneratorMatrix()));
    } 
    
    public AllClosestPoints(LatticeAndNearestPointAlgorithmInterface L) {
        this.L = L;
        sd = new ModSphereDecoder(L);
    }
    
    public Set<Matrix> closestPoints(double[] y) {
        L.nearestPoint(y);
        double[] x = L.getLatticePoint();
        double d = VectorFunctions.distance_between(x, y); //distance to the closest point
        return sd.findVectorsCloserThan(d, y);
    }
    
    protected static class ModSphereDecoder extends SphereDecoder{
        
        protected Set<Matrix> closestPoints;

        public ModSphereDecoder(LatticeInterface L){
            super(L);
        }

        public Set<Matrix> findVectorsCloserThan(double d, double[] y){
            closestPoints = new HashSet<>();
            
            //iinitialise yr variable for decode function
            matrixMultVector(Qtrans, y, yr);
            
            //Add DELTA to avoid numerical error causing the
            D = d*d + DELTA;

            //current element being decoded
            int k = n-1;

            decode(k, 0);
            
            return closestPoints;          
        }

        /**
         * Recursive decode function to test nearest plane
         * for a particular dimension/element
         */
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

            //set least possible ut[k]
            ut[k] = Math.ceil((-Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k));

            while(ut[k] <= (Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k) ){
                double kd = R.get(k, k)*ut[k] + rsum - yr[k];
                double sumd = d + kd*kd;

                //if this is not the first element then recurse
                if( k > 0)
                    decode(k-1, sumd);
                else{
                    if(sumd <= D){
                        if(sumd < D - 2*DELTA) throw new RuntimeException("You have found a vector closest than the closest point. This should not happen!");
                        Matrix v = G.times(U).times(columnMatrix(ut)); //this is a closest point
                        closestPoints.add(v);
                    }
                }
                ut[k]++;
            }

        }


    }
    
}
