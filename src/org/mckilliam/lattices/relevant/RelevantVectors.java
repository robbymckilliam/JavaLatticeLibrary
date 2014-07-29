package org.mckilliam.lattices.relevant;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Set;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.cvp.AllClosestPoints;
import org.mckilliam.lattices.util.AbstractPointEnumerator;
import org.mckilliam.lattices.util.IntegerVectors;
import org.mckilliam.lattices.util.PointEnumerator;

/**
 * Returns all relevant vectors in the lattice. 
 * @author Robby McKilliam
 */
public class RelevantVectors 
    extends AbstractPointEnumerator
        implements PointEnumerator{

    public final LatticeInterface L; //the lattice for will find the relevant vectors of
    public final Matrix B;  //the basis matrix of the lattice
        
    protected final AllClosestPoints allcps;
    protected final IntegerVectors intenum;
    protected java.util.Iterator<Matrix> currentvecs; //stores the relevant vectors found on the last iter
    protected Matrix v; //the current point we have computed closest lattice points for
    
    public RelevantVectors(LatticeInterface L){
        this.L = L;
        allcps = new AllClosestPoints(L);
        B = L.generatorMatrix();
        int N = L.dimension();
        intenum = new IntegerVectors(N, 2);
        intenum.nextElement(); //first element is all zeros so discard it.
        setupnext(); 
    }
    
    private void setupnext() {
        v = B.times(intenum.nextElement()).times(0.5);
        Set<Matrix> allpointsset = allcps.closestPoints(v.getColumnPackedCopy());
        while(allpointsset.size() > 2 && intenum.hasMoreElements()) { //only want cosest with two short vectors
            v = B.times(intenum.nextElement()).times(0.5);
            allpointsset = allcps.closestPoints(v.getColumnPackedCopy());
            if(allpointsset.size() < 2) throw new RuntimeException("The number of cosest should be at least 2.  Something bad numerically might be happening.");
        }
        if(allpointsset.size() != 2) currentvecs = new HashSet<Matrix>().iterator(); //we are finished if the size of allpointsset is not 2. Return an empty iterator to indicate this.
        else currentvecs = allpointsset.iterator(); //setup first relevant vectors
    }
    
    @Override
    public boolean hasMoreElements() {
        while(!currentvecs.hasNext() && intenum.hasMoreElements()) setupnext();
        return currentvecs.hasNext() || intenum.hasMoreElements();
    }
    
    @Override
    public Matrix nextElement() {
        if(!hasMoreElements()) throw new ArrayIndexOutOfBoundsException("There are no more relevant vectors!");
        return currentvecs.next().minus(v).times(2.0);
    }
    
    @Override
    public double[] nextElementDouble() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}