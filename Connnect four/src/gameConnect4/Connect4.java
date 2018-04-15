package gameConnect4;

import java.awt.Color;
import java.awt.Point;
import java.util.Stack;

public class Connect4 {
	private int board[][] = new int[7][6];
	private int[] column;
	private int slots;
	private int winner;
	private boolean current;//true is player one, false is player two
	private Color[][] color;
	private boolean won;
	private Stack<Point> moves;
	
	public Connect4()
	{
		color = new Color[7][6];
		column = new int[7];
		
		for(int r = 0; r < board.length; r++)
			for(int c = 0; c < board[r].length; c++)
			{
				board[r][c] = 0;
				color[r][c] = Color.WHITE;
				column[r] = 5;
			}
		
		slots = 42;
		winner = 0;//0 is no one, 1 is player one, 2 is player two
		current = true;
		won = false;
		moves = new Stack<Point>();
	}
	
	public boolean makeMove(int c)//returns false if the column is full
	{
		if(current)//player one
		{
			if(column[c] == -1)
				return false;
			
			board[c][column[c]] = 1;
			color[c][column[c]] = Color.RED;
			
			moves.push(new Point(c, column[c]));
			
			column[c] = column[c] - 1;
			current = !current;
			slots--;
			return true;
		}else//player two
		{
			if(column[c] == -1)
				return false;
			
			board[c][column[c]] = 2;
			color[c][column[c]] = Color.BLACK;
			
			moves.push(new Point(c, column[c]));
			
			column[c] = column[c] - 1;
			current = !current;
			slots--;
			return true;
		}
	}
	
	public void checkWin(int c)
	{	
		int player = 1;
		if(current)
			player = 2;
		
		if(checkDown(c, player) > 3)//add one so that it includes the check point
		{
			winner = player;
			won =true;
		}else if(checkHorizontal(c, player) > 3)//adds the two lateral directions so the you can have placement in the middle
		{
			winner = player;
			won = true;
		}else if(checkDiagonalTB(c, player) > 3)
		{
			winner = player;
			won = true;
		}else if(checkDiagonalBT(c, player) > 3)
		{
			winner = player;
			won = true;
		}
		
		if(slots == 0)
		{
			winner = 0;
			won = true;
		}
	}
	
	private int checkDown(int c, int player)
	{
		int countDown = 0;
		int y = column[c] + 1;//sets it back to where the last piece was played
		
		for(int r  = y + 1; r < y + 4; r++)
			if(r < board[c].length && board[c][r] != player)
				break;//ends the loop because there is a piece of the other player
			else if(r < board[c].length)
				countDown++;
		
		return countDown + 1;
	}
	
	private int checkHorizontal(int c, int player)
	{
		int countLeft = 0;
		int countRight = 0;
		int y = column[c] + 1;//sets it back to where the last piece was played
		
		for(int r = c + 1; r < c + 4; r++)
			if(r < board.length && board[r][y] != player)
				break;//ends the loop because there is a piece of the other player
			else if(r < board.length)
				countRight++;
		
		for(int r = c - 1; r > c - 4; r--)
			if(r >= 0 && board[r][y] != player)
				break;//ends the loop because there is a piece of the other player
			else if(r >= 0)
				countLeft++;
		
		return countLeft + countRight + 1;
	}
	
	private int checkDiagonalTB(int c, int player)
	{
		int countBR = 0;
		int countTL = 0;
		
		int y = column[c] + 1;
		
		for(int i = 1; i < 4; i++)
			if(c + i < board.length && y + i < board[c].length && board[c + i][y + i] == player)
				countBR++;
			else
				break;
		
		for(int i = 1; i < 4; i++)
			if(c - i >= 0 && y - i >= 0 && board[c - i][y - i] == player)
				countTL++;
			else
				break;
		
		return countBR + countTL + 1;
	}
	
	private int checkDiagonalBT(int c, int player)
	{
		int countTR = 0;
		int countBL = 0;
		int y = column[c] + 1;//sets it back to where the last piece was played
		
		for(int i = 1; i < 4; i++)
			if(c-i >= 0 && y + i < board[c].length && board[c - i][y + i] == player)
				countBL++;
			else
				break;
		
		for(int i = 1; i < 4; i++)
			if(c+i < board.length && y - i >= 0 && board[c + i][y - i] == player)
				countTR++;
			else
				break;
		
		return countBL + countTR + 1;
	}
	
	public void undo()
	{
		if(moves.isEmpty() || won)
			return;
		
		Point temp = moves.pop();
		board[(int)temp.getX()][(int)temp.getY()] = 0;
		color[(int)temp.getX()][(int)temp.getY()] = Color.WHITE;
		
		current = !current;
		slots++;
		column[(int)temp.getX()] = (int)temp.getY();
	}
	
	public Color[][] getColor(){
		return color;
	}
	
	public boolean getCurrent(){	return current;	}
	public int[][] getBoard()  {	return board; 	}
	public int getWinner()     {	return winner;	}
	public boolean getGameOver(){	return won;		}
	public int[] getColumn(){		return column; 	}
}

