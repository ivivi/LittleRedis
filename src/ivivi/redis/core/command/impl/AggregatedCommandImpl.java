package ivivi.redis.core.command.impl;

import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;

import ivivi.redis.core.command.AggregatedCommand;
import ivivi.redis.core.message.Message;

public abstract class AggregatedCommandImpl implements AggregatedCommand {

	@Override
	public void del(String... keys) {
		Message m = new Message(CommandType.BASE,CommandPrefix.DEL,keys);
		sendCommand(m);
	}

	@Override
	public void dump(String key) {
		Message m = new Message(CommandPrefix.DUMP,CommandType.BASE,key);
		sendCommand(m);
	}

	@Override
	public void exists(String key) {
		Message m = new Message(CommandPrefix.EXISTS,CommandType.BASE,key);
		sendCommand(m);
	}

	@Override
	public void expire(String key, int seconds) {
		Message m = new Message(CommandType.BASE,CommandPrefix.EXPIRE,key,String.valueOf(seconds));
		sendCommand(m);
	}

	@Override
	public void expireAt(String key, long unixTimestamp) {
		Message m = new Message(CommandType.BASE,CommandPrefix.EXPIREAT,key,String.valueOf(unixTimestamp));
		sendCommand(m);
	}

	@Override
	public void get(String key) {
		Message m = new Message(CommandPrefix.GET,CommandType.BASE,key);
		sendCommand(m);
	}

	@Override
	public void set(String key, String value) {
		Message m = new Message(CommandType.BASE,CommandPrefix.SET,key,value);
		sendCommand(m);
	}
	
	private final void sendCommand(Message m) {
		CommandTool.sendCommand(getOS(), m);
	}
}
