/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anm;

import java.util.Arrays;
import pubsim.IndexedDouble;
import pubsim.Util;
import pubsim.lattices.Anstar.Anstar;

/**
 * Implementation of the O(n log(n)) algorithm to find the nearest
 * lattice point in the Coxeter lattice A_{n/m}.  This was suggested
 * by Warren Smith.
 * @author Robby McKilliam
 */
public class AnmSorted extends Anm{
    
    private final IndexedDouble[] z;
    
    /** Constructor can set the m part of A_{n/m}. */
    public AnmSorted(int n, int m){
        super(n,m);
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n + 1; i++)
            z[i] = new IndexedDouble();
    }
    
    /** {@inheritDoc} */
    @Override
    public final void nearestPoint(double[] y){
        if (n != y.length-1) throw new RuntimeException("y is the wrong length");
        
        int gamma = 0;
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            gamma += Math.round(y[i]);
            z[i].value = y[i] - Math.round(y[i]);
            z[i].index = i;
            a += z[i].value;
            b += z[i].value * z[i].value;
        }
        gamma = Util.mod(gamma, m);
        
        Arrays.sort(z);
        
        double D = Double.POSITIVE_INFINITY;
        int k = 0;
        for(int i = 0; i < n+1; i++){
            double dist = b - a*a/(n+1);
            if(dist < D && gamma == 0){
                D = dist;
                k = i;
            }
            gamma = Util.mod(gamma + 1, this.m);
            a -= 1;
            b += -2*z[n-i].value + 1;
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < k; i++)
            u[z[n-i].index] += 1;
        
        Anstar.project(u, v);
           
    }

}
