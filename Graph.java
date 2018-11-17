/** this is the actual class Graph i wrote
*	writer: Ziran Cao
*
*
*/
import java.util.List;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Graph {
    private static int VertexN = 0;//to record the number of vertex
	private static int EdgeN = 0;
	//private static boolean[] VertexMarked;          // distTo[v] = distance  of shortest s->v path
	private static EdgeWeightedGraph Eg = null;
	//private static EdgeWeightedDigraph Edg = null;
    private static MinPQ<Integer> pq;						//remember to store everyone of the pq	
    private static KruskalMST KMST = null;//remember to store everyone of the pq
	private static List< Integer > downNode;
	private static List< Integer > articulatePoints;
	private static List< Integer > PrevArticulatePoints;
	private static DijkstraAllPairsSP DiPairs=null;	//need to store the length and calculate the speed

	
    public static void main(String[] args) {
		
		String FILENAME = null;//"graph1.txt";
		int lineCount = 0;
		int fromEdge = 0;
		int toEdge = 0;
		int weight = 0;
		
		if(args.length != 1){
			System.out.println("please enter the file you want to build a network with");
		}
        else{
			FILENAME = args[0]+".txt";	//get the file name			
		}
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
		   String sCurrentLine;
			while (( sCurrentLine = br.readLine()) != null ) {	//set up the vertexN
				if( lineCount == 0 ){
					VertexN = Integer.parseInt( sCurrentLine );
					Eg = new EdgeWeightedGraph(VertexN);//initialize the weighted graph
					//Edg =new EdgeWeightedDigraph(VertexN);//initialize the weighted graph
					//VertexMarked=new boolean[VertexN];
					downNode = new ArrayList< Integer >();
					articulatePoints = new ArrayList< Integer >();
					PrevArticulatePoints = new ArrayList< Integer >();
						/*for(int j=0;j<VertexN;j++){//default setting
							VertexMarked[j]=false;
						}*/						
					
				}
				else if( lineCount == 1 ){	//set up the edgeN
					EdgeN = Integer.parseInt( sCurrentLine );
				}
				else{	//initialize the edges 
					String[] words = sCurrentLine.split("\\s+");
						for (int i = 0; i < words.length; i++) {					
							words[i] = words[i].replaceAll("[^\\w]", "");
						}
					fromEdge = Integer.parseInt(words[0]);
					toEdge = Integer.parseInt(words[1]);
					weight = Integer.parseInt(words[2]);
					Edge myEdge = new Edge(fromEdge, toEdge, (double) weight);	//set up the edge
					//DirectedEdge myEdge1= new DirectedEdge(fromEdge, toEdge, (double) weight);
					//DirectedEdge myEdge2= new DirectedEdge(toEdge, fromEdge, (double) weight);
					
					//Edg.addEdge(myEdge1);
					//Edg.addEdge(myEdge2);
					Eg.addEdge( myEdge );	//edge weithted graph add undirected edges
					//VertexMarked[fromEdge]=true;	//mark connected
					//VertexMarked[toEdge]=true;
				}
				lineCount++;
				//DiPairs=new DijkstraAllPairsSP(Edg);//initialize dijkstra instance here			
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
							
		String userInput = null;
		String[] inputChar;				//my array size is always 1 for some reasons, have no idea why
		
		
		//add the articulation points first, then do things
					
		Biconnected bic1 = new Biconnected(Eg);			
			if(articulatePoints.size() == 0){			//need to initialize if it wasn't there
				for (int v = 0; v < Eg.V(); v++){									//the curarticulation point is changing everytime, i need to store the preious articulation points everytime and delete them
					if (bic1.isArticulation( v ) ){
						//System.out.println("articulate point is "+v);
						articulatePoints.add( v );									//add the articulate points
						PrevArticulatePoints.add( v );
					} 
				}
			}
		
		
		do{
			Scanner reader = new Scanner(System.in);
			System.out.println("please enter the operations that you want to do:\n 1.R \n 2.M \n 3.S I J( I,J are verteercies )\n 4.P I J X(display distince path between I to J with equal or less than X weight)\n 5.D I"+
			"(Node i will go down)\n 6.U I(Node i will go up) \n 7.C I J X(change edge weight between i and j into x )\n 8.A i j x(add new verteercies, i is the from, j is to and x is the weight) \n 9.Q(quit) ");
			userInput=reader.nextLine();
			inputChar = userInput.split("\\s+");
				
				if(inputChar[0].equals( "R" )){	//report
					// use hasvisited array to check if connected, then use depthfirst first to print the connected components
					boolean isConnected = true;
					System.out.println("connected situation is: ");

					for(int i = 0; i< PrevArticulatePoints.size(); i++){
						for(int j = 0;j < downNode.size() ; j++){					//connected has some issues, should find articulate points
							//System.out.println("i is "+downNode.get( j )+" j is "+PrevArticulatePoints.get(i));
							if(downNode.get( j ) == PrevArticulatePoints.get( i ) ){
								//System.out.println("changed into false");
								isConnected = false;
							}
						}
					}
						
					if(isConnected){
						System.out.println("is connected");
					}
					else{
						System.out.println("not connected");
					}
					System.out.println("the following node are down: ");
						for(int i = 0; i < downNode.size(); i++ ){
							System.out.print( downNode.get(i)+" " );
						}
					System.out.println("\nthe nodes up are: ");
					boolean inDownNode = false;
						for(int i = 0; i < Eg.V(); i++){
							for(int j = 0; j < downNode.size(); j++ ){
								if( i == downNode.get( j )){
									inDownNode = true;
								}
							}
							if(!inDownNode){
								System.out.print(i+" ");
							}
							inDownNode = false;
						}
					System.out.println("\nconnected conponent(iterate from an up node and print out the connected nodes)");
					
					
					for(int s = 0; s < Eg.V(); s ++){
						DepthFirstPaths dfs = new DepthFirstPaths(Eg, s);
							for (int v = 0; v < Eg.V(); v++) {
								if (dfs.hasPathTo(v)) {
									System.out.print( s+" to "+ v+" : ");
									for (int x : dfs.pathTo(v)) {
										if (x == s) System.out.print(x);
										else        System.out.print("-" + x);
									}
								System.out.println();
								}
							else {
								//System.out.print("not connected\n");
							}
						}
					}
					
					
				}
				if(inputChar[0].equals( "M" )){	//MST
					System.out.println("the edges in the MST follows: ");
					KruskalMST myK = new KruskalMST( Eg );
					for (Edge e : myK.edges()) {
						StdOut.println(e);
					}	
					
				}
				if(inputChar[0].equals( "S" )){	//shortest path, need to convert the edgeweighted graph first and then use it, remember to use a copy of the graph, because we might need it later 
					int userFrom = 0;
					int userTo = 0;
					userFrom = Integer.parseInt(inputChar[ 1 ]);
					userTo=Integer.parseInt(inputChar[ 2 ]);
					
					
					int temp = 0;
						if(userFrom > userTo){		//swap if the order is not correct
							temp = userFrom;
							userFrom = userTo;
							userTo = temp;
						}
					DiPairs=new DijkstraAllPairsSP(Eg);//initialize dijkstra instance here	
					
					
					Iterable<Edge> path=DiPairs.path(userFrom,userTo);//this iterable class is a stack, try to get information out of the stack
										
					System.out.println("path is "+path);//still have some issues with the output but generally working
					
					//DijkstraSP Dipath = new DijkstraSP(Eg, userFrom);
					
					//DijkstraAllPairsSP Dipath1 = new DijkstraAllPairsSP(Edg/*, userFrom*/);
					//Iterable<Edge> path = Dipath.pathTo(userTo);
								//DiPairs=new DijkstraAllPairsSP(EDG);//initialize dijkstra instance here			

					//Iterable<DirectedEdge> path1 =Dipath1.path(userTo, userFrom);
					//System.out.println("path1 is "+path1);					
					//System.out.println("path is "+path);			//still have some issues with the output but generally working
					
				}
				if(inputChar[0].equals( "P" )){	//display distinct path
					
					int userFrom = 0;
					int userTo = 0;
					int assignedWeight = 0;
					userFrom = Integer.parseInt(inputChar[ 1 ]);
					userTo = Integer.parseInt(inputChar[ 2 ]);
					assignedWeight = Integer.parseInt(inputChar[ 3 ]);
					int temp = 0;
					if(userFrom > userTo){		//swap if the order is not correct
							temp = userFrom;
							userFrom = userTo;
							userTo = temp;
						}
					Eg.distinctPath(userFrom, userTo, assignedWeight );
					//get the starting edge first
					
					
				}
				if(inputChar[0].equals( "D" )){	//node down, when putting the edges down, i can just set up a valid int of the vertices number, then print out the number there
						
						int downNodeN =	Integer.parseInt(inputChar[1] );
						Eg.NodeDown( downNodeN );
						downNode.add( downNodeN );	
						System.out.println("after the operation "+ downNodeN +" is down");
						
				}
				if(inputChar[0].equals( "U" )){	//node back up, still have some issues, but can't do anything
						//System.out.println("verteercy back up");
						//System.out.println(Eg);
						int upNodeN = Integer.parseInt(inputChar[ 1 ] ); 
						int index = downNode.indexOf( upNodeN );	//-1 means not in the arraylist
						if( index != -1 ){		//check if the node exist before, if not exist, don't do anything
							downNode.remove( index );
						}
						Eg.NodeUp( upNodeN, downNode );
						
						System.out.println("after the operation "+ upNodeN +" is up");
						//System.out.println(Eg);
				}
				if(inputChar[0].equals( "C" )){	//change weight
					System.out.println("verteercy back up");
					//System.out.println(Eg);
					//System.out.println("weight input is "+inputChar[ 3 ]);					//the way of breaking the input is not correct
					Eg.changeEdgeWeight( Integer.parseInt( inputChar[ 1 ] ), Integer.parseInt( inputChar[ 2 ] ), Integer.parseInt( inputChar[ 3 ]) ); 
					//System.out.println("after the operation");
					//System.out.println(Eg);
				}
				if(inputChar[0].equals( "A" )){	//add new vertice
					int userFrom = 0;
					int userTo = 0;
					int userWeight = 0;
					userFrom = Integer.parseInt(inputChar[ 1 ]);
					userTo = Integer.parseInt(inputChar[ 2 ]);
					userWeight = Integer.parseInt(inputChar[ 3 ]);
					Edge myEdge = new Edge(userFrom, userTo, (double) userWeight);	//set up the edge
					EdgeWeightedGraph tempEg = null;
					if(userFrom > VertexN){
						 tempEg = new EdgeWeightedGraph(userFrom);//initialize the weighted graph
					}
					if(userTo > VertexN){
						 tempEg = new EdgeWeightedGraph(userTo);//initialize the weighted graph
					}
					tempEg = Eg.copyGraph(tempEg);
					Eg = tempEg;		//switch to the new graph
					Eg.addEdge( myEdge );	//edge weithted graph add undirected edges
					System.out.println("the associated edges with verteercy "+userFrom+" are ");
					for (Edge e : Eg.getEdges(userFrom)) {
						StdOut.println(e);
					}		
					
				}
				if(inputChar[0].equals( "Q" )){	//quit
					System.out.println( "exiting" );
				}
				PrevArticulatePoints.clear();
				for(int i = 0; i < articulatePoints.size(); i++){
					PrevArticulatePoints.add( articulatePoints.get( i ) );
				}
				Biconnected bic = new Biconnected(Eg);
				articulatePoints.clear();											//clear previous data			
				for (int v = 0; v < Eg.V(); v++){									//the curarticulation point is changing everytime, i need to store the preious articulation points everytime and delete them
					if (bic.isArticulation( v ) ){
						//System.out.println("articulate point in loop is "+v);
						articulatePoints.add( v );									//add the articulate points
					} 
				}
		}while( !inputChar[0].equals("Q") );						        

    }

}

