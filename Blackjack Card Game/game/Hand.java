package game;

import java.util.ArrayList;
import java.util.List;
/**
* 
* This class defines Hand and can be used both by a Player or the Dealer and contains important information such as the bet associated
* to the hand and other important methods associated to hand operations such as check over splits, surrendered and also has the possibility to return
* the hand value.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class Hand {
	private ArrayList<Card> cards;
	private int bet;
	private boolean hasDoubleDowned;
	private boolean surrendered = false;
	
	/* Constructor */	
	/**
	   * Method is the Constructor of the class Hand and is used to initialize or create a Hand for the participant. 
	   * @param bet Bet associated to the Hand.
	*/
	public Hand(int bet) {
		this.cards = new ArrayList<Card>();
		this.bet = bet;
		this.hasDoubleDowned = false;
		this.surrendered = false;
	}
	
	//* Methods *// ----------------------------------------------------------	
	/**
	   * Method that return true if the hand has surrendered
	   * @return Boolean When it is surrendered.
	*/
	public boolean isSurrendered() {
		return surrendered;
	}
	/**
	   * Method that sets if the hand has surrendered
	   * @param surrendered When it is surrendered.
	*/
	public void setSurrendered(boolean surrendered) {
		this.surrendered = surrendered;
	}
	/**
	   * Method that returns a boolean if it is Doubling Down. 
	   * @return Boolean true if it's doubling Down
	*/
	public Boolean getHasDoubleDowned() {
		return hasDoubleDowned;
	}
	/**
	   * Method that sets a boolean if it is Doubling Down. 
	   * @param hasDoubleDowned true if it's doubling Down
	*/
	public void setHasDoubleDowned(Boolean hasDoubleDowned) {
		this.hasDoubleDowned = hasDoubleDowned;
	}
	
	/**
	   * Method that adds a card tp the original hand.
	   * @param card Card to add.
	*/
	public void addCard(Card card) {
		cards.add(card);
		checkAces();
	}
	/**
	   * Method that removes a card from the original hand.
	   * @param card Card to add.
	*/
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	/**
	   * Method that empties the original hand.
	*/
	public void clearHand() {
		cards.clear();
	}
	
	/**
	   * Method that gets the hand value.
	   * @return int Hand value.
	*/
	public int getHandValue() {
		int handValue = 0;
		
		for (int i = 0; i < cards.size(); i++) {
			handValue += cards.get(i).cardValue();
		}
		
		return handValue;
	}
	/**
	   * Method thatCheck if the Hands are well values in 11 or 1.
	*/
	private void checkAces() {
		if (getHandValue() > 21) {
			for (int i = cards.size()-1; i >= 0; i--) {
				if (cards.get(i).cardValue() == 11) {
					cards.get(i).valueAceChange();
					break;
				}
			}
		}
	}
	/**
	   * Method that check if the hand is busted.
	   * @return Boolean Check when the hand is over 21.
	*/
	public Boolean isBusted() {
		if (getHandValue() > 21) return true;
		
		return false;
	}
	/**
	   * Method that check if there is a pure blackJack.
	   * @return Boolean Check when the hand is a pure blackjack.
	*/
	public Boolean isBlackJack() {
		if (cards.size() != 2) return false;
		
		if ((cards.get(0).cardValue() == 11 && cards.get(1).cardValue() == 10) || 
			(cards.get(0).cardValue() == 10 && cards.get(1).cardValue() == 11)) return true;
		
		return false;
	}
	
	/**
	   * Method that check if the hand can split.
	   * @param cards2 Hand to be checked
	   * @return Boolean Check when the hand can split.
	*/
	public Boolean checkSplit(List<Card> cards2) {
		if (cards2.size() == 2 && 
		   ((cards2.get(0).cardValue() == cards2.get(1).cardValue()) || cards2.get(0).getRank().equals(cards2.get(1).getRank()))){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	   * Method that  Check if the hand has gone Bust -> over 21.
	   * @return Boolean Check when the hand is busted.
	*/
	public Boolean CheckIfBust() {
		return(getHandValue() > 21);
	}
	
	/**
	   * Method that Check if hand has As for hard,soft and check pairs.
	   * @return Boolean Check if hand has As for hard,soft and check pairs.
	*/
	public Boolean HasAce() {
		for (Card c : cards) {
			if (c.getRanksString() == "A"){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	   * Method that Check if first cards are Ace.
	   * @return Boolean Check if first cards are Ace.
	*/
	public Boolean checkFirstAces() {
		if (cards.get(0).getRanksString() == "A" && cards.get(1).getRanksString() == "A") {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	/**
	   * Method that Check if it's a soft hand.
	   * @return Boolean Check if it's a soft hand.
	*/
	public boolean CheckSoft() {
		for (Card c : cards) {
			if (c.getRanksString() == "A" && c.cardValue() == 11 && !checkFirstAces()){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	   * Method that Check if all As are 1.
	   * @return Boolean Check if all As are 1.
	*/
	public boolean CheckAllAces1() {
		for (Card c : cards) {
			if (c.getRanksString() == "A" && c.cardValue() != 1){
				return false;
			}
		}
		return true;
	}
	
	/**
	   * Method that Check if it's a Hard hand.
	   * @return Boolean Check if it's a Hard hand.
	*/
	public boolean CheckHard() {
		return ((!HasAce() || CheckAllAces1()) && !checkFirstAces());
	}
	
	
	// Counts Aces
	/**
	   * Method that Counts the number of aces.
	   * @return int Number of Aces.
	*/
	public int countAces() {
		int countAces = 0;
		for (Card c : cards) {
			if (c.getRanksString() == "A"){
				countAces++;
			}
		}
		return countAces;
	}	
	/**
	   * Method that Counts the number of fives.
	   * @return int Number of Fives.
	*/
	public int countFives() {
		int countFives = 0;
		for (Card c : cards) {
			if (c.getRanksString() == "5"){
				countFives++;
			}
		}
		return countFives;
	}
	/**
	   * Method that gets a string of cards to print.
	   * @return String  string of cards to print.
	*/
	private String printCards() {
		String str = "";
		for (int i = 0; i < cards.size(); i++) {
			str = str.concat(cards.get(i) + " ");
		}
		return str;
	}
	/**
	   * Method that prints a specific card.
	   * @param i index of the card on the hand.
	   * @return String print of the card.
	*/
	private String printCard(int i) {
		return "" + cards.get(i);
	}
	
	@Override
	/**
	   * Method that prints the hand in a specific way.
	   * @return String print of the hand.
	*/
	public String toString() {
		return printCards() + "(" + getHandValue() + ")";
	}
	/**
	   * Method that prints the hand in a specific way when one card is hidden.
	   * @return String print of the hand.
	*/
	public String toStringHidden() {
		return printCard(0) + " X";
	}
	/**
	   * Method that Gets the cards of the hand.
	   * @return ArrayList Get the cards of the hand.
	*/
	public List<Card> getCards() {
		return cards;
	}
	
	/**
	   * Method that Gets the bet of the hand.
	   * @return int Get the bet of the hand.
	*/
	public int getBet() {
		return bet;
	}
	/**
	   * Method that Sets the bet of the hand.
	   * @param amount Amount to be set.
	*/
	public void setBet(int amount) {
		bet = amount;
	}
}