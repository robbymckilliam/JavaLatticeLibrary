/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.leech;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static pubsim.VectorFunctions.onesColumn;
import static pubsim.VectorFunctions.print;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class LeechTest {

    public LeechTest() {
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
     * Test of volume method, of class Leech.
     */
    @Test
    public void testVolume() {
        Leech lattice = new Leech();

        System.out.println(print(lattice.getGeneratorMatrix()));

        assertEquals(lattice.getGeneratorMatrix().det(), 1.0, 0.000000001);
        assertEquals(lattice.getGeneratorMatrix().det(), lattice.volume(), 0.000000001);
    }
    
     /**
     * Test of volume method, of class Leech.
     */
    @Test
    public void testObtuseBasis() {
        Leech lattice = new Leech();

        int N = 24, M = 24;
        
        Matrix Bs = lattice.getGeneratorMatrix();
        Matrix bnp1 = Bs.times(onesColumn(N));  //the extra vector
        
        //fill an extended obtuse superbasis matrix
        Matrix B = new Matrix(M,N+1);
        for(int n = 0; n < N; n++) 
            for(int m = 0; m < M; m++) B.set(m, n, B.get(m,n));
        for(int m = 0; m < M; m++) B.set(m, N, bnp1.get(m,0));
        
        //compute the extended Gram matrix (i.e. the Selling parameters)
        Matrix Q = B.transpose().times(B);
        
        System.out.println(print(Q));

        assertEquals(lattice.getGeneratorMatrix().det(), 1.0, 0.000000001);
        assertEquals(lattice.getGeneratorMatrix().det(), lattice.volume(), 0.000000001);
    }

}