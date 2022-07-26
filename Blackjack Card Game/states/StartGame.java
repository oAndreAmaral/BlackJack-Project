package states;

import game.Blackjack;
import game.Game;
import game.GameStateContext;

/**
* This class implements the start of the game. This class is part of the game's 
* state machine.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class StartGame implements GameState{
	/**
     * This method is responsible for initialising advice's strategies and 
     * create both the player and the dealer.
     * @param stateContext context through which game states are called
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */
	public int run(GameStateContext stateContext, String[] args, Game game) {
		// code to set up the start of the game (shoe already created)
		((Blackjack)game).initStrategies();
		((Blackjack)game).createPlayer();
		((Blackjack)game).createDealer();
		
		// set the next state
		stateContext.set(new StartRound());
		return 1;
	}
}
