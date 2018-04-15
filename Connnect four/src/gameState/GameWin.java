package gameState;

import java.awt.Color;
import java.awt.Graphics;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameInput.MouseInput;

public class GameWin extends State{
	
	private String winner;
	private GameButton button;
	
	public GameWin(String winner)
	{
		this.winner = winner;
		button = new GameButton(225, 200, 150, 50, new Color(250, 0, 0), new Color(200, 0, 0), "Back to Main Menu", new Return(), true);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawString(winner +" Wins the game!", 230, 300);
		button.render(g);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		button.tick(MouseInput.getX(), MouseInput.getY());
	}
	
	private class Return implements gameAction
	{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			State.setState(new GameMenu());
		}
		
	}

}
