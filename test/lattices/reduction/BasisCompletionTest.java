/*
 */
package pubsim.lattices.reduction;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.lattices.Lattice;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;

/**
 *
 * @author Robby McKilliam
 */
public class BasisCompletionTest {

    public BasisCompletionTest() {
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
     * Test of notDone method, of class BasisCompletion.
     */
    @Test
    public void testWorking() {
        double tol = 1e-7;
	int dim = 5;
	Matrix B = Matrix.random(dim, dim);
	ShortVectorSphereDecoded svsd
	    = new ShortVectorSphereDecoded(new Lattice(B));
	BasisCompletion cb = new BasisCompletion();
	Matrix sv = VectorFunctions.columnMatrix(svsd.getShortestVector());
	//System.out.println("final B = ");
	cb.completeBasis(sv, B);
	//System.out.println("final M = ");
	cb.getUnimodularMatrix();
	//System.out.println("det M = " + cb.getUnimodularMatrix().det());
        double error = B.times(cb.getUnimodularMatrix().getMatrix(0, dim-1, 0, 0)).minus(sv).norm2();
        assertEquals(error, 0.0, tol);
    }
}
