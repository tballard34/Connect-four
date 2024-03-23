package gameState;

import java.awt.Graphics;

public abstract class State {
	private static State cs = null;

	public static void setState(State state) {
		cs = state;
	}

	public static State getState() {
		return cs;
	}

	public abstract void render(Graphics g);

	public abstract void tick();
}
