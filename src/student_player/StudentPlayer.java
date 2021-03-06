package student_player;

import boardgame.Move;

import Saboteur.SaboteurPlayer;
import Saboteur.cardClasses.SaboteurTile;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("xxxxxxxxx");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(SaboteurBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
    	MyTools myTools = new MyTools(boardState);
    	//myTools.displayBoard();
    	//myTools.buildPathGraph();
    	
    	
    	//myTools.getAllTileMoves();
    	SaboteurTile[] arr = myTools.getNeighbors(new int[] {5,5});
    	
    	for (SaboteurTile tile : arr) {
    		if (tile != null) {
    			System.out.println("N: " + tile.getName());
    			System.out.println("legal : " + myTools.legalPathConnectingMove(boardState.getHiddenBoard()[5][5], tile));
    		}
    		
    	}

    	
//    	SaboteurMove move = myTools.calculatePathDistanceForAllLegalTileMoves();
//    	if (move != null) {
//    		System.out.println("Best Move : " + move.toPrettyString());
//    	}
//    	
//        // Is random the best you can do?
//        Move myMove = boardState.getRandomMove();
//        
//        // Return your move to be processed by the server.
//        return move;

    	
    	ArrayList<SaboteurMove> list = boardState.getAllLegalMoves();  
    	
    	//assuming we are player 1, checks if we are blocked
//    	if(boardState.getNbMalus(1)==1) {
//    		for (SaboteurMove move : list) {
//    			if (move.getCardPlayed().getName().contains("Bonus")) {
//    				return move;
//    			}
//    			else {
//    				//drop card
//    			}
//    		}
//    	}
//    	//what to do when we don't know the exact goal state
//    	if(!myTools.isNuggetFound()) {
//    		for (SaboteurMove move : list) {
//    			if (move.getCardPlayed().getName().contains("Map")) {//how to select the position of where to play the map?
//    				return move;
//    			}
//    		}
//    	}
//    	//check if a tile is broken and if we can fix it
//    	//have to implement
//    	
//    	//play a tile move if none of the previous options have applied
//    	double distance = 200;
//    	
//    	for (SaboteurMove move : list) {
//    		double temp = myTools.calculatePathDistance(move);
//    		SaboteurMove temp2 = new SaboteurMove(move.getCardPlayed(), 0, 0, 0);//needs editing
//    		if(temp < distance) {
//    			distance = temp;
//    			temp2 = new SaboteurMove(move.getCardPlayed(), 0, 0, 0);
//    		}
//    		return temp2;
//		}
    	//
    	
//    	int [][] grid = new int[][] {
//            {1, 2, 3, 4},
//            {5, 6, 7, 8},
//            {9, 10, 11, 12},
//            {13, 14, 15, 16}
//	    };
//	    myTools.DFS(boardState.getHiddenBoard()); 
    	
	    SaboteurMove move = myTools.calculatePathDistanceForAllLegalTileMoves();
	    System.out.println("M; " + move);
	    System.out.println("MOVE : " + move.toPrettyString());
    	return move;
    
    }
}