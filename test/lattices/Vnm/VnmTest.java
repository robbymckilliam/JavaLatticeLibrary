package pubsim.lattices.Vnm;

import Jama.Matrix;
import Jama.QRDecomposition;
import static org.junit.Assert.*;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.Vnm.Vnm;

/**
 *
 * @author Robby McKilliam
 */
public class VnmTest {

    public VnmTest() {
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
     * Test of logVolume method, of class Vnm.
     */
    @Test
    public void testLogVolume() {
        System.out.println("logVolume");
        Vnm instance = new Vnm(12, 2);
        Matrix gen = instance.getGeneratorMatrix();
        Matrix gram = gen.transpose().times(gen);

        System.out.println(VectorFunctions.print(gen));
        System.out.println(VectorFunctions.print(new QRDecomposition(gen).getR()));

        assertEquals(Math.sqrt(gram.det()), instance.volume(), 1.0);
    }

    /**
     * Test of logVolume method, of class Vnm.
     */
    @Test
    public void testPrintOutShortVects() {
        System.out.println("print out short vectors");
        int maxn = 15;
        int m = 3;
        for (int n = 1; n < maxn; n += 1) {
            Vnm instance = new Vnm(n, m);
            System.out.println(n + ", " + instance.kissingNumber() + ", " + Math.pow(2*instance.inradius(),2));
        }
    }
    
    /**
     * Test of Vn0.
     */
    @Test
    public void testVn0() {
        System.out.println("test Vn0");
        int minn = 1;
        int maxn = 50;
        int m = 0;
        for(int n = minn; n < maxn; n++){
            Vnm.Vn0 testee = new Vnm.Vn0(n);
            Vnm tester = new Vnm(n, m);
            assertEquals(testee.volume(), tester.volume(), 0.00001);
            assertEquals(testee.kissingNumber(), tester.kissingNumber());
        }
    }
    
     /**
     * Test of Vn1.
     */
    @Test
    public void testVn1() {
        System.out.println("test Vn1");
        int minn = 1;
        int maxn = 40;
        int m = 1;
        for(int n = minn; n < maxn; n += 1){
            Vnm.Vn1 testee = new Vnm.Vn1(n);
            Vnm tester = new Vnm(n, m);
            assertEquals(testee.volume(), tester.volume(), 0.00001);
            //System.out.println(testee.kissingNumber() + ", " + tester.kissingNumber());
            //System.out.println(testee.inradius() + ", " + tester.inradius());
            assertEquals(testee.kissingNumber(), tester.kissingNumber());
        }
    }
    
    /**
     * Test ProbCodingError.
     */
    @Test
    public void testProbCodingError() {
        System.out.println("ProbCodingError");
        Vnm instance = new Vnm(12, 1);
        Matrix gen = instance.getGeneratorMatrix();
        Matrix gram = gen.transpose().times(gen);
        for(double S = 0.1; S < 5; S+=0.1) System.out.println(instance.probCodingError(S));
    }
    
    /**
     * Test ProbCodingError.
     */
    @Test
    public void testSeededKissingNumber() {
        System.out.println("SeededKissingNumber");
        int minn = 1;
        int maxn = 30;
        int m = 2;
        for(int n = minn; n < maxn; n += 1){
            Vnm tester = new Vnm(n, m);
            System.out.println(tester.seededKissingNumber() + ", " + tester.kissingNumber() + ", " + tester.norm());
        }
    }    
    
    
       /**
     * Test of volume method, of class Vnm.
     */
    @Test
    public void volume() {
        System.out.println("volume");
        
        int n = 20;
        int a = 3;
        
        Vnm instance = new Vnm(n, a);

        Matrix gen = instance.getGeneratorMatrix();
        //System.out.println(VectorFunctions.print(gen));
        Matrix gram = gen.transpose().times(gen);
        double expResult = Math.sqrt(gram.det());
        
        double result = instance.volume();
        assertEquals(expResult, result, 0.001);

    }

        /**
     * Test of volume method, of class Vnm.
     */
    @Test
    public void generatorMatrix() {
        System.out.println("generatorMatrix");

        int n = 10;
        int a = 3;

        Vnm instance = new Vnm(n, a);
        Matrix M = instance.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(M));
        System.out.println(VectorFunctions.print(M.transpose().times(M)));

    }

     /**
     * Test of volume method, of class Vnm.
     */
    @Test
    public void generateKissingNumbers() {
        System.out.println("generateKissingNumbers");

        int n = 48;
        int a = 7;

        Vnm instance = new Vnm(n, a);

        System.out.println(instance.inradius());
        System.out.println(instance.kissingNumber());

    }

    
//    
//    @Test
//    public void testRangeHypothesis() {
//        System.out.println("testing range hypothesis");
//        int n = 64;
//        int m = 0;
//        int iters = 100;
//        Vnm lattice = new Vnm(m,n);
//        SphereDecoder decoder = new SphereDecoderSchnorrEuchner(lattice);
//        Matrix G = lattice.getGeneratorMatrix();
//        Matrix invG = ((G.transpose().times(G)).inverse()).times(G.transpose());
//        for(int i = 1; i < iters; i++){
//            double[] y = VectorFunctions.randomGaussian(n+m+1, 0.0, 1000.0);
//            double[] z = VectorFunctions.round(VectorFunctions.matrixMultVector(invG, y));
//            decoder.nearestPoint(y);
//            double[] sdz = decoder.getIndex();
//            for(int j = 0; j < z.length; j++){
//                System.out.println(sdz[j] - z[j]);
//                //assertTrue(Math.abs(sdz[j] - z[j]) <= m+1);
//            }
//        }
//    }
    
    
//    @Test
//    public void searchForSquares() {
//        System.out.println("search for squares");
//        int m = 1;
//        for (int n = m + 1; n < 1000; n++) {
//            double vol = (new Vnm(4, n)).volume();
//            String marker = "";
//            if (Math.abs(vol - Math.round(vol)) < 0.000001) {
//                marker = "******";
//            }
//            System.out.println(n + "\t" + vol + "\t" + marker);
//        }
//
//    }
}