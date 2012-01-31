package alp5.u12.pong;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game {
	
	private int width,height;
	private String windowTitle = "Pong Clone Prototype";
	
	public Game(int width, int heigth) {
		this.width = width;
		this.height = heigth;
		init();
	}
	
	private void init() {
		// init window
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Display.setTitle(windowTitle);
		
		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void mainLoop() {
		// init variables
		boolean gameRunning = true;
		int fps = 0;
		long delta = 0;
		long lastFPSUpdate = 0;
		long lastLoopStart = System.nanoTime();
				
		while(gameRunning) {
			// variables for the FPS and loop time 
			delta = System.nanoTime() - lastLoopStart;
			lastLoopStart = System.nanoTime();
			lastFPSUpdate += delta;
			fps++;
			// refresh FPS in title every second
			if (lastFPSUpdate >= 1000000000) {
				Display.setTitle(windowTitle +" (FPS: "+fps+")");
				lastFPSUpdate = 0;
				fps = 0;
			}
			// clear the render buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			// input handling
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				gameRunning = false;
				System.out.println("Debug: quit");
			}
			
			// TODO: game logic
			// TODO: rendering
			
			// display changes
			Display.update();
			// frame limiter; max FPS = 60  
			if ((delta = (16666667 + lastLoopStart - System.nanoTime())/1000000) > 10 ) {
				try {
					System.out.println("Debug: sleep " + delta + "ms");
					Thread.sleep(delta);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// clear up and exit
		Display.destroy();
	}
	
	public static void main(String[] args) {
		Game game = new Game(640,480);
		game.mainLoop();
	}
}
