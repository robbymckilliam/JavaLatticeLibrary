/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec.generators;

import Jama.Matrix;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.lattices.Vnm.Vnm;

/**
 *
 * @author Robby McKilliam
 */
public class UpperTriangularGeneratorTest {
    
    public UpperTriangularGeneratorTest() {
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
    public void setThrowsExceptionCorrectly() {
        System.out.println("set throws exception correctly");
        int n = 10;
        int m = 1;
        double v = 1.0;
        UpperTriangularGenerator instance = new UpperTriangularGenerator(n, m);
        try { instance.set(-1, 5, v); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.set(11, 5, v); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.set(4, 11, v); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.set(4, -11, v); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.set(0, 1, v); } catch(ArrayIndexOutOfBoundsException e){ }
    }
    
    @Test
    public void getThrowsExceptionCorrectly() {
        System.out.println("get throws exception correctly");
        int n = 10;
        int m = 1;
        UpperTriangularGenerator instance = new UpperTriangularGenerator(n, m);
        try { instance.get(-1, 5); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.get(11, 5); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.get(4, 11); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.get(4, -11); } catch(ArrayIndexOutOfBoundsException e){ }
        try { instance.get(0, 1); } catch(ArrayIndexOutOfBoundsException e){ }
    }
    
    @Test
    public void setAndget() {
        System.out.println("test setting and getting");
        int n = 10;
        int m = 0;
        double v = 1.0;
        UpperTriangularGenerator instance = new UpperTriangularGenerator(n, m);
        instance.set(0, 1, v); assertEquals(v, instance.get(0,1), 0.0000001);
        instance.set(0, 2, v); assertEquals(0.0, instance.get(0,2), 0.0000001);
        instance.set(5, 5, v); assertEquals(v, instance.get(5,5), 0.0000001);
        instance.set(5, 9, v); assertEquals(0.0, instance.get(5,9), 0.0000001);
        //for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) instance.set(i, j, v);
        //System.out.print(instance);
    }
    
    @Test
    public void printVersusQR() {
        System.out.println("test the generator versus QR");
        int n = 100;
        int m = 2;
        UpperTriangularGenerator instance = new UpperTriangularGenerator(n, m);
        Matrix R = new Vnm(n,m).getGeneratorMatrix().qr().getR();
        for(int i = 0; i < n; i++) 
            for(int j = 0; j < n; j++) 
                assertEquals(Math.abs(instance.get(i,j)),Math.abs(R.get(i,j)), 0.00001);
    }
    
    @Test
    public void printGenerator() {
        System.out.println("print the generator");
        int n = 2;
        int m = 0;
        UpperTriangularGenerator instance = new UpperTriangularGenerator(n, m);
        System.out.print(instance);
    }

}
