package alp5.u12.pong;

import org.lwjgl.input.Keyboard;

public class ReadInput {

	public static String getChar(String ip){
		while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		    	switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_0:
					ip += 0; 
					break;
				case Keyboard.KEY_1:
					ip += 1; 
					break;
				case Keyboard.KEY_2:
					ip += 2; 
					break;
				case Keyboard.KEY_3:
					ip += 3; 
					break;
				case Keyboard.KEY_4:
					ip += 4; 
					break;
				case Keyboard.KEY_5:
					ip += 5; 
					break;
				case Keyboard.KEY_6:
					ip += 6; 
					break;
				case Keyboard.KEY_7:
					ip += 7; 
					break;
				case Keyboard.KEY_8:
					ip += 8; 
					break;
				case Keyboard.KEY_9:
					ip += 9; 
					break;
				case Keyboard.KEY_PERIOD:
					ip += "."; 
					break;
				case Keyboard.KEY_BACK:
					if (ip.length() > 0) 
						ip = ip.substring(0, ip.length()-1);
					break;
				case Keyboard.KEY_RETURN:
					ip += "F";
					break;	
				default:
					break;
				}
		    }
		}
		return ip;
	}
}
