package pubsim.lattices.firstkind.shortvector;

import Jama.Matrix;
import java.util.Set;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import static pubsim.VectorFunctions.matrixMultVector;
import pubsim.lattices.decoder.ShortVector;
import pubsim.lattices.firstkind.LatticeOfFirstKind;

/**
 * Computes a short vector in a lattice of first type using the Stoer-Wagner minimum cut algorithm
 * @author Robby McKilliam
 */
public class MinCutShortVector implements ShortVector {
    
    final double[] u,v;
    
    public MinCutShortVector(LatticeOfFirstKind L){
        final Matrix B = L.superbasis();
        final Matrix Q = L.extendedGram();
        final int N = L.getDimension();  
        
        WeightedGraph<Integer, DefaultWeightedEdge> G =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
       
        //add all the vertices
        for(int i = 0; i < N+1; i++) G.addVertex(i); 
        
        //add all the edges
        for(int i = 0; i < N+1; i++) 
            for(int j = i+1; j < N+1; j++)
                G.setEdgeWeight(G.addEdge(i,j), -Q.get(i,j));
        
        //compute the minimum cut
        StoerWagnerMinimumCut<Integer, DefaultWeightedEdge> mincut = 
                new StoerWagnerMinimumCut<>(G);
        
        u = new double[N+1];
        v = new double[N+1];
        
        Set<Integer> C = mincut.minCut();
        for( Integer i : C ) u[i] = 1;
        
        //compute the short vector from the index
        matrixMultVector(B, u, v); 
        
    } 

    @Override
    public double[] getShortestVector() {
        return v;
    }

    /** This is an index for the obtuse superbasis matrix, not the original generator */
    @Override
    public double[] getShortestIndex() {
        return u;
    }
    
    
    
}
