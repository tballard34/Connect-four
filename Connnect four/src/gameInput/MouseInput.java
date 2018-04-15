package gameInput;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	
	private static final int NUM_BUT = 10;
	
	private static final boolean buttons[] = new boolean[NUM_BUT];
	private static final boolean lastButton[] = new boolean[NUM_BUT];
	
	private static int x;
	private static int y;
	
	public void mousePressed(MouseEvent e){
		System.out.println("Mouse Button: " + e.getButton());
		buttons[e.getButton()] = true;
	}
	
	public void mouseReleased(MouseEvent e){
		buttons[e.getButton()] = false;
	}
	
	public void mouseDragged(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		x = e.getX();
		y = e.getY();
	}
	
	public static void update(){
		for(int k = 0; k < buttons.length; k++){
			lastButton[k] = buttons[k];
		}
	}
	
	public static boolean isButtonClicked(int button){
		return buttons[button] == true;
	}
	
	public static int getX(){ return x; }
	public static int getY(){ return y; }
}
