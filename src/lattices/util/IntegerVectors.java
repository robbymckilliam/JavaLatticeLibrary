package pubsim.lattices.util;

import Jama.Matrix;

/**
 * Return all integer vectors with elements in {0, 1, 2, ..., r-1}.
 * @author Robby McKilliam
 */
public class IntegerVectors
        extends AbstractPointEnumerator
        implements PointEnumerator{

    private final Matrix U;
    public final int N, r;
    public final long numsamples;
    protected long counter;
    int n = 0;
    private boolean finished = false;

    /**
     * Returns vectors of length N whose elements are
     * in {0, 1, 2, ..., r-1}.
     */
    public IntegerVectors(int N, int r){
        U = new Matrix(N, 1, 0.0);
        this.N = N;
        this.r = r;
        numsamples = (long)Math.pow(r, N);
        counter = 0;
    }

    @Override
    public double[] nextElementDouble() {
        return U.getColumnPackedCopy();
    }

    @Override
    public double percentageComplete() {
        return ((double)counter)/numsamples;
    }

    @Override
    public boolean hasMoreElements() {
        return !finished;
    }

    @Override
    public Matrix nextElement() {
        addto(0);
        counter++;
        if(counter >= numsamples) finished = true;
        return U;
    }

    /** This should be tail recursively optimisable (how does I assert that in java) */
    protected void addto(int i){
        if(U.get(i, 0) >= r - 1){
            U.set(i, 0, 0.0);
            if(i+1 < N)
                addto(i+1);
        }else{
            U.set(i, 0, U.get(i, 0) + 1.0);
        }
    }

}
