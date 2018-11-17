/*************************************************************************
 *  Compilation:  javac DijkstraSP.java
 *  Execution:    java DijkstraSP V E
 *  Dependencies: EdgeWeightedGraph.java IndexMinPQ.java Stack.java DirectedEdge.java
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *************************************************************************/

 // Modified for CS 1501 Summer 2016
// i only changed the parameter type from EdgeWeightedDigraph into EdgeWeightedGraph, from() into either and to() into other()
public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    public DijkstraSP(EdgeWeightedGraph G, int s) {
		for (Edge e : G.edges()) {//find out the difference between directededgegraph and the edgeweightedgraph
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
		
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
				//System.out.println("disto is "+distTo);
		//validateVertex(s);
		
		
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
			distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());					//i think i need to do something like v*2
        pq.insert(s, distTo[s]);

		
		//the number of insertion is not right?
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e);				//something was wrong here
        }
        // check optimality conditions
        assert check(G, s);	
		
    }

    // relax edge e and update pq if changed
    private void relax(Edge e) {
        int v = e.either(), w = e.other(e.either());
		System.out.println("v is "+v+" w is "+w+"\n disto w is "+distTo[w]+"disto v is "+distTo[v]+" e weight is "+e.weight());
        if ((distTo[w] > distTo[v] + e.weight())||(distTo[w] == 0)) {
            distTo[w] = distTo[v] + e.weight();
			System.out.println("fuck you");
            edgeTo[w] = e;
            if (pq.contains(w)) pq.change(w, distTo[w]);
            else                pq.insert(w, distTo[w]);	
        }
    }

    // length of shortest path from s to v
    public double distTo(int v) {
        return distTo[v];
    }

    // is there a path from s to v?
    public boolean hasPathTo(int v) {
		//System.out.println("i hate you, disto is "+distTo[v]);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
	
    // shortest path from s to v as an Iterable, null if no such path
    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;				//find it why this is null
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.either()]) {//think about why this
            path.push(e);
			//System.out.println("e is "+e);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedGraph G, int s) {

        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(e.either());
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            int v = e.either();
            if (w != e.other(e.either())) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        /*EdgeWeightedGraph G;

        if (args.length == 0) {
            // read digraph from stdin
            G = new EdgeWeightedGraph(new In());
        }
        else {
            // random digraph with V vertices and E edges, parallel edges allowed
            int V = Integer.parseInt(args[0]);
            int E = Integer.parseInt(args[1]);
            G = new EdgeWeightedGraph(V, E);
        }

        // print graph
        StdOut.println("Graph");
        StdOut.println("--------------");
        StdOut.println(G);


        // run Dijksra's algorithm from vertex 0
        int s = 0;
        DijkstraSP sp = new DijkstraSP(G, s);
        StdOut.println();


        // print shortest path
        StdOut.println("Shortest paths from " + s);
        StdOut.println("------------------------");
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                for (Edge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, v);
            }
        }*/
    }

}