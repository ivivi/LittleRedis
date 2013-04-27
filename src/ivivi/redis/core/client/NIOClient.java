package ivivi.redis.core.client;

import ivivi.redis.core.handler.ClientHandler;

public class NIOClient {
	
	private ClientHandler handler;

	public NIOClient(String hostname,int port,int timeout) {
		handler = ClientHandler.getClientHandler(hostname,port,timeout);
	}
	
	public void initClient() {
		handler.initHandler();
	}

	public void closeClient() {
		handler.close();
		handler = null;
	}
	
	public boolean isConnected() {
		return handler.isConnected();
	}
	
	public ClientHandler getHandler() {
		return handler;
	}
	
}