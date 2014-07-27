/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.An;

import junit.framework.TestCase;
import pubsim.VectorFunctions;
import pubsim.distributions.Uniform;
import pubsim.distributions.processes.NoiseVector;

/**
 *
 * @author Robby
 */
public class AnSortedTest extends TestCase {
    
    public AnSortedTest(String testName) {
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
     * Test of nearestPoint method, of class AnSorted.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        //this test uses the fact that I know that
        //any value in the unit cube should have 
        //the origin as the closest point in An
        
        int n = 10;
        int iters = 100;
        
        Uniform noise = Uniform.constructFromMeanAndRange(0,1.0);
        NoiseVector siggen = new NoiseVector(noise, n);
        
        AnSorted instance = new AnSorted(n-1);
        //instance.setDimension(n - 1);
        
        for(int i = 0; i < iters; i++){
            Double[] y = siggen.generateReceivedSignal();
            instance.nearestPoint(y);
            assertEquals(0.0, VectorFunctions.sum(instance.getLatticePoint()));
        }
        
    }


}
