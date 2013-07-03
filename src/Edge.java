/**
 * An edge between two vertices.
 * 
 * @author Akira
 */
public class Edge {
	
	/**
	 * The ID of the vertex the edge connects to.
	 */
	private Vertex vertex;
	
	/**
	 * The distance from the parent vertex to the stored vertex.
	 */
	private int dist;
	
	/**
	 * Constructor for the edge.
	 * @param vertex The target vertex.
	 * @param distance The distance from the parent vertex to the
	 *                 stored vertex.
	 */
	public Edge(Vertex _vertex, int distance){
		vertex = _vertex;
		dist = distance;
	}
	
	/**
	 * Get the ID of the target vertex.
	 * @return The ID of the target vertex.
	 */
	public Vertex getTargetVertex(){
		return vertex;
	}
	
	/**
	 * Get the distance from the parent vertex to the target vertex.
	 * @return The distance between the two vertices.
	 */
	public int getDistance(){
		return dist;
	}
}
