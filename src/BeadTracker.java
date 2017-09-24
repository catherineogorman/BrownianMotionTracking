/*************************************************************************
 * Name			: Catherine O'Gorman
 * Run			: java Beadtracker P tau dt folder_of_images/*.jpg
 * 				  - integer P (pixel # min threshold)
 * 				  - double tau (monochrome luminance threshold)
 * 				  - double dt (time elapsed between two images)
 * 				  - folder_of_images/*.jpg (source images for blob identification)
 * 				  Example: java Blobfinder 25 180.0 25.0 input_data/run_1/*.jpg
 * 				  Output: 
 * Dependencies	: StdIn/Out, Blob, BlobFinder, Picture
 * Description	: Using BlobFinder, takes in a sequence of images and tracks
 *                the displacement of beads
 ****************************************************************************/

public class BeadTracker {
	public static void main(String[] args) { 
		//number of frames given by command-line args
	    int frames = args.length-3;
	    ///instance variables
	    int P = Integer.parseInt(args[0]);
	    double tau = Double.parseDouble(args[1]);
	    double delta = Double.parseDouble(args[2]);
	    Picture pic1;
	    Picture pic2;
	    Blob[] blobA;
	    Blob[] blobB;
	    double[] min;
	    double d;
    
	    //compares two frames at a time to determine displacement
	    for (int n = 0; n < frames-1; n++) {
	    		pic1 = new Picture(args[n+3]);
	    		pic2 = new Picture(args[n+4]);
	      
	    		BlobFinder blob1 = new BlobFinder(pic1, tau);
	    		BlobFinder blob2 = new BlobFinder(pic2, tau);
	      
	    		//finding beads in each frame
	    		blobA = new Blob[blob1.countBeads(P)];
	    		blobB = new Blob[blob2.countBeads(P)];
	    		for (int a = 0; a < blobA.length; a++)
	    			blobA[a] = blob1.getBeads(P)[a];
	    		for (int b = 0; b < blobB.length; b++)
	    			blobB[b] = blob2.getBeads(P)[b];
	      
	    		min = new double[blob2.countBeads(P)];
	      
	    		//comparing beads in frame t+1 to t and determining the minimum displacement 
	    		for (int i = 0; i < blob2.countBeads(P); i++) {
	    			min[i] = blobB[i].distanceTo(blobA[0]);
		        for (int j = 0; j < blob1.countBeads(P); j++) {
		        		d = blobB[i].distanceTo(blobA[j]);
		        		if (d < min[i])
		        			min[i] = d;
		        }
	    		}
	      
	    		//printing formatted results
	    		for (int k = 0; k < blob2.countBeads(P); k++)
	    			if (min[k] < delta) {
	    				StdOut.println(String.format("%8.4f", min[k]));
	    			}
	    }
	}
}