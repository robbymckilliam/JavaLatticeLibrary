package pubsim.lattices.util.region;

import Jama.Matrix;
import static pubsim.VectorFunctions.matrixMultVector;
import static pubsim.VectorFunctions.subtract;

/**
 *
 * @author Robby McKilliam
 */
public class Parallelepiped implements Region, BoundingBox {

    protected Matrix M;
    protected Matrix invM;
    protected double[] t;

    /**
     * Parallelepiped defined by the column vectors in matrix M.
     * i.e. Mb where b \in [0, 1].
     * The matrix may be m x n where m >= n.
     * If m > n then the region is an infinite cylinder with
     * the parallelepiped as a cross-section.
     *
     * This Parallelepiped has one vertex at the origin.
     */
    public Parallelepiped(Matrix M){
        init(M, new double[M.getRowDimension()]);
    }

    /**
     * Parallelepiped defined by the column vectors in matrix M
     * and a translation.
     * i.e. Mb + t where b \in [0, 1].
     * The matrix may be m x n where m >= n.
     * If m > n then the region is an infinite cylinder with
     * the parallelepiped as a cross-section.
     *
     */
    public Parallelepiped(Matrix M, double[] t){
        init(M, t);
    }

    private double[] diff, p;
    private double[] maxbounds, minbounds;
    private void init(Matrix M, double[] t){
        if(M.getRowDimension() != t.length)
            throw new ArrayIndexOutOfBoundsException("t.length must be equal to the row dimension");
        this.M = M;
        invM = M.inverse();
        this.t = t;
        diff = new double[M.getRowDimension()];
        p = new double[M.getColumnDimension()];
        computeBoundingBox();
    }

    /** 
     * Compute the min and max values for each
     * coordinate in the parallelepiped.
     */
    private void computeBoundingBox() {
        maxbounds = new double[M.getRowDimension()];
        minbounds = new double[M.getRowDimension()];
        for(int m = 0; m < M.getRowDimension(); m++){
            maxbounds[m] = t[m];
            minbounds[m] = t[m];
            for(int n = 0; n < M.getColumnDimension(); n++){
                double val = M.get(m, n);
                if(val > 0)
                    maxbounds[m] += val;
                else
                    minbounds[m] +=val;
            }
        }
    }

    public boolean within(double[] y) {
        if(y.length != rowDimension())
            throw new ArrayIndexOutOfBoundsException("y.length must be equal to the row dimension");

        double[][] mat = M.getArray();

        subtract(y, t, diff);

        //System.out.print(print())

        matrixMultVector(invM, diff, p);
        for(int n = 0; n < dimension(); n++){
            if(Math.floor(p[n]) != 0.0) return false;
        }
        return true;
    }

    public int dimension() {
        return M.getColumnDimension();
    }

    /**
     * Number of rows in the matrix.
     * This is greater than dimension if the matrix
     * is rectangular.
     */
    public int rowDimension() {
        return M.getRowDimension();
    }

    /** Same as dimension. */
    public int columnDimension() {
        return dimension();
    }

    public double minInCoordinate(int n) {
        return minbounds[n];
    }

    public double maxInCoordinate(int n) {
        return maxbounds[n];
    }

}
