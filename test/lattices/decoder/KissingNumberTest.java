package pubsim.lattices.decoder;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.lattices.Zn;
import pubsim.lattices.leech.Leech;

/**
 *
 * @author Robby McKilliam
 */
public class KissingNumberTest {

    public KissingNumberTest() {
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
     * Test of kissingNumber method, of class KissingNumber.
     */
    @Test
    public void testKissingNumberonZn() {
        System.out.println("kissingNumber Zn test");
        int n = 5;
        KissingNumber instance = new KissingNumber(new Zn(n));
        int expResult = 2*n;
        int result = (int)instance.kissingNumber();
        assertEquals(expResult, result);

    }

     /**
     * Test of kissingNumber method, of class KissingNumber.
     */
    @Test
    public void testKissingNumberonLeech() {
        System.out.println("kissingNumber Leech test");
        KissingNumber instance = new KissingNumber(new Leech());
        int expResult = 196560;
        int result = (int)instance.kissingNumber();
        assertEquals(expResult, result);

    }

}