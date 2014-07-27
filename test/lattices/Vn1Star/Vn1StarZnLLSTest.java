/*
 * Vn1StarZnLLSTest.java
 * JUnit based test
 *
 * Created on 12 August 2007, 22:11
 */

package pubsim.lattices.Vn1Star;

import pubsim.lattices.Vn1Star.Vn1StarZnLLS;
import pubsim.lattices.Vn1Star.Vn1StarGlued;
import java.util.Random;
import junit.framework.*;
import pubsim.*;

/**
 *
 * @author Robby McKilliam
 */
public class Vn1StarZnLLSTest extends TestCase {
    
    public Vn1StarZnLLSTest(String testName) {
        super(testName);
    }

    /**
     * Test of setDimension method, of class simulator.PhinStar2.
     */
    public void testSetDimension() {
        System.out.println("setDimension");        
        int n = 5;
        Vn1StarZnLLS instance = new Vn1StarZnLLS(5);
    }

    /**
     * Test of nearestPoint method, of class simulator.PhinStar2.
     */
    public void testNearestPoint() {
        
        System.out.println("nearestPoint");
        int n = 34;
        Random rand = new Random();
        
        double[] y = new double[n];
        double[] QgQ1y = new double[n];
        Vn1StarZnLLS znlls = new Vn1StarZnLLS(n-2);
        Vn1StarGlued glued = new Vn1StarGlued(n-2);
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 100 * rand.nextGaussian();
        
            Vn1StarZnLLS.project(y,QgQ1y);
            
            znlls.nearestPoint(QgQ1y);
            glued.nearestPoint(QgQ1y);
            
            double diff = VectorFunctions.distance_between(znlls.getLatticePoint(), glued.getLatticePoint());

            //System.out.println(diff);
            
            assertEquals(diff < 0.000001, true);
            
            
        }
        
    }
    
}
