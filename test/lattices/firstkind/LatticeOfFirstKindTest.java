package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.distributions.Uniform;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.Lattice;

/**
 *
 * @author Robby McKilliam
 */
public class LatticeOfFirstKindTest {
    
    public LatticeOfFirstKindTest() {
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
     * Test of superbasis method, of class LatticeOfFirstKind.
     */
    @Test
    public void testSuperbasisWithAn() {
        System.out.println("superbasis with An");
        AnFastSelect anlattice = new AnFastSelect(5);
        LatticeOfFirstKind instance = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        //System.out.println(VectorFunctions.print(instance.superbasis()));
        //System.out.println(VectorFunctions.print(instance.extendedGram()));
    }
    
     /**
     * Test of superbasis method, of class LatticeOfFirstKind.
     */
    @Test
    public void testSuperbasisWithAnStar() {
        System.out.println("superbasis with An*");
        Anstar anlattice = new AnstarLinear(5);
        LatticeOfFirstKind instance = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
        //System.out.println(VectorFunctions.print(instance.superbasis()));
        //System.out.println(VectorFunctions.print(instance.extendedGram()));
    }
    
//     /**
//     * Test of construct from extended Gram matrix
//     */
//    @Test
//    public void testconstructfromextededGramAn() {
//        System.out.println("construct from extended Gram matrix with An");
//        AnFastSelect anlattice = new AnFastSelect(5);
//        LatticeOfFirstKind extGram = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
//        LatticeOfFirstKind instance = LatticeOfFirstKind.constructFromExtendedGram(extGram.extendedGram());
//        assertEquals(anlattice.kissingNumber(), instance.kissingNumber());
//        assertEquals(anlattice.norm(), instance.norm(), 0.00001);
//        assertEquals(anlattice.volume(), instance.volume(), 0.00001);
//        assertEquals(anlattice.packingDensity(), instance.packingDensity(), 0.00001);
//        assertEquals(anlattice.centerDensity(), instance.centerDensity(), 0.00001);
//    }
//    
//     /**
//     * Test of construct from extended Gram matrix
//     */
//    @Test
//    public void testconstructfromextededGramAnstar() {
//        System.out.println("construct from extended Gram matrix with An*");
//        Anstar anlattice = new AnstarLinear(6);
//        LatticeOfFirstKind extGram = new LatticeOfFirstKind(anlattice.getGeneratorMatrix());
//        LatticeOfFirstKind instance = LatticeOfFirstKind.constructFromExtendedGram(extGram.extendedGram());
//        assertEquals(anlattice.kissingNumber(), instance.kissingNumber());
//        assertEquals(anlattice.norm(), instance.norm(), 0.00001);
//        assertEquals(anlattice.volume(), instance.volume(), 0.00001);
//        assertEquals(anlattice.packingDensity(), instance.packingDensity(), 0.00001);
//        assertEquals(anlattice.centerDensity(), instance.centerDensity(), 0.00001);
//        assertEquals(anlattice.logCenterDensity(), instance.logCenterDensity(), 0.00001);
//    }
//    
//     /**
//     * Test of construct random first kind lattice
//     */
//    @Test
//    public void testconstructrandom() {
//        System.out.println("construct random");
//        for(int n = 1; n <= 24; n++){
//            LatticeOfFirstKind lattice = LatticeOfFirstKind.randomLatticeOfFirstKind(n, Uniform.constructFromMeanAndRange(-0.5,1));
//            //System.out.println(VectorFunctions.print(lattice.extendedGram()));
//        }
//    }
// 
//    /**
//     * Test of construct random first kind lattice
//     */
//    @Test
//    public void testconstructnorm() {
//        System.out.println("test norm");
//        for(int n = 2; n <= 24; n++){
//            LatticeOfFirstKind lattice = LatticeOfFirstKind.randomLatticeOfFirstKind(n, Uniform.constructFromMeanAndRange(-0.5,1));
//            Lattice blat = new Lattice(lattice.getGeneratorMatrix());
//            double sdnorm = blat.norm();
//            double mincutnorm = lattice.norm();
//            //System.out.println(n + ", " + sdnorm + ", " + mincutnorm + ", " + lattice.getGeneratorMatrix().cond());
//            //System.out.println(VectorFunctions.print(lattice.extendedGram()));
//            assertEquals(sdnorm,mincutnorm,0.00001);
//        }
//    }
    
    /** Test extended Gram generator */
    @Test
    public void testExtendedGramGenerator() {
        System.out.println("test random extended Gram matrix");
        double tol = 1e-8;
         for(int n = 2; n <= 5; n++){
            Matrix Q = LatticeOfFirstKind.extendGramMatrix(n,Uniform.constructFromMeanAndRange(-0.5,1));
            //assert symmetric
            assertTrue( Q.transpose().minus(Q).normF() < tol);
            //test row sums are zero
            Matrix ones = VectorFunctions.onesColumn(n+1);
            assertTrue( Q.times(ones).normF() < tol);
            //test column sums are zero
            assertTrue( ones.transpose().times(Q).normF() < tol);
         }
    }
    
    
    /** Test Selling's formula */
    @Test
    public void testSellingsFormula() {
        System.out.println("test Sellings formula");
        for(int n = 2; n <= 20; n++){
            Matrix Q = LatticeOfFirstKind.extendGramMatrix(n,Uniform.constructFromMeanAndRange(-0.5,1));
            double[] x = new double[n+1];
            Random rand = new Random();
            for(int i = 0; i <= n; i++) x[i] = rand.nextGaussian(); //random x
           
            //compute quadratic form by Selling's formula
            double sellings = 0.0;
            for(int i = 0; i <= n; i++)
                for(int j = i+1; j <= n; j++) sellings -= Q.get(i,j)*(x[i] - x[j])*(x[i] - x[j]);
            
            //compute quadratic form directly
            Matrix xm = VectorFunctions.columnMatrix(x);
            Matrix qf = xm.transpose().times(Q).times(xm);
            //check qf is scalar
            assertTrue(qf.getColumnDimension()==1); 
            assertTrue(qf.getRowDimension()==1);
            double direct = qf.get(0,0);
            
            assertEquals(direct,sellings, 0.0000001);
            
        }
    }
    
    /** Test reviewers dot product formula */
    @Test
    public void testReviewersFormula() {
        System.out.println("test reviewers dot product formula");
        for(int n = 2; n <= 20; n++){
            Matrix Q = LatticeOfFirstKind.extendGramMatrix(n,Uniform.constructFromMeanAndRange(-0.5,1));
            double[] x = new double[n+1];
            double[] y = new double[n+1];
            Random rand = new Random();
            for(int i = 0; i <= n; i++) {
                x[i] = rand.nextGaussian();
                y[i] = rand.nextGaussian();
            } 
           
            //compute quadratic form by reviewer's formula
            double reviewers = 0.0;
            for(int i = 0; i <= n; i++)
                for(int j = i+1; j <= n; j++) reviewers -= Q.get(i,j)*(x[i] - x[j])*(y[i] - y[j]);
            
            //compute quadratic form directly
            Matrix xm = VectorFunctions.columnMatrix(x);
            Matrix ym = VectorFunctions.columnMatrix(y);
            Matrix qf = xm.transpose().times(Q).times(ym);
            //check qf is scalar
            assertTrue(qf.getColumnDimension()==1); 
            assertTrue(qf.getRowDimension()==1);
            double direct = qf.get(0,0);
            
            assertEquals(direct,reviewers, 0.0000001);
            
        }
    }

}