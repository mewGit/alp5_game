package alp5.u12.pong;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {

	private Game game;
	private InetAddress host;
	private int port;
	private DatagramSocket socket;
	private DatagramPacket packetSend, packetReceive;
	private byte[] buf = new byte[128];
	// 0 = player Y 1; 1 = ball X; 2 = ball Y; 3 = player1 score 4 = player2 score 
	private float[] gameState = new float[5];
	
	public Connection(Game game) {
		this.game = game;
	}
	
	// host
	public void connect(int port) {
		try {
			socket = new DatagramSocket(port);
			packetSend = new DatagramPacket(buf, buf.length);
			socket.receive(packetSend);
			System.out.println("Debug: " + new String(packetSend.getData(), 0, packetSend.getLength()));
			packetReceive = new DatagramPacket(buf, buf.length);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendGameState(Ball ball, Player player, Score score) {
		String tmp = Float.toString(player.y) +";"+ Float.toString(ball.x) +";"+ Float.toString(ball.y)
				+";"+ score.getScore()[0] +";"+ score.getScore()[1];
		packetSend.setData(tmp.getBytes(), 0, tmp.length());
		try {
			socket.send(packetSend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receivePlayerPos(Player player) {
		try {
			socket.receive(packetReceive);
			String[] tmp = (new String(packetReceive.getData(), 0, packetReceive.getLength())).split(";");
			player.y = Float.valueOf(tmp[0]);
			player.keyLastMove = Integer.parseInt(tmp[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// client
	public void connect(String host, int port) {
		try {
			this.host = InetAddress.getByName(host);
			this.port = port;
			packetSend = new DatagramPacket("Hello".getBytes(), 5, this.host, this.port);
			socket = new DatagramSocket();
			socket.send(packetSend);
			packetReceive = new DatagramPacket(buf, buf.length);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public float[] reciveGameState(Ball ball, Player player, Score score) {
		try {
			socket.receive(packetReceive);
			String[] tmp = (new String(packetReceive.getData(), 0, packetReceive.getLength())).split(";");
			if (tmp.length == 5) {
				player.y = Float.valueOf(tmp[0]);
				ball.x = Float.valueOf(tmp[1]);
				ball.y = Float.valueOf(tmp[2]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gameState;
	}
	
	public void sendPlayerPosition(Player player) {
		String tmp = Float.toString(player.y)+";"+player.keyLastMove;
		packetSend.setData(tmp.getBytes(), 0, tmp.length());
		try {
			socket.send(packetSend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
