package org.mckilliam.lattices.relevant;

import org.mckilliam.lattices.relevant.RelevantVectors;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import org.mckilliam.lattices.An.AnFastSelect;
import org.mckilliam.lattices.LatticeAndNearestPointAlgorithm;
import org.mckilliam.lattices.Zn;
import org.mckilliam.lattices.util.PointEnumerator;

/**
 *
 * @author Robby McKilliam
 */
public class RelevantVectorsTest {
    
    public RelevantVectorsTest() {
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
     * Test with the integer lattice.
     */
    @Test
    public void testWithIntegerLattice() {
        System.out.println("test with integer lattice Z2");
        PointEnumerator rvs = new RelevantVectors(new Zn(2));
        while(rvs.hasMoreElements())
            System.out.println(VectorFunctions.print(rvs.nextElement()));
            
    }
    
    /**
     * Test with the integer lattice.
     */
    @Test
    public void testWithA2() {
        System.out.println("test with A2");
        PointEnumerator rvs = new RelevantVectors(new AnFastSelect(2));
        while(rvs.hasMoreElements())
            System.out.println(VectorFunctions.print(rvs.nextElement()));            
    }
    
    /**
     * Test with the integer lattice.
     */
    @Test
    public void testWithA3() {
        System.out.println("test with A3");
        PointEnumerator rvs = new RelevantVectors(new AnFastSelect(3));
        while(rvs.hasMoreElements())
            System.out.println(VectorFunctions.print(rvs.nextElement()));            
    }

    
    /**
     * Test with the randomlattice.
     */
    @Test
    public void testWithRandomLattice() {
        System.out.println("test with random lattice");
        Matrix B = Matrix.random(3, 2);
        PointEnumerator rvs = new RelevantVectors(new LatticeAndNearestPointAlgorithm(B));
        while(rvs.hasMoreElements())
            System.out.println(VectorFunctions.print(rvs.nextElement()));            
    }
    
}
