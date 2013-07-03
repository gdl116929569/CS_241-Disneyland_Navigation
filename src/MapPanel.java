import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * The map for displaying the Disney map and its markers.
 * 
 * @author Akira
 */
public class MapPanel extends JPanel {

	/**
	 * Label for displaying the map.
	 */
	private JLabel mapLabel;
	
	/**
	 * Dimension array holding all the marker locations. The locations
	 * are hard-coded.
	 */
	private Dimension[] markerLoc;
	
	/**
	 * The layered pane to lay the markers over the map.
	 */
	private JLayeredPane lp;
	
	/**
	 * The marker selected as the first destination.
	 */
	private Marker dest1;
	
	/**
	 * The marker selected as the second destination.
	 */
	private Marker dest2;
	
	/**
	 * The array of vertices.
	 */
	private Vertex[] vertices;
	
	/**
	 * The image for an unselected marker.
	 */
	private ImageIcon markerB;
	
	/**
	 * The image for a marker that is selected or hovered over.
	 */
	private ImageIcon markerR;
	
	/**
	 * Is this map used for single or double selection?
	 */
	private boolean isSingleSelection;
	
	/**
	 * Constructor for the map panel.
	 */
	public MapPanel(Vertex[] vertices, boolean single){
		this.vertices = vertices;
		isSingleSelection = single;
		lp = new JLayeredPane();
		lp.setLayout(null);
		lp.setVisible(true);
		lp.setOpaque(true);
		lp.setPreferredSize(new Dimension(500,361));
		
		mapLabel = new JLabel();
		mapLabel.setIcon(new ImageIcon("images/Disneyland_Map_by_Captain_Halfbeard2.jpg"));
		mapLabel.setBounds(0,0,mapLabel.getPreferredSize().width, mapLabel.getPreferredSize().height);
		lp.add(mapLabel, 1);
		
		markerLoc = new Dimension[20];
		markerB = new ImageIcon("images/Disney Marker_Black.png");
		markerR = new ImageIcon("images/Disney Marker_Red.png");
		initializeMarkers();
		
		lp.setBounds(0, 0, lp.getPreferredSize().width, lp.getPreferredSize().height);
		add(lp);
	}
	
	private void initializeMarkers(){
		markerLoc[0]  = new Dimension(283, 205); //Main Street
		markerLoc[1]  = new Dimension(284, 294); //Main Gate
		markerLoc[2]  = new Dimension(409, 191); //Space Mountain
		markerLoc[3]  = new Dimension(156, 240); //Indiana Jones
		markerLoc[4]  = new Dimension(287, 127); //Sleeping Beauty's Castle
		markerLoc[5]  = new Dimension(280, 164); //Disney's Statue
		markerLoc[6]  = new Dimension(362, 179); //Star Tours
		markerLoc[7]  = new Dimension(210, 197); //Jungle Cruise
		markerLoc[8]  = new Dimension(191, 129); //Thunder Mountain
		markerLoc[9]  = new Dimension(371, 101); //Matterhorn
		markerLoc[10] = new Dimension(252,  27); //Mickey's House
		markerLoc[11] = new Dimension(280, 259); //Main Street Station
		markerLoc[12] = new Dimension( 85, 175); //Haunted Mansion
		markerLoc[13] = new Dimension( 63, 117); //Splash Mountain
		markerLoc[14] = new Dimension( 76, 190); //New Orleans Station
		markerLoc[15] = new Dimension(236, 183); //Tiki Room
		markerLoc[16] = new Dimension(254, 105); //Snow White's Scary Adventures
		markerLoc[17] = new Dimension(369,  56); //It's A Small World
		markerLoc[18] = new Dimension(150, 190); //Pirates of the Caribbean
		markerLoc[19] = new Dimension(479, 141); //Tomorrowland Station
		
		for(int x = 0; x < markerLoc.length; x++){
			final Marker marker = new Marker(markerLoc[x].width, markerLoc[x].height, vertices[x], markerB);
			Dimension size = marker.getPreferredSize();
			marker.setBounds(markerLoc[x].width, markerLoc[x].height, size.width, size.height);
			marker.setToolTipText(marker.getVertex().getName());
			marker.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {
					if(dest1 == null){
						dest1 = marker;
					}
					else if(dest2 == null && !isSingleSelection){
						dest2 = marker;
					}
					else{
						dest1.setIcon(markerB);
						dest1 = marker;
						if(!isSingleSelection){
							dest2.setIcon(markerB);
							dest2 = null;
						}
					}
					marker.setIcon(markerR);
				}
				public void mouseEntered(MouseEvent e) {
					marker.setIcon(markerR);
				}
				public void mouseExited(MouseEvent e) {
					if(dest1 != marker && dest2 != marker){
						marker.setIcon(markerB);
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
			});
			lp.add(marker, 0);
		}
	}
	
	public Marker getDest1(){
		return dest1;
	}
	
	public Marker getDest2(){
		return dest2;
	}
}
