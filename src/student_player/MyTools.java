package student_player;

import java.util.ArrayList;
import java.util.Stack;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurTile;


public class MyTools {
	
	SaboteurCard entrance;
	SaboteurCard hidden1;
	SaboteurCard hidden2;
	SaboteurCard hidden3; 
	SaboteurBoardState boardState;
	

    public MyTools(SaboteurBoardState sboardState) {
    	entrance = sboardState.getHiddenBoard()[5][5];
		hidden1 = sboardState.getHiddenBoard()[12][3];
		hidden2 = sboardState.getHiddenBoard()[12][5];
		hidden3 = sboardState.getHiddenBoard()[12][7];
		boardState = sboardState;
	}
    

	public static double getSomething() {
        return Math.random();
    }
	
	public void displayBoard() {
		for(int row = 0; row < boardState.getHiddenBoard().length; row++) {
    		for (int col = 0; col < boardState.getHiddenBoard()[row].length; col++) {
    			if (boardState.getHiddenBoard()[row][col] == null) {
    				System.out.print("null");
    			} else {
    				System.out.print(boardState.getHiddenBoard()[row][col].getName());
    			}
    			
    		}
    		System.out.println("");
    	}
		System.out.println("hidden tiles (1,2,3): " + hidden1.getName() + " | " + hidden2.getName() + " | " + hidden3.getName());
	}
	
	public ArrayList<SaboteurMove> getAllTileMoves() {
		ArrayList<SaboteurMove> tileMoves = new ArrayList<>();
		
		ArrayList<SaboteurMove> allLegalMoves = boardState.getAllLegalMoves();
		
		for (SaboteurMove move : allLegalMoves ) {
		
//			System.out.println(move.getCardPlayed().getName().contains("Tile"));
//			System.out.println(move.getPlayerID());
//			System.out.println(move.getPosPlayed()[0] + " , " + move.getPosPlayed()[1]);
			
			if (move.getCardPlayed().getName().contains("Tile")) {
				tileMoves.add(move);
				//System.out.println(move.toPrettyString());
			}
		}
		
		
		return tileMoves;
	}
	
	public ArrayList<SaboteurMove> getAllDropMoves() {
		ArrayList<SaboteurMove> dropMoves = new ArrayList<>();
		
		ArrayList<SaboteurMove> allLegalMoves = boardState.getAllLegalMoves();
		
		for (SaboteurMove move : allLegalMoves ) {
		
//			System.out.println(move.getCardPlayed().getName().contains("Tile"));
//			System.out.println(move.getPlayerID());
//			System.out.println(move.getPosPlayed()[0] + " , " + move.getPosPlayed()[1]);
			
			if (move.getCardPlayed().getName().contains("Drop")) {
				dropMoves.add(move);
				//System.out.println(move.toPrettyString());
			}
		}
		
		
		return dropMoves;
	}
	
	public ArrayList<SaboteurMove> getAllMapMoves() {
		ArrayList<SaboteurMove> dropMoves = new ArrayList<>();
		
		ArrayList<SaboteurMove> allLegalMoves = boardState.getAllLegalMoves();
		
		for (SaboteurMove move : allLegalMoves ) {
		
//			System.out.println(move.getCardPlayed().getName().contains("Tile"));
//			System.out.println(move.getPlayerID());
//			System.out.println(move.getPosPlayed()[0] + " , " + move.getPosPlayed()[1]);
			
			if (move.getCardPlayed().getName().contains("Map")) {
				dropMoves.add(move);
				//System.out.println(move.toPrettyString());
			}
		}
		
		
		return dropMoves;
	}
    
    public double calculatePathDistance(SaboteurMove move) {
    	
    	// if null calculate distance from entrance to center hidden tile (hidden2)
    	if (move == null) {
    		
    		int xDistance = 5 - 5;
    		int yDistance = 12 - 5;
    		
    		return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    	} else {
    		if (isNuggetFound()) {
    			
    			int xDistance = Math.abs(move.getPosPlayed()[0] - getNuggetLocation()[0]);
    			int yDistance = Math.abs(move.getPosPlayed()[1] - getNuggetLocation()[1]);
    			return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    		} else {
    			// Calculate distance to center hidden tile (hidden2)
    			int xDistance = Math.abs(move.getPosPlayed()[0] - 12);
    			int yDistance = Math.abs(move.getPosPlayed()[1] - 5);
    			return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    		}
    	}
    }
    
    public SaboteurMove calculatePathDistanceForAllLegalTileMoves() {
    	
    	SaboteurMove bestMove = null;
    	double bestDistance = Double.POSITIVE_INFINITY;
    	
    	SaboteurMove worstMove = null;
    	double worstDistance = Double.NEGATIVE_INFINITY;
    	
    	if (!isNuggetFound() && getAllMapMoves().size() > 0) {
    		return getAllMapMoves().get(0);
    	}
    	
    	for(SaboteurMove move :  getAllTileMoves()) {
    		double moveDistance = calculatePathDistance(move);
    		System.out.println("Distance : " + moveDistance );
    		SaboteurTile tile = (SaboteurTile) move.getCardPlayed();
    		
    		if (moveDistance < bestDistance && !isWasteMaan(tile) ) {
    			bestDistance = moveDistance;
    			bestMove = move;
    		}
    		else {
    			worstDistance = moveDistance;
    			worstMove = move;
    		}
    	}
    	
    	if (bestMove == null) {
    		bestMove = getBestCardToDrop();
    	}
    	
    	if (bestMove == null) {
    		bestMove = boardState.getAllLegalMoves().get(0);
    	}
    	
    	return bestMove;
    }
    
    public SaboteurMove getBestCardToDrop() {
    	// fix this for getalldrop moves

    	for (SaboteurMove move : getAllDropMoves()) {
    		SaboteurCard card = move.getCardPlayed();
    		System.out.print(card.getName() + " ");
    		if (card.getName().contains("Bonus")) {
    			return move;
    		} else if (card.getName().contains("Destroy")) {
    			return move;
    		} else if (card.getName().contains("Malus")) {
    			return move;
    		}
    	}
    	
    	return null;
    }
    
    /*
     * Returns the location of nugget as an array with two values[row,col]
     * */
    public int[] getNuggetLocation() {
    	
    	if (isNuggetFound()) {
    		if (hidden1.getName() == "Tile:nugget") {
    			return new int[] {12,3};
    		} else if (hidden2.getName() == "Tile:nugget") {
    			return new int[] {12,5};
    		} else if (hidden3.getName() == "Tile:nugget") {
    			return new int[] {12,7};
    		}
    	}
    	
    	return null;
    }
    
    /*
     * Returns if the nugget is found, true or false
     * */
    public boolean isNuggetFound() {
    	if (hidden1.getName() == "Tile:nugget" || hidden2.getName() == "Tile:nugget" || hidden3.getName() == "Tile:nugget") {
    		return true;
    	}
    	
    	return false;
    }
    
    // implement depth first search
    public ArrayList<SaboteurTile> getBestPath(int[] coord) {
    	ArrayList<SaboteurTile> path = new ArrayList<>();
    	
    	SaboteurTile[] neighbors = getNeighbors(coord);
    	
    	while(neighbors.length > 0) {
    		
    	}
    	
    	return path;
    }
    
    // dfs
    public void DFS(SaboteurTile[][] grid) {
    	
    	int h = grid.length;
    	if (h == 0)
    		return;
    	
    	int l = grid[0].length;
    	
    	boolean[][] visited = new boolean[h][l];
    	
    	Stack<String> stack = new Stack<>();
    	
    	stack.push(5 + "," + 5);
    	
    	System.out.println("DEPTH-FIRST TRAVERSAL");
    	
    	while (stack.empty() == false) {
    		String x = stack.pop();
    		int row = Integer.parseInt(x.split(",")[0]);
    		int col = Integer.parseInt(x.split(",")[1]);
    		
    		if (row < 0 || col < 0 || row >= h || col >= l || visited[row][col] || grid[row][col] == null)
    			continue;
    		
    		visited[row][col] = true;
    		System.out.print("(" + grid[row][col].getName() + ") ");
    		stack.push(row+ "," + (col-1)); // go left
    		stack.push(row+ "," + (col+1)); // go right
    		stack.push((row-1)+ "," + col); // go up
    		stack.push((row+1)+ "," + col); // go down
    	}
    	
    }
    
    /*
     * Given a coordinate find neighbors
     * Return 4 neighbors, null if no neighbor
     * */
    
    public SaboteurTile[] getNeighbors(int[] coord) {
    	SaboteurTile[] neighbors = new SaboteurTile[4];
    	int row = coord[0];
    	int col = coord[1];
    	
    	int i = 0;
    	int[][] neighborCoords = new int[][]{{row-1, col}, {row+1, col}, {row, col+1}, {row, col-1}};
    	for (int[] coords : neighborCoords) {
    		System.out.println("NULL? : " + boardState.getHiddenBoard()[coords[0]][coords[1]]);

			SaboteurTile tile = boardState.getHiddenBoard()[coords[0]][coords[1]];
			
			neighbors[i] = tile;

    		i++;
    	}
    	
    	
    	return neighbors;
    }
    
    /*
     * Given two coordinate return if path can continue from a to b
     * */
    
    public boolean legalPathConnectingMove(SaboteurTile center, SaboteurTile neighbor) {
    	
    	int[][] centerPath = center.getPath(); // path = {{botl,midl,topl},{mid,mid,mid},{botr,midr,topr}}
    	int[][] neighborPath = neighbor.getPath();
    	
    	// n on top of c
    	int c = centerPath[1][2];
    	int n = neighborPath[1][0];
    	
    	if (c == 1 && n == 1) {
    		return true; 
    	}
    	
    	// n on bot of c
    	c = centerPath[1][0];
    	n = neighborPath[1][2];
    	
    	if (c == 1 && n == 1) {
    		return true; 
    	}
    	
    	// n on left of c
    	c = centerPath[0][1];
    	n = neighborPath[2][1];
    	
    	if (c == 1 && n == 1) {
    		return true; 
    	}
    	
    	// n on right of c
    	c = centerPath[2][1];
    	n = neighborPath[0][1];
    	
    	if (c == 1 && n == 1) {
    		return true; 
    	}
    	
    	
    	return false;
    }
    
    /*
     * Return if path ending tile move
     * */
    
    public boolean isWasteMaan(SaboteurTile tile) {
    	
    	int[][] path = tile.getPath();
    	int center = path[1][1];
    	int left = path[0][1];
    	int right = path[2][1];
    	int top = path[1][2];
    	int bottom = path[1][0];
    	
    	if (center == 0) {
    		return true;
    	}
    	
    	int[] temp = new int[] {left,right, top,bottom};
    	int count = 4;
    	for (int i = 0; i < 4; i++) {
    		if (temp[i] == 1) {
    			count--;
    		}
    	}
    	for (int t : temp) {
    		
    	}
    	
    	if (count > 2) {
    		return true;
    	}
    	
    	
    	return false;
    }
    
}











