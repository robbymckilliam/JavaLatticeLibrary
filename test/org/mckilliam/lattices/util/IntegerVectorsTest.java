package org.mckilliam.lattices.util;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class IntegerVectorsTest {
    
    public IntegerVectorsTest() {
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
    public void testIntegerVectors() {
        System.out.println("test the modified IntegerVectors class");
        for (int n = 1; n <= 4; n++) {
            for (int r = 2; r <= 5; r++) {
                IntegerVectors ivs = new IntegerVectors(n, r);
                int count = 0;
                while (ivs.hasMoreElements()) {
                    Matrix v = ivs.nextElement();
                    //System.out.println(VectorFunctions.print(v));
                    count++;
                }
                assertEquals((int) Math.pow(r, n), count);
            }
        }
    }
}
