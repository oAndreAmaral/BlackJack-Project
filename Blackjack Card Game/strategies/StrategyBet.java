package strategies;

import game.Dealer;
import game.Player;

/**
* 
* Based on a Strategy Pattern Model we define a StrategyBet method that will be used both for the AceFiveStrategy and the Standard Strategy. 
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24h
*/
public interface StrategyBet{
	/**
	   * This method defines the StrategyBet to return a possible bet.
	   * @param min_bet Minimum Possible bet defined by the User.
	   * @param current_bet Defines the current bet of the player.
	   * @param max_bet Maximum Possible bet defined by the User.
	   * @param lastWinBalance Defines the last result.
	   * @param player Class player in the program.
	   * @param dealer Class dealer in the program.
	   * @return int amount to be bet
	*/
	public int getStrategyBet(int min_bet, int current_bet, int max_bet, int lastWinBalance, Player player, Dealer dealer);
}
