package pubsim.lattices.decoder;

import Jama.Matrix;
import pubsim.lattices.LatticeInterface;

/**
 * 
 * @author Robby McKilliam
 */
public class MbestNoLLL extends Mbest {

    public MbestNoLLL(LatticeInterface L, int M){
        super(L,M,new pubsim.lattices.reduction.None());
    }

}
