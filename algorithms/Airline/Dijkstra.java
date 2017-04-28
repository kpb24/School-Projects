public class Dijkstra{



public String Dijkstra(Graph G, int s, int d, int flag){
	String pathType = "";
	
	if(flag == 1){
		this.changeWeightToDistance();
		pathType = new String("Distance");
	}
	else if(flag == 2){
		this.changeWeightToPrice();
		pathType = new String("Price");
	}

	
	double[] distTo = new double[V]; //distance of shortest s->v path
	Edge[] edgeTo = new Edge[V]; //Last edge on shortest s-> path
	double[] weightOf = new double[V];
	int[] parentOf = new int[V];
	IndexMinPQ<Double> pq = new IndexMinPQ<Double>(V); //priority queue of vertices
	Stack<Integer> path = new Stack<Integer>();
	boolean found = false;
	
	
	for(int v = 0; v < V; v++){
		distTo[v] = Double.POSITIVE_INFINITY;
		weightOf[v] = 0.0;
		parentOf[v] = 0;
	}
	
	distanceTo[s] = 0.0;
	parentOf[s] = s;
	
	
	//relax
	pq.insert(s, distTo[s]);
	while(!pq.isEmpty() && !found){
		int v = pq.delMin();
		for(Edge e: adj(v)){
			relax(v, e, pq, distTo, edgeTo, weightOf, parentOf);
		}
		
		if(v == d){
			found = true;
		}
		
		if(pq.isEmpty() && !found){
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
	
	StringBuilder swpPath = new StringBuilder("Shortest " + pathType + " path " + vertices.get(s) + " to " + vertices.get(d) + " is " + distTo[d] + "\n");
	
	while(!path.isEmpty()){
		v = path.pop();
		if(weightOf[v] != 0){
			swpPath.append(weightOf[v] + " ");
			swpPath.append(vertices.get(v) + " ");
		}
	}
	swpPath.append("\n");
	return swpPath.toString();
}





private void relax(int v, DirectedEdge e, IndexMinPQ<Double> pq, double[] distTo, DirectedEdge[] edgeTo, double[] weightOf, int[] parentOf){
	int w = e.other(v);
	if(distTo[w] > distanceTo[v] + e.weight()){
		distTo[w] = distTo[v] + e.weight();
		edgeTo[w] = e;
		weightOf[w] = e.weight();
		parentOf[w] = v;
		
		if(pq.contains(w)){
			pq.decreaseKey(w, distTo[w]);
		}
		else{
			pq.insert(w, distTo[w]);
		}
	}
}  
}