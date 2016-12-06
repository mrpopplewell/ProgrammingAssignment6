import java.util.*;
import java.io.*;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Info-I202 Programming Assignment 6
 * ProgrammingAssignment6.java
 * @author Michael Popplewell
 */
public class ProgrammingAssignment6{
    private int CITI; //# of cities
    private int[][] adjacency;
    private int bestcost = Integer.MAX_VALUE;
    ArrayList<Integer> bestpath; 
    private Stack<Integer> pathStack;
    public ProgrammingAssignment6(int n){
	CITI = n;
        pathStack = new Stack<Integer>();
	adjacency = new int[CITI][CITI];
	bestpath = new ArrayList<>();
    } // Lab5 constructor
    
    /**
     * This method retrieves a String - a name of a file - under the variable 'fname', reads the file, and places the values of the 
     * file - which contains an array - within the adjacency matrix. There is no physical output. Only the change of adjacency occurs. 
     * @param fname 
     */
    public void populateMatrix(String fname){
	File f = new File(fname);
	try{
            Scanner input = new Scanner(f);
            int i,j;
            for(i = 0; i < CITI && input.hasNext(); i++){
		for(j = i; j < CITI && input.hasNext(); j++){
                    if(i == j){
			adjacency[i][j] = 0;
                    } // if
                    else{
			int value = input.nextInt();
			adjacency[i][j] = value;
			adjacency[j][i] = value;
                    } // else
		} // for
            } // for
            input.close();
        } // try 
	catch(IOException e){
            System.out.println("File reading failed!");
	} // catch
    } // populateMatrix method
    public int cost(ArrayList<Integer> path){
        int cost = 0;
        for(int i = 0; i < path.size()-1; i++){
            cost += adjacency[path.get(i)][path.get(i+1)];
        } // for
        if(path.size() == CITI){
            cost += adjacency[path.get(path.size()-1)][0];
        } // if
        return cost; 
    } // cost method
    
    /**
     * This algorithm finds the shortest path between two nodes within a tree. It does this by utilizing a stack called 'pathStack'.
     */
    public void tsp() {
        CITI = adjacency[1].length - 1;
        int[] visitedCities = new int[CITI + 1];
        visitedCities[0] = 1;
        pathStack.push(1);
        int currentCity = 1, closestCity = 0, i;
        int min = Integer.MAX_VALUE;
        boolean minFlag = false;
        System.out.print(currentCity + "\t");

        while (!pathStack.isEmpty()) {
            currentCity = pathStack.peek();
            i = 1;
            min = Integer.MAX_VALUE;
            while (i <= CITI) {
                if (adjacency[currentCity][i] > 1 && visitedCities[i] == 0) {
                    if (min > adjacency[currentCity][i]) {
                        min = adjacency[currentCity][i];
                        closestCity = i;
                        minFlag = true;
                    } // if
                } // if
                i++;
            } // while
            if (minFlag) {
                visitedCities[closestCity] = 1;
                pathStack.push(closestCity);
                bestpath.add(closestCity);
                System.out.print(closestCity + "\t");
                minFlag = false;
                continue;
            } // if
            pathStack.pop();
        } // while
        CITI++;
        System.out.println("\nCost of path: " + cost(bestpath));
    } // tsp
    

   public static void main(String[] args){
        ProgrammingAssignment6 tsp = new ProgrammingAssignment6(29);
        tsp.populateMatrix("tsp29.txt");
        long start = System.nanoTime();
        tsp.tsp();
        long stop = System.nanoTime();
        long duration = stop - start;
        System.out.println("Time duration for tsp.29: " + duration);
        
    } // main
} // ProgrammingAssignment6
