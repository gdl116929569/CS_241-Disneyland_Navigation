/**
 * 
 * @author Akira
 */
public class Vertex {

	/**
	 * Location number. Used for building relationships.
	 */
	private int locID;
	
	/**
	 * The two letter code for the vertex.
	 */
	private String code;
	
	/**
	 * Name of the location.
	 */
	private String name;

	/**
	 * The population of the vertex.
	 */
	private int population;
	
	/**
	 * Elevation of the location.
	 */
	private int elevation;
	
	/**
	 * The distance from this node to the source node.
	 */
	private int weight;
	
	/**
	 * The vertex to jump to 
	 */
	private Vertex previous;
	
	/**
	 * The vertex's edges.
	 */
	private Edge[] edges = new Edge[20];
	
	/**
	 * The number of neighbors this vertex has.
	 */
	private int numberOfNeighbors;
	
	/**
	 * Constructor for a {@code Vertex}.
	 * @param ID The ID number of the vertex.
	 * @param code The two letter code of the vertex.
	 * @param name The name of the vertex.
	 * @param population The population of the vertex.
	 * @param elevation The elevation of the vertex.
	 */
	public Vertex(int ID, String code, String name, int population, int elevation){
		locID = ID;
		this.code = code;
		this.name = name;
		this.population = population;
		this.elevation = elevation;
		weight = 999999999; //Simulating infinity
		previous = null;
	}
	
	/**
	 * Set the ID number of the vertex.
	 * @param ID The ID number of the vertex.
	 */
	public void setID(int ID){
		locID = ID;
	}
	
	/**
	 * Get the ID number of the vertex.
	 * @return The ID number of the vertex.
	 */
	public int getID(){
		return locID;
	}
	
	/**
	 * Set the two letter code of the vertex.
	 * @param code The new two letter code of the vertex.
	 */
	public boolean setCode(String code){
		if(code.length() == 2){
			this.code = code.toUpperCase();
			return true;
		}
		return false;
	}
	
	/**
	 * Get the two letter code of the vertex.
	 * @return The two letter code of the vertex.
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * Set the name of vertex.
	 * @param name The name of the vertex.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Get the name of the vertex.
	 * @return The name of the vertex.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Set the population of the vertex.
	 * @param population The population of the vertex.
	 */
	public void setPopulation(int population){
		this.population = population;
	}
	
	/**
	 * Get the population of the vertex.
	 * @return The population of the vertex.
	 */
	public int getPopulation(){
		return population;
	}
	
	/**
	 * Set the elevation of the vertex.
	 * @param elevation The elevation of the vertex.
	 */
	public void setElevation(int elevation){
		this.elevation = elevation;
	}
	
	/**
	 * Set the elevation of the vertex.
	 * @return The elevation of the vertex.
	 */
	public int getElevation(){
		return elevation;
	}
	
	/**
	 * Set the distance of this vertex to the source. The distance
	 * must be positive.
	 * @param dist The distance from this vertex to the source.
	 * @return {@code true} if the distance was set, {@code false}
	 *         otherwise.
	 */
	public boolean setWeight(int dist){
		if(dist < 0){
			return false;
		}
		weight = dist;
		return true;
	}
	
	/**
	 * Get the vertex's distance from the source node. Used in
	 * Dijkstra's Algorithm.
	 * @return The distance from this vertex to the source.
	 */
	public int getWeight(){
		return weight;
	}
	
	/**
	 * Get the number of neighbors this vertex has.
	 * @return The number of neighbors.
	 */
	public int getNumberOfNeighbors(){
		return numberOfNeighbors;
	}
	
	/**
	 * Get the vertex's neighbors.
	 * @return An array of vertices filled with the vertex's neighbors.
	 */
	public Vertex[] getNeighbors(){
		if(numberOfNeighbors != 0){
			Vertex[] neighbors = new Vertex[numberOfNeighbors];
			int index = 0;
			for(int x = 0; x < edges.length; x++){
				if(edges[x] != null){
					neighbors[index++] = edges[x].getTargetVertex();
				}
			}
			return neighbors;
		}
		return null;
	}
	
	/**
	 * Set this vertex's best connection to the source.
	 * @param _previous The vertex's best connection to the source.
	 */
	public void setPrevious(Vertex _previous){
		previous = _previous;
	}
	
	/**
	 * Get this vertex's best connection to the source.
	 * @return The vertex's best connection to the source.
	 */
	public Vertex getPrevious(){
		return previous;
	}
	
	/**
	 * Add an edge to this vertex.
	 * @param targetVertex The vertex this vertex connects to.
	 * @param distance The distance from this vertex to the other vertex.
	 * @throws RoadExistsException Thrown if a connection between this
	 *                             vertex and the target vertex already
	 *                             exists.
	 */
	public void addEdge(Vertex targetVertex, int distance) throws RoadExistsException{
		if(edges[targetVertex.getID()-1] != null){
			throw new RoadExistsException(); 
		}
		else{
			edges[targetVertex.getID()-1] = new Edge(targetVertex, distance);
			numberOfNeighbors++;
		}
	}
	
	/**
	 * Delete an edge to a vertex.
	 * @param vertex The vertex to delete an edge to.
	 * @throws NoEdgeException Thrown if there is not edge between this vertex
	 *                         and the target vertex.
	 */
	public void deleteEdge(int vertex) throws NoEdgeException{
		if(edges[vertex-1] == null){
			throw new NoEdgeException();
		}
		else{
			edges[vertex-1] = null;
			numberOfNeighbors--;
		}
	}
	
	public Edge[] getEdges(){
		return edges;
	}
	
	/**
	 * Return the name.
	 */
	public String toString(){
		return name;
	}
	
	/**
	 * Get the information in the vertex.
	 * @return The vertex's information.
	 */
	public String getInfo(){
		return ("ID: " + locID + " Code: " + code + " Name: " + name
				+ " Population: " + population + " Elevation: " + elevation);
	}
	
	/**
	 * Returns a {@code String} of all of this vertex's children.
	 * @return A {@code String} of all of this vertex's children.
	 */
	public String getChildren(){
		String children = "";
		for(int x = 0; x < edges.length; x++){
			if(edges[x] != null){
				children += edges[x].getTargetVertex() + "-" + edges[x].getDistance() + " ";
			}
		}
		return children;
	}
}
