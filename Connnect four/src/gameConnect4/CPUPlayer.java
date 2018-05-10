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
		return 0;
	}

	public int minimax(BoardPosition currentBoard, int currentDepth, boolean isCPU, int alpha, int beta)
	{
		if (currentDepth == maxDepth) {
			return scoreBoard(currentBoard);
		}

		if (isCPU) {
			int bestVal = Integer.MIN_VALUE;
			boolean[] possibleMoves = currentBoard.possibleMoves();

			for (int slot = 0; slot < possibleMoves.length; slot++) {

				if (!possibleMoves[slot]) continue; //move is not a possible slot

				int val = currentBoard.checkMove(slot);
				if (val != 0) {
					if (val > 0) { //val is max int, winning move for cpu must take
						bestVal = val;
						break;
					}
				}

				else {
					val = minimax(currentBoard.makeMove(slot), currentDepth + 1, false, alpha, beta);
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
			boolean[] possibleMoves = currentBoard.possibleMoves();

			for (int slot = 0; slot < possibleMoves.length; slot++) {

				if (!possibleMoves[slot]) continue; //move is not a possible slot

				int val = currentBoard.checkMove(slot);
				if (val != 0) {
					if (val < 0) { //val is min int, winning move for player must take
						bestVal = val;
						break;
					}
				}

				else {
					val = minimax(currentBoard.makeMove(slot), currentDepth + 1, false, alpha, beta);
				}

				bestVal = Integer.min(val, bestVal); //min?
				beta = Integer.min(beta, bestVal);
				if (beta <= alpha)
					break;
			}
			return bestVal;
		}
	}

	//Heuristic method
	//ranks the current boardPosition, high int means in favor of CPU, low int means in not in favor of CPU
	//CPU wins returns max int possible
	//CPU loses returns min int possible
	public int scoreBoard(BoardPosition board) {

		boolean[][] winningPositions = board.findWinningPositions();
		boolean[][] losingPositions = board.findLosingPositions();

		//step 1: check for double ups (two kinds)
		//double ups on top of each other

		///double ups not on top of each other but at different slots but both bottom layer

		//step 2: check for even/odd threats
		//find number of odd threats(for cpu) that has no even threats(for player) below

		//find number of even threats(for player) with no odd threats(for cpu) below

		return 0;
	}

}
