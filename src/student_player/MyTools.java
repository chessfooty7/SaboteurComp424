package student_player;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;

public class MyTools {
	
	SaboteurCard entrance;
	SaboteurCard hidden1;
	SaboteurCard hidden2;
	SaboteurCard hidden3; 
	

    public MyTools(SaboteurBoardState boardState) {
    	entrance = boardState.getHiddenBoard()[5][5];
		hidden1 = boardState.getHiddenBoard()[12][3];
		hidden2 = boardState.getHiddenBoard()[12][5];
		hidden3 = boardState.getHiddenBoard()[12][7];
	}

	public static double getSomething() {
        return Math.random();
    }
    
    public int calculatePathDistance(SaboteurBoardState boardState, SaboteurMove move) {
    	
    	for(int row = 5; row < boardState.getHiddenBoard().length; row++) {
    		for (int col = 0; col < boardState.getHiddenBoard()[row].length; col++) {
    			if (boardState.getHiddenBoard()[row][col] == null) {
    				System.out.print("null");
    			} else {
    				System.out.print(boardState.getHiddenBoard()[row][col].getName());
    			}
    			
    		}
    		System.out.println("");
    	}
    	

    	
    	System.out.println("** " + hidden1.getName() + " " + hidden2.getName() + " " + hidden3.getName());
    	
    	return 0;
    }
}
