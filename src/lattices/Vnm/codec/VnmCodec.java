/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import pubsim.lattices.Vnm.Vnm;
import pubsim.lattices.Vnm.codec.generators.UpperTriangularGenerator;

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
