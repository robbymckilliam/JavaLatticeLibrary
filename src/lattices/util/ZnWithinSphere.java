package pubsim.lattices.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Returns all the elements of the integer lattice contained in a sphere
 * about the origin of radius r.  
 * 
 * UNDER CONSTRUCTION.
 * Currently, this just does a cube of side length r
 * @author Robby McKilliam
 */
public class ZnWithinSphere implements Collection {

    protected double r;
    protected int n;
    protected double[] x;
    
    public ZnWithinSphere(){        
    }
    
    public ZnWithinSphere(int dimension, double radius){  
        setRadius(radius);
        setDimension(dimension);
    }
    
    /** Set the radius of the sphere to decode from */
    public void setRadius(double r){
        this.r = r;
        System.out.println("r = " + r);
    }
    
    /** Set the dimension of the lattice */
    public void setDimension(int n){
        this.n = n;
        x = new double[n];    
        System.out.println("n = " + n);
    }
    
    public Iterator iterator() {
        return new ZnWithinSphereIterator();
    }
    
    public class ZnWithinSphereIterator implements Iterator {

        protected boolean finished = false;
        
        public ZnWithinSphereIterator(){
            for(int i = 0; i < n; i++)
                x[i] = -Math.floor(r);
        }
        
        public boolean hasNext() {
            return !finished;
        }

        int count = 0;
        public double[] next() {
            addto(0);
            count++;
            if(count > Math.pow(2*Math.floor(r) + 1, n))
                finished = true;
            return x;
        }
        
        public void addto(int i){
            if(x[i] + 1 > r){
                x[i] = -Math.floor(r);
                if(i < n-1)
                    addto(i+1);
            }else{
                x[i]++;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
        
    }
    
    
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
