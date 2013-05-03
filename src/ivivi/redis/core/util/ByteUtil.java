package ivivi.redis.core.util;

public class ByteUtil {
	
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);//最低位 
		targets[1] = (byte) ((res >> 8) & 0xff);//次低位 
		targets[2] = (byte) ((res >> 16) & 0xff);//次高位 
		targets[3] = (byte) (res >>> 24);//最高位,无符号右移
		
		return targets; 
	}
	
	public static int byte2int(byte[] res) { 
		int target = (res[0] & 0xff) | 
					((res[1] << 8) & 0xff00) | 
					((res[2] << 24) >>> 8) | 
					 (res[3] << 24); 
		
		return target;
	}
}
