/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Vector;
import static pubsim.Range.range;
import pubsim.distributions.Gaussian;
import pubsim.lattices.Anm.AnmGlued;

/**
 * Runs computational trials on lattices.
 * @author Robby McKilliam
 */
public class LatticeTiming {

     /**
     * Main function for testing the practical running times
     * of nearest point algorithms.
     */
    public static void main(String[] args) throws Exception {

        System.out.println("timingTest");

        //int numTrials = 20000;
        int numTrials = 200;
        Gaussian rand = new Gaussian(0.0, 1000.0);
        Vector<Double> timearray = new Vector<Double>();

        int nstart = 4;
        int nend = 250;
        int nstep = 16;

        LatticeAndNearestPointAlgorithmInterface lattice = null;

        for(int n : range(nstart, nend, nstep) ){

            int M = 4;
            double[] y = new double[n];

            //lattice = new AnSorted(n-1);
            //lattice = new AnFastSelect(n-1);
            //lattice = new AnstarSorted(n-1);
            //lattice = new AnmLinear(n-1,M);
            lattice = new AnmGlued(n-1,M);
            //lattice = new AnmSorted(n-1,M);

            Date timer = new Date();
            long start = timer.getTime();

            for(int i=0; i<numTrials; i++){
                for(int k = 0; k < n; k++){
                    y[k] = rand.noise();
                }
                lattice.nearestPoint(y);
            }
            timer = new Date();
            long end = timer.getTime();
            double runtime = (end - start)/1000.0;
            System.out.println("n = " + n + " time = " + runtime);
            timearray.add(runtime);
            //assertTrue(true);
        }

        String fname = (lattice.getClass().toString() + "_m4").replace(' ', '.');
        File file = new File( fname );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for(int n : range(nstart, nend, nstep)){
            writer.write(
                    n
                    + "\t" + timearray.get(count).toString().replace('E', 'e'));
            writer.newLine();
            count++;
        }
        writer.close();

    }

}
