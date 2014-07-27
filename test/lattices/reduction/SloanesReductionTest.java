/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.reduction;

import pubsim.lattices.reduction.LLL;
import pubsim.lattices.reduction.SloanesReduction;
import Jama.Matrix;
import pubsim.distributions.Gaussian;
import pubsim.lattices.leech.Leech;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class SloanesReductionTest {

    public SloanesReductionTest() {
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
    public void testUpperTriangularBasis() {
        System.out.println("test upper triangular basis");
        int n = 4;
        Matrix B = Matrix.random(n, n);
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        System.out.println(VectorFunctions.print(L));
    }

    @Test
    public void testLwMatrix() {
        System.out.println("test Lw matrix");
        int n = 4;
        Matrix B = Matrix.random(n, n);
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 10);
        System.out.println(VectorFunctions.print(Lw));
    }

    @Test
    public void testSpecialColReduce() {
        System.out.println("test special column reduce");
        int n = 4;
        Matrix B = Matrix.random(n, n);
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 5);
        Matrix Lt = SloanesReduction.specialColumnReduce(Lw);
        System.out.println(VectorFunctions.print(Lt));
    }

    @Test
    public void testGetProjectionVector() {
        System.out.println("test get projection vector");
        int n = 24;
        Matrix B = VectorFunctions.randomMatrix(n, n, new Gaussian(0, 1.0));
        Matrix L = SloanesReduction.upperTriangularBasis(B);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 1);
        Matrix Lt = SloanesReduction.specialColumnReduce(Lw);
        System.out.println(VectorFunctions.print(Lw));

        double[] v = new SloanesReduction(L,1).getProjectionVector();
        System.out.println(VectorFunctions.print(v));

    }

    @Test
    public void testOnLeechLattice() {
        System.out.println("test on Leech lattice");
        Matrix M = new Leech().getGeneratorMatrix().times(Math.sqrt(8.0));
        System.out.println(VectorFunctions.print(M));
        Matrix L = SloanesReduction.upperTriangularBasis(M);
        Matrix Lw = SloanesReduction.computeSlonesLw(L, 1);
        Matrix Lt = SloanesReduction.specialColumnReduce(Lw);
        System.out.println(VectorFunctions.print(Lw));
        double[] v = new SloanesReduction(M,1).getProjectionVector();
        System.out.println(VectorFunctions.print(v));

    }

    /**
     * Doesn't actually output a histogram yet, just mean and
     * variance, there is no reason you can't do this though.
     */
    @Test
    public void histogramRandomLattice() {
        System.out.println("histogram on random on lattice");
        int n = 8, iters = 10000;
        
        double mean = 0, var = 0;
        for(int i = 0; i < iters; i++){
            Matrix B = VectorFunctions.randomMatrix(n, n, new Gaussian(0, 1.0));
            Matrix L = new LLL().reduce(B); //LLL seems to make B smaller.
            L = SloanesReduction.upperTriangularBasis(L);
            //Matrix L = SloanesReduction.upperTriangularBasis(B);
            //System.out.println(VectorFunctions.print(Lw));
            double[] v = new SloanesReduction(L,2).getProjectionVector();
            double lv = Math.abs(v[n-1]);
            //grab the element of v with maximum magnitude, damn scala would be nicer here!
            for(int k = 0; k < n; k++) {
                double vabs = Math.abs(v[k]);
                if(lv < vabs) lv = vabs;
            }
            mean += lv; var += lv*lv;
        }
        mean /= iters;
        //var = (var - 2*mean* + iters*mean*mean)/(iters - 1);
        System.out.println("mean = " + mean);
    }


}