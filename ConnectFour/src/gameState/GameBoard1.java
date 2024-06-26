package gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameConnect4.CPUPlayer;
import gameConnect4.Connect4;
import gameInput.MouseInput;

public class GameBoard1 extends State {

	private GameButton[] buttons = new GameButton[7];
	private GameButton undo;
	private Connect4 game;
	private CPUPlayer cpu;
	private long lastTime = 0;
	private String winner;
	private BufferedImage img;

	public GameBoard1(int dif) {
		for (int i = 0; i < buttons.length; i++)
			buttons[i] = new GameButton(i * 85 + 2, 0, 85, 25, new Color(150, 150, 255), new Color(100, 100, 200),
					"" + (i + 1), new Handeler(i), false);

		undo = new GameButton(250, 410, 100, 40, new Color(150, 150, 255), new Color(100, 100, 200), "Undo", new Undo(),
				false);

		game = new Connect4();
		cpu = new CPUPlayer(game, dif);
		try {
			img = ImageIO.read((new File("ConnectFour/src/icons/Connect4.png")));// reads the png file and makes it to an image
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {

		Color[][] map = game.getColor();// gets the color map from the connect4 game object
		for (int r = 0; r < map.length; r++) {
			for (int c = 0; c < map[r].length; c++) {
				g.setColor(map[r][c]);
				g.fillOval(r * 84 + 19, c * 62 + 43, 50, 50);
			}
		}

		g.drawImage(img, 0, 0, null);// draws the board image to the screen after the buttons to

		g.setColor(Color.GRAY);// draws the gray bar at the bottom
		g.fillRect(0, 410, 600, 40);

		for (int i = 0; i < buttons.length; i++)// draws the buttons at the top of the screen
			buttons[i].render(g);

		undo.render(g);// draws the undo button

		if (game.getGameOver()) {
			g.setColor(new Color(50, 50, 50));
			g.fillRect(245, 170, 110, 60);

			g.setColor(Color.WHITE);
			g.fillRect(250, 175, 100, 50);

			g.setColor(Color.BLACK);
			g.drawString("Game Over!!!", 270, 200);
		}
	}

	public void tick() {
		for (int i = 0; i < buttons.length; i++)
			buttons[i].tick(MouseInput.getX(), MouseInput.getY());

		undo.tick(MouseInput.getX(), MouseInput.getY());

		if (game.getCurrent() && !game.getGameOver())// getCurrent is false when it's the seconds player's turn
		{
			int col = cpu.makeMove();

			game.makeMove(col);
			game.checkWin(col);
		}

		if (game.getGameOver() && lastTime == 0)// true if the game is over
		{
			if (game.getWinner() == 2)
				winner = "The human";
			else if (game.getWinner() == 1)
				winner = "CPU";
			else
				winner = "Tie, no one ";

			lastTime = System.currentTimeMillis();
		} else if (lastTime != 0) {
			if (System.currentTimeMillis() - lastTime > 3000)
				State.setState(new GameWin(winner));
		}
	}

	private class Handeler implements gameAction {
		private int col;

		public Handeler(int col) {
			this.col = col;
		}

		public void action() {
			if (!game.getCurrent() && !game.getGameOver()) {
				game.makeMove(col);
				game.checkWin(col);
			}
		}
	}

	private class Undo implements gameAction {

		@Override
		public void action() {
			game.undo();
			game.undo();

		}

	}
}
