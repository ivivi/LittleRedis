package ivivi.redis.core.command.impl;

public enum CommandType {
	
	BASE_Keys((byte)1),
	BASE_Strings((byte)2),
	BASE_Hashes((byte)3),
	BASE_Lists((byte)4),
	BASE_Sets((byte)5),
	BASE_SortedSets((byte)6),
	
	EXT_PubAndSub((byte)101),
	EXT_Transcations((byte)102),
	EXT_Scripting((byte)103),
	EXT_Connection((byte)104),
	EXT_Server((byte)105);
	
	private byte type;
	
	private CommandType(byte b) {
		this.type = b;
	}
	
	public byte toByte() {
		return type;
	}
}
