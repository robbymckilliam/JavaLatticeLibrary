/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.util.region;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class NSphereTest {

    public NSphereTest() {
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
     * Test of within method, of class NSphere.
     */
    @Test
    public void testWithin() {
        System.out.println("within");
        NSphere instance = new NSphere(1.0, 3);
        double[] y = {0.1, 0.1, 0.1};
        assertTrue(instance.within(y));

        double[] y1 = {2.1, 0.1, 0.1};
        assertFalse(instance.within(y1));
    }

    /**
     * Test of dimension method, of class NSphere.
     */
    @Test
    public void testDimension() {
        System.out.println("dimension");
        double[] c = {0.1, 0.1, 0.1};
        NSphere instance = new NSphere(1.0, c);
        assertEquals(instance.dimension(), 3);
    }

}