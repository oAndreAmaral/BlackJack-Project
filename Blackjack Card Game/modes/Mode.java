package modes;

import game.Blackjack;
import game.Shoe;

/**
* This abstract class defines mode dependent methods.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public abstract class Mode {
	/**
     * This method is responsible for getting the shoe to be used
     * in the game.
     * @param game the game itself, where all high-level methods are
     * @return returns the game's shoe
     */	
	public abstract Shoe getShoe(Blackjack game);
	
	/**
     * This method is responsible for getting the next command to be
     * interpreted by the StartRound or PlayerTurn states.
     * @param flag in simulation mode, it is a flag to switch between 
     * bet (1), deal (2) and player action (3) situation
     * @param game the game itself, where all high-level methods are
     * @param hand index of the hand in the array list of hands of the player
     * @return returns a command
     */	
	public abstract String getCommand(int flag, Blackjack game, int hand);
}