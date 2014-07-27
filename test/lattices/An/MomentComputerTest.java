/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.An;

import bignums.BigInteger;
import bignums.BigRational;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import pubsim.Util;

/**
 *
 * @author Robby McKilliam
 */
public class MomentComputerTest {
    
    public MomentComputerTest() {
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
     * Test of factorial method and recursion.
     */
    @Test
    public void testFactorial() {
        System.out.println("test factorial");
        
        MomentComputer.RecursionStorageAndCompute G = new MomentComputer.RecursionStorageAndCompute();
        for(int n = 0; n < 20; n++)
            assertTrue(G.factorial(n).compareTo(new BigInteger(Long.toString(Util.factorial(n)))) == 0);
    }

    /**
     * Test of binomial method and recursion.
     */
    @Test
    public void testBinomial() {
        System.out.println("test binomial");
        
        MomentComputer.RecursionStorageAndCompute G = new MomentComputer.RecursionStorageAndCompute();
        for(int m = 0; m < 20; m++)
            for(int k = 0; k <= m; k++)
                assertTrue(G.binom(m,k).compareTo(new BigInteger(Long.toString(Util.binom(m,k)))) == 0);
    }

    /**
     * Test of the G function.
     */
    @Test
    public void testG() {
        System.out.println("test G");
        
        MomentComputer.RecursionStorageAndCompute G = new MomentComputer.RecursionStorageAndCompute();
        assertTrue(G.G(2,2,0).compareTo(new BigRational(28,45))==0);
        assertTrue(G.G(2,2,1).compareTo(new BigRational(13,15))==0);
        assertTrue(G.G(2,2,5).compareTo(new BigRational(1549,315))==0);
        assertTrue(G.G(3,2,2).compareTo(new BigRational(1235,252))==0);
        
        
    }

    /**
     * Test of the G function.
     */
    @Test
    public void testmoment() {
        System.out.println("test moment");
        
        MomentComputer.RecursionStorageAndCompute G = new MomentComputer.RecursionStorageAndCompute();

        assertTrue(G.moment(2,2).compareTo(new BigRational(7,45))==0);
        assertTrue(G.moment(2,1).compareTo(new BigRational(5,9))==0);
        assertTrue(G.moment(3,3).compareTo(new BigRational(1397,32256))==0);
        assertTrue(G.moment(4,2).compareTo(new BigRational(63,250))==0);
        assertTrue(G.moment(5,3).compareTo(new BigRational(1177,13608))==0);
    }
    
    /**
     * Test of the proberror function.
     */
    @Test
    public void testproberror() {
        System.out.println("test probability of error");
        
        
        MomentComputer mc = new MomentComputer("testname");
        System.out.println("finished deserialising");
        
        assertEquals(mc.ProbError(5, new BigRational(1,5),1e-15), 0.611081,1e-6);
        //assertEquals(mc.ProbError(5, new BigRational(1,100),1e-15), -2.0615e-7,1e-11);
        assertEquals(mc.ProbError(2, new BigRational(1,10),1e-15), 0.0652598,1e-6);
        
        mc.save("testname");
    }
}
    


