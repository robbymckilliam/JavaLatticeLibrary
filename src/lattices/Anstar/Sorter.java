package pubsim.lattices.Anstar;

import java.util.Arrays;

/**
 * A utility class to sort an array of Comparables in such a way as
 * to return the indices of the original array on return.
 * @author Vaughan Clarkson, 24-Feb-05.
 * Changed sortIndices so that it doesn't do its own memory alloc, 16-Jun-05.
 */
public class Sorter {

    // Pre: result.length >= x.length

    public static void sortIndices(Comparable[] x, int[] result) {
	int n = x.length;
	ComparableHolder[] z = new ComparableHolder[n];
	for (int i = 0; i < n; i++)
	    z[i] = new ComparableHolder(x[i], i);
	Arrays.sort(z);
	for (int i = 0; i < n; i++)
	    result[i] = z[i].index;
    }

    public static class ComparableHolder implements Comparable {
	Comparable comparable;
	int index;

	ComparableHolder(Comparable comparable, int index) {
	    this.comparable = comparable;
	    this.index = index;
	}

	public int compareTo(Object o) {
	    ComparableHolder co = (ComparableHolder) o;
	    return comparable.compareTo(co.comparable);
	}
    }
}
