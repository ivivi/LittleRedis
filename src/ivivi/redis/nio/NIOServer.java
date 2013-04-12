package ivivi.redis.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NIOServer {
	
	private Selector selector = null;
	
	public void initServer(int port) throws Exception {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		
		serverChannel.socket().bind(new InetSocketAddress(port));
		this.selector = Selector.open();
		
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void dispatch() throws Exception {
		for(;;) {
			selector.select();
			for(Iterator<SelectionKey> iter = selector.selectedKeys().iterator();iter.hasNext();) {
				SelectionKey selectionKey = iter.next();
				iter.remove();
				
				handler(selectionKey);
			}
		}
	}
	
	private void handler(SelectionKey selectionKey) {
		if(selectionKey.isAcceptable()) {
			handle4acceptable(selectionKey);
		} else if(selectionKey.isReadable()) {
			handle4readable(selectionKey);
		} else if(selectionKey.isWritable()) {
			handle4writable(selectionKey);
		}
	}

	private void handle4writable(SelectionKey selectionKey) {
		
	}

	private void handle4readable(SelectionKey selectionKey) {
		
	}

	private void handle4acceptable(SelectionKey selectionKey) {
		
	}

	public void closeServer() {
		
	}
}
