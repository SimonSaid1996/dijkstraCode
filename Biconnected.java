/*************************************************************************
 *  Compilation:  javac Biconnected.java
 *  Dependencies: Graph.java 
 *
 *  Identify articulation points and print them out.
 *  This can be used to decompose a graph into biconnected components.
 *  Runs in O(E + V) time.
 *
 *  http://www.cs.brown.edu/courses/cs016/book/slides/Connectivity2x2.pdf
 *
 *************************************************************************/

// Modified for CS 1501 Summer 2016
// same as the author's code

public class Biconnected {
    private int[] low;
    private int[] pre;
    private int cnt;
    private int bridges;
    private boolean[] articulation;

    public Biconnected(EdgeWeightedGraph G) {
        low = new int[G.V()];
        pre = new int[G.V()];
        articulation = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) low[v] = -1;
        for (int v = 0; v < G.V(); v++) pre[v] = -1;
        
        for (int v = 0; v < G.V(); v++)
            if (pre[v] == -1)
                dfs(G, v, v);
    }

    private void dfs(EdgeWeightedGraph G, int u, int v) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : G.adjInt(v)) {  // iterate through neighbors of current vertex
            if (pre[w] == -1) {	  // if neighbor has not yet been visited
                children++;		  // parent has another childe
                dfs(G, v, w);     // visit that child

                // update low number to minimum of previous low
                // for v and low of child (obtained recursively).  
                // 	  In other words, if child can get "back up" the tree in some
                // other way, then this vertex can do the same by going down to
                // the child and then back around.
                low[v] = Math.min(low[v], low[w]);

                // non-root of DFS is an articulation point if low[w] >= pre[v].
                //    In other words, the child vertex w cannot get to a vertex
                // above the parent vertex v without going through v.
                if (low[w] >= pre[v] && u != v) 
                    articulation[v] = true;
            }

            // update low number - ignore reverse of edge leading to v
            // In other words, we are using a back edge to get around
            // the parent and reach a higher DFS number
           else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }

        // root of DFS is an articulation point if it has more than 1 child
        if (u == v && children > 1)
            articulation[v] = true;
    }

    // is vertex v an articulation point?
    public boolean isArticulation(int v) { return articulation[v]; }

    // test client
    public static void main(String[] args) {		//don'e understand why no articulation points
	    /*Graph G = new Graph(6);
					Edge myEdge1 = new Edge(0, 1, (double) 8);	//set up the edge
					Edge myEdge2 = new Edge(0, 2, (double) 4);	//set up the edge
					Edge myEdge3 = new Edge(0, 4, (double) 5);	//set up the edge
					Edge myEdge4 = new Edge(1, 5, (double) 7);	//set up the edge
					Edge myEdge5 = new Edge(2, 4, (double) 3);	//set up the edge
					Edge myEdge6 = new Edge(3, 4, (double) 9);	//set up the edge
					Edge myEdge7 = new Edge(3, 5, (double) 6);	//set up the edge
					Edge myEdge8 = new Edge(4, 5, (double) 2);	//set up the edge
					

		G.addEdge(0, 1);
		G.addEdge( 0, 2 );
		G.addEdge( 0, 4 );
		G.addEdge( 1, 5 );
		G.addEdge( 2, 4 );
		G.addEdge( 3, 4 );
		//G.addEdge( 3, 5 );
		//G.addEdge( 4, 5 );
        Biconnected bic = new Biconnected(G);

        // print out articulation points
        System.out.println();
        System.out.println("Articulation points");
        System.out.println("-------------------");
        for (int v = 0; v < G.V(); v++)
            if (bic.isArticulation(v)) System.out.println(v);
		*/
    }

}
