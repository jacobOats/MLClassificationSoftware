package q4;

import java.io.*;
import java.util.*;

/*****************************************************************************/

//This programs solves knapsack problem using genetic algorithm 
public class Knapsack
{
    private int[][] table;                //weights and values of items
    private int numberItems;              //number of items
    private int maximumWeight;            //maximum weight

    private int populationSize;           //population size
    private int stringLength;             //string length
    private int numberIterations;         //number of iterations
    private double crossoverRate;         //crossover rate
    private double mutationRate;          //mutation rate
    private Random random;                //random number generator

    private int[][] population;           //population of strings
    private double[] fitnessValues;       //fitness values of strings  
    
    private PrintWriter out;

    /**
     * @throws IOException ***********************************************************************/

    //Costructor of Knapsack class
    public Knapsack(int[][] table, int numberItems, int maximumWeight, String out) throws IOException 
    {
        //set knapsack data
        this.table = table;
        this.numberItems = numberItems;
        this.maximumWeight = maximumWeight;
        this.out = new PrintWriter(new FileWriter(out));
    }

    /*************************************************************************/

    //Method sets parameters of genetic algorithm
    public void setParameters(int populationSize, int stringLength,
    int numberIterations, double crossoverRate, double mutationRate, int seed)
    {
        //set genetic algorithm parameters
        this.populationSize = populationSize;
        this.stringLength = stringLength;
        this.numberIterations = numberIterations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.random = new Random(seed);

        //create population and fitness arrays
        this.population = new int[populationSize][stringLength];
        this.fitnessValues = new double[populationSize];
    }

    /*************************************************************************/

    //Method executes genetic algorithm
    public void solve()
    {
         //initialize population
         initialize();

         //create generations
         for (int i = 0; i < numberIterations; i++)
         {
             //crossover strings
             crossover(); 

             //mutate strings
             mutate();

             //reproduce strings
             reproduce();
         }

         //choose best string
         solution();
    }

    /*************************************************************************/

    //Method initializes population
    private void initialize()
    {
        //initialize strings with random 0/1
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = random.nextInt(2);

        //initial fitness values are zero
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = 0;
    }

    /*************************************************************************/

    //Method performs crossover operation
    private void crossover()
    {
        //go thru each string
        for (int i = 0; i < populationSize; i++)
        {
             //if crossover is performed
             if (random.nextDouble() < crossoverRate)
             {
                  //choose another string
                  int j = random.nextInt(populationSize);

                  //choose crossover point
                  int cut = random.nextInt(stringLength);

                  //crossover bits of the two strings
                  for (int k = cut; k < stringLength; k++)
                  {
                       int temp = population[i][k];
                       population[i][k] = population[j][k];
                       population[j][k] = temp;
                  }
             }
        }
    }

    /*************************************************************************/

    //Method performs mutation operation
    private void mutate()
    {
        //go thru each bit of each string
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
            {
                //if mutation is performed
                if (random.nextDouble() < mutationRate)
                {
                    //flip bit from 0 to 1 or 1 to 0
                    if (population[i][j] == 0)
                        population[i][j] = 1;
                    else
                        population[i][j] = 0;
                }
            }    
    }

    /*************************************************************************/

    //Method performs reproduction operation
    private void reproduce()
    {
        //find fitness values of all strings
        computeFitnessValues();

        //create array for next generation
        int[][] nextGeneration = new int[populationSize][stringLength];

        //repeat population number of times
        for (int i = 0; i < populationSize; i++)
        {
            //select a string from current generation based on fitness
            int j = select();

            //copy selected string to next generation
            for (int k = 0; k < stringLength; k++)
                nextGeneration[i][k] = population[j][k];
        }

        //next generation becomes current generation
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = nextGeneration[i][j];
    }

    /*************************************************************************/

    //Method computes fitness values of all strings
    private void computeFitnessValues()
    {
        //compute fitness values of strings
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //accumulate fitness values
        for (int i = 1; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i] + fitnessValues[i-1];

         //normalize accumulated fitness values
         for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i]/fitnessValues[populationSize-1];            
    }

    /*************************************************************************/

    //Method selects a string based on fitness values
    private int select()
    {
        //create a random number between 0 and 1
        double value = random.nextDouble();

        //determine on which normalized accumulated fitness value it falls
        int i;
        for (i = 0; i < populationSize; i++)
            if (value <= fitnessValues[i])
                break;

        //return the string index where the random number fell
        return i;
    }

    /*************************************************************************/

    //Method finds the best solution
    private void solution()
    {
        //compute fitness values of strings
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //find the string with best fitness value
        int best = 0;
        for (int i = 0; i < populationSize; i++)
            if (fitnessValues[i] > fitnessValues[best])
                best = i;

        //display the best string information
        display(population[best]);
    }

    /*************************************************************************/

    //Method computes fitness value of a string
    private double fitness(int[] string)
    {
        //initialize weight and value
        int weight = 0;
        int value = 0;

        //go thru string
        for (int i = 0; i < stringLength; i++)
            //add up values and weights of selected items
            if (string[i] == 1)
            {
                weight += table[i][0];
                value += table[i][1];  
            }

        //if total weight does not exceed maximum weight fitness is total value
        if (weight <= maximumWeight)
            return value;
        //if total weight exceeds maximum weight fitness is zero
        else
            return 0;   
    }

    /*************************************************************************/

    //Method displays weight and value information about a string
    private void display(int[] string)
    {
        //go thru string
    	int weight = 0, value = 0;
    	//print houses
    	out.print("Houses: ");
        for (int i = 0; i < stringLength; i++) {
        	//if item is selected
            if (string[i] == 1) {
            	//print house numbers chosen, starting index is 1
               out.print(i + 1 + " ");
               //add weight and values of each house to total weight and value
               weight = weight + table[i][0];
               value = value + table[i][1];
            }
        }
        //print the total price and weight
        out.println("\nPrice: " + weight);
        out.println("Profit: " + value);
        //close file
        out.close();
    }

    /*************************************************************************/
}
