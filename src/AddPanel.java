import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel for the visuals for when the user wants to add an edge to a
 * vertex.
 * 
 * @author Akira
 */
public class AddPanel extends JPanel {
	
	/**
	 * Label for displaying information about the selected edge to the user.
	 */
	private JLabel pathLabel;
	
	/**
	 * Label for displaying "Start".
	 */
	private JLabel startLabel;
	
	/**
	 * Label for displyaing "Finish".
	 */
	private JLabel finishLabel;
	
	private JLabel v1DestLabel;
	
	private JLabel v2DestLabel;
	
	private JLabel v1DestNameLabel;
	
	private JLabel v2DestNameLabel;
	
	/**
	 * Text field for input of distance between two vertices.
	 */
	private JTextField distanceTextField;
	
	/**
	 * Button for adding an edge
	 */
	private JButton addButton;
	
	/**
	 * Panel for displaying and getting user selected data.
	 */
	private MapPanel mapPanel;
	
	private boolean recentlyAdded = false;
	
	/**
	 * Constructor.
	 * @param _vertices The set of vertices to use for the map panel.
	 */
	public AddPanel(Vertex[] _vertices){
		mapPanel = new MapPanel(_vertices, false);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		pathLabel = new JLabel("Select a starting location");
		startLabel = new JLabel("Start");
		finishLabel = new JLabel("Finish");
		
		v1DestLabel = new JLabel("Vertex: ");
		v2DestLabel = new JLabel("Vertex: ");
		v1DestNameLabel = new JLabel("");
		v2DestNameLabel = new JLabel("");
		
		distanceTextField = new JTextField();
		distanceTextField.setMinimumSize(new Dimension(130, distanceTextField.getPreferredSize().height));
		distanceTextField.setMaximumSize(new Dimension(130, distanceTextField.getPreferredSize().height));
		distanceTextField.setEnabled(false);
		
		addButton = new JButton("Add!");
		addButton.setEnabled(false);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addButtonActionPerformed();
			}
		});
		
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {
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
										.addComponent(v1DestNameLabel)
								)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(distanceTextField)
								.addComponent(addButton)
						)
						.addGroup(layout.createParallelGroup()
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
				.addGroup(layout.createParallelGroup()
						.addComponent(startLabel)
						.addComponent(finishLabel)
						.addComponent(distanceTextField)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(v1DestLabel)
						.addComponent(v1DestNameLabel)
						.addComponent(addButton)
						.addComponent(v2DestLabel)
						.addComponent(v2DestNameLabel)
				)
				
				.addComponent(pathLabel)
		);
			
		setLayout(layout);
	}
	
	private void mouseMovedPerformed(){
		Marker m1 = mapPanel.getDest1();
		Marker m2 = mapPanel.getDest2();
		
		//First location has been selected, update its name
		if(m2 == null && m1 != null){
			pathLabel.setText("Select a destination");
			distanceTextField.setText("");
			v1DestNameLabel.setText(m1.getVertex().getName());
			v2DestNameLabel.setText("");
			distanceTextField.setEnabled(false);
			addButton.setEnabled(false);
			recentlyAdded = false;
		}
		//Check to see if there is already an edge between these two
		else if(!recentlyAdded && m1 != null){
			v2DestNameLabel.setText(m2.getVertex().getName());
			Edge[] edges = m1.getVertex().getEdges();
			if(edges[m2.getVertex().getID()-1] != null){
				distanceTextField.setEnabled(false);
				addButton.setEnabled(false);
				pathLabel.setText("A path already exists between these two places!");
			}
			else{
				distanceTextField.setEnabled(true);
				addButton.setEnabled(true);
				pathLabel.setText("You can add a path here!");
			}
		}
	}
	
	private void addButtonActionPerformed(){
		try {
			Vertex v1 = mapPanel.getDest1().getVertex();
			Vertex v2 = mapPanel.getDest2().getVertex();
			v1.addEdge(v2, Integer.parseInt(distanceTextField.getText()));
			pathLabel.setText("You added a path between " + 
					v1.getName() + " and " + v2.getName());
			addButton.setEnabled(false);
			recentlyAdded = true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Enter a valid distance!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		} catch (RoadExistsException e) {
			//This should never happen.
			e.printStackTrace();
		}
	}
}
