package pubsim.lattices.Vnm.codec.generators;

import Jama.Matrix;
import org.junit.*;
import static org.junit.Assert.*;
import pubsim.lattices.Vnm.Vnm;

/**
 *
 * @author Robby McKilliam
 */
public class StandardGeneratorTest {
    
    public StandardGeneratorTest() {
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
     * Test of toString method, of class StandardGenerator.
     */
    @Test
    public void testMatrix() {
        System.out.println("test matrix");
        int n = 17;
        int m = 3;
        StandardGenerator instance = new StandardGenerator(n, m);
        Matrix tester = new Vnm(n,m).getGeneratorMatrix();
        double err = 0.0;
        for(int i = 0; i < n+m+1; i++) for(int j = 0; j < n; j++) err += Math.abs(instance.get(i,j) - tester.get(i,j));
        assertTrue(err < 0.0001);
    }
    
    /**
     * Test of toString method, of class StandardGenerator.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int n = 10;
        int m = 2;
        StandardGenerator instance = new StandardGenerator(n, m);
        System.out.println(instance);
    }
}
