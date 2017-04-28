public class LazyPrim{


public String LazyPrim(int s, boolean[] flagged, StringBuilder mstPath, Arraylist[] vertices, Graph G){

	double weight = 0;
	Stack<Edge> path = new Stack<Edge>();
	MinPQ<Edge> pq = new MinPQ<Edge>();
	
	//this.changeWeightToDistance();
	scan(s, flagged, pq);
	
	while(!pq.isEmpty() && path.size() < (V-1)){
		Edge e = pq.delMin();
		int v = e.either(), w = e.other(v);
		if(flagged[v] && flagged[w]){
			continue;
		}
		path.push(e);
		weight += e.weight();
		if(!flagged[v]){
			scan(v, flagged, pq);
		}
		if(!flagged[w]){
			scan(w,flagged, pq);
		}
	}
	
	mstPath.append("Edges in the minimum spanning tree follow " + weight + "\n");
	while(!path.isEmpty()){
		mstPath.append(path.pop().toString(vertices, true) + "\n");
	}
	mstPath.append("\n");
	return mstPath;
	}

private void scan(int v, boolean[] flagged, MinPQ<Edge> pq){
	flagged[v] = true;
	for(Edge e: adj(v)){
		if(!flagged[e.other(v)]){
			pq.insert(e);
		}
	}
}	
}