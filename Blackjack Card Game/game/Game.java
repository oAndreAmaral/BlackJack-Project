package game;
/**
* 
* This is a class that initialise a game. it is very generic in order to be functional with other games and not only with the Blackjack.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public interface Game {
	/**
	   * This method initialise the game.
	   * @param args Input terminal arguments.
	   * @param game Input of the game to be played
	   */
	void init(String[] args, Game game);
}
