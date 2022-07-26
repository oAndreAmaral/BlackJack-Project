package game;

import java.util.Scanner;

/**
* 
* This class implements the Reading of the User Input in the terminal.
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class UserInput {

	/**
     * creation of an object to stor the scan
     */
	private Scanner myObjValue = new Scanner(System.in);
	/**
     * field to stor teh input char from the terminal
     */
	private String inputChar;
	
	/**
	 * This method reads the user input (and verifies if the input char is in the command list given in the paper)
	 * This method returns the input command or if it is invalid it returns a String saying "illegal command"
	 * @return String input from user
	 */
	public String readUserInput(){
		inputChar = myObjValue.nextLine(); 
		if(inputChar.startsWith("b") || inputChar.equals("$") || inputChar.equals("d") || inputChar.equals("h") || 
		   inputChar.equals("s") || inputChar.equals("i") || inputChar.equals("u") || inputChar.equals("p") || 
		   inputChar.equals("2") || inputChar.equals("ad") || inputChar.equals("st") || inputChar.equals("q")) {
				return inputChar;	
		}
		return "illegal command";
	}
}