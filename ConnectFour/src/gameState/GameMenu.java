package gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameInput.MouseInput;
/*
 * this is the state that is used when the game starts and in between games
 */
public class GameMenu extends State{
	
	private BufferedImage img;//used to add the connectLogo to the screen
	GameButton pvp;//game mode selection
	GameButton pvc;
	
	public GameMenu()
	{
		pvp = new GameButton(200, 125, 200, 75, new Color(250, 0,0), new Color(200, 0,0), "P v P", new StartAction(1), true);
		pvc = new GameButton(200, 250, 200, 75, new Color(250, 0,0), new Color(200,0,0), "P v CPU", new StartAction(2), true);
		try{
			img = ImageIO.read((new File("connect4logo.png")));//reads the png file and makes it to an image
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//draws the button and the logo
	public void render(Graphics g){
		g.setColor(new Color(200,200,255));
		g.fillRect(0, 0, 600, 450);
		g.setColor(Color.BLACK);
		g.drawImage(img, 85, 0, null);
		pvp.render(g);
		pvc.render(g);
	}
	
	//updates the buttons
	public void tick()
	{	
		pvp.tick(MouseInput.getX(), MouseInput.getY());
		pvc.tick(MouseInput.getX(), MouseInput.getY());
	}
	
	//controls what state the user is sent to
	private class StartAction implements gameAction
	{
		private int id;//1 is PvP and 2 is PvCPU
		
		public StartAction(int id)
		{
			this.id = id;
		}
		
		public void action() {
			if(id == 1)
				State.setState(new GameBoard());//PvP game
			else if(id == 2)
				State.setState(new GameDif());//state that selects difficulty
		}
		
	}
}
