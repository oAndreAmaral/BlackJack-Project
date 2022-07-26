package game;

/**
* 
* This class implements the Cards that will be used in the shoe.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class Card {

	/**
     * String Field with the Card Ranks 
     */
	private static String[] ranks = {" "," ","2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	/**
     * String Field with the Card Suits 
     */
	private static String[] suits = {"C","D","H","S"};
	/**
     * indexes to run the strings searching for the correct rank
     */
	private int rank_idx;
	/**
     * indexes to run the strings searching for the correct suits
     */
	private int suit_idx;
	/**
     * variables to get the game card value
     */
	private int game_card_value;
	
	/**
	   * This method initialise the variables and extract the card value in the game.
	   * @param rank_idx To get inside the index of the card rank
	   * @param suit_idx  To get inside the index of the card suit
	   */
	public Card(int rank_idx, int suit_idx) {
		//super();
		this.rank_idx = rank_idx;
		this.suit_idx = suit_idx;
		
		if (rank_idx > 10 && rank_idx <14)
			this.game_card_value = 10;
		else if (rank_idx == 14)
			this.game_card_value = 11;
		else 
			this.game_card_value = rank_idx;
	}

	/**
	   * This method Obtain a card Value correspondent to each rank (2 -> 10 numbers, 11->13 figures, 14 -> ace)
	   * @return game_card_value  This returns the game_card_value
	   */
	public int cardValue() {
		return game_card_value;
	}
	
	/**
	   * This method gets a string with the card rank
	   * @return ranks[rank_idx]  This returns a string with card rank
	   */
	public String getRank() {
		return ranks[rank_idx];
	}
	
	/**
	   * This method change the game card value of an Ace from 11 to 1
	   * @return game_card_value  This returns a string with the game card value
	   */
	public int valueAceChange() {
		if (rank_idx == 14)
			game_card_value = 1;
	return game_card_value;
	}
	
	/**
	   * This method shows the card
	   * @return ranks[rank_idx] + suits[suit_idx] This returns a string with the card 
	   */
	public String toString() {
		return ranks[rank_idx] + suits[suit_idx];
	}

	/**
	   * This method gets a string with the card rank
	   * @return ranks[rank_idx]  This returns a string with card rank
	   */
	public String getRanksString() {
		return ranks[rank_idx];
	}
	
}
