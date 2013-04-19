package ivivi.redis.core.handler;

import ivivi.redis.core.client.NIOClient;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public abstract class Handler {
	
	public void handle(SelectionKey key,Selector selector) throws IOException {
		if(key.isAcceptable()) {
			handleAccept(key,selector);
		} else if(key.isConnectable()) {
			handleConnect(key,selector);
		} else if(key.isReadable()) {
			handleRead(key,selector);
		} else if(key.isWritable()) {
			handleWrite(key,selector);
		}
	}
	
	protected abstract void handleAccept (SelectionKey key,Selector selector) throws IOException;
	protected abstract void handleConnect(SelectionKey key,Selector selector) throws IOException;
	protected abstract void handleRead(SelectionKey key,Selector selector) throws IOException;
	protected abstract void handleWrite(SelectionKey key,Selector selector) throws IOException;
}
