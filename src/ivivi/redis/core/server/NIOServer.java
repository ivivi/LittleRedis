package ivivi.redis.core.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
	
	private final Selector selector;
	private boolean listenSwitch = true;
	
	public NIOServer() throws IOException {
		selector = Selector.open();
	}
	
	public void initServer(int port) throws IOException {
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void start() throws IOException {
		listen();
	}
	
	private void listen() throws IOException {
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
		
		if(key.isAcceptable()) {
			handleAccept(key);
		} else if(key.isReadable()) {
			handleRead(key);
		} else if(key.isWritable()) {
			handleWrite(key);
		}
	}

	private void handleWrite(SelectionKey key) {
		
	}

	private void handleRead(SelectionKey key) {
		
	}

	private void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		
		socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	public void closeServer() {
		
	}
}
