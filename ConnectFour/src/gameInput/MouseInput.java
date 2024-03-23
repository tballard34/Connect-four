package gameInput;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

	private static final int NUM_BUT = 10;// this is used to set the max amount of buttonls that the mouse can use

	private static final boolean buttons[] = new boolean[NUM_BUT];// if they had been pushed it makes this true
	private static final boolean releasedButton[] = new boolean[NUM_BUT];

	private static int x;// x position
	private static int y;// y position

	public void mousePressed(MouseEvent e) {// sets the pushed button to true
		System.out.println("Mouse Button: " + e.getButton());
		buttons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;// sets it to false because its not being clicked
		releasedButton[e.getButton()] = true;
	}

	public void mouseMoved(MouseEvent e) {// keeps track of where the mouse is at all times
		x = e.getX();
		y = e.getY();
	}

	public static boolean released(int button) {
		boolean btn = releasedButton[button];
		releasedButton[button] = false;
		return btn;
	}

	public static boolean isButtonClicked(int button) {
		return buttons[button] == true;
	}

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}
}
