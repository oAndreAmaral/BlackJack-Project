package strategies;

import game.Hand;

/**
* 
* This class implements the counting cards strategy, more properly the Basic Strategy
*
* @author  André Amaral, Eduardo Cunha, Rafael Cordeiro
* @version 1.0
* @since   2021-05-24
*/
public class BasicStrategy implements StrategyAction {

	/**
     * creation of a matrix to store the strategy for an hard hand
     */
	private static String matrixHard[][] = new String[23][23] ;
	/**
     * creation of a matrix to store the strategy for a soft hand
     */
	private static String matrixSoft[][] = new String[23][23] ;
	/**
     * creation of a matrix to store the strategy for a pair hand
     */
	private static String matrixPair[][] = new String[23][23] ; 
	/**
     * creation of a matrix to store the strategy to be used
     */
	private String matrix1;
	
	/**
	 * This method constructs the matrix. It is useful in order to only construct those matrices one single time
	 */
	public BasicStrategy() {
		//---------------------- Initialise hard matrix-----------------------------------------------
		matrixHard[4][2] = matrixHard[4][3] = matrixHard[4][4] = matrixHard[4][5] = matrixHard[4][6] = matrixHard[4][7] = 
		matrixHard[4][8] = matrixHard[4][9] = matrixHard[4][10] = matrixHard[4][11] = "hit";
				
		for(int i = 5; i<=8; i++) {
			for(int j = 2; j<=11; j++) {
				matrixHard[i][j] = "hit";
			}
		}
		
		matrixHard[9][2] = matrixHard[9][7] = matrixHard[9][8] = matrixHard[9][9] = matrixHard[9][10] = matrixHard[9][11] = "hit";
		matrixHard[9][3] = matrixHard[9][4] = matrixHard[9][5] =  matrixHard[9][6] = "double if possible, otherwise hit"; 
		
		for(int i = 10; i<=11; i++) {
			for(int j = 2; j<=9; j++) {
				matrixHard[i][j] = "double if possible, otherwise hit";
			}
		}   
		
		matrixHard[10][10] = matrixHard[10][11] = matrixHard[11][11] = "hit";
		matrixHard[11][10] = "double if possible, otherwise hit";
		   
		matrixHard[12][2] = matrixHard[12][3] = "hit";
		matrixHard[12][4] = matrixHard[12][5] = matrixHard[12][6] = "stand";
		 
		for(int i = 12; i<=14; i++) {
			for(int j = 7; j<=11; j++) {
				matrixHard[i][j] = "hit";
			}
		} 
		for(int i = 13; i<=16; i++) {
			for(int j = 2; j<=6; j++) {
				matrixHard[i][j] = "stand";
			}
		} 
		for(int i = 15; i<=16; i++) {
			for(int j = 7; j<=8; j++) {
				matrixHard[i][j] = "hit";
			}
		} 
		matrixHard[15][9] = matrixHard[15][11] = "hit";
		matrixHard[15][10] = matrixHard[16][9] = matrixHard[16][10] = matrixHard[16][11] = "surrender if possible, otherwise hit";
		   
		for(int i = 17; i<=21; i++) {
			for(int j = 2; j<=11; j++) {
				matrixHard[i][j] = "stand";
			}
		}
		
		//---------------------- Initialise Soft matrix----------------------------------------------
		for(int i = 13; i<=16; i++) {
			for(int j = 2; j<=3; j++) {
				matrixSoft[i][j] = "hit";
			}
		} 
		
		matrixSoft[13][4] = matrixSoft[14][4] = matrixSoft[17][2] = "hit";
		matrixSoft[15][4] = matrixSoft[16][4] = matrixSoft[17][4] = matrixSoft[17][3] = "double if possible, otherwise hit";
		
		for(int i = 13; i<=17; i++) {
			for(int j = 5; j<=6; j++) {
				matrixSoft[i][j] = "double if possible, otherwise hit";
			}
		}
		
		for(int i = 13; i<=17; i++) {
			for(int j = 7; j<=11; j++) {
				matrixSoft[i][j] = "hit";
			}
		}
		
		for(int i = 19; i<=21; i++) {
			for(int j = 2; j<=11; j++) {
				matrixSoft[i][j] = "stand";
			}
		}
		
		matrixSoft[18][2] = matrixSoft[18][7] = matrixSoft[18][8] = "stand";
		matrixSoft[18][3] = matrixSoft[18][4] = matrixSoft[18][5] = matrixSoft[18][6] = "double if possible, otherwise stand";
		matrixSoft[18][9] = matrixSoft[18][10] = matrixSoft[18][11] = "hit";
		
		//---------------------- Initialise Pair matrix----------------------------------------------
				
		matrixPair[4][2] = matrixPair[4][3] = matrixPair[4][8] = matrixPair[4][9] = matrixPair[4][10] = matrixPair[4][11] =   "hit";
		matrixPair[4][4] = matrixPair[4][5] = matrixPair[4][6] = matrixPair[4][7] = "split";
		   
		matrixPair[6][2] = matrixPair[6][3] = matrixPair[6][8] = matrixPair[6][9] = matrixPair[6][10] = matrixPair[6][11] =   "hit";
		matrixPair[6][4] = matrixPair[6][5] = matrixPair[6][6] = matrixPair[6][7] = "split";
		
		matrixPair[8][2] = matrixPair[8][3] = matrixPair[8][8] = matrixPair[8][9] = matrixPair[8][10] = matrixPair[8][11] =   "hit";
		matrixPair[8][4] = matrixPair[8][5] = matrixPair[8][6] = matrixPair[8][7] = "hit";
		   
		matrixPair[10][2] = matrixPair[10][3] = matrixPair[10][4] = matrixPair[10][5] = matrixPair[10][6] = matrixPair[10][7] = matrixPair[10][8] = matrixPair[10][9] = "double if possible, otherwise hit"; 
		matrixPair[10][10] = matrixPair[10][11] = "hit";
			 
		matrixPair[12][2] = matrixPair[12][7] = matrixPair[12][8] = matrixPair[12][9] = matrixPair[12][10] = matrixPair[12][11] = "hit";
		matrixPair[12][3] = matrixPair[12][4] = matrixPair[12][5] = matrixPair[12][6] = "split";
		
		matrixPair[14][2] = matrixPair[14][3] = matrixPair[14][4] = matrixPair[14][5] = matrixPair[14][6] = matrixPair[14][7] = "split";
		matrixPair[14][8] = matrixPair[14][9] = matrixPair[14][10] = matrixPair[14][11] = "hit";
			
		matrixPair[16][2] = matrixPair[16][3] = matrixPair[16][8] = matrixPair[16][9] = matrixPair[16][10] = matrixPair[16][11] =   "split";
		matrixPair[16][4] = matrixPair[16][5] = matrixPair[16][6] = matrixPair[16][7] = "split";	
		   
		matrixPair[18][7] = matrixPair[18][10] = matrixPair[18][11] = "stand";
		matrixPair[18][2] = matrixPair[18][3] = matrixPair[18][4] = matrixPair[18][5] = matrixPair[18][6] = matrixPair[18][8] = matrixPair[18][9] = "split";
		
		matrixPair[20][2] = matrixPair[20][3] = matrixPair[20][8] = matrixPair[20][9] = matrixPair[20][10] = matrixPair[20][11] = "stand";
		matrixPair[20][4] = matrixPair[20][5] = matrixPair[20][6] = matrixPair[20][7] = "stand";	
			
		matrixPair[22][2] = matrixPair[22][3] = matrixPair[22][8] = matrixPair[22][9] = matrixPair[22][10] = matrixPair[22][11] =   "split";
		matrixPair[22][4] = matrixPair[22][5] = matrixPair[22][6] = matrixPair[22][7] = "split";					
	}
	
	/**
	 * This method executes the Basic Strategy taking in consideration the type of hand.
	 * @param player To get inside the method the player hand value
	 * @param dealer_val To get inside the method the dealer face-up card value
	 * @return matrix1 This returns the action according to the type of hand
	 */
	public String executeActionStrategy(Hand player, int dealer_val, int handsSize) {
		if(player.checkSplit(player.getCards()) && handsSize < 4) {
			matrix1 = executeActionStrategyPAIR(player, dealer_val);
		} else if(player.CheckSoft() == true){
			matrix1 = executeActionStrategySOFT(player, dealer_val);			
		} else{
			matrix1 = executeActionStrategyHARD(player, dealer_val);
		}	
		return matrix1;
	}
	
	/**
	 * This method generates the strategy actions if the hand is Hard.
	 * @param player To get inside the method the player hand value
	 * @param dealer_val To get inside the method the dealer face-up card value
	 * @return matrixHard[player.getHandValue()][dealer_val]  This returns the action
	 */
	public String executeActionStrategyHARD(Hand player, int dealer_val){
		return matrixHard[player.getHandValue()][dealer_val]; 
	}

	/**
	 * This method generates the strategy actions if the hand is Soft.
	 * @param player To get inside the method the player hand value
	 * @param dealer_val To get inside the method the dealer face-up card value
	 * @return matrixSoft[player.getHandValue()][dealer_val]  This returns the action
	 */
	public String executeActionStrategySOFT(Hand player, int dealer_val){
	    return matrixSoft[player.getHandValue()][dealer_val]; 		
	}

	/**
	 * This method generates the strategy actions if the hand has a Pair.
	 * @param player To get inside the method the player hand value
	 * @param dealer_val  To get inside the method the dealer face-up card value
	 * @return matrixPair[player.getHandValue()][dealer_val]  This returns the action
	 */
	public String executeActionStrategyPAIR(Hand player, int dealer_val){
		return matrixPair[player.getHandValue()][dealer_val]; 
	}
}