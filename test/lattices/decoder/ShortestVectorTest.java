/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import static pubsim.VectorFunctions.sum2;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.E8;
import pubsim.lattices.Vnm.Vnm;
import pubsim.lattices.leech.Leech;

/**
 *
 * @author Robby McKilliam
 */
public class ShortestVectorTest {

    public ShortestVectorTest() {
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
     * Test of getShortestVector method, of class ShortestVector.
     */
    @Test
    public void testShortVectorNorm() {
        System.out.println("testShortVectorNorm");
        assertEquals(4.0, sum2(new ShortVectorSphereDecoded(new Vnm(30, 1)).getShortestVector()), 0.000001);
        assertEquals(8.0, sum2(new ShortVectorSphereDecoded(new Vnm(30, 3)).getShortestVector()), 0.000001);
        assertEquals(6.0, sum2(new ShortVectorSphereDecoded(new Vnm(30, 2)).getShortestVector()), 0.000001);
        assertEquals(2.0, sum2(new ShortVectorSphereDecoded(new E8()).getShortestVector()), 0.000001);
        assertEquals(2.0, sum2(new ShortVectorSphereDecoded(new AnFastSelect(10)).getShortestVector()), 0.000001);
        assertEquals(4.0, sum2(new ShortVectorSphereDecoded(new Leech()).getShortestVector()), 0.000001);
    }


}