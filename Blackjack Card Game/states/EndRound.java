package states;

import game.Blackjack;
import game.Game;
import game.GameStateContext;

/**
* This class implements the end of the game's current round. This class 
* is part of the game's state machine.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class EndRound implements GameState{
	/**
     * This method is responsible for evaluating the outcome of the round. It
     * checks for anyone's blackjack and if the player insured, after which it
     * evaluates the player's hand relative to the dealer's hand. It also updates
     * the game's statistics, updates Ace Five strategy internal information and
     * clears both the player's and the dealer's hands.
     * @param stateContext context through which game states are called
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */
	public int run(GameStateContext stateContext, String[] args, Game game) {
		// code to evaluate round output (win, lose, push for each hand), check insurances, etc., etc., etc. ...
		
		((Blackjack)game).checkBlackjack();
		((Blackjack)game).checkInsurance();
		
		for (int i = 0; i < ((Blackjack)game).getHandsSize(); i++) { // for each player hand
			((Blackjack)game).checkWinner(i, ((Blackjack)game).getHandsSize()); // print outcome and update player balance
		}
		
		// statistics
		((Blackjack)game).updateStatistics();
		
		// update Ace Five Strategy
		((Blackjack)game).updateAceFiveCounter();
		
		// erase player and dealer hands
		((Blackjack)game).clearHands();
		
		stateContext.set(new StartRound()); // goes back to the start round
		
		return 1;
	}
}