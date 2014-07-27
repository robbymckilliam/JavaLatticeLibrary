/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import pubsim.lattices.util.ZnWithinSphere;
import java.util.Collection;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import static org.junit.Assert.*;

/**
 * 
 * @author Robby McKilliam
 */
public class ZnWithinSphereTest {
   

    public ZnWithinSphereTest() {
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
     * Test of iterator method, of class ZnWithinSphere.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        
        int n = 3;
        double r = 3;
        
        ZnWithinSphere instance = new ZnWithinSphere();
        instance.setDimension(n);
        instance.setRadius(r);
                
        int count = 0;
        Iterator itr = instance.iterator();
        while(itr.hasNext()){
            count++;
            double[] x =(double[])itr.next();
            System.out.println(VectorFunctions.print(x));
            assertTrue( VectorFunctions.max(x) <= r );
        }
        System.out.println(count);
       
    }

}