package q2;
import java.io.*;
import java.util.*;

//Nearest neighbor classifier
public class NearestNeighbor
{
    /*************************************************************************/

    //Record class (inner class)
    private class Record 
    {
        private int[][] attributes;         //attributes of record      
        private int className;               //class of record

        //Constructor of Record
        private Record(int[][] attributes, int className)
        {
            this.attributes = attributes;    //set attributes 
            this.className = className;      //set class
        }
    }

    /*************************************************************************/

    private int numberRecords;               //number of training records   
    private int numberAttributes;            //number of attributes   
    private int numberClasses;               //number of classes
    private int numberNeighbors;             //number of nearest neighbors
    private ArrayList<Record> records;       //list of training records

    /*************************************************************************/

    //Constructor of NearestNeighbor
    public NearestNeighbor()
    {         
        //initial data is empty           
        numberRecords = 0;      
        numberAttributes = 0;
        numberClasses = 0;
        numberNeighbors = 0; 
        records = null;                        
    }

    /*************************************************************************/

    //Method loads data from training file, does not need
    //any record or class number at the top of file
    public void loadTrainingData(String trainingFile) throws IOException
    {
         Scanner inFile = new Scanner(new File(trainingFile));
         String line = " "; String[] process = new String[16];
         numberRecords = countRecords(trainingFile);
         numberClasses = 2;
         //create empty list of records
         records = new ArrayList<Record>();   
         while(inFile.hasNextLine()) {
        	 int[][] attributeArray = new int[16][16];
	         //for each record
	         for (int i = 0; i < 16; i++)    
	         {
	        	 line = inFile.nextLine();
	        	 process = line.strip().split(" ");
	             if(!process[0].equals("")) {            
		             //read attribute values
		             for (int j = 0; j < 16; j++)   
		                  attributeArray[i][j] = Integer.parseInt(process[j]);  
	             }
	         }
		             //read class name
	         if(!process[0].equals("")) {
		         int className = Integer.parseInt(inFile.nextLine().strip());
		         //create record
		         Record record = new Record(attributeArray, className);
		         //add record to list of records
		         records.add(record);
	         }
         }
         inFile.close();
    }

    /*************************************************************************/

    //Method sets number of nearest neighbors
    public void setParameters(int numberNeighbors)
    {
        this.numberNeighbors = numberNeighbors;
    }

    /*************************************************************************/

    //Method reads records from test file, determines their classes, 
    //and writes classes to classified file
    public void classifyData(String testFile, String classifiedFile) throws IOException
    {
         Scanner inFile = new Scanner(new File(testFile));
         PrintWriter outFile = new PrintWriter(new FileWriter(classifiedFile));
         String line = " "; String[] process = new String[16];
         while(inFile.hasNextLine()) {
        	 int[][] attributeArray = new int[16][16];
	         //for each record
        	 if(!line.equals("")) {
		         for (int i = 0; i < 16; i++)
		         {
		        	 line = inFile.nextLine();
		        	 process = line.strip().split(" ");
		        	 if(!process[0].equals("")) {
			             //read attribute values
			             for (int j = 0; j < 16; j++)
			                  attributeArray[i][j] = Integer.parseInt(process[j]);
		        	 }
		         }
		         if(!process[0].equals("")) {
			          //find class of attributes
			          int className = classify(attributeArray);
			
			          //write class name
			          outFile.println(className);
			          line = inFile.nextLine(); //dividing lines
		         }
	         }
         }
	         inFile.close();
	         outFile.close();
    }    

    /*************************************************************************/

    //Method determines the class of a set of attributes
    private int classify(int[][] attributes)
    {
        double[] distance = new double[numberRecords];
        int[] id = new int[numberRecords];

        //find distances between attributes and all records
        for (int i = 0; i < numberRecords; i++)
        {
            distance[i] = distance(attributes, records.get(i).attributes);
            id[i] = i;
        }

        //find nearest neighbors
        nearestNeighbor(distance, id);

        //find majority class of nearest neighbors
        int className = majority(id);

        //return class
        return className;
    }

    /*************************************************************************/

    //Method finds the nearest neighbors
    private void nearestNeighbor(double[] distance, int[] id)
    {
        //sort distances and choose nearest neighbors
        for (int i = 0; i < numberNeighbors; i++)
            for (int j = i; j < numberRecords; j++)
                if (distance[i] > distance[j])
                {
                    double tempDistance = distance[i];
                    distance[i] = distance[j];
                    distance[j] = tempDistance;

                    int tempId = id[i];
                    id[i] = id[j];
                    id[j] = tempId;
                }
    }

    /*************************************************************************/

    //Method finds the majority class of nearest neighbors
    private int majority(int[] id)
    {
        int[] frequency = new int[numberClasses];
        //class frequencies are zero initially
        for (int i = 0; i < numberClasses; i++)
            frequency[i] = 0;

        //each neighbor contributes 1 to its class
        for (int i = 0; i < numberNeighbors; i++) {
            frequency[records.get(id[i]).className] += 1;
        }
        //find majority class
        int maxIndex = 0;                         
        for (int i = 0; i < numberClasses; i++)   
            if (frequency[i] > frequency[maxIndex])
               maxIndex = i;
               
        return maxIndex;
    }

    /*************************************************************************/

    //Method finds binary distance between two points
    private double distance(int[][] u, int[][] v)
    {
    	int mismatch = 0;
    	for (int i = 0; i < u.length; i++)
        	for(int j = 0; j < u.length; j++) {
        		if(u[i][j] != v[i][j])
        			mismatch += 1;
        	}
    	return mismatch/256.0; //number of mismatches/bits of 16 * 16 array            
    }

    /*************************************************************************/

    //Method validates classifier using validation file and displays error rate
    public void validate(String validationFile) throws IOException
    {
    	int numError = 0;
    	for(int i=0;i<records.size();i++) {
    		int predicted = classifyValid(records.get(i).attributes, i);
    		int actual = records.get(i).className;
    		if(predicted != actual) {
    			numError += 1;
    		}
    		
    	}
    	//find and print error rate
        double errorRate = 100.0*numError/numberRecords;
        System.out.println("validation error: " + errorRate + "%");
    }
    /************************************************************************/
    //method to count the amount of records in a file
    private int countRecords(String file) throws FileNotFoundException {
    	Scanner inFile = new Scanner(new File(file));
    	int count = 0;
    	while(inFile.hasNextLine()) {
    		String line = inFile.nextLine();
    		if(!(line.equals("")))
    			count++;
    	}
    	return count/17;	//17 lines per record in training file
    }
    /************************************************************************/  
    //Method determines the class of a set of attributes for validation
    //using leave one out method
    private int classifyValid(int[][] attributes, int index)
    {
        double[] distance = new double[numberRecords];
        int[] id = new int[numberRecords];

        //find distances between attributes and all records
        for (int i = 0; i < numberRecords; i++)
        {
        	if(i != index) { //if i != index, it is training record
        		distance[i] = distance(attributes, records.get(i).attributes);
        		id[i] = i;
        	}else { //i == index, meaning it is the validation record, leave it out
        		distance[i] = Integer.MAX_VALUE;
        		id[i] = i;
        	}
        }

        //find nearest neighbors
        nearestNeighbor(distance, id);

        //find majority class of nearest neighbors
        int className = majority(id);

        //return class
        return className;
    }
}
