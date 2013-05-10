package ivivi.redis.core.handler;

import ivivi.redis.core.command.impl.AggregatedCommandImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHandler extends AggregatedCommandImpl {
	
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
	
	@Override
	public OutputStream getOS() {
		if(null == os) 
			initHandler();
		
		return os;
	}

	@Override
	public InputStream getIS() {
		if(null == is)
			initHandler();
		
		return is;
	}
	
}