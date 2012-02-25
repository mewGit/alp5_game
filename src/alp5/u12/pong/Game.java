package alp5.u12.pong;

import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game {
	
	protected int width,height;
	private String windowTitle = "Pong Clone Prototype";
	private TextureLoader textureLoader;
	
	public Game(int width, int heigth) {
		this.width = width;
		this.height = heigth;
		init();
	}
	
	private void init() {
		try {
			// Initialize window
			Display.setDisplayMode(new DisplayMode(width, height));
			// XXX: does it work?
			Display.setVSyncEnabled(true);
			Display.create();
			
			Display.setTitle(windowTitle);
//			Mouse.setGrabbed(true);
			
			// Initialize OpenGL
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, width, 0, height, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glViewport(0, 0, width, height);
			
			textureLoader = new TextureLoader();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void mainLoop() {
		// Initialize local variables
		LinkedList<Entity> entitieList = new LinkedList<Entity>(); 
		boolean gameRunning = true;
		int fps = 0;
		long delta = 0;
		long lastFPSUpdate = 0;
		long lastLoopStart = System.nanoTime();
		Boarder boarder = new Boarder(this);
		Ball ball = new Ball(this, "ball.png", 0, 0);
		ball.setSpeed(3f, 3f);
		entitieList.add(ball);
		//player
		Player player1 = new Player(this, "player.png", 16, (height/2)-32);
		Player player2 = new Player(this, "player.png",616, (height/2)-32);
		entitieList.add(player1);
		entitieList.add(player2);
		int collision = 0;
		
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
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			// input handling
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				gameRunning = false;
				System.out.println("Debug: quit");
			}
			
			// TODO: game logic
			ball.move();
			player1.move();
			player2.move();
			collision = boarder.collides(ball);
			collision += player1.collides(ball);
			ball.handleCollision(collision);
			
			// TODO: rendering
			for (Entity entity : entitieList) {
				entity.draw();
			}
			
			// display changes
			Display.update();
			// LWJGL takes care of frame limiting; section can be removed 
/*			// frame limiter; max FPS = 60  
			if ((delta = (16666667 + lastLoopStart - System.nanoTime())/1000000) > 10 ) {
				try {
					System.out.println("Debug: sleep " + delta + "ms");
					Thread.sleep(delta);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
*/
		}
		// clear up and exit
		Display.destroy();
	}
	
	public static void main(String[] args) {
		Game game = new Game(640,480);
		game.mainLoop();
	}

	public Sprite getSprite(String ref) {
		return new Sprite(textureLoader, ref);
	}
}
