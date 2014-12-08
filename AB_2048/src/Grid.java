import java.util.ArrayList;
import java.lang.Math;




public class Grid { // implements GameState
	
	// would be in GameState
	private final static int SIZE = 4; // square is 4x4
	private final static int BOUND = SIZE - 1; // rows are numbered 0 - 3
	private final static int EMPTY = 0; // empty square will hold 0
	
	private int[][] grid; // top left is (0, 0)
	
	// directional arrays for moving tiles
	private final int[] LEFT = {0, -1}; // ascii 37
	private final int[] UP = {-1, 0};  // 38
	private final int[] RIGHT = {0, 1}; // 39
	private final int[] DOWN = {1, 0}; // 40
	
	
	// constructor - create new empty grid
	public Grid() {
		super(); // primary constructor
		
		grid = new int[SIZE][SIZE];
		emptyGrid();
		
	}
	
	public Grid(int[][] initGrid) {
		grid = initGrid;
	}
	
	// used for AB pruning to create a new state of the board
	public Grid clone() {
		int[][] remake = new int[SIZE][SIZE];
		// make new board so the new state isn't referencing the old board
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				remake[i][j] = grid[i][j];
			}
		}
		return new Grid(remake);
	}
	
	// set grid to all 0s
	private void emptyGrid() {
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[i][j] = EMPTY;
			}
		}
	}
	
	// clone grid function?
	
	public int[][] getGrid() {
		return grid;
	}
	
	public int getSize() {
		return SIZE;
	}
	/*
	// returns True if there are no available spaces
	public boolean isFull() {
		//return (this.availableSpaces().size() == 0); ?
		return availableSpaces().size() == 0;
	}
	*/
	// returns if (x, y) is within the bounds of the grid
	public boolean inBounds(int x, int y) {
		return (x >= 0) && (x <= BOUND) && (y >= 0) && (y <= BOUND);
	}
	
	// returns if (x, y) is an open space
	private boolean emptySpace(int x, int y) {
		return grid[x][y] == EMPTY;
	}
	
	// returns and ArrayList of the available spaces
	// top left is 0, next to the right is 1, bottom right is 15
	private ArrayList<Position> availableSpaces() {
		ArrayList<Position> spaces = new ArrayList<Position>();
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				
				// add the (x, y) of all 0s to spaces
				if ( emptySpace(i, j) ) {
					spaces.add(new Position(i, j));
				}		
			}
		}
		return spaces;
	}
	
	
	// make a move by taking in a direction and collapsing all tiles
	public void makeMove(int direction) {
		int[] dir = LEFT; // vector to push tiles, default to left
		int index = 1; // index of dir that is doing the work - 1 for changing y values
		
		// set other vectors based on arrow key ascii input
		switch (direction) {
			case 1: dir = UP;
				index = 0; // vertical vector - 0 for changing x values
				break;
			case 2: dir = RIGHT;
				break;
			case 3: dir = DOWN;
				index = 0;
				break;
		}
		
		// positive or negative direction across segments - will be 1 or -1
		int scalar = dir[0] + dir[1];
		
		int starting = 1; // default for LEFT, to move right down rows while tiles are pushing left
		if (scalar > 0) starting = 2;
		
		// loop through the dimension that tiles are NOT pushed along 
		for (int segment = 0; segment <= BOUND; segment++) {

			pushTiles(dir, segment, starting, scalar, index);
		}
	}
	
	public void pushTiles(int[] dir, int segment, int starting, int scalar, int index) {
		// push all tiles to the border first without merging
		for (int place = starting; place < BOUND && place > 0; place -= scalar) {
			for (int other = starting; other >= 0 && other <= BOUND; other -= scalar) {
				// tiles are pushed along columns
				if (index == 0)  lightPush(other, segment, dir);
				
				// tiles are pushed across rows
				else lightPush(segment, other, dir);
			}
		}
		
		// merge tiles that can merge, but increment the starting place each time to
		// 	   avoid merging the same tile more than once per move
		for (int place = starting; place < BOUND && place > 0; place -= scalar) {		
			for (int other = place; other >= 0 && other <= BOUND; other -= scalar) {
				if (index == 0)  push(other, segment, dir);
				else push(segment, other, dir);
			}
		}	
	}
	
	// fill in tiles to the border but don't merge yet
	public void lightPush(int x, int y, int[] dir) {
		int newx = x + dir[0], newy = y + dir[1];
		
		// if the potential space is empty, put the tile there
		if ( emptySpace(newx, newy) ) {
			move(x, y, newx, newy);
		}
	}
	
	// try to push the tile in a direction, either move, merge, or can't move
	public boolean push(int x, int y, int[] dir) {
		int newx = x + dir[0], newy = y + dir[1];
		
		// tile can't move if it's on the border or if there's no tile there
		if (emptySpace(x, y))  return true;
		if (!inBounds(newx, newy)) return false; // false means tiles have hit the edge
		
		// if the potential space is empty, put the tile there
		if ( emptySpace(newx, newy) ) {
			move(x, y, newx, newy);
		}
		
		// if there is an equal number there, merge the tiles
		else if (grid[x][y] == grid[newx][newy]) {
			move(x, y, newx, newy);
			grid[newx][newy] *= 2;
		}
		
		// else if there is an unequal number, don't move the tile
		return true;
	}
	
	// move a tile to new coordinates
	public void move(int x, int y, int newx, int newy) {
		grid[newx][newy] = grid[x][y];
		grid[x][y] = EMPTY;
	}
	
	// inserts a new tile into a random open space
	// returns false if a new tile can't be inserted - game is over
	public boolean randomInsert() {
		ArrayList<Position> spaces = availableSpaces();
		
		// don't insert anything if board is full
		if (spaces.size() == 0) return false;
		
		// find a random position out of the available spaces
		double index = Math.random() * spaces.size();
		Position avail = spaces.get((int) Math.floor(index));
		
		int x = avail.x, y = avail.y;
		
		// 1/10th chance the new tile will be a 4
		int val = 2;
		if (Math.random() > 0.9)  val = 4;
		
		insert(val, x, y);
		return true;
	}
	
	// set any space to any value tile
	public void insert(int val, int x, int y) {
		grid[x][y] = val;
	}
	
	// static evaluation of the board's value - different from score
	public int staticEval() {
		int score = 0;
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				score += Math.pow(grid[i][j]/2, 2);
			}
		}
		return score;
	}
	
	
	class Position {
		int x, y;
		
		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}