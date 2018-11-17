/*************************************************************************
 *  Compilation:  javac EdgeWeightedGraph.java
 *  Execution:    java EdgeWeightedGraph V E
 *  Dependencies: Bag.java Edge.java
 *
 *  An edge-weighted undirected graph, implemented using adjacency lists.
 *  Parallel edges and self-loops are permitted.
 *
 *************************************************************************/

/**
 *  The <tt>EdgeWeightedGraph</tt> class represents an undirected graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  in the graph, iterate over all of the neighbors incident to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

 
// Modified for CS 1501 Summer 2016
// i have changed v not into a final int so that i can change v's value

// also added a copyadj bag array and a adjint array to keep track the items in the graph
//distinctPath, validVertexToVisit, getEdges, nodeDown, adjInt and nodeUp are implemented by messed
// and i added copyadj and adjInt in the addEdge method
import java.util.List;
import java.util.*;

public class EdgeWeightedGraph {
    private /*final*/ int V;
    private int E;
    private Bag<Edge>[] adj;
	/////////////
	private Bag<Edge>[] copyAdj;
	private Bag<Integer>[] adjInt;
	//private static boolean[] isMarked;          // distTo[v] = distance  of shortest s->v path
	///////////
    
   /**
     * Create an empty edge-weighted graph with V vertices.
     */
    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
		copyAdj = (Bag<Edge>[]) new Bag[V];
		adjInt = (Bag<Integer>[]) new Bag[V];
		
        for (int v = 0; v < V; v++) {
			adj[v] = new Bag<Edge>();
			copyAdj[v] = new Bag<Edge>();
			adjInt[v] = new Bag<Integer>();
		}
    }


   /**
     * Create a random edge-weighted graph with V vertices and E edges.
     * The expected running time is proportional to V + E.
     */
    public EdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            double weight = Math.round(100 * Math.random()) / 100.0;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }
///////////////////////////
	public EdgeWeightedGraph copyGraph(EdgeWeightedGraph newGraph){
		/*for(int i =0; i< adj.length; i++){
			newGraph.adj[ i ] = this.adj[ i ];
			newGraph.copyAdj[ i ] = this.copyAdj[ i ]; 
			newGraph.adjInt[ i ] = this.adjInt[ i ];
		}*/
		System.out.println(newGraph);
		for(int i =0; i < this.V(); i++ ){
			for (Edge e : this.getEdges(i)) {
				newGraph.addEdge(e);
			}
		}
		
		return newGraph;
	}


	public boolean validVertexToVisit(int w, boolean[] isMarked) {	//check the to vertercy w with boolean isMarked, if isMarkedm can't visit there, return false. sooner check the weight
        for( int j = 0; j < V; j++){		//default setting
			if(isMarked[ j ] && j == w ){	// the to vertercy is marked, can't go
				//System.out.println("destination "+w+ " is marked");
				return false;
			}
		}
        return true;
    }


	public  Bag<Edge> getEdges( int v ){	//this function will get edges from a vertercy
		return adj[ v ];
	}

	//base case:1. reaches w
			  //2. exceed the top limit
			  //
	public void distinctPath(int v, int w, int finalGoal ,int AssignedWeight, double curWeight, boolean[] isMarked, Stack<Edge> path){
		isMarked[w] = true; //mark visited	
		Bag<Edge> curVertex = getEdges( w );		//get an vertex
		boolean hasSwapped = false;
		//System.out.println("marked is "+w);
		
		int curV = w;	//update the current vertecy

		for(Edge e : curVertex){// first check if visited vertex, if not, go there
			v = e.either();
			w = e.other( e.either() );
			if(curV != v){		//situations when i need to swap v and w like on vertecy 5 and use edge 4-5
					int temp = v;
					v = w;
					w = temp;
					hasSwapped = true;
			}
			if( (curWeight + e.weight() ) > AssignedWeight){
				//do nothing, exceed the limit
				//System.out.println("overweighted, v is "+e.either()+" w is "+e.other(e.either()));
			}
			else{
				
				if(w == finalGoal){		//reached the destination, base case
					curWeight = curWeight + e.weight();
					path.push(e);					//push to the stack
					System.out.println("reach the end "+" the total Weight is "+curWeight+" path is "+path+"\n");
					curWeight = curWeight - e.weight();			//add the weight
					path.pop();
				}
				else{
					if( validVertexToVisit(w, isMarked ) ){
						curWeight = curWeight + e.weight();
						path.push(e);
						distinctPath(v, w , finalGoal, AssignedWeight, curWeight, isMarked, path);
						curWeight = curWeight - e.weight();			//add the weight
						path.pop();
					}
				//System.out.println("next inside edge, current v is "+v+" w is "+w);				
				}
			}
			
		}
		isMarked[v] = false;		
	}		  
			  
	//write some if and else checking before the recursion for the weight		  
	public void distinctPath(int v, int w,int AssignedWeight){		//v is from and w is to vertercy   //this method will print out the pathes
		Bag<Edge> curVertex =getEdges( v );		//get an vertex
		Stack<Edge> path = new Stack<Edge>();		//not super sure how to store the path.......
		double curWeight = 0;
		boolean[] isMarked = new boolean[V];
			for( int j = 0; j < V; j++){//default setting
				isMarked[ j ] = false;
			}
		int temp = 0;
			if(v > w){	//swap the order if from vertercy is larger than to vertecy because the system doesn't support going from higher to the lower
				temp = v;
				v = w;
				w = temp;
			}
			int finalGoal = w;
			isMarked[v] = true;		//need to say we already visited the spot?
			//System.out.println("marked is "+v);
			for(Edge e : curVertex){// first check if visited vertex, if not, go there
				int tempV = e.either() ;
				int tempW = e.other( e.either() );
				if(tempV != v){	//swap the order if from vertercy is larger than to vertecy because the system doesn't support going from higher to the lower
					temp = tempV;
					tempV = tempW;
					tempW = temp;
				}	
				
				if(e.weight() > AssignedWeight){
					// if the weight is above the assigned weight in the first place, do nothing, skip
				}
				else{
					if(tempW == w){		//reached the destination, base case
						curWeight = curWeight + e.weight();			//add the weight
						path.push(e);					//push to the stack
						System.out.println("reach the end, the total weight is  "+curWeight+" the path is "+path+"\n");
						curWeight = curWeight - e.weight();			//add the weight
						path.pop();
					}
					else{
						if( validVertexToVisit(tempW, isMarked ) ){
							curWeight = curWeight + e.weight();			//add the weight
							path.push(e);//pass a new stack edge with the same info?
							distinctPath(tempV, tempW , finalGoal , AssignedWeight, curWeight, isMarked,path);
							curWeight = curWeight - e.weight();			//add the weight
							path.pop();
						}		
						//System.out.println("next edge outside, current is "+e.either()+" w is "+e.other(e.either()));
					}
				}
				
				
			}
	}
	

//////////////////////////	
	public void NodeDown ( int verticesN ){		//the assigned vertex will be gone and it's following edges will be gone also
		//need to delete one row of the array
		//another issue is that should we resize the array? if we resize the array, the index of the vertices might be messed up though
		//Bag newAdj = (Bag<Edge>[]) new Bag[V-1];//shrink insize
		for(int i = 0; i < V; i++){		//must call this function outside, to convert the edgeweighted graph into directed 
			//if( i == verticesN){
				//System.out.println("hola");
				for(Edge e : this.adj[i]){
					if(e.either() == verticesN || e.other(e.either()) == verticesN){
						this.adj[i].delete(e);
						//System.out.println("deleteing in adjint "+ e.either() +" 's edge "+ e.other(e.either()) );
						this.adjInt[ i ].delete( e.other(/*e.either()*/i) );				//this part has error too
						//this.adjInt[ e.other(e.either()) ].delete( e.either() );
						E--;
					}
				}
		}
		
		//deleteOneEdge(verticesN, i);
		//V--;		//also here, 
	}
	
	
	public void NodeUp (int verticesN, List< Integer > downNode ){	//to put the vertices back
		if( verticesN > V){
			//do nothing, can't back up a node that didn't exist before
		}
		else{
			for(int i = 0; i < V; i++){		//must call this function outside, to convert the edgeweighted graph into directed 
				for(Edge e : this.copyAdj[i]){
					if(e.either() == verticesN || e.other(e.either()) == verticesN){
						boolean inDownNode = false;
						for(int j = 0; j < downNode.size(); j ++){
							if(j == i || j == e.other(i) )
								inDownNode = true;
						}
						if( !inDownNode ){		//have to check if the edges are in the downnode, if so, can't add
							this.adj[i].add(e);
							this.adjInt[ i ].add( e.other(/*e.either()*/i) );				//add the edges back
							E++;
						}
						
						
					}
				}
			}
		}
	}
	
	 public Iterable<Integer> adjInt(int v) {
        return adjInt[v];
    }
	
////////////////	
   /**
     * Create a weighted graph from input stream.
     */
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

   /**
     * Return the number of vertices in this graph.
     */
    public int V() {
        return V;
    }

   /**
     * Return the number of edges in this graph.
     */
    public int E() {
        return E;
    }


   /**
     * Add the edge e to this graph.
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
		System.out.println("v is "+v);
        adj[v].add(e);
        adj[w].add(e);
		copyAdj[v].add(e);
        copyAdj[w].add(e);
		adjInt[v].add(w);
        adjInt[w].add(v);
        E++;
    }

///////////////////////////////////////////////// i think i can make some methods to reset the E and adj, and then added another method called addDiEdge, then this graph will become the directed edge
//////////////////////////////////////////////// or you just need a function called change into directed graph, which delete the other() side of the adj	
	/**
     * change the graph into directed edgeweighted graph
	 * only delete the edges that don't started from V
     */
	/*public void deleteOneEdge(Edge e, int i){
		int v = e.either();
		int w = e.other(v);
			if(v != i){
				adj[w].delete(e);
				//E--;
			}		
	}
	
	public void restoreOneEdge(Edge e, int i){
		int v = e.either();
		int w = e.other(v);
			if(v != i){
				adj[w].add(e);
				//E++;
			}		
	}
	
	public void convertToDiEdge(){
		for(int i = 0; i < V; i++){		//must call this function outside, to convert the edgeweighted graph into directed 
			for(Edge e : this.adj[i]){
				if(e.either() != i){
					System.out.println("i is "+i+" v is "+e.either()+" w is "+e.other(e.either()));
					e.swapVNW();
					//it swapped every one of the vertices, and i don't know why
				}
			}
		}
	}
	
	public void convertBack(){					//check if there are same edges first, if no same, then restore the edge
	//boolean haveSameEdge = false;
	adj = (Bag<Edge>[]) new Bag[V];
    for (int v = 0; v < V; v++) adj[v] = new Bag<Edge>();//reset adj
	
		for(int i = 0; i < V; i++){		//must call this function outside, to convert the edgeweighted graph into directed 
			for(Edge e : this.copyAdj[i]){		//need to add a check about if the edge is in the graph or not
				adj[i].add(e);	//add every edges back
			}
		}
	}*/
///////////////////////////////////////////////	
	
	public void changeEdgeWeight(int v, int w, int changedWeight){
		//first find if the edge exist, if exist, just change the weight, if not, creat a new edge
		//need to check two edges here, because this is an undirected graph
		
		System.out.println("changedweight is "+changedWeight);
		boolean edgeExist = false; //default
		for(int i = 0; i < V; i++){		//must call this function outside, to convert the edgeweighted graph into directed 
			for(Edge e : this.adj[i]){
				if( (e.either() == v && e.other(e.either()) == w) || (e.either() == w && e.other(e.either()) == v) ){//situations where the edge already exist
					if(changedWeight > 0){
						e.setWeight(changedWeight);			//update the weight
					}
					else{		//delete the dege
						this.adj[i].delete( e );				//situation to delete the edge
						this.copyAdj[i].delete(e);			//keep two arrays the same 
						E--;
					}
					edgeExist = true;
				}
			}
		}
		if(!edgeExist){				//situations to add the new edge, two edges
			if(v > V || w > V){
				System.out.println("didn't support this opertaion yet, can't create more vertecy that didn't exist before");
			}
			else{
				Edge myEdge = new Edge(v, w, (double) changedWeight);	//set up the edge
				adj[v].add(myEdge);
				adj[w].add(myEdge);
				copyAdj[v].add(myEdge);
				copyAdj[w].add(myEdge);
				E = E+2;
			}
		}
		
	}
///////////////////////////////////////	
   /**
     * Return the edges incident to vertex v as an Iterable.
     * To iterate over the edges incident to vertex v, use foreach notation:
     * <tt>for (Edge e : graph.adj(v))</tt>.
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

   /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges, use foreach notation:
     * <tt>for (Edge e : graph.edges())</tt>.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }



   /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

   /**
     * Test client.
     */
    public static void main(String[] args) {
        /*EdgeWeightedGraph G;
        if (args.length == 0) {
            // read graph from stdin
            G = new EdgeWeightedGraph(new In());
        }
        else {
            // random graph with V vertices and E edges, parallel edges allowed
            int V = Integer.parseInt(args[0]);
            int E = Integer.parseInt(args[1]);
            G = new EdgeWeightedGraph(V, E);
        }
        StdOut.println(G);*/
		EdgeWeightedGraph G;
		G = new EdgeWeightedGraph(6);
		Edge myEdge1 = new Edge(0, 1, (double) 8);
		Edge myEdge2 = new Edge(0, 2, (double) 4);
		Edge myEdge3 = new Edge(0, 4, (double) 5);
		Edge myEdge4 = new Edge(1, 5, (double) 7);
		Edge myEdge5 = new Edge(2, 4, (double) 7);
		G.addEdge(myEdge1);
		G.addEdge(myEdge2);
		G.addEdge(myEdge3);
		G.addEdge(myEdge4);
		G.addEdge(myEdge5);
		System.out.println(G);
		
		System.out.println("after the change");
		/*for(int i = 0; i < 6; i++){		//must call this function outside, to convert the edgeweighted graph into directed 
			for(Edge e : G.adj[i]){
				G.deleteOneEdge(e,i);
			}
		}*/
		//G.convertToDiEdge();		
		System.out.println(G);

		
    }

}