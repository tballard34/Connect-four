package GamePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import gameDisplay.Display;
import gameInput.MouseInput;
import gameState.GameMenu;
import gameState.State;

public class Game implements Runnable {

	private Display display;
	private Dimension d;

	private boolean running;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	private State gameMenu;

	private MouseInput in;

	public Game(String title, int width, int height) {
		display = new Display(title, width, height);
		d = new Dimension(width, height);
	}

	// starts when thread is started
	public void run() {

		init();

		double target = 60.0;
		double nsPerTick = 1000000000 / target;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double unprocessed = 0.0;
		int fps = 0;
		int tps = 0;
		boolean canRen = false;

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			if (unprocessed >= 1.0) {

				tick();
				unprocessed--;
				tps++;
				canRen = true;
			} else
				canRen = false;

			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (canRen) {
				render();
				fps++;
			}

			if (System.currentTimeMillis() - 1000 > timer) {
				timer += 1000;
				System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
				fps = 0;
				tps = 0;
			}
		}

		stop();
	}

	// used to draw to the screen using a bufferedStrategy
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear Screen

		g.clearRect(0, 0, (int) d.getWidth(), (int) d.getHeight());

		// Draw Here
		if (State.getState() != null)
			State.getState().render(g);

		// EndDrawing
		bs.show();
		g.dispose();
	}

	// used for making the game progress
	public void tick() {
		// in.update();
		if (State.getState() != null)
			State.getState().tick();
	}

	private void init() {
		gameMenu = new GameMenu();
		State.setState(gameMenu);

		in = new MouseInput();
		display.getCanvas().addMouseListener(in);
		display.getCanvas().addMouseMotionListener(in);
	}

	// used to start the game and start the thread
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.setName("Game");
		thread.start();
	}

	// kills the thread and stops the game
	public synchronized void stop() {
		if (!running)
			return;
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
				}
	}
}
