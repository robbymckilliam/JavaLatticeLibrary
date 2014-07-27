package pubsim.lattices.decoder;

import Jama.Matrix;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.AnstarSorted;
import pubsim.lattices.Lattice;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.Zn;
import pubsim.lattices.reduction.HKZ;
import pubsim.lattices.reduction.LLL;

/**
 *
 * @author Robby McKilliam
 */
public class BabaiTest {

    public BabaiTest() {
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
    public void returnsErrorWhenDimensionAreWrong() {
        System.out.println("nearestPoint");
        double[] y = {1, 2, 3, 4};
        Lattice lattice = new Lattice(Matrix.random(6, 5));
        Babai instance = new Babai(lattice);
        
        boolean caught = false;
        try{
            instance.nearestPoint(y);
        } catch(RuntimeException e){
            caught = true;
        }
        
        assertTrue(caught);
        
    }
    
    /** 
     * Babai's algorithm should work perfectly for Zn.
     * This tests that it does.
     */
    @Test
    public void returnsCorrectForZn() {
        System.out.println("returnsCorrectForZn");
        double[] y = {1.1, 2.2, 3.9, -4.1, -100.49};
        //construc the integer lattice
        Zn lattice = new Zn(y.length);
        
        Babai babai = new Babai(lattice);
        
        lattice.nearestPoint(y);
        double[] xtrue = lattice.getLatticePoint();
        double[] utrue = lattice.getIndex();
        
        babai.nearestPoint(y);
        double[] xtest = babai.getLatticePoint();
        double[] utest = babai.getIndex();
        
        
        System.out.println(VectorFunctions.print(xtrue));
        System.out.println(VectorFunctions.print(xtest));
        
        assertTrue(VectorFunctions.distance_between(utest, utrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(utest, xtrue) < 0.00001);
        assertTrue(VectorFunctions.distance_between(xtest, utrue) < 0.00001);
       
    }
    
    /** 
     * Test a column matrix.
     */
    @Test
    public void testSmallDeviations() {
        System.out.println("testSmallDeviations");
    
        //run nearest point test by making small deviations (del) to lattice points.
        int iters = 10;
        Random r = new Random();
        double del = 0.0001;
        for(int t = 0; t < iters; t++){
            int n = r.nextInt(10) + 5;
            LatticeAndNearestPointAlgorithmInterface lattice = new AnstarSorted(n-1);
            Matrix G = lattice.getGeneratorMatrix();
            
            Babai babai = new Babai(lattice);
            
           // System.out.println("G is " + G.getRowDimension() + " by " + G.getColumnDimension());
            double[] x = new double[G.getRowDimension()];
            double[] xdel = new double[G.getRowDimension()];
            double[] u = VectorFunctions.randomIntegerVector(n, 1000);
            
            //System.out.println("u is length " + u.length + ", x is length"  + x.length);
            
            VectorFunctions.matrixMultVector(G, u, x);
            for(int i = 0; i < x.length; i++){
                xdel[i] = x[i] +  r.nextGaussian()*del;
            }
            
            babai.nearestPoint(xdel);
            double dist = VectorFunctions.distance_between(babai.getLatticePoint(), x);
            System.out.println(dist);
            assertEquals(true, dist<0.00001);
            
        }
    }
    
    @Test
    public void testVaughanTests() {
        	int dim = 10;
	Matrix B = Matrix.random(dim, dim);
	System.out.println("B = ");
	B.print(6, 2);
	Matrix y = Matrix.random(dim, 1);
	System.out.println("y = ");
	y.transpose().print(6, 2);
	Lattice Lambda = new Lattice(B);
	Babai blll = new Babai(Lambda, new LLL());
	Babai bhkz = new Babai(Lambda, new HKZ());
	blll.computeBabaiPoint(y.getRowPackedCopy());
	bhkz.computeBabaiPoint(y.getRowPackedCopy());
	Matrix xlll = new Matrix(blll.getLatticePoint(), dim);
	Matrix xhkz = new Matrix(bhkz.getLatticePoint(), dim);
	System.out.println("xlll = ");
	xlll.transpose().print(6, 2);
	System.out.println("xhkz = ");
	xhkz.transpose().print(6, 2);
	System.out.println("||xlll - y|| = " + xlll.minus(y).norm2());
	System.out.println("||xhkz - y|| = " + xhkz.minus(y).norm2());
    }

}