/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.reduction;

import pubsim.lattices.reduction.Hermite;
import Jama.Matrix;
import Jama.QRDecomposition;
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
public class HermiteTest {

    public HermiteTest() {
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

    @Test
    public void reduceReturnsNullIfNoMatrixNull() {
        System.out.println("reduceReturnsNullIfNoMatrixNull");
        Matrix B = null;
        Hermite instance = new Hermite();
        Matrix result = instance.reduce(B);
        assertNull(result);

    }
    
    @Test
    public void getUnimodularReturnsNullIfNoMatrix() {
        System.out.println("getUnimodularReturnsNullIfNoMatrix");
        Matrix B = null;
        Hermite instance = new Hermite();
        Matrix result = instance.reduce(B);
        assertNull(instance.getUnimodularMatrix());
    }
    
    @Test
    public void getUnimodularReturnsNbyNMatrix() {
        System.out.println("getUnimodularReturnsNbyNMatrix");
        Matrix B = new Matrix(10,5);
        Hermite instance = new Hermite();
        instance.reduce(B);
        Matrix result = instance.getUnimodularMatrix();
        assertEquals(result.getColumnDimension(), B.getColumnDimension());
        assertEquals(result.getRowDimension(), B.getColumnDimension());
    }
    
    @Test
    public void reduceReturnsNotNullIfMatrixNotNull() {
        System.out.println("reduceReturnsNotNullIfMatrixNotNull");
        Matrix B = new Matrix(10,10);
        Hermite instance = new Hermite();
        Matrix result = instance.reduce(B);
        assertNotNull(result);
    }

    @Test
    public void reduceReturnsEqualSizeMatrix() {
        System.out.println("reduceReturnsEqualSizeMatrix");
        Matrix B = Matrix.random(10, 8);
        Hermite instance = new Hermite();
        Matrix result = instance.reduce(B);
        //System.out.println(VectorFunctions.print(result));
        assertEquals(result.getColumnDimension(), B.getColumnDimension());
        assertEquals(result.getRowDimension(), B.getRowDimension());
    }
    
    @Test
    public void reduceIsHermiteReduced() {
        System.out.println("reduceIsHermiteReduced");
        int n = 10;
        Matrix B = Matrix.random(12, n);
        Hermite instance = new Hermite();
        Matrix result = instance.reduce(B);
        Jama.QRDecomposition QR = new QRDecomposition(result);
        Matrix R = QR.getR();
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                //this is the Hermite reduction criteria
                assertTrue( 2*Math.abs(R.get(i,j)) <= Math.abs(R.get(i, i)) );
            }
        }
    }
    
    @Test
    public void testUnimodularMatrix() {
        System.out.println("testUnimodularMatrix");
        int n = 5;
        Matrix B = Matrix.random(7, n);
        Hermite instance = new Hermite();
        Matrix redB = instance.reduce(B);
        Matrix M = instance.getUnimodularMatrix();
        Matrix BM = B.times(M);
        Matrix redBM = redB.times(M);
        System.out.println(VectorFunctions.print(B));
        System.out.println(VectorFunctions.print(redB));
        System.out.println(VectorFunctions.print(M));
        System.out.println(VectorFunctions.print(BM));
        System.out.println(VectorFunctions.print(redBM));
        assertTrue(redB.minus(BM).normF() < 0.0001);
    }

}