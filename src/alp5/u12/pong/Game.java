package alp5.u12.pong;

import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.Display.*;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

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
			setDisplayMode(new DisplayMode(width, height));
			create();
			
			setTitle(windowTitle);
			Mouse.setGrabbed(true);
			
			// Initialize OpenGL
			glDisable(GL_LIGHTING);
			glDisable(GL_DITHER);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_DEPTH_TEST);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, width, 0, height, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			glViewport(0, 0, width, height);
			
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
		boolean serve = false;
		int fps = 0;
		long delta = 0;
		long lastFPSUpdate = 0;
		long lastLoopStart = System.nanoTime();
		Score score = new Score(10);
		int[] s = new int[2];
		Background background = new Background(this, "background.png");
		Boarder boarder = new Boarder(this);
		Ball ball = new Ball(this, "ball.png", width/2, height/2);
		ball.setSpeed(-8f, 0f);
		entitieList.add(ball);
		//player
		Player player1 = new Player(this, "player_left.png", true);
		Player player2 = new Player(this, "player_right.png", false);
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
				setTitle(windowTitle +" (FPS: "+fps+")");
				lastFPSUpdate = 0;
				fps = 0;
			}
			// clear the render buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			// input handling
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				gameRunning = false;
				System.out.println("Debug: quit");
			}
			
			// TODO: game logic
			if (serve) {
				serve = false;
				ball.serve(collision >> 5);
			}
			ball.move();
			player1.move();
			player2.move();
			collision = 0;
			collision = boarder.collides(ball);
			collision += player1.collides(ball);
			collision += player2.collides(ball);
			ball.handleCollision(collision);

			if ((collision >> 5) > 0) {
				serve = true;
				score.incScore(collision >> 5);
				s = score.getScore();
				System.out.println("Debug: Score\n\tPlayer 1: "+s[0] +"\t Player 2: "+s[1]);
			}
			
			// TODO: rendering
			background.draw();
			for (Entity entity : entitieList) {
				entity.draw();
			}
			
			// display changes
			update();
			// LWJGL takes care of frame limiting; section can be removed 
			// frame limiter; max FPS = 60  
			if ((delta = (16666667 + lastLoopStart - System.nanoTime())/1000000) > 10 ) {
				try {
					//System.out.println("Debug: sleep " + delta + "ms");
					Thread.sleep(delta);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		// clear up and exit
		destroy();
	}
	
	public static void main(String[] args) {
		Game game = new Game(800,480);
		game.mainLoop();
	}

	public Sprite getSprite(String ref) {
		return new Sprite(textureLoader, ref);
	}
}
