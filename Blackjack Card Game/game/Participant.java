package game;

import java.util.ArrayList;


/**
* 
* This class implements the Participant which is a super class of the dealer and player.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class Participant {

	/**
     * Array List of handsa
     */
	protected ArrayList<Hand> hands;
	/**
     * boolean variable to check if has double downed
     */
	protected Boolean hasDoubleDowned;
	
	/**
	   * This method initialise the variables.
	   */
	public Participant() {
		this.hands = new ArrayList<Hand>();
		this.hasDoubleDowned = false;
	}
	
	/**
	   * This method initialise the double down variable
	   * @param hasDoubleDowned To be use to initialise the doubleDown variable
	   */
	public void setHasDoubleDowned(Boolean hasDoubleDowned) {
		this.hasDoubleDowned = hasDoubleDowned;
	}

	/**
	   * This method is used to do the "Hit" action.
	   * @param hand To get the respective hands where the dealt card will go.
	   * @param shoe To get the shoe where the card will be withdraw
	   * @return true if hit went successfully
	   */
	public Boolean hit(int hand, Shoe shoe) {		
		Card card = shoe.giveCard();
		
		hands.get(hand).addCard(card);
		
		return true;
	}
	
	/**
	   * This method is used to get the requested hand.
	   * @param hand To get the respective hand where the dealt card will go.
	   * @return hands.get(hand) This return the requested hand.
	   */
	public Hand getHand(int hand) {
		return hands.get(hand);
	}
	
	/**
	   * This method is used to get a ArrayList of the hands in the game
	   * @return hands This return the hands in the table.
	   */
	public ArrayList<Hand> getHands() {
		return hands;
	}
	
	/**
	   * This method is used to remove specific hand
	   * @param hand To get the respective hand where the card will be removed
	   */
	protected Boolean removeHand(int hand) {
		hands.remove(hands.get(hand));
		return true;
	}
	
	/**
	   * This method is used to remove all the hands in the table. It is specially used in the end of each round.
	   * @return if all hands were removed
	   */
	public Boolean removeAllHands() {
		for (int i = hands.size()-1; i >= 0; i--) {
			if(!removeHand(i)) {
				return false;
			}
		}
		return true;
	}
}
