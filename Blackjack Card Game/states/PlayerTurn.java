package states;

import game.Blackjack;
import game.Game;
import game.GameStateContext;

/**
* This class implements the player's turn in the current round. This 
* class is part of the game's state machine.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class PlayerTurn implements GameState{
	/**
     * This method is responsible for handling player's actions for each 
     * hand until there's a stand or a bust.
     * @param stateContext context through which game states are called
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */	
	public int run(GameStateContext stateContext, String[] args, Game game) {
		/**
		 * Auxiliary variable to save the commands that are scanned from file.
		 */
		String input;
		
		// code to handle player actions, etc.		
		for (int i = 0; i < ((Blackjack)game).getHandsSize(); i++) {
			if (((Blackjack)game).getHandsSize() > 1) {
				((Blackjack)game).playingHand(i); // tells what hand is being played
				((Blackjack)game).playerHit(i); // hit hand
				((Blackjack)game).printPlayerHand(i, ((Blackjack)game).getHandsSize()); // show hand
			}
			
			if (((Blackjack)game).checkIfIsSplitAce(i, ((Blackjack)game).getHandsSize())) {
				continue;
			}
			
			while(true) { // until ends hand
				input = ((Blackjack)game).getCommand(3, i);
				
				// print -cmd with the command if it is debug mode
				if (((Blackjack)game).getModeStr().equals("-d") && !input.equals("q")) {
					System.out.println("-cmd " + input);
				}
				
				if (input.equals("h")) { // hit
					if (!((Blackjack)game).playerHit(i) && !((Blackjack)game).getModeStr().equals("-s")) {
						System.out.println("h: illegal command");
					} else {
						((Blackjack)game).playerHitOutput(i, ((Blackjack)game).getHandsSize());
					}
				} else if (input.equals("s")) { // stand
					((Blackjack)game).playerStands(i, ((Blackjack)game).getHandsSize());
					break;
				} else if (input.equals("ad")) { // advice

					String basic_advice = ((Blackjack)game).getBasicAdvice(((Blackjack)game).getPlayerHand(i));
					String HiLo_advice = ((Blackjack)game).getHiLoAdvice(((Blackjack)game).getPlayerHand(i));
					
					System.out.println("basic\t" + basic_advice);
					System.out.println("hi-lo\t" + HiLo_advice);
				} else if (input.equals("p")) { // split
					if(!((Blackjack)game).playerSplit(i) && !((Blackjack)game).getModeStr().equals("-s")) {
						System.out.println("p: illegal command");
					} else {
						if (!((Blackjack)game).getModeStr().equals("-s")) {
							System.out.println("player is splitting");
						}
						i--;
						break;
					}
				} else if (input.equals("i")) { // insurance
					if (((Blackjack)game).getHandsSize() > 1 || !((Blackjack)game).checkFirstDealerCard() || 
						((Blackjack)game).getHandSize(0) != 2 || ((Blackjack)game).playerDoubleDowned(0)) {
						if (!((Blackjack)game).getModeStr().equals("-s")) {
							System.out.println("i: illegal command");
						}
					} else {
						((Blackjack)game).insurance();
						if (!((Blackjack)game).getModeStr().equals("-s")) {
							System.out.println("player is insuring");
						}
					}
				} else if (input.equals("u")) { // command is "u", surrender
					if (((Blackjack)game).getHandSize(i) > 2 && !((Blackjack)game).getModeStr().equals("-s")) { // can't surrender after hitting
						System.out.println("u: illegal command");
					} else {
						((Blackjack)game).surrender(i);
					}
					break;
				} else if (input.equals("2")) {
					if (!((Blackjack)game).doubleDown(i) && !((Blackjack)game).getModeStr().equals("-s")) { // can't doubledown after hitting or if hand value is not 9, 10 or 11
						System.out.println("2: illegal command");
					} else {
						((Blackjack)game).playerHit(i);
						((Blackjack)game).playerDoubleDownOutput(i, ((Blackjack)game).getHandsSize());
						break;
					}
				} else if (input.equals("$")) { // command is "$"
					((Blackjack)game).printPlayerBalance();
				} else if (input.equals("st")) { // command is "st"
					((Blackjack)game).printStatistics();
				} else if (input.equals("q")) { // command is "q"
					// only prints bye if it is not in debug mode
					if (!((Blackjack)game).getModeStr().equals("-d")) {
						System.out.println("bye");
					}
					stateContext.set(new EndGame());
					return 1;
				} else { // command not supported now
					((Blackjack)game).printIllegalCommand(input);
				}
				
				if (((Blackjack)game).checkIfBusted(i, ((Blackjack)game).getHandsSize())) {
					break;
				}
			}
		}
		
		// set the next state
		stateContext.set(new DealerTurn());
		return 1;
	}
}