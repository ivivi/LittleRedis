package ivivi.redis.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOBuffer {

	public static void main(String[] args) {
		try {
			//testBuffer();
			//testBuffer1();
			testBuffer2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testBuffer() throws IOException {
		
		RandomAccessFile aFile = new RandomAccessFile("buffer.dat", "rw");
		aFile.writeChars("Call buffer.flip()");		
		aFile.close();
		
		aFile = new RandomAccessFile("buffer.dat", "rw");		
		FileChannel fileChannel = aFile.getChannel();

		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		
		int bytesRead = fileChannel.read(byteBuffer);
		System.out.println("bytesRead : " + bytesRead);
		for(;bytesRead != -1;bytesRead = fileChannel.read(byteBuffer)) {
			byteBuffer.flip();
			
			for(;byteBuffer.hasRemaining();) {
				System.out.print(byteBuffer.getChar());
			}
			
			byteBuffer.clear();
		}
		
		aFile.close();
	}
	
	private static void testBuffer1() throws Exception {
	    RandomAccessFile rf = new RandomAccessFile("rtest.dat", "rw");  
        for (int i = 0; i < 10; i++) {  
            //写入基本类型double数据  
            rf.writeDouble(i * 1.414);  
        }  
        rf.close();
        
        rf = new RandomAccessFile("rtest.dat", "rw");  
        //直接将文件指针移到第5个double数据后面  
        rf.seek(5 * 8);  
        //覆盖第6个double数据  
        rf.writeDouble(47.0001);  
        rf.close();  
        rf = new RandomAccessFile("rtest.dat", "r");  
        for (int i = 0; i < 10; i++) {  
            System.out.println("Value " + i + ": " + rf.readDouble());  
        }  
        rf.close();
	}
	
	private static void testBuffer2() throws IOException {
		
		RandomAccessFile aFile = new RandomAccessFile("niodata", "rw");
		aFile.writeChars("Read data out of the Buffer");
		aFile.seek(0);
		
		FileChannel fileChannel = aFile.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		
		for(int bytesRead = fileChannel.read(byteBuffer);bytesRead != -1;bytesRead = fileChannel.read(byteBuffer)) {
			System.out.println("bytesRead:" + bytesRead);
			
			bufferStatus(byteBuffer,"before");
			byteBuffer.flip();//It sets the limit to the current position and then sets the position to zero.
			bufferStatus(byteBuffer,"after");
			
			for(;byteBuffer.hasRemaining();) {
				System.out.print(byteBuffer.getChar());
			}
			System.out.println("");
			
			byteBuffer.clear();//It sets the limit to the capacity and the position to zero.
		}
		
		aFile.close();
	}
	
	private static void bufferStatus(Buffer buffer,String flipFlag) {
		System.out.println("limit " + flipFlag + " flip : " + buffer.limit());
		System.out.println("position " + flipFlag + " flip : " + buffer.position());
	}
}
