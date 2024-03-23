package gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameAssets.GameButton;
import gameAssets.gameAction;
import gameConnect4.Connect4;
import gameInput.MouseInput;

/*
 * this is the PvP state 
 */
public class GameBoard extends State {

	private GameButton[] buttons = new GameButton[7];// the buttons to control the drop of the pieces
	private GameButton undo;
	private Connect4 game;// the logic of the game is this
	private String winner;// holds the winner at the end
	private long lastTime = 0;// used to time the display of game over at the end of the game
	private BufferedImage img;
	private Color buttonBaseColor = new Color(150, 150, 255);
	private Color buttonHoverColor = new Color(100, 100, 200);

	public GameBoard() {
		for (int i = 0; i < buttons.length; i++)// makes all the buttons to control the drops of pieces
			buttons[i] = new GameButton(i * 85 + 2, 0, 85, 25, buttonBaseColor, buttonHoverColor, "" + (i + 1),
					new Handeler(i), false);
		// makes the undo button
		undo = new GameButton(250, 410, 100, 40, buttonBaseColor, buttonHoverColor, "Undo", new Undo(), false);

		try {
			img = ImageIO.read((new File("ConnectFour/src/icons/Connect4.png")));// reads the png file and makes it to an image
		} catch (IOException e) {
			e.printStackTrace();
		}

		game = new Connect4();// makes the game object
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

		if (game.getCurrent())// Write to the canvas who is playing at the moment
			g.drawString("Current Turn: Red", 25, 435);
		else
			g.drawString("Current Turn: Black", 25, 435);

		if (game.getGameOver())// draws the box in the canvas when the game is over
		{
			g.setColor(new Color(50, 50, 50));
			g.fillRect(245, 170, 110, 60);

			g.setColor(Color.WHITE);
			g.fillRect(250, 175, 100, 50);

			g.setColor(Color.BLACK);
			g.drawString("Game Over!!!", 270, 200);
		}
	}

	// updates the buttons and checks if the game is over and therer is a winner
	public void tick() {
		for (int i = 0; i < buttons.length; i++)
			buttons[i].tick(MouseInput.getX(), MouseInput.getY());

		undo.tick(MouseInput.getX(), MouseInput.getY());

		if (game.getGameOver() && lastTime == 0)// checks if lastTime is 0 so it only goes through the if statement once
		{
			if (game.getWinner() == 1)
				winner = "Player 1";
			else if (game.getWinner() == 2)
				winner = "Player 2";
			else
				winner = "Tie, on one ";

			lastTime = System.currentTimeMillis();// gets the time of the game ending
		} else if (game.getGameOver() && System.currentTimeMillis() - lastTime > 3000)// if 3 seconds has passed then
																						// its game over
			State.setState(new GameWin(winner));
	}

	// controls the button for drops
	private class Handeler implements gameAction {
		private int col;

		public Handeler(int col) {
			this.col = col;
		}

		// if the game isn't over then it makes the move
		public void action() {
			if (!game.getGameOver()) {
				game.makeMove(col);
				game.checkWin(col);
			}
		}
	}

	// undoes the last move using the method from the Connect4 class
	private class Undo implements gameAction {

		@Override
		public void action() {
			game.undo();

		}

	}
}
