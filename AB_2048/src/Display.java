import java.util.Scanner;
//import java.util.InputMismatchException;


public class Display {
	Scanner input;
	
	// constructor, initializes scanner
	public Display() {
		input = new Scanner(System.in);
	}
	
	// print the grid
	public void show(Grid game) {
		
		for (int i = 0; i < game.getSize(); i++) {
			for (int j = 0; j < game.getSize(); j++) {
				
				printTile(game.getGrid()[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// print one tile values with 
	private void printTile(int val) {
		String s = String.valueOf(val);
		// assuming tile values won't go above 4 digits
		int space = 5 - s.length();
		for (int i = 0; i < space; i++)  System.out.print(" ");
		System.out.print(s);
	}
	
	// temporary read in moves instead of using arrow keys
	public int getMove() throws NumberFormatException {
		System.out.print("left 0, up 1, right 2, down 3 (4 to quit): ");
		int move = -1; 
		
		while (move < 0 || move > 4) {
			try {
				move = Integer.parseInt(input.nextLine());
			}
			catch (NumberFormatException e) {
				continue;
			}
		}
		return move;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}