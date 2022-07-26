package game;

/**
* 
* This class implements the Statistics
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class Statistics {

	/**
     * field to store the number of player blackjacks
     */
	private int playerBJ;
	/**
     * field to store the number of dealer blackjacks
     */
	private int dealerBJ;
	/**
     * field to store the number of player hands
     */
	private int playerHands;
	/**
     * field to store the number of dealer hands
     */
	private int dealerHands;
	/**
     * field to store the number of player wins
     */
	private int wins;
	/**
     * field to store the number of player losses
     */
	private int loses;
	/**
     * field to store the number of player pushes
     */
	private int pushes;
	/**
     * field to store the player initial balance
     */
	private float initialBalance;
	/**
     * field to store the player actual balance
     */
	private float playerBalance;
	
	/**
	   * This method initialise the variables.
	   * @param initialBalance To get the initial balance.
	   */
	public Statistics(float initialBalance) {
		this.playerBJ = 0;
		this.dealerBJ = 0;
		this.playerHands = 0;
		this.dealerHands = 0;
		this.wins = 0;
		this.loses = 0;
		this.pushes = 0;
		this.initialBalance = initialBalance;
		this.playerBalance = 0;
	}
	
	/**
	   * This is the general method to update the statistics
	   * @param dealerHand To get the dealer hand.
	   * @param player To get the player characteristics.
	   * @param numberHandsRound To get the number of hands in each round
	   */
	public void updateStatistics(Hand dealerHand, Player player , int numberHandsRound) {
		// updates number of played hands
		playerHands += numberHandsRound;
		dealerHands++;
		
		// update player and dealer BlackJack counters
		updateBJCount(player, dealerHand);
		
		// update number of player wins/loses/pushes 
		updateWinLosePush(player, dealerHand);
		
		// update player balance
		playerBalance = player.getBalance();
	}
	
	/**
	   * This method updates the Blackjack counter for the player and dealer
	   * @param dealerHand To get the dealer hand.
	   * @param player To get the player characteristics.
	   */
	private void updateBJCount(Player player, Hand dealerHand) {
		if (player.handsSize() == 1) { // only counts as blackjack if it was the opening hand
			if (player.getHand(0).isBlackJack()) {
				playerBJ++;
			}
		}
		
		if (dealerHand.isBlackJack()) {
			dealerBJ++;
		}
	}
	
	/**
	   * This method updates the win, lose and push counter.
	   * @param dealerHand To get the dealer hand.
	   * @param player To get the player characteristics.
	   */	
	private void updateWinLosePush(Player player, Hand dealerHand) {
		for (int  i = 0; i < player.handsSize(); i ++) {
			if (player.getHand(i).isBusted()) { // player busted
				loses++;
			} else if (dealerHand.isBusted()) { // dealer busted
				wins++;
			} else if (player.getHand(i).isBlackJack() && !dealerHand.isBlackJack()) { // player had blackjack and dealer did not
				wins++;
			} else if (!player.getHand(i).isBlackJack() && dealerHand.isBlackJack()) { // dealer had blackjack and player did not
				loses++;
			} else if (player.getHand(i).getHandValue() == dealerHand.getHandValue()) { // player pushed
				pushes++;
			} else if (player.getHand(i).getHandValue() > dealerHand.getHandValue()) { // player won
				wins++;
			} else if (player.getHand(i).getHandValue() < dealerHand.getHandValue()) { // player lost
				loses++;
			}
		}
	}
	
	/**
	   * This method updates the player blackjack per hand.
	   * @return playerHands Returns the average number of player' Blackjacks
	   */	
	private float getAvgPlayerBJ() {
		return playerHands == 0 ? 0 : (float)playerBJ/playerHands;
	}
	
	/**
	   * This method updates the dealer blackjack per hand.
	   * @return playerHands Returns the average number of dealer' Blackjacks
	   */
	private float getAvgDealerBJ() {
		return dealerHands == 0 ? 0 : (float)dealerBJ/dealerHands;
	}
	
	/**
	   * This method updates the player win average per hand.
	   * @return playerHands Returns the average number of player wins
	   */
	private float getAvgWins() {
		return playerHands == 0 ? 0 : (float)wins/playerHands;
	}
	
	/**
	   * This method updates the player lose average per hand.
	   * @return playerHands Returns the average number of player loses
	   */
	private float getAvgLoses() {
		return playerHands == 0 ? 0 : (float)loses/playerHands;
	}
	
	/**
	   * This method updates the player push average per hand.
	   * @return playerHands Returns the average number of player pushes
	   */
	private float getAvgPushes() {
		return playerHands == 0 ? 0 : (float)pushes/playerHands;
	}
	
	/**
	   * This method updates the player initial Balance.
	   * @return initialBalance Returns the initialBalance of the player
	   */
	private float getBalancePercentage() {
		return initialBalance == 0 ? 0 : playerBalance/initialBalance*100;
	}
	
	/**
	   * This method generates the required output of the statistics
	   */
	@Override
	public String toString() {
		return "BJ P/D " + getAvgPlayerBJ() + "/" + getAvgDealerBJ() + "\n" + 
			   "Win " + getAvgWins() + "\n" + 
			   "Lose " + getAvgLoses() +"\n" + 
			   "Push " + getAvgPushes() + "\n" + 
			   "Balance " + playerBalance + "(" + getBalancePercentage() + "%)";
	}
}