/**
 *  The <tt>Edge</tt> class represents a weighted edge in an 
 *  {@link EdgeWeightedGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the weight. The natural order for this data type is by
 *  ascending order of weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
import java.util.*;
//Kerilee Bookleiner
public class Edge implements Comparable<Edge> { 
    private final int v;
    private final int w;
    private double weight;
    private double price;
    private double distance;
    

    /**
     * Initializes an edge between vertices <tt>v</tt> and <tt>w</tt> of
     * the given <tt>weight</tt>.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt> 
     *         is a negative integer
     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
     */
    public Edge(int v, int w, double distance, double price) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = distance;
        this.distance = distance;
        this.price = price;
    }
    
    
    public Edge(int v, int w){
    	this.v = v;
    	this.w = w;
    	this.weight = 0;
    	this.distance = 0;
    	this.price = 0;
    }
    

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }


    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }
    
    
    public double distance(){
    	return distance;
    }
    
    
    public double price(){
    	return price;
    }
    

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

	//changes the weight to the either the price or distance
	public void changeToDistance(){
		weight = distance;
	}
	public void changeToPrice(){
		weight = price;
	}
    /**
     * Compares two edges by weight.
     * Note that <tt>compareTo()</tt> is not consistent with <tt>equals()</tt>,
     * which uses the reference equality implementation inherited from <tt>Object</tt>.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        if      (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else                                    return  0;
    }


	public boolean equals(Object object){
		Edge that = (Edge)object;
		if(this.either() == that.either()){
			if(that.other(that.either()) == this.other(this.either())){
				return true;
			}
		}
		else if(this.either() == that.other(that.either())){
			if(that.either() == this.other(this.either())){
				return true;
			
			}
		}
			return false;
		
	}


    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    	public String toString(ArrayList<String> vertices, boolean appendString) {
        StringBuilder string = new StringBuilder();
        
        
        //for printing minimum spanning tree
        if(appendString){
        	string.append(vertices.get(v) + ", " + vertices.get(w) + ": " + weight);
        }
        
        //for printing all routes
        else{
        	string.append("Price: " + price + " -> " + vertices.get(v) + " " + weight + " " + vertices.get(w));
        }
        return string.toString();   
    }
}
