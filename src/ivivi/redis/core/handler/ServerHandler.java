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
		System.out.println(Steper.getStep() + "accept" + key);
	}

	@Override
	protected void handleConnect(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "conn" + key.hashCode());
	}

	@Override
	protected void handleRead(SelectionKey key,Selector selector) throws IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		BufferUtil.clearBuffer(buffer);
		
		int count = socketChannel.read(buffer);

		for(;count != -1;) {
			buffer.flip();
			for(;buffer.hasRemaining();) {
				System.out.print(buffer.getChar());
			}
		  
			BufferUtil.clearBuffer(buffer);
			count = socketChannel.read(buffer);
		}

		System.out.println("have handled");
	}

	@Override
	protected void handleWrite(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "write" + key.hashCode());
	}
}