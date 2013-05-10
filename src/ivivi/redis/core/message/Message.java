package ivivi.redis.core.message;

import ivivi.redis.core.command.impl.CommandPrefix;
import ivivi.redis.core.command.impl.CommandType;
import ivivi.redis.core.util.ByteUtil;

public class Message {
	
    private static final byte DOLLAR_BYTE = '$';
    private static final byte ASTERISK_BYTE = '*';
	
	public Head head;
	public Body body;
	public Tail tail;
	
	public Message(CommandType type,CommandPrefix command) {
		this.head = new Head();
		this.body = new Body();
		this.tail = new Tail();
		
		this.head.setType(type);
		this.body.setCommand(command);
	}
	
	public Message(CommandPrefix command,CommandType type,String...keys) {
		
		this(type,command);
		this.body.setKeys(keys);
		
	}
	
	public Message(CommandType type,CommandPrefix command,String[] keys,String...args) {
		
		this(type,command);
		this.body.setKeys(keys);
		this.body.setArgs(args);
		
	}
	
	public Message(CommandType type,CommandPrefix command,String oneKey,String...args) {
		
		this(type,command);
		this.body.setOneKey(oneKey);
		this.body.setArgs(args);
		
	}
	
	/*
	 * allocate 7 bytes for head
	 */
	public static final class Head {
		private static final byte[] prefix = new byte[]{(byte)0xa5,(byte)0xa5};
		private CommandType type;
		private int bodyLength;
		
		public void setType(CommandType type) {
			this.type = type;
		}
		public int getBodyLength() {
			return bodyLength;
		}
		public void setBodyLength(int bodyLength) {
			this.bodyLength = bodyLength;
		}
		
		private byte[] toBytes() {
			byte[] hb = new byte[7];
			System.arraycopy(prefix, 0, hb, 0, 2);
			hb[2] = type.toByte();
			System.arraycopy(ByteUtil.int2byte(bodyLength), 0, hb, 3, 4);
			
			return hb;
		}
	}
	
	public static final class Body {
		
		private CommandPrefix command;
		private String[] keys;
		private String[] args;
		
		public void setCommand(CommandPrefix command) {
			this.command = command;
		}

		public void setKeys(String[] keys) {
			this.keys = keys;
		}

		public void setArgs(String[] args) {
			this.args = args;
		}
		
		public void setOneKey(String key) {
			this.keys = new String[]{key};
		}
		
		private byte[] toBytes() {
			return command.raw;
		}
	}
	
	public static final class Tail {
		private static final byte[] suffix = new byte[]{(byte)0xa6,(byte)0xa6};
		
		public byte[] toBytes() {
			return suffix;
		}
	}
	
	public final byte[] toBytes() {
		
		head.bodyLength = body.toBytes().length;
		
		byte[] mb = new byte[7 + body.toBytes().length + 2];
		System.arraycopy(head.toBytes(), 0, mb, 0, 7);
		System.arraycopy(body.toBytes(), 0, mb, 7, mb.length-9);
		System.arraycopy(tail.toBytes(), 0, mb, mb.length-2, 2);
		
		return mb;
	}
}
