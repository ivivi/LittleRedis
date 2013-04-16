package ivivi.redis.core.server;

import ivivi.redis.core.handler.ServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
	
	private final ServerSocketChannel serverSocketChannel;
	private final Selector selector;
	private final ServerHandler handler;
	private boolean listenSwitch = true;
	
	public NIOServer() throws IOException {
		serverSocketChannel = ServerSocketChannel.open();
		selector = Selector.open();
		handler = ServerHandler.getServerHandler();
	}
	
	public void initServer(int port) throws IOException {
		
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void start() throws IOException {
		listen();
	}
	
	private void listen() throws IOException {
		System.out.println("listening");
		
		for(;listenSwitch;) {
			selector.select();
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			for(;iter.hasNext();) {
				SelectionKey key = iter.next();
				iter.remove();
				
				handle(key);
			}
		}
	}
	
	private void handle(SelectionKey key) throws IOException {
		handler.handle(key, selector);
	}

	public void closeServer() throws IOException {
		serverSocketChannel.close();
		selector.close();
	}
}
