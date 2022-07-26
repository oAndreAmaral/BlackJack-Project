package modes;

import game.Blackjack;
import game.Shoe;
import strategies.StrategyAction;
import strategies.StrategyBet;

/**
* This class implements the simulation mode.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class ModeSimulation extends Mode{
	/**
     * Betting strategy to be used in simulation mode.
     */
	private StrategyBet strategyBet;
	
	/**
     * Player Action Strategy to be used in simulation mode.
     */
	private StrategyAction strategyAction;
	
	/**
     * This method is responsible for setting the betting strategy for
     * the simulation mode
     * @param strat the betting strategy
     */
	public void setStrategyBet(StrategyBet strat) {
		strategyBet = strat;
	}
	
	/**
     * This method is responsible for setting the action strategy for
     * the simulation mode
     * @param strat the player action strategy
     */
	public void setStrategyAction(StrategyAction strat) {
		strategyAction = strat;
	}
	
	/**
     * This method is responsible for getting the betting strategy
     * @return returns the simulation's betting strategy
     */	
	public StrategyBet getStrategyBet() {
		return strategyBet;
	}
	
	/**
     * This method is responsible for getting the action strategy
     * @return returns the simulation's player action strategy
     */	
	public StrategyAction getStrategyAction() {
		return strategyAction;
	}
	
	/**
     * This method is responsible for getting the shoe to be used
     * in the game by generating it.
     * @param game the game itself, where all high-level methods are
     * @return returns the game's shoe
     */	
	public Shoe getShoe(Blackjack game) {
		Shoe shoe = new Shoe(game.getDecks());
		shoe.generateShoe();
		shoe.shuffle();
		if (!((Blackjack)game).getModeStr().equals("-s")) {
			System.out.println("\nshuffling the shoe...");
		}
		return shoe;
	}

	/**
     * This method is responsible for getting the next command to be
     * interpreted by the StartRound or PlayerTurn states, by calling
     * the correct strategy in each situation, either for betting or
     * playing.
     * @param flag in simulation mode, it is a flag to switch between 
     * bet (1), deal (2) and player action (3) situation
     * @param game the game itself, where all high-level methods are
     * @param hand index of the hand in the array list of hands of the player
     * @return returns a command
     */	
	public String getCommand(int flag, Blackjack game, int hand) {
		// if else 3 hipoteses: bet, deal, ou jogada (hit, split, doubledown, o que for)
		switch(flag) {
		case 1:
			int bet = strategyBet.getStrategyBet(game.getMinBet(), game.getPrevBet(), game.getMaxBet(), 
												 game.getLastWinBalance(), game.getPlayer(), game.getDealer());
			return "b " + bet;
		case 2:
			return "d";
		case 3:
			switch(strategyAction.executeActionStrategy(game.getPlayerHand(hand), game.getFirstDealerCardValue(), game.getHandsSize())) {
			case "hit":
				return "h";
			case "stand":
				return "s";
			case "surrender":
				return "u";
			case "split":
				return "p";
			case "insurance":
				return "i";
			case "double":
				return "2";
			case "surrender if possible, otherwise hit":
				return "u";
			case "double if possible, otherwise hit":
				return game.getPlayer().cannotDoubleDown(game.getPlayerHand(hand)) ? "h" : "2";
			case "double if possible, otherwise stand":
				return game.getPlayer().cannotDoubleDown(game.getPlayerHand(hand)) ? "s" : "2";
			}
		}
	
	return "";
	}
}
