package states;

import game.Blackjack;
import game.Game;
import game.GameStateContext;

/**
* This class implements the start of the game's round. This class is 
* part of the game's state machine.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/

public class StartRound implements GameState{
	/**
     * This method is responsible for starting the game's round. It waits for
     * the round's new bet and the confirmation to deal cards, while 
     * handling other possible commands. It also checks if it is time for 
     * shoe shuffling and, during simulation mode, if it should end.
     * @param stateContext context through which game states are called
     * @param args main parameters set by the user when running the program
     * @param game the game itself, where all high-level methods are
     * @return returns state related flag, which is used to know if the game 
     * should stop
     */	
	public int run(GameStateContext stateContext, String[] args, Game game) {
		String input;
		int bet;
		
		// code to start round, player, dealer, check if shuffle is needed, etc., etc., etc. ...
		((Blackjack)game).checkShuffle(); // checks if it needs to shuffle
		
		if (((Blackjack)game).isSimulationOver()) {
			stateContext.set(new EndGame());
			return 1;
		}
		
		// wait for bet
		while(true) {
			input = ((Blackjack)game).getCommand(1, 0);
			
			// print -cmd with the command if it is debug mode
			if (((Blackjack)game).getModeStr().equals("-d") && !input.equals("q")) {
				System.out.println("-cmd " + input);
			}
			
			if (input.startsWith("b")) { // command is "b"
				int checkBet = splitCommandB(input);
				if (checkBet == 0) { // bet should be the last one or minBet
					bet = ((Blackjack)game).getCorrectBet();
					((Blackjack)game).setPlayerBet(bet);
					break;
				} else if (checkBet == -1){ // incorrect bet command
					System.out.println("b: illegal command");
				} else { // bet is correct
					bet = checkBet;
					((Blackjack)game).setPlayerBet(bet);
					break;
				}
			} else if(input.equals("ad")) { // ad in betting
				int bet_acefive = ((Blackjack)game).getAceFiveAdvice();
				int bet_std = ((Blackjack)game).getStandardBetAdvice();
				
				System.out.println("standard\t" + bet_std);
				System.out.println("ace-five\t" + bet_acefive);
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
		}
		
		// wait for deal
		while(true) {
			input = ((Blackjack)game).getCommand(2, 0);
			
			// print -cmd with the command if it is debug mode
			if (((Blackjack)game).getModeStr().equals("-d") && !input.equals("q")) {
				System.out.println("-cmd " + input);
			}
			
			if (input.equals("d")) { // command is "d"
				((Blackjack)game).dealCards();
				break;
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
		}
		
		// reset win balance to be ready to count next round's win balance
		((Blackjack)game).resetLastWinBalance();
		
		// set the next state
		stateContext.set(new PlayerTurn());
		return 1;
	}
	/**
     * This method is responsible for splitting bet commands, by extracting
     * the bet value from the command string.
     * @param inputChar bet command from which to extract the bet's value
     * @return returns the command's bet value
     */
	private int splitCommandB(String inputChar) {
		int bet = 0;
		String betStr;
		if (inputChar.length() > 1) {
			if(inputChar.substring(0, 2).equals("b ")) {
				betStr = inputChar.substring(2, inputChar.length());
				try{
					bet = Integer.parseInt(betStr);
			    } catch(NumberFormatException e){
			        return -1;
			    }
			} else {
				return -1;
			}
		}
		return bet;
	}
}
