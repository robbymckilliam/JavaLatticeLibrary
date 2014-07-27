package pubsim.lattices;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.lattices.decoder.KissingNumber;

/**
 *
 * @author Robby McKilliam
 */
public class E6Test {
    
    public E6Test() {
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
     * Test of volume versus Gram matrix determinant
     */
    @Test
    public void testVolume() {
        System.out.println("volume");
        E6 instance = new E6();
        Matrix B = instance.getGeneratorMatrix();
        assertEquals(Math.sqrt((B.transpose().times(B)).det()), instance.volume(), 0.00001);
    }
    
    @Test
    public void testKissingNumber() {
        System.out.println("Kissing number");
        E6 instance = new E6();
        KissingNumber ks = new KissingNumber(instance);
        assertEquals(ks.kissingNumber(), instance.kissingNumber());
    }

}