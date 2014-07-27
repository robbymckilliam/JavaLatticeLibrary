/*
 */

package pubsim.lattices;

import pubsim.lattices.VoronoiCodeAutoTranslation;
import pubsim.lattices.Hexagonal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static pubsim.VectorFunctions.print;
import static pubsim.VectorFunctionsTest.assertVectorsEqual;

/**
 *
 * @author Robby McKilliam
 */
public class VoronoiCodeAutoTranslationTest {

    public VoronoiCodeAutoTranslationTest() {
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
     * This should return [-1/4, 0] or [1/4, 0] as per
     * Conway and Sloane.
     */
    @Test
    public void test8Hex() {
        System.out.println("test8Hex");
        Hexagonal lattice = new Hexagonal();
        int r = 3;
        VoronoiCodeAutoTranslation inst =
                new VoronoiCodeAutoTranslation(lattice, r);
        System.out.println(print(inst.getTranslation()));
        System.out.print("pow = " + inst.averagePower());

    }

    /**
     * This should return [-1/4, 0] or [1/4, 0] as per
     * Conway and Sloane.
     */
    @Test
    public void test16Hex() {
        System.out.println("test16Hex");
        Hexagonal lattice = new Hexagonal();
        int r = 4;
        VoronoiCodeAutoTranslation inst = 
                new VoronoiCodeAutoTranslation(lattice, r);
        System.out.println(print(inst.getTranslation()));
        double[] exp = {-0.25, 0.0};
        assertVectorsEqual(exp, inst.getTranslation(), 0.00001);
        assertEquals(2.1875,inst.averagePower(), 0.00001);

    }

    /**
     * This should return [-1/4, 0] or [1/4, 0] as per
     * Conway and Sloane.
     */
    @Test
    public void test32Hex() {
        System.out.println("test32Hex");
        Hexagonal lattice = new Hexagonal();
        int r = 5;
        VoronoiCodeAutoTranslation inst =
                new VoronoiCodeAutoTranslation(lattice, r);
        System.out.println(print(inst.getTranslation()));
        System.out.print("pow = " + inst.averagePower());
        //double[] exp = {-0.25, 0.0};
        //assertVectorsEqual(exp, inst.getTranslation(), 0.00001);

    }

}