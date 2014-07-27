/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.util.region;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static pubsim.VectorFunctions.matrixMultVector;
import static pubsim.VectorFunctions.add;

/**
 *
 * @author Robby McKilliam
 */
public class ParallelepipedTest {

    public ParallelepipedTest() {
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
     * Test of within method, of class Parallelepiped.
     */
    @Test
    public void testWithin() {
        System.out.println("within");
        int m = 6;
        int n  = 4;
        Matrix M = Matrix.random(m, n);
        double[] p1 = {0.5, 0.5, 0.5, 0.5};
        double[] y = matrixMultVector(M, p1);
        Parallelepiped R = new Parallelepiped(M);
        assertTrue(R.within(y));

        double[] p2 = {1.5, 0.5, 0.5, 0.5};
        y = matrixMultVector(M, p2);
        assertFalse(R.within(y));

        double[] t = {1.0,2.0,3.0,4.0,5.0,6.0};
        R = new Parallelepiped(M, t);
        y = add(matrixMultVector(M, p1), t);
        assertTrue(R.within(y));

        R = new Parallelepiped(M, t);
        y = add(matrixMultVector(M, p2), t);
        assertFalse(R.within(y));

    }

        /**
     * Test of within method, of class Parallelepiped.
     */
    @Test
    public void testBoundingBox() {
        System.out.println("BoundingBox");
        int M = 4;
        int N  = 4;
        Matrix Mat = Matrix.identity(M, N);
        Parallelepiped R = new Parallelepiped(Mat);
        for(int m = 0; m < M; m++){
            assertEquals(1.0, R.maxInCoordinate(m), 0.000000001);
            assertEquals(0.0, R.minInCoordinate(m), 0.000000001);
        }

        M = 6;
        N  = 4;
        Mat = Matrix.identity(M, N);
        R = new Parallelepiped(Mat);
        for(int m = 0; m < N; m++){
            assertEquals(1.0, R.maxInCoordinate(m), 0.000000001);
            assertEquals(0.0, R.minInCoordinate(m), 0.000000001);
        }
        for(int m = N; m < M; m++){
            assertEquals(0.0, R.maxInCoordinate(m), 0.000000001);
            assertEquals(0.0, R.minInCoordinate(m), 0.000000001);
        }

        M = 6;
        N  = 4;
        Mat = Matrix.identity(M, N);
        Mat.set(5, 3, -3.0);
        Mat.set(4, 1, -3.0);
        R = new Parallelepiped(Mat);
        for(int m = 0; m < N; m++){
            assertEquals(1.0, R.maxInCoordinate(m), 0.000000001);
            assertEquals(0.0, R.minInCoordinate(m), 0.000000001);
        }
        for(int m = N; m < M; m++){
            assertEquals(0.0, R.maxInCoordinate(m), 0.000000001);
            assertEquals(-3.0, R.minInCoordinate(m), 0.000000001);
        }

    }

}