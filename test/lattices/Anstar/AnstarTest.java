/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anstar;

import Jama.Matrix;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.decoder.KissingNumber;

/**
 *
 * @author Robby
 */
public class AnstarTest {

    public AnstarTest() {
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
     * Test of getGeneratorMatrix method, of class Anstar.
     */
    @Test
    public void testGetGeneratorMatrix() {
        System.out.println("getGeneratorMatrix");
        Anstar instance = new AnstarAnGlued(4);
        Matrix result = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(result));
    }

    /**
     * Test of glue method, of class Anstar.
     */
    @Test
    public void testGlue() {
        System.out.println("glue");
        int i = 3, n = 6;
        double dn = 1.0/n;
        double[] glue = new double[n];
        Anstar.glue(i, glue);
        assertEquals(glue[0], i*(1.0 - dn), 0.000001);
        assertEquals(glue[1], i*(-dn), 0.000001);
    }
    
    /**
     * Test of kissing number method, of class Anstar.
     */
    @Test
    public void testKissingNumber() {
        System.out.println("kissing number");
        final int n = 8;
        Anstar lattice = new AnstarLinear(n);
        KissingNumber kissingnumber = new KissingNumber(lattice);
        assertEquals(kissingnumber.kissingNumber(),lattice.kissingNumber());
    }    

}