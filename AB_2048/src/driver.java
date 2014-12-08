// driver to test methods of Grid and Display
public class driver { 
	public static void main(String args[]) {
		
		Display view = new Display();
		Grid game = new Grid();
		
		// print board
		view.show(game);
		
		// check random insert of tiles
		game.randomInsert();
		game.randomInsert();
		view.show(game);
		System.out.println(game.staticEval());
		
		// check moving a tile
		game.insert(2, 3, 3);
		view.show(game);
		game.move(3, 3, 0, 3);
		view.show(game);
		
		// check pushing two tiles together
		game.insert(2, 0, 3);
		game.insert(2, 1, 3);
		view.show(game);
		int[] dir = {-1, 0}; // up is all 0s for pushing
		game.push(1, 3, dir);
		view.show(game);
		
		game.insert(2, 1, 3);
		game.push(1, 3, dir);
		view.show(game);
		
		// check pushing against a wall and pushing mismatch
		dir[0] = 0;
		dir[1] = -1;
		game.push(0, 3, dir);
		view.show(game);
		
		game.insert(2, 1, 3);
		
		dir[0] = -1;
		dir[1] = 0;
		game.push(0, 2, dir);
		view.show(game);
		
		// check pushing a column together
		game.insert(2, 0, 0);
		game.insert(2, 1, 0);
		game.insert(2, 2, 0);
		game.insert(4, 3, 0);
		view.show(game);
		
		game.insert(2, 0, 0);
		game.insert(2, 1, 0);
		game.insert(2, 2, 0);
		game.insert(2, 3, 0);
		view.show(game);
		
		//game.pushTiles(dir, 0, 0, 0);
		view.show(game);
		
		//game.pushTiles(dir, 0, 0, 0);
		view.show(game);
		
		//game.pushTiles(dir, 0, 0, 0);
		view.show(game);
		
		// debug makeMove by running loop manually
		game.insert(2, 0, 0);
		game.insert(2, 1, 0);
		game.insert(2, 2, 0);
		//game.insert(2, 3, 0);
		view.show(game);
		/*
		System.out.println(game.push(0, 0, dir));
		System.out.println(game.push(1, 0, dir));
		System.out.println(game.push(2, 0, dir));
		System.out.println(game.push(3, 0, dir));
		view.show(game);
		
		System.out.println(game.push(0, 0, dir));
		System.out.println(game.push(1, 0, dir));
		System.out.println(game.push(2, 0, dir));
		System.out.println(game.push(3, 0, dir));
		*/
		// check makeMove
		game.insert(2, 0, 0);
		game.insert(2, 1, 0);
		game.insert(2, 2, 0);
		game.insert(2, 3, 0);
		view.show(game);
		
		game.makeMove(1);
		view.show(game);
		
		// check all directions and general gameplay
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game.randomInsert();
			}
			view.show(game);
			game.makeMove(i);
			view.show(game);
		}
		
		// check availableSpaces by inserting a full board
		for (int i = 0; i < 16; i++) {
			game.randomInsert();
		}
		view.show(game);
		
		// check move input
		int move = view.getMove();
		game.makeMove(move);
		view.show(game);
		
		Grid gameover = new Grid();
		view.show(gameover);
		while(gameover.randomInsert()) {
			view.show(gameover);
		}
		
		System.out.println("game over");
		
		// check tiles can only merge once per turn
		game.insert(2, 0, 0); // 2 2 2 2 
		game.insert(2, 1, 0);
		game.insert(2, 2, 0);
		game.insert(2, 3, 0);
		view.show(game);
		System.out.println(game.staticEval());
		/*
		// starting = 1, don't try to push first tile off
		//game.pushTiles(dir, 0, 0, 1); // 4 2 2 0
		view.show(game);
		// starting = 2, don't push into new merged tile
		//game.pushTiles(dir, 0, 0, 2); // 4 4 0 0
		view.show(game);
		// starting = 3
		//game.pushTiles(dir, 0, 0, 3); // stays at 4 4 0 0
		view.show(game);
		// see the pattern? increment starting on each
		// don't need while loop 
		*/
		// deleted pushTiles method
		game.makeMove(1); // push 2s up
		// 2 2 2 2 --> 4 4 0 0
		view.show(game);
		System.out.println(game.staticEval());
		System.out.println("done");
		
	}
}