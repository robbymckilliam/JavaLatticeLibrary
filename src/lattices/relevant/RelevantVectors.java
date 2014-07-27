package pubsim.lattices.relevant;

import Jama.Matrix;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.decoder.AllClosestPoints;
import pubsim.lattices.util.AbstractPointEnumerator;
import pubsim.lattices.util.IntegerVectors;
import pubsim.lattices.util.PointEnumerator;

/**
 * Returns all relevant vectors in the lattice.  This returns all of the 'strict' and 'lax' relevant 
 * vectors.
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
        B = L.getGeneratorMatrix();
        int N = L.getDimension();
        intenum = new IntegerVectors(N, 2);
        setupnext(); 
    }
    
    private void setupnext() {
        v = B.times(intenum.nextElement()).times(0.5);
        currentvecs = allcps.closestPoints(v.getColumnPackedCopy()).iterator(); //setup first relevant vectors
    }
    
    @Override
    public double[] nextElementDouble() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet.");
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

}