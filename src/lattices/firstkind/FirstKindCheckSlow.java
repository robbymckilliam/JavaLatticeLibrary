package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import pubsim.CombinationEnumerator;
import pubsim.VectorFunctions;
import static pubsim.VectorFunctions.dot;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * Tests whether a given lattice is of first kind or not.  This slow implementation searches
 * over all combinations of relevant vectors.  It will run in reasonable time only
 * for lattices of small dimension, say 5 or less.
 * @author Robby McKilliam
 */
public class FirstKindCheckSlow {
    
    ///Set containing all relevant vectors
    protected final LatticeInterface L;
    
    ///Set containing vectors from the obtuse superbase if it exists
    protected Set<Matrix> B = new HashSet();
    
    ///True if this lattice is of first kind
    public final boolean isFirstKind;
    
    ///Dimension of the lattice
    public final int n;
   
    /** Asserts if the lattice with generator B is of first kind */
    public FirstKindCheckSlow(Matrix B) {
        this(new LatticeAndNearestPointAlgorithm(B));
    }
    
    /** Asserts if the lattice L is of first kind */
    public FirstKindCheckSlow(LatticeInterface L) {
        n = L.getDimension();
        this.L = L;
        isFirstKind = containsObtuseSuperBasis();
    }
    
    ///Given a set R containingatleast k vectors, decide whether the set contains an obtuse super basis
    protected boolean containsObtuseSuperBasis() {
        Set<Matrix> R = new HashSet(); 
        for( Matrix v : L.relevantVectors() ) R.add(v); //load all relevant vectors into the set R
        for( Set<Matrix> C : new CombinationEnumerator<>(R,n+1) ) {
            if( isObtuse(C) && isSuperbase(C) && isRankn(C) ) {
                B = new HashSet(C);
                return true;
            }
        }
        return false;
    }    
    
    ///@return true if this lattice is of first kind
    public boolean isFirstKind() { return isFirstKind; }
    
    /**
     * @return set containing vectors from the obtuse superbase if it 
     * exists, i.e., if this lattice is of first kind. Returns emptyset otherwise.
     * */
    public Set<Matrix> obtuseSuperbase() { return B; }
    
    ///Returns true if the vectos in C are all obtuse or orthogonal. TOL > 0 decides how close to zero is considered zero.
    public static boolean isObtuse(Set<Matrix> C, double TOL) {
        int N = C.size();
        Matrix[] b = new Matrix[N];
        C.toArray(b); //build an array with pointers to vectors in C
        for(int i = 0; i < N; i++)
            for(int j = i+1; j < N; j++)
                if( dot(b[i],b[j]) > Math.abs(TOL) ) return false;
        return true;
    }
    ///Default TOL = 1e-10
    public static boolean isObtuse(Set<Matrix> C) { return isObtuse(C, 1e-10); }
    
    ///Returns true if the vectors in C sum to zero.  TOL decides how close to zero each element must be
    public static boolean isSuperbase(Set<Matrix> C, double TOL) {
        Iterator<Matrix> itr = C.iterator();
        Matrix s = itr.next();
        while(itr.hasNext()) s = s.plus(itr.next());
        return s.normInf() < TOL;
    }
    ///Default TOL = 1e-8
    public static boolean isSuperbase(Set<Matrix> C) { return isSuperbase(C, 1e-8); }
    
    ///Check that the first n vectors are linearly independent. TOL is how small the determinant can be
    public static boolean isRankn(Set<Matrix> C, double TOL){
        int n = C.size();
        Matrix[] b = new Matrix[n];
        C.toArray(b); //build an array with pointers to vectors in R (also vertices in G)
        Matrix bGram = new Matrix(n-1,n-1);
        for(int i = 0; i < n-1; i++)
            for(int j = 0; j < n-1; j++)
                bGram.set(i,j, dot(b[i],b[j]) ); 
        return Math.abs(bGram.det()) > TOL; 
    }
    ///Default TOL = 1e-10
    public static boolean isRankn(Set<Matrix> C) { return isRankn(C, 1e-10); }
    
        /** Check that a given obtuse basis actually is a basis for the lattice */
    public static boolean isBasis(Set<Matrix> B, LatticeInterface L) {
        int n = L.getDimension();
        if( B.size() != n+1 ) return false; //wrong number of vectors
        
        //stash superbase vectors into an array
        Matrix[] b = new Matrix[n+1];
        B.toArray(b); //build an array with pointers to vectors in R (also vertices in G)
        
        //compute the basis matrix corresponding with first n superbase vectors
        int m = b[0].getRowDimension();
        Matrix bLattice = new Matrix(m,n);
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                bLattice.set(i,j, b[j].get(i,0) );
        System.out.println(VectorFunctions.print(bLattice));
        
        Matrix latticeBasis = L.getGeneratorMatrix();
        Matrix U = (bLattice.transpose().times(bLattice)).inverse().times(bLattice.transpose().times(latticeBasis));
        System.out.println(VectorFunctions.print(U));
        System.out.println(U.det());

        return VectorFunctions.isUnimodular(U);
    }
    
}
