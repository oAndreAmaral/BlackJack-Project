package application;

import game.GameContext;
import game.Game;
import game.Blackjack;
/**
* 
* This class represents the starting point of the program
* as it initialises a game whenever the program is activated.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class App {
	/**
	 * This class initializes the program by creating an object of class game
	 * @param args main parameters set by the user when running the program
	 */
	public static void main(String[] args) {
		/**
		 * Defines a new application.
		 */
		GameContext app = new GameContext();
		/**
		 * Defines a new game.
		 */
		Game game = new Blackjack(args);
		
		app.set(game);
		app.startGame(args);
	}
}