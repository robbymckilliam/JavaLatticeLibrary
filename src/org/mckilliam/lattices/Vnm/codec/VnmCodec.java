package org.mckilliam.lattices.Vnm.codec;

/**
 * Class for encoding and decoding the Vnm lattice based codes.  See the paper
 * R. G McKilliam, "Convolutional lattice codes and the Prouhet-Tarry-Escott problem"
 * @author Robby McKilliam
 */
public interface VnmCodec {

    /** Encode a set of integers */
    public double[] encode(int[] u);
    
    /** Decode a given vector of doubles */
    public int[] decode(double[] y);
    
    /** Return the shaping loss of this code with respect to the hypercube */
    public double shapingLoss();
    
}
