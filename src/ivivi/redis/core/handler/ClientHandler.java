package ivivi.redis.core.handler;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.command.BaseCommand;
import ivivi.redis.core.test.Steper;
import ivivi.redis.core.util.BufferUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientHandler extends Handler implements BaseCommand {
	
	private final NIOClient client;
	private SocketChannel socketChannel;
	private final ByteBuffer buffer;
	
	private ClientHandler(NIOClient client) {
		this.client = client;
		this.buffer = ByteBuffer.allocate(1024);
	}
	
	public static ClientHandler getClientHandler(NIOClient client) {
		return new ClientHandler(client);
	}

	@Override
	protected void handleAccept(SelectionKey key,Selector selector) throws IOException {
		
	}

	@Override
	protected void handleConnect(SelectionKey key,Selector selector) throws IOException {
		//SocketChannel socketChannel = (SocketChannel)key.channel();
		this.socketChannel = (SocketChannel)key.channel();
		for(;;) {
			if(socketChannel.finishConnect()) break;
		}
		client.setConnected(true);
		//socketChannel.register(selector, SelectionKey.OP_WRITE);
	}

	@Override
	protected void handleRead(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "read" + key.hashCode());
	}

	@Override
	protected void handleWrite(SelectionKey key,Selector selector) throws IOException {
		System.out.println(Steper.getStep() + "write" + key.hashCode());
	}

	/*
	 * commands
	 */
	@Override
	public void exists(String key) {
		BufferUtil.str2buf(buffer, key);
		try {
			this.socketChannel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void get(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub
		
	}
	
	
}