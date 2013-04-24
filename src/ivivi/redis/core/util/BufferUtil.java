package ivivi.redis.core.util;

import java.nio.ByteBuffer;

public class BufferUtil {
	
	public static void str2buf(ByteBuffer buffer,String str) {
		buffer.clear();
		buffer.put(str.getBytes());
		buffer.flip();
	}
	
	public static void clearBuffer(ByteBuffer buffer) {
		buffer.clear();
	}
}
