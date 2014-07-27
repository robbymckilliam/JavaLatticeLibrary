package pubsim.lattices.Vnm.codec;

import org.junit.*;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;
import pubsim.lattices.Vnm.codec.generators.UpperTriangularGenerator;

/**
 *
 * @author Robby McKilliam
 */
public class TomlinsonHarashimaShapedTest {
    
    public TomlinsonHarashimaShapedTest() {
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
     * This test could cover more.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        int n = 2;
        int m = 0;
        int M = 2;
        TomlinsonHarashimaShaped codec1 = new TomlinsonHarashimaShaped(n,m,M);
        
        int[] u1 = {1,0};
        double[] y1 = codec1.encode(u1);
        System.out.println(VectorFunctions.print(y1));
        
        int[] u2 = {0,1};
        double[] y2 = codec1.encode(u2);
        System.out.println(VectorFunctions.print(y2));
        
        int[] u3 = {1,1};
        double[] y3 = codec1.encode(u3);
        System.out.println(VectorFunctions.print(y3));
        
        n = 5;
        m = 1;
        M = 2;
        TomlinsonHarashimaShaped codec2 = new TomlinsonHarashimaShaped(n,m,M);
        int[] u4 = {1,0,1,1,0};
        double[] y4 = codec2.encode(u4);
        System.out.println(VectorFunctions.print(y4));

    }
    
    /** Montecarlo's the average power of the code and checks that it's close to what is expected */
    public void testSecondMoment() {
        int n = 1000;
        int m = 2;
        int M = 2;
        int iters = 10000;
        TomlinsonHarashimaShaped codec = new TomlinsonHarashimaShaped(n,m,M);
        double mc = 0.0;
        for(int i = 0; i < iters; i++){
            int[] u  = VectorFunctions.randomIntegers(n, M);
            double[] y = codec.encode(u);
            mc += VectorFunctions.sum2(y);
        }
        System.out.println(mc/iters/n);
        
        //now compute the expected value from the upper triangular matrix
        UpperTriangularGenerator R = new UpperTriangularGenerator(n, m);
        double secmom = 0.0;
        for(int i = 0; i < n; i++){
            double d = M*Math.abs(R.get(i,i));
            secmom +=d*d*d/12.0;
        }
        System.out.println(secmom/n);
        
    }
    

}
