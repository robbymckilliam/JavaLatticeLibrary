package pubsim.lattices.util;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public abstract class AbstractPointEnumerator implements PointEnumerator{

    @Override
    public double[] nextElementDouble() {
        return nextElement().getColumnPackedCopy();
    }

    @Override
    public abstract double percentageComplete();

    @Override
    public abstract boolean hasMoreElements();

    @Override
    public abstract Matrix nextElement();

    @Override
    public java.util.Iterator<Matrix> iterator() {
        return new Iterator();
    }

    protected class Iterator implements java.util.Iterator<Matrix>{

        @Override
        public boolean hasNext() {
            return hasMoreElements();
        }

        @Override
        public Matrix next() {
            return nextElement();
        }

        @Override
        public void remove() {
        }

    }

}