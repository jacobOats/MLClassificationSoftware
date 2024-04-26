package q1;
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
      network.setParameters(4, 10000, 0.3, 2375);

      //train network
      network.train();

      //test network
      network.testData("testfile", "outputfile");
     
      //convert outputs
      network.convertOutput("outputfile", out);

      //validate network
      network.validate("validationfile");
  }
  
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
    		  double normInp = convertInp(inp);
    		  outFile.print(normInp + " ");
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
    		  double normInp = convertInp(inp);
    		  outFile.print(normInp + " ");
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
    		  double normInp = convertInp(inp);
    		  outFile.println(normInp + " ");
    	  }
    	 outFile.println();
      }
      inFile.close();
      outFile.close();
  }
	  //method normalizes output
	private static double convertOut(double output) {
		return output/186.93;
	}
	//method normalizes input 2
	private static double convertInp(double inp) {
		return inp/9.97;
	}
}
