package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.AllCliquesOfSize;
import pubsim.CombinationEnumerator;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.AnstarLinear;
import pubsim.lattices.Dn;
import pubsim.lattices.E6;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.lattices.LatticeInterface;
import static pubsim.lattices.firstkind.FirstKindCheckSlow.isObtuse;

/**
 *
 * @author Robby McKilliam
 */
public class FirstKindCheckTest {
    
    public FirstKindCheckTest() {
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
     * Test that the lattice An is of first kind.
     */
    @Test
    public void testFirstKindCheckAn() {
        System.out.println("An is first kind");
        int n = 4;
        assertTrue(new FirstKindCheckSlow(new AnFastSelect(n)).isFirstKind);       
        assertTrue(new FirstKindCheck(new AnFastSelect(n)).isFirstKind);       
    }
    
    /**
     * Test that the lattice An is of first kind.
     */
    @Test
    public void testFirstKindCheckAnstar() {
        System.out.println("Anstar is first kind");
        int n = 4;
        assertTrue(new FirstKindCheckSlow(new AnstarLinear(n)).isFirstKind);       
        assertTrue(new FirstKindCheck(new AnstarLinear(n)).isFirstKind);  
    }
    
    @Test
    public void testRandom1DisfirstKind() {
        System.out.println("random 1-dimensional lattice is first kind");
        assertTrue(new FirstKindCheckSlow(Matrix.random(4, 1)).isFirstKind);    
        assertTrue(new FirstKindCheck(Matrix.random(4, 1)).isFirstKind);  
    }
    
    @Test
    public void testRandom2DisfirstKind() {
        System.out.println("random 2-dimensional lattice is first kind");
        Matrix B = Matrix.random(2, 2);
        LatticeAndNearestPointAlgorithm L = new LatticeAndNearestPointAlgorithm(B);
        //for( Matrix v : L.relevantVectors() ) System.out.println(VectorFunctions.print(v));
        FirstKindCheckSlow f = new FirstKindCheckSlow(L);
        FirstKindCheckSlow ffast = new FirstKindCheckSlow(L);
        assertTrue(f.isFirstKind);    
        assertTrue(ffast.isFirstKind);
        assertTrue(FirstKindCheckSlow.isBasis(ffast.obtuseSuperbase(), L));
    }
    
    @Test
    public void testRandom3DisfirstKind() {
        System.out.println("random 3-dimensional lattice is first kind");
        Matrix B = Matrix.random(3, 3);
        assertTrue(new FirstKindCheckSlow(B).isFirstKind);    
        assertTrue(new FirstKindCheck(B).isFirstKind); 
    }
    
    @Test
    public void testDnlatticesisfirstKind() {
        System.out.println("Test that the root lattice D3 is first kind");
        for(int n = 2; n <= 3; n++){
            FirstKindCheck f = new FirstKindCheck(new Dn(n));
            assertTrue(f.isFirstKind); 
            for( Matrix v : f.obtuseSuperbase() ) System.out.print(VectorFunctions.print(v.transpose()));
            System.out.println();
            assertTrue(FirstKindCheckSlow.isBasis(f.obtuseSuperbase(), new Dn(n)));
        }
    }
    
    @Test
    public void testD4isfirstKind() {
        System.out.println("Test for D4");
        FirstKindCheck f = new FirstKindCheck(new Dn(4));
        assertTrue(f.isFirstKind);
        for (Matrix v : f.obtuseSuperbase()) System.out.print(VectorFunctions.print(v.transpose()));
        System.out.println();
        assertTrue(FirstKindCheckSlow.isBasis(f.obtuseSuperbase(), new Dn(4)));
    }
   
    @Test
    public void testE6latticesisfirstKind() {
        System.out.println("Test that the root lattice E6 is first kind");
        FirstKindCheck f = new FirstKindCheck(new E6());
        assertTrue(f.isFirstKind);
        System.out.println(f.obtuseSuperbase().size());
        for( Matrix v : f.obtuseSuperbase() ) System.out.print(VectorFunctions.print(v.transpose()));
        System.out.println();
        assertTrue(FirstKindCheckSlow.isBasis(f.obtuseSuperbase(), new E6()));
    }
    
//    @Test
//    public void testE8latticesisfirstKind() {
//        System.out.println("Test that the root lattice E8 is first kind");
//        FirstKindCheck f = new FirstKindCheck(new E8());
//        assertTrue(f.isFirstKind); 
//        for( Matrix v : f.obtuseSuperbase() ) System.out.print(VectorFunctions.print(v.transpose()));
//        System.out.println();
//        assertTrue(FirstKindCheck.isBasis(f.obtuseSuperbase(), new E8()));
//    }
    
//    @Test
//    public void testHowManyIn4D() {
//        System.out.println("Calculate proportion of first type lattices in 4 dimensions");
//        int N = 100;
//        int count = 0;
//        for(int i = 0; i < N; i++) {
//            Matrix B = Matrix.random(4,4);
//            if(new FirstKindCheck(new LatticeAndNearestPointAlgorithm(B)).isFirstKind)
//                count++;
//        }
//        System.out.println(1.0*count/N);
//    }
    
//    @Test
//    public void testSlowAndFastSameFor4D() {
//        System.out.println("check that slow and fast verions are the same in 4 dimensions");
//        int N = 10;
//        int count = 0;
//        for(int i = 0; i < N; i++) {
//            Matrix B = Matrix.random(4,4);
//            FirstKindCheck ffast = new FirstKindCheck(B);
//            FirstKindCheckSlow fslow = new FirstKindCheckSlow(B);
//            assertTrue(ffast.isFirstKind == fslow.isFirstKind);
//        }
//    }
    
    /**
     * Test of isObtuse method, of class FirstKindCheck.
     */
    @Test
    public void testIsObtuse() {
        System.out.println("is Obtuse");
        int n = 5;
        Matrix B = new AnFastSelect(n).getGeneratorMatrixBig();
        Set<Matrix> S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheckSlow.isObtuse(S));
        
        B = new AnstarLinear(n).getGeneratorMatrixBig();
        S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheckSlow.isObtuse(S));
        
        B = Matrix.identity(n, n);
        S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheckSlow.isObtuse(S));
        
        B.set(1,0,1.0);
        S = VectorFunctions.splitColumns(B);
        assertFalse(FirstKindCheckSlow.isObtuse(S));
    }

    /**
     * Test of isSuperbase method, of class FirstKindCheck.
     */
    @Test
    public void testIsSuperbase() {
        System.out.println("is Superbase");
        int n = 5;
        Matrix B = new AnFastSelect(n).getGeneratorMatrixBig();
        Set<Matrix> S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheckSlow.isSuperbase(S));
        
        B = new AnstarLinear(n).getGeneratorMatrixBig();
        S = VectorFunctions.splitColumns(B);
        assertTrue(FirstKindCheckSlow.isSuperbase(S));
        
        B = Matrix.identity(n, n);
        S = VectorFunctions.splitColumns(B);
        assertFalse(FirstKindCheckSlow.isSuperbase(S));
    }
    
    @Test
    public void obtuseConnectedGraphAn() {
        System.out.println("testing obtuse connected graph with An");
        int n = 3;
        LatticeInterface L = new AnFastSelect(n);
        Set<Matrix> R = new HashSet(); 
        for( Matrix v : L.relevantVectors() ) R.add(v); //load all relevant vectors into the set R
        Graph<Matrix,?> G = FirstKindCheck.obtuseConnectedGraph(R);
        for(Matrix v : R) {
            for(Matrix x : R) {
                boolean isObtuse = VectorFunctions.dot(v,x) < 1e-10;
                boolean isEdge = G.containsEdge(x,v);
                assertTrue(isObtuse == isEdge);
            }
        }
        
        System.out.println(G.edgeSet().size());
        System.out.println(G.vertexSet().size());
        
        int countbrutecliques=0;
        for( Set<Matrix> C : new CombinationEnumerator<>(R,n+1) ) {
            assertTrue(C.size()==n+1);
            if (isObtuse(C)) {
                countbrutecliques++;
                int N = C.size();
                Matrix[] b = new Matrix[N];
                C.toArray(b); //build an array with pointers to vectors in C
                for (int i = 0; i < N; i++) {
                    for (int j = i + 1; j < N; j++) {
                        assertTrue(VectorFunctions.dot(b[i], b[j]) < 1e-10);
                        assertTrue( G.containsEdge(b[i],b[j]) );
                        assertTrue( G.containsEdge(b[j],b[i]) );
                    }
                }
            }
        }
        
        int countcliques = 0;
        for( Set<Matrix> S : new AllCliquesOfSize<>(n+1,G)){
            countcliques++;
            //for( Matrix v : S ) System.out.print(VectorFunctions.print(v.transpose()));
            //System.out.println();
        }
           
        assertEquals(countbrutecliques,countcliques);
    }
    
    @Test
    public void obtuseConnectedGraphAnstar() {
        System.out.println("testing obtuse connected graph with An*");
        int n = 3;
        LatticeInterface L = new AnstarLinear(n);
        Set<Matrix> R = new HashSet(); 
        for( Matrix v : L.relevantVectors() ) R.add(v); //load all relevant vectors into the set R
        Graph<Matrix,?> G = FirstKindCheck.obtuseConnectedGraph(R);
        for(Matrix v : R) {
            for(Matrix x : R) {
                boolean isObtuse = VectorFunctions.dot(v,x) < 1e-10;
                boolean isEdge = G.containsEdge(x,v);
                assertTrue(isObtuse == isEdge);
            }
        }
        
        System.out.println(G.edgeSet().size());
        System.out.println(G.vertexSet().size());
        
        int countbrutecliques=0;
        for( Set<Matrix> C : new CombinationEnumerator<>(R,n+1) ) {
            assertTrue(C.size()==n+1);
            if (isObtuse(C)) {
                countbrutecliques++;
                int N = C.size();
                Matrix[] b = new Matrix[N];
                C.toArray(b); //build an array with pointers to vectors in C
                for (int i = 0; i < N; i++) {
                    for (int j = i + 1; j < N; j++) {
                        assertTrue(VectorFunctions.dot(b[i], b[j]) < 1e-10);
                        assertTrue( G.containsEdge(b[i],b[j]) );
                        assertTrue( G.containsEdge(b[j],b[i]) );
                    }
                }
            }
        }
        
        int countcliques=0;
        for( Set<Matrix> S : new AllCliquesOfSize<>(n+1,G) ){
            countcliques++;
            //for( Matrix v : S ) System.out.print(VectorFunctions.print(v.transpose()));
            //System.out.println();
        }
        
        assertEquals(countbrutecliques,countcliques);
        
    }
    
}
