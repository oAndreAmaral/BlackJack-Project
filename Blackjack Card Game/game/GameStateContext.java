package game;

import states.GameState;

/**
* This class implements the context through which the game's 
* states are called and set.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class GameStateContext {
	/**
     * The current state of the game.
     */
	private GameState state;
	
	/**
     * This method is responsible for setting the game's current state.
     * @param gameState the game state to be set
     */	
	public void set(GameState gameState) {
		state = gameState;
	}
	
	/**
     * This method is responsible for running the current state
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */	
	public int runState(String[] args, Game game) {
		return state.run(this, args, game);
	}
}