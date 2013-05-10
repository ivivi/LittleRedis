package ivivi.redis.core.command.impl;

public enum CommandType {
	
	BASE((byte)1);
	
	private byte type;
	
	private CommandType(byte b) {
		this.type = b;
	}
	
	public byte toByte() {
		return type;
	}
}
