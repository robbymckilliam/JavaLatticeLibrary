package pubsim.lattices;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.VectorFunctions;
import pubsim.lattices.util.PointEnumerator;

/**
 *
 * @author Robby McKilliam
 */
public class ZnTest {
    
    public ZnTest() {
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
     * Test of volume method, of class Zn.
     */
    @Test
    public void testVolume() {
        System.out.println("volume");
        for(int n = 1; n < 10; n++)
            assertEquals(new Zn(n).volume(), 1.0, 0.00000001);
    }

    /**
     * Test of norm method, of class Zn.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        for(int n = 1; n < 10; n++)
            assertEquals(new Zn(n).norm(), 1.0, 0.00000001);
    }

    /**
     * Test of kissingNumber method, of class Zn.
     */
    @Test
    public void testKissingNumber() {
        System.out.println("kissingNumber");
        for(int n = 1; n < 10; n++)
            assertEquals(new Zn(n).kissingNumber(), 2*n);
    }

    /**
     * Test of coveringRadius method, of class Zn.
     */
    @Test
    public void testCoveringRadius() {
        System.out.println("coveringRadius");
        for(int n = 1; n < 10; n++)
            assertEquals(new Zn(n).coveringRadius(), Math.sqrt(n)/2, 0.0000001);
    }
    
    /**
     * Test of relevantVectors method, of class Zn.
     */
    @Test
    public void testRelevantVectors() {
        System.out.println("relevantVectors");
        PointEnumerator rvs = new Zn(2).relevantVectors();
        while(rvs.hasMoreElements()) {
            System.out.println(VectorFunctions.print(rvs.nextElement()));
            System.out.println(rvs.percentageComplete());
        }
    }
    
}
