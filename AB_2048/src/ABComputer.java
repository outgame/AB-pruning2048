

public class ABComputer {
	
	private int depth;
	
	public ABComputer (int maxDepth) {
		depth = maxDepth;
	}
	
	private class Move {
		private int move, score;
		
		private Move(int aMove, int aScore) {
			move = aMove;
			score = aScore;
		}
	}
	
	public int bestMove(Grid game) {
		Move best = pickBestMove(game, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE);
		return best.move;
	}
	
	private Move pickBestMove(Grid game, int depth, int low, int high) {
		Move best = new Move(-1, -Integer.MAX_VALUE);
		Move c;
		
		for (int arrow = 0; arrow <= 3; arrow++) {
			Grid nextMove = game.clone();
			nextMove.makeMove(arrow);
			//nextMove.randomInsert();
			
			if (!nextMove.randomInsert() || depth == 0) {
				c = new Move(arrow, -nextMove.staticEval());
			}
			
			else {
				c = pickBestMove(nextMove, depth - 1, -high, -low);
				c.move = arrow;
				c.score = - c.score;
			}
			
			// replace the best
			if (best.score < c.score) {
				best = c;
				
				// pruning
				if (best.score > low)
					low = best.score;
				if (best.score >= high)
					return best;
			}
		}
		return best;
	}
	
	
	public static void main(String args[]) {
		
		Display view = new Display();
		Grid game = new Grid();
		ABComputer vox = new ABComputer(5);

		// start with two random tiles
		game.randomInsert();
		
		int move;
		while (game.randomInsert()) {
			
			// display the grid
			view.show(game);
			
			// ask for a move
			move = vox.bestMove(game);
			
			game.makeMove(move);
		}
		System.out.println("game over");
	}
	
}