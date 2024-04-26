package q3;
import java.io.*;
import java.util.Scanner;

//Program tests neural network in a specific application
public class NeuralNetworkTester
{
  //Main method
  public static void main(String[] args) throws IOException
  {
	  //get files
	  Scanner input = new Scanner(System.in);
	  System.out.println("Enter training file: ");
	  String training = input.nextLine();
	  System.out.println("Enter test file: ");
	  String test = input.nextLine();
	  System.out.println("Enter validation file: ");
	  String valid = input.nextLine();
	  System.out.println("Enter output file: ");
	  String out = input.nextLine();
	  
      //construct neural network
      NeuralNetwork network = new NeuralNetwork();
      
      //normalize files
      convertTraining(training,"trainingfile");
      convertTest(test, "testfile");
      convertValidation(valid, "validationfile");
      
      //load training data
      network.loadTrainingData("trainingfile");

      //set parameters of network
      network.setParameters(100, 1000, 0.5, 2375);

      //train network
      network.train();

      //test network
      network.testData("testfile", "outputfile");
     
      //convert outputs
      network.convertOutput("outputfile", out);

      //validate network
      network.validate("validationfile");
  }
//method to convert training file of given input file
private static void convertTraining(String inputFile, String outputFile) throws IOException {
	  Scanner inFile = new Scanner(new File(inputFile));
      PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
      
      int numRecords = inFile.nextInt();
      outFile.println(numRecords);
      int numInputs = inFile.nextInt();
      outFile.println(numInputs);
      int numOutputs = inFile.nextInt();
      outFile.println(numOutputs);
      //for each record
      for(int i=0;i<numRecords;i++) {
    	  for(int j=0;j<numInputs;j++) {
    		  double inp = inFile.nextDouble();
    		  outFile.print(inp + " ");
    	  }
    	  for(int j=0;j<numOutputs;j++) {
	    	  double output = inFile.nextDouble();
	    	  double normOut = convertOut(output);
	    	  outFile.print(normOut + " ");
    	  }
    	  outFile.println();
      }
      inFile.close();
      outFile.close();
  }
  //method to convert validation file 
  private static void convertValidation(String inputFile, String outputFile) throws IOException {
	  Scanner inFile = new Scanner(new File(inputFile));
      PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
      
      int numRecords = inFile.nextInt();
      outFile.println(numRecords);
      int numInputs = inFile.nextInt();
      int numOutputs = inFile.nextInt();
      //for each record
      for(int i=0;i<numRecords;i++) {
    	  for(int j=0;j<numInputs;j++) {
    		  double inp = inFile.nextDouble();
    		  outFile.print(inp + " ");
    	  }
    	  for(int j=0;j<numOutputs;j++) {
    		  double output = inFile.nextDouble();
        	  double normOut = convertOut(output);
        	  outFile.print(normOut + " ");
    	  }
    	  outFile.println();
      }
      inFile.close();
      outFile.close();
  }
  //method to convert test file
  private static void convertTest(String inputFile, String outputFile) throws IOException {
	  Scanner inFile = new Scanner(new File(inputFile));
      PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
      
      int numRecords = inFile.nextInt();
      outFile.println(numRecords);
      int numInputs = inFile.nextInt();
      //for each record
      for(int i=0;i<numRecords;i++) {
    	  for(int j=0;j<numInputs;j++) {
    		  double inp = inFile.nextDouble();
    		  outFile.print(inp + " ");
    	  }
    	 outFile.println();
      }
      inFile.close();
      outFile.close();
  }
	  //method normalizes output
	private static double convertOut(double output) {
		if(output == 2.0) 
			return 1.0;
		else if(output == 1.0)
			return .5;
		else
			return 0.0;		//0
	}
}
