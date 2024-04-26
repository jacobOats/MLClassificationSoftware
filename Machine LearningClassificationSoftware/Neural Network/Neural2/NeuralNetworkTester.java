package q2;
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
      network.setParameters(7, 10000, 0.4, 2375);

      //train network
      network.train();

      //test network
      network.testData("testfile", "outputfile");
     
      //convert outputs
      network.convertOutput("outputfile", out);

      //validate network
      network.validate("validationfile");
  }
  
  //method to convert given training file 
private static void convertTraining(String inputFile, String outputFile) throws IOException {
	  Scanner inFile = new Scanner(new File(inputFile));
      PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
      
      int numRecords = inFile.nextInt();
      outFile.print(numRecords + " ");
      int numInputs = inFile.nextInt();
      outFile.print(numInputs + " ");
      int numOutputs = inFile.nextInt();
      outFile.print(numOutputs + " ");
      //for each record
      for(int i=0;i<numRecords;i++) {
    	  for(int j=0;j<numInputs;j++) {
    		  if(j == 0) { //credit
    			  int credit = inFile.nextInt();
    			  double normCredit = convertCredit(credit);
    			  outFile.print(normCredit + " ");
    		  }else if(j == 1) { //income
    			  int income = inFile.nextInt();
    			  double normIncome = convertIncome(income);
    			  outFile.print(normIncome + " ");
    		  }else if(j == 2) { //age
    			  int age = inFile.nextInt();
    			  double normAge = convertAge(age);
    			  outFile.print(normAge + " ");
    		  }else if(j == 3) { //sex
    			  String sex = inFile.next();
    			  double normSex = convertGender(sex);
    			  outFile.print(normSex + " ");
    		  }else if(j == 4) { //marital status
    			  String marital = inFile.next();
    			  double normMarital = convertMarital(marital);
    			  outFile.print(normMarital + " ");
    		  }
    	  }
    	  for(int j=0;j<numOutputs;j++) {
	    	  String output = inFile.next();
	    	  double normOut = convertClass(output);
	    	  outFile.print(normOut + " ");
    	  }
    	  outFile.println();
      }
      inFile.close();
      outFile.close();
  }
  //method that converts given validation file
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
    		  if(j == 0) { //credit
    			  int credit = inFile.nextInt();
    			  double normCredit = convertCredit(credit);
    			  outFile.print(normCredit + " ");
    		  }else if(j == 1) { //income
    			  int income = inFile.nextInt();
    			  double normIncome = convertIncome(income);
    			  outFile.print(normIncome + " ");
    		  }else if(j == 2) { //age
    			  int age = inFile.nextInt();
    			  double normAge = convertAge(age);
    			  outFile.print(normAge + " ");
    		  }else if(j == 3) { //sex
    			  String sex = inFile.next();
    			  double normSex = convertGender(sex);
    			  outFile.print(normSex + " ");
    		  }else if(j == 4) { //marital status
    			  String marital = inFile.next();
    			  double normMarital = convertMarital(marital);
    			  outFile.print(normMarital + " ");
    		  }
    	  }
    	  for(int j=0;j<numOutputs;j++) {
	    	  String output = inFile.next();
	    	  double normOut = convertClass(output);
	    	  outFile.print(normOut + " ");
    	  }
    	  outFile.println();
      }
      inFile.close();
      outFile.close();
  }
  //method that converts a given test file
  private static void convertTest(String inputFile, String outputFile) throws IOException {
	  Scanner inFile = new Scanner(new File(inputFile));
      PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
      
      int numRecords = inFile.nextInt();
      outFile.println(numRecords);
      int numInputs = inFile.nextInt();
      //for each record
      for(int i=0;i<numRecords;i++) {
    	  for(int j=0;j<numInputs;j++) {
    		  if(j == 0) { //credit
    			  int credit = inFile.nextInt();
    			  double normCredit = convertCredit(credit);
    			  outFile.print(normCredit + " ");
    		  }else if(j == 1) { //income
    			  int income = inFile.nextInt();
    			  double normIncome = convertIncome(income);
    			  outFile.print(normIncome + " ");
    		  }else if(j == 2) { //age
    			  int age = inFile.nextInt();
    			  double normAge = convertAge(age);
    			  outFile.print(normAge + " ");
    		  }else if(j == 3) { //sex
    			  String sex = inFile.next();
    			  double normSex = convertGender(sex);
    			  outFile.print(normSex + " ");
    		  }else if(j == 4) { //marital status
    			  String marital = inFile.next();
    			  double normMarital = convertMarital(marital);
    			  outFile.print(normMarital + " ");
    		  }
    	  }
    	 outFile.println();
      }
      inFile.close();
      outFile.close();
  }
	  //method normalizes credit
	private static double convertCredit(int credit) {
		return credit/900.0;
	}
	//method normalizes income
	private static double convertIncome(int income) {
		return income/90.0;
	}
	//method normalizes age
	private static double convertAge(int age) {
		return age/80.0;
	}
	//method converts gender
	private static double convertGender(String sex) {
		if(sex.equals("male")) 
			return 1.0;
		else
			return 0.0;		//female
		
	}
	//method that converts class of a record
	private static double convertClass(String output) {
		if(output.equals("high"))
			return 1.0;
		else if(output.equals("medium"))
			return .75;
		else if(output.equals("low"))
			return .5;
		else
			return .25;  //undetermined
	}
	//method that converts marital status
	private static double convertMarital(String marital) {
		if(marital.equals("single")) 
			return 1.0;
		else if(marital.equals("divorced"))
			return .5;
		else
			return 0.0;		//married
	}
}
