package ivivi.redis.core.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerHandler extends Handler {
	
	private static final ServerHandler handler = new ServerHandler();
	
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
		
		socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	@Override
	protected void handleConnect(SelectionKey key) throws IOException {
		
	}

	@Override
	protected void handleRead(SelectionKey key) throws IOException {
		
	}

	@Override
	protected void handleWrite(SelectionKey key) throws IOException {
		
	}
}