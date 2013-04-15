package ivivi.redis.core.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class ClientHandler extends Handler {
	
	private static final ClientHandler handler = new ClientHandler();
	
	private ClientHandler() {
		
	}
	
	public static ClientHandler getClientHandler() {
		return handler;
	}

	@Override
	protected void handleAccept(SelectionKey key, Selector selector) throws IOException {
		
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