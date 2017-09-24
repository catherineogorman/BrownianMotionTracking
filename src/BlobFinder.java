/*************************************************************************
 * Name			: Catherine O'Gorman
 * Run			: java Blobfinder P tau image.jpg
 * 				  - integer P (pixel # min threshold)
 * 				  - double tau (monochrome luminance threshold)
 * 				  - jpeg file image.jpg (source for blob identification)
 * 				  Example: java Blobfinder 25 180.0 input_data/run_1/frame00001.jpg
 * 				  Output: Identifies # beads and blobs with mass and center
 * Dependencies	: StdIn/StdOut, Blob, Color, Picture
 * Description	: Using Blob data type, takes a picture, bead size and 
 *                threshold to find all the beads in an image.
 ****************************************************************************/
//importing color class to calculate luminance
import java.awt.Color;

public class BlobFinder {
  
	//private instance variables to be used in the program
	private int width;
	private int height;
	private boolean[][] blob;
	private Picture picture;
	private double tau;
	private Blob b;
	private Node start;
  
	//node subclass to create object for linked list
	private class Node {
		private Blob b;
		private Node next;
    
		//Node constructor
		public Node(Blob b) {
			this.b = b;
			this.next = null;
		}
	} 
  
	//method to store each blob (appending to end of linked list)
	private void store(Blob blob) {
		if (start == null)          //case 1: if linked list is currently empty
			start = new Node(blob);
    
		else {                      //case 2: if there are already stored blobs
			Node node = start;
			while (node.next != null) { //traversing list
				node = node.next;
			}
		Node newNode = new Node(blob);   //adding new blob to the end
		node.next = newNode;
		newNode.next = null;
		}
	}
  
  //helper method to calculate luminance
	private double luminance(int i, int j) {
		Color color = picture.get(i, j); 
    
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		return .299*red + .587*green + .114*blue;    
	}
  
	//helper method that recursively calculates the size of a blob
	private void dfs(int x, int y) {
		//return if (x, y) is outside the dimensions of the picture 
		if (x >= width || y >= height) return;
		else if (x < 0 || y < 0) return;
		//return if pixel has already been visited
		else if (blob[x][y]) return;
		//return if pixel is dark and mark as visited
		else if (luminance(x, y) < tau) {
			blob[x][y] = true;
			return;
		}
    
		else {
			//add point to blob and mark as visited
			b.addMass(x, y);      
			blob[x][y] = true;
      
			//recursively call dfs method down, up, left, right
			dfs(x, y-1);
			dfs(x, y+1);
			dfs(x-1, y);
			dfs(x+1, y);
		}
	}
  
	//constructor
	//finds all blobs in the picture using the luminance threshold tau
	public BlobFinder(Picture picture, double tau) {
	    //setting values of each variable
	    start = null;
	    this.picture = picture;
	    this.tau = tau;
	    width = picture.width();
	    height = picture.height();
	    blob = new boolean[width][height];

	    for (int i = 0; i < width; i++) 
	    		for (int j = 0; j < height; j++)
	    			blob[i][j] = false;

    
	    for (int i = 0; i < width; i++) { 
	    		for (int j = 0; j < height; j++) {
	    			if (!blob[i][j] || (luminance(i, j) >= tau)) {
         
	    				b = new Blob();         //creates new blob
	    				dfs(i, j);              //calling dfs
	    				if (b.getMass() != 0)      //storing blob in linked list
	    					store(b);
	    			}
	    			else blob[i][j] = true;
	    		}
	    } 
	}

	// return the number of beads with >= P pixels
	public int countBeads(int P) {
		int size = 0;
		if (start == null) return size;       //0 case

		Node node = start;
	    do {
	      if (node.b.getMass() >= P)              //traversing linked list, determining size
	        size++;
	      node = node.next;
	    } while (node != null);
	    return size;                          //returns number of nodes with blobs >= P
	  }
  
	  // return all beads with >= P pixels
	  public Blob[] getBeads(int P) {
	    Blob[] beads = new Blob[countBeads(P)];
	    
	    if (start == null) return null;       //base case
	    int count = 0;
	    Node node = start;
	    do {
	      if (node.b.getMass() >= P) {
	        beads[count] = node.b;            //creating array to store beads
	        count++;
	      }
	      node = node.next;
	    } while (node != null); 
	    return beads;
	  }
	  
	  public static void main(String[] args) {  
		  //command-line arguments determine P, tau, picture
		  int P = Integer.parseInt(args[0]);           
		  double tau = Double.parseDouble(args[1]);
		  Picture picture = new Picture(args[2]);
	    
		  //outputting results
		  BlobFinder blob = new BlobFinder(picture, tau);
		  StdOut.println(blob.countBeads(P)+" Beads:");
		  for (int i = 0; i < blob.countBeads(P); i++)
			  StdOut.println(blob.getBeads(P)[i]);
		  StdOut.println(blob.countBeads(0)+" Blobs:");
		  for (int i = 0; i < blob.countBeads(0); i++)
			  StdOut.println(blob.getBeads(0)[i]);
	 }
}