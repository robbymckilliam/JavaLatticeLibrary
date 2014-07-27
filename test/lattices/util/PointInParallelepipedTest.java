/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.util;

import pubsim.lattices.util.PointInParallelepiped;
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
public class PointInParallelepipedTest {

    public PointInParallelepipedTest() {
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
     * Test of nextElement method, of class PointInParallelepiped.
     */
    @Test
    public void testNextElement() {
        System.out.println("nextElement");
        Matrix B = Matrix.identity(3, 3);
        PointInParallelepiped instance
                = new PointInParallelepiped(B, 4);

        for(Matrix M : instance){
            System.out.print(VectorFunctions.print(M.transpose()));
        }
    }

}