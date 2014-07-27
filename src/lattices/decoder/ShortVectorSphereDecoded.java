/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import pubsim.VectorFunctions;
import pubsim.lattices.LatticeInterface;

/**
 *
 * @author Robby McKilliam
 */
public class ShortVectorSphereDecoded implements ShortVector{

    protected final ModSphereDecoder ssd;

    public ShortVectorSphereDecoded(LatticeInterface L){
        ssd = new ModSphereDecoder(L);
        ssd.findShortVector();

    }

    /** Finds shortest vector in a lattice Uses d as starting
     * distance for sphere decoder.  d needs to be bigger than
     * smallest distance
     */
    public ShortVectorSphereDecoded(LatticeInterface L, double d){
        ssd = new ModSphereDecoder(L);
        ssd.findShortVector(d);
    }

    @Override
    public double[] getShortestVector(){
        return ssd.getLatticePoint();
    }

    @Override
    public double[] getShortestIndex(){
        return ssd.getIndex();
    }

    protected class ModSphereDecoder extends SphereDecoder{

        public ModSphereDecoder(LatticeInterface L){
            super(L);
        }


        public void findShortVector(){
            //compute the radius squared of the sphere we are decoding in.
            //Add DELTA to avoid numerical error causing the
            //Babai point to be rejected.
            D = VectorFunctions.minColumnNorn(R) + DELTA;

            nearestPoint(new double[m]);
        }

        public void findShortVector(double d){

            //compute the radius squared of the sphere we are decoding in.
            //Add DELTA to avoid numerical error causing the
            //Babai point to be rejected.
            D = d + DELTA;

            nearestPoint(new double[m]);

        }

        @Override
        public void nearestPoint(double[] y) {

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
                //otherwise check if this is the best point so far encounted
                //and update if required.  This is modified so that it doesn't
                else{
                    if(sumd <= D && sumd > DELTA){
                        System.arraycopy(ut, 0, ubest, 0, n);
                        D = sumd;
                        //System.out.println( "found norm = " + D );
                    }
                }
                ut[k]++;
            }

        }


    }


}
