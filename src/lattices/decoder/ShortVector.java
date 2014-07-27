/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder;

/**
 * Interface for computing short vectors in lattices
 * @author Robby McKilliam
 */
public interface ShortVector {
    
    public double[] getShortestVector();

    public double[] getShortestIndex();
    
}
