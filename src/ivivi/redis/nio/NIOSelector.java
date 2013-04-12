package ivivi.redis.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NIOSelector {
	
	private static final int channelOne = 1;

	public static void main(String[] args) {
		try {
			testSelector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testSelector() throws Exception {
		
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		
		int interestedKeys = SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT;
		
		SelectionKey key = socketChannel.register(selector, interestedKeys, channelOne);
		
		System.out.println(key.interestOps());
		System.out.println(key.readyOps());
		
	}
}
