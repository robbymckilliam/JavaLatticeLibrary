package pubsim.lattices.util;

import Jama.Matrix;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Interface for generating points (ie Matrix of double[]).
 * @author Robby McKilliam
 */
public interface PointEnumerator extends 
        Enumeration<Matrix>, Iterable<Matrix>, Serializable{

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    double[] nextElementDouble();

    double percentageComplete();



}
