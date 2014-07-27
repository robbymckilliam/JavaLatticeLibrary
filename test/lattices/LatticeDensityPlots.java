/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;
import static pubsim.Range.*;
import pubsim.lattices.Vnm.Vnm;

/**
 * Class draws a plot of the density of a lattice.
 * @author Robby McKilliam
 */
public class LatticeDensityPlots {

    public static void main(String[] args) throws Exception {


        int nstart = 1;
        int nend = 1500;
        int nstep = 1;
        Vector<Double> density = new Vector<Double>();

        int a = 6;
        for(int n : range(nstart, nend, nstep) ){
            LatticeInterface L = new Vnm(n, a);
            //Lattice L = new Craig(n, a);
            double d = L.logCenterDensity();
            //double d = L.inradius();
            //double d = L.logPackingDensity();
            //double d = L.kissingNumber();
            density.add(d);
            System.out.println(n + ", " + d + " , " + kissingIn2(n+a));
        }

        File file = new File( "Craig" + a + "_cdensity" );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for(int n : range(nstart, nend, nstep)){
            writer.write(n + "\t"
                    + density.get(count).toString().replace('E', 'e'));
            writer.newLine(  );
            count++;
        }
        writer.close();

    }

    public static int kissingIn2(int n){
        int d = n/2; //integer round is implicit.
        int sum = 0;
        for(int i = 2; i <= d; i++){
            sum += i*(i-1)*(n-2*i);
        }
        return 2*sum;
    }

}
