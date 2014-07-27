package pubsim.lattices.util.region;

/**
 * Bounding box.
 * @author Robby McKilliam
 */
public interface BoundingBox {

    /** Get minimum value of the nth coordinate. */
    public double minInCoordinate(int n);

    /** Get maximum value of the nth coordinate. */
    public double maxInCoordinate(int n);

}
