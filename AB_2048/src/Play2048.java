

public class Play2048 {
	
	public static void main(String args[]) {
		
		Display view = new Display();
		Grid game = new Grid();
		
		int move;
		
		// start with two random tiles
		game.randomInsert();
		
		while (game.randomInsert()) {
			
			// display the grid
			view.show(game);
			
			// ask for a move
			move = view.getMove();
			if (move == 4)  break;
			
			game.makeMove(move);
		}
		
		
		
	}
}