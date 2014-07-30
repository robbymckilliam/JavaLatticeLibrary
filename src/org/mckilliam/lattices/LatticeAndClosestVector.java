package org.mckilliam.lattices;

import Jama.Matrix;
import org.mckilliam.lattices.cvp.SphereDecoderSchnorrEuchner;
import org.mckilliam.lattices.relevant.RelevantVectors;
import org.mckilliam.lattices.util.PointEnumerator;

/**
 * General lattice with a nearest point algorithm included with it.
 * By default the sphere decoder is used but you can use other
 * algorithms by using the appropriate constructor
 * @author Robby McKilliam
 */
public class LatticeAndClosestVector extends Lattice implements LatticeAndClosestVectorInterface {

    private final ClosestVectorInterface decoder;

    public LatticeAndClosestVector(Matrix B){
        super(B);
        decoder = new SphereDecoderSchnorrEuchner(this);
        //decoder = new SphereDecoder(this);
    }

    public LatticeAndClosestVector(double[][] B){
        super(new Matrix(B));
        decoder = new SphereDecoderSchnorrEuchner(this);
        //decoder = new SphereDecoder(this);
    }

     public LatticeAndClosestVector(Matrix B, ClosestVectorInterface np){
        super(B);
        decoder = np;
    }

    @Override
    public double[] closestPoint(double[] y) {
        decoder.closestPoint(y);
        return decoder.getLatticePoint();
    }

    @Override
    public double[] getLatticePoint() {
        return decoder.getLatticePoint();
    }

    @Override
    public double[] getIndex() {
        return decoder.getIndex();
    }

    @Override
    public double distance() {
        return decoder.distance();
    }
    
    /** @return an enumeration of the relevant vectors for this lattice */
    @Override
    public PointEnumerator relevantVectors() {
        return new RelevantVectors(this);
    }

}
