package q4;
import java.io.*;
import java.util.Scanner;

//Program tests k-means clustering in a specific application
public class KmeansTester
{
  //Main method
  public static void main(String[] args) throws IOException
  {
	//get files
	Scanner input = new Scanner(System.in);
  	System.out.println("Enter test file: ");
  	String test = input.nextLine();
  	System.out.println("Enter file for output of classified data: ");
  	String classified = input.nextLine();
  	//preprocess
  	convertTestFile(test, "testfile");

      //create clustering object
      Kmeans k = new Kmeans();

      //load records
      k.load("testfile");

      //set parameters
      k.setParameters(5, 10000, 58947);

      //perform clustering
      k.cluster();

      //display records and clusters
      k.display(classified);
  }
//method to normalize attibutes of a file containing records
private static void convertTestFile(String inputFile, String outputFile) throws IOException {
	//input and output files
    Scanner inFile = new Scanner(new File(inputFile));
    PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

    //read number of records, attributes
    int numberRecords = inFile.nextInt(); 
    int numberAttributes = inFile.nextInt();

    //write number of records, attributes, classes
    outFile.println(numberRecords + " " + numberAttributes);

    //for each record
    for (int i = 0; i < numberRecords; i++)    
    {          
    	//convert age
        int age = inFile.nextInt();
        double ageNum = normAge(age);
        outFile.print(ageNum + " ");
        //convert income
        int income = inFile.nextInt();                       
        double incomeNumber = normIncome(income);
        outFile.print(incomeNumber + " ");
        //convert credit
        int credit = inFile.nextInt();
        double creditNum = normCredit(credit);
        outFile.print(creditNum);
                 
        outFile.println();
    }
    inFile.close();
    outFile.close();
}
//method to normalize credit
private static double normCredit(int credit) {
	return credit/900.0;
}
//method to normalize income
private static double normIncome(int income) {
	return income/100.0;
}
//method to normalize age
private static double normAge(int age) {
	return age/100.0;
}
  
  
}
