package gameConnect4;

public class CPUPlayer{
	
	private int board[][];//this board is linked to the game on so don't change it. if you do it will affect the game 7x6 
	private int[] column;
	private Connect4 game;
	
	public CPUPlayer(Connect4 game)
	{
		this.game = game;
		this.board = this.game.getBoard();
		column =  game.getColumn();
	}
	
	//the logic for the CPU player is written below
	//return the column it should place piece 0 - 6
	 public int makeMove()
	 {
		 return 0;
	 }
}