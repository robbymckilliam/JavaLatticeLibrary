/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.reduction;

import Jama.Matrix;
import static org.junit.Assert.*;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarSorted;

/**
 *
 * @author Robby McKilliam
 */
public class LLLTest {

    public LLLTest() {
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
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        assertNull(result);

    }
    
    @Test
    public void reduceReturnsNotNullIfMatrixNotNull() {
        System.out.println("reduceReturnsNotNullIfMatrixNotNull");
        Matrix B = Matrix.identity(10,10);
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        assertNotNull(result);
    }

    @Test
    public void reduceReturnsEqualSizeMatrix() {
        System.out.println("reduceReturnsEqualSizeMatrix");
        Matrix B = Matrix.identity(10,9);
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        assertEquals(result.getColumnDimension(), B.getColumnDimension());
        assertEquals(result.getRowDimension(), B.getRowDimension());
    }
    
    @Test
    public void reduceIsHermiteReduced() {
        System.out.println("reduceIsHermiteReduced");
        int n = 4;
        int m = 6;
        
        Matrix B = Matrix.random(m, n);
        System.out.println("B = " + VectorFunctions.print(B));
        
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        System.out.println(VectorFunctions.print(result));
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(result);
        Matrix R = QR.getR();
        System.out.println(VectorFunctions.print(R));
        for(int i = 0; i < m; i++){
            for(int j = i+1; j < n; j++){
                //this is the Hermite reduction criteria
                assertTrue( 2*Math.abs(R.get(i,j)) <= Math.abs(R.get(i, i)) );
            }
        }
    }
    
     @Test
    public void getUnimodularReturnsNullIfNoMatrix() {
        System.out.println("getUnimodularReturnsNullIfNoMatrix");
        Matrix B = null;
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        assertNull(instance.getUnimodularMatrix());
    }
    
    @Test
    public void getUnimodularReturnsNbyNMatrix() {
        System.out.println("getUnimodularReturnsNbyNMatrix");
        Matrix B = Matrix.identity(10,5);
        LLL instance = new LLL();
        instance.reduce(B);
        Matrix result = instance.getUnimodularMatrix();
        assertEquals(result.getColumnDimension(), B.getColumnDimension());
        assertEquals(result.getRowDimension(), B.getColumnDimension());
    }
    
    @Test
    public void reduceIsLovasReduced() {
        System.out.println("reduceIsLovasReduced");
        int m = 4;
        int n = 3;
        Matrix B = Matrix.random(m, n);
        System.out.println("rank = " + B.rank());
//        B = B.times(100);
//        for(int i = 0; i < B.getRowDimension(); i++){
//            for(int j = 0; j < B.getColumnDimension(); j++){
//                B.set(i, j, Math.round(B.get(i,j)));
//            }
//        }
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        System.out.println(VectorFunctions.print(result));
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(result);
        Matrix R = QR.getR();
        System.out.println(VectorFunctions.print(R));
        for(int j = 0; j < n-1; j++){
            //this is the Lovas reduction criteria
            System.out.println("j = " + j);
            assertTrue( R.get(j,j)*R.get(j,j) <= 2*R.get(j+1,j+1)*R.get(j+1,j+1) );
        }
        
    }
    
    @Test
    public void testUnimodularMatrix() {
        System.out.println("testUnimodularMatrix");
        int n = 3;
        int m = 4;
        
        
        Matrix B = Matrix.random(m, n);
        System.out.println("rank = " + B.rank());
//        B = B.times(100);
//        for(int i = 0; i < B.getRowDimension(); i++){
//            for(int j = 0; j < B.getColumnDimension(); j++){
//                B.set(i, j, Math.round(B.get(i,j)));
//            }
//        }
        LLL instance = new LLL();
        Matrix redB = instance.reduce(B);
        Matrix M = instance.getUnimodularMatrix();
        Matrix BM = B.times(M);
        System.out.println(VectorFunctions.print(B));
        System.out.println(VectorFunctions.print(redB));
        System.out.println(VectorFunctions.print(M));
        System.out.println(VectorFunctions.print(BM));
        assertTrue(redB.minus(BM).normF() < 0.0001);
        
    }
    
    @Test
    public void reducesAnStar() {
        System.out.println("reducesAnStar");
        int m = 4;
        int n = 3;  
        Anstar anstar = new AnstarSorted(n);

        Matrix B = anstar.getGeneratorMatrix();
        
        System.out.println(VectorFunctions.print(B));
        
        LLL instance = new LLL();
        Matrix result = instance.reduce(B);
        System.out.println(VectorFunctions.print(result));
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(result);
        Matrix R = QR.getR();
        System.out.println("R = " + VectorFunctions.print(R));
        for(int j = 0; j < n-1; j++){
            //this is the Lovas reduction criteria
            System.out.println("j = " + j);
            assertTrue( R.get(j,j)*R.get(j,j) <= 2*R.get(j+1,j+1)*R.get(j+1,j+1) );
        }
        
        System.out.println(VectorFunctions.print(instance.getUnimodularMatrix()));
        
    }
    
}