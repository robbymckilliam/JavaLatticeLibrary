package org.mckilliam.lattices.decoder;

import Jama.Matrix;
import org.mckilliam.lattices.LatticeInterface;

/**
 * 
 * @author Robby McKilliam
 */
public class MbestNoLLL extends Mbest {

    public MbestNoLLL(LatticeInterface L, int M){
        super(L,M,new org.mckilliam.lattices.reduction.None());
    }

}
