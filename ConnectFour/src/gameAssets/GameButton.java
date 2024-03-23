package gameAssets;

import java.awt.Color;
import java.awt.Graphics;

import gameInput.MouseInput;

public class GameButton {
	
	private int bx, by;
	private int tx, ty;
	private Color cBase;
	private Color cHover;
	private Color current;
	private String text;
	private int width;
	private int height;
	private gameAction event;
	private boolean boarder;
	
	public GameButton(int x, int y,int width, int height, Color base, Color hover, String text, gameAction event, boolean boarder)
	{
		bx = x;//Coordinates for the top left corner of the button
		by = y;//
		cBase = base;//color when the mouse is not on it
		cHover = hover;//color when the mouse is on it
		this.text = text;
		this.width = width;
		this.height = height;
		this.event = event;//the event that will happen when the button is clicked
		this.boarder = boarder;//if they want a boarder its true
		
		ty = by + height/2 + 3;//centers the text on the button in the vertical position
		tx = bx + width / 2 - text.length() * 3;//makes the text centered in the lateral position 
		current = cBase;//sets the current color to the base color
	}
	
	public void tick(int mx, int my)
	{
		boolean bounded = inBounds(mx, my);
      
      if(bounded)//if the mouse is in bounds it changes the color to hover
			current = cHover;
		else
			current = cBase;
		
		if(bounded && MouseInput.released(1))//if it is clicked and inbounds and its the first time clicked it will run the event
			event.action();//does the action of the event
	}
	
	public void render(Graphics g)
	{
		if(boarder)//if they want a boarder it draws it first
		{
			g.setColor(Color.BLACK);
			g.fillRect(bx - 5, by - 5, width + 10, height + 10);
		}
		g.setColor(current);//set the button background
		g.fillRect(bx, by, width, height);
		g.setColor(Color.BLACK);
		g.drawString(text, tx, ty);
	}
	
	public String getText()
	{
		return text;
	}
	
	public boolean inBounds(int mx, int my)
	{
		return bx < mx && mx < bx + width && by < my && my < by + height;//checks if its in bounds 
	}
	
}
