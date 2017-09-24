/*************************************************************************
 * Name			: Catherine O'Gorman
 * Run			: java Blob
 * Dependencies	: StdOut
 * Description	: Defining blob object that contains mass and center of mass
 ****************************************************************************/

public class Blob {
	private double cx;    //x-coordinate of center of mass
	private double cy;    //y-coordinate of center of mass
	private int mass;     //mass of blob
  
	//construct an empty blob
	public Blob() {                    
		mass = 0;
	}
  
  // add a pixel (i, j) to the blob
	public void addMass(int i, int j) {  
		double x = cx*mass+i;
		double y = cy*mass+j;
    
		//increases mass 
		mass++;
    
		//calculates new center of mass
		cx = x/mass;
		cy = y/mass;
	}

	// return number of pixels added = its mass
	public int getMass() {               
	  return mass;
	}
	
	// return distance between centers of masses of this blob and b
	public double distanceTo(Blob b) { 
		double bx = b.cx;
		double by = b.cy;
   
		double dx = Math.abs(cx-bx);
		double dy = Math.abs(cy-by);
    
		return Math.sqrt(dx*dx+dy*dy);
	}
  
	//return string containing this blob's mass
	public String toString() {        
		//and center of mass format center-of-mass coordinates with 4 digits to right
		return String.format("%2d (%8.4f, %8.4f)", mass, cx, cy); // of decimal point
	}  
  
	public static void main(String[] args) {  //for debugging and testing
    
		Blob test1 = new Blob();
		test1.addMass(1, 1);
    		test1.addMass(2, 2);
    		test1.addMass(3, 2);
    		StdOut.println(test1);
    
    		Blob test2 = new Blob();
    		test2.addMass(5, 5);
    		test2.addMass(7, 6);
    		test2.addMass(6, 7);
    		StdOut.println(test2);
    
    		StdOut.println(test2.distanceTo(test1));
  }
}