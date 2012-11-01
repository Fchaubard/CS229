import java.util.Random;

public class TriangleArray {
	//attributes
	private static int depth = 4;
	private static int min = 0;
	private static int max = 19;
	private static int[][]triangleArray = new int[depth][];
	private static int[][] newTriangleArray = new int[depth][]; // to hold the summed values as we approach the top
	private static int[] nodeArray = new int[depth];
	
	
	//methods
	private static void createTriangleArray(){
	
		Random rand;
		
		for(int i=0; i<depth; i++)
		{
			triangleArray[i] = new int[i+1]; 
			
			for(int j=0; j<=i; j++)
			{
				rand = new Random();
				int temp = rand.nextInt(max - min + 1);
				triangleArray[i][j] =  temp+ min;
			}
		}
		return;
	}
	
	private static int[][] setEqualTo(int[][] triangleArray2) {
		
		int[][] array = new int[depth][];
		
		for(int i=0; i<depth; i++)
		{
			array[i] = new int[i+1]; 
			for(int j=0; j<=i; j++)
			{
				array[i][j] =  triangleArray2[i][j];
			}
		}
		return array;
	}
	
	private static void printTrinagleArray(int[][]tArray){
		
		for(int i=0; i<depth; i++)
		{
			for(int j=0; j<=i; j++)
			{
				System.out.printf("%s ", tArray[i][j]);
			}
			System.out.println("\n");
		}
	}
	
	
	private static void sumArrayFromBottomUp() {
		// Lets try a Bottom-Up approach
		// Start from the 2nd to last row and find the best path [i+1] by summing. 
		// Save the highest "summed value" node off into nodeArray.
		// Then go to the 3rd to last row and sum the new 2nd row with the best [i+1] path.
		// Save the max node off.
		// Then go to the ...
		// Until we reach the top row. 
		// The final top row value will be the maximum sum itself! 
		newTriangleArray = setEqualTo(triangleArray); // make a copy of original array
		
		for (int i = depth - 2; i >= 0; i--){
			for (int j = 0; j <= i; j++){
				if (newTriangleArray[i + 1][j] > newTriangleArray[i + 1][j + 1]){
					
					newTriangleArray[i][j] = newTriangleArray[i][j] + newTriangleArray[i + 1][j];
				}
				else{
					
					newTriangleArray[i][j] = newTriangleArray[i][j] + newTriangleArray[i + 1][j + 1];
				}
				
			}
		}
	}

	private static void findNodes() {
		// now undo the summed array to find the nodes
		nodeArray[0]=triangleArray[0][0];
		int index = 0; // keep your bearings on where you are horizontally
		for (int i = 0; i < depth-1; i++){
			
				if (newTriangleArray[i + 1][index] > newTriangleArray[i + 1][index + 1]){
					nodeArray[i+1]=triangleArray[i+1][index];
				}
				else{
					index+=1;
					nodeArray[i+1]=triangleArray[i+1][index];
				}
				
			
		}
	}

	private static void printNode(int[] nodeArray2) {
		
		for (int i=0; i<depth; i++){
			System.out.printf("%s -> ",nodeArray2[i]);
		}
		System.out.println("end");
	}
	
	private static void calculateBestPath() {
		//wrapper method
		
		// Sum from Button Up
		sumArrayFromBottomUp();
		
		// Reverse the summed tree to find the nodes
		findNodes();
	}
	
	
	
	
	
	public static void main(String[]args){
		
		// Create Random triangular array of certain depth and bounded values
		createTriangleArray();
		
		// Print the created array
		printTrinagleArray(triangleArray);
		
		// Calculate Best Path
		calculateBestPath();
		
		// Print the nodes
		printNode(nodeArray);
		
		
	}

}
