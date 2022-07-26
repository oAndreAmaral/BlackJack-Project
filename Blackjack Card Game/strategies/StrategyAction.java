package strategies;

import game.Hand;

/**
* 
* This Interface which contains the strategy methods to be universal and implements the Strategy Pattern in order to turn the code extensible to future Strategy.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public interface StrategyAction {
	
	/**
	 * This method is the main method for the strategies, once we implement a strategy this method will be called
	 * @param player To get inside the method the player hand value
	 * @param dealer_val To get inside the method the dealer face-up card value
	 * @param handsSize To get inside the method the hand size
	 * @return String action to be performed
	 */
	public String executeActionStrategy(Hand player, int dealer_val, int handsSize);

}
