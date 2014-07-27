package pubsim.lattices.util;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class IntegerVectorsTest {
    
    public IntegerVectorsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of nextElement method, of class IntegerVectors.
     */
    @Test
    public void testNextElement() {
        System.out.println("nextElement");
        IntegerVectors ivs = new IntegerVectors(2,2);
        while(ivs.hasMoreElements())
            System.out.println(VectorFunctions.print(ivs.nextElement()));
    }
    
}
