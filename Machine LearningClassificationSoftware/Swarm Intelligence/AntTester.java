package q1;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Program tests travelling salesman solver
public class AntTester
{
  /**
 * @throws IOException ******************************************************************/

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
  	
    //create adjacency matrix
    int size = inFile.nextInt();
    int numEdge = inFile.nextInt();
    int[][] matrix = new int[size][size];
    
    //set diagonal to 0
    for(int i=0;i<size;i++)
    	matrix[i][i] = 0;
    
    for(int i=0; i<numEdge;i++) {
    	//get vertices and weight of edge between them
    	int u = inFile.nextInt() - 1;
    	int v = inFile.nextInt() - 1;
    	int weight = inFile.nextInt();
    	//set weight to proper locations in matrix
    	matrix[u][v] = matrix[v][u] = weight;
    }

    //display adjacency matrix
    displayMatrix(matrix, size);

    //create travelling salesman solver
    Ant a = new Ant(matrix, size, out);

    //set parameters
    int seed = 93684623;
    int iterations = 100;
    double chemicalExponent = 1.0;
    double distanceExponent = 1.0;
    double initialDeposit = 100;
    double depositAmount = 5;
    double decayRate = .4;
    a.setParameters(iterations, chemicalExponent, distanceExponent, 
    initialDeposit, depositAmount, decayRate, seed);

    //find optimal solution
    a.solve();
  }
/* 160 file 1
   int seed = 93684623;
    int iterations = 100;
    double chemicalExponent = 1.0;
    double distanceExponent = 1.0;
    double initialDeposit = 100;
    double depositAmount = 5;
    double decayRate = .4;
    a.setParameters(iterations, chemicalExponent, distanceExponent, 
    initialDeposit, depositAmount, decayRate, seed);
    
    446 file 2
    int seed = 93684623;
    int iterations = 100;
    double chemicalExponent = 1.0;
    double distanceExponent = 1.0;
    double initialDeposit = 100;
    double depositAmount = 5;
    double decayRate = .4;
    a.setParameters(iterations, chemicalExponent, distanceExponent, 
    initialDeposit, depositAmount, decayRate, seed);
*/
  /**********************************************************************/

  //method prints an adjacency matrix
  public static void displayMatrix(int[][] matrix, int size)
  {
       for (int i = 0; i < size; i++)
       {
           for (int j = 0; j < size; j++)
               System.out.print(matrix[i][j] + " ");
           System.out.println();
       }
  }

  /***********************************************************************/
}