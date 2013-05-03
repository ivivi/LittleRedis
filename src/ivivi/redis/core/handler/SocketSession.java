package ivivi.redis.core.handler;

import ivivi.redis.core.util.ByteUtil;

public class SocketSession {
	
	private static final int builderSize = 32;
	
	private byte[] head = new byte[7];
	private byte[] tail = new byte[2];
	private int headIndex = 0;
	private int tailIndex = 0;
	
	private int bodyLength = 0;
	private StringBuilder body = new StringBuilder(builderSize);
	
	private SocketSession() {
		
	}
	
	public static SocketSession getSocketSession() {
		return new SocketSession();
	}
	
	public boolean isFilled4head() {
		if(headIndex == head.length) {
			ensureHeadValid();
			setBodyLength();
			return true;
		} 
		return false;
	}
	
	public boolean isFilled4body() {
		if(body.length() == bodyLength - 2)
			return true;
		return false;
	}
	
	public boolean isFilled4tail() {
		if(tailIndex == 2) {
			ensureTailAndBodyValid();
			return true;
		}
			
		return false;
	}
	
	public void put2head(byte b) {
		head[headIndex] = b;
		headIndex++;
	}
	
	public void put2tail(byte b) {
		tail[tailIndex] = b;
		tailIndex++;
	}

	public void put2body(byte b) {
		body.append((char)b);
	}
	
	public void setBodyLength() {
		if(bodyLength == 0) {
			byte[] intByte = new byte[4];
			System.arraycopy(head, 3, intByte, 0, 4);
			this.bodyLength = ByteUtil.byte2int(intByte);
		}
	}

	public boolean isFilled4message() {
		return isFilled4head() && isFilled4body() && isFilled4tail();
	}

	private void ensureHeadValid() {
		if(head[0] != (byte)0xa5 || head[1] != (byte)0xa5) 
			throw new RuntimeException("invalid message head");
	}
	
	private void ensureTailAndBodyValid() {
		if(bodyLength <= 0)
			throw new RuntimeException("invalid message body");
		if(tail[0] != (byte)0xa6 || tail[1] != (byte)0xa6)
			throw new RuntimeException("invalid message tail");
	}
	
	public String getBody() {
		return body.toString();
	}

	public void clear() {
		headIndex = 0;
		tailIndex = 0;
		bodyLength = 0;
		
		body = new StringBuilder(builderSize);
	}
}
