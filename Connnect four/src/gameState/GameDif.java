package gameState;

import java.awt.Color;
import java.awt.Graphics;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameInput.MouseInput;

public class GameDif extends State{

	private GameButton easy;
	private GameButton med;
	private GameButton hard;
	
	private final int EASY = 1;
	private final int MED = 2;
	private final int HARD = 3;
	
	public GameDif()
	{
		easy = new GameButton(225 - 160, 200, 150, 50, new Color(250, 0, 0), new Color(200, 0, 0), "Easy", new Set(EASY), true);
		med = new GameButton(225, 200, 150, 50, new Color(250, 0, 0), new Color(200, 0, 0), "Medium", new Set(MED), true);
		hard = new GameButton(225 + 160, 200, 150, 50, new Color(250, 0, 0), new Color(200, 0, 0), "Hard", new Set(HARD), true);
	}
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(200,200,255));
		g.fillRect(0, 0, 600, 450);
		
		easy.render(g);
		med.render(g);
		hard.render(g);
	}

	@Override
	public void tick() {
		easy.tick(MouseInput.getX(), MouseInput.getY());
		med.tick(MouseInput.getX(), MouseInput.getY());
		hard.tick(MouseInput.getX(), MouseInput.getY());
	}
	private class Set implements gameAction
	{
		private int dif;
		
		public Set(int dif)
		{
			this.dif = dif;
		}
		
		public void action() {
			State.setState(new GameBoard1(dif));
			
		}
		
	}
}
