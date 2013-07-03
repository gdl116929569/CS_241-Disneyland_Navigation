import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Main class for project 3.
 * @author Akira
 */
public class Project3 extends JFrame{
	
	/**
	 * Radio button for selecting a path from one vertex to another.
	 */
	private JRadioButton pathRadioButton;
	
	/**
	 * Radio button for adding a path from one vertex to another.
	 */
	private JRadioButton addRadioButton;
	
	/**
	 * Radio button for deleting a path from one vertex to another.
	 */
	private JRadioButton deleteRadioButton;
	
	/**
	 * Radio button for getting the information of a vertex.
	 */
	private JRadioButton infoRadioButton;
	
	/**
	 * Panel for the path functionality.
	 */
	private JPanel pathPanel;
	
	/**
	 * Panel for the add functionality.
	 */
	private JPanel addPanel;
	
	/**
	 * Panel for displaying the delete functionality.
	 */
	private JPanel deletePanel;
	
	/**
	 * Panel for displaying the vertex information.
	 */
	private JPanel infoPanel;
	
	/**
	 * Panel for displaying the radio buttons to change modes.
	 */
	private JPanel buttonPanel;
	
	/**
	 * Array of all cities. Index of a city is city number - 1.
	 */
	private Vertex[] vertices;
	
	/**
	 * Project constructor.
	 * @param name Name of the project.
	 */
	public Project3(String name){
		super(name);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Couldn't get any kind of data, close program
		if(!getFileData()){
			System.exit(0);
		}
		initiateComponents();
		pack();
		setVisible(true);
	}
	
	/**
	 * Initiate the Disney mode and regular mode components.
	 */
	private void initiateComponents(){
		pathRadioButton = new JRadioButton("Find Path");
		pathRadioButton.setSelected(true);
		pathRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pathRadioButtonActionPerformed();
			}
		});
		
		addRadioButton = new JRadioButton("Add Edge");
		addRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addRadioButtonActionPerformed();
			}
		});
		
		deleteRadioButton = new JRadioButton("Delete Edge");
		deleteRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteRadioButtonActionPerformed();
			}
		});
		
		infoRadioButton = new JRadioButton("Get Info");
		infoRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				infoRadioButtonActionPerformed();
			}
		});
		
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			
			public void mouseMoved(MouseEvent arg0) {
				setFrame();
			}
		});
		
		buttonPanel = new JPanel();
		GroupLayout buttonLayout = new GroupLayout(buttonPanel);
		buttonLayout.setAutoCreateGaps(true);
		buttonLayout.setAutoCreateContainerGaps(true);
		
		buttonLayout.setHorizontalGroup(buttonLayout.createSequentialGroup()
				.addComponent(pathRadioButton)
				.addComponent(addRadioButton)
				.addComponent(deleteRadioButton)
				.addComponent(infoRadioButton)
		);
		buttonLayout.setVerticalGroup(buttonLayout.createParallelGroup()
				.addComponent(pathRadioButton)
				.addComponent(addRadioButton)
				.addComponent(deleteRadioButton)
				.addComponent(infoRadioButton)
		);
		buttonPanel.setLayout(buttonLayout);

		initiatePanels();
		
		setLayout();
	}
	
	private void initiatePanels(){
		pathPanel = new PathPanel(vertices);
		addPanel = new AddPanel(vertices);
		deletePanel = new DeletePanel(vertices);
		infoPanel = new InfoPanel(vertices);
		
		pathPanel.setVisible(true);
		addPanel.setVisible(false);
		deletePanel.setVisible(false);
		infoPanel.setVisible(false);
		
		pathPanel.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				setFrame();
			}
		});
		addPanel.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				setFrame();
			}
		});
		deletePanel.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				setFrame();
			}
		});
		infoPanel.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				setFrame();
			}
		});
	}
		
	/**
	 * Grab the data for vertices from dcity.dat (Disney mode is enabled
	 * by default) or dcity.dat when Disney mode is disabled.
	 * @return {@code true} if the data was successfully loaded,
	 *         {@code false} if it failed.
	 */
	private boolean getCityData(){
		Scanner scan;
		
		try{
			scan = new Scanner(new File("Data Files/dcity.dat"));
			Scanner tmp = new Scanner(new File("Data Files/droad.dat"));
			tmp.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Error: Can't load Disney data! Loading regular data instead");
			try{
				JOptionPane.showMessageDialog(this, "ERROR: dcity.dat or droad.dat not found. " +
						"Starting in regular mode.", "WARNING", JOptionPane.WARNING_MESSAGE);
				scan = new Scanner(new File("Data Files/city.dat"));
			}
			catch(FileNotFoundException ex){
				System.err.println("Error: dcity.dat or city.dat required");
				JOptionPane.showMessageDialog(this, "ERROR: Required files not found. " +
						"Be sure dcity.dat or city.dat are in the same directory as the " +
						"program and restart the program.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		String line, code, name;
		int ID, population, elevation;
		vertices = new Vertex[20];
		
		while(scan.hasNext()){
			int initial = 0, cursor = 0;
			line = scan.nextLine();
			
			//Single digit ID
			if(line.charAt(0) == ' '){
				initial = cursor = 1;
			}
			//Grab digits
			while(line.charAt(++cursor) != ' '){}
			try{
				ID = Integer.parseInt(line.substring(initial, cursor));
			}
			catch(NumberFormatException e){
				displayBadFormat();
				scan.close();
				return false;
			}
			initial = cursor += 2; //Set to start of city code
			
			//Grab city code
			code = line.substring(initial, cursor + 2);
			initial = cursor += 6; //Set to start of city name
			
			//Grab city name
			for(; !line.substring(cursor, cursor+2).equals("  "); ++cursor){}
			name = line.substring(initial, cursor);
			
			//Skip spaces to population
			for(; line.charAt(++cursor) == ' ';){}
			initial = cursor; //Set first digit found to initial
			
			//Find the end of the population
			while(line.charAt(++cursor) != ' '){}
			try{
				population = Integer.parseInt(line.substring(initial, cursor));
			}
			catch(NumberFormatException e){
				displayBadFormat();
				scan.close();
				return false;
			}
			
			//Skip spaces to elevation
			for(; line.charAt(++cursor) == ' ';){}
			initial = cursor; //Set first digit found to initial
			
			//Set cursor to last index if space at end of string
			if(line.charAt(line.length() - 1) == ' '){
				cursor = line.length() - 1;
			}
			//Otherwise the cursor is the string length
			//(substring is exclusive at the end index)
			else{
				cursor = line.length();
			}
			try{
				elevation = Integer.parseInt(line.substring(initial, cursor));
			}
			catch(NumberFormatException e){
				displayBadFormat();
				scan.close();
				return false;
			}
			
			//Create and save new vertex
			vertices[ID-1] = new Vertex(ID, code, name, population, elevation);
		}
		scan.close();
		return true;
	}
	
	/**
	 * Grab the data for edges from droads.dat (Disney mode is enabled
	 * by default) or droads.dat when Disney mode is disabled.
	 * @return {@code true} if the data was successfully loaded,
	 *         {@code false} if it failed.
	 */
	private boolean getRoadData(){
		Scanner scan;
		
		try{
			scan = new Scanner(new File("Data Files/droad.dat"));
			Scanner tmp = new Scanner(new File("Data Files/dcity.dat"));
			tmp.close();
		}
		catch(FileNotFoundException e){
			try{
				scan = new Scanner(new File("Data Files/road.dat"));
			}
			catch(FileNotFoundException ex){
				System.err.println("Error: droad.dat or road.dat required");
				JOptionPane.showMessageDialog(this, "ERROR: Required files not found. " +
						"Be sure droad.dat or road.dat are in the same directory as the " +
						"program and restart the program.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	
		//Begin grabbing roads!!
		int v1, v2, dist;
		
		while(scan.hasNext()){
			v1 = Integer.parseInt(scan.next());
			v2 = Integer.parseInt(scan.next());
			dist = Integer.parseInt(scan.next());
			
			try{
				vertices[v1-1].addEdge(vertices[v2-1], dist);
			}
			catch(NumberFormatException e){
				displayBadFormat();
				scan.close();
				return false;
			}
			catch(RoadExistsException e){
				JOptionPane.showMessageDialog(null, "The road from "
						+ vertices[v1-1].getName() + " to "
						+ vertices[v2-1].getName() + " already exists.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		scan.close();
		return true;
	}
	
	/**
	 * Load the vertex data and the edge data. The returned boolean
	 * relies on both the vertex and edge data to be loaded successfully.
	 * If one of them fails to load the appropriate data, the program
	 * will terminate.
	 * @return {@code true} if both vertex data and edge data are loaded
	 *         successfully. {@code false} if either one fails.
	 */
	private boolean getFileData(){
		return getCityData() && getRoadData();
	}
	
	/**
	 * Changes the selected radio button to the path radio button.
	 */
	private void pathRadioButtonActionPerformed(){
		pathRadioButton.setSelected(true);
		addRadioButton.setSelected(false);
		deleteRadioButton.setSelected(false);
		infoRadioButton.setSelected(false);
		pathPanel.setVisible(true);
		addPanel.setVisible(false);
		deletePanel.setVisible(false);
		infoPanel.setVisible(false);
		setFrame();
	}
	
	/**
	 * Changes the selected radio button to the add radio button.
	 */
	private void addRadioButtonActionPerformed(){
		pathRadioButton.setSelected(false);
		addRadioButton.setSelected(true);
		deleteRadioButton.setSelected(false);
		infoRadioButton.setSelected(false);
		pathPanel.setVisible(false);
		addPanel.setVisible(true);
		deletePanel.setVisible(false);
		infoPanel.setVisible(false);
		setFrame();
	}
	
	/**
	 * Changes the selected radio button to the delete radio button.
	 */
	private void deleteRadioButtonActionPerformed(){
		pathRadioButton.setSelected(false);
		addRadioButton.setSelected(false);
		deleteRadioButton.setSelected(true);
		infoRadioButton.setSelected(false);
		pathPanel.setVisible(false);
		addPanel.setVisible(false);
		deletePanel.setVisible(true);
		infoPanel.setVisible(false);
		setFrame();
	}
	
	/**
	 * Changes the selected radio button to the info radio button.
	 */
	private void infoRadioButtonActionPerformed(){
		pathRadioButton.setSelected(false);
		addRadioButton.setSelected(false);
		deleteRadioButton.setSelected(false);
		infoRadioButton.setSelected(true);
		pathPanel.setVisible(false);
		addPanel.setVisible(false);
		deletePanel.setVisible(false);
		infoPanel.setVisible(true);
		setFrame();
	}
	
	/**
	 * Display a {@link JOptionPane} informing the user that the file
	 * the program is reading from is not formatted correctly for
	 * loading data.
	 */
	private void displayBadFormat(){
		JOptionPane.showMessageDialog(this, "ERROR: Bad file format",
				"ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Changes the layout.
	 */
	private void setLayout(){
		GroupLayout layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(buttonPanel)
				.addComponent(pathPanel)
				.addComponent(addPanel)
				.addComponent(deletePanel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(infoPanel)
				)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(buttonPanel)
				.addGroup(layout.createParallelGroup()
						.addComponent(pathPanel)
						.addComponent(addPanel)
						.addComponent(deletePanel)
						.addComponent(infoPanel)
				)
		);
		
		setLayout(layout);
		pack();
		setVisible(true);
		setFrame();
	}

	/**
	 * Update the frame and it's location on the screen.
	 */
	private void setFrame(){
		Dimension size = getPreferredSize();
		Dimension loc = Toolkit.getDefaultToolkit().getScreenSize();
		
		setMinimumSize(size);
		setSize(size);
		
		//Center on screen
		setLocation((loc.width - (int)size.getWidth()) / 2, (loc.height - (int)size.getHeight()) / 2);
	}
	
	/**
	 * Start the program!
	 * @param args
	 */
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Project3("Project 3");
            }
        });
	}
}
