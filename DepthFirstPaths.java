/*************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java Stack.java
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  %  java Graph tinyCG.txt
 *  6 8
 *  0: 2 1 5 
 *  1: 0 2 
 *  2: 0 1 3 4 
 *  3: 5 4 2 
 *  4: 3 2 
 *  5: 3 0 
 *
 *  % java DepthFirstPaths tinyCG.txt 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 *************************************************************************/

// Modified for CS 1501 Summer 2016
// same as the author's code

public class DepthFirstPaths {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    public DepthFirstPaths(EdgeWeightedGraph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v
    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (int w : G.adjInt(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    // is there a path between s and v?
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // return a path between s to v; null if no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
			path.push(s);
        return path;
    }

  public static void main(String[] args) {
        //In in = new In(args[0]);
		
        /*Graph G = new Graph(6);
					Edge myEdge1 = new Edge(0, 1, (double) 8);	//set up the edge
					Edge myEdge2 = new Edge(0, 2, (double) 4);	//set up the edge
					Edge myEdge3 = new Edge(0, 4, (double) 5);	//set up the edge
					Edge myEdge4 = new Edge(1, 5, (double) 7);	//set up the edge
					Edge myEdge5 = new Edge(2, 4, (double) 3);	//set up the edge
					Edge myEdge6 = new Edge(3, 4, (double) 9);	//set up the edge
					Edge myEdge7 = new Edge(3, 5, (double) 6);	//set up the edge
					Edge myEdge8 = new Edge(4, 5, (double) 2);	//set up the edge
					

		//G.addEdge(0, 1);
		G.addEdge( 0, 2 );
		G.addEdge( 0, 4 );
		G.addEdge( 1, 5 );
		//G.addEdge( 2, 4 );
		//G.addEdge( 3, 4 );
		G.addEdge( 3, 5 );
		//G.addEdge( 4, 5 );
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                //StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }*/
    }

}