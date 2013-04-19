package ivivi.redis.core.handler;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.test.Steper;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientHandler extends Handler {
	
	private final NIOClient client;
	
	private ClientHandler(NIOClient client) {
		this.client = client;
	}
	
	public static ClientHandler getClientHandler(NIOClient client) {
		return new ClientHandler(client);
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
		client.setConnected(true);
		socketChannel.register(selector, SelectionKey.OP_WRITE);
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