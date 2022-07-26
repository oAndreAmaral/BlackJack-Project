package states;

import game.Blackjack;
import game.Game;
import game.GameStateContext;
/**
* 
* This class is part of the state pattern(Game State) that defines each phase of the BlackJack game. In this case
* we have the dealer Turn which defines the dealer actions in the game, from hitting, standing or going bust.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class DealerTurn implements GameState{
	/**
	   * Method that reuns the DealerTurn state that performs the dealer action and prints the cards for the terminal.
	   * In the end it passes to the end round to define if the player or dealer won.
	   * @param stateContext State Patterm.
	   * @param args Arguments.
	   * @param game Current game.
	   * @return int When dealer play ends.
	*/
	public int run(GameStateContext stateContext, String[] args, Game game) {
		// code to handle dealer actions
		
		// update running count with hole card
		((Blackjack)game).updateRunningCountWithHoleCard();
		
		while(true) { // until ends hand
			((Blackjack)game).PrintDealerHandsRevealed(); 
			
			if(((Blackjack)game).checkIfAllHandsBustedOrSurrendered()) {
				break;
			}
			
			String DealerAction = ((Blackjack)game).dealerActionTurn();
			// Player bust Dealer só mostra a hand
			if(DealerAction.equals("h")) {
				if (!((Blackjack)game).getModeStr().equals("-s")) {
					System.out.println("dealer hits");
				}
				((Blackjack)game).dealerHit();
			} else if(DealerAction.equals("s")){
				if (!((Blackjack)game).getModeStr().equals("-s")) {
					System.out.println("dealer stands");
				}
				break;
			} else if(DealerAction.equals("b")){
				if (!((Blackjack)game).getModeStr().equals("-s")) {
					System.out.println("dealer busts");
				}
				break;
			} else {
				if (!((Blackjack)game).getModeStr().equals("-s")) {
					System.out.println("Error Ocurred");
				}
			}
		
		}
		
		// next round dealer's second card must be hidden!
		((Blackjack)game).hideDealerHand();
		
		// set player doubleDowned to false
		((Blackjack)game).setPlayerDoubleDownFalse();
		
		// set the next state
		stateContext.set(new EndRound());
		return 1;
	}
}