package ivivi.redis.core.command.impl;

public enum CommandType {
	
	/*
	 * byte is 8 bits
	 * the highest bit indicating which category it's belonged to
	 * 0 indicating BASE
	 * 1 indicating EXT
	 */
	
	BASE_Keys((byte)1),			// 0000 0001
	BASE_Strings((byte)2),		// 0000 0010
	BASE_Hashes((byte)4),   	// 0000 0100
	BASE_Lists((byte)8),      	// 0000 1000
	BASE_Sets((byte)16),	    // 0001 0000
	BASE_SortedSets((byte)32),	// 0010 0000
	
	EXT_PubAndSub((byte)129),	// 1000 0001 
	EXT_Transcations((byte)130),// 1000 0010
	EXT_Scripting((byte)132),	// 1000 0100
	EXT_Connection((byte)136),	// 1000 1000
	EXT_Server((byte)144);		// 1001 0000
	
	private byte type;
	
	private CommandType(byte b) {
		this.type = b;
	}
	
	public byte toByte() {
		return type;
	}
}
