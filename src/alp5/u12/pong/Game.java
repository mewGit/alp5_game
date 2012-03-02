package alp5.u12.pong;

import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.Display.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import alp5.u12.pong.entitys.Background;
import alp5.u12.pong.entitys.Ball;
import alp5.u12.pong.entitys.MenuElement;
import alp5.u12.pong.entitys.Entity;
import alp5.u12.pong.entitys.Player;
import static org.lwjgl.opengl.GL11.*;

public class Game {
	
	public int width,height;
	private String windowTitle = "Pong Clone ver 0.7 alpha";

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
//			Mouse.setGrabbed(true);
			
			// Initialize OpenGL
			glDisable(GL_LIGHTING);
			glDisable(GL_DITHER);
			glDisable(GL_DEPTH_TEST);
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, width, 0, height, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			glViewport(0, 0, width, height);
			
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void mainLoop() {
		// Initialize title screen
		boolean titleactive = true;
		boolean join = false;
		int choosebutton = 0;
		Background titlescreen = new Background(this, "menu/titlescreen.png");
		MenuElement activebutton = new MenuElement(this, "menu/active_button.png", width/10+190, 144);
		MenuElement hostWaitingMsg = new MenuElement(this, "menu/host_waiting.png");
		MenuElement enterIPBox = new MenuElement(this, "menu/enter_IP.png", width/10+242 , 72);
		MenuElement textRender = new MenuElement(this, "menu/numbers.png", 0, 0, 12, 16);
		// title screen buttons
		MenuElement hostbutton = new MenuElement(this, "menu/host_button.png", width/10, 134);
		MenuElement joinbutton = new MenuElement(this, "menu/join_button.png", width/10, 72);
		MenuElement quitbutton = new MenuElement(this, "menu/quit_button.png", width/10, 10);
		// Initialize local variables
		LinkedList<Entity> entitieList = new LinkedList<Entity>();
		Connection connection = new Connection();
		String regExIPv4 = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}[F]\\z";
		String ip = "";
		boolean host = false;
		boolean gameRunning = true;
		boolean serve = false;
		int fps = 0;
		long delta = 0;
		long lastFPSUpdate = 0;
		long lastLoopStart = System.nanoTime();
		Score score = new Score(10);
		boolean endRound = false;
		Background background = new Background(this, "sprite/background_tile.png");
		Background centerLine = new Background(this, "sprite/center_line.png", width/2, 0, true);
		Boarder boarder = new Boarder(this);
		Ball ball = new Ball(this, "sprite/ball.png", width/2, height/2);
		Random random = new Random();
		ball.serve(random.nextInt(2)+1);
		entitieList.add(ball);
		//player
		Player player1 = new Player(this, "sprite/player_left.png", true);
		Player player2 = new Player(this, "sprite/player_right.png", false);
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
			
			// exit with ESC
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				gameRunning = false;
			}
			
			// title screen
			if (titleactive){
				// render menu
				titlescreen.draw();
				hostbutton.draw();
				joinbutton.draw();
				quitbutton.draw();
				activebutton.draw();
				
				// enter IP address and join 
				if (join) {
					enterIPBox.draw();
					textRender.draw(ip, width/10+244, 74, 1.0f);
					ip = ReadInput.getChar(ip);
					if (ip.matches(regExIPv4)) {
						ip = ip.substring(0, ip.length()-1);
						join = false;
						titleactive = !connection.connect(ip, 1234);
					} else if (ip.length() > 0 && ip.charAt(ip.length()-1) == 'F') {
						join = false;
						ip = "";
					}
				}
				// menu navigation and selection
				while (titleactive && Keyboard.next() && !join) {
				    if (Keyboard.getEventKeyState()) {
				    	if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
							choosebutton = ((--choosebutton % 3) + 3) % 3;
							activebutton.y = 144-62*choosebutton;
						}
						if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
							choosebutton = ++choosebutton % 3;
							activebutton.y = 144-62*choosebutton;
						}
						if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
							switch (choosebutton) {
							case 1:
								host = false;
								join = true;
								break;
							case 2:	
								gameRunning = false;
								break;
							default:
								host = true;
								hostWaitingMsg.draw();
								Display.update();
								titleactive = !connection.connect(1234);
								break;
							}
						}
				    }
				}
			} else {
			// run the game
				
				// game logic
				if (endRound) {
					endRound = false;
					score.resetScore();
				}
				if (serve) {
					serve = false;
					ball.serve(collision >> 5);
				}
				ball.move();
				// sync with other player over network
				if (host) {
					player1.move();
					connection.receivePlayerPos(player2);
					connection.sendGameState(ball, player1, score);
					collision = 0;
					collision = boarder.collides(ball);
					collision += player1.collides(ball);
					collision += player2.collides(ball);
					ball.handleCollision(collision);
				} else {
					player2.move();
					connection.sendPlayerPosition(player2);
					connection.reciveGameState(ball, player1, score);
				}
				// has the score updated?
				if ((collision >> 5) > 0) {
					serve = true;
					endRound = score.incScore(collision >> 5);
				}
				
				// rendering screen
				background.drawAsTile(width, height);
				centerLine.draw();
				textRender.draw(Integer.toString(score.p1), width/2-12*3, height*0.9f, 2.0f);
				textRender.draw(Integer.toString(score.p2), width/2+12*2, height*0.9f, 2.0f);
				for (Entity entity : entitieList) {
					entity.draw();
				}
			}	
			// display changes
			update();
			
			// frame limiter; max FPS = 60; network screws with it
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
		// clean up and exit
		destroy();
	}
	
	public static void main(String[] args) {
		Game game = new Game(800,480);
		game.mainLoop();
	}
}
