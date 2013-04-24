package ivivi.redis.core.test;

import java.nio.ByteBuffer;

import ivivi.redis.core.server.NIOServer;

public class TestServer {

	public static void main(String[] args) {
		try {
			
			NIOServer server = new NIOServer();
			server.initServer();
			server.start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
