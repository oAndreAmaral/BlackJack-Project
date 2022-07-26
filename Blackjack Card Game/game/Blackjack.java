package game;

import modes.Mode;
import modes.ModeDebug;
import modes.ModeInteractive;
import modes.ModeSimulation;
import states.StartGame;
import strategies.AceFiveStrategy;
import strategies.BasicStrategy;
import strategies.HiLoStrategy;
import strategies.StandardStrategy;
/**
* 
* This class is the cern of the program and it's here where all the state patterns are called and executed as well as the modes for the game.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class Blackjack implements Game{
	/**
	 * The Shoe of the Game
	 */
	private Shoe shoe;
	/**
	 * The Player of the Game.
	 */
	private Player player;
	/**
	 * The Dealer of the Game.
	 */
	private Dealer dealer;
	/**
	 * The initializer of the BasicStrategy.
	 */
	private BasicStrategy basic;
	/**
	 * The initializer of the Hi-Lo Strategy.
	 */
	private HiLoStrategy hiLo;
	/**
	 * The initializer of the Standard Strategy.
	 */
	private StandardStrategy std;
	/**
	 * The initializer of the Ace-Five Strategy.
	 */
	private AceFiveStrategy aceFive;
	/**
	 * The initializer of the Statistics of the Game.
	 */
	private Statistics stats;
	/**
	 * The initializer of the Mode class that can be specialized in each game mode.
	 */
	private Mode mode;
	/**
	 * The string that defines the mode from the arguments of the game.
	 */
	private String modeStr;
	/**
	 * The minimum bet.
	 */
	private int minBet;
	/**
	 * The maximum bet.
	 */
	private int maxBet;
	/**
	 * The balance of the player.
	 */
	private float balance;
	/**
	 * The number of decks.
	 */
	private int decks;
	/**
	 * The shuffle percentage.
	 */
	private float shuffle;
	/**
	 * The Shoe file.
	 */
	private String shoeFile;
	/**
	 * The command file.
	 */
	private String cmdFile;
	/**
	 * The number of shuffles to perform until ending the simulation.
	 */
	private int sNumber;
	/**
	 * The current number of shuffles performed.
	 */
	private int numberOfShuffles;
	/**
	 * The string of the strategy from the arguments of the game.
	 */
	private String strat;
	/**
	 * The balance of wins/loses/pushes.
	 */
	private int lastWinBalance;
	/**
	 * The check boolean to understand if the correct arguments were used.
	 */
	private Boolean hasCorrectArgs;
	/**
	 * The initializer of the game state context.
	 */
	private GameStateContext stateContext;
	//* Constructor *// ----------------------------------------------------------
	/**
	   * BlackJack constructor that initializes the attributes associated to BlackJack.
	   * @param args main parameters set by the user when running the program
	*/
	public Blackjack(String[] args) {
		this.hasCorrectArgs = assignArgs(args);
		this.stats = new Statistics(this.balance);
		this.numberOfShuffles = 0;
		this.lastWinBalance = 0;
		this.stateContext = new GameStateContext();
	}
	
	/**
	 * Method that initializes the game and read the arguments for the Blackjack.
	 * @param game Blackjack Game.
	 * @param args Arguments.
	 */
	public void init(String[] args, Game game) {
		if (!hasCorrectArgs) {
			System.out.println("Incorrect arguments.");
			return;
		}
		
		DebugModeLoadCommands(); // load commands if debug mode
		createShoe(); // create and shuffle shoe
		initModeStrategies(); // initialize mode strategies
		
		stateContext.set(new StartGame());
		
		while(stateContext.runState(args, game) == 1) {
			// while the game hasn't ended
		}
	}
	
	/**
	   * Method that Check if the arguments are correct and assigns them to local attributes.
	   * @param args Game arguments.
	   * @return Boolean When the arguments are correct.
	*/
	private Boolean assignArgs(String[] args) {
		if (args == null) return false;
		if ((args.length != 6 || !args[0].equals("-i")) && // interactive mode
			(args.length != 6 || !args[0].equals("-d")) && // debug mode
			(args.length != 8 || !args[0].equals("-s"))) { // simulation mode
			return false;
		}
		
		modeStr = args[0];
		
		minBet = Integer.parseInt(args[1]);
		
		maxBet = Integer.parseInt(args[2]);
		if (maxBet < 10*minBet || maxBet > 20*minBet) return false;
		
		balance = Float.parseFloat(args[3]);
		if (balance < 50*minBet) return false;
		
		switch(modeStr) {
		case "-i":
			this.mode = new ModeInteractive();
			
			decks = Integer.parseInt(args[4]);
			if (decks < 4 || decks > 8) return false;
			
			shuffle = Float.parseFloat(args[5]);
			if (shuffle < 10 || shuffle > 90) return false;
			break;
		case "-d":
			this.mode = new ModeDebug();
			
			shoeFile = args[4];
			if (!shoeFile.endsWith(".txt")) return false;
			
			cmdFile = args[5];
			if (!cmdFile.endsWith(".txt")) return false;
			break;
		case "-s":
			this.mode = new ModeSimulation();
			
			decks = Integer.parseInt(args[4]);
			if (decks < 4 || decks > 8) return false;
			
			shuffle = Float.parseFloat(args[5]);
			if (shuffle < 10 || shuffle > 100) return false;
			
			sNumber = Integer.parseInt(args[6]);
			
			strat = args[7];
			
			// define strategies of the simulation mode
			if (!(strat.equals("BS") || strat.equals("BS-AF") || strat.equals("HL") || strat.equals("HL-AF"))) { // Basic and Standard
				return false;		
			}
		}
		return true;
	}

	/**
	   * Method that creates the Shoe of the game.
	*/
	public void createShoe() {
		shoe = mode.getShoe(this);
	}
	/**
	   * Method that load the command list.
	*/
	public void DebugModeLoadCommands() {
		if (modeStr.equals("-d")) {
			((ModeDebug)mode).loadCommandList(this);
		}
	}
	/**
	   * Method that initializes the strategies on the simulation mode.
	*/
	private void initModeStrategies() {
		// define strategies of the simulation mode
		if (modeStr.equals("-s")) {
			if (strat.equals("BS")) { // Basic and Standard
				((ModeSimulation)mode).setStrategyAction(new BasicStrategy());
				((ModeSimulation)mode).setStrategyBet(new StandardStrategy());
			}
			else if (strat.equals("BS-AF")) { // Basic and Ace-Five
				((ModeSimulation)mode).setStrategyAction(new BasicStrategy());
				((ModeSimulation)mode).setStrategyBet(new AceFiveStrategy());	
			}
			else if (strat.equals("HL")) { // HiLo and Standard
				((ModeSimulation)mode).setStrategyAction(new HiLoStrategy(this));
				((ModeSimulation)mode).setStrategyBet(new StandardStrategy());	
			}
			else if (strat.equals("HL-AF")) { // HiLo and Ace-Five
				((ModeSimulation)mode).setStrategyAction(new HiLoStrategy(this));
				((ModeSimulation)mode).setStrategyBet(new AceFiveStrategy());
			}
		}
	}
	/**
	   * Method that initializes the strategies.
	*/
	public void initStrategies() {
		basic = new BasicStrategy();
		hiLo = new HiLoStrategy(this);
		std = new StandardStrategy();
		aceFive = new AceFiveStrategy();
	}
	/**
	   * Method that resets all the counters related to the strategies.
	*/
	private void resetStrategyCounters() {
		// reset advice strategies
		aceFive.resetCounter();
		hiLo.resetCounter();
		
		// reset simulation strategies (if hilo or aceFive)
		if (modeStr.equals("-s")) {
			if (strat.equals("BS-AF")) { // Basic and Ace-Five
				((AceFiveStrategy)((ModeSimulation)mode).getStrategyBet()).resetCounter();
			}
			else if (strat.equals("HL")) { // HiLo and Standard
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).resetCounter();
			}
			else if (strat.equals("HL-AF")) { // HiLo and Ace-Five
				((AceFiveStrategy)((ModeSimulation)mode).getStrategyBet()).resetCounter();
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).resetCounter();
			}
		}
	}
	/**
	   * Method that gets the mode to be played.
	   * @return String Mode.
	*/
	public String getModeStr() {
		return modeStr;
	}
	/**
	   * Method that gets the command to be played.
	   * @return String Command.
	*/
	public String getCmdFile() {
		return cmdFile;
	}
	/**
	   * Method that gets the shoe file.
	   * @return String Shoe File.
	*/
	public String getShoeFile() {
		return shoeFile;
	}
	/**
	   * Method that gets the minimum bet to be played.
	   * @return int minimum bet.
	*/
	public int getMinBet() {
		return minBet;
	}
	/**
	   * Method that gets the previous bet used.
	   * @return int Previous Bet.
	*/
	public int getPrevBet() {
		return player.getPrevBet();
	}
	/**
	   * Method that gets the maximum bet
	   * @return int maximum bet.
	*/
	public int getMaxBet() {
		return maxBet;
	}
	/**
	   * Method that gets the Last balance from Win.
	   * @return int Last balance from Win.
	*/
	public int getLastWinBalance() {
		return lastWinBalance;
	}
	/**
	   * Method that resets the Last balance from Win.
	*/
	public void resetLastWinBalance() {
		lastWinBalance = 0;
	}
	/**
	   * Method that gets the Player.
	   * @return Player Game Player.
	*/
	public Player getPlayer() {
		return player;
	}
	/**
	   * Method that gets the Dealer.
	   * @return Dealer Game Dealer.
	*/
	public Dealer getDealer() {
		return dealer;
	}
	/**
	   * Method that creates the Player.
	*/
	public void createPlayer() {
		player = new Player(balance);
	}
	/**
	   * Method that creates the Dealer.
	*/
	public void createDealer() {
		dealer = new Dealer();
	}
	/**
	   * Method that gets the decks.
	   * @return int Game decks.
	*/
	public int getDecks() {
		return decks;
	}
	
	/**
	 * This method returns the number of decks left
	 * @return float number of decks left
	 */
	public float getDecksLeft() {
		return shoe.getDecksLeft();
	}
	
	/**
	 * This method returns the number of cards played
	 * @return int number of cards played
	 */
	public int getCardsPlayed() {
		return shoe.getCardsPlayed();
	}
	
	/**
	   * Method that if there is a need to shuffle and if there is, then shuffle the cards.
	*/
	public void checkShuffle() {
		if (modeStr.equals("-d")) return; // does not shuffle in debug mode
		
		if (shoe.playedPercentage() > shuffle) {
			shoe.generateShoe();
			shoe.shuffle();
			incrementNumberOfShuffles();
			if (!isSimulationOver() && !modeStr.equals("-s")) { // only prints if the game is not going to end
				System.out.println("\nshuffling the shoe...");
			}
			resetStrategyCounters();
		}
	}
	/**
	   * Method that gets commands
	   * @param flag in simulation mode, it is a flag to switch between 
	   * bet (1), deal (2) and player action (3) situation
	   * @param hand index of the hand in the Array List
	   * @return String Command.
	*/
	public String getCommand(int flag, int hand) {
		if (!modeStr.equals("-s")) {
			System.out.println(); // /n to separate commands
		}
		return mode.getCommand(flag, this, hand);
	}
	/**
	   * Method that gets the Player bet.
	   * @return int Get the corrected bet.
	*/
	public int getCorrectBet() {
		if (player.getPrevBet() != 0) return player.getPrevBet();
		
		return (int)Math.floor(minBet);
	}
	/**
	   * Method that prints the player balance.
	*/
	public void printPlayerBalance() {
		if (!modeStr.equals("-s")) {
			System.out.println("player current balance is " + player.getBalance());
		}
	}
	/**
	   * Method that prints the game Statistics.
	*/
	public void printStatistics() {
		System.out.println(stats);
	}
	/**
	   * Method that prints an Illegal Command.
	   * @param input Illegal command input
	*/
	public void printIllegalCommand(String input) {
		if (!modeStr.equals("-s")) {
			if (input.equals("illegal command")) {
				System.out.println(input);
			} else if (input.equals("ad")) {
				String twoChars = input.substring(0, 2);
				System.out.println(twoChars + ": illegal command");
			} else {
				String firstChar = input.substring(0, 1);
				System.out.println(firstChar + ": illegal command");
			}
		}
	}
	/**
	   * Method that sets the Player bet.
	   * @param bet current bet
	*/
	public void setPlayerBet(int bet) {
		player.setBet(bet);
		player.bet(bet);
		if (!modeStr.equals("-s")) {
			System.out.println("player is betting " + bet);
		}
	}
	/**
	   * Method that deals the cards to both the dealer and the player.
	*/
	public void dealCards() {
		dealer.addHand(0);
		dealer.hit(0, shoe);
		dealer.hit(0, shoe);
		
		player.addHand(player.getBet());
		player.hit(0, shoe);
		player.hit(0, shoe);
		
		if (!modeStr.equals("-s")) {
			dealer.printHand();
			player.printHand(0, 1);
		}
		
		// update running count
		hiLo.updateRunningCount(dealer.getHand(0).getCards().get(0).cardValue());
		hiLo.updateRunningCount(player.getHand(0).getCards().get(0).cardValue());
		hiLo.updateRunningCount(player.getHand(0).getCards().get(1).cardValue());
		
		// update running count if simulation has Hi-Lo Strategy
		if (modeStr.equals("-s")) {
			if (strat.equals("HL") || strat.equals("HL-AF")) {
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).updateRunningCount(dealer.getHand(0).getCards().get(0).cardValue());
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).updateRunningCount(player.getHand(0).getCards().get(0).cardValue());
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).updateRunningCount(player.getHand(0).getCards().get(1).cardValue());
			}
		}
	}
	/**
	   * Method that prints the Player Hand.
	   * @param hand Index of the hand in the participant hands.
	   * @param handsSize number of hands
	*/
	public void printPlayerHand(int hand, int handsSize) {
		if (!modeStr.equals("-s")) {
			player.printHand(hand, handsSize);
		}
	}
	/**
	   * Method that prints the Dealer Hand.
	*/
	public void printDealerHand() {
		if (!modeStr.equals("-s")) {
			dealer.printHand();
		}
	}
	/**
	   * Method that prints the hand size.
	   * @return int number of hands
	*/
	public int getHandsSize() {
		return player.getHands().size();
	}
	/**
	   * Method that performs the hit for the player, by adding a new card and update the strategy values.
	   * @param hand index of hand of list of hands
	   * @return boolean true if hit was successful
	*/
	public Boolean playerHit(int hand) {
		Boolean output = player.hit(hand, shoe);
		int lastPlayerCardIndex = player.getHand(hand).getCards().size() - 1;
		
		// update running count
		hiLo.updateRunningCount(player.getHand(hand).getCards().get(lastPlayerCardIndex).cardValue());
		
		// update running count if simulation has Hi-Lo Strategy
		if (modeStr.equals("-s")) {
			if (strat.equals("HL") || strat.equals("HL-AF")) {
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).updateRunningCount(player.getHand(hand).getCards().get(lastPlayerCardIndex).cardValue());
			}
		}
		return output;
	}
	/**
	   * Method that updates the running count of the Hi-lo strategy keeping in mind that the dealer has a hole card.
	*/
	public void updateRunningCountWithHoleCard() {
		// update running count
		hiLo.updateRunningCount(dealer.getHand(0).getCards().get(1).cardValue());
		
		// update running count if simulation has Hi-Lo Strategy
		if (modeStr.equals("-s")) {
			if (strat.equals("HL") || strat.equals("HL-AF")) {
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).updateRunningCount(dealer.getHand(0).getCards().get(1).cardValue());
			}
		}
	}
	/**
	   * Method that performs the Hit to the dealer.
	   * @return Boolean True if the hit was successful
	*/
	public Boolean dealerHit() {
		Boolean output = dealer.hit(0, shoe);
		int lastDealerCardIndex = player.getHand(0).getCards().size() - 1;
		
		// update running count
		hiLo.updateRunningCount(player.getHand(0).getCards().get(lastDealerCardIndex).cardValue());
		
		// update running count if simulation has Hi-Lo Strategy
		if (modeStr.equals("-s")) {
			if (strat.equals("HL") || strat.equals("HL-AF")) {
				((HiLoStrategy)((ModeSimulation)mode).getStrategyAction()).updateRunningCount(player.getHand(0).getCards().get(lastDealerCardIndex).cardValue());
			}
		}
		return output;
	}
	/**
	   * Method that prints player hand when he hits.
	   * @param hand index of the hand,
	   * @param handsSize Hand Size of the player
	*/
	public void playerHitOutput(int hand, int handsSize) {
		if (!modeStr.equals("-s")) {
			System.out.println("player hits");
			player.printHand(hand, handsSize);
		}
	}
	/**
	   * Method that prints player hand when he DoublesDown.
	   * @param hand index of the hand,
	   * @param handsSize Hand Size of the player
	*/
	public void playerDoubleDownOutput(int hand, int handsSize) {
		if (!modeStr.equals("-s")) {
			player.printHand(hand, handsSize);
		}
	}
	/**
	   * Method that prints which hand is playing.
	   * @param hand index of the hand,
	*/
	public void playingHand(int hand) {
		if (!modeStr.equals("-s")) {
			switch(hand) {
			case 0:
				System.out.println("playing 1st hand...");
				break;
			
			case 1:
				System.out.println("playing 2nd hand...");
				break;
			
			case 2:
				System.out.println("playing 3rd hand...");
				break;
			
			case 3:
				System.out.println("playing 4th hand...");
			}
		}
	}
	/**
     * Method that gets the player hand.
     * @param idx index of the hand,
     * @return Hand Player Hand
     */
	public Hand getPlayerHand (int idx) {
		return player.getHand(idx);
	}
	
	/**
     * Method that performs the player split.
     * @param hand index of the hand
     * @return Boolean If operation is successful
     */
	public Boolean playerSplit(int hand) {
		return player.split(player.getHand(hand));
	}
	
	/**
	   * Method that check if the desler exposed card is an Ace.
	   * @return Boolean True if dealer revealed card is an Ace
	*/
	
	public Boolean checkFirstDealerCard() {
		return dealer.getHand(0).getCards().get(0).getRanksString().equals("A");
	}
	
	/**
	   * Method that applies the insurance to the player.
	*/
	public void insurance() {
		player.insurance();
	}
	
	/**
	   * Method that gets the player hand Size.
	   * @param hand index of the hand
	   * @return size of hand
	*/
	public int getHandSize(int hand) {
		return player.getHand(hand).getCards().size();
	}
	
	
	/**
	   * Method that performs the DoubleDown.
	   * @param hand index of the hand
	   * @return true if double down was successful
	*/
	public Boolean doubleDown(int hand) {
		return player.doubleDown(player.getHand(hand));
	}
	/**
	   * Method that checks if the player is DoubleDowned.
	   * @param hand index of the hand
	   * @return true if player double downed
	*/
	public Boolean playerDoubleDowned(int hand) {
		return player.getHand(hand).getHasDoubleDowned();
	}
	/**
	   * Method that performs the Stand action for the player.
	   * @param hand index of the hand.
	   * @param handsSize The hand size of the player
	*/
	public void playerStands(int hand, int handsSize) {
		if (!modeStr.equals("-s")) {
			if (handsSize == 1) {
				System.out.println("player stands");
			} else {
				System.out.println("player stands [" + (hand+1) + "]");
			}
		}
	}
	/**
	   * Method that checks if the hand is busted.
	   * @param hand index of the hand.
	   * @param handsSize The player hands size.
	   * @return Boolean true if the player is busted
	*/
	public Boolean checkIfBusted(int hand, int handsSize) {
		Boolean busted = player.getHand(hand).isBusted();
		
		if (busted && !modeStr.equals("-s")) {
			if (handsSize == 1) {
				System.out.println("player busts");
			} else {
				System.out.println("player busts [" + (hand+1) + "]");
			}
		}
		return busted;
	}
	/**
	   * Method that checks if the split hand is with aces.
	   * @param hand index of the hand.
	   * @param handsSize The player hands size.
	   * @return Boolean true if the hand to split has two aces.
	*/
	public Boolean checkIfIsSplitAce(int hand, int handsSize) {
		if (handsSize > 1 && player.getHand(hand).getCards().get(0).getRank().equals("A")) {
			return true;
		}
		return false;
	}
	/**
	   * Method that checks if all the player hands are busted.
	   * @return Boolean true if all hands are busted or surrendered.
	*/
	public Boolean checkIfAllHandsBustedOrSurrendered() {
		for (Hand h : player.getHands()) {
			if (!h.isBusted() && !h.isSurrendered()) { // if any hand didn't bust and didn't surrender
				return false;
			}
		}
		return true;
	}
	/**
	   * Method that performs the surrender of a hand.
	   * @param hand index of the hand,
	*/
	public void surrender(int hand) {
		if (!modeStr.equals("-s")) {
			System.out.println("player is surrendering");
		}
		player.surrender(hand);
	}
	/**
	   * Method that performs the Dealer action.
	   * @return String Dealer Action
	*/
	public String dealerActionTurn() {
		return dealer.executeAction(dealer.getHand(0).getHandValue());
	}
	/**
	   * Method that prints the dealer Hand when the cards are all revealed.
	*/
	public void PrintDealerHandsRevealed() {
		dealer.setReveal(true);
		if (!modeStr.equals("-s")) {
			dealer.printHand();	
		}
	}
	/**
	   * Method that sets to false the reveal attribute of the dealer that 
	   * defines if one of the cards is not revealed yet..
	*/
	public void hideDealerHand() {
		dealer.setReveal(false);
	}
	/**
	   * Method resets the DoubleDown attribute.
	*/
	public void setPlayerDoubleDownFalse() {
		player.setHasDoubleDowned(false);
	}
	/**
	   * Method that calls the Ace Five strategy for the recommendation.
	   * @return int Bet to make by AceFive Strategy.
	*/
	public int getAceFiveAdvice() {
		return aceFive.getStrategyBet(minBet, player.getPrevBet(), maxBet, lastWinBalance, player, dealer);
	}
	/**
	   * Method that calls the Standard strategy bet for the recommendation.
	   * @return int Bet to make by Standard Strategy.
	*/
	public int getStandardBetAdvice() {
		return std.getStrategyBet(minBet, player.getPrevBet(), maxBet, lastWinBalance, player, dealer);
	}
	/**
	   * Method that calls the Hi-Lo strategy for the recommendation.
	   * @param hand Hand to get advice on
	   * @return String Action to make by Hi-Lo Strategy.
	*/
	public String getHiLoAdvice(Hand hand) {
		return hiLo.executeActionStrategy(hand, dealer.getHand(0).getCards().get(0).cardValue(), getHandsSize());
	}
	/**
	   * Method that calls the Basic strategy for the recommendation.
	   * @param hand Hand to get advice on
	   * @return String Action to make by Basic Strategy.
	*/
	public String getBasicAdvice(Hand hand) {
		return basic.executeActionStrategy(hand, dealer.getHand(0).getCards().get(0).cardValue(), getHandsSize());
	}
	/**
	   * Method that gets the dealer card value.
	   * @return int Dealer card value
	*/
	public int getFirstDealerCardValue() {
		return dealer.getHand(0).getCards().get(0).cardValue();
	}
	/**
	   * Method that checks the winner of the round.
	   * @param hand index of the hand.
	   * @param handsSize The player hands size.
	*/
	public void checkWinner(int hand, int handsSize) {
		if (player.isSurrendered(hand)) { // player surrendered
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player loses ");
				} else {
					System.out.print("player loses [" + (hand+1) + "] ");
				}
			}
			lastWinBalance--;
		} else if (player.getHand(hand).isBusted()) { // player busted
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player loses ");
				} else {
					System.out.print("player loses [" + (hand+1) + "] ");
				}
			}
			lastWinBalance--;
		} else if (dealer.getHand(0).isBusted()) { // dealer busted
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player wins ");
				} else {
					System.out.print("player wins [" + (hand+1) + "] ");
				}
			}
			if (player.getHand(hand).isBlackJack()) {
				if (handsSize == 1) { // only wins 2.5 bet when blackjack is on the starting hand
					player.winBJ(hand);
				} else {
					player.win(hand);
				}
			} else {
				player.win(hand);
			}
			lastWinBalance++;
		} else if (player.getHand(hand).isBlackJack() && !dealer.getHand(0).isBlackJack()) { // player has blackjack and dealer does not
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player wins ");
				} else {
					System.out.print("player wins [" + (hand+1) + "] ");
				}
				if (handsSize == 1) { // only wins 2.5 bet when blackjack is on the starting hand
					player.winBJ(hand);
				} else {
					player.win(hand);
				}
			}
			lastWinBalance++;
		} else if (!player.getHand(hand).isBlackJack() && dealer.getHand(0).isBlackJack()) { // dealer has blackjack and player does not
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player loses ");
				} else {
					System.out.print("player loses [" + (hand+1) + "] ");
				}
			}
			lastWinBalance--;
		} else if (player.getHand(hand).getHandValue() == dealer.getHand(0).getHandValue()) { // dealer and player have same hand value or both blackjack
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player pushes ");
				} else {
					System.out.print("player pushes [" + (hand+1) + "] ");
				}
			}
			player.push(hand);
//			lastResult = "Push";
		} else if (player.getHand(hand).getHandValue() > dealer.getHand(0).getHandValue()) { // player has higher hand value
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player wins ");
				} else {
					System.out.print("player wins [" + (hand+1) + "] ");
				}
			}
			player.win(hand);
			lastWinBalance++;
		} else if (player.getHand(hand).getHandValue() < dealer.getHand(0).getHandValue()) { // dealer has higher hand value
			if (!modeStr.equals("-s")) {
				if (handsSize == 1) {
					System.out.print("player loses ");
				} else {
					System.out.print("player loses [" + (hand+1) + "] ");
				}
			}
			lastWinBalance--;
		}
		if (!modeStr.equals("-s")) {
			System.out.println("and his current balance is " + player.getBalance());
		}
	}
	/**
	   * Method that checks if here is a blackjack and prints that information.
	*/
	public void checkBlackjack() {
		if (dealer.getHand(0).isBlackJack() && !modeStr.equals("-s")) {
			System.out.println("blackjack!!");
			return;
		}
		
		// if player has blackjack (opening hand)
		if (player.getHand(0).isBlackJack() && player.handsSize() == 1 && !modeStr.equals("-s")) {
			System.out.println("blackjack!!");
			return;
		}
	}
	/**
	   * Method that checks if it's possible to get insurance.
	*/
	public void checkInsurance() {
		player.applyInsurance(dealer.getHand(0).isBlackJack());
	}
	/**
	   * Method that updates the strategies.
	*/
	public void updateStatistics() {
		stats.updateStatistics(dealer.getHand(0), player, getHandsSize());
	}
	
	/**
	   * Method that prints the statistics in simulation mode.
	*/
	public void printStats() {
		if (modeStr.equals("-s")) {
			System.out.println(stats);
		}
	}
	/**
	   * Method that updates the AceFive Counter with the new cards that where introduced.
	*/
	public void updateAceFiveCounter() {
		// update Ace Five Counter for advice purposes
		aceFive.aceFiveCount(player, dealer);
		
		// update Ace Five Counter for simulation purposes
		if (modeStr.equals("-s")) {
			if (strat.equals("BS-AF")) { // Basic and Ace-Five
				((AceFiveStrategy)((ModeSimulation)mode).getStrategyBet()).aceFiveCount(player, dealer);
			}
			else if (strat.equals("HL-AF")) { // HiLo and Ace-Five
				((AceFiveStrategy)((ModeSimulation)mode).getStrategyBet()).aceFiveCount(player, dealer);
			}
		}
	}
	/**
	   * Method that clear both player and dealer hands.
	*/
	public void clearHands() {
		player.removeAllHands();
		dealer.removeAllHands();
	}
	/**
	   * Method that increments the number of shuffles.
	*/
	private void incrementNumberOfShuffles() {
		numberOfShuffles++;
	}
	/**
	   * Method that check if simulation mode has finished.
	   * @return boolean if simulation is over
	*/
	public boolean isSimulationOver() {
		if (modeStr.equals("-s")) {
			if (numberOfShuffles > sNumber) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
