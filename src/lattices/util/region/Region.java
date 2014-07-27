package pubsim.lattices.util.region;

/**
 * Represents an arbitrary region in N dimensional space
 * @author Robby McKilliam
 */
public interface Region {

    /** Returns true if the point y is in the region, else false */
    public boolean within(double[] y);

    /** Returns the dimension of the region. */
    public int dimension();

}
