package strategies;

import game.Hand;
import game.Blackjack;
import game.Game;

/**
* 
* This class implements the counting cards strategy, more properly the HiLoStrategy. This class implements the method of the interface "StrategyAction".
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class HiLoStrategy implements StrategyAction {

	/**
     * field to store the running count
     */
	private int runningCount = 0;
	/**
     * field to store the true count
     */
	private int trueCount = 0;
	/**
     * field to store the dealer face-up card value
     */
	private int dealerValue;
	/**
     * field to store the number of decks left
     */
	private float decksLeft;
	/**
     * field to create a field of the class Game
     */
	private Game game;
	/**
     * field to import the basic strategy to be used.
     */
	private StrategyAction useBasicStrategy = new BasicStrategy();

	/**
	   * This method initialise the game attribute.
	   * @param game To initialise the attribute game
	   */
	public HiLoStrategy(Game game) {
		super();
		this.game = game;
	}
	
	/**
	   * This method executes the HiLoStrategy
	   * @param player To get inside the method the player hand value
	   * @param dealer_val To get inside the method the dealer face-up card value
	   * @param handsSize To get inside the method the hand size
	   * @return This returns the action according to the type of hand
	   */
	@Override
	public String executeActionStrategy(Hand player, int dealer_val, int handsSize) {
		trueCount = getTrueCount();
		dealerValue = dealer_val;
		
		// Special Case TTvs5||4
		if (player.checkSplit(player.getCards())) {
			if(player.getCards().get(0).cardValue() == 10 && player.getCards().get(1).cardValue() == 10) {
				if (dealerValue == 5 && trueCount >= 5) {
					return handsSize < 4 ? "split" : "stand";
				}
				else if (dealerValue == 6 && trueCount >= 4) {
					return handsSize < 4 ? "split" : "stand";
				}
				return "stand";
			}			
		}
		
		// Special Case Insurance (If dealer face up card is an A (its card value is an 11))
		if(dealerValue == 11 && trueCount >= 3) {
			return "insurance";
		}
				
		switch(player.getHandValue()) {
		case 16:
			if (dealerValue == 10) {
				if (trueCount >= 0) {
					return "stand";	
				} else {
					return "hit";
				}
			} else if (dealerValue == 9) {
				if (trueCount >= 5) {
					return "stand";
				} else {
					return "hit";
				}
			}
			break;
		case 15:
			if(dealerValue == 10) {
				if(trueCount>=4) {
					return "stand";
				}else if(trueCount <=3 && trueCount >= 0) {
					return "surrender";	
				} else return "hit";
			}
			
			if(dealerValue == 9) {
				if(trueCount >= 2) {
					return "surrender";			
				}else {
					return useBasicStrategy.executeActionStrategy(player, dealer_val, handsSize) ;
				}
			}
			
			if(dealerValue == 11) {
				if(trueCount >= 1) {
					return "surrender";			
				} else {
					return useBasicStrategy.executeActionStrategy(player, dealer_val, handsSize) ;
				}
			}
			break;
			
		case 14:
			if(dealerValue == 10) {
				if(trueCount >= 3) {
					return "surrender";
				}else{
					return useBasicStrategy.executeActionStrategy(player, dealer_val, handsSize) ;
				}
			}
			break;
			
		case 13:
			if(dealerValue == 2) {
				if(trueCount >= -1) {
					return "stand";
				} else return "hit";
			}
			if(dealerValue == 3 ) {
				if(trueCount >= -2) {
					return "stand";			
				}else return "hit";
			}
			break;
			
		case 12:
			if(dealerValue == 3) {
				if(dealerValue == 3 && trueCount >= 2) {
					return "stand";
				}else return "hit";
			}	
			if(dealerValue ==2) {
				if(trueCount >= 3) {
					return "stand";
				} else return "hit";
			}
			if(dealerValue == 4) {
				if(trueCount >= 0) {
				return "stand";
				}else return "hit";
			}
			if(dealerValue == 5) {
				if(trueCount >= -2) {
					return "stand";
				}else return "hit";
			}
			
			if(dealerValue == 6) {
				if(trueCount >= -1) {
					return "stand";
				}else return "hit";
			}				
			break;
			
		case 11:
			if(dealerValue == 11) {
				if(trueCount >= 1) {
					return "double";
				}else return "hit";
			}
			break;
			
		case 10:
			if(dealerValue == 10) {
				if(trueCount >= 4) {
					return "double";
				}else return "hit";
			}
			if(dealerValue == 11) {
				if(trueCount >= 4){
					return "double";				
				}else return "hit";
			}
			break;
			
		case 9:
			if(dealerValue == 2) {
				if(trueCount >= 1) {
					return "double";
				}else return "hit";
			}
			if(dealerValue == 7) {
				if(trueCount >= 3){
					return "double";				
				}else return "hit";
			}
			break;				
		}
		return useBasicStrategy.executeActionStrategy(player, dealer_val, handsSize) ;
	}
	
	/**
	   * This method updates the running count
	   * @param cardValue This is the card value used to update running count
	   */
	public void updateRunningCount(int cardValue) {
		if (cardValue >= 2 && cardValue <= 6) {
			runningCount ++;
		} else if(cardValue >= 10 || cardValue == 1) {
			runningCount --;
		}
	}
	
	/**
	   * This method gets the true count value
	   * @return trueCount This returns the true count according to the count of cards
	   */
	public int getTrueCount() {	
		decksLeft = 52.0f * ((Blackjack)game).getDecksLeft() - ((Blackjack)game).getCardsPlayed() / 52.0f;
		trueCount = Math.round(runningCount/decksLeft);
	
		return trueCount;
	}
	
	/**
	   * This method resets the running count
	   */
	public void resetCounter() {
		runningCount = 0;
	}
}
