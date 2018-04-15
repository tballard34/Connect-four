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
	private boolean clicked;
	private gameAction event;
	private boolean boarder;
	
	public GameButton(int x, int y,int width, int height, Color base, Color hover, String text, gameAction event, boolean boarder)
	{
		bx = x;
		by = y;
		cBase = base;
		cHover = hover;
		this.text = text;
		this.width = width;
		this.height = height;
		this.event = event;
		this.boarder = boarder;
		
		ty = by + height/2 + 3;
		tx = bx + width / 2 - text.length() * 3;
		current = cBase;
		clicked = false;
	}
	
	public void tick(int mx, int my)
	{
		if(inBounds(mx, my))
			current = cHover;
		else
			current = cBase;
		
		if(MouseInput.isButtonClicked(1) && inBounds(mx, my) && !clicked)
		{
			clicked = true;
			event.action();
		}
		
		if(!MouseInput.isButtonClicked(1) && clicked)
			clicked = false;
		
	}
	
	public void render(Graphics g)
	{
		if(boarder)
		{
			g.setColor(Color.BLACK);
			g.fillRect(bx - 5, by - 5, width + 10, height + 10);
		}
		g.setColor(current);
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
		return bx < mx && mx < bx + width && by < my && my < by + height;
	}
	
}
