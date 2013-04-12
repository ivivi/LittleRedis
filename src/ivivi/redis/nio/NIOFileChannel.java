package ivivi.redis.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {

	public static void main(String[] args) {
		try {
			testChannel();
			//testChannel2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void testChannel() throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("fromfile", "rw");
		RandomAccessFile toFile = new RandomAccessFile("tofile", "rw");
		
		FileChannel fromChannel = fromFile.getChannel();
		FileChannel toChannel = toFile.getChannel();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.put("ab".getBytes());
		byteBuffer.flip();
		fromChannel.write(byteBuffer);
		fromFile.seek(0);
		
		toChannel.transferFrom(fromChannel, 0, fromChannel.size());
		ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
		
		for(int bytesRead = toChannel.read(byteBuffer2);bytesRead != -1;bytesRead = toChannel.read(byteBuffer2)) {
			
			byteBuffer2.flip();
			
			for(;byteBuffer2.hasRemaining();) {
				System.out.print((char)byteBuffer2.get());
			}
			
			byteBuffer2.clear();
		}
		
		fromFile.close();
		toFile.close();
	}
	
	private static void testChannel2() throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("fromfile", "rw");
		fromFile.writeChars("abcdef");
		fromFile.seek(0);
		
		RandomAccessFile toFile = new RandomAccessFile("tofile", "rw");
		
		FileChannel fromChannel = fromFile.getChannel();
		FileChannel toChannel = toFile.getChannel();
		
		toChannel.transferFrom(fromChannel, 0, fromChannel.size());
		ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
		
		for(int bytesRead = toChannel.read(byteBuffer2);bytesRead != -1;bytesRead = toChannel.read(byteBuffer2)) {
			
			byteBuffer2.flip();
			
			for(;byteBuffer2.hasRemaining();) {
				System.out.print(byteBuffer2.getChar());
			}
			
			byteBuffer2.clear();
		}
		
		fromFile.close();
		toFile.close();
	}
}
