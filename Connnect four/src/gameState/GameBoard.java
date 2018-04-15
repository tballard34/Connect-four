package gameState;

import java.awt.Color;
import java.awt.Graphics;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameConnect4.Connect4;
import gameInput.MouseInput;

public class GameBoard extends State{
	
	private GameButton[] buttons = new GameButton[7];
	private GameButton undo;
	private Connect4 game;
	private String winner;
	private long lastTime = 0;
	
	public GameBoard()
	{
		for(int i = 0; i < buttons.length; i++)
			buttons[i] = new GameButton(i * 85 + 2, 0, 85,25,new Color(150,150,255), new Color(100,100, 200), "" + (i + 1), new Handeler(i), false);
		
		undo = new GameButton(250, 410, 100, 40, new Color(150,150,255), new Color(100,100, 200), "Undo", new Undo(), false);
		
		game = new Connect4();
	}
	
	public void render(Graphics g) {
		//sets a blue background
		g.setColor(new Color(0,0,255));
		g.fillRect(0,0,600,450);
		
		//draws the buttons
		for(int i = 0; i < buttons.length;i++)
			buttons[i].render(g);
		
		//draws the slots on the board
		Color[][] map = game.getColor();
		for(int r = 0; r < map.length; r++)
		{
			for(int c = 0; c < map[r].length; c++)
			{
				g.setColor(map[r][c]);
				g.fillOval(r * 85 + 5, c * 66 + 26, 50, 50);
			}
		}	
		
		//draws the bottom bar
		g.setColor(Color.GRAY);
		g.fillRect(0, 410, 600, 40);
		
		//draws the current player state
		g.setColor(Color.BLACK);
		if(game.getCurrent())
			g.drawString("Current: Red", 50, 435);
		else
			g.drawString("Current: Black", 50, 435);
		
		//draw the undo button
		undo.render(g);
		
		// draws the game over box
		if(game.getGameOver())
		{
			g.setColor(new Color(50,50,50));
			g.fillRect(245, 170, 110, 60);
			
			g.setColor(Color.WHITE);
			g.fillRect(250, 175, 100, 50);
			
			g.setColor(Color.BLACK);
			g.drawString("Game Over!!!", 270, 200);
		}
		
	}

	public void tick() {
		for(int i = 0; i < buttons.length;i++)
			buttons[i].tick(MouseInput.getX(), MouseInput.getY());
		
		undo.tick(MouseInput.getX(), MouseInput.getY());
		
		if(game.getGameOver() && lastTime == 0)
		{
			if(game.getWinner() == 1)
				winner = "Player 1";
			else if(game.getWinner() == 2)
				winner = "Player 2";
			else
				winner = "Tie, on one ";
			
			lastTime = System.currentTimeMillis();
		}else if(game.getGameOver() && System.currentTimeMillis() - lastTime > 3000)
			State.setState(new GameWin(winner));
	}
	
	private class Handeler implements gameAction
	{
		private int col;
		
		public Handeler(int col)
		{
			this.col = col;
		}
		
		public void action() {
			if(!game.getGameOver())
			{
				game.makeMove(col);
				game.checkWin(col);
			}
		}
	}
	
	private class Undo implements gameAction
	{

		@Override
		public void action() {
			game.undo();
			
		}
		
	}
}
