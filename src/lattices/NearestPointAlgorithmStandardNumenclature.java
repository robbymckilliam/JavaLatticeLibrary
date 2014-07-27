package pubsim.lattices;

import pubsim.lattices.Anm.AnmBucket;
import java.util.Date;
import java.util.Random;

/**
 * Abstract class for Nearest point algorithms.  You are not required
 * to use this but it saves you from writing getters and setters and
 * uses some standard variable names.  Don't use this abstract class
 * if you don't intend to use the following numenclature!
 * ie.  u is the index of the nearest lattice point.
 *      v is the nearest lattice point.
 *      n is the dimension of this lattice.
 * @author Robby McKilliam
 */
public abstract class NearestPointAlgorithmStandardNumenclature
    extends AbstractLattice implements LatticeAndNearestPointAlgorithmInterface {
    
    /** The nearest lattice point */
    protected double[] v;
    
    /** The integer index that generate the nearest lattice point */
    protected double[] u;

    /** Dimension of the lattice */
    final protected int n;
    
    public NearestPointAlgorithmStandardNumenclature(int n){
        this.n = n;
    }
    
    
    /**Getter for the nearest point. */
    @Override
    public double[] getLatticePoint() {return v;}
    
    /**Getter for the integer vector. */
    @Override
    public double[] getIndex() {return u;}

    /**
     * Return the covering radius for this lattice
     */
    @Override
    public double coveringRadius(){
        throw new UnsupportedOperationException("Covering radius not supported");
    }
    
    /** 
     * This assumes that n is the dimension of your lattice! 
     * Don't use this abstract class if you don't intend n to
     * be the dimension of your lattice.
     */
    @Override
    public int getDimension(){ return n; }
    
    
    
    
    /** 
     * Main function for testing the practical running times
     * of nearest point algorithms.
     */
    public static void main(String[] args) {

        System.out.println("timingTest");
        
        int numTrials = 100000;
        int n = 1024;
        int M = n/4;
        Random rand = new Random();
        double[] y = new double[n];
        
        LatticeAndNearestPointAlgorithmInterface instance = new AnmBucket(n-1, M);
        /*
        double[] y = {0.21, 0.211, 0.2111, 0.21111, 0.211111, 0.21112};
        
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            
            
            System.out.println(" test d = " + VectorFunctions.distance_between(y, v_tester));
            System.out.println(" inst d = " + VectorFunctions.distance_between(y, v_instance));
            System.out.println(" test u = " + VectorFunctions.print(tester.getIndex()));
            System.out.println(" inst u = " + VectorFunctions.print(instance.getIndex()));
            
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        */

        Date timer = new Date();
        long start = timer.getTime();
        
        for(int i=0; i<numTrials; i++){
            for(int k = 0; k < n; k++){
                y[k] = rand.nextGaussian()*100.0;
            }
            instance.nearestPoint(y);
        }
        timer = new Date();
        long end = timer.getTime();
        double runtime = (end - start)/1000.0;
        System.out.println("time = " + runtime);
        
        //assertTrue(true);
        
    }
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            yDoubletoy = new double[y.length];
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }

}
