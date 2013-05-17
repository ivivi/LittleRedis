package ivivi.redis.core.message;

import ivivi.redis.core.command.impl.CommandPrefix;
import ivivi.redis.core.command.impl.CommandType;
import ivivi.redis.core.util.ByteUtil;
import ivivi.redis.core.util.SeparatorUtil;

public class Message {
	
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
			StringBuilder sb = new StringBuilder();
			sb.append(command.name()).append(SeparatorUtil.SEPARATOR_MAIN);
			if(null != keys) {
				for(String key : keys) 
					sb.append(key).append(SeparatorUtil.SEPARATOR_SUB_SON);
			}
			sb.append(SeparatorUtil.SEPARATOR_SUB);
			if(null != args) {
				for(String arg : args) 
					sb.append(arg).append(SeparatorUtil.SEPARATOR_SUB_SON);
			}
			//Encodes this String into a sequence of bytes using the
		    //platform's default charset, storing the result into a new byte array.
			return sb.toString().getBytes();
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
		
		byte[] mbytes = new byte[7 + body.toBytes().length + 2];
		System.arraycopy(head.toBytes(), 0, mbytes, 0, 7);
		System.arraycopy(body.toBytes(), 0, mbytes, 7, mbytes.length-9);
		System.arraycopy(tail.toBytes(), 0, mbytes, mbytes.length-2, 2);
		
		return mbytes;
	}
}
