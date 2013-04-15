package ivivi.redis.core.handler;

import ivivi.redis.core.test.Steper;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientHandler extends Handler {
	
	private static final ClientHandler handler = new ClientHandler();
	
	private ClientHandler() {
		
	}
	
	public static ClientHandler getClientHandler() {
		return handler;
	}

	@Override
	protected void handleAccept(SelectionKey key,Selector selector) throws IOException {
		
	}

	@Override
	protected void handleConnect(SelectionKey key,Selector selector) throws IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		for(;;) {
			if(socketChannel.finishConnect()) break;
		}
		socketChannel.register(selector, SelectionKey.OP_WRITE);
		System.out.println(Steper.getStep() + "conn" + key.hashCode());
	}

	@Override
	protected void handleRead(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "read" + key.hashCode());
	}

	@Override
	protected void handleWrite(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "write" + key.hashCode());
	}
}