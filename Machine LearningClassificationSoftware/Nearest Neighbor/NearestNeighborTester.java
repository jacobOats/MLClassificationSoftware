package q2;
import java.io.*;
import java.util.*;

//Program tests nearest neighbor classifier in a specific application
public class NearestNeighborTester
{
    /*************************************************************************/

    //number of nearest neighbors
    private static final int NEIGHBORS = 3;

    //Main method
    public static void main(String[] args) throws IOException
    {
    	//get file names
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter training file: ");
    	String training = input.nextLine();
    	System.out.println("Enter test file: ");
    	String test = input.nextLine();
    	System.out.println("Enter file for output of classified data: ");
    	String classified = input.nextLine();
    	
        //construct nearest neighbor classifier
        NearestNeighbor classifier = new NearestNeighbor();

        //load training data
        classifier.loadTrainingData(training);

        //set nearest neighbors
        classifier.setParameters(NEIGHBORS);

        //validate classfier
        classifier.validate(training);

        //classify test data
        classifier.classifyData(test, classified);
    }
}

