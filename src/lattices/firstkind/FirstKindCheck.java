package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import pubsim.AllCliquesOfSize;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.lattices.LatticeInterface;
import static pubsim.VectorFunctions.dot;
import static pubsim.lattices.firstkind.FirstKindCheckSlow.isSuperbase;

/**
 * Tests whether a given lattice is of first kind or not.  Efficiently searches over the obtuse 
 * combinations of relevant vectors.  Can be used for n up to 8.
 * @author Robby McKilliam
 */
public class FirstKindCheck extends FirstKindCheckSlow {
    
    public FirstKindCheck(Matrix B) {
        this(new LatticeAndNearestPointAlgorithm(B));
    }
    
    public FirstKindCheck(LatticeInterface L) {
        super(L);        
    }
    
    ///Given a set R containing atleast k vectors, decide whether the set contains an obtuse super basis
    @Override
    protected boolean containsObtuseSuperBasis() {
        Set<Matrix> R = new HashSet(); 
        for( Matrix v : L.relevantVectors() ) R.add(v); //load all relevant vectors into the set R
        Graph<Matrix,?> G = obtuseConnectedGraph(R);
        for( Set<Matrix> C : new AllCliquesOfSize<>(n+1,G) ) {
            if( isSuperbase(C) && isRankn(C) ) { //no need to check for obtuse
                B = new HashSet(C);
                return true;
            }
        }
        return false;
    }
    
    /** 
     * Returns a graph, each vertex corresponding with an vector in R, and edges 
     * connecting vertices (vectors) if they are obtuse or at right angles.
     * @param TOL > 0 decides how close to zero is considered zero.
     */
    public static Graph<Matrix,DefaultEdge> obtuseConnectedGraph(Set<Matrix> R, double TOL){
        Graph G = new SimpleGraph<>(DefaultEdge.class);
        for( Matrix v : R ) G.addVertex(v); //graph contains the relevant vectors
        int N = R.size();
        Matrix[] b = new Matrix[N];
        R.toArray(b); //build an array with pointers to vectors in R (also vertices in G)
        
        //add edges between obtuse relevant vectors.
        for(int i = 0; i < N; i++)
            for(int j = i+1; j < N; j++)
                if( dot(b[i],b[j]) < Math.abs(TOL) ) G.addEdge(b[i],b[j]);
        
        return G;
    }
    ///Default TOL = 1e-10
    public static Graph<Matrix,DefaultEdge> obtuseConnectedGraph(Set<Matrix> R) {
        return obtuseConnectedGraph(R,1e-10);
    }
    
}
