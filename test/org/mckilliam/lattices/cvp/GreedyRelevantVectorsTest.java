package org.mckilliam.lattices.cvp;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mckilliam.distributions.Uniform;
import org.mckilliam.lattices.An.AnFastSelect;
import org.mckilliam.lattices.E8;
import org.mckilliam.lattices.Lattice;
import org.mckilliam.lattices.LatticeAndClosestVector;
import org.mckilliam.lattices.LatticeAndClosestVectorInterface;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.ScaledLattice;
import org.mckilliam.lattices.Zn;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class GreedyRelevantVectorsTest {
    
    public GreedyRelevantVectorsTest() {
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
    public void sameAsSphereDecoder() {
        System.out.println("sameAsSphereDecoder");

        int iters = 10;
        int n = 7;
        int m = 9;
        double[] origin = new double[m];

        LatticeInterface lattice = new Lattice(Matrix.random(m, n));

        SphereDecoder sdSE = new SphereDecoderSchnorrEuchner(lattice);
        GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);

        for(int t = 0; t < iters; t++){
            double[] y = VectorFunctions.randomGaussian(m, 0.0, 20.0);
            //test with default Babai starting point
            double decdist = VectorFunctions.distance_between2(sdSE.closestPoint(y), grv.closestPoint(y));
            assertTrue(decdist <= 0.000001);
            //test with origin for starting point
            decdist = VectorFunctions.distance_between2(sdSE.closestPoint(y), grv.closestPoint(y,origin));
            assertTrue(decdist <= 0.000001);
        }
        
    }

    /**
     * Test of closestRelevantVector method with Zn lattice
     */
    @Test
    public void testClosestRelevantVectorWithZn() {
        System.out.println("test closestRelevantVector with Zn");
        int n = 3;
        LatticeInterface lattice = new Zn(n);
        GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);
        double[] ya = {0.6,0,0};
        Matrix em = new Matrix(n,1,0); em.set(0,0,1.0); //expect relevant vector (1,0,0)
        Matrix rm = grv.closestRelevantVector(ya);
        assertTrue(em.minus(rm).normF() <= 0.000001);
        
        double[] yb = {0.2,-1.6,0.3};
        em = new Matrix(n,1,0); em.set(1,0,-1.0); //expect relevant vector (0,-1,0)
        rm = grv.closestRelevantVector(yb);
        assertTrue(em.minus(rm).normF() <= 0.000001);
        
        double[] yc = {-0.16,0.2,0.3};
        em = new Matrix(n,1,0); //expect relevant vector (0,0,0)
        rm = grv.closestRelevantVector(yc);
        assertTrue(em.minus(rm).normF() <= 0.000001);
    }
    
    /**
     * Test of closestRelevantVector method with An lattice.
     */
    @Test
    public void testClosestRelevantVectorWithAn() {
        System.out.println("test closestRelevantVector with An");
        int n = 2;
        LatticeInterface lattice = new AnFastSelect(n);
        GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);
        
        double[] ya = {0.6,-0.6,0};
        Matrix em = new Matrix(n+1,1,0); em.set(0,0,1.0); em.set(1,0,-1.0); //expect relevant vector (1,-1,0)
        Matrix rm = grv.closestRelevantVector(ya);
        assertTrue(em.minus(rm).normF() <= 0.000001);
                
        double[] yc = {-0.1,0.1,0.02};
        em = new Matrix(n+1,1,0); //expect relevant vector (0,0,0)
        rm = grv.closestRelevantVector(yc);
        assertTrue(em.minus(rm).normF() <= 0.000001);
    }
    
    @Test
    public void iterationStats(){
        System.out.println("statistics about thenumber of iterations when starting within twice the Voronoi cell");
        
        int iters = 10;
        
        int n = 8;
        int m = n; 
        double[] origin = new double[m];
        
        for (int itr = 0; itr < iters; itr++) {

//          LatticeAndClosestVectorInterface lattice = new LatticeAndClosestVector(B); //the lattice of interest
            LatticeAndClosestVectorInterface lattice = new Zn(n);
            Matrix B = lattice.generatorMatrix();
            //Matrix B = Matrix.random(m, n); //random generator matrix
            //Matrix B = Matrix.identity(m, m); //try with interger lattice
            LatticeAndClosestVectorInterface latticeX2 = new ScaledLattice(lattice,2); //sublattice with generator mulitplied by 2

            //System.out.println(VectorFunctions.print(B));
            //System.out.println(VectorFunctions.print(latticeX2.generatorMatrix()));

            Uniform u02 = Uniform.constructFromMinMax(0, 2);
            double[] u = new double[m];
            for (int i = 0; i < m; i++) u[i] = u02.noise();
            double[] intwicefundppd = pubsim.VectorFunctions.matrixMultVector(B, u); //vector uniformly distributed in twice the fundamental parrallelipided
            //System.out.println(VectorFunctions.print(intwicefundppd));
            double[] x = latticeX2.closestPoint(intwicefundppd);
            //System.out.println(VectorFunctions.print(x));
            double[] y = new double[m];
            for (int i = 0; i < m; i++) y[i] = intwicefundppd[i] - x[i]; //vector y is uniformly distributed in twice the Voronoi cell.
            //System.out.println(VectorFunctions.print(y));
            GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);
            grv.closestPoint(y, origin);
            long numiterations = grv.numberOfIterations();
            System.out.println(numiterations);
        }
        
    }
    
    
}
