package pubsim.lattices.util;

import Jama.Matrix;
import pubsim.lattices.LatticeInterface;

/**
 * Return a set of point uniformly (kind of) distributed in the
 * fundamental parallelepiped of the lattice given by Bu where B is the
 * generator matrix and u in [0,1]^N.
 * @author Robby McKilliam
 */
public class PointInParallelepiped
        extends AbstractPointEnumerator
        implements PointEnumerator{
    
    protected Matrix u;
    protected boolean finished = false;
    protected int N;
    protected Matrix B;
    protected int counter = 0;

    private int[] samples;
    private int sampleproduct;

    protected PointInParallelepiped(){}

    /**
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public PointInParallelepiped(LatticeInterface L, int samples){
        init(L.getGeneratorMatrix(), samples);
    }

    /**
     * @param B the generator matrix for the lattice
     * @param samples is the number of samples used per dimension
     */
    public PointInParallelepiped(Matrix B, int samples){
        init(B, samples);
    }

        /**
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public PointInParallelepiped(LatticeInterface L, int[] samples){
        init(L.getGeneratorMatrix(), samples);
    }

    /**
     * @param B the generator matrix for the lattice
     * @param samples is the number of samples used per dimension
     */
    public PointInParallelepiped(Matrix B, int[] samples){
        init(B, samples);
    }

    private void init(Matrix B, int samples){
        this.B = B;
        N = B.getColumnDimension();
        this.samples = new int[N];
        u = new Matrix(N, 1);
        sampleproduct = 1;
        for(int i = 0; i < N; i++){
            this.samples[i]  = samples;
            sampleproduct *= samples;
        }
    }

    private void init(Matrix B, int[] samples){
        this.B = B;
        //System.out.println(VectorFunctions.print(samples));
        N = B.getColumnDimension();
        this.samples = new int[N];
        u = new Matrix(N, 1);
        sampleproduct = 1;
        for(int i = 0; i < N; i++){
            this.samples[i]  = samples[i];
            sampleproduct *= this.samples[i];
        }
    }

    @Override
    public boolean hasMoreElements() {
        return !finished;
    }

    @Override
    public Matrix nextElement() {
        if(B.getRowDimension() == 0) {
            finished = true;
            return null;
        }
        addto(0);
        counter++;
        if(counter >= sampleproduct) finished = true;
        return B.times(u);
    }
    
    /**
     * Return value between 0 and 100 that indicates
     * the percentage of elements that have been returned.
     * This is just useful for feedback when the number of
     * element is very large.
     * @return a % in [0, 100]
     */
    public double percentageComplete(){
        return (100.0*counter)/sampleproduct;
    }

    protected void addto(int i){
        if(u.get(i, 0) >= 1.0 - 1.0/samples[i]){
            u.set(i, 0, 0.0);
            if(i+1 < N)
                addto(i+1);
        }else{
            u.set(i, 0, u.get(i, 0) + 1.0/samples[i]);
        }
    }

    public double[] nextElementDouble() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
