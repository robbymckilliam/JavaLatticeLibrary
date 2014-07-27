/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anm;

import pubsim.lattices.Anstar.AnstarVaughan;
import pubsim.lattices.Anm.AnmGlued;
import pubsim.lattices.Anm.AnmSorted;
import Jama.Matrix;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class AnmGluedTest {

    public AnmGluedTest() {
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
     * Test of nearestPoint method, of class AnmGlued.
     */
    @Test
    public void nearestPoint() {
         int numTrials = 10000;
        int n = 100;
        int M = 25;
        Random rand = new Random();
        double[] y = new double[n];
        double[] v_instance = null;
        double[] v_tester = null;
        double[] x = new double[n];
        
        AnmGlued instance = new AnmGlued(n-1,M);
        AnmSorted tester = new AnmSorted(n-1,M);
        /*
        double[] y = {0.21, 0.211, 0.2111, 0.21111, 0.211111, 0.21112};
        
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            
            
            System.out.println(" test d = " + VectorFunctions.distance_between(y, v_tester));
            System.out.println(" inst d = " + VectorFunctions.distance_between(y, v_instance));
            System.out.println(" test u = " + VectorFunctions.print(tester.getIndex()));
            System.out.println(" inst u = " + VectorFunctions.print(instance.getIndex()));
            
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        */

        for(int i=0; i<numTrials; i++){
            for(int k = 0; k < n; k++){
                y[k] = rand.nextGaussian()*100.0;
            }
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            v_instance = instance.getLatticePoint();
            v_tester = tester.getLatticePoint();
            AnstarVaughan.project(y,x);
            
            
            /*
             System.out.println(" test d = " + VectorFunctions.distance_between(y, v_tester));
            System.out.println(" inst d = " + VectorFunctions.distance_between(y, v_instance));
            System.out.println(" test u = " + VectorFunctions.print(tester.getIndex()));
            System.out.println(" inst u = " + VectorFunctions.print(instance.getIndex()));
            */
            //System.out.println(" test mod = " + VectorFunctions.sum(tester.getIndex())%M);
            //System.out.println(" inst mod = " + VectorFunctions.sum(instance.getIndex())%M);
            
            
            assertEquals(VectorFunctions.distance_between(v_instance, v_tester) < 0.00001, true);
        }
    }

//    /**
//     * Test of inradius method, of class AnmGlued.
//     */
//    @Test
//    public void inradius() {
//        System.out.println("inradius");
//        AnmGlued instance = null;
//        double expResult = 0.0;
//        double result = instance.inradius();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeneratorMatrix method, of class AnmGlued.
//     */
//    @Test
//    public void getGeneratorMatrix() {
//        System.out.println("getGeneratorMatrix");
//        AnmGlued instance = null;
//        Matrix expResult = null;
//        Matrix result = instance.getGeneratorMatrix();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//   }

}