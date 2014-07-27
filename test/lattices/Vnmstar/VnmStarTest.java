/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Vnmstar;

import pubsim.lattices.Vnmstar.VnmStar;
import Jama.Matrix;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.reduction.LLL;

/**
 *
 * @author Robby McKilliam
 */
public class VnmStarTest {

    public VnmStarTest() {
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
     * Test of project method, of class lattices.VnmStarSampledEfficient.
     */
    @Test
    public void testProject() {
        System.out.println("project");

        double[] x = {1,4,5,2,1};
        double[] y = new double[x.length];
        int m = 1;

        //from matlab
        double[] exp = {-2, 1.2, 2.4 ,-0.4, -1.2};

        VnmStar.project(x, y, m);

        System.out.println(VectorFunctions.print(y));
        double dist = VectorFunctions.distance_between(y, exp);
        System.out.println(" y = " + VectorFunctions.print(y));
        assertEquals(true, dist<0.0001);

        //test with a larger projection.
        x = VectorFunctions.randomGaussian(100);
        y = new double[x.length];
        m = 4;
        VnmStar.project(x, y, m);
        VnmStar.getMMatrix(m, 100 - m - 1);
        Matrix ym = VectorFunctions.rowMatrix(y);
        Matrix ret = ym.times(VnmStar.getMMatrix(m, 100 - m - 1));
        for(int i = 0; i <= m; i++){
            assertEquals(0.0, ret.get(0,i), 0.0001);
        }


    }

    @Test
    public void testMMatrix(){
        System.out.println("testGeneratorMatrix");

        int n = 8;
        int m = 2;

        Matrix M = VnmStar.getMMatrix(m, n);
        System.out.println(VectorFunctions.print(M));
    }

    @Test
    public void testGeneratorMatrix(){
        System.out.println("testGeneratorMatrix");

        int n = 11;
        int m = 3;

        Matrix M = VnmStar.getGeneratorMatrix(m, n);
        System.out.println(VectorFunctions.print(M));

        Matrix Mpt = M.inverse().transpose();
        System.out.println(VectorFunctions.print(Mpt));

        LLL lll = new LLL();
        System.out.println(VectorFunctions.print(lll.reduce(Mpt)));

    }
    
    
    @Test
    public void testObtuse(){
        System.out.println("test obtuse");

        int n = 30;
        int m = 1;
        
        Matrix M = VnmStar.getGeneratorMatrix(m, n);
        System.out.println(VectorFunctions.print(M));

        Matrix Mpt = M.transpose().times(M);
        System.out.println(VectorFunctions.print(Mpt));
    }
    
    
    @Test
    public void printGeneratorAndInverse(){
        System.out.println("print generators");

        int n = 5;
        int m = 1;
        
        Matrix M = VnmStar.getGeneratorMatrix(m, n);
        System.out.println(VectorFunctions.print(M));

        Matrix Minv = ((M.transpose().times(M)).inverse()).times(M.transpose());
        for(int i = 0; i < Minv.getRowDimension(); i++)
            for(int j = 0; j < Minv.getColumnDimension(); j++)
                Minv.set(i,j, Math.round(Minv.get(i,j)));
        System.out.println(VectorFunctions.print(Minv));

    }

}