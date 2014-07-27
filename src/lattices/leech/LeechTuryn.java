package pubsim.lattices.leech;

import static pubsim.VectorFunctions.add;
import pubsim.lattices.E8;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.ScaledLattice;

/**
 * Nearest point algorithm for the Leech lattice based on the Turyn
 * construction of the Golay code. This algorithm was devised by 
 * Conway and Sloane in:
 * "Soft decoding techniques for codes and lattices, including the
 * Golay code and the Leech lattice", IEEE Trans. Info. Th., vol. 32, 1986.
 * 
 * UNFINISHED!
 * 
 * @author Robby McKilliam
 */
public class LeechTuryn extends Leech implements LatticeAndNearestPointAlgorithmInterface {
    
    protected static final double[][] aT
            = { {0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0},
                {2,2,2,2,0,0,0,0},
                {-2,2,2,2,0,0,0,0},
                {2,2,0,0,2,2,0,0},
                {-2,2,0,0,2,2,0,0},
                {2,2,0,0,0,0,2,2},
                {-2,2,0,0,0,0,2,2},
                {2,0,2,0,2,0,2,0},
                {-2,0,2,0,2,0,2,0},
                {2,0,2,0,0,2,0,2},
                {-2,0,2,0,0,2,0,2},
                {2,0,0,2,2,0,0,2},
                {-2,0,0,2,2,0,0,2},
                {2,0,0,2,0,2,2,0},
                {-2,0,0,2,0,2,2,0} };
    
    protected static final double[][] tT
            = { {0,0,0,0,0,0,0,0},
                {2,2,2,0,0,2,0,0},
                {2,2,0,2,0,0,0,2},
                {2,0,2,2,0,0,2,0},
                {0,2,2,2,2,0,0,0},
                {2,2,0,0,2,0,2,0},
                {2,0,2,0,2,0,0,2},
                {2,0,0,2,2,2,0,0},
                {-3,1,1,1,1,1,1,1},
                {3,-1,-1,1,1,-1,1,1},
                {3,-1,1,-1,1,1,1,-1},
                {3,1,-1,-1,1,1,-1,1},
                {3,1,1,1,1,-1,-1,-1},
                {3,-1,1,1,-1,1,-1,1},
                {3,1,-1,1,-1,1,1,-1},
                {3,1,1,-1,-1,-1,1,1} };
    
    protected final double[][][] atT = new double[16][16][8];
    
    protected final int[][] cT = new int[16][16];
    
    // an array of E8 nearest points
    protected final ScaledLattice[][][] E8list = new ScaledLattice[3][16][16];
    
    /** 
     * Constructor creates all the necessary tables to make this algorithm fast.
     * You ideally only want to call this constructor once.
     */
    public LeechTuryn(){
        
        //fill up the atT table with all the combinations of sums from aT and tT tables.
        for(int a = 0; a < 16; a++)
            for(int t = 0; t < 16; t++) 
                for(int k = 0; k < 8; k++) atT[a][t][k] = aT[a][k] + tT[t][k];
        
        //fill up the cT index table so that triples from the aT table are in 4*E8
        ScaledLattice fE8 = new ScaledLattice(new E8(), 4);
        for(int a = 0; a < 16; a++){
            for(int t = 0; t < 16; t++) {
                boolean bool = false;
                int c = -1;
                while(!bool){
                    c++;
                    double[] v = add(add(aT[a],aT[c]), aT[t]);
                    fE8.nearestPoint(v);
                    bool = fE8.distance() < 1e-9;
                }
                cT[a][t] = c;
            }
        }
        
        //a little test
       double[] v = add(add(aT[1],aT[2]), aT[cT[1][2]]);
       fE8.nearestPoint(v);
       assert(fE8.distance() < 1e-9);
       
       //construct aT list of E8 lattices
       for(int a = 0; a < 16; a++) 
            for(int t = 0; t < 16; t++) {
                E8list[0][a][t] = new ScaledLattice(new E8(), 4);
                E8list[1][a][t] = new ScaledLattice(new E8(), 4);
                E8list[2][a][t] = new ScaledLattice(new E8(), 4);
            }
        
    }
    
    
    protected final double[] y8t = new double[8];
    @Override
    public void nearestPoint(double[] y) {
        
        //compute the 3*256 required E8 points and their distances
        for(int a = 0; a < 16; a++){
            for(int t = 0; t < 16; t++) {
                for(int k = 0; k < 8; k++) y8t[k] = y[k] - atT[a][t][k];
                E8list[0][a][t].nearestPoint(y8t);
                
                for(int k = 0; k < 8; k++) y8t[k] = y[k+8] - atT[a][t][k];
                E8list[1][a][t].nearestPoint(y8t);
                
                for(int k = 0; k < 8; k++) y8t[k] = y[k+16] - atT[a][t][k];
                E8list[2][a][t].nearestPoint(y8t);
            }
        }
        
        //test all combinations of distances
        int besti = 0, bestj = 0;
        double bestdist = Double.POSITIVE_INFINITY;
        for(int a = 0; a < 16; a++)
            for(int b = 0; b < 16; b++)
                for(int t = 0; t < 16; t++) {
                
                }
        D = bestdist;
    }
    
    protected double D = Double.POSITIVE_INFINITY;
    @Override
    public double distance() {
        return D;
    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private double[] yDoubletoy = new double[24];
    @Override
    public void nearestPoint(Double[] y) {
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }

}
