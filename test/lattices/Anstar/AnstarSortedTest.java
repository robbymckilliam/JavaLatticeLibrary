/*
 * AnstarSortedTest.java
 * JUnit based test
 *
 * Created on 14 November 2007, 10:16
 */

package pubsim.lattices.Anstar;

import java.util.Random;
import junit.framework.TestCase;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class AnstarSortedTest extends TestCase {
    
    public AnstarSortedTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.AnstarOn.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 30;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        AnstarSorted instance = new AnstarSorted(n-1);
        AnstarVaughan tester = new AnstarVaughan(n-1);

        for(int i=0; i<50; i++){
            for(int k = 0; k < n; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*10.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            AnstarVaughan.project(y,x);
            System.out.println(VectorFunctions.distance_between(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
        
        
    }
    
}
