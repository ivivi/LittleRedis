package ivivi.redis.core.command.impl;


import ivivi.redis.core.message.Message;

import java.io.OutputStream;

public class CommandTool {
	
	private CommandTool() {
	
	}
	
	public static void sendCommand(final OutputStream os,final Message message) {
		try {
			os.write(message.toBytes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("send failed");
		}
	}
	
}
