package ivivi.redis.core.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public abstract class Handler {
	
	public void handle(SelectionKey key,Selector selector) throws IOException {
		if(key.isAcceptable()) {
			handleAccept(key,selector);
		} else if(key.isConnectable()) {
			handleConnect(key);
		} else if(key.isReadable()) {
			handleRead(key);
		} else if(key.isWritable()) {
			handleWrite(key);
		}
	}
	
	protected abstract void handleAccept (SelectionKey key,Selector selector) throws IOException;
	protected abstract void handleConnect(SelectionKey key) throws IOException;
	protected abstract void handleRead(SelectionKey key) throws IOException;
	protected abstract void handleWrite(SelectionKey key) throws IOException;
}
