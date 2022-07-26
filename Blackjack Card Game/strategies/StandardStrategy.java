package strategies;

import game.Dealer;

import game.Player;

/**
* 
* This class is an implementation of the strategy Bet and represents the standard betting Strategy which is based on the past round. If the player lost, the best solution is to
* bet the minimum bet, if the player won, then double the last bet until a maximum bet. Otherwise, return the current bet plus a min_bet.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class StandardStrategy implements StrategyBet {
	/**
	   * This method represents the Standard Strategy and returns a bet based on it. In short, If the player lost, the best solution is to
	   * bet the minimum bet, if the player won, then double the last bet until a maximum bet. Otherwise, return the current bet plus a min_bet.
	   * @param min_bet Minimum Possible bet defined by the User.
	   * @param current_bet Defines the current bet of the player.
	   * @param max_bet Maximum Possible bet defined by the User.
	   * @param lastWinBalance Defines the last result.
	   * @param player Class player in the program.
	   * @param dealer Class dealer in the program.
	   * @return int Bet to make.
	*/
	public int getStrategyBet(int min_bet, int current_bet, int max_bet, int lastWinBalance, Player player, Dealer dealer) {
		/**
		 * A variable that defines the new bet.
		 */
		int newBet = current_bet + lastWinBalance*min_bet;
		
		if (newBet > max_bet) {
			return max_bet;
		} else if (newBet < min_bet) {
			return min_bet;
		} else {
			return newBet;
		}

	}
}	

