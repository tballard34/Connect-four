package gameConnect4;

public class CPUPlayer{

	private int board[][];//this board is linked to the game on so don't change it. if you do it will affect the game 7x6 
	private int[] column;
	private Connect4 game;
	private int maxDepth;

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
		
		for(int slot = 0; slot < 7; slot++)
		{
			if(column[slot] < 0) {
				moves[slot] = -1000;
				continue;
			}
    	  
			int indexMove = column[slot]*7 + slot;

			String newMove = "000000000000000000000000000000000000000000";
			newMove = newMove.substring(0,indexMove)+"1" + newMove.substring(indexMove + 1);
			long lMove = Long.parseLong(newMove,2);
			
			int val = checkMove((red|lMove), black, indexMove ,moves[slot]);
			//int bestVal = -1000;--------------------------------why do we need this
    	  
			column[slot] = column[slot] - 1;
    	  
			if (val != 0){
				if (val > 0) { //val is max int, winning move for cpu must take
					moves[slot] = 100;
					column[slot] = column[slot] + 1;
					break;
				}
			}else if(maxDepth == 1)
				moves[slot] = scoreBoard((red|lMove), black);
			else{
				val = minimax((red|lMove), black,column[0],column[1],column[2],column[3],column[4],column[5],column[6] , maxDepth - 1, false, -1000, 1000);
			}
			column[slot] = column[slot] + 1;
      }
      return maxIndex(moves);
	}
//****************************************minimax method*************************************************
	public int minimax(long red, long black,int c, int c1, int c2,int c3, int c4, int c5, int c6, int currentDepth, boolean isCPU, int alpha, int beta)
	{
		if (currentDepth == 1) {
			return scoreBoard(red, black);
		}
		
		int[] moves = {c, c1, c2, c3, c4, c5 ,c6 };
		
		if (isCPU) {
			int bestVal = Integer.MIN_VALUE;

			for (int slot = 0; slot < 7; slot++) {

				if (moves[slot] < 0) continue; //move is not a possible slot
				
				int indexMove = moves[slot]*7 + slot;
				
				String newMove = "000000000000000000000000000000000000000000";
				newMove = newMove.substring(0,indexMove)+"1" + newMove.substring(indexMove + 1);
				long lMove = Long.parseLong(newMove,2);

				int val = checkMove((red|lMove), black, indexMove ,moves[slot]);

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
				alpha = Integer.max(alpha, bestVal);
				if (beta <= alpha)
					break;
			}
			return bestVal;
		}

		else {
			int bestVal = Integer.MAX_VALUE;

			for (int slot = 0; slot < 7; slot++) {

				if (moves[slot] < 0) continue; //move is not a possible slot
				
				int indexMove = moves[slot]*7 + slot;
				
				String newMove = "000000000000000000000000000000000000000000";
				newMove = newMove.substring(0,indexMove)+"1" + newMove.substring(indexMove + 1);
				long lMove = Long.parseLong(newMove,2);
				
				int val = checkMove(red, (black|lMove), indexMove, moves[slot]);
				System.out.println(val);
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
				beta = Integer.min(beta, bestVal);
				if (beta <= alpha)
					break;
			}
			return bestVal;
		}
	}
	//******************************************************************************************************************************************
	//used in the makeMove method to pick the best move
	private int maxIndex(int [] moves)
	{
		int max = 0;
		for(int i = 1; i < moves.length; i++)
			if(moves[i] > moves[i-1])
				max = i;
		return max;
	}
	//*********************************************************************************************************************************************
	//Heuristic method
	//ranks the current boardPosition, high int means in favor of CPU, low int means in not in favor of CPU
	//CPU wins returns max int possible
	//CPU loses returns min int possible
	
	private int checkMove(long red, long black, int indexMove, int slot)
	{
		if(moveWon(red, black, indexMove, slot))//if there has been a win will check who then return there value
			if(longToString(red).charAt(indexMove) == '1')
				return 100;//cpu
			else
				return -100;//person
		return 0;
	}
	
	private boolean moveWon(long red, long black, int indexMove, int slot)
	{
		char player = '1';
		String str;

		if(longToString(red).charAt(indexMove) == '1') {//sets up how is being looked at for a win
			System.out.println("red");
			str = longToString(red);
		}
		else{
			System.out.println("black");
			str = longToString(black);
		}
		
		int count = 0;//hold the count value of the winning pieces
		for(int i = 1; i < 4; i++)//checks down
		{
			if(indexMove - 7*i > 0 && str.charAt(indexMove - 7*i) == player) {
				count++;
			}else
				break;
				
		}

		if(count + 1 == 4)
			return true;
      
      
		count = 0;
		for(int i = 1; i < 4; i++)//checks diagonal bottom left to top right
		{
			if(indexMove - 6*i > 0 && str.charAt(indexMove - 6*i) == player) {
				count++;
			}else
				break;
		}
		for(int i = 1; i < 4; i++)//checks diagonal top right to bottom left
		{
			if(indexMove + 6*i < str.length() && str.charAt(indexMove + 6*i) == player) {
				count++;
			}else
				break;
		}
		//System.out.println(count);
		if(count+1 == 4)
			return true;
      
      
		count = 0;
		for(int i = 1; i < 4; i++)//checks diagonal bottom right to top left
		{
			if(indexMove - 8*i > 0 && str.charAt(indexMove - 8*i) == player) {
				count++;
			}else
				break;
		}
		for(int i = 1; i < 4; i++)//checks diagonal top left to bottom right
		{
			if(indexMove + 8*i < str.length() && str.charAt(indexMove + 8*i) == player) {
				count++;
			}else
				break;
		}

		if(count + 1 == 4)
			return true;
         
         
		count = 0;
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

		if(count + 1== 4)
			return true;
      
      
      
		return false;
	}
	
	public int scoreBoard(long red, long black) {

		//step 1: check for double ups (two kinds)
		//double ups on top of each other

		///double ups not on top of each other but at different slots but both bottom layer

		//step 2: check for even/odd threats
		//find number of odd threats(for cpu) that has no even threats(for player) below

		//find number of even threats(for player) with no odd threats(for cpu) below

		return 4;
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

}
