/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.util;

import pubsim.lattices.Zn;
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
public class MomentApproximatorTest {

    public MomentApproximatorTest() {
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
     * Test of properties for Zn lattice
     */
    @Test
    public void testZn() {
        System.out.println("test with integer lattice Zn");

        int N = 4;
        int samples = 100000;

        MomentApproximator prop = new MomentApproximator(new Zn(N), 2);
        prop.uniformlyDistributed(samples);
        assertEquals(N/12.0, prop.moment(), 0.001);

        prop = new MomentApproximator(new Zn(N), 3);
        prop.uniformlyDistributed(samples);
        assertEquals(0.0, prop.moment(), 0.001);

        prop = new MomentApproximator(new Zn(N), 4);
        prop.uniformlyDistributed(samples);
        assertEquals(N/16.0/5.0, prop.moment(), 0.001);


    } 

}