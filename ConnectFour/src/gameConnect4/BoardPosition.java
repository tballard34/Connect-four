package gameConnect4;

import java.util.ArrayList;

// THIS CLASS IS CURRENTLY UNUSED

public class BoardPosition {

	// bitMap? need to find way to implement a board efficiently

	public BoardPosition() {

	}

	// returns and boolean array with the possible moves moves as true and the
	// others false
	// returns empty array if the game is over
	public boolean[] possibleMoves() {

		boolean[] temp = new boolean[7];
		return null;
	}

	// checks a single move for a four in a row
	// returns max int if cpu wins, min int if cpu loses, 0 if nothing
	public int checkMove(int slot) {

		if (checkMoveForWin(slot))
			return Integer.MAX_VALUE;
		if (checkMoveForLose(slot))
			return Integer.MIN_VALUE;
		return 0;
	}

	// return true if cpu wins from move slot, false otherwise
	private boolean checkMoveForWin(int slot) {

		return false;
	}

	// return true if cpu wins from move slot, false otherwise
	private boolean checkMoveForLose(int slot) {

		return false;
	}

	// checks the whole board for four in a row
	// returns true if the game comes to an end, false if not
	public boolean checkFourInARow() {

		return false;
	}

	// returns the new board position after a move has been made
	public BoardPosition makeMove(int slot) {

		return null;
	}

	// finds the winning positions for cpu
	public boolean[][] findWinningPositions() {

		boolean[][] temp = new boolean[6][7];
		return temp;
	}

	// find the losing positions for cpu
	public boolean[][] findLosingPositions() {

		boolean[][] temp = new boolean[6][7];
		return temp;
	}

}
