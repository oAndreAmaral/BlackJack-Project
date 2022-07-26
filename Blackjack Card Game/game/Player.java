package game;

/**
* 
* This class defines the player, including it's current hands, bet, balance and all the related actions such
* has the split, DoubleDown, Insurance and surrender.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class Player extends Participant{
	/**
	 * The hand limit.
	 */
	private int handLimit;
	/**
	 * The balance of the Player.
	 */
	private float balance;
	/**
	 * The insurance bet.
	 */
	private int insurance;
	/**
	 * The bet of the player.
	 */
	private int bet;
	/**
	 * The previous bet of the player.
	 */
	private int prevBet;
	/**
	 * The action taken on the last play.
	 */
	private String lastPlay;
	
	/* Constructor */	
	/**
	   * Method is the Constructor of the class Player and is used to initialize or create a player for the game. 
	   * @param balance Initial balance of the Player.
	*/
	public Player(float balance) {
		super();
		this.handLimit = 4;
		this.balance = balance;
		this.insurance = 0;
		this.lastPlay = "None";
	}
	
	/**
	   * Method that returns the bet of the Player 
	   * @return int Bet
	*/
	public int getBet() {
		return bet;
	}

	/**
	   * Method that prints the bet of the Player  
	   * @param bet current bet
	*/
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	/**
	   * Method gets previous bet  
	   * @return int previous bet
	*/
	public int getPrevBet() {
		return prevBet;
	}
	
	/**
	   * Method prints previous bet  
	   * @param prevBet previous bet
	*/
	public void setPrevBet(int prevBet) {
		this.prevBet = prevBet;
	}
	
	/**
     * Method prints previous bet
     * @param hand index of the hands of the participant
     * @return boolean if the hand is surrendered or not
     */
	public boolean isSurrendered(int hand) {
		return getHand(hand).isSurrendered();
	}
	
	/**
     * Method prints previous bet
     * @param hand index of the hands of the participant
     * @param surrendered true if the hand is surrendered
     */
	public void setSurrendered(int hand, boolean surrendered) {
		getHand(hand).setSurrendered(surrendered);
	}
	/**
	   * Method that returns the last play 
	   * @return String Represents an action
	*/
	public String getLastPlay() {
		return lastPlay;
	}
	/**
	   * Method that returns the last play
	   * @param lastPlay represents an action
	*/
	public void setLastPlay(String lastPlay) {
		this.lastPlay = lastPlay;
	}
	/**
	   * Method that returns the balance
	   * @return float balance
	*/
	public float getBalance() {
		return balance;
	}
	
	/**
	   * Method that adds a bet and removes from the balance 
	   * @param bet Current bet
	*/
	public void bet(int bet) {
		balance -= bet;
		prevBet = bet;
	}
	/**
	   * Method gives the player the spoils when he wins.
	   * @param hand Hand index
	*/
	public void win(int hand) {
		balance += 2*getHand(hand).getBet();
	}
	/**
	   * Method gives the player the spoils when he winsBJ.
	   * @param hand Hand index
	*/
	public void winBJ(int hand) {
		balance += 2.5*getHand(hand).getBet();
	}
	/**
	   * Method gives the player the bet when he pushes.
	   * @param hand Hand index
	*/
	public void push(int hand) {
		balance += getHand(hand).getBet();
	}
	
	
	/**
	   * Method that adds a hand to the player.
	   * @param bet current bet.
	   * @return Boolean When the hand is succesfull.
	*/
	public Boolean addHand(int bet) {
		if (hands.size() == handLimit) return false;
		
		Hand new_hand = new Hand(bet);
		hands.add(new_hand);
		
		return true;
	}
	
	/**
	   * Method that creates a new hand after split with same bet and one of the cards of the original hand.
	   * @param bet current bet.
	   * @param card Card to add.
	   * @param idx index of the hand.
	   * @return Boolean When the hand is succesfull.
	*/
	protected Boolean addHand(int bet, Card card, int idx) {
		if (hands.size() == handLimit) return false;
		
		Hand new_hand = new Hand(bet);
		new_hand.addCard(card);
		hands.add(idx+1, new_hand);
		
		return true;
	}
	/**
	   * Method that returns the hand size.
	   * @return int Hand Size.
	*/
	public int handsSize() {
		return hands.size();
	}
	
	/**
	   * Method that processes the bet.
	   * @param bet current bet .
	   * @param hand index of the hand.
	   * @return Boolean When the bet is placed correctly 
	*/
	public Boolean bet(int hand, int bet) {
		getHand(hand).setBet(bet);
		balance += getHand(hand).getBet();
		balance -= bet;
		prevBet = bet;
		return true;
	}
	
	/**
	   * Method that returns if it's possible or not to do doubleDown.
	   * @param hand Represents the hand being analysed.
	   * @return Boolean When the hand is succesfull.
	*/
	public Boolean cannotDoubleDown(Hand hand) {
		return (hand.getHandValue() != 9 && hand.getHandValue() != 10 && hand.getHandValue() != 11) ||
				(hand.getCards().size() > 2);
	}
	
	
	/**
	   * Method does the Double Down to the Player- Reducing the balance, increasing the bet and block all other
	   * actions until the end of the round.
	   * @param hand Represents the hand being analysed.
	   * @return Boolean When the operation is sucessfull
	*/
	public Boolean doubleDown(Hand hand) {
		if (cannotDoubleDown(hand)) return false;
		
		balance -= hand.getBet();
		hand.setBet(hand.getBet()*2);
		hasDoubleDowned = true;
		
		hand.setHasDoubleDowned(true);
		
		return true;
	}
	
	/**
	   * Method does the Split to the Player- Creating a new hand, reducing the balance, placing a bet to the ne hand.
	   * @param hand Represents the hand being analysed.
	   * @return Boolean When the operation is sucessfull
	*/
	public Boolean split(Hand hand) {
		if (!hand.checkSplit(hand.getCards())) return false; // cards must have the same value
		
		int idx = hands.indexOf(hand); // position of hand in arraylist of hands
		// add second card to new hand with the same bet if hand limit won't be exceeded
		if(!addHand(hand.getBet(), hand.getCards().get(1), idx)) return false;
		
		// remove second card from original hand
		hand.removeCard(hand.getCards().get(1));
		
		// remove amount from balance
		balance -= hand.getBet();
		
		return true;
	}
	
	/**
	   * Method does the Surrender to the Player -  Get back half of the Bet
	   * @param hand Represents the hand being analysed.
	   * @return Boolean When the operation is sucessfull
	*/
	public Boolean surrender(int hand) {
		// get back half the bet
		balance += (float)getHand(hand).getBet()/2;
		setSurrendered(hand, true);
		return true;
	}
	
	/**
	   * Method does the Insurance to the Player - Remove be from balanceand is only activated when dealer's face-up card is an A.
	*/
	public void insurance() {
		if(hasDoubleDowned){
			throw new IllegalStateException("Cannot do any action while DoubleDown");
		} else {
			insurance = getHand(0).getBet();
			balance -= insurance;
		}		
	}
	
	/**
	   * Method that applies the Insurance to the Player when he wins.
	   * @param dealerHasBlackJack Represents if the dealer has or not a blackjack.
	*/
	public void applyInsurance(Boolean dealerHasBlackJack) {
	    if (dealerHasBlackJack && insurance != 0) {
	        balance += 2*insurance;
	        System.out.println("player wins insurance");
	    } else if (!dealerHasBlackJack && insurance != 0) {
	        System.out.println("player loses insurance");
	    }
	    insurance = 0;
	}
	
	/**
	   * Method that applies the Insurance to the Player when he wins.
	   * @param hand Represents the index of the hand being analysed.
	   * @param handsSize Represents the handDSize of the player
	*/
	public void printHand(int hand, int handsSize) {
		if (handsSize == 1) {
			System.out.println("player's hand " + getHand(hand));
		} else {
			System.out.println("player's hand [" + (hand+1) + "] " + getHand(hand));
		}
		
	}
}
