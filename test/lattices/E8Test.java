/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices;

import Jama.Matrix;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.VectorFunctions;
import static pubsim.VectorFunctions.distance_between;
import pubsim.distributions.Gaussian;
import pubsim.distributions.processes.NoiseVector;
import static pubsim.lattices.E8.dMat;
import pubsim.lattices.decoder.SphereDecoder;
import pubsim.lattices.util.PowerOfEuclideanNorm;

/**
 *
 * @author Robby McKilliam
 */
public class E8Test {
    
    public E8Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

     /**
     * Test of nearestPoint method, of class E8.
     */
    @Test
    public void testNearestPoint() {
        System.out.println("nearestPoint");
        
        int n = 8;
        int iters = 100;
        
        Gaussian noise = new Gaussian(0.0, 1000.0);
        NoiseVector siggen = new NoiseVector(noise, n);
        
        E8 instance = new E8();
        SphereDecoder tester = new SphereDecoder(instance);
        
        for(int i = 0; i < iters; i++){
            Double[] y = siggen.generateReceivedSignal();
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            //System.out.println(print(instance.getLatticePoint()));
            //System.out.println(print(tester.getLatticePoint()));
            assertEquals(distance_between(instance.getLatticePoint(), tester.getLatticePoint())<0.0001, true);
        }
    }
    
    /**
     * Test of second moment method, of class An.
     */
    @Test
    public void testSecondMoment() {
        System.out.println("test second moment");
        E8 instance = new E8();
        
        PowerOfEuclideanNorm mcc = new PowerOfEuclideanNorm(instance, 1);
        mcc.uniformlyDistributed(1000000);
        double momentmc = mcc.moment();
        
        System.out.println(momentmc + ", " + instance.secondMoment());
        
        assertEquals(momentmc, instance.secondMoment(), 0.01);

    }
    
//    @Test
//    public void testObtuseSuperbasis() {
//        final double[][] Bmat
//            = { {2,0,0,0,0,0,0,0},
//                {-1,1,0,0,0,0,0,0},
//                {0,-1,1,0,0,0,0,0},
//                {0,0,-1,1,0,0,0,0},
//                {0,0,0,-1,1,0,0,0},
//                {0,0,0,0,-1,1,0,0},
//                {0,0,0,0,0,-1,1,0},
//                {-1.0/2,-1.0/2,-1.0/2,-1.0/2,-1.0/2,-1.0/2,-1.0/2,-1.0/2}, 
//                {-1.0/2,1.0/2,1.0/2,1.0/2,1.0/2,1.0/2,-1.0/2,1.0/2}, 
//            };
//        for(int i = 0; i < 8; i++) {
//            double b = 0.0;
//            for(int j = 0; j < 9; j++) b = b + Bmat[j][i];
//            assertEquals(b, 0.0, 0.0001);
//        }
//         final Matrix B = new Matrix(dMat);
//         System.out.println(VectorFunctions.print(B.times(B.transpose())));
//    }
    
}
