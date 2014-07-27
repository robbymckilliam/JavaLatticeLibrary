/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckilliam.lattices.decoder;

import Jama.Matrix;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.NearestPointAlgorithmInterface;

/**
 * Lazy Babai algorithm that does not bother to
 * do LLL reduction first.
 * @author Robby McKilliam
 */
public class BabaiNoLLL extends Babai
                        implements NearestPointAlgorithmInterface {

    public BabaiNoLLL(LatticeInterface L){
        super(L,new org.mckilliam.lattices.reduction.None());
    }
    
}
