/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anm;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import pubsim.FastSelection;
import pubsim.IndexedDouble;
import pubsim.Util;
import pubsim.lattices.Anstar.AnstarVaughan;

/**
 * This is an O(n) bucket A_{n/m} nearest point
 * algorithm.  This uses the polynomial approximation
 * idea suggested by Warren.
 * @author Robby McKilliam
 */
public class AnmBucket extends Anm {

    private final IndexedDoubleList[] buckets;
    private final ListElem[] bes;
    private final double[] z;
    
    protected final int numBuckets;
    
    protected final FastSelection fselect;
    
    /** Constructor can set the m part of A_{n/m}. */
    public AnmBucket(int n, int m){
        super(n,m);
        z = new double[n+1];
        
        //setup the buckets.
        numBuckets = (n+1)/m;
        
        buckets = new IndexedDoubleList[numBuckets];
        for(int i = 0; i < numBuckets; i++)
            buckets[i] = new IndexedDoubleList();
                    
        bes = new ListElem[n+1];
        for(int i = 0; i < n + 1; i++){
            bes[i] = new ListElem();
            bes[i].elem = new IndexedDouble(0.0, i);
        }
        
        fselect = new FastSelection(n+1);
    }

    
    /** {@inheritDoc} */
    @Override
    public final void nearestPoint(double[] y){
        if (n != y.length-1) throw new RuntimeException("y is the wrong length");
        
        //make sure that the buckets are empty!
        for(int i = 0; i < numBuckets; i++)
            buckets[i].clear();
        
        double a = 0, b = 0;
        int k = 0;
        for(int i = 0; i < n+1; i++){
            z[i] = y[i] - Math.round(y[i]);
            k += Math.round(y[i]);
            bes[i].elem.value = -z[i];
            bes[i].elem.index = i;
            //int bi = numBuckets - 1 - (int)(Math.floor(numBuckets*(z[i]+0.5)));
            int bi = (int)(Math.ceil(numBuckets*(0.5 - z[i]))) - 1;
            buckets[bi].add(bes[i]);
            a += z[i];
            b += z[i] * z[i];
        }
        
        double D = Double.POSITIVE_INFINITY;
        int p = 0, bestbucket = 0;
        for(int i = 0; i < numBuckets; i++){
            
            //approximate value of z[i] in the bucket
            //this is modified to decrease the
            //approximation to it's minimum possible. 
            //double za = 0.5 - i/numBuckets;
            
            //get the first modularly admissble index in the bucket
            //int j = nearestMultM(k) - k;
            //int j = m*(int)Math.ceil(((double)k)/M) - k;
            int j = this.m - Util.mod(k, this.m);
            //if(j < 0) j+=m;
            
            //calculate the polynomial approximation
            //double p = b - 2*za*j + j - (a-j)*(a-j)/(n+1);

            //System.out.println("bucketsize = " + buckets[i].size());
            //System.out.println("g = " + j);

            
            //test the first modularly admissible point in the
            //bucket if it can be better than the current best point
            if( /*p < D &&*/ j > 0 && j <= buckets[i].size()){
            
                fselect.select(j, buckets[i]);
                
              //  if(!fselect.smallest().isEmpty() || j == 0){
                    double ad = a;
                    double bd = b;
                    Iterator itr = fselect.smallest().iterator();
                    while(itr.hasNext()){
                        int ind = ((IndexedDouble)itr.next()).index;
                        ad -= 1;
                        bd += -2*z[ind] + 1;   
                    }
                    double dist = bd - ad*ad/(n+1);
                    //System.out.println("dist = " + dist);
                    if(dist < D){
                        D = dist;
                        p = j;
                        bestbucket = i;
                    }
                //}
            }
            
            //get the last modularly admissble index in the bucket
            //j = nearestMultM(k + buckets[i].size()) - k;
            //j = m*(int)Math.floor(((double)k + buckets[i].size())/M) - k;
            j = buckets[i].size() - Util.mod(buckets[i].size() + k, this.m);
            //if(j > buckets[i].size()) j-=m;

            //System.out.println(", p = " + j);
            
            //calculate the polynomial approximation
           // p = b - 2*za*j + j - (a-j)*(a-j)/(n+1);
            
            //test the last modularly admissible point in the
            //bucket if it can be better than the current best point
            if(/*p < D &&*/ j > 0 && j <= buckets[i].size() ){
            
                fselect.select(j, buckets[i]);

             //   if(!fselect.smallest().isEmpty() || j == 0){
                    double ad = a;
                    double bd = b;
                    Iterator itr = fselect.smallest().iterator();
                    while(itr.hasNext()){
                        int ind = ((IndexedDouble)itr.next()).index;
                        ad -= 1;
                        bd += -2*z[ind] + 1;   
                    }
                    double dist = bd - ad*ad/(n+1);
                    //System.out.println("dist = " + dist);
                    if(dist < D){
                        D = dist;
                        p = j;
                        bestbucket = i;
                    }
               // }       
            }
            
            //add all the indices on for the next bucket
            IndexedDoubleListIterator itrd = buckets[i].iterator();
            while(itrd.hasNext()){
                int ind = itrd.next().index;
                a -= 1;
                b += -2*z[ind] + 1; 
                k++;
            }
           
            
        }
        
        //get the first element in the Bresenham set
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        //add all the buckets before the best on
        for(int i = 0; i < bestbucket; i++){
            IndexedDoubleListIterator itr = buckets[i].iterator();
            while(itr.hasNext()){
                int ind = ((IndexedDouble)itr.next()).index;
                //System.out.print(ind + ", ");
                u[ind]++;
            }
        }
        
        //fast select the best element of the best bucket and add the
        //previous elements
        fselect.select(p, buckets[bestbucket]);
        Iterator itr = fselect.smallest().iterator();
        while(itr.hasNext()){
            int ind = ((IndexedDouble)itr.next()).index;
            //System.out.print(ind + ", ");
            u[ind]++;
        }


        //project index to nearest lattice point
        AnstarVaughan.project(u, v);
        
    }
    
    /** Returns the integer multiple of m to v */
    public static int nearestMultM(double v, int M){
        return M*(int)Math.round(v/M);
    }
    
    /** Returns the integer multiple of m to v */
    protected int nearestMultM(double v){
        return m*(int)Math.round(v/m);
    }
    
    protected int nearestMultInRange(double v, double min, double max){
        return nearestMultInRange(v, min, max, m);
    }
    
     public static int nearestMultInRange(double v, double min, double max, int M){
        int j = nearestMultM(v, M);
        if( j < min ){
            j = nearestMultM(min, M);
            if(j < min)
                j += M;
        }else if( j > max ){
            j = nearestMultM(max, M);
            if(j > max)
                j -= M;
        }
        return j;
    }

    
    /** 
     * Specialised list implementation for the bucket
     * sorting algorithm.  This should be significantly
     * faster than java's list implementations.  It
     * allows a fixed memory implementation.
     * 
     * Also a toArray method is included to allow testing
     * before an implementation of fast sorting is made.
     */
    public static class IndexedDoubleList implements Collection, Serializable{
        protected int size;
        protected ListElem current, first;
        protected IndexedDoubleListIterator itr;
        
        public IndexedDoubleList(){
            first = new ListElem();
            current = first;
            current.next = null;
            size = 0;
            itr = new IndexedDoubleListIterator(this);
        }
        
        public boolean add(ListElem e){
            current.next = e;
            current = e;
            current.next = null;
            size++;
            return true;
        }
        
        @Override
        public void clear(){
            current = first;
            current.next = null;
            size = 0;
        }
        
        @Override
        public IndexedDoubleListIterator iterator(){
            itr.reset(this);
            return itr;
        }
        
        /** Returns the list as an array of indexed doubles. */
        @Override
        public IndexedDouble[] toArray(){
            IndexedDouble[] ret = new IndexedDouble[size];
            IndexedDoubleListIterator itra = this.iterator();
            int count = 0;
            while(itra.hasNext()){
                ret[count] = itra.next();
                count++;
            }
            return ret;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size==0;
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object[] toArray(Object[] a) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean add(Object e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean containsAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean addAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean removeAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean retainAll(Collection c) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    /** List element for IntList */
    public static class ListElem implements Serializable{
        protected ListElem next;
        protected IndexedDouble elem;
    }
    
    /** An iterator for IntList */
    public static class IndexedDoubleListIterator implements Iterator, Serializable{
        protected ListElem current;
        
        public IndexedDoubleListIterator(IndexedDoubleList list){
            current = list.first;
        }
        
        public void reset(IndexedDoubleList list){
            current = list.first;
        }
        
        @Override
        public boolean hasNext(){
            if(current.next == null) return false;
            else return true;
        }
        
        @Override
        public IndexedDouble next(){
            current = current.next;
            return current.elem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
    
}
