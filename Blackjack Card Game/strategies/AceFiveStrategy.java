package strategies;

import game.Dealer;
import game.Hand;
import game.Player;
/**
* 
* This class is an implementation of the Strategy Action that performs
* the AceFiveStrategy and returns a betting action based on the conditions.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class AceFiveStrategy implements StrategyBet {
	
	/**
	 * An integer that defines the ace five counter - Aces count -1 and 5's count +1.
	 */
	private int aceFiveCounter;
	/**
	 * The counter of 5s in the game.
	 */
	private int count5s;
	/**
	 * The counter of Aces in the game.
	 */
	private int countAs;

	
	/**
	   * This method the constructor that initializes the count of 5s and As for the strategy
	   */
	public AceFiveStrategy() {
		super();
		this.aceFiveCounter = 0;
		this.count5s = 0;
		this.countAs = 0;
	}	

	@Override
	/**
	   * This method is the main of the strategy that comes from the Class StrategyBet by using a Strategy Pattern. It implements
	   * the Ace-Five strategy, which is based on counting he number of Aces and Fives, where the Fives value 1 and Aces are valued -1.
	   * If this count is bigger or equal than 2 then double the previous bet, otherwise apply the minimum bet. 
	   * @param min_bet Minimum Possible bet defined by the User.
	   * @param current_bet Defines the current bet of the player.
	   * @param max_bet Maximum Possible bet defined by the User.
	   * @param lastWinBalance Defines the last result.
	   * @param player Class player in the program.
	   * @param dealer Class dealer in the program.
	   * @return int Bet to make.
	*/
	public int getStrategyBet(int min_bet, int current_bet, int max_bet, int lastWinBalance, Player player, Dealer dealer) {
		if(aceFiveCounter >= 2) {
			if(2*current_bet > max_bet) {
				return max_bet;
			} else {
				return 2*current_bet;
			}
		} else {
			return min_bet;
		}	
	}
	
	/**
	   * Counter of the AceCount, which count the number of Fives which are valued by 1 and Aces which are valued by -1.
	   * @param player Player of the game.
	   * @param dealer  Dealer of the game.
	*/
	public void aceFiveCount(Player player, Dealer dealer) {
		// Gets number of 5s and Aces from all player hands
		for (int i = 0; i < player.getHands().size(); i++) {
			Hand test_hand =  player.getHands().get(i);
			count5s += test_hand.countFives();
			countAs += test_hand.countAces();
		}
		// Get nº of 5s and As when all cards are revealed or when only the first is revealed
		if (dealer.isReveal()) {
			count5s += dealer.getHand(0).countFives();
			countAs += dealer.getHand(0).countAces();		
		} else {
			count5s += (dealer.getHand(0).getCards().get(0).getRanksString().equals("5")) ? 1 : 0;
			countAs += (dealer.getHand(0).getCards().get(0).getRanksString().equals("A")) ? 1 : 0;		
		}
		
		aceFiveCounter = count5s*1 + countAs*(-1);
	}
	
	
	/**
	   * Reset the AceFice Counter.
	*/
	public void resetCounter() {
		aceFiveCounter = 0;
	}
}

