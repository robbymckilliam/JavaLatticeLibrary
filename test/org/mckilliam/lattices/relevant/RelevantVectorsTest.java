package org.mckilliam.lattices.relevant;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mckilliam.lattices.An.AnFastSelect;
import org.mckilliam.lattices.Dn;
import org.mckilliam.lattices.E8;
import org.mckilliam.lattices.cvp.LatticeAndClosestVector;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.Zn;
import org.mckilliam.lattices.util.IntegerVectors;
import org.mckilliam.lattices.util.PointEnumerator;
import pubsim.VectorFunctions;

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
        for(int n = 1; n <= 6; n++ ){
            System.out.println("test with integer lattice Z" + n);
            PointEnumerator rvs = new RelevantVectors(new Zn(n));
            int count = 0;
            while(rvs.hasMoreElements()) {
                Matrix v = rvs.nextElement();
                assertEquals( 1.0, v.normF(), 0.0001 ); //check that all relevant vectors are minimal, Zn is a root lattice
                //System.out.println(VectorFunctions.print(v));
                count++;
            }
            assertEquals(2*n,count);
        }
    }
    
    /**
     * Test with the root lattice An for n = 1,2,3,4,5,6
     */
    @Test
    public void testWithAn() {
        for(int n = 1; n <= 6; n++ ){
            System.out.println("test with A" + n);
            LatticeInterface an = new AnFastSelect(n);
            PointEnumerator rvs = new RelevantVectors(an);
            int count = 0;
            while(rvs.hasMoreElements()) {
                Matrix v = rvs.nextElement();
                assertEquals( Math.sqrt(an.norm()), v.normF(), 0.0001 ); //check that all relevant vectors are minimal, An is a root lattice
                //System.out.println(VectorFunctions.print(v));
                count++;   
            }
            assertEquals(an.kissingNumber(),count);
        }
    }
    
    /**
     * Test with the root lattice Dn for n = 2,2,3,4,5,6
     */
    @Test
    public void testWithDn() {
        for(int n = 2; n <= 4; n++ ){
            System.out.println("test with D" + n);
            LatticeInterface dn = new Dn(n);
            PointEnumerator rvs = new RelevantVectors(dn);
            int count = 0;
            while(rvs.hasMoreElements()) {
                Matrix v = rvs.nextElement();
                assertEquals( Math.sqrt(dn.norm()), v.normF(), 0.0001 ); //check that all relevant vectors are minimal, Dn is a root lattice
                System.out.println(VectorFunctions.print(v));
                count++;   
            }
            assertEquals(dn.kissingNumber(),count);
        }
    }
    
   /**
     * Test with the E8 lattice.
     */
    @Test
    public void testWithE8() {
            System.out.println("test with E8");
            LatticeInterface e8 = new E8();
            PointEnumerator rvs = new RelevantVectors(e8);
            int count = 0;
            while(rvs.hasMoreElements()) {
                Matrix v = rvs.nextElement();
                assertEquals( Math.sqrt(e8.norm()), v.normF(), 0.0001 ); //check that all relevant vectors are minimal, E8 is a root lattice
                //System.out.println(VectorFunctions.print(v));
                count++;   
            }
            assertEquals(e8.kissingNumber(),count);
    }

    
    /**
     * Test with random lattice.
     */
    @Test
    public void testWithRandomLattice() {
        System.out.println("test with random lattice");
        int m = 5, n = 3;
        Matrix B = Matrix.random(m, n);
        PointEnumerator rvs = new RelevantVectors(new LatticeAndClosestVector(B));
        while(rvs.hasMoreElements()) {
            Matrix v = rvs.nextElement();
            //we will generate a whole bunch of lattice points and assert that each relevant vector satisies
            //the require inequality
            IntegerVectors vec = new IntegerVectors(n, 6);
            vec.nextElement(); //ditch the first vector that is all zeros.
            while( vec.hasMoreElements() ){
                Matrix x = B.times(vec.nextElement());
                assertTrue( (v.transpose().times(x)).get(0,0) <= (x.transpose().times(x)).get(0,0) );
            }
            //System.out.println(VectorFunctions.print(rvs.nextElement()));           
        }
    }
    
}
