/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import java.util.*;
//Kerilee Bookleiner
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private double weight;
    private LinkedList<Edge>[] adj; //Linked list of edges --- adjacency list
    private ArrayList<String> vertices; //cities
    
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        //use linked list instead of a bag for adjacency list
        adj = (LinkedList<Edge>[]) new LinkedList[V]; 
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<Edge>();
        }  
    }
   
    
    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }


    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }


    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }

	public void setVerticesArrayList(ArrayList<String> vertices){
		this.vertices = vertices;
	}
	
	
    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(edge);
        adj[w].add(edge);
    }


	public void removeEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        E--;
        adj[v].remove(edge);
        adj[w].remove(edge);
    }
    
    /**
     * Returns the vertices adjacent to vertex <tt>v</tt>.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex <tt>v</tt>, as an iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
	public Iterable<Edge> adj(int v) {
    	validateVertex(v);
    	return adj[v];
	}


	public Iterable<Edge> edges(){
		LinkedList<Edge> list = new LinkedList<Edge>();
		for(int v = 0; v < V; v++){
			int selfLoops =0;
			for(Edge e: adj(v)){
				if(e.other(v) > v){
					list.add(e);
				}
				else if(e.other(v) == v){
					if(selfLoops % 2 == 0){
						list.add(e);
					}
					selfLoops++;
				}
			}
		}

		return list;
	}



    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\nROUTES:\n");
		s.append("--------------------------------\n");

		for (int v = 0; v < V; v++) {
			s.append((v + 1)+ ": ");
			for (Edge edge : adj[v]) {
				s.append(edge.toString(vertices, false) + " ");
			}
			s.append("\n\n");
		}
		return s.toString();
	}


	////////For minimum spanning tree/////////////////////////////////////////////////////////
	public String prim(){
		boolean[] marked = new boolean[V];
		StringBuilder minimumSpanning = new StringBuilder("\nMINIMUM SPANNING TREE (In Order)\n");
		minimumSpanning.append("---------------------\n");
		prim(0, marked, minimumSpanning);
		for(int i = 0; i < V; i++){
			if(!marked[i] && adj[i].isEmpty()){
				minimumSpanning.append("Not connected"); 
				prim(i, marked, minimumSpanning); 
			}
					// check optimality conditions
		assert check();
		}
		return minimumSpanning.toString();
	}

	public void prim(int s, boolean[] marked, StringBuilder minimumSpanning){
		//double weight = 0;
		Queue<Edge> path = new Queue<Edge>();
		MinPQ<Edge> pq = new MinPQ<Edge>();
		for(int i = 0; i < V; i++){
			for(Edge edge: adj(i)){
				edge.changeToDistance();
			}
		}
		scan(s, marked, pq); 
		while(!pq.isEmpty()){ 
			Edge e = pq.delMin(); 
			int v = e.either(), w = e.other(v); 
			if(marked[v] && marked[w]){ 
				continue; 
			}
			path.enqueue(e); 
			weight += e.weight(); 
			if(!marked[v]){
				scan(v, marked, pq);
			}
			if(!marked[w]){
				scan(w,marked, pq);
			}
		}
		minimumSpanning.append("Edges in the minimum spanning tree follow:\n");
		while(!path.isEmpty()){
			minimumSpanning.append(path.dequeue().toString(vertices, true) + "\n"); //edge tostring will know printing min spanning tree
		}
	}

	private void scan(int v, boolean[] marked, MinPQ<Edge> pq){
		marked[v] = true;
		for(Edge e: adj(v)){
			if(!marked[e.other(v)]){
				pq.insert(e);
			}
		}
	}	

	public double weight(){
		return weight;
	}


	private boolean check() {
			// check weight
			double totalWeight = 0.0;
			for (Edge e : edges()) {
				totalWeight += e.weight();
			}
			double EPSILON = 1E-12;
			if (Math.abs(totalWeight - weight()) > EPSILON) {
				System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
				return false;
			}
			return true;
		}




	//////shorted path based on price or miles///////// choice 3 or 4
	public String dijkstra(int s, int d, String pathType){
		double[] distTo = new double[V]; //distance of shortest s->v path
		Edge[] edgeTo = new Edge[V]; //Last edge on shortest s-> path
		double[] weightArray = new double[V];
		int[] parentOf = new int[V];
		IndexMinPQ<Double> pq = new IndexMinPQ<Double>(V); //priority queue of vertices
		Stack<Integer> path = new Stack<Integer>();
		boolean found = false;
		//Accounting for having two weights for each edge. 
		if(pathType.equals("Distance")){
			for(int i = 0; i < V; i++){
				for(Edge edge: adj(i)){
					edge.changeToDistance();
				}
			}
		}
		else if(pathType.equals("Price")){
			for(int i = 0; i < V; i++){
				for(Edge edge: adj(i)){
					edge.changeToPrice();
				}
			}
		}
		for(int v = 0; v < V; v++){  
			distTo[v] = Double.POSITIVE_INFINITY;   
			weightArray[v] = 0.0;
			parentOf[v] = 0;
		}
		distTo[s] = 0.0;
		parentOf[s] = s;
		pq.insert(s, distTo[s]);
		while(!pq.isEmpty()){ 
			int v = pq.delMin();
			for(Edge e: adj(v)){
				relax(v, e, pq, distTo, edgeTo, weightArray, parentOf);
			}
		
			if(v == d){ //if get to the destination then we are done
				break;
			}
			if(pq.isEmpty()){
				return "No path exists\n";
			}
		}
		path.push(d);
		int v = parentOf[d];
		while( v != s){
			path.push(v);
			v = parentOf[v];
		}
		path.push(s);
		StringBuilder shortestPath = new StringBuilder("\nSHORTEST " + pathType + " PATH from " + vertices.get(s) + " to " + vertices.get(d) + "(In order)");
		shortestPath.append("\n---------------------------------------------------");
		shortestPath.append("\nShortest " + pathType + " path " + vertices.get(s) + " to " + vertices.get(d) + " is " + distTo[d] + "\n");
		shortestPath.append("Path with edges (In Order): \n");
		while(!path.isEmpty()){
			v = path.pop();
			if(weightArray[v] != 0){
				shortestPath.append(weightArray[v] + " ");
			}
			shortestPath.append(vertices.get(v) + " "); 
		}
		return shortestPath.toString();
	}

	private void relax(int v, Edge e, IndexMinPQ<Double> pq, double[] distTo, Edge[] edgeTo, double[] weightArray, int[] parentOf){
		int w = e.other(v);
		if(distTo[w] > distTo[v] + e.weight()){ 
			distTo[w] = distTo[v] + e.weight(); 
			edgeTo[w] = e;
			weightArray[w] = e.weight();
			parentOf[w] = v;
			if(pq.contains(w)){
				pq.decreaseKey(w, distTo[w]);
			}
			else{
				pq.insert(w, distTo[w]);
			}
		}
	}  



	//for fewest hops//////choice 5
	public String bfs(int s, int d){
		validateVertex(s);
		validateVertex(d);
		if(s == d){
			throw new IllegalArgumentException();
		}
		Stack<Integer> path = new Stack<Integer>();
		Queue<Integer> q = new Queue<Integer>();
		boolean[] marked = new boolean[V];
		int[] parentArray = new int[V]; //parent array
		boolean found = false;
		int hops = 0;
		marked[s] = true;
		q.enqueue(s);
	
		while(!q.isEmpty() && !found){
			int v = q.dequeue();
			for(Edge e: adj[v]){
				int w = e.other(v);
			
				if(w == d){
					parentArray[w] = v;
					path.push(w);
					v = parentArray[w];
				
					while(v != s){
						path.push(v);
						hops++;
						v = parentArray[v];
					}
					path.push(v);
					hops++;
					found = true;
					break;
				}
				else if(!marked[w]){
					parentArray[w] = v;
					marked[w] = true;
					q.enqueue(w);
				}	
			}
		 if(q.isEmpty() && !found){
			return "No path exists\n";
		 }
		}

		StringBuilder fewestHops = new StringBuilder("\nFEWEST HOPS from "  + vertices.get(s) + " to " + vertices.get(d) + "(In Order)");
		fewestHops.append("\n--------------------------------------------");
		fewestHops.append("\nFewest hops from " + vertices.get(s) + " to " + vertices.get(d) + " is " + hops + "\n");
		fewestHops.append("Path (In Order):\n");
		while(!path.isEmpty()){
			fewestHops.append(vertices.get(path.pop()) + " ");
		}
		fewestHops.append("\n");
		return fewestHops.toString();
	}


	/////max cost someone wants to pay
	public void maxPricePaths(double maxPrice){
		boolean[] marked;
		double[] weights; //array of weights
		double weight = 0;
		ArrayList<Integer> path;
		for(int i = 0; i < V; i++){
			for(Edge edge: adj(i)){
				edge.changeToPrice();
			}
		}
		StringBuilder maximumPath = new StringBuilder("ALL PATHS OF COST " + maxPrice + " OR LESS\nNote that paths are duplicated, once from each end city's point of view\n");
		maximumPath.append("-------------------------------------------------------------\n");
		maximumPath.append("List of paths at most " + maxPrice + " or less: \n");
	
		for(int i = 0; i < V; i++){
			marked = new boolean[V];
			weights = new double[V];
			weight = 0;
			path = new ArrayList<Integer>();
			for(int v = 0; v < V; v++){
				marked[v] = false;
				weights[v] = 0.0;
			}
			path.add(i);
			marked[i] = true;
			maxPricePaths(i, maxPrice, maximumPath, weight, path, weights, marked);
		}
		System.out.println(maximumPath.toString());
	}

	public void maxPricePaths(int v, double maxPrice, StringBuilder maximumPath, double weight, ArrayList<Integer> path, double[] weights, boolean[] marked){
		Queue<Integer> q = new Queue<Integer>();
		for(Edge e: adj(v)){
			if(!marked[e.other(v)] && (weight + e.weight()) <= maxPrice){
				q.enqueue(e.other(v));
				weights[e.other(v)] = e.weight();
			}
		}

		while(!q.isEmpty()){
			int w = q.dequeue();
			double temporaryWeight = weight + weights[w];
			marked[w] = true;
			path.add(w);
			maximumPath.append("Price: " + temporaryWeight + " Path (In Order): ");
			for(int i = 0; i < path.size(); i++){
				int x = path.get(i);
				if(weights[x] != 0){
					maximumPath.append(weights[x] + " ");
				}
				maximumPath.append(vertices.get(x) +" ");
			}
			maximumPath.append("\n\n");
			if(temporaryWeight != maxPrice){
				maxPricePaths(w, maxPrice, maximumPath, temporaryWeight, path, weights, marked);
			}
		
			Integer removeVertex = new Integer(w);
			path.remove(removeVertex);
			marked[w] = false;
		}
	}
}
