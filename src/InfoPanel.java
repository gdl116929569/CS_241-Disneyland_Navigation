import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for displaying all the information of a single vertex.
 * 
 * @author Akira
 */
public class InfoPanel extends JPanel {
	
	/**
	 * Static label for displaying the ID.
	 */
	private JLabel IDLabel;
	
	/**
	 * Dynamic label for displaying the ID.
	 */
	private JLabel IDDispLabel;
	
	/**
	 * Static label for displaying the city code.
	 */
	private JLabel cityCodeLabel;
	
	/**
	 * Dynamic label for displaying the city code.
	 */
	private JLabel cityCodeDispLabel;
	
	/**
	 * Static label for displaying the name.
	 */
	private JLabel nameLabel;
	
	/**
	 * Dynamic label for displaying the name.
	 */
	private JLabel nameDispLabel;
	
	/**
	 * Static label for displaying the population.
	 */
	private JLabel popLabel;
	
	/**
	 * Dynamic label for displaying the population.
	 */
	private JLabel popDispLabel;
	
	/**
	 * Static label for displaying the population.
	 */
	private JLabel elevLabel;
	
	/**
	 * Dynamic label for displaying the population.
	 */
	private JLabel elevDispLabel;
	
	/**
	 * Static label for displaying the neighbors.
	 */
	private JLabel neighborLabel;
	
	/**
	 * Dynamic label for displaying the neighbors.
	 */
	private JLabel neighborDispLabel;
	
	/**
	 * Panel for displaying the map.
	 */
	private MapPanel mapPanel;
	
	/**
	 * Constructor for the panel.
	 * @param vertices The vertices to use.
	 */
	public InfoPanel(Vertex[] vertices){
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		mapPanel = new MapPanel(vertices, true);
		
		IDLabel = new JLabel("ID: ");
		IDDispLabel = new JLabel();
		
		cityCodeLabel = new JLabel("City Code: ");
		cityCodeDispLabel = new JLabel();
		
		nameLabel = new JLabel("Name: ");
		nameDispLabel = new JLabel();
		
		popLabel = new JLabel("Population: ");
		popDispLabel = new JLabel();
		
		elevLabel = new JLabel("Elevation: ");
		elevDispLabel = new JLabel();
		
		neighborLabel = new JLabel("Neighbors: ");
		neighborDispLabel = new JLabel();
		
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}

			public void mouseMoved(MouseEvent arg0) {
				mouseMovedActionPerformed();
			}
			
		});
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(mapPanel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(IDLabel)
								.addComponent(IDDispLabel)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(cityCodeLabel)
								.addComponent(cityCodeDispLabel)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(nameLabel)
								.addComponent(nameDispLabel)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(popLabel)
								.addComponent(popDispLabel)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(elevLabel)
								.addComponent(elevDispLabel)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(neighborLabel)
								.addComponent(neighborDispLabel)
						)
				)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mapPanel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(IDLabel)
						.addComponent(IDDispLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(cityCodeLabel)
						.addComponent(cityCodeDispLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(nameDispLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(popLabel)
						.addComponent(popDispLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(elevLabel)
						.addComponent(elevDispLabel)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(neighborLabel)
						.addComponent(neighborDispLabel)
				)
		);
		
		setLayout(layout);
	}
	
	/**
	 * Get a {@code String} representation of all the neighbors for displaying.
	 * @return The {@code String} representation of all the neighbors.
	 */
	private String getNeighborString(){
		Marker m = mapPanel.getDest1();
		if(m != null){
			Vertex[] array = m.getVertex().getNeighbors();
			String neighbors = "";
			int count = 0;
			if(array != null){
				for(Vertex v : array){
					neighbors += v.getName() + ", ";
					count++;
				}
				return neighbors.substring(0, neighbors.length()-2);
			}
		}
		return "None";
	}
	
	/**
	 * Change info if the datsdfhjseifj
	 */
	private void mouseMovedActionPerformed(){
		Marker m = mapPanel.getDest1();
		if(m != null){
			Vertex v = m.getVertex();
			IDDispLabel.setText(""+v.getID());
			cityCodeDispLabel.setText(v.getCode());
			nameDispLabel.setText(v.getName());
			popDispLabel.setText(""+v.getPopulation());
			elevDispLabel.setText(""+v.getElevation());
			neighborDispLabel.setText(getNeighborString());
		}
	}
}
