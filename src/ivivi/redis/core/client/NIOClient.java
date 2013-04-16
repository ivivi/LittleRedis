package ivivi.redis.core.client;

import ivivi.redis.core.handler.ClientHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {
	
	private final SocketChannel socketChannel;
	private final Selector selector;
	private final ClientHandler handler;
	private boolean connectSwitch = true;
	
	public NIOClient() throws IOException {
		socketChannel = SocketChannel.open();
		selector = Selector.open();
		handler = ClientHandler.getClientHandler();
	}
	
	public void initClient() throws IOException {
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
	}

	public void start(String hostname,int port) throws IOException {
		connect(hostname,port);
	}

	private void connect(String hostname,int port) throws IOException {
		socketChannel.connect(new InetSocketAddress(hostname, port));
		for(;connectSwitch;) {
			selector.select();
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			for(;iter.hasNext();) {
				SelectionKey key = iter.next();
				iter.remove();
				
				handler.handle(key,selector);
			}
		}
	}
	
	public void closeClient() throws IOException {
		socketChannel.close();
		selector.close();
	}
}
