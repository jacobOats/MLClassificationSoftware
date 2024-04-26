package q3;

import java.io.*;
import java.util.*;

//Program tests Bayes classifier in specific application
public class BayesTester
{
    /*************************************************************************/

    //Main method
    public static void main(String[] args) throws IOException
    {
    	//get files
		Scanner input = new Scanner(System.in);
    	System.out.println("Enter training file: ");
    	String training = input.nextLine();
    	System.out.println("Enter test file: ");
    	String test = input.nextLine();
    	System.out.println("Enter file for output of classified data: ");
    	String classified = input.nextLine();
    	
        //preprocess files
        convertTrainingFile(training, "trainingfile");
        convertValidationFile(training, "validationfile"); 
        convertTestFile(test, "testfile"); 

        //construct bayes classifier
        Bayes classifier = new Bayes();

        //load training data
        classifier.loadTrainingData("trainingfile");

        //compute probabilities
        classifier.computeProbability();

        //classify data
        classifier.classifyData("testfile", "classifiedfile");
        
        //validate classifier
        classifier.validate("validationfile");
        
        //postprocess files
        convertClassFile("classifiedfile", classified);
    }

    /****************************************************************************/

    //Method converts training file to numerical format
    private static void convertTrainingFile(String inputFile, String outputFile) throws IOException
    {
        //input and output files
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

        //read number of records, attributes, classes
        int numberRecords = inFile.nextInt(); 
        int numberAttributes = inFile.nextInt();
        int numberClasses = inFile.nextInt();

        //read attribute values
        int[] attributeValues = new int[numberAttributes];
        for (int i = 0; i < numberAttributes; i++)
            attributeValues[i] = inFile.nextInt();

        //write number of records, attributes, classes
        outFile.println(numberRecords + " " + numberAttributes + " " + numberClasses);

        //write attribute values
        for (int i = 0; i < numberAttributes; i++)
            outFile.print(attributeValues[i] + " ");
        outFile.println();

        //for each record
        for (int i = 0; i < numberRecords; i++)    
        {        
        	//convert number of languages
            int lang = inFile.nextInt();
            int langNum = convertLang(lang);
            outFile.print(langNum + " ");
            //convert java
            String java = inFile.next();                      
            int javaNumber = convertJavaToNumber(java);
            outFile.print(javaNumber + " ");
            //convert experience
            int exp = inFile.nextInt();
            int expNum = convertExp(exp);
            outFile.print(expNum + " ");
            //convert major
            String major = inFile.next();                         
            int majorNumber = convertMajorToNumber(major);
            outFile.print(majorNumber + " ");
            //convert acad
            String acad = inFile.next();                   
            int acadNumber = convertAcadToNumber(acad);
            outFile.print(acadNumber + " "); 
            //convert class/interview
            String interview = inFile.next();
            int interviewNum = convertClassToNumber(interview);
            outFile.print(interviewNum);
                     
            outFile.println();
        }

        inFile.close();
        outFile.close();
    }

    /****************************************************************************/

    //Method converts validation file to numerical format
    private static void convertValidationFile(String inputFile, String outputFile) throws IOException
    {
        //input and output files
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

        //read number of records
        int numberRecords = inFile.nextInt(); 
        for(int i=0; i< 7; i++)
        	inFile.nextInt(); //read irrelevant header values
        //write number of records
        outFile.println(numberRecords);

        //for each record
        for (int i = 0; i < numberRecords; i++)    
        {       
        	//convert lang
        	int lang = inFile.nextInt();
        	int langNum = convertLang(lang);
            outFile.print(langNum + " ");
            //convert java
            String java = inFile.next();                       
            int javaNumber = convertJavaToNumber(java);
            outFile.print(javaNumber + " ");
            //convert experience
            int exp = inFile.nextInt();
            int expNum = convertExp(exp);
            outFile.print(expNum + " ");
            //convert major
            String major = inFile.next();                         
            int majorNumber = convertMajorToNumber(major);
            outFile.print(majorNumber + " ");
            //convert acad
            String acad = inFile.next();                    
            int acadNumber = convertAcadToNumber(acad);
            outFile.print(acadNumber + " "); 
            //convert interview/class
            String interview = inFile.next();
            int interviewNum = convertClassToNumber(interview);
            outFile.print(interviewNum);
            
            outFile.println();
        }

        inFile.close();
        outFile.close();
    }

    /****************************************************************************/
 
    //Method converts test file to numerical format
    private static void convertTestFile(String inputFile, String outputFile) throws IOException
    {
        //input and output files
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

        //read number of records
        int numberRecords = inFile.nextInt();    
    
        //write number of records
        outFile.println(numberRecords);

        //for each record
        for (int i = 0; i < numberRecords; i++)    
        {        
        	//convert lang
        	int lang = inFile.nextInt();
        	int langNum = convertLang(lang);
            outFile.print(langNum + " ");
            //convert java
            String java = inFile.next();                     
            int javaNumber = convertJavaToNumber(java);
            outFile.print(javaNumber + " ");
            //convert experience
            int exp = inFile.nextInt();
            int expNum = convertExp(exp);
            outFile.print(expNum + " ");
            //convert major
            String major = inFile.next();                        
            int majorNumber = convertMajorToNumber(major);
            outFile.print(majorNumber + " ");
            //convert acad
            String acad = inFile.next();                   
            int acadNumber = convertAcadToNumber(acad);
            outFile.print(acadNumber + " "); 
            
            outFile.println();
        }

        inFile.close();
        outFile.close();
    }

    /****************************************************************************/
 
    //Method converts class file to text format
    private static void convertClassFile(String inputFile, String outputFile) throws IOException
    {
        //input and output files
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

        //read number of records
        int numberRecords = inFile.nextInt();    
    
        //write number of records
        //outFile.println(numberRecords);

        //for each record
        for (int i = 0; i < numberRecords; i++)    
        {      
            int number = inFile.nextInt();                       //convert class number
            String className = convertNumberToClass(number);
            outFile.println(className);
        }

        inFile.close();
        outFile.close();          
    } 

    /****************************************************************************/

    //Method converts java type to number
    private static int convertJavaToNumber(String java)
    {
        if (java.equals("java"))
            return 1;
        else
            return 2;           
    }

    /*****************************************************************************/

    //Method converts major to number
    private static int convertMajorToNumber(String major)
    {
        if (major.equals("cs"))
            return 1;
        else
            return 2;           
    }

    /****************************************************************************/
  //Method converts major to number
    private static int convertLang(int lang)
    {
        if (lang == 0)
            return 1;
        else if(lang == 1)
        	return 2;
        else
            return 3;           
    }
    /****************************************************************************/
    //Method converts major to number
      private static int convertExp(int exp)
      {
          if (exp == 0)
              return 1;
          else if(exp == 1)
          	return 2;
          else
              return 3;           
      }
    /*****************************************************************************/
    //Method converts acad status to number
    private static int convertAcadToNumber(String acad)
    {
        if (acad.equals("A"))
            return 1;
        else if(acad.equals("B"))
            return 2;          
        else if(acad.equals("C"))
        	return 3;
        else
        	return 4;
    }

    /****************************************************************************/

    //Method converts class name to number
    private static int convertClassToNumber(String className)
    {
        if (className.equals("interview"))
            return 1;
        else
            return 2; 
    }

    /*****************************************************************************/

    //Method converts number to class name
    private static String convertNumberToClass(int number)
    {
        if (number == 1)
           return "interview";
        else
           return "no";
    }

    /*****************************************************************************/
}


