package pubsim.lattices.util;

import Jama.Matrix;
import java.util.concurrent.LinkedBlockingQueue;
import pubsim.lattices.LatticeInterface;
import pubsim.VectorFunctions;

/**
 *  Returns all the points of a lattice in a sphere of given
 * radius.  The sphere does not have to be centered at
 * the origin.
 * This uses a producer/consumer threaded pattern
 * but perhaps it could be better implemented.
 * @author Robby McKilliam
 */
public class PointInSphere 
        extends AbstractPointEnumerator
        implements PointEnumerator{

    protected Matrix M;
    protected Matrix r, uret;

    Matrix R, Q;
    double[] y, u;
    double D;
    int n, m;
    
    DecodeThread dthread;

    //keeps the compiler happy.  Silly compiler.
    protected PointInSphere(){
        
    }

    /**
     * @param lattice
     * @param radius The radius of the sphere
     * @param center The position of the center of the sphere
     */
    public PointInSphere(LatticeInterface lattice, double radius, double[] center){
        init(lattice, radius, center);
    }

    /**
     * Assumes the center of the sphere is the origin.
     * @param lattice
     * @param radius The radius of the sphere
     */
    public PointInSphere(LatticeInterface lattice, double radius){
        double[] center = new double[lattice.getGeneratorMatrix().getRowDimension()];
        init(lattice, radius, center);
    }

    protected void init(LatticeInterface lattice, double radius, double[] center){
        M = lattice.getGeneratorMatrix();
        m = M.getRowDimension();
        n = M.getColumnDimension();
        if(m != center.length)
            throw new ArrayIndexOutOfBoundsException("center is not the correct dimension");

        //System.out.println(VectorFunctions.print(center));

        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(M);
        R = QR.getR();
        Q = QR.getQ();
        D = radius*radius;
        //compute the center in the triangular frame
        y = VectorFunctions.matrixMultVector(Q.transpose(), center);
        u = new double[n];

        //System.out.println("M = \n" + VectorFunctions.print(M));
        //System.out.println("y = " + VectorFunctions.print(center));
        //System.out.println("d = " + radius);
        ///System.out.println("R = " + VectorFunctions.print(R));
        //System.out.println("Q = " + VectorFunctions.print(Q));

        //start thread to compute points.
        dthread = new DecodeThread();
        //dthread.setPriority(Thread.MAX_PRIORITY);
        dthread.start();

    }

    protected LinkedBlockingQueue<Matrix> queue = new
            LinkedBlockingQueue<Matrix>(1000);

    public Matrix nextElement() {
        //while the queue is empty, yield the main thread
        while(queue.isEmpty())
            Thread.yield();
        try{
            uret = queue.take();
            r = M.times(uret);
        }catch(InterruptedException e){
            throw new RuntimeException("Taking from linked queue interrupted");
        }
        return r;
    }

    /**
     * Returns the index of the previously returned lattice point.
     * This does not iterate through the points (does not remove
     * from the enumeration).
     * @return index
     */
    public Matrix getElementIndex(){
        return uret;
    }

    /**
     * Returns the index of the previously returned lattice point.
     * This does not iterate through the points (does not remove
     * from the enumeration).
     * @return index
     */
    public double[] getElementIndexDouble(){
        return uret.getColumnPackedCopy();
    }

    /**
     * Thread to iteratively compute points inside the sphere.
     */
    protected class DecodeThread extends Thread{

        @Override
        public void run(){
            decode(n-1, 0);
        }

        /**
         * Recursive decode function to test nearest plane
         * for a particular dimension/element
         */
        protected void decode(int k, double d){
            //return if this is already not in the sphere
            if(d > D){
                return;
            }

            //compute the sum of R[k][k+i]*uh[k+i]'s
            //and the distance so far
            double rsum = 0.0;
            for(int i = k+1; i < n; i++ ){
                rsum += u[i]*R.get(k, i);
            }

            //set least possible ut[k]
            u[k] = Math.ceil((-Math.sqrt(D - d) + y[k] - rsum)/R.get(k,k));

             //System.out.println("u = " + VectorFunctions.print(u));
             //System.out.println("k = " + k);
             //System.out.println("D = " + D);
             //System.out.println("rsum = " + rsum);

            while(u[k] <= (Math.sqrt(D - d) + y[k] - rsum)/R.get(k,k) ){
                double kd = R.get(k, k)*u[k] + rsum - y[k];
                double sumd = d + kd*kd;

                //if this is not the first element then recurse
                if( k > 0)
                    decode(k-1, sumd);
                //otherwise check if this is the best point so far encounted
                //and update if required
                else{
                    if(sumd <= D){
                        //System.out.println("*** u = " + VectorFunctions.print
                        queue.add(VectorFunctions.columnMatrix(u));
                        //while the queue is a bit big, yield this thread
                        while(queue.size() >= 990)
                            yield();
                    }
                }
                u[k]++;
            }
        }
    }

    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasMoreElements() {
        //System.out.println(dthread.isAlive() + ", " + !queue.isEmpty());
        return dthread.isAlive() || !queue.isEmpty();
    }

    public double[] nextElementDouble() {
        return nextElement().getColumnPackedCopy();
    }

}
