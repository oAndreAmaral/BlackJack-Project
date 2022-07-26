package game;

/**
* This class implements the context through which the game
* is called and set.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class GameContext {
	/**
     * The current game that can be started.
     */
	private Game game;
	
	/**
     * This method is responsible for setting the game
     * @param newGame the game itself
     */
	public void set(Game newGame) {		
		game = newGame;
	}
	
	/**
     * This method is responsible for starting the game
     * @param args main parameters set by the user when running the program
     */
	public void startGame(String[] args) {
		if (game == null) {
			System.out.println("Set game first!");
			return;
		}
		
		game.init(args, game);
	}
}
