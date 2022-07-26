package modes;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import game.Shoe;
import game.Card;
import game.Blackjack;

/**
* 
* This class is an extension of the mode class and defines the debug mode. In short, the class reads commands and a shoe from 2 different files and stores
* that information in specific variables that are then used for the game.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class ModeDebug extends Mode{
	
	/**
	 * The List of commands in a given file.
	 */
	ArrayList<String> cmdList;
	/**
	 * A counter to understand the number of cards.
	 */
	int counter;
	/**
	 * The base path of the file.
	 */
	String basePath;
	/**
	 * The path of the examples after having the base path on the PC.
	 */
	String path;
	
	/**
	   * Method is the Constructor of the class ModeDebug and is used to initialize the paths of the files and counter of the number of cards. 
	*/
	public ModeDebug() {
		this.cmdList = new ArrayList<String>(); // list of commands
		this.counter = 0;
		this.basePath = new File("").getAbsolutePath(); // Project\Blackjack
		this.path = basePath + "\\..\\DebugFiles\\";
	}

	@Override
	/**
	   * Method returns the Shoe from a specific Shoe File that comes from the arguments of the game.
	   * @param game Defines the BlackJAck game.
	   * @return Shoe Shoe from file.
	*/
	public Shoe getShoe(Blackjack game) {
		File fileShoe = new File(path + game.getShoeFile());
		
	    ArrayList<Card> newShoe = new ArrayList<Card>();
	    
		String[] ranksString = {" "," ","2","3","4","5","6","7","8","9","10","J","Q","K","A"};
		String [] suitsString = {"C","D","H","S"};
	    
		int rank_idx = 0;
	    int suit_idx = 0;
	    int count = 0;
	    
	    try {
	    	Scanner fileReader = new Scanner(fileShoe);
	    	while (fileReader.hasNext()) {
	    		String word = fileReader.next();
	    		count = count +1;
	    		if( word.length() == 2) {
	    			for(int j = 0; j < ranksString.length ; j++ ) {
	    				if(ranksString[j].equals(String.valueOf(word.charAt(0)))) {
	    					rank_idx = j;	
	    				}	
	    			}
	    			for(int j = 0; j < suitsString.length ; j++ ) {
	    				if(suitsString[j].equals(String.valueOf(word.charAt(1)))) {
	    					suit_idx = j;	
	    				}
	    			}
	    			newShoe.add(new Card(rank_idx,suit_idx));
	    		}
		        if(word.length() == 3) {
		        	String number_final = new StringBuilder().append(word.charAt(0)).append(word.charAt(1)).toString();
					for(int j = 0; j < ranksString.length ; j++ ) {
						if(ranksString[j].equals(number_final)) {
							rank_idx = j;
						}
					}
					for(int j = 0; j < suitsString.length ; j++ ) {
						if(suitsString[j].equals(String.valueOf(word.charAt(2)))) {
							suit_idx = j;
						}
					}
					newShoe.add(new Card(rank_idx,suit_idx));
		        }
		        //Now you can use the word. By default, Scanner is space-delimited.
	    	}
			fileReader.close();
			Shoe shoe = new Shoe((float)count/52);
			shoe.generateShoeFromFile(newShoe, count);
			
			return shoe;
			
	    } catch (IOException e) {
	      // Handle error...
	    	return null;
	    }
	}

	@Override
	/**
	   * Method returns the next command from the command list.
	   * @param flag Specific flag for the Game State.
	   * @param game Defines the BlackJAck game.
	   * @param hand Index of the AHnd in the Hands in the Hands of the Participant.
	   * @return String Command.
	*/
	public String getCommand(int flag, Blackjack game, int hand) {
	    if (counter < cmdList.size()) {
	    	String cmd = cmdList.get(counter);
	    	counter++;
	    	return cmd;
	    } else {
	    	return "q";
	    }
	}
	/**
	   * Method saves the command list from a specific file.
	   * @param game Defines the BlackJack Game.
	*/
	public void loadCommandList(Blackjack game) {
		File fileCmd = new File(path + game.getCmdFile());
		
		try {
			Scanner fileReader_cmd = new Scanner(fileCmd);
			while (fileReader_cmd.hasNext()) {
				String commands = fileReader_cmd.next();
				if (cmdList.size() > 0 && cmdList.get(cmdList.size() - 1).equals("b") && checkIfInteger(commands)) {
					cmdList.set(cmdList.size() - 1, cmdList.get(cmdList.size() - 1).concat(" " + commands));
					continue;
				}
				cmdList.add(commands);
			}
			
			fileReader_cmd.close();
		} catch (IOException e) {
			// Handle error...
	    }
	}
	/**
	   * Method that check if the command is an integer. Useful for the betting.
	   * @param cmd Command to analyse.
	   * @return Boolean if the command read is an integer
	*/
	private boolean checkIfInteger(String cmd) {
		try {
	        Integer.parseInt(cmd);
	        return true;
	    } catch (final NumberFormatException e) {
	        return false;
	    }
	}
}