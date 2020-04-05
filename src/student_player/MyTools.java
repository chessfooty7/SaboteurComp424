package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;

class PathNode {
	
	PathNode UP;
	PathNode DOWN;
	PathNode LEFT;
	PathNode RIGHT;
	int x;
	int y;
	
	PathNode(int xVal, int yVal) {
		UP = null;
		DOWN = null;
		LEFT = null;
		RIGHT = null;
		x = xVal;
		y = yVal;
	}
}

class PathGraph {
	
	PathNode path;
	
	PathGraph(PathNode entrance) {
		path = entrance;
	}
}

public class MyTools {
	
	SaboteurCard entrance;
	SaboteurCard hidden1;
	SaboteurCard hidden2;
	SaboteurCard hidden3; 
	SaboteurBoardState boardState;
	PathGraph pGraph;
	

    public MyTools(SaboteurBoardState sboardState) {
    	entrance = sboardState.getHiddenBoard()[5][5];
		hidden1 = sboardState.getHiddenBoard()[12][3];
		hidden2 = sboardState.getHiddenBoard()[12][5];
		hidden3 = sboardState.getHiddenBoard()[12][7];
		boardState = sboardState;
		
		PathNode pNode = new PathNode(5,5);
		pGraph = new PathGraph(pNode);
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
    	for(SaboteurMove move :  getAllTileMoves()) {
    		double moveDistance = calculatePathDistance(move);
    		System.out.println("Distance : " + moveDistance );
    		if (moveDistance < bestDistance) {
    			bestDistance = moveDistance;
    			bestMove = move;
    		}
    	}
    	
    	return bestMove;
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
    
    public void buildPathGraph() {
    	//TODO
    	// Figure out how to calculate valid path?
    	
    	for(int row = 0; row < boardState.getHiddenBoard().length; row += 3) {
    		for (int col = 0; col < boardState.getHiddenBoard()[row].length; col += 3) {
    			int topLeft = boardState.getHiddenIntBoard()[row][col];
    			int topMid = boardState.getHiddenIntBoard()[row][col+1];
    			int topRight = boardState.getHiddenIntBoard()[row][col+2];
    			int midLeft = boardState.getHiddenIntBoard()[row+1][col];
    			int midMid = boardState.getHiddenIntBoard()[row+1][col+1];
    			int midRight = boardState.getHiddenIntBoard()[row+1][col+2];
    			int botLeft = boardState.getHiddenIntBoard()[row+2][col];
    			int botMid = boardState.getHiddenIntBoard()[row+2][col+1];
    			int botRight = boardState.getHiddenIntBoard()[row+2][col+2];
    			System.out.print(topLeft + " " + topMid + " " + topRight + " | ");
    			System.out.print(midLeft + " " + midMid + " " + midRight + " | ");
    			System.out.print(botLeft + " " + botMid + " " + botRight + " | ");
    			System.out.println(" ");
    		}
    		System.out.println(" ");
    	}
    }
}











