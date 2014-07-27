/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Vn1Star;

import pubsim.lattices.Vn1Star.Vn1StarZnLLS;
import pubsim.lattices.Vn1Star.Vn1Star;
import pubsim.lattices.Vn1Star.Vn1StarSampled;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class Vn1StarTest {

    public Vn1StarTest() {
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
     * Test derived formula for volume of this lattice.
     */
    @Test
    public void testVolumeFormula() {
        int n = 5;

        double volf = Math.sqrt(12.0/((n+3)*(n+2)*(n+1)*(n+2)));

        Vn1Star lattice = new Vn1StarZnLLS(n);
        double vol = lattice.volume();

        assertEquals(volf, vol, 0.0000000001);
    }

//    /**
//     * Test of inradius method, of class Vn1Star.
//     */
//    @Test
//    public void testInradius() {
//        System.out.println("inradius");
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeneratorMatrix method, of class Vn1Star.
//     */
//    @Test
//    public void testGetGeneratorMatrix() {
//        System.out.println("getGeneratorMatrix");
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getMMatrix method, of class Vn1Star.
     */
    @Test
    public void testGetMMatrix() {
        int N = 10;
        Matrix M = Vn1Star.getMMatrix(N-2);
        for(int n = 0; n < n; n++){
            assertEquals(1.0, M.get(n, 0), 0.000000001);
            assertEquals(n+1, M.get(n, 0), 0.000000001);
        }
    }

    /**
     * Test of getgVector method, of class Vn1Star.
     */
    @Test
    public void testGetgVector() {
        System.out.println("getgVector");
        int n = 5;
        double[] result = Vn1Star.getgVector(n);
        double[] expResult = {-2, -1, 0, 1, 2};
        for(int i = 0; i < n; i++)
            assertEquals(expResult[i], result[i], 0.000001);

        n = 6;
        result = Vn1Star.getgVector(n);
        double[] expResult2 = {-2.5, -1.5, -.5, .5, 1.5, 2.5};
        for(int i = 0; i < n; i++)
            assertEquals(expResult2[i], result[i], 0.000001);

    }

    /**
     * Test of sumg2 method, of class Vn1Star.
     */
    @Test
    public void testSumg2() {
        System.out.println("sumg2");
        for(int n = 3; n < 50; n++){
            double test = VectorFunctions.sum2(Vn1Star.getgVector(n));
            double val = Vn1Star.sumg2(n);
            assertEquals(test, val, 0.000001);
        }
    }

}