import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for deleting an edge between two vertices.
 * 
 * @author Akira
 */
public class DeletePanel extends JPanel {

	/**
	 * Label for the start combo box.
	 */
	private JLabel startLabel;
	
	/**
	 * Label for the finish combo box.
	 */
	private JLabel finishLabel;
	
	/**
	 * Label for displaying if the edge is able to be deleted or not.
	 */
	private JLabel pathLabel;
	
	/**
	 * Static label for displaying the name of the starting vertex. 
	 */
	private JLabel v1DestLabel;
	
	/**
	 * Dynamic label for displaying the name of the starting vertex. 
	 */
	private JLabel v1DestNameLabel;
	
	/**
	 * Static label for displaying the name of the ending vertex. 
	 */
	private JLabel v2DestLabel;
	
	/**
	 * Dynamic label for displaying the name of the ending vertex. 
	 */
	private JLabel v2DestNameLabel;
	
	/**
	 * Button for confirming the deletion of the edge.
	 */
	private JButton deleteButton;
	
	/**
	 * Panel for displaying the map and getting user selected data.
	 */
	private MapPanel mapPanel;
	
	/**
	 * A boolean to keep the deleted edge message until the next vertex is selected.
	 */
	private boolean recentlyDeleted = false;
	
	/**
	 * Constructor for the delete panel.
	 * @param _vertices The vertices to use for data.
	 */
	public DeletePanel(Vertex[] _vertices){		
		mapPanel = new MapPanel(_vertices, false);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		startLabel = new JLabel("Start");
		finishLabel = new JLabel("Finish");
		pathLabel = new JLabel("Select a starting location");
		
		v1DestLabel = new JLabel("Vertex: ");
		v2DestLabel = new JLabel("Vertex: ");
		
		v1DestNameLabel = new JLabel();
		v2DestNameLabel = new JLabel();
		
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteButtonActionPerformed();
			}
		});
		
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			
			public void mouseMoved(MouseEvent arg0) {
				mouseMovedPerformed();
			}
			
		});
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(mapPanel)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(startLabel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v1DestLabel)
										.addComponent(v1DestNameLabel)
								)
						)
						.addGap(50)
						.addGroup(layout.createSequentialGroup()
								.addComponent(deleteButton)
						)
						.addGap(50)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(finishLabel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(v2DestLabel)
										.addComponent(v2DestNameLabel)
								)
						)
				)
				.addComponent(pathLabel)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mapPanel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(startLabel)
						.addComponent(deleteButton)
						.addComponent(finishLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(v1DestLabel)
						.addComponent(v2DestLabel)
						.addComponent(v1DestNameLabel)
						.addComponent(v2DestNameLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(pathLabel)
				)
				
		);
		
		setLayout(layout);
	}
	
	private void mouseMovedPerformed(){
		Marker m1 = mapPanel.getDest1();
		Marker m2 = mapPanel.getDest2();
		
		//First location has been selected, update its name
		if(m2 == null && m1 != null){
			pathLabel.setText("Select a destination");
			v1DestNameLabel.setText(m1.getVertex().getName());
			v2DestNameLabel.setText("");
			deleteButton.setEnabled(false);
			recentlyDeleted = false;
		}
		//Check to see if there is already an edge between these two
		else if(!recentlyDeleted && m1 != null){
			v2DestNameLabel.setText(m2.getVertex().getName());
			Edge[] edges = m1.getVertex().getEdges();
			if(edges[m2.getVertex().getID()-1] != null){
				deleteButton.setEnabled(true);
				pathLabel.setText("An edge can be deleted!");
			}
			else{
				deleteButton.setEnabled(false);
				pathLabel.setText("There is no edge between these places!");
			}
		}
	}
	
	/**
	 * The action to perform when the delete button is pressed.
	 */
	private void deleteButtonActionPerformed(){
		try {
			Vertex v1 = mapPanel.getDest1().getVertex();
			Vertex v2 = mapPanel.getDest2().getVertex();
			v1.deleteEdge(v2.getID());
			pathLabel.setText("You deleted a path between " + 
					v1.getName() + " and " + v2.getName());
			deleteButton.setEnabled(false);
			recentlyDeleted = true;
		} catch (NoEdgeException e) {
			//This should never happen.
			e.printStackTrace();
		}
	}
}
