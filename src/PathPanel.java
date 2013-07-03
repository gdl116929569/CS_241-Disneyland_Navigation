import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for displaying the components for finding the path between
 * two vertices.
 * @author Akira
 */

public class PathPanel extends JPanel{
	
	/**
	 * The static label for distance.
	 */
	private JLabel distLabel;
	
	/**
	 * The dynamic label for displaying the distance.
	 */
	private JLabel distNumberLabel;
	
	/**
	 * The static label for displaying the name of the first vertex.
	 */
	private JLabel v1DestLabel;
	
	/**
	 * The static label for displaying the name of the second vertex.
	 */
	private JLabel v2DestLabel;
	
	/**
	 * The dynamic label for displaying the name of the first vertex.
	 */
	private JLabel v1DestNumberLabel;
	
	/**
	 * The dynamic label for displaying the name of the second vertex.
	 */
	private JLabel v2DestNumberLabel;
	
	/**
	 * The static label for displaying the population of the
	 * first vertex.
	 */
	private JLabel v1PopLabel;
	
	/**
	 * The static label for displaying the population of the
	 * second vertex.
	 */
	private JLabel v2PopLabel;
	
	/**
	 * The dynamic label for displaying the population of the
	 * first vertex.
	 */
	private JLabel v1PopNumberLabel;
	
	/**
	 * The dynamic label for displaying the population of the
	 * second vertex.
	 */
	private JLabel v2PopNumberLabel;
	
	/**
	 * The static label for displaying the elevation of the
	 * first vertex.
	 */
	private JLabel v1ElevLabel;
	
	/**
	 * The static label for displaying the elevation of the
	 * second vertex.
	 */
	private JLabel v2ElevLabel;
	
	/**
	 * The dynamic label for displaying the elevation of the
	 * first vertex.
	 */
	private JLabel v1ElevNumberLabel;
	
	/**
	 * The dynamic label for the displaying the elevatin of the
	 * second vertex.
	 */
	private JLabel v2ElevNumberLabel;
	
	/**
	 * The static label for displaying the path from the source to
	 * the target vertex.
	 */
	private JLabel pathLabel;
	
	/**
	 * The dynamic label for displaying the path from the source to
	 * the target vertex.
	 */
	private JLabel pathDispLabel1;
	
	/**
	 * The dynamic label for displaying the path from the source to
	 * the target vertex.
	 */
	private JLabel pathDispLabel2;
	
	/**
	 * Label for telling the user which combo box is the starting point.
	 */
	private JLabel startLabel;
	
	/**
	 * Label for telling the user which combo box is the ending point.
	 */
	private JLabel finishLabel;
	
	/**
	 * Panel for displaying the map and getting selected data.
	 */
	private MapPanel mapPanel;
	
	/**
	 * A copy of the graph.
	 */
	private Vertex[] vertices;
	
	/**
	 * The distance from the source vertex to the target vertex taking
	 * the shortest route possible.
	 */
	private int distToTarget;
	
	/**
	 * Constructor for the panel for handling path determinations
	 * @param _vertices The array of vertices to use for the data.
	 */
	public PathPanel(Vertex[] _vertices){
		vertices = _vertices;
		
		mapPanel = new MapPanel(_vertices, false);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		distLabel = new JLabel("Distance");
		distNumberLabel = new JLabel("0");
		
		v1DestLabel = new JLabel("Vertex: ");
		v2DestLabel = new JLabel("Vertex: ");
		
		v1PopLabel = new JLabel("Population: ");
		v2PopLabel = new JLabel("Population: ");
		
		v1ElevLabel = new JLabel("Elevation: ");
		v2ElevLabel = new JLabel("Elevation: ");
		
		pathLabel = new JLabel("Path to take: ");
		pathDispLabel1 = new JLabel("Select a starting location");
		pathDispLabel2 = new JLabel();
		
		startLabel = new JLabel("Start");
		finishLabel = new JLabel("Finish");
		
		v1DestNumberLabel = new JLabel("None");
		v2DestNumberLabel = new JLabel("None");
		
		v1PopNumberLabel = new JLabel("");
		v2PopNumberLabel = new JLabel("");
		
		v1ElevNumberLabel = new JLabel("");
		v2ElevNumberLabel = new JLabel("");
		
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				mouseMovedPerformed();
			}
		});
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(mapPanel)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(startLabel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v1DestLabel)
										.addComponent(v1DestNumberLabel)
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v1PopLabel)
										.addComponent(v1PopNumberLabel)
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v1ElevLabel)
										.addComponent(v1ElevNumberLabel)
								)
						)
						.addGap(50)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(distLabel)
								.addComponent(distNumberLabel)
						)
						.addGap(50)
						.addGroup(layout.createParallelGroup()
								.addComponent(finishLabel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v2DestLabel)
										.addComponent(v2DestNumberLabel)
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v2PopLabel)
										.addComponent(v2PopNumberLabel)
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v2ElevLabel)
										.addComponent(v2ElevNumberLabel)
								)
						)
				)
				.addGroup(layout.createSequentialGroup()
						.addComponent(pathLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(pathDispLabel1)
								.addComponent(pathDispLabel2)
						)
				)
				
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mapPanel)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(startLabel)
								.addGroup(layout.createParallelGroup()
										.addComponent(v1DestLabel)
										.addComponent(v1DestNumberLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(v1PopLabel)
										.addComponent(v1PopNumberLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(v1ElevLabel)
										.addComponent(v1ElevNumberLabel)
								)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(distLabel)
								.addComponent(distNumberLabel)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(finishLabel)
								.addGroup(layout.createParallelGroup()
										.addComponent(v2DestLabel)
										.addComponent(v2DestNumberLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(v2PopLabel)
										.addComponent(v2PopNumberLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(v2ElevLabel)
										.addComponent(v2ElevNumberLabel)
								)
						)
				)
				.addGap(50)
				.addGroup(layout.createParallelGroup()
						.addComponent(pathLabel)
						.addGroup(layout.createSequentialGroup()
								.addComponent(pathDispLabel1)
								.addComponent(pathDispLabel2)
						)
				)
		);
		
		setLayout(layout);
	}
	
	/**
	 * Chill brah, and relax the edges from the vertex selected in {@code v1ComboBox} to
	 * the vertex at {@code v2ComboBox}.
	 * @return An array of pointers to nodes for the shortest route
	 *         to the source.
	 */
	private Stack<Vertex> DijkstraRelax(){
		Vertex source = mapPanel.getDest1().getVertex();
		Vertex target = mapPanel.getDest2().getVertex();
		Vertex[] vCopy = vertices.clone();
		Stack<Vertex> stack = new Stack<Vertex>();
		
		//Each vertex is at a distance of "infinity" by default, but reinitialize anyway
		for(Vertex v : vCopy){
			if(v != null){
				v.setWeight(999999999);
				v.setPrevious(null);
			}
		}
		//Set source vertex distance to 0.
		source.setWeight(0);
		
		while(!isEmpty(vCopy)){
			int smallest = findSmallest(vCopy);
			Vertex u = vCopy[smallest];
			vCopy[smallest] = null; //Remove the vertex so we don't keep hitting it
			
			//We've reached the target.
			if(u == target){
				break;
			}
			
			//If the smallest distance we can find is "infinity,"
			//then we have no possible way of getting anywhere else.
			if(u.getWeight() == 999999999){
				break;
			}
			
			Vertex[] uNeighbors = u.getNeighbors();
			Edge[] uEdges = u.getEdges();
			if(uNeighbors != null){
				for(Vertex v : uNeighbors){
					int uTovDist = 999999999; //Should never end up being 999999999 after processing
					//Find the distance from u to v
					for(int x = 0; x < uEdges.length; x++){
						if(uEdges[x] != null){
							if(uEdges[x].getTargetVertex() == v){
								uTovDist = uEdges[x].getDistance();
							}
						}
					}
					
					int alt = u.getWeight() + uTovDist;
					if(alt < v.getWeight()){
						v.setWeight(alt);
						v.setPrevious(u);
					}
				}
			}
		}
		
		Vertex u = target;
		while(u != null){
			stack.push(u);
			u = u.getPrevious();
		}
		
		distToTarget = target.getWeight();
		
		return stack;
	}
	
	/**
	 * Find the index of the vertex with the smallest weight in the array.
	 * @param array The array to search through.
	 * @return The index of the vertex with the smallest weight. 
	 */
	private int findSmallest(Vertex[] array){
		int smallestIndex = -1;
		//Find first vertex for smallest index
		for(int x = 0; x < array.length; x++){
			if(array[x] != null){
				smallestIndex = x;
				break;
			}
		}
		
		//If the array is empty, signal and return
		if(smallestIndex == -1){
			return smallestIndex;
		}
		
		//Find the smallest index
		for(int x = smallestIndex; x < array.length; x++){
			if(array[x] != null){
				if(array[x].getWeight() < array[smallestIndex].getWeight()){
					smallestIndex = x;
				}
			}
		}
		return smallestIndex;
	}
	
	/**
	 * Determine if the array is empty (full of {@code null}).
	 * @param array The array to check.
	 * @return {@code true} if the array is empty, {@code false}
	 *         otherwise.
	 */
	private boolean isEmpty(Vertex[] array){
		for(int x = 0; x < array.length; x++){
			if(array[x] != null){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Update the path and distance labels.
	 * @param stack A {@link Stack} filled with vertices that lead
	 *              from the source to the target vertex.
	 */
	private void updatePathDispandDistLabel(Stack<Vertex> stack){
		String path1 = "";
		String path2 = "";
		
		//65
		while(!stack.empty()){
			Vertex v = stack.pop();
			if(path1.length() + v.getName().length() <= 75){
				path1 += v.getName() + " >> ";
			}
			else{
				path2 += v.getName() + " >> ";
			}
		}
		
		if(distToTarget == 999999999){
			path1 = "There is no way there from here!";
		}
		else if(distToTarget > 0){
			if(path2.length() == 0){
				path1 = path1.substring(0, path1.length()-4);
			}
			else{
				path2 = path2.substring(0, path2.length()-4);
			}
		}
		
		pathDispLabel1.setText(path1);
		pathDispLabel2.setText(path2);
		distNumberLabel.setText(""+distToTarget+ " steps");
	}

	/**
	 * Set labels to correct values and update the path if two vertices are selected.
	 */
	private void mouseMovedPerformed(){
		Marker m1 = mapPanel.getDest1();
		Marker m2 = mapPanel.getDest2();
		
		if(m1 != null){
			Vertex v1 = m1.getVertex();
			if(v1.getName() != v1DestNumberLabel.getText()){
				v1DestNumberLabel.setText(v1.getName());
				v1PopNumberLabel.setText(""+v1.getPopulation());
				v1ElevNumberLabel.setText(""+v1.getElevation());
				v2DestNumberLabel.setText("None");
				v2PopNumberLabel.setText("");
				v2ElevNumberLabel.setText("");
				pathDispLabel1.setText("Select a destination");
				pathDispLabel2.setText("");
			}
		}
		
		if(m2 != null){
			Vertex v2 = m2.getVertex();
			updatePathDispandDistLabel(DijkstraRelax());
			v2DestNumberLabel.setText(v2.getName());
			v2PopNumberLabel.setText(""+v2.getPopulation());
			v2ElevNumberLabel.setText(""+v2.getElevation());
		}
	}
}
