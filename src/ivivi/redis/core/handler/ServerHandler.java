package ivivi.redis.core.handler;

import ivivi.redis.core.test.Steper;
import ivivi.redis.core.util.BufferUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerHandler extends Handler {
	
	private static final ServerHandler handler = new ServerHandler();
	private static final ByteBuffer buffer = ByteBuffer.allocate(1024);
	
	private ServerHandler() {
		
	}
	
	public static ServerHandler getServerHandler() {
		return handler;
	}
	
	@Override
	protected void handleAccept(SelectionKey key,Selector selector) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		
		socketChannel.register(selector, SelectionKey.OP_READ);
	}

	@Override
	protected void handleConnect(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "conn" + key.hashCode());
	}

	@Override
	protected void handleRead(SelectionKey key,Selector selector) throws IOException {
		System.out.println("handleRead start");
		
		SocketChannel socketChannel = (SocketChannel)key.channel();
		BufferUtil.clearBuffer(buffer);//the position is set back to 0 and the limit to capacity
		
		int count = 0;
		/*
		 * Because NIO SocektChannel will not block until the data is available for read. 
		 * i.e, Non Blocking Channel can return 0 on read() operation.
		 * On the otherhand, blocking channel will wait till the data is available and return how much data is available. 
		 * i.e, Blocking channel will never return 0 on read() operation.
		 */
		for(;(count = socketChannel.read(buffer)) > 0;) {
			buffer.flip();//sets the position back to 0, and sets the limit to where position just was.
			for(;buffer.hasRemaining();) {
				System.out.print((char)buffer.get());
			}
		  
			BufferUtil.clearBuffer(buffer);
		}
		
		if(count == -1)	
			socketChannel.close();
		
		
		System.out.println("\r\nhandleRead end");
	}

	@Override
	protected void handleWrite(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "write" + key.hashCode());
	}
}