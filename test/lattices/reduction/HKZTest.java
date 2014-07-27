/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.reduction;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vaughan Clarkson
 * @author Robby McKilliam
 */
public class HKZTest {

    public HKZTest() {
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

    @Test
    public void testSomeMethod() {
        double tol = 1e-10;
        int dim = 5;
        Matrix B = Matrix.random(dim, dim);
        //System.out.println("B = ");
        //B.print(8, 2);
        HKZ hkz = new HKZ();
        Matrix Bred = hkz.reduce(B);
        //System.out.println("Bred = ");
        //Bred.print(8, 2);
        Matrix Rred = hkz.getR();
        //System.out.println("Rred = ");
        //Rred.print(8, 2);
        //System.out.println("Mred = ");
        //hkz.getUnimodularMatrix().print(8, 2);
        hkz.reduce(Bred);
        Matrix Rredred = hkz.getR();
        double sqtr = 0.0;
        for (int j = 0; j < dim; j++) {
            double dd = Rred.get(j, j) - Rredred.get(j, j);
            sqtr += dd * dd;
        }
        assertEquals(sqtr,0.0,tol);
    }
}
