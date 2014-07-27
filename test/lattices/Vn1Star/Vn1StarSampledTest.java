/*
 * Vn1StarSampledTest.java
 * JUnit based test
 *
 * Created on 18 August 2007, 17:55
 */

package pubsim.lattices.Vn1Star;

import pubsim.lattices.Vn1Star.Vn1StarGlued;
import pubsim.lattices.Vn1Star.Vn1Star;
import pubsim.lattices.Vn1Star.Vn1StarSampled;
import java.util.Random;
import junit.framework.TestCase;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class Vn1StarSampledTest extends TestCase {
    
    public Vn1StarSampledTest(String testName) {
        super(testName);
    }

    /**
     * Test of nearestPoint method, of class simulator.Vn1StarSampled.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 30;
        Random rand = new Random();
        
        double[] y = new double[n];
        Vn1StarSampled instance = new Vn1StarSampled(n-2, n*10);
        Vn1StarGlued tester = new Vn1StarGlued(n-2);
        
        for(int i = 0; i < 10; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
            
            Vn1Star.project(y,y);
            
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            
            double dist = VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint());
            
            System.out.println("inst = " + VectorFunctions.print(instance.getIndex()));
            System.out.println("test = " + VectorFunctions.print(tester.getIndex()));
            double[] s = VectorFunctions.subtract(instance.getIndex(), tester.getIndex());
            System.out.println("diff = " + VectorFunctions.print(s));
            
            System.out.println("inst = " + VectorFunctions.distance_between(instance.getLatticePoint(), y));
            System.out.println("test = " + VectorFunctions.distance_between(tester.getLatticePoint(), y));
            assertEquals(dist < 0.0001, true);
            
        }

    }
    
}
