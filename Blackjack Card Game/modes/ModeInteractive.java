package modes;

import game.Blackjack;
import game.Shoe;
import game.UserInput;

/**
* 
* This class implements the Interactive Mode, and it is an sub class of the super class "Mode".
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class ModeInteractive extends Mode{

	/**
	   * This method obtains the shoe for the interactive mode
	   * @param game This parameter takes into account the type of game chosen
	   * @return shoe This returns the shuffled shoe
	   */
	@Override
	public Shoe getShoe(Blackjack game) {
		Shoe shoe = new Shoe(game.getDecks());
		shoe.generateShoe();
		shoe.shuffle();
		System.out.println("\nshuffling the shoe...");
		return shoe;
	}

	/**
	   * This method is responsible to execute the commands and it will be important in the state pattern to implement the user actions
	   * @param flag This input is used to divide the game phases (it is just a simple flag)
	   * @param game This input takes into account the type of game chosen
	   * @param hand This input is useful to the implementation due to the importance of knowing the player and dealer hands
	   * @return input.readUserInput() This returns the input of the user
	   */
	@Override
	public String getCommand(int flag, Blackjack game, int hand) {
		UserInput input = new UserInput();
		return input.readUserInput();
	}
}