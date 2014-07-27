/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anm;

import pubsim.lattices.Anstar.AnstarVaughan;
import pubsim.lattices.Anm.AnmBucket;
import pubsim.lattices.Anm.AnmSorted;
import java.util.Date;
import java.util.Random;
import junit.framework.TestCase;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class AnmBucketTest extends TestCase {
    
    public AnmBucketTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of nearestPoint method, of class AnmBucket.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int numTrials = 1000;
        int n = 100;
        int M = 20;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        
        AnmBucket instance = new AnmBucket(n-1,M);
        AnmSorted tester = new AnmSorted(n-1,M);
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

        for(int i=0; i<numTrials; i++){
            for(int k = 0; k < n; k++){
                y[k] = rand.nextGaussian()*100.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            AnstarVaughan.project(y,x);
            
            
            /*
             System.out.println(" test d = " + VectorFunctions.distance_between(y, v_tester));
            System.out.println(" inst d = " + VectorFunctions.distance_between(y, v_instance));
            System.out.println(" test u = " + VectorFunctions.print(tester.getIndex()));
            System.out.println(" inst u = " + VectorFunctions.print(instance.getIndex()));
            */
            //System.out.println(" test mod = " + VectorFunctions.sum(tester.getIndex())%M);
            //System.out.println(" inst mod = " + VectorFunctions.sum(instance.getIndex())%M);
            
            
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
        
    }
    

    /**
     * Test of nearestMultM method, of class AnmBucket.
     */
    public void testNearestMultM() {
        System.out.println("nearestMultM");
        double v = 1.0;
        int M = 4;
        int expResult = 0;
        int result = AnmBucket.nearestMultM(v, M);
        assertEquals(expResult, result);
        
        v = 3;
        M = 4;
        expResult = 4;
        result = AnmBucket.nearestMultM(v, M);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of nearestMultM method, of class AnmBucket.
     */
    public void testNearestMultInRange() {
        System.out.println("nearestMultM");
        double v = 1.0;
        int M = 4;
        int expResult = 12;
        int result = AnmBucket.nearestMultInRange(v, 10, 20, M);
        assertEquals(expResult, result);

        v = 30;
        M = 4;
        expResult = 20;
        result = AnmBucket.nearestMultInRange(v, 10, 20, M);
        assertEquals(expResult, result);

        
    }

}
