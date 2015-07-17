package org.mckilliam.lattices.cvp;

import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.cvp.ClosestVectorInterface;

/**
 * Interface for lattices with nearest specific nearest point algorithms
 * for that lattice.  There is some argument that this should be refactored,
 * in some way along with SphereDecoder and Babai.
 * 
 * @author Robby McKilliam
 */
public interface LatticeAndClosestVectorInterface extends LatticeInterface, ClosestVectorInterface {
    
}