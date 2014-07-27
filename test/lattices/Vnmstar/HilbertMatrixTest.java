/*
 */
package pubsim.lattices.Vnmstar;

import pubsim.lattices.Vnmstar.HilbertMatrix;
import bignums.BigInteger;
import bignums.BigRational;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubsim.lattices.Vnmstar.VnmStar;
import Jama.Matrix;

/**
 *
 * @author RobbyMcKilliam
 */
public class HilbertMatrixTest {

    public HilbertMatrixTest() {
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
     * Test of q method, of class HilbertMatrix.
     */
    @Test
    public void testq() {
        System.out.println("q");
        int M = 11;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(M,N);
        assertTrue(inst.q(0, 0).compareTo(BigRational.ONE) == 0);
        assertTrue(inst.q(0, 10).compareTo(BigRational.ZERO) == 0);
        assertTrue(inst.q(0, -1).compareTo(BigRational.ZERO) == 0);
        assertTrue(inst.q(7, 1).compareTo(new BigRational(363, 140)) == 0);
        assertTrue(inst.q(4, 1).compareTo(new BigRational(-25, 12)) == 0);
        assertTrue(inst.q(4, 2).compareTo(new BigRational(35, 24)) == 0);
        assertTrue(inst.q(12, 3).compareTo(new BigRational(-5356117, 1814400)) == 0);
    }
    
    /**
     * Test of binom method, of class HilbertMatrix.
     */
    @Test
    public void testbinom() {
        System.out.println("binom");
        assertTrue(HilbertMatrix.binom(1, 0).compareTo(BigInteger.ONE) == 0);
        assertTrue(HilbertMatrix.binom(2, 0).compareTo(BigInteger.ONE) == 0);
        assertTrue(HilbertMatrix.binom(2, 1).compareTo(new BigInteger("2")) == 0);
        assertTrue(HilbertMatrix.binom(3, 1).compareTo(new BigInteger("3")) == 0);
        assertTrue(HilbertMatrix.binom(7, 3).compareTo(new BigInteger("35")) == 0);
    }
    
    /**
     * Test of P method, of class HilbertMatrix.
     */
    @Test
    public void testP() {
        System.out.println("P");
        int M = 11;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(M,N);
        assertTrue(inst.P(0, 0).compareTo(BigRational.ONE) == 0);
        assertTrue(inst.P(0, 1).compareTo(BigRational.ZERO) == 0);
        assertTrue(inst.P(1, 1).compareTo(new BigRational(2,1)) == 0);
        assertTrue(inst.P(2, 2).compareTo(new BigRational(3,1)) == 0);
        assertTrue(inst.P(1, 0).compareTo(new BigRational(-11,1)) == 0);
        assertTrue(inst.P(2, 0).compareTo(new BigRational(66,1)) == 0);
        assertTrue(inst.P(4, 3).compareTo(new BigRational(-385,6)) == 0);
        assertTrue(inst.P(5, 2).compareTo(new BigRational(-10395,4)) == 0);
    }
    
     /**
     * Test of HinverseDouble method, of class HilbertMatrix.
     */
    @Test
    public void testHinverse2() {
        System.out.println("Hinverse of size 2");
        double tol = 1e-6;
        int m = 2;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(m+1,N);
        Matrix M = VnmStar.getMMatrix(m, N-m-1);
        Matrix inv = (M.transpose().times(M)).inverse();
        //inv.print(3,4);
        Matrix test = inst.HinverseDouble();
        //test.print(3,4);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j <= m; j++)
                assertEquals(inv.get(i,j), test.get(i, j), tol);
    }
    
    /**
     * Test of HinverseDouble method, of class HilbertMatrix.
     */
    @Test
    public void testHinverse3() {
        System.out.println("Hinverse of size 3");
        double tol = 1e-6;
        int m = 3;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(m+1,N);
        Matrix M = VnmStar.getMMatrix(m, N-m-1);
        Matrix inv = (M.transpose().times(M)).inverse();
        //inv.print(3,4);
        Matrix test = inst.HinverseDouble();
        //test.print(3,4);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j <= m; j++)
                assertEquals(inv.get(i,j), test.get(i, j), tol);
    }
    
    /**
     * Test of HinverseDouble method, of class HilbertMatrix.
     */
    @Test
    public void testHinverse4() {
        System.out.println("Hinverse of size 4");
        double tol = 1e-6;
        int m = 4;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(m+1,N);
        Matrix M = VnmStar.getMMatrix(m, N-m-1);
        Matrix inv = (M.transpose().times(M)).inverse();
        //inv.print(3,4);
        Matrix test = inst.HinverseDouble();
        //test.print(3,4);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j <= m; j++)
                assertEquals(inv.get(i,j), test.get(i, j), tol);
    }
    
    /**
     * Test of HinverseDouble method, of class HilbertMatrix.
     */
    @Test
    public void testKProjection() {
        System.out.println("Test the K matrix");
        double tol = 1e-6;
        int m = 4;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(m+1,N);
        Matrix M = VnmStar.getMMatrix(m, N-m-1);
        Matrix Mt = M.transpose();
        Matrix Kexp = (Mt.times(M)).inverse().times(Mt);
        //Kexp.print(4,4);
        Matrix test = inst.KDouble();
        //test.print(4,4);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j < N; j++)
                assertEquals(Kexp.get(i,j), test.get(i, j), tol);
    }
    
    /**
     * Test of HinverseDouble method, of class HilbertMatrix.
     */
    @Test
    public void testVnmStarGenerator() {
        System.out.println("Test the VnmStar generator matrix");
        double tol = 1e-6;
        int m = 4;
        int N = 10;
        HilbertMatrix inst = new HilbertMatrix(m+1,N);
        Matrix M = VnmStar.getMMatrix(m, N-m-1);
        Matrix Mt = M.transpose();
        Matrix Gexp = Matrix.identity(N, N).minus(M.times((Mt.times(M)).inverse().times(Mt)));
        //Gexp.print(4,4);
        Matrix test = inst.VnmStarGeneratorDouble();
        //test.print(4,4);
        for(int i = 0; i <= m; i++)
            for(int j = 0; j < N; j++)
                assertEquals(Gexp.get(i,j), test.get(i, j), tol);
    }
    
}
