/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices;

import Jama.Matrix;
import org.junit.*;
import static org.junit.Assert.*;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class BarnesWallTest {
    
    public BarnesWallTest() {
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
     * Test of coveringRadius method, of class BarnesWall.
     */
    @Test
    public void printNominalGain() {
        System.out.println("printNominalGain");
        for(int m = 1; m < 9; m++){
            BarnesWall lattice = new BarnesWall(m);
            System.out.println(lattice.getDimension() + ", " + lattice.nominalCodingGain());
        }
    }
    
    @Test
    public void testGenerator() {
        for(int m = 1; m < 5; m++){
            BarnesWall lattice = new BarnesWall(m);
            Matrix B = lattice.getGeneratorMatrix();
            Lattice gl = new Lattice(B);
            //System.out.println(VectorFunctions.print(B));
            //System.out.println(gl.norm() + ", " + lattice.norm());
            //System.out.println(gl.volume() + ", " + lattice.volume());
            //System.out.println(gl.kissingNumber() + ", " + lattice.kissingNumber());
            
            assertEquals(gl.norm(),lattice.norm(),0.00001);
            //assertEquals(gl.volume(),lattice.volume(),0.00001);
            assertEquals(gl.kissingNumber(),lattice.kissingNumber(),0.00001);
        }
    }
    
    
}
