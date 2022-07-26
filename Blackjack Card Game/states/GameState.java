package states;

import game.Game;
import game.GameStateContext;

/**
* This interface defines the execution of any game's state. This 
* interface's implementations define the game's state machine.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public interface GameState {
	/**
     * This method is responsible for running the game's state.
     * @param stateContext context through which game states are called
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */	
	int run(GameStateContext stateContext, String[] args, Game game);
}
