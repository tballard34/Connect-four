package gameState;

import java.awt.Color;
import java.awt.Graphics;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameInput.MouseInput;

public class GameMenu extends State{
	
	GameButton pvp;
	GameButton pvc;
	
	public GameMenu()
	{
		pvp = new GameButton(200, 100, 200, 100, new Color(250, 0,0), new Color(200, 0,0), "P vs. P", new StartAction(1), true);
		pvc = new GameButton(200, 250, 200, 100, new Color(250, 0,0), new Color(200,0,0), "Human vs. CPU", new StartAction(2), true);
	}
	
	public void render(Graphics g){
		g.setColor(new Color(200,200,255));
		g.fillRect(0, 0, 600, 450);
		g.setColor(Color.BLACK);
		g.drawString("Connect 4", 280, 50);
		pvp.render(g);
		pvc.render(g);
	}
	
	public void tick()
	{	
		pvp.tick(MouseInput.getX(), MouseInput.getY());
		pvc.tick(MouseInput.getX(), MouseInput.getY());
	}
	
	private class StartAction implements gameAction
	{
		private int id;
		
		public StartAction(int id)
		{
			this.id = id;
		}
		
		public void action() {
			if(id == 1)
				State.setState(new GameBoard());
			else if(id == 2)
				State.setState(new GameDif());
		}
		
	}
}
