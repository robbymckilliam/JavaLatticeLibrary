package org.mckilliam.lattices;

import org.mckilliam.lattices.Dn;
import Jama.Matrix;
import junit.framework.TestCase;
import org.mckilliam.distributions.Gaussian;
import org.mckilliam.lattices.cvp.SphereDecoder;
import org.mckilliam.distributions.processes.NoiseVector;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class DnTest extends TestCase {
    
    public DnTest(String testName) {
        super(testName);
    }            

    /**
     * Test of nearestPoint method, of class Dn.
     */
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 7;
        int iters = 100;
        
        Gaussian noise = new Gaussian(0.0, 1000.0);
        NoiseVector siggen = new NoiseVector(noise, n);
        
        Dn instance = new Dn(n);
        SphereDecoder tester = new SphereDecoder(instance);
        
        for(int i = 0; i < iters; i++){
            Double[] y = siggen.generateReceivedSignal();
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            assertEquals(VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint())<0.0001, true);
        }
    }

    /**
     * Test of nearestPoint method, of class Dn.
     */
    public void testGeneratorMatrix() {
        System.out.println("testGeneratorMatrix");

        int n = 24;

        Dn instance = new Dn(n);
        Matrix G = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.printForMathematica(G));

        System.out.println(G.det());

    }


}
