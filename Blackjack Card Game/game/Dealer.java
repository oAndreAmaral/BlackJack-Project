package game;

/**
* 
* This class defines the Dealer of the game which is an extention of the class Participant. In the class
* dealer, one can find the dealer action, save important attitudes of related to the dealer and other relevant methods. 
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class Dealer extends Participant {
	
	//* Fields *// ----------------------------------------------------------
	
	/**
	 * A boolean that defines if the dealer hits 17.
	 */
	private boolean DealerHits17;
	/**
	 * A boolean that defines if the dealer revealed or not the cards.
	 */
	private boolean reveal;
	
	//* Constructor *// ----------------------------------------------------------
	/**
	   * Dealer constructor that initializes the variables.
	*/
	public Dealer() {
		super();
		DealerHits17 = false;
		reveal = false;
	}
	
	//* Methods *// ----------------------------------------------------------
	/**
	   * Method that adds a Hand to the dealer in the start of the game, generally with bet=0 
	   * @param bet Bet for the hand.
	   * @return boolean.
	*/
	public Boolean addHand(int bet) {
		/**
		 * A new hand for the dealer, when he does not have one.
		 */
		Hand new_hand = new Hand(bet);
		hands.add(new_hand);
		
		return true;
	}
	
	/**
	   * Method that Checks Soft17 and changes value dealerHits17 to stop him from hitting again
	   * @return boolean.
	*/
	public boolean Soft17() {
		if (hands.get(0).CheckSoft() && hands.get(0).getHandValue() == 17) {	
			DealerHits17 = true;			
			return true;
		} else {
			return false;
		}
	}	
	
	// Setters and Getters
	/**
	   * Method that returns if the variable that defines if the dealer has a hand valued at 17
	   * @return true if dealer hit 17
	*/
	public boolean isDealerHits17() {
		return DealerHits17;
	}
	
	/**
	   * Method sets the variable that defines if the dealer has a hand valued at 17
	   * @param dealerHits17 true if dealer hit 17
	*/
	public void setDealerHits17(boolean dealerHits17) {
		DealerHits17 = dealerHits17;
	}
	
	/**
	   * Method returns a boolean if the the dealer cards are revealed or not
	   * @return true if dealer's hole card is revealed
	*/
	public boolean isReveal() {
		return reveal;
	}
	
	/**
	   * Method that sets the reveal attribute
	   * @param reveal true if dealer's hole card is revealed
	*/
	public void setReveal(boolean reveal) {
		this.reveal = reveal;
	}
	/**
	   * Method that returns the dealer action based on the standard dealer procedure. If the card is less than 17-> Hit otherwise stand if not busted
	   * @param dealer_val Dealer Hand value
	   * @return  dealer action.
	*/
	public String executeAction( int dealer_val) {
		if(dealer_val < 17) {
			return "h"; // hit
		} else if (dealer_val >= 17 && dealer_val <= 21){
			return "s"; // stand
		} else {
			return "b";
		}
		
	}

	@Override
	public String toString() {
		return "Dealer [DealerHits17=" + DealerHits17 + ", reveal=" + reveal + "]";
	}
	/**
	   * Method prints the Dealers hand which is revealed in a specific way
	*/
	public void printHand() {
		String str = "dealer's hand ";
		if (!reveal) {
			str = str.concat(getHand(0).toStringHidden());
		} else {
			str = str.concat(getHand(0).toString());
		}
		
		System.out.println(str);
	}
	
	

}
