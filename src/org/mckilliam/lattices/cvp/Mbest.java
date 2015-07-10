package org.mckilliam.lattices.cvp;

import java.util.Map.Entry;
import java.util.TreeMap;
import pubsim.VectorFunctions;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.reduction.LLL;
import org.mckilliam.lattices.reduction.LatticeReduction;

/**
 * The Mbest approximate closest vector algorithm.  Allies the same tree first search
 * used by the sphere decoder, but culls branches to ensure that only M remain at any time.
 * @author Robby McKilliam
 */
public class Mbest extends Babai {

    /** Current sphere radius squared */
    protected double D;

    //temporary variable for ut
    protected double[] ut, ubest;

    protected double[] xr;

    //small number to avoid numerical errors in branches.
    protected double DELTA = 0.000001;

    /** M for the M-best method */
    protected int M;

    /**
     * Constructor sets the M parameter for the M best method.
     * This is the maximum number of points that can be kept at
     * each iteration of the decoder.  Default uses LLL reduction first
     */
    public Mbest(LatticeInterface L, int M){
        this(L, M, new LLL());
    }
    
    /**
     * Constructor sets the M parameter for the M best method.
     * This is the maximum number of points that can be kept at
     * each iteration of the decoder.  This allows you to set an initial lattice reduction.
     */
    public Mbest(LatticeInterface L, int M, LatticeReduction lr){
        super(L,lr);
        this.M = M;
        
        ut = new double[n];
        ubest = new double[n];
        xr = new double[n];

        //CAREFULL!  This version of the decoder requires R to
        //have positive diagonal entries.
        //System.out.println(VectorFunctions.print(B));
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();
        Qtrans = Q.transpose();
    }
                
    @Override
    public double[] closestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");

        //this will store the nearest point in the variable x
        //NEED TO CALL THIS, it sets yr.  Could change it but meh.
        computeBabaiPoint(y);

        //compute the radius squared of the sphere we are decoding in.
        //Add DELTA to avoid numerical error causing the
        //Babai point to be rejected.
        D = VectorFunctions.distance_between2(y, x) + DELTA;

        //set least possible ut[k]
        TreeMap<Double, List<Integer>> prevmap = new TreeMap<>();
        
        //ok, set up the initial tree map with the first M elements
        int k = n-1;
        int u = (int)Math.round(yr[k]/R.get(k,k));
        double d = R.get(k, k)*u - yr[k];
        addToMap( prevmap, d*d, new List<>(u));
        int m = 0;
        boolean keepAdding = true;
        //this is being a bit lazy, it might use a little more that M. No big problem though.
        while(m < M/2 + 1 && keepAdding ){
            //add u+m
            d = R.get(k, k)*(u+m) - yr[k];
            keepAdding = addToMap( prevmap, d*d, new List<>(u+m));
            //add u-m
            d = R.get(k, k)*(u-m) - yr[k];
            keepAdding |= addToMap( prevmap, d*d, new List<>(u-m));
            m++;
        }

        //now run the algorithm
        for(k = n-2; k >= 0; k--){

            TreeMap<Double, List<Integer>> nextmap = new TreeMap<>();
            
            for(int Mtimes = 0; Mtimes < prevmap.size(); Mtimes++){

                Entry<Double, List<Integer>> entry = prevmap.pollFirstEntry();
                List<Integer> vec = entry.getValue();
                double rdist = entry.getKey();
                //compute the sum of R[k][k+i]*uh[k+i]'s
                double rsum = 0.0;
                for(int i = k+1; i < n; i++ ){
                    int tu = vec.element;
                    rsum += tu*R.get(k, i);
                    vec = vec.tail;
                }
                u = (int)Math.round((yr[k] - rsum)/R.get(k,k));
                d = R.get(k, k)*u + rsum - yr[k];
                addToMap( nextmap, d*d + rdist, new List<>(u,entry.getValue()));

                m = 0;
                keepAdding = true;
                //this is being a bit lazy, it might use a little more that M. No big problem though.
                while(m < M/2 + 1 && keepAdding && (d*d + rdist) < D){
                    //add u+m
                    d = R.get(k, k)*(u+m) + rsum - yr[k];
                    keepAdding = addToMap( nextmap, d*d + rdist, new List<>(u+m,entry.getValue()));
                    //add u-m
                    d = R.get(k, k)*(u-m) + rsum - yr[k];
                    keepAdding |= addToMap( nextmap, d*d + rdist, new List<>(u-m,entry.getValue()));
                    m++;
                }
            }
            prevmap = nextmap;

        }

        //there is no garauntee that this point is better than the Babai point
        //so check it and update only if it is better.
        if(prevmap.size() != 0){
            Entry<Double, List<Integer>> entry = prevmap.firstEntry();

            if(entry.getKey() < D){

                //now the approximate nearest point is the best point in prevmap
                List<Integer> vec = entry.getValue();
                for(int i =0; i < n; i++ ) {
                    ubest[i] = vec.element.doubleValue();
                    vec = vec.tail;
                }
                
                //compute index u = Uuh so that Gu is nearest point
                VectorFunctions.matrixMultVector(U, ubest, this.u);

                //compute nearest point
                VectorFunctions.matrixMultVector(G, this.u, x);

            }
        }
        return getLatticePoint();
    }

    /**
     * Adds to a map but doesn't allow the size to get larger than M.
     * i.e. it's like a sorted buffer.
     * Also, only adds if the distance is less than D (i.e. the Babai distance).
     * Also returns whether the map was added to.
     */
    private boolean addToMap(TreeMap<Double, List<Integer>> map, double d, List<Integer> vec) {
        boolean addedToMap = false;
        if( d < D ){
            map.put(d, vec);
            addedToMap = true;
            //dump the worst entry if this map is sufficiently big.
            while( map.size() > M) map.pollLastEntry();
            addedToMap &= map.containsKey(d);
        }
        return addedToMap;
    }

    /** Extremely simple immutable singly linked list */
    public static class List<T> {

        public final List<T> tail;
        public final T element;

        public List(T element, List tail) {
            this.tail = tail;
            this.element = element;
        }

        public List(T element) {
            this(element, null);
        }

    }
    

}
