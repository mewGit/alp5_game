package alp5.u12.pong;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import alp5.u12.pong.entitys.Ball;
import alp5.u12.pong.entitys.Player;

public class Connection {

	private InetAddress host;
	private int port;
	private DatagramSocket socket;
	private DatagramPacket packetSend, packetReceive;
	private byte[] buf = new byte[128];
	// 0 = player Y 1; 1 = ball X; 2 = ball Y; 3 = player1; score 4 = player2 score 
	private float[] gameState = new float[5];
	
	// host
	public boolean connect(int port) {
		try {
			socket = new DatagramSocket(port);
			packetSend = new DatagramPacket(buf, buf.length);
			socket.setSoTimeout(45000);
			socket.receive(packetSend);
//			System.out.println("Debug: " + new String(packetSend.getData(), 0, packetSend.getLength()));
			packetReceive = new DatagramPacket(buf, buf.length);
			socket.setSoTimeout(50);
			packetSend.setData("Pong".getBytes());
			socket.send(packetSend);
		} catch (SocketTimeoutException e) {
			socket.close();
			return false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void sendGameState(Ball ball, Player player, Score score) {
		String tmp = Float.toString(player.y) +";"+ Float.toString(ball.x) +";"+ Float.toString(ball.y);
		if (score.changed) {
			score.changed = false;
			tmp += ";"+ score.p1 +";"+ score.p2;
		}
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
		} catch (SocketTimeoutException e) {
			// do nothing
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// client
	public boolean connect(String host, int port) {
		try {
			this.host = InetAddress.getByName(host);
			this.port = port;
			packetSend = new DatagramPacket("Ping".getBytes(), 4, this.host, this.port);
			socket = new DatagramSocket();
			socket.setSoTimeout(3000);
			socket.send(packetSend);
			packetReceive = new DatagramPacket(buf, buf.length);
			socket.receive(packetReceive);
//			System.out.println("Debug: " + new String(packetReceive.getData(), 0, packetReceive.getLength()));
			socket.setSoTimeout(50);
		} catch (SocketTimeoutException e) {
			socket.close();
			return false;
		} catch (UnknownHostException e) {
			socket.close();
			return false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public float[] reciveGameState(Ball ball, Player player, Score score) {
		try {
			socket.receive(packetReceive);
			String[] tmp = (new String(packetReceive.getData(), 0, packetReceive.getLength())).split(";");
			if (tmp.length >= 3) {
				player.y = Float.valueOf(tmp[0]);
				ball.x = Float.valueOf(tmp[1]);
				ball.y = Float.valueOf(tmp[2]);
			}
			if (tmp.length == 5) {
				score.changed = true;
				score.p1 = Integer.parseInt(tmp[3]);
				score.p2 = Integer.parseInt(tmp[4]);
			}
		} catch (SocketTimeoutException e) {
			// XXX: do nothing
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
