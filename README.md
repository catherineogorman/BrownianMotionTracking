# Brownian Motion Tracking
This project takes in a series of .jpg files of polystyrene beads undergoing Brownian motion, identifies and tracks the beads based on depth-first search image processing techniques, and fits the data to Einstein's model in order to approximate Avogadro's number.

## Background
When particles are suspended in a liquid or gas, they seem to move randomly, a phenomenon known as Brownian motion (named after Robert Brown, a botanist who observed this in the early 19th century when tracking pollen particles in water). In 1905, Einstein wrote a paper theorizing that this motion is caused by the collisions these particles have with water (or other fluid) molecules, which in turn he used to prove that atoms in fact exist. This was later verified experimentally by Jean Perrin, who won the Nobel Prize in Physics for his work. In this project, I replicate Perrin's experiment using image processing techniques to measure and track polystyrene beads and fit displacement data to Einstein's mathematical model in order to estimate Avogadro's number (an important constant in chemistry and physics). 

For more information: 
* [Brownian Motion](https://en.wikipedia.org/wiki/Brownian_motion)
* [Einstein's Paper](http://www.maths.usyd.edu.au/u/UG/SM/MATH3075/r/Einstein_1905.pdf)

## This project: Methods
Using jpg files from videos that record beads undergoing Brownian motion (10 trials, 200 images each), this program detects in the video and tracks their displacement over time using luminance thresholding analysis. In order to do this, we create the helper class `Blob` which contain mass and center of mass coordinates. Blob objects from an individual video frame are constructed and stored using the methods in the `BlobFinder` class, which also distinguishes blobs from beads based on a size threshold P and luminance tau (in this particular experiment, P = 25 px and tau = 180.0). In `BeadTracker`, we read in a series of 200 image files from each run and output the displacement of each blob between each frame using an additional input dt to set the maximum radial displacement considered, which allows us to filter outlier particle data (here, dt = 25.0). In order to obtain Avogadro's number (and a bonus: the [Boltzmann constant!](https://en.wikipedia.org/wiki/Boltzmann_constant)), we pipe the results of `BeadTracker` to `Avogadro` and use the Einstein-Smoluchowski equation and the Stokes-Einstein relation on the output to provide estimates of the two constants.

## Running the analysis
Pick your experimental run # (1 through 10) and pipe the output of `BeadTracker` to `Avogadro` using P = 25, tau = 180.0, and delta = 25.0:

```
% java BeadTracker 25 180.0 25.0 input_data/run_1/*.jpg | java Avogadro
Boltzmann = 1.2535e-23 
Avogadro = 6.6333e+23
```

## Notes:
* The `StdIn`, `StdOut`, and `Picture` classes are part of a standard library I used in my undergraduate classes. They were downloaded from the course website [here](http://introcs.cs.princeton.edu/java/code/). 
