/*************************************************************************
 * Name			: Catherine O'Gorman
 * Run			: java BeadTracker 25 180.0 25.0 input_data/run_1/*.jpg | java Avogadro
 * 				  - integer P (pixel # min threshold)
 * 				  - double tau (monochrome luminance threshold)
 * 				  - double dt (time elapsed between two images)
 * 				  - folder_of_images/*.jpg (source images for blob identification)
 * Output		: Boltzmann = 1.2535e-23
				  Avogadro = 6.6333e+23
 * Dependencies	: StdIn, Color, Blob, BlobFinder, BeadTracker
 * Description	: Using the BeadTracker output from a series of images,
 * 					approximates Avogadro's number and the Boltzmann constant.
 ****************************************************************************/

public class Avogadro {
	public static void main(String[] args) {
		//instance variables
		double D = 0;
		int count = 0;
		double temp;
    
		//reading from StdIn (temp is set to each successive displacement value)
		while (!StdIn.isEmpty()) {
			temp = StdIn.readDouble() * 0.175e-6; //converting to meters
			D += temp*temp;
			count++;
		}
    
		D /= (2*count);
    
		//calculating Boltzmann constant
		double k = D * 6 * Math.PI * 9.135e-4 * 0.5e-6 / 297;
    
		//calculating Avogadro's number
		double N = 8.31457 / k;
    
		//printing formatted results
		StdOut.println("Boltzmann = " + String.format("%10.4e", k));
		StdOut.println("Avogadro = " +  String.format("%10.4e", N));
	}
}