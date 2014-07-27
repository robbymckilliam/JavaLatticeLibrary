/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckilliam.lattices.cvp;

import Jama.Matrix;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.ClosestVectorInterface;

/**
 * Lazy Babai algorithm that does not bother to
 * do LLL reduction first.
 * @author Robby McKilliam
 */
public class BabaiNoLLL extends Babai
                        implements ClosestVectorInterface {

    public BabaiNoLLL(LatticeInterface L){
        super(L,new org.mckilliam.lattices.reduction.None());
    }
    
}
