package states;

import game.Blackjack;
import game.Game;
import game.GameStateContext;

/**
* This class implements the end of the game. This class is part of the game's 
* state machine.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class EndGame implements GameState{
	/**
     * This method is responsible for printing game statistics if needed
     * and leaving the state machine.
     * @param stateContext context through which game states are called
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */
	public int run(GameStateContext stateContext, String[] args, Game game) {	
		((Blackjack)game).printStats();
		
		return 0;
	}
}
