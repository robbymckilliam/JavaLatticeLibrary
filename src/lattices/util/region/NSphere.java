package pubsim.lattices.util.region;

import static pubsim.VectorFunctions.distance_between;


/**
 * An N dimensional sphere
 * @author Robby McKilliam
 */
public class NSphere implements Region, BoundingBox {

    protected double[] center;
    protected double radius;

    /** Sphere of dimension N centered at the origin */
    public NSphere(double radius, int N){
        init(radius, new double[N]);
    }

    /** Sphere of dimension center.length centered at center */
    public NSphere(double radius, double[] center){
        init(radius, center);
    }

    private void init(double radius, double[] center){
        this.center = center;
        this.radius = radius;
    }

    public boolean within(double[] y) {
        return distance_between(y, center) <= radius;
    }

    public int dimension() {
        return center.length;
    }

    public double minInCoordinate(int n) {
        return center[n] - radius;
    }

    public double maxInCoordinate(int n) {
        return center[n] + radius;
    }

}
