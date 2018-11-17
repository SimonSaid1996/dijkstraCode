/*************************************************************************
 *  Compilation:  javac Edge.java
 *  Execution:    java Edge
 *
 *  Immutable weighted edge.
 *
 *************************************************************************/

  // Modified for CS 1501 Summer 2016
  //changed v,w and weight not into final anymore because i need to change their value later
 
/**
 *  The <tt>Edge</tt> class represents a weighted edge in an undirected graph.
 *  <p>
 *  For additional documentation, see <a href="/algs4/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Edge implements Comparable<Edge> { 

    private /*final*/ int v;
    private /*final*/ int w;
    private /*final*/ double weight;
	//private boolean isMarked;			//default setting for isMarked is false, will be used for the p

   /**
     * Create an edge between v and w with given weight.
     */
    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
		//isMarked = false;
    }

   /**
     * Return the weight of this edge.
     */
    public double weight() {
        return weight;
    }

	
	
	//////////////////////////
	/**
     * reset the weight of this edge.
     */
	public void setWeight(double changedWeight) {
        weight = changedWeight;
    }
	
	public void swapVNW(){
		int tempv = this.v;
		int tempw = this.w;
		this.v = tempw;
		this.w = tempv;
		//System.out.println("swapped v "+tempv+" and w "+tempw);
	}
	
	
	/*public void hasVisited(){			//visited the edge
		isMarked = true;
	}
	
	public boolean getIsMarked(){		//to check if this edge has been visited
		return isMarked;
	}*/
	///////////////////
   /**
     * Return either endpoint of this edge.
     */
    public int either() {
        return v;
    }

   /**
     * Return the endpoint of this edge that is different from the given vertex
     * (unless a self-loop).
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Illegal endpoint");
    }

   /**
     * Compare edges by weight.
     */
    public int compareTo(Edge that) {
        if      (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else                                    return  0;
    }

   /**
     * Return a string representation of this edge.
     */
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }


   /**
     * Test client.
     */
    public static void main(String[] args) {
       /* Edge e = new Edge(12, 23, 3.14);
        StdOut.println(e);*/
    }
}


