package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;

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
				System.out.println(move.toPrettyString());
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
    			int xDistance = Math.abs(12 - getNuggetLocation()[0]);
    			int yDistance = Math.abs(5 - getNuggetLocation()[1]);
    			return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    		}
    	}
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
}










