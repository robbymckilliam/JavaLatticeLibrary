/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Vnmstar;

import pubsim.lattices.Vnmstar.VnmStarGlued;
import pubsim.lattices.Vnmstar.VnmStar;
import java.util.Random;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarSorted;
import pubsim.lattices.decoder.SphereDecoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;
import static pubsim.Util.binom;
import static pubsim.Util.factorial;

/**
 *
 * @author Robby McKilliam
 */
public class VnmStarGluedTest {

    public VnmStarGluedTest() {
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


//    /**
//     * Test of discreteLegendrePolynomialVector method, of class VnmStarGlued.
//     */
//    @Test
//    public void testDiscreteLegendrePolynomial() {
//        System.out.println("discreteLegendrePolynomial");
//
//        int n = 10;
//        int m = 3;
//
//        for(int i = 0; i <= m; i++){
//            for(int j = 0; j <= m; j++){
//                double[] pi = VnmStarGlued.discreteLegendrePolynomialVector(n, i);
//                double[] pj = VnmStarGlued.discreteLegendrePolynomialVector(n, j);
//                if(i!=j)
//                    assertEquals(0.0, VectorFunctions.dot(pi, pj), 0.00001);
//                if(i==j)
//                    assertEquals(factorial(i)/binom(2*i, i)*binom(n+i, 2*i+1),
//                                        VectorFunctions.dot(pi, pj), 0.00001);
//            }
//        }
//
//    }

    /**
     * Test of nearestPoint method, of class VnmStarGlued.
     */
    @Test
    public void testNearestPointAnStar() {
        System.out.println("testNearestPointAnStar");
        int n = 20;
        int m = 0;
        int N = n + m + 1;

        Random rand = new Random();
        double[] y = new double[N];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[N];
        Anstar tester = new AnstarSorted(n);
        VnmStarGlued instance = new VnmStarGlued(m, n);

        //instance.setDimension(n - 1);
        //tester.setDimension(n - 1);
        for(int i=0; i<50; i++){
            for(int k = 0; k < N; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*100.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            Anstar.project(y,x);

            //System.out.println(VectorFunctions.distance_between2(v_instance, x));
            //System.out.println(VectorFunctions.distance_between2(v_tester, x));

            //System.out.println(VectorFunctions.distance_between2(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
    }

    /**
     * Test of nearestPoint method, of class VnmStarGlued.
     */
    @Test
    public void testNearestPointSphereDecoder() {
        System.out.println("testNearestPointSphereDecoder");
        int n = 7;
        int m = 1;
        int N = n + m + 1;

        Random rand = new Random();
        double[] y = new double[N];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[N];
        VnmStarGlued instance = new VnmStarGlued(m, n);
        SphereDecoder tester = new SphereDecoder(instance);

        //instance.setDimension(n - 1);
        //tester.setDimension(n - 1);
        for(int i=0; i<100; i++){
            for(int k = 0; k < N; k++){
                y[k] = ( rand.nextGaussian() - 0.5 )*100.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();

            //System.out.println(VectorFunctions.distance_between2(v_instance, x));
            //System.out.println(VectorFunctions.distance_between2(v_tester, x));

            //System.out.println(VectorFunctions.distance_between2(v_instance, v_tester));
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
    }

}