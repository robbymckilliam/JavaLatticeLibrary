/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import Jama.Matrix;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.NearestPointAlgorithmInterface;

/**
 * Lazy Babai algorithm that does not bother to
 * do LLL reduction first.
 * @author Robby McKilliam
 */
public class BabaiNoLLL extends Babai
                        implements NearestPointAlgorithmInterface {

    public BabaiNoLLL(LatticeInterface L){
        super(L,new pubsim.lattices.reduction.None());
    }
    
}
