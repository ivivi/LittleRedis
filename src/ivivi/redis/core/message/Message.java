package ivivi.redis.core.message;

import ivivi.redis.core.util.ByteUtil;

public class Message {
	
    private static final byte DOLLAR_BYTE = '$';
    private static final byte ASTERISK_BYTE = '*';
	
	public Head head;
	public Body body;
	public Tail tail;
	
	public Message() {
		this.head = new Head();
		this.body = new Body();
		this.tail = new Tail();
	}
	
	/*
	 * allocate 7 bytes for head
	 */
	public static final class Head {
		private static final byte[] prefix = new byte[]{(byte)0xa5,(byte)0xa5};
		private byte type;
		private int bodyLength;
		
		public byte getType() {
			return type;
		}
		public void setType(byte type) {
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
			hb[2] = type;
			System.arraycopy(ByteUtil.int2byte(bodyLength), 0, hb, 3, 4);
			
			return hb;
		}
	}
	
	public static final class Body {
		private String command;
		private String[] keys;
		private String[] args;
		
		private String oneKeys() {
			return null;
		}

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}
		
		private byte[] toBytes() {
			return command.getBytes();
		}
	}
	
	public static final class Tail {
		private static final byte[] suffix = new byte[]{(byte)0xa6,(byte)0xa6};
		
		public byte[] toBytes() {
			return suffix;
		}
	}
	
	public final byte[] toBytes() {
		byte[] mb = new byte[7+this.body.getCommand().length()+2];
		System.arraycopy(head.toBytes(), 0, mb, 0, 7);
		System.arraycopy(body.toBytes(), 0, mb, 7, mb.length-9);
		System.arraycopy(tail.toBytes(), 0, mb, mb.length-2, 2);
		
		return mb;
	}
}
