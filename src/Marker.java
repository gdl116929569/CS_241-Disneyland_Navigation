import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class represents a marker for the Disney map. It includes the
 * marker's x and y position relative to the map, the it relates to,
 * and the image for the marker.
 * 
 * @author Akira
 */

public class Marker extends JLabel{
	
	public int x_pos;
	
	public int y_pos;
	
	private JLabel label;
	
	private Vertex vertex;
	
	public Marker(int x, int y, Vertex vertex, ImageIcon markerImage){
		x_pos = x;
		y_pos = y;
		setIcon(markerImage);
		this.vertex = vertex;
	}
	
	public Vertex getVertex(){
		return vertex;
	}
}
