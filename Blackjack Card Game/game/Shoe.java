package game;

import java.util.ArrayList;
import java.util.Collections;

/**
* 
* This class implements the counting the shoe
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class Shoe {

	/**
     * field to store the number of Decks
     */
	private float numDecks;
	/**
     * field to store the total of cards
     */
	private int totalCards;
	/**
     * field to store the number of cardsPlayed
     */
	private int cardsPlayed;
	/**
     * field to store the shoe cards list
     */
	private ArrayList<Card> shoe;
	
	/**
	   * This method initialise the variables needed related to the shoe
	   * @param numDecks To get inside the method the number of desired decks
	   */
	public Shoe( float numDecks ) {
		this.numDecks = numDecks;
		this.totalCards = 52*Math.round(numDecks);
		this.cardsPlayed = 0;
	}
	
	/**
	   * This method is a getter to obtain the Number of Decks
	   * @return numDecks This returns the number of Decks
	   */
	public float getNumDecks() {
		return numDecks;
	}

	/**
	 * This method computes the number of decks left
	 * @return float number of decks left
	 */
	public float getDecksLeft() {
		return (float)(totalCards - cardsPlayed)/(52*numDecks);
	}
	
	/**
	   * This method is a getter to obtain the Number of cards played
	   * @return cardsPlayed This returns the number of cards played
	   */
	public int getCardsPlayed() {
		return cardsPlayed;
	}

	/**
	   * This method Create The Shoe
	   */
	public void generateShoe(){
		ArrayList<Card> newShoe = new ArrayList<Card>();
		
		for(int number = 0; number < numDecks; number++) {
			for(int suit = 0; suit <= 3; suit++ ) {
				for(int rank = 2; rank <= 14; rank++ ) {
					
					newShoe.add(new Card(rank, suit));
					
				}
			}			
		}
		shoe = newShoe;
		cardsPlayed = 0;
	}
	
	/**
	   * This method executes the shuffle
	   * @return shoe This returns a shuffled show
	   */
	public ArrayList<Card> shuffle() {
		Collections.shuffle(shoe);
		return shoe;
	}
	
	/**
	   * This method give a card, randomly, out of our shoe
	   * @return shoe.remove(0) This returns the removed card from the shoe
	   */
	public Card giveCard() {
		cardsPlayed++;
		return shoe.remove(0);		
	}
	
	/**
	   * This method calculates the played percentage
	   * @return float this returns the played percentage
	   */
	public float playedPercentage() {
		return ((float)cardsPlayed/totalCards)*100;
	}
	
	/**
	   * This method generates a shoe from the file
	   * @param setOfCards list of cards
	   * @param numDecks number of decks
	   */
	public void generateShoeFromFile(ArrayList<Card> setOfCards, int numDecks){
		shoe = setOfCards;
		cardsPlayed = 0;
	}
		
	/**
	   * This method prints the string of the shoe cards
	   */
	public String toString() {
		return "The Shoe has: " + numDecks + " Decks. The Shoe cards are the following: " +shoe;
	}	
}
