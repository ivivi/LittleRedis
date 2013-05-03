package ivivi.redis.core.handler;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.command.BaseCommand;
import ivivi.redis.core.message.Message;
import ivivi.redis.core.test.Steper;
import ivivi.redis.core.util.BufferUtil;
import ivivi.redis.core.util.ByteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientHandler implements BaseCommand {
	
	private final Socket socket = new Socket();
	private final String hostname;
	private final int port;
	private final int timeout;
	private OutputStream os;
	private InputStream is;
	
	private ClientHandler(String hostname,int port,int timeout) {
		this.hostname = hostname;
		this.port = port;
		this.timeout = timeout;
	}
	
	public static ClientHandler getClientHandler(String hostname,int port,int timeout) {
		return new ClientHandler(hostname, port, timeout);
	}
	
	public void initHandler() {
		try {
			socket.setReuseAddress(true);
			socket.setKeepAlive(true);
			socket.setTcpNoDelay(true);
			socket.setSoLinger(true,0);//Control calls close () method, the underlying socket is closed immediately
			
			socket.connect(new InetSocketAddress(hostname, port),timeout);
			socket.setSoTimeout(timeout);
			
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			is.close();
			os.close();
            if (!socket.isClosed()) {
                socket.close();
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	/*
	 * commands
	 */
	@Override
	public void exists(String key) {
		try {
			
			Message message = new Message();
			message.head.setType((byte)1);
			
			message.body.setCommand(key);
			message.head.setBodyLength(key.length() + 2);
			
			this.os.write(message.toBytes());
			
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