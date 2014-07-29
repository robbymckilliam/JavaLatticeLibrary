/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckilliam.lattices.Anm;

import org.mckilliam.lattices.Anm.AnmSorted;
import Jama.Matrix;
import junit.framework.TestCase;
import pubsim.VectorFunctions;
import pubsim.VectorFunctionsTest;
import org.mckilliam.lattices.LatticeAndClosestVector;
import org.mckilliam.lattices.LatticeAndClosestVectorInterface;

/**
 *
 * @author Robby McKilliam
 */
public class AnmTest extends TestCase {
    
    public AnmTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
        /**
     * Test of closestPoint method, of class Anm.
     */
    public void testGeneratorMatrix() {
        System.out.println("testGeneratorMatrix");
        int m = 2;
        int n = 13;
        LatticeAndClosestVectorInterface anm = new AnmSorted(n,m);
        Matrix Mat = anm.generatorMatrix();
        System.out.println(VectorFunctions.print(Mat));

        double det = Mat.transpose().times(Mat).det();
        System.out.println(Math.sqrt(det));
        System.out.println(anm.volume());


        double[] r = VectorFunctions.randomGaussian(n+1, 100, 1000);
        LatticeAndClosestVector sd
                = new LatticeAndClosestVector(Mat);

        sd.closestPoint(r);
        anm.closestPoint(r);

        VectorFunctionsTest.assertVectorsEqual(sd.getLatticePoint(), anm.getLatticePoint());
        
//        System.out.println(VectorFunctions.print(sd.getLatticePoint()));
//        System.out.println(VectorFunctions.print(anm.getLatticePoint()));
//        System.out.println(VectorFunctions.print(r));


    }
    
    

}
