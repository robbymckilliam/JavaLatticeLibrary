package pubsim.lattices.decoder;

import Jama.Matrix;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static pubsim.VectorFunctions.columnMatrix;
import static pubsim.VectorFunctions.print;
import static pubsim.VectorFunctions.print;
import pubsim.lattices.An.An;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.Zn;

/**
 *
 * @author Robby McKilliam
 */
public class AllClosestPointsTest {
    
    public AllClosestPointsTest() {
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
     * Test of closestPoints method, of class AllClosestPoints.
     */
    @Test
    public void testClosestPointsWithIntegerLattice() {
        System.out.println("closest points in Zn");
        Zn L = new Zn(2);
        
        //Check single point successfully found
        double[] y1 = {1.1,0.1};
        Set<Matrix> allcps = new AllClosestPoints(L).closestPoints(y1);
        assertTrue(allcps.size()==1);
        L.nearestPoint(y1);
        assertTrue( columnMatrix(L.getLatticePoint()).minus(allcps.iterator().next()).normInf() < 1e-9);
        
        double[] y2 = {1.5,0.1};
        allcps = new AllClosestPoints(L).closestPoints(y2);
        assertTrue(allcps.size()==2);
        for( Matrix v : allcps) System.out.print(print(v.transpose()));
        System.out.println();
        
        double[] y3 = {1.5,-0.5};
        allcps = new AllClosestPoints(L).closestPoints(y3);
        assertTrue(allcps.size()==4);
        for( Matrix v : allcps) System.out.print(print(v.transpose()));
        System.out.println();
        
    }
    
        /**
     * Test of closestPoints method, of class AllClosestPoints.
     */
    @Test
    public void testClosestPointsWithAn() {
        System.out.println("closest point in An");
        An L = new AnFastSelect(2);
        
        //Check single point successfully found
        double[] y1 = {1.1,-1.1,0.1};
        Set<Matrix> allcps = new AllClosestPoints(L).closestPoints(y1);
        //System.out.println(allcps.size());
        assertTrue(allcps.size()==1);
        L.nearestPoint(y1);
        assertTrue( columnMatrix(L.getLatticePoint()).minus(allcps.iterator().next()).normInf() < 1e-9);
        
        double[] y2 = {0.5,-0.5,0.0};
        allcps = new AllClosestPoints(L).closestPoints(y2);
        assertTrue(allcps.size()==2);
        for( Matrix v : allcps) System.out.print(print(v.transpose()));
        System.out.println();
                
    }
    
}
