/*****************************************************************************/
package q4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//Program tests knapsack solver
public class KnapsackTester
{
    //Main method
    public static void main(String[] args) throws IOException
    {
    	//get files
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter input file name:");
    	String file = input.nextLine();
    	System.out.println("Enter output file name:");
    	String out = input.nextLine();
    	
    	//open file
    	Scanner inFile = new Scanner(new File(file));
    	
    	//get knapsack data
    	int numberItems = inFile.nextInt();
    	int maximumWeight = inFile.nextInt();
    	//initialize table
    	int[][] table = new int[numberItems][2];
    	//read in table from file
    	for(int i=0;i<numberItems;i++) {
    		for(int j=0;j<2;j++) {
    			table[i][j] = inFile.nextInt();
    		}
    	}
         //create knapsack solver
         Knapsack k = new Knapsack(table, numberItems, maximumWeight, out);
         
         //file 13: 20,20,1000,.3,.1,475932 = 187, 639
         //file 14: 50,50,1500,.6,.2,475932 = 1481, 1907
         
         //set parameters of genetic algorithm
         k.setParameters(50, 50, 1500, 0.6, 0.2, 475932);

         //find optimal solution
         k.solve();
    }
}

/*****************************************************************************/