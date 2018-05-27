package gameConnect4;

public class CPUPlayer{

	private int board[][];//this board is linked to the game on so don't change it. if you do it will affect the game 7x6 
	private int[] column;
	private Connect4 game;
	private int maxDepth;
	private final int LOSING_MOVE = Integer.MIN_VALUE + 1;
	private final int WINNING_MOVE = Integer.MAX_VALUE - 1;

	public CPUPlayer(Connect4 game, int maxDepth)
	{
		this.game = game;
		this.board = this.game.getBoard();
		this.maxDepth = maxDepth;
		column =  game.getColumn();
	}

	//the logic for the CPU player is written below
	//return the column it should place piece 0 - 6
	public int makeMove()
	{
		long red = boardToLong(1);
		long black = boardToLong(2);
		int[] moves = new int[7];
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		for(int slot = 0; slot < 7; slot++)
		{
			if(column[slot] < 0) {
				moves[slot] = Integer.MIN_VALUE;
				continue;
			}
    	  
			int indexMove = column[slot]*7 + slot;

			String newMove = "000000000000000000000000000000000000000000";
			newMove = newMove.substring(0,indexMove)+"1" + newMove.substring(indexMove + 1);
			long lMove = Long.parseLong(newMove,2);
			
			int val = checkMove((red|lMove), black, indexMove ,moves[slot]);
			//int bestVal = Integer.MIN_VALUE;
    	  
			column[slot] = column[slot] - 1;
    	  
			if (val != 0){
				if (val > 0) { //val is max int, winning move for cpu must take
					moves[slot] = WINNING_MOVE;
					column[slot] = column[slot] + 1;
					//bestVal = WINNING_MOVE;
				}
			}else if(maxDepth == 1)
				moves[slot] = scoreBoard((red|lMove), black, column);
			else{
				moves[slot] = minimax((red|lMove), black,column[0],column[1],column[2],column[3],column[4],column[5],column[6] , maxDepth - 1, false, alpha, beta);
			}
			column[slot] = column[slot] + 1;
      }
		
		for(int i = 0; i < moves.length; i++)
			System.out.println(moves[i]);
		int index = maxIndex(moves);
		System.out.println("index" + index);
      return index;
	}
//****************************************minimax method*************************************************
	public int minimax(long red, long black,int c, int c1, int c2,int c3, int c4, int c5, int c6, int currentDepth, boolean isCPU, int alpha, int beta)
	{
		
		int[] moves = {c, c1, c2, c3, c4, c5 ,c6 };
		
		if (currentDepth == 1) {
			return scoreBoard(red, black, moves);
		}
		
		if (isCPU) {
			int bestVal = Integer.MIN_VALUE;

			for (int slot = 0; slot < 7; slot++) {

				if (moves[slot] < 0) continue; //move is not a possible slot
				
				int indexMove = moves[slot]*7 + slot;
				
				String newMove = "000000000000000000000000000000000000000000";
				newMove = newMove.substring(0,indexMove)+"1" + newMove.substring(indexMove + 1);
				long lMove = Long.parseLong(newMove,2);

				int val = checkMove((red|lMove), black, indexMove ,moves[slot]);
				//System.out.println("red" + val);
				moves[slot] = moves[slot] - 1;
				
				if (val != 0) {
					if (val > 0) { //val is max int, winning move for cpu must take
						bestVal = val;
						break;
					}
				}

				else {
					val = minimax((red|lMove), black,moves[0],moves[1],moves[2],moves[3],moves[4],moves[5],moves[6] , currentDepth - 1, false, alpha, beta);
				}

				bestVal = Integer.max(val, bestVal);
				/*alpha = Integer.max(alpha, bestVal);
				if (beta <= alpha)
					break;*/
			}
			return bestVal;
		}

		else {
			int bestVal = Integer.MAX_VALUE;

			for (int slot = 0; slot < 7; slot++) {

				if (moves[slot] < 0) continue; //move is not a possible slot
				
				int indexMove = moves[slot]*7 + slot;
				//System.out.println(moves[slot]);
				String newMove = "000000000000000000000000000000000000000000";
				newMove = newMove.substring(0,indexMove)+"1" + newMove.substring(indexMove + 1);
				long lMove = Long.parseLong(newMove,2);
				
				int val = checkMove(red, (black|lMove), indexMove, moves[slot]);
				//System.out.println("black" + val);
				moves[slot] = moves[slot] - 1;
				
				if (val != 0) {
					if (val < 0) { //val is min int, winning move for player must take
						bestVal = val;
						break;
					}
				}

				else {
					val = minimax(red, (black|lMove),moves[0],moves[1],moves[2],moves[3],moves[4],moves[5],moves[6] , currentDepth - 1, true, alpha, beta);
				}
				
				moves[slot] = moves[slot] + 1;
				
				bestVal = Integer.min(val, bestVal); //min?
				/*beta = Integer.min(beta, bestVal);
				if (beta <= alpha)
					break;*/
			}
			//System.out.println(bestVal);
			return bestVal;
		}
	}
	//******************************************************************************************************************************************
	//used in the makeMove method to pick the best move
   //by picking the integer in the array with the highest value and returning its index
	private int maxIndex(int [] moves)
	{
		int max = 0;
		for(int i = 1; i < moves.length; i++)
			if(moves[i] > moves[max])
				max = i;
		return max;
	}
	//*********************************************************************************************************************************************
	/*
   *this is used to check if the move that is trying to be made is a win and if it is then it returns
   *Integer.MAX_VALUE - 1 for the cpu and Integer.MIN_VALUE + 1 for the human.
   */
	
	private int checkMove(long red, long black, int indexMove, int slot)
	{
		if(moveWon(red, black, indexMove, slot))//if there has been a win will check who then return there value
			if(longToString(red).charAt(indexMove) == '1')
				return WINNING_MOVE;//cpu
			else
				return LOSING_MOVE;//person
		return 0;
	}
	/**********************************************************************************************************************/
   //Heuristic method
	//ranks the current boardPosition, high int means in favor of CPU, low int means in not in favor of CPU
	//CPU wins returns max int possible
	//CPU loses returns min int possible
	private boolean moveWon(long red, long black, int indexMove, int slot)
	{
		char player = '1';
		String str;

		if(longToString(red).charAt(indexMove) == '1') {//sets up how its being looked at for a win
			//System.out.println("red");
			str = longToString(red);
		}
		else{
			//System.out.println("black");
			str = longToString(black);
		}
		
		//System.out.println(str);
		
		
      
      if(checkDown(str, indexMove, slot))
         return true;
		else if(checkTopLeftToBottomRight(str, indexMove, slot))
         return true;
      else if(checkBottomLeftToTopRight(str, indexMove, slot))
         return true;
      else if(checkHorizontal(str, indexMove, slot))
         return true;

		return false;
	}
	
	public int scoreBoard(long red, long black, int[] columnsAvailable) {

		//create a 2d array with a 1 in positions that player 1 wins, 2 where player 2 wins, and 3 both players win
		//doesn't count vertical 4 in a rows
		int[][] winningPositions = findWinningSlots(red, black, columnsAvailable);
		
		//step 1: check for double ups (two kinds)
		//double ups on top of each other
		for (int r = 0; r < 7; r++) {
			if (columnsAvailable[r] == -1) continue; //no slot
			for (int c = columnsAvailable[r]; c < columnsAvailable[r] + 3 && c < 7; c++) {
				int wPVal = winningPositions[r][c];
				if (wPVal != 0 || wPVal != 3) { //if there is a clear winner at the spot
					if (wPVal == 1 && (winningPositions[r][c + 1] == 1 || winningPositions[r][c + 1] == 3)) { //if their is a double up in favor of cpu
						return WINNING_MOVE;
					}
					else if (wPVal == 2 && (winningPositions[r][c + 1] == 2 || winningPositions[r][c + 1] == 3)) {
						return LOSING_MOVE;
					}
				}
				
			}
		}
		
		int score = 0;//score of the current board
		
		//step 2: check for even/odd threats
		//find number of odd threats(for cpu) that has no even threats(for player) below
		int numOddThreatsCPU = 0;
		int numOddThreatsPlayer = 0;
		int numEvenThreatsPlayer = 0;
		for (int r = 0; r < 7; r++) {
			if (columnsAvailable[r] == -1) continue; //no slot
			for (int c = columnsAvailable[r] + 1; c < 6; c++) { //starts on not directly playable slots
				
				if (r % 2 == 1) { //even row
					if (winningPositions[r][c] == 1 || winningPositions[r][c] == 3) { //even threat
						numEvenThreatsPlayer++;
						break;
					}
				}
				else { //odd row
					if (winningPositions[r][c] == 2 || winningPositions[r][c] == 3) { //odd threat
						numOddThreatsCPU++;
						break;
					}
					if (winningPositions[r][c] == 2) {
						numOddThreatsPlayer++;
					}
				}
			}
		}
		if (numOddThreatsPlayer == 1 && numOddThreatsCPU == 1) {
			score += 100;
		}
		else if (numOddThreatsCPU != 0) {
			score += 1000;
		}
		else if (numEvenThreatsPlayer >= 2) {
			score -= 5000;
		}
		else if (numEvenThreatsPlayer == 1) {
			score += 500;
		}
		
		//step 3: check middle column
		for (int c = 0; c < 6; c++) {
			//if their is a piece of cpu

		}
		
		return score;
	}
	
	//converts the board in to a bitboard for each player by making the board in to a long value of base two
	public long boardToLong(int player)
	{
		String s = "";
		for(int i = 0; i < 42; i++)
		{
			if(board[i%7][i/7] == player)
				s += "1";
			else 
				s += "0";
		}
		return Long.parseLong(s,2);//makes the long base two
	}
	
	public String longToString(long l)
	{
		String s1 = "";
		
		for(int i = 41; i >= 0; i--)
		{
			double temp = Math.pow(2, i);
			if(l >= temp)
			{
				l -= temp;
				s1 += "1";
			}
			else
				s1 += "0";
		}
		return s1;
	}
	
	private int[][] findWinningSlots(long red, long black, int [] columnsAvalable) {
		int [][] map = new int[7][6];
      for(int i = 0; i < 42; i++)
      {
         map[i/7][i%6] = 0;
      }
      
      map = findWinningSlotsHelper(map, longToString(red), columnsAvalable, 1);
      map = findWinningSlotsHelper(map, longToString(black), columnsAvalable, 2);
      return map;
	}
   
   private int[][] findWinningSlotsHelper(int [][] map, String playerString, int[] columnsAvalable, int player)
   {
      for(int c = 0; c < columnsAvalable.length; c++)
         for(int r = columnsAvalable[c]; r >= 0; r--)
         {
            int moveIndex = r*7 + c;
            if(checkTopLeftToBottomRight(playerString, moveIndex, r))
            {
               map[c][r] = map[c][r] + player;
               continue;
            }else if(checkBottomLeftToTopRight(playerString, moveIndex, r))
            {
               map[c][r] = map[c][r] + player;
               continue;
            }else if(checkHorizontal(playerString, moveIndex, r))
            {
               map[c][r] = map[c][r] + player;
               continue;
            }
         }
      return map;
   }
   
   private boolean checkBottomLeftToTopRight(String str, int indexMove, int slot)
   {
      char player = '1';
      int count = 0;
		for(int i = 1; i < 4; i++)//checks diagonal bottom left to top right
		{
			if(indexMove - 6*i > 0 && indexMove - 6*i < (slot - i + 1) * 7 && str.charAt(indexMove - 6*i) == player) {
				count++;
			}else
				break;
		}
		for(int i = 1; i < 4; i++)//checks diagonal top right to bottom left
		{
			if(indexMove + 6*i < str.length() && indexMove + 6 * i < (slot + i + 1) * 7 && str.charAt(indexMove + 6*i) == player) {
				count++;
			}else
				break;
		}
		//System.out.println(count);
		if(count == 3)
			return true;
      return false;
   }
   
   private boolean checkTopLeftToBottomRight(String str,int indexMove,int slot)
   {
      char player = '1';
      int count = 0;
		for(int i = 1; i < 4; i++)//checks diagonal bottom right to top left
		{
			if(indexMove - 8*i > 0 && indexMove - 8*i >= (slot - i) * 7 && str.charAt(indexMove - 8*i) == player) {
				count++;
			}else
				break;
		}
		for(int i = 1; i < 4; i++)//checks diagonal top left to bottom right
		{
			if(indexMove + 8*i < str.length() && indexMove + 8*i < (slot + i + 1) * 7 && str.charAt(indexMove + 8*i) == player) {
				count++;
			}else
				break;
		}
		//System.out.println(count);
		if(count == 3)
			return true;
      return false;
   }
   
   private boolean checkHorizontal(String str, int indexMove, int slot)
   {
      char player = '1';
      int count = 0;
		for(int i = 1; i < 4; i++)//checks left
		{
			if(indexMove - i > (slot)*7-1 && str.charAt(indexMove - i) == player) {
				count++;
			}else
				break;
		}
		for(int i = 1; i < 4; i++)//checks right
		{
			if(indexMove + i < (slot+1)*7 && str.charAt(indexMove + i) == player) {
				count++;
			}else
				break;
		}
		//System.out.println(count);
		if(count == 3)
			return true;
      return false;
   }
   
   private boolean checkDown(String str, int indexMove, int slot)
   {
      char player = '1';
      int count = 0;//hold the count value of the winning pieces
		for(int i = 1; i < 4; i++)//checks down
		{
			if(indexMove + 7*i < str.length() && str.charAt(indexMove + 7*i) == player) {
				count++;
			}else
				break;
				
		}
		if(count == 3)
			return true;
      return false;
   }
	
	private boolean isPiece(long pLong, int r, int c)
	{
		int indexMove = r * 7 + c;
		if((pLong >> indexMove) % 2 == 1)
			return true;
		
		return false;
	}
}
